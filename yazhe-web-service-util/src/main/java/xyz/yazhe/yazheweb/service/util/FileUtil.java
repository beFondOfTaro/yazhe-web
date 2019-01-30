package xyz.yazhe.yazheweb.service.util;

/**
 * @author Yazhe
 * Created at 20:47 2019/1/30
 */
public class FileUtil {

	/**
	 * 获取文件后缀名
	 * @param fileName
	 * @return
	 */
	public static String getSuffix(String fileName){
		int suffixIndex = fileName.lastIndexOf(".");
		if (suffixIndex == -1){
			return "";
		}
		return fileName.substring(suffixIndex + 1);
	}
}
