package xyz.yazhe.yazheweb.service.blog.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import xyz.yazhe.yazheweb.service.blog.repository.FileInfoRepository;
import xyz.yazhe.yazheweb.service.blog.service.FileService;
import xyz.yazhe.yazheweb.service.blog.bean.entity.FileInfo;
import xyz.yazhe.yazheweb.service.domain.base.FileInfoVo;
import xyz.yazhe.yazheweb.service.domain.common.constants.FileInfoType;
import xyz.yazhe.yazheweb.service.domain.exception.BusinessException;
import xyz.yazhe.yazheweb.service.domain.exception.VerificationException;
import xyz.yazhe.yazheweb.service.util.FileUtil;
import xyz.yazhe.yazheweb.service.util.KeyUtil;
import xyz.yazhe.yazheweb.service.util.web.RequestUtil;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Yazhe
 * Created at 18:36 2019/1/28
 */
@Slf4j
@Service
public class FileServiceImpl implements FileService {

	@Autowired
	private FileInfoRepository fileInfoRepository;
	@Value("${file-upload.file-path-prefix}")
	private String filePathPrefix;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public List<FileInfoVo> saveBatchFile(List<MultipartFile> multipartFileList) throws VerificationException {
		List<FileInfo> fileInfoList = new ArrayList<>();
		List<FileInfoVo> fileInfoVos = new ArrayList<>();
		String currentUserId = RequestUtil.getCurrentUserId();
		/*
		生成文件夹路径，格式为yyyyMM/dd/HH/mm/ss
		按照此方式生成文件夹路径是因为操作系统某一目录下文件数量超过1024时读取速度会大幅降低
		 */
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM/dd/HH/mm/ss");
		String fileDirPath = getFileAbsoluteUrl(simpleDateFormat.format(new Date()));
		//不存在则创建文件夹
		File fileDir = new File(fileDirPath);
		if (!fileDir.exists()){
			fileDir.mkdirs();
		}
		for (MultipartFile multipartFile : multipartFileList){
			FileInfo fileInfo = new FileInfo();
			fileInfo.setId(KeyUtil.genUniqueKey());
			String fileName = multipartFile.getOriginalFilename();
			fileInfo.setName(fileName);
			fileInfo.setSize(multipartFile.getSize());
			fileInfo.setType(FileInfoType.getInstanceBySuffix(fileName).getCode());
			fileInfo.setCreateUserId(currentUserId);
			fileInfo.setUpdateUserId(currentUserId);

			//上传路径，将文件id写入进去，可通过url获取文件id
			String fileUrl = fileDirPath + "/" + fileInfo.getId() + "." + FileUtil.getSuffix(fileName);
			fileInfo.setUrl(fileUrl);
			//如果文件已经存在，则直接失败
			FileInfoVo fileInfoVo = new FileInfoVo();
			fileInfoVo.setFileName(fileInfo.getName());
			File file = new File(fileUrl);
			if (file.exists()){
				fileInfoVo.setSuccessful(Boolean.FALSE);
				break;
			}
			//开始上传
			try {
				multipartFile.transferTo(new File(fileUrl));
				fileInfoList.add(fileInfo);
				// 添加返回信息
				fileInfoVo.setId(fileInfo.getId());
				fileInfoVo.setUrl(getRelativeFileUrl(fileUrl));
				fileInfoVo.setSuccessful(Boolean.TRUE);
				fileInfoVos.add(fileInfoVo);
			} catch (IOException e) {
				log.error("文件: " + fileInfo.getName() + ",上传失败", e);
			}
		}
		if (fileInfoList.isEmpty()){
			throw new BusinessException("文件上传失败");
		}
		if (fileInfoRepository.insertBatch(fileInfoList) != fileInfoList.size()){
			//todo 进行文件回滚操作，删除已上传的

			throw new BusinessException("文件上传失败");
		}
		return fileInfoVos;
	}

	@Override
	public void deleteBatchFile(List<Integer> fileIds) throws VerificationException {
		fileInfoRepository.queryInFileIds(fileIds).parallelStream().forEach(fileInfo -> {
			if (new File(fileInfo.getUrl()).delete()){
				log.info("删除文件：" + fileInfo.getUrl());
			}
		});
		fileInfoRepository.deleteBatchInFileIds(fileIds);
	}

	/**
	 * 获取文件相对路径
	 * @param absoluteFileUrl 绝对路径
	 * @return
	 */
	private String getRelativeFileUrl(String absoluteFileUrl){
		return absoluteFileUrl.substring(absoluteFileUrl.indexOf(filePathPrefix) + filePathPrefix.length());
	}

	/**
	 *  获取文件绝对路径
	 * @param relativeFileUrl 相对路径
	 * @return
	 */
	private String getFileAbsoluteUrl(String relativeFileUrl){
		return filePathPrefix + relativeFileUrl;
	}
}
