package xyz.yazhe.yazheweb.service.blog.dao;

import xyz.yazhe.yazheweb.service.blog.bean.entity.FileInfo;

import java.util.List;

public interface FileInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FileInfo record);

    int insertSelective(FileInfo record);

    FileInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FileInfo record);

    int updateByPrimaryKey(FileInfo record);

    int insertBatch(List<FileInfo> fileInfos);

    List<FileInfo> queryInFileIds(List<Integer> fileIds);

    int deleteBatchInFileIds(List<Integer> fileIds);

    List<FileInfo> queryFileInUrls(List<String> urls);
}