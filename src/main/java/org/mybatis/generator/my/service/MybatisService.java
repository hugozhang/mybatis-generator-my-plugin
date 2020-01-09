package org.mybatis.generator.my.service;

import java.util.List;

import org.mybatis.generator.my.page.Page;

public interface MybatisService <En, Ex> {

    Page<En> ofPage(int pageNo,int pageSize,Ex example);

    int deleteByPrimaryKey(Integer primaryKey);

    long countByExample(Ex example);

    int insert(En record);

    int insertSelective(En record);

    int insertBatch(List<En> list);

    int insertBatchSelective(List<En> list);

    List<En> selectByExample(Ex example);

    En selectByPrimaryKey(Integer primaryKey);

    int updateByPrimaryKey(En record);

    int updateByPrimaryKeySelective(En record);

    int updateBatchByPrimaryKey(List<En> list);

    int updateBatchByPrimaryKeySelective(List<En> list);

}