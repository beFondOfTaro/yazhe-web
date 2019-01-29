package xyz.yazhe.yazheweb.service.domain.common.constants;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Yazhe
 * Created at 10:16 2019/1/29
 */
@Getter
public enum FileInfoType {

	IMAGE(0, "图片", new String[]{"jpg","jpeg"})
	;
	private Integer code;

	private String value;

	/**
	 * 允许的扩展名
	 */
	private List<String> allowSuffixList;

	FileInfoType(Integer code, String value, String[] allowSuffixList) {
		this.code = code;
		this.value = value;
		this.allowSuffixList = new ArrayList<>(Arrays.asList(allowSuffixList));
	}

	public static FileInfoType getInstanceByCode(Integer code){
		if (code == null){
			return null;
		}
		for (FileInfoType fileInfoType : FileInfoType.values()){
			if (fileInfoType.getCode().equals(code)){
				return fileInfoType;
			}
		}
		return null;
	}

	/**
	 * 校验文件后缀名
	 * @param fileName
	 * @return
	 */
	public boolean verifySuffix(String fileName){
		int suffixIndex = fileName.lastIndexOf(".");
		if (suffixIndex == -1){
			return false;
		}
		return FileInfoType.IMAGE.getAllowSuffixList().parallelStream()
				.anyMatch(allowSuffix -> allowSuffix.equals(fileName.substring(suffixIndex)));
	}
}
