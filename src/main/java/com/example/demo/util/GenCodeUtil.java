package com.example.demo.util;

import cn.org.rapid_framework.generator.GeneratorFacade;
import cn.org.rapid_framework.generator.GeneratorProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * 自动生成service代码
 *
 * @author
 */
@Slf4j
public class GenCodeUtil {

//	public static Connection getConnection() throws Exception {
//		String configFilePath = PropertiesUtil.loadResourcePath(Constant.GENERATOR_CONFIG_XML);
//
//		log.debug("[GenCodeUtil][genByMyBatisGenerator]加载MyBatisGenerator配置文件,path:{}", configFilePath);
//		File configFile = new File(configFilePath);
//		ConfigurationParser cp = new ConfigurationParser(null);
//		Configuration config = cp.parseConfiguration(configFile);
//		System.out.println("config = " + config);
//		JDBCConnectionConfiguration jdbcConnectionConfiguration = config.getContext("genTable")
//				.getJdbcConnectionConfiguration();
//		DatabaseConfig databaseConfig = new DatabaseConfig();
//
//		databaseConfig.setConnURL(jdbcConnectionConfiguration.getConnectionURL());
//		databaseConfig.setUserName(jdbcConnectionConfiguration.getUserId());
//		databaseConfig.setUserPwd(jdbcConnectionConfiguration.getPassword());
//		DBTypeEnum dbType = DBTypeEnum.valueOfDriverClass(jdbcConnectionConfiguration.getDriverClass());
//		databaseConfig.setDbType(dbType.name());
//		return DBUtil.getConnection(databaseConfig);
//	}

	/**
	 * 使用模板生成mapper代码
	 *
	 * @throws Exception
	 * @param tableNames
	 */
	public static void genMapper(String[] tableNames) throws Exception {
		genByTemplate(tableNames, "template/crud/mapper");
		log.info("[GenCodeUtil][genMapper]生成mapper成功！tableNames:{}", tableNames);
	}

	/**
	 * 使用模板生成service代码
	 *
	 * @throws Exception
	 * @param tableNames
	 */
	public static void genService(String[] tableNames) throws Exception {
		genByTemplate(tableNames, "template/crud/service");
		log.info("[GenCodeUtil][genService]生成service成功！tableNames:{}", tableNames);
	}

	/**
	 * 使用模板生成rpc service的 interface
	 *
	 * @throws Exception
	 */
	public static void genRpcInterface(String[] tableNames) throws Exception {
		GeneratorFacade g = new GeneratorFacade();
		String outRootDir = g.getGenerator().getOutRootDir();
		outRootDir = outRootDir.replaceAll("-service", "-interface");
		genByTemplate(tableNames, "template/crud/rpcServiceInterface", outRootDir);
		log.info("[GenCodeUtil][genRpcInterface] 生成rpc service interface 成功,tableNames:{}", tableNames);
	}

	/**
	 * 使用模板生成rpc service impl代码
	 *
	 * @throws Exception
	 * @param tableNames
	 */
	public static void genRpcServiceImpl(String[] tableNames) throws Exception {
		genByTemplate(tableNames, "template/crud/rpcServiceImpl");
		log.info("[GenCodeUtil][genRpcServiceImpl]生成rpc service impl 成功！tableNames:{}", tableNames);
	}

	/**
	 * 使用模板生成 service的test case
	 *
	 * @throws Exception
	 * @param tableNames
	 * @param testCasseOutRootDir
	 */
	public static void genServiceTestCase(String[] tableNames, String testCasseOutRootDir) throws Exception {
		genByTemplate(tableNames, "template/crud/serviceTest", testCasseOutRootDir);
		log.info("[GenCodeUtil][genServiceTestCase]生成service test case 成功! tableNames:{},testCasseOutRootDir:{}",
				tableNames, testCasseOutRootDir);
	}

	/**
	 * 使用模板生成 rpc service的test case
	 *
	 * @throws Exception
	 * @param tableNames
	 * @param rpcTestCaseOutRootDir
	 */
	public static void genRpcServiceTestCase(String[] tableNames, String rpcTestCaseOutRootDir) throws Exception {
		genByTemplate(tableNames, "template/crud/rpcServiceTest", rpcTestCaseOutRootDir);
		log.info(
				"[GenCodeUtil][genRpcServiceTestCase]生成rpc service test case 成功! tableNames:{},rpcTestCaseOutRootDir:{}",
				tableNames, rpcTestCaseOutRootDir);
	}



	/**
	 * 使用模板生成代码，生成目标路径为generator.properties中配置的路径
	 *
	 * @param tableNames
	 * @param hlmyTemplateName
	 * @throws Exception
	 */
	private static void genByTemplate(String[] tableNames, String hlmyTemplateName) throws Exception {
		genByTemplate(tableNames, hlmyTemplateName, null);
	}


	/**
	 * 使用模板生成代码基础方法，根据模板路径找到对应的模板，生成对应的文件
	 *
	 * @param tableNames 表名数组
	 * @param hlmyTemplateName 模板名称
	 * @param outRootDir 输出路径
	 * @throws Exception
	 */
	private static void genByTemplate(String[] tableNames, String hlmyTemplateName, String outRootDir)
			throws Exception {
		// 指定模板读取路径
		//String templatePath = ClassLoader.getSystemResource(hlmyTemplateName).toString();
		String templatePath = null; //PropertiesUtil.loadResourcePath(hlmyTemplateName);
//		String templatePath = PropertiesUtil.loadResourcePath(hlmyTemplateName);

		GeneratorFacade g = new GeneratorFacade();
		g.getGenerator().addTemplateRootDir(templatePath);
		// 有外部的输出路径，覆盖之
		if (StringUtils.isNotBlank(outRootDir)) {
			g.getGenerator().setOutRootDir(outRootDir);
		}
		// 兼容处理文件路径
		outRootDir = g.getGenerator().getOutRootDir();
//		outRootDir = FileUtil.getRealFilePath(outRootDir);
		g.getGenerator().setOutRootDir(outRootDir);
		// 通过数据库表生成文件
		g.generateByTable(tableNames);
		// 避免生成工具本地线程缓存，每次调用之后需要清除上下文并重载
		GeneratorProperties.clear();
		GeneratorProperties.reload();

	}



//	/**
//	 * 使用MyBatisGenerator生成model、criteria、mapper等文件
//	 *
//	 */
//	public static void genByMyBatisGenerator() {
//		List<String> warnings = new ArrayList<>();
//		try {
//			String configFilePath = PropertiesUtil.loadResourcePath(Constant.GENERATOR_CONFIG_XML);
//			log.debug("[GenCodeUtil][genByMyBatisGenerator]加载MyBatisGenerator配置文件,path:{}", configFilePath);
//			boolean overwrite = true;
//			File configFile = new File(configFilePath);
//			ConfigurationParser cp = new ConfigurationParser(warnings);
//			Configuration config = cp.parseConfiguration(configFile);
//			DefaultShellCallback callback = new DefaultShellCallback(overwrite);
//			MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
//			ProgressCallback progressCallback = new VerboseProgressCallback();
//			myBatisGenerator.generate(progressCallback);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		for (String warning : warnings) {
//			log.debug("[GenCodeUtil][genByMyBatisGenerator]warning:{}", warning);
//		}
//	}
}
