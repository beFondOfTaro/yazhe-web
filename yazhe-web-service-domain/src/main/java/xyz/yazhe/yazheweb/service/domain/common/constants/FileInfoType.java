package xyz.yazhe.yazheweb.service.domain.common.constants;

import lombok.Getter;
import xyz.yazhe.yazheweb.service.domain.exception.VerificationException;

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
	 * 根据后缀名获取文件类型
	 * @param fileName
	 * @return
	 */
	public static FileInfoType getInstanceBySuffix(String fileName) throws VerificationException {
		int suffixIndex = fileName.lastIndexOf(".");
		for (FileInfoType type : FileInfoType.values()){
			for (String allowSuffix : type.getAllowSuffixList()){
				if (suffixIndex == -1 && "".equals(allowSuffix)){
					return type;
				}
				if (allowSuffix.equals(fileName.substring(suffixIndex + 1))){
					return type;
				}
			}
		}
		throw new VerificationException("不支持该类型文件");
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
		return getAllowSuffixList().stream()
				.anyMatch(allowSuffix -> allowSuffix.equals(fileName.substring(suffixIndex + 1)));
	}
}
