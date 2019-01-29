package xyz.yazhe.yazheweb.service.blog.service;

import org.springframework.web.multipart.MultipartFile;
import xyz.yazhe.yazheweb.service.domain.base.FileInfoVo;
import xyz.yazhe.yazheweb.service.domain.exception.VerificationException;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 文件服务（仅内部调用）
 * @author Yazhe
 * Created at 18:36 2019/1/28
 */
public interface FileService {

	/**
	 * 批量保存文件
	 * @param multipartFileList
	 * @return
	 * @throws VerificationException
	 */
	List<FileInfoVo> saveBatchFile(List<MultipartFile> multipartFileList) throws VerificationException;

	/**
	 * 批量删除文件
	 * @param fileIds
	 * @throws VerificationException
	 */
	void deleteBatchFile(List<Integer> fileIds) throws VerificationException;

	/**
	 * 下载文件
	 * @param response
	 * @param relativeFileUrl
	 */
	void downloadFile(HttpServletResponse response, String relativeFileUrl);
}
