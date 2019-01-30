package xyz.yazhe.yazheweb.service.domain.base;

import lombok.Data;

/**
 * 文件上传返回信息
 * @author Yazhe
 * Created at 18:20 2019/1/28
 */
@Data
public class FileInfoVo {

	private String id;

	/**
	 * 是否上传成功
	 */
	private Boolean successful;

	/**
	 * 文件名
	 */
	private String fileName;

	private String url;
}
