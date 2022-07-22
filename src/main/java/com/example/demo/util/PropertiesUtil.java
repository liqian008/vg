package com.example.demo.util;

import lombok.extern.slf4j.Slf4j;


/**
 * @author :xuejw
 * @date 2019-01-17 14:59
 */
@Slf4j
public class PropertiesUtil {


//	/**
//	 * 更新properites配置文件
//	 *
//	 * @param map
//	 */
//	public static void updateProperites(Map<String,String> map){
//		Properties properties = loadProp(Constant.PROPERTIES_FILE_NAME);
//		for (Map.Entry<String,String> item : map.entrySet()) {
//			properties.setProperty(item.getKey(), item.getValue());
//		}
//		String propertiesFilePath = loadResourcePath(Constant.PROPERTIES_FILE_NAME);
//		writeProp(properties, propertiesFilePath);
//	}
//
//	/**
//	 * load prop
//	 *
//	 * @param propertyFileName disk path when start with "file:", other classpath
//	 * @return
//	 */
//	public static Properties loadProp(String propertyFileName) {
//		Properties prop = new Properties();
//		InputStream in = null;
//		try {
//			// load file location, disk or resource
//			String filePrefix = "file:";
//			if (propertyFileName.startsWith(filePrefix)) {
//				URL url = new File(propertyFileName.substring(filePrefix.length())).toURI().toURL();
//				in = new FileInputStream(url.getPath());
//			} else {
//				ClassLoader loder = Thread.currentThread().getContextClassLoader();
//				in = loder.getResourceAsStream(propertyFileName);
//			}
//			if (in != null) {
//				prop.load(new InputStreamReader(in, StandardCharsets.UTF_8));
//			}
//		} catch (IOException e) {
//			log.error("[loadProp]load prop fail，propertyFileName:{}", propertyFileName);
//		} finally {
//			if (in != null) {
//				try {
//					in.close();
//				} catch (IOException e) {
//					log.error(e.getMessage(), e);
//				}
//			}
//		}
//		return prop;
//	}
//
//	/**
//	 * load resource path
//	 *
//	 * @param resourceFileName disk path when start with "file:", other classpath
//	 * @return resource path
//	 */
//	public static String loadResourcePath(String resourceFileName) {
//		String result = "";
//		// load file location, disk or resource
//		String filePrefix = "file:";
//		try {
//			if (resourceFileName.startsWith(filePrefix)) {
//				URL url = new File(resourceFileName.substring(filePrefix.length())).toURI().toURL();
//				result = url.getPath();
//			} else {
//				ClassLoader loder = Thread.currentThread().getContextClassLoader();
//				result  = Objects.requireNonNull(loder.getResource(resourceFileName)).getPath();
//			}
//		} catch (Exception e) {
//			log.error("[loadResourcePath]resourceFileName:{},exception:{}", resourceFileName, e);
//		}
//		return result;
//	}
//
//	/**
//	 * write prop to disk
//	 *
//	 * @param properties
//	 * @param filePathName
//	 * @return
//	 */
//	public static boolean writeProp(Properties properties, String filePathName) {
//		try (FileOutputStream fileOutputStream = new FileOutputStream(filePathName, false)) {
//			File file = new File(filePathName);
//			// 若文件不存在,则先创建父文件夹
//			if (!file.exists()) {
//				FileUtils.forceMkdirParent(file);
//			}
//			properties.store(new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8), null);
//			return true;
//		} catch (IOException e) {
//			log.error(e.getMessage(), e);
//			return false;
//		}
//	}
//
//	/**
//	 * 查看是否存在配置文件,如果不存在则创建
//	 *
//	 */
//	public static void existsFile(String fileName) {
//		File file = new File(fileName);
//		if (!file.exists()) {
//			try (InputStream fis = Thread.currentThread().getContextClassLoader()
//					.getResourceAsStream(fileName);
//					FileOutputStream fos = new FileOutputStream(fileName)) {
//				byte[] buffer = new byte[1024];
//				int byteread = 0;
//				while ((byteread = fis.read(buffer)) != -1) {
//					fos.write(buffer, 0, byteread);
//				}
//			}catch (IOException e){
//				log.error("[existsConfig],exception:{}", e);
//			}
//		}
//	}
}
