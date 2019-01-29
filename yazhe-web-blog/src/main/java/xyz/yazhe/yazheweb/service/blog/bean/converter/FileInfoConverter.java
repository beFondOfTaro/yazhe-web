package xyz.yazhe.yazheweb.service.blog.bean.converter;

import xyz.yazhe.yazheweb.service.blog.bean.entity.FileInfo;
import xyz.yazhe.yazheweb.service.domain.base.FileInfoVo;

/**
 * @author Yazhe
 * Created at 15:20 2019/1/29
 */
public class FileInfoConverter {

	public static FileInfoVo convertDo2Vo(FileInfo fileInfo){
		FileInfoVo fileInfoVo = new FileInfoVo();
		fileInfoVo.setId(fileInfo.getId());
		fileInfoVo.setUrl(fileInfo.getUrl());
		fileInfoVo.setFileName(fileInfo.getName());
		return fileInfoVo;
	}
}
