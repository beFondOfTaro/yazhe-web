package xyz.yazhe.yazheweb.service.blog.repository;

import xyz.yazhe.yazheweb.service.blog.bean.entity.FileInfo;
import xyz.yazhe.yazheweb.service.domain.exception.VerificationException;

import java.util.List;

/**
 * @author Yazhe
 * Created at 10:51 2019/1/29
 */
public interface FileInfoRepository {

	/**
	 * 批量写入文件信息
	 * @param fileInfos
	 * @return
	 * @throws VerificationException
	 */
	int insertBatch(List<FileInfo> fileInfos) throws VerificationException;

	/**
	 * 根据id批量查询文件信息
	 * @param fileIds
	 * @return
	 * @throws VerificationException
	 */
	List<FileInfo> queryInFileIds(List<Integer> fileIds) throws VerificationException;

	/**
	 * 根据id批量删除文件信息
	 * @param fileIds
	 * @return
	 * @throws VerificationException
	 */
	int deleteBatchInFileIds(List<Integer> fileIds) throws VerificationException;

	/**
	 * 根据url查询文件信息
	 * @param urls 绝对路径
	 * @return
	 */
	List<FileInfo> queryFileInUrls(List<String> urls) throws VerificationException;
}
