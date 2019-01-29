package xyz.yazhe.yazheweb.service.blog.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import xyz.yazhe.yazheweb.service.blog.dao.FileInfoMapper;
import xyz.yazhe.yazheweb.service.blog.bean.entity.FileInfo;
import xyz.yazhe.yazheweb.service.domain.exception.VerificationException;

import java.util.List;

/**
 * @author Yazhe
 * Created at 10:51 2019/1/29
 */
@Repository
public class FileInfoRepositoryImpl implements FileInfoRepository {

	@Autowired
	private FileInfoMapper fileInfoMapper;

	@Override
	public int insertBatch(List<FileInfo> fileInfos) throws VerificationException {
		if (fileInfos == null || fileInfos.isEmpty()){
			throw new VerificationException("文件列表不能为空");
		}
		return fileInfoMapper.insertBatch(fileInfos);
	}

	@Override
	public List<FileInfo> queryInFileIds(List<Integer> fileIds) throws VerificationException {
		if (fileIds == null || fileIds.isEmpty()){
			throw new VerificationException("文件id列表不能为空");
		}
		return fileInfoMapper.queryInFileIds(fileIds);
	}

	@Override
	public int deleteBatchInFileIds(List<Integer> fileIds) throws VerificationException {
		if (fileIds == null || fileIds.isEmpty()){
			throw new VerificationException("文件id列表不能为空");
		}
		return fileInfoMapper.deleteBatchInFileIds(fileIds);
	}

	@Override
	public List<FileInfo> queryFileInUrls(List<String> urls) throws VerificationException {
		if (urls == null || urls.isEmpty()){
			throw new VerificationException("文件url列表不能为空");
		}
		return fileInfoMapper.queryFileInUrls(urls);
	}
}
