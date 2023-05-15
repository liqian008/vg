package com.example.demo.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.org.rapid_framework.generator.GeneratorFacade;
import cn.org.rapid_framework.generator.GeneratorProperties;
import cn.org.rapid_framework.generator.util.ZipUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.demo.consts.Constants;
import com.example.demo.model.*;
import com.example.demo.model.entity.TemplateEntity;
import com.example.demo.model.metadata.MetadataColumnVo;
import com.example.demo.model.metadata.MetadataTableVo;
import com.example.demo.service.IGenerateService;
import com.example.demo.service.IMetadataService;
import com.example.demo.service.entity.ITemplateEntityService;
import com.example.demo.util.JsonUtil;
import com.example.demo.util.ZipUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.example.demo.consts.Constants.*;

/**
 * @author bruce
 */
@Slf4j
@Service
public class GenerateServiceImpl implements IGenerateService, InitializingBean {


	/** 模板存放目录 */
	@Value("${variable-generator.path.templates}")
	private String DIRNAME_TEMPLATE_ROOT;
	/** 代码输出目录 */
	@Value("${variable-generator.path.output}")
	private String DIRNAME_OUTPUT;
	/** 上传文件&解压的临时目录 */
	@Value("${variable-generator.path.temp}")
	private String DIRNAME_TEMPLATE_TMP;

	/** 模板存放目录 */
	private File templatesRootDir = null;
	/** 代码输出目录 */
	private File outputRootDir = null;
	/** 临时目录 */
	private File tmpDir = null;

	@Autowired
	private ITemplateEntityService templateEntityService;
	@Autowired
	private IMetadataService metadataService;

	/**
	 * 预加载
	 */
	@PostConstruct
	private void init() throws Exception {
		reloadTemplatesProperties();
	}

	private void reloadTemplatesProperties() throws Exception {
		//TODO 预加载，减少IO开销
	}


	/**
	 * 根据请求参数生成文件
	 * @param generateConfig
	 * @return
	 */
	@Override public FileDownloadVo generate(GenerateConfigBase generateConfig) throws Exception {
		//TODO 校验参数
		String templateKey = generateConfig.getTemplateKey();
		synchronized (templateKey.intern()) {
			try {
				templateEntityService.loadByTemplateKey(templateKey, true);
			} catch (Exception e) {
				throw new Exception("模板不存在，请检查！");
			}

			//先清空Output目录，FIXME Sass场景下有问题
			if (outputRootDir.exists()) {
				try {
					FileUtils.cleanDirectory(outputRootDir);
				} catch (Exception e) {
					//目录或其中文件被占用无法删除的异常情况
					log.error("[generate]error, filePath:{}", outputRootDir.getAbsolutePath());
					//TODO 抛异常
				}
			}
			//File destTemplateDir = new File(outputRootDir, templateName);
			//处理文件
			String filename = processOutputFile(generateConfig, outputRootDir);
			FileDownloadVo result = new FileDownloadVo(filename, "/code/download?filename=" + filename);

			log.info("[generate]result:{}, generateConfig:{}", result, generateConfig);
			return result;
		}
	}

    /**
	 * 上传zip包文件
	 * 上传后，先做解压（临时目录），处理文件
	 * TODO 考虑模板升级后，文件覆盖的场景;  上传的同步锁
	 *
	 * @param userId
	 * @param templateZipFile
	 * @return
	 */
	@Override public boolean uploadTemplateFile(int userId, MultipartFile templateZipFile) throws Exception {

		Date currentTime = new Date();
		String zipFilefullname = templateZipFile.getOriginalFilename();
		String templateKey = StringUtils.left(zipFilefullname,  zipFilefullname.length() - SUFFIX_ZIP.length());

		synchronized (templateKey.intern()){
			log.info("[uploadTemplateFile]enter, templateKey:{}, filename:{}", templateKey, zipFilefullname);
			//为避免重复，增加时间戳后缀
			String datetimeText = DateFormatUtils.format(currentTime, FORMAT_YYYYMMDD_HHMMSS);
			String newZipFilename = templateKey;//TODO + "_" + datetimeText;
			String newZipFileFullname = newZipFilename + SUFFIX_ZIP;
			//		if(!tmpDir.exists()){
			//			tmpDir.mkdirs();
			//		}
			log.info("[uploadTemplateFile]enter, tmpDir:{}", tmpDir.getAbsolutePath());
			boolean result;

			//将zip文件写到临时目录
			File destFile = new File(tmpDir, newZipFileFullname);
			FileUtils.writeByteArrayToFile(destFile, templateZipFile.getBytes());
			//解压包，获取json配置文件
			ZipUtil.exctract(destFile, tmpDir.getAbsolutePath());
			//检查配置文件中内容
			File zipDirFile =  new File(tmpDir, newZipFilename);
			File uploadedTemplateConfig = new File(zipDirFile, FILENAME_TEMPLATE_CONFIG);
			if(!uploadedTemplateConfig.exists()){
				throw new Exception("模板中缺少配置文件"+FILENAME_TEMPLATE_CONFIG+"，请检查。" );
			}
			StringBuilder configSb = readConfigText(uploadedTemplateConfig);

			TemplateVo uploadedTemplateVo;
			String configText = configSb.toString();
			try {
				uploadedTemplateVo = JsonUtil.GSON.fromJson(configSb.toString(), TemplateVo.class);
			}catch(Exception e){
				throw new Exception("模板配置文件"+FILENAME_TEMPLATE_CONFIG+"格式有误，请检查。" );
			}

			if(uploadedTemplateVo.getVersion()==null || uploadedTemplateVo.getVersion()<=0 ){
				throw new Exception("模板配置文件中verion不合法，请检查。" );
			}

			TemplateEntity templateEntity = templateEntityService.loadByTemplateKey(templateKey);

			if(TemplateEntity.isValid(templateEntity)){
				if(userId != templateEntity.getCreateUid()){
					throw new Exception(templateKey+"对应的模板已存在，请更换。" );
				}
			}

			if(TemplateEntity.isValid(templateEntity)){
				if(uploadedTemplateVo.getVersion() <= templateEntity.getVersion()){
					throw new Exception("模板配置文件中verion不能低于当前模板version，请检查。" );
				}
			}

			if(!StringUtils.isNotBlank(uploadedTemplateVo.getTitle())){
				throw new Exception("模板配置文件"+FILENAME_TEMPLATE_CONFIG+"中title未填写，请检查。" );
			}
			if(!StringUtils.equalsIgnoreCase(templateKey, uploadedTemplateVo.getTemplateKey())){
				throw new Exception("模板配置文件"+FILENAME_TEMPLATE_CONFIG+"中templateKey与文件名不匹配，请检查。" );
			}

			//删除原模板文件，TODO 保留历史，而非直接删除
			File descDirFile = new File(templatesRootDir, templateKey);
			FileUtils.deleteQuietly(descDirFile);
			File descZipFile = new File(templatesRootDir, templateKey+ SUFFIX_ZIP);
			FileUtils.deleteQuietly(descZipFile);

			//将新的zip文件移动到Templates目录下
			FileUtils.moveFileToDirectory(new File(tmpDir, newZipFileFullname), templatesRootDir, true);
			ZipUtils.unzip(templatesRootDir, new File(templatesRootDir, templateKey+ SUFFIX_ZIP));

			//		FileUtils.moveDirectory(new File(tmpDir, templateKey), new File(templatesRootDir, templateKey));

			if(TemplateEntity.isValid(templateEntity)){
				//更新模板
				templateEntity.setVersion(uploadedTemplateVo.getVersion());
				templateEntity.setTitle(uploadedTemplateVo.getTitle());
				templateEntity.setDescription(uploadedTemplateVo.getDescription());
				templateEntity.setPath(newZipFilename);
				templateEntity.setConfig(configText);
				templateEntity.setUpdateUid(USER_ID_OFFICIAL);
				templateEntity.setUpdateTime(currentTime);
				result = templateEntityService.updateById(templateEntity);
			}else{
				//创建模板DB
				templateEntity = new TemplateEntity();
				templateEntity.setTemplateKey(templateKey);
				templateEntity.setVersion(uploadedTemplateVo.getVersion());
				templateEntity.setTitle(uploadedTemplateVo.getTitle());
				templateEntity.setDescription(uploadedTemplateVo.getDescription());
				templateEntity.setPath(newZipFilename);
				templateEntity.setConfig(configText);
				templateEntity.setCreateUid(USER_ID_OFFICIAL);
				templateEntity.setCreateTime(currentTime);
				result = templateEntityService.save(templateEntity);
			}
			return result;
		}
	}

	/**
	 * 读取模板中的配置文件
	 * @param uploadedTemplateConfig
	 * @return
	 * @throws Exception
	 */
	private StringBuilder readConfigText(File uploadedTemplateConfig) throws Exception {
		StringBuilder configSb = new StringBuilder();
		try {
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(new FileInputStream(uploadedTemplateConfig), "utf-8"));
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				configSb.append(line);
			}
			bufferedReader.close();
		} catch (IOException e) {
			//处理失败
			throw new Exception("模板配置文件加载失败，请检查: " + uploadedTemplateConfig.getAbsolutePath());
		}
		return configSb;
	}


	@Override public File doZipFile(String sourceDirName) throws Exception {
		File sourceDir = new File(outputRootDir, sourceDirName);
		String destZipFilename = sourceDirName + SUFFIX_ZIP;
		File descZipFile = new File(outputRootDir, destZipFilename);
		ZipUtil.zip(descZipFile.getAbsolutePath(), sourceDir.getAbsolutePath());
		return descZipFile;
	}

	/**
	 * TODO 事务
	 * @param templateKey
	 * @return
	 */
	@Override public boolean deleteTemplate(String templateKey) throws IOException {
		//删除数据库
		LambdaQueryWrapper<TemplateEntity> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(TemplateEntity::getTemplateKey, templateKey);
		templateEntityService.remove(wrapper);
		//删除模板文件
		File selectedTemplateFile = new File(templatesRootDir, templateKey);
		FileUtils.deleteQuietly(selectedTemplateFile);
		return true;
	}

	/**
	 * 处理要输出的文件
	 * @param generateConfig 变量参数
//	 * @param sourceTemplateDir 模板目录的文件对象
	 * @param outputRootDir 输出的文件对象
	 * @throws Exception
	 */
	private String processOutputFile(GenerateConfigBase generateConfig, File outputRootDir) throws Exception {
		String templateKey = generateConfig.getTemplateKey();

		String outputDirName = templateKey;
		if(GenerateConfigBase.isOutputZip(generateConfig)){
			//zip包的方式（Saas化部署），为避免重复，增加时间戳后缀
			String datetimeText = DateFormatUtils.format(new Date(), FORMAT_YYYYMMDD_HHMMSS);
			outputDirName = templateKey + "_" + datetimeText;
		}
		//使用rapid-generator生成文件
		File destDirFile = new File(outputRootDir, outputDirName);
		doRapidGenerate(new File(templatesRootDir, templateKey), destDirFile, generateConfig);
//		String outputDirName = doRapidGenerate(templatesRootDir, outputRootDir, varMap);

		//清除无用的文件
		clearUselessFiles(destDirFile);
		return outputDirName;
	}



	/**
	 * 使用rapid-generator生成文件
	 * @param sourceTemplateDir
	 * @param outputRootDir
	 * @param generateConfig
	 * @throws Exception
	 */

	private void doRapidGenerate(File sourceTemplateDir, File outputRootDir, GenerateConfigBase generateConfig) throws Exception {
//	private void doRapidGenerate(File sourceTemplateDir, File outputRootDir, Map<String, String> varMap) throws Exception {
		GeneratorFacade generator = new GeneratorFacade();
		generator.getGenerator().addTemplateRootDir(sourceTemplateDir);

//		File descTemplateFileDir = new File(outputRootDir, outputDirName);
		generator.getGenerator().setOutRootDir(outputRootDir.getAbsolutePath());
		Map<String, String> varMap = generateConfig.getVarMap();

		if(GenerateConfigBase.isGenerateCrud(generateConfig)){
			// CRUD模式

			// 根据配置，获取元数据信息
			int datasourceId = generateConfig.getTableCrudConfig().getDatasourceId();
			String dbName = generateConfig.getTableCrudConfig().getDbName();
			List<GenerateConfigBase.TableInfo> tables = generateConfig.getTableCrudConfig().getTables();
			List<String> tableNames = tables==null?null:tables.stream().map(item->item.getTableName()).collect(Collectors.toList());

			List<MetadataTableVo> metadataTableVos = metadataService.listUserTables(1, datasourceId, dbName, tableNames==null?null:new HashSet<>(tableNames));

			// 构造建表所需的变量
			List<GenerateTableVo> generateTableVos = convert(generateConfig, metadataTableVos);

			//for循环生成表操作相关内容
			for(GenerateTableVo loopTableVo: generateTableVos) {
				//TODO 克隆varMap数据（序列化反序列化，或者能保证能覆盖式替换变量值，避免复用）
				Map<String, Object> crudVarMap = new HashMap<>(generateConfig.getVarMap());
				crudVarMap.put(Constants.TABLE, loopTableVo);

				generator.generateByMap(crudVarMap);
				// 避免生成工具本地线程缓存，每次调用之后需要清除上下文并重载
				GeneratorProperties.clear();
				GeneratorProperties.reload();
			}
		}else{
			// 项目工程文件模式

			generator.generateByMap(varMap);
			// 避免生成工具本地线程缓存，每次调用之后需要清除上下文并重载
			GeneratorProperties.clear();
			GeneratorProperties.reload();
		}
//		return outputDirName;
	}

	/**
	 * 将元数据信息转为generate所需的对象信息
	 *
	 * @param generateConfig
	 * @param metadataTableVos
	 * @return
	 */
	private List<GenerateTableVo> convert(GenerateConfigBase generateConfig, List<MetadataTableVo> metadataTableVos) {
		List<GenerateTableVo> result = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(metadataTableVos)){

			String globalTablenamePrefix = null;
			final Map<String, String> tablenamePrefixMap = new HashMap<>();
			boolean chooseAll = generateConfig.getTableCrudConfig().isChooseAll();
			if(chooseAll){
				globalTablenamePrefix = generateConfig.getTableCrudConfig().getTablenamePrefix();
			}else{
				//非全局模式，则需逐一替换表名的前缀，先构造表名的map数据做缓存
				generateConfig.getTableCrudConfig().getTables().stream().forEach(
						item -> tablenamePrefixMap.put(item.getTableName(), item.getTablenamePrefix()));
			}

			for(MetadataTableVo loopTable: metadataTableVos){
				//处理表名的前缀（全局or逐一处理）
				String tablenamePrefix;
				if(chooseAll){
					tablenamePrefix = globalTablenamePrefix;
				}else{
					tablenamePrefix = tablenamePrefixMap.get(loopTable.getTableName());
				}

				//类名&变量的驼峰命名（TODO 名称考虑支持自定义扩展）
				String camelCaseText =  StrUtil.toCamelCase(StringUtils.replace(loopTable.getTableName(), tablenamePrefix, ""));
				String className = buildClassName(camelCaseText);

				GenerateTableVo tableVo = GenerateTableVo.builder()
						.name(loopTable.getTableName()).remark(loopTable.getRemark()).className(className).build();

				List<GenerateTableVo.GenerateColumnVo> columnVos = new ArrayList<>();

				List<MetadataColumnVo> columns = loopTable.getColumns();
				for(MetadataColumnVo loopColumn: columns){
					GenerateTableVo.GenerateColumnVo columnVo = GenerateTableVo.GenerateColumnVo.builder()
							.name(loopColumn.getColumn()).remark(loopColumn.getRemark()).fieldType(loopColumn.getDataType())
							//驼峰
							.fieldName(buildClassName(loopColumn.getColumn()))
							.build();
					columnVos.add(columnVo);
				}
				tableVo.setColumns(columnVos);
				result.add(tableVo);
			}
		}
		return result;
	}

	/**
	 * 生成className
	 * @param text
	 * @return
	 */
	private String buildClassName(String text) {
		return StringUtils.upperCase(StringUtils.substring(text, 0, 1))
							+  StringUtils.substring(text, 1, text.length());
	}

//	/**
//	 * 使用rapid-generator生成文件
//	 * @param sourceTemplateDir
//	 * @param outputRootDir
//	 * @param varMap
//	 * @throws Exception
//	 */
//	private String doRapidGenerate(File sourceTemplateDir, File outputRootDir, String templateKey, Map<String, String> varMap) throws Exception {
//		GeneratorFacade g = new GeneratorFacade();
//		g.getGenerator().addTemplateRootDir(sourceTemplateDir);
//		//为避免重复，增加时间戳后缀
//		String datetimeText = DateFormatUtils.format(new Date(), FORMAT_YYYYMMDD_HHMMSS);
//		String outputDirName = templateKey + "_" + datetimeText;
//		File descTemplateFileDir = new File(outputRootDir, outputDirName);
//		g.getGenerator().setOutRootDir(descTemplateFileDir.getAbsolutePath());
//		// 通过数据库表生成文件
//		g.generateByMap(varMap);
//		// 避免生成工具本地线程缓存，每次调用之后需要清除上下文并重载
//		GeneratorProperties.clear();
//		GeneratorProperties.reload();
//
//		return outputDirName;
//	}


	/**
	 * 清除目录中无用的文件
	 * @param destTemplateDir 目录
	 * @throws IOException
	 */
	private void clearUselessFiles(File destTemplateDir) throws IOException {
		//模板配置文件
		FileUtils.deleteQuietly(new File(destTemplateDir, FILENAME_TEMPLATE_CONFIG));
	}



	//	/**
//	 * 加载变量文件到properties
//	 * @param destTemplateDir
//	 * @return
//	 * @throws Exception
//	 */
//	private TemplateVo initTemplateConfig(File destTemplateDir) throws Exception {
//		File templateConfigFile = new File(destTemplateDir, FILENAME_TEMPLATE_CONFIG);
//		if (!templateConfigFile.exists()) {
//			throw new Exception("模板不规范，模板配置不存在，请检查: " + templateConfigFile.getAbsolutePath());
//		}
//		StringBuilder sb = readConfigText(templateConfigFile);
//		TemplateVo result = GSON.fromJson(sb.toString(), TemplateVo.class);
//		return result;
//	}



//	/**
//	 * 加载变量文件到properties
//	 * @param destTemplateDir
//	 * @return
//	 * @throws Exception
//	 */
//	@Deprecated
//	private Properties initVarProperties(File destTemplateDir) throws Exception {
//		File variableFile = new File(destTemplateDir, FILENAME_TEMPLATE_CONFIG);
////		File variableFile = new File(destTemplateDir, FILENAME_VAR_PROP);
//		if (!variableFile.exists()) {
//			throw new Exception("模板不规范，模板配置不存在，请检查: " + variableFile.getAbsolutePath());
//		}
//		InputStream inputStream = new FileInputStream(variableFile);
//		Properties variableProperties = new Properties();
//		try {
//			variableProperties.load(new InputStreamReader(inputStream, CHARSET_UTF8));
//		} catch (IOException e) {
//			log.error("[initVarProperties]error, destTemplateDir:{}, exception:{}", destTemplateDir, e);
//			throw new Exception("模板配置文件加载失败，请检查: " + variableFile.getAbsolutePath());
//		} finally {
//			inputStream.close();
//		}
//
//		if(StringUtils.isBlank((String) variableProperties.get(KEY_TEMPLATE_NAME))){
//			throw new Exception("选定模板中变量"+KEY_TEMPLATE_NAME+"不存在，请检查！" );
//		}
//		if(StringUtils.isBlank((String) variableProperties.get(KEY_TEMPLATE_DESCRIPTION))){
//			throw new Exception("选定模板中变量"+KEY_TEMPLATE_DESCRIPTION+"不存在，请检查！" );
//		}
//		return variableProperties;
//	}



	//	/**
	//	 * 获取模板列表
	//	 * @return
	//	 */
	//	@Override public List<TemplateVo> getTemplateList() throws Exception {
	//		File[] templateDirs = listTemplateDirs(templatesRootFile);
	//		List<TemplateVo> result = new ArrayList<>();
	//		for(File templateDirFile: templateDirs) {
	//			TemplateVo templateConfigVo = initTemplateConfig(templateDirFile);
	//			templateConfigVo.setTemplateKey(templateDirFile.getName());
	////			Properties variableProperties = initVarProperties(templateDirFile);
	////			String templateName = (String) variableProperties.get(KEY_TEMPLATE_NAME);
	////			String templateDescription = (String) variableProperties.get(KEY_TEMPLATE_DESCRIPTION);
	////			TemplateVo templateVo = TemplateVo.builder()
	////					.dirname(templateDirFile.getName()).title(templateName).description(templateDescription)
	////					.build();
	//			//new TemplateVo(templateDirFile.getName(), templateName, templateDescription, null);
	//			result.add(templateConfigVo);
	//		}
	//		log.info("[getTemplateList]result:{}", result);
	//		return result;
	//	}


	//	@Override public Set<VarDefinitionVo> loadTemplateVars(String templateName) throws Exception {
	//		log.info("[loadTemplatVars]enter, templateName:{}", templateName);
	//		LinkedHashSet<VarDefinitionVo> result = new LinkedHashSet<>();
	//		File sourceTemplateDir = new File(templatesRootFile, templateName);
	//		TemplateVo templateVo = initTemplateConfig(sourceTemplateDir);
	//
	//
	//
	////		Properties varProperties = initVarProperties(sourceTemplateDir);
	////		Set<Map.Entry<Object, Object>> entries = varProperties.entrySet();
	////		for(Map.Entry entry: entries){
	////			String key = (String) entry.getKey();
	////			VarDefinitionVo varDefinitionVo = new VarDefinitionVo();
	////			varDefinitionVo.setKey(key);
	////			varDefinitionVo.setRequired(true);
	////			result.add(varDefinitionVo);
	////		}
	//		log.info("[loadTemplatVars]result:{}, templateName:{}", result, templateName);
	//
	//		return result;
	//	}


//	/**
//	 *
//	 * @param templatesRootFile
//	 * @return
//	 * @throws Exception
//	 */
//	private File[] listTemplateDirs(File templatesRootFile) throws Exception {
//		if(!templatesRootFile.exists()){
//			throw new Exception("模板目录不存在，请检查: " + templatesRootFile.getAbsolutePath());
//		}
//		File[] templateDirs = templatesRootFile.listFiles(file -> file != null && file.isDirectory());
//		if(templateDirs==null || templateDirs.length<=0){
//			throw new Exception("模板ROOT目录下无模板文件，请检查: " + templatesRootFile.getAbsolutePath());
//		}
//		return templateDirs;
//	}


	@Override public void afterPropertiesSet() throws Exception {
		/** 模板存放目录 */
		templatesRootDir = new File(DIRNAME_TEMPLATE_ROOT);
		/** 代码输出目录 */
		outputRootDir = new File(DIRNAME_OUTPUT);
		/** 临时目录 */
		tmpDir = new File(DIRNAME_TEMPLATE_TMP);
	}

	public static void main(String[] args) throws Exception {

		System.out.println(StrUtil.toCamelCase("a_b"));
//		List<GenerateTableVo> tableVos = new ArrayList<>();
//		for(int i=0;i<3;i++){
//			GenerateTableVo tableVo = new GenerateTableVo();
//			tableVo.setClassName("TbApp"+i);
//			tableVo.setName("tb_app"+i);
//			tableVo.setRemark("表注释"+i);
//
//
//			Map<String, Object> extraData = new HashMap<>();
//			extraData.put("businessName", "app");
//			extraData.put("path", "/app");
//			tableVo.setExtraData(extraData);
//
//			//TODO 映射字段类型到Java类型
//			GenerateTableVo.GenerateColumnVo columnVo1 = GenerateTableVo.GenerateColumnVo.builder().fieldType("Integer").fieldName("id").name("id").remark("字段1").build();
//			GenerateTableVo.GenerateColumnVo columnVo2 = GenerateTableVo.GenerateColumnVo.builder().fieldType("String").fieldName("name").name("name").remark("字段2").build();
//			tableVo.setColumns(Stream.of(columnVo1, columnVo2).collect(Collectors.toList()));
//
//			tableVos.add(tableVo);
//
//			Map<String, Object> varMap = new HashMap<>();
//			varMap.put("basepackage", "com.bruce");
//			varMap.put("author", "bruce");
////			varMap.put("className", "TbApp"+i);
//			generate(varMap, tableVos);
//		}
	}


	private static void generate(Map<String, Object> varMap, List<GenerateTableVo> tableVoList) throws Exception {
		if(tableVoList==null){
			tableVoList = new ArrayList<>();
		}

		GeneratorFacade g = new GeneratorFacade();
		g.getGenerator().addTemplateRootDir("D:\\git\\bruce\\backend\\vg\\templates");
		g.getGenerator().setOutRootDir("D:\\git\\bruce\\backend\\vg\\output");

//		Map<String, Object> tableMap = new HashMap<>();
		for(GenerateTableVo loopItem: tableVoList){
			//TODO 表生成的默认保留配置，如：表前缀，驼峰配置等

			varMap.put(Constants.TABLE, loopItem);
//			varMap.put("className", "abc_test"+i);
			// 通过数据库表生成文件
			g.generateByMap(varMap);
		}

	}
}
