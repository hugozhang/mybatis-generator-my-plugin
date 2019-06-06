package org.mybatis.generator.my.service;

import java.util.List;

import org.mybatis.generator.my.page.Page;
import org.mybatis.generator.my.page.PageFunction;

public interface MybatisService <En, Ex> {

    <Out> Page<Out> pageOf(int pageSize,int pageNo,PageFunction<Out> func);
    
    int deleteByExample(Ex example);
    
    long countByExample(Ex example);
    
    int insert(En record);
    
    int insertSelective(En record);
    
    int insertBatch(List<En> list);
    
    int insertBatchSelective(List<En> list);
    
    List<En> selectByExample(Ex example);
    
    En selectByPrimaryKey(Integer primaryKey);
    
    int updateByPrimaryKeySelective(En record);
    
    int updateBatchByPrimaryKey(List<En> list);
    
    int updateBatchByPrimaryKeySelective(List<En> list);
    
}
