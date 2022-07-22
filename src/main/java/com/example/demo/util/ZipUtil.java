package com.example.demo.util;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Zip工具类
 * @author bruce
 */
@Slf4j
public class ZipUtil {

	/**
	 * @param zipFileName 压缩后的文件(整个完整路径)
	 * @param inputFileName 你要压缩的文件夹(整个完整路径)
	 * @throws Exception
	 */
	public static boolean zip(String zipFileName, String inputFileName) throws Exception {
		zip(zipFileName, new File(inputFileName));
		return true;
	}

	private static void zip(String zipFileName, File inputFile) throws Exception {
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFileName));
		zip(out, inputFile, "");
		out.flush();
		out.close();
	}

	private static void zip(ZipOutputStream out, File file, String base) throws Exception {
		if (file.isDirectory()) {
			File[] fl = file.listFiles();
			out.putNextEntry(new ZipEntry(base + "/"));
			base = base.length() == 0 ? "" : base + "/";
			for (int i = 0; i < fl.length; i++) {
				zip(out, fl[i], base + fl[i].getName());
			}
		} else {
			out.putNextEntry(new ZipEntry(base));
			FileInputStream in = new FileInputStream(file);
			int b;
			while ((b = in.read()) != -1) {
				out.write(b);
			}
			in.close();
		}
	}


	/**
	 * 解压缩
	 * @param zipFilePath 要解压的文件
	 * @param destPath 解压到某文件夹
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List exctract(String zipFilePath, String destPath) {
		File zipFile = new File(zipFilePath);
		return exctract(zipFile, destPath);
	}

	/**
	 * 解压缩
	 * @param zipFile 要解压的文件
	 * @param destPath 解压到某文件夹
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<String> exctract(File zipFile, String destPath) {
		List<String> allFileName = new ArrayList<>();
		try {
			// 先指定压缩档的位置和档名，建立FileInputStream对象
			FileInputStream fins = new FileInputStream(zipFile);
			// 将fins传入ZipInputStream中
			ZipInputStream zins = new ZipInputStream(fins);
			ZipEntry ze = null;
			byte[] ch = new byte[256];
			while ((ze = zins.getNextEntry()) != null) {
				File zfile = new File(destPath, ze.getName());
				File fpath = new File(zfile.getParentFile().getPath());
				if (ze.isDirectory()) {
					if (!zfile.exists())
						zfile.mkdirs();
					zins.closeEntry();
				} else {
					if (!fpath.exists())
						fpath.mkdirs();
					FileOutputStream fouts = new FileOutputStream(zfile);
					int i;
					allFileName.add(zfile.getAbsolutePath());
					while ((i = zins.read(ch)) != -1)
						fouts.write(ch, 0, i);
					zins.closeEntry();
					fouts.close();
				}
			}
			fins.close();
			zins.close();
		} catch (Exception e) {
			System.err.println("Extract error:" + e.getMessage());
		}
		return allFileName;
	}





//	public static void main(String [] temp){
//		try {
//			zip("E:\\ftl","E:\\test.zip");//你要压缩的文件夹   和 压缩后的文件
//		}catch (Exception ex) {
//			ex.printStackTrace();
//		}
//	}


//	/**
//	 *  压缩指定文件夹中的所有文件，生成指定名称的zip压缩包
//	 *
//	 * @param sourcePath 需要压缩的文件名称列表(包含相对路径)
//	 * @param outputZipFilename 压缩后的文件名称
//	 **/
//	public static String doZip(String sourcePath, String outputZipFilename) {
//		ZipOutputStream zipOutputStream = null;
//		WritableByteChannel writableByteChannel = null;
//		MappedByteBuffer mappedByteBuffer = null;
//		try {
//			zipOutputStream = new ZipOutputStream(new FileOutputStream(outputZipFilename));
//			writableByteChannel = Channels.newChannel(zipOutputStream);
//			File file = new File(sourcePath);
//			for (File source : file.listFiles()) {
//				long fileSize = source.length();
//				//利用putNextEntry来把文件写入
//				zipOutputStream.putNextEntry(new ZipEntry(source.getName()));
//				long read = Integer.MAX_VALUE;
//				int count = (int) Math.ceil((double) fileSize / read);
//				long pre = 0;
//				//由于一次映射的文件大小不能超过2GB，所以分次映射
//				for (int i = 0; i < count; i++) {
//					if (fileSize - pre < Integer.MAX_VALUE) {
//						read = fileSize - pre;
//					}
//					mappedByteBuffer = new RandomAccessFile(source, "r").getChannel()
//							.map(FileChannel.MapMode.READ_ONLY, pre, read);
//					writableByteChannel.write(mappedByteBuffer);
//					pre += read;
//				}
//			}
//			mappedByteBuffer.clear();
//
//		} catch (Exception e) {
//			log.error("Zip more file error， fileNames: " + sourcePath, e);
//		} finally {
//			try {
//				if (null != zipOutputStream) {
//					zipOutputStream.close();
//				}
//				if (null != writableByteChannel) {
//					writableByteChannel.close();
//				}
//				if (null != mappedByteBuffer) {
//					mappedByteBuffer.clear();
//				}
//			} catch (Exception e) {
//				log.error("Zip more file error, file names are:" + sourcePath, e);
//			}
//		}
//		return outputZipFilename;
//	}

}
