package org.mybatis.generator.my.service.v2;

import org.mybatis.generator.my.page.Page;
import org.mybatis.generator.my.where.Example;

import java.util.List;

public interface MybatisService <En> {

    Page<En> ofPage(Example example);

    int deleteByPrimaryKey(Integer primaryKey);
    
    long countByExample(Example example);
    
    int insert(En record);
    
    int insertSelective(En record);
    
    int insertBatch(List<En> list);
    
    int insertBatchSelective(List<En> list);
    
    List<En> selectByExample(Example example);
    
    En selectByPrimaryKey(Integer primaryKey);

    int updateByPrimaryKey(En record);

    int updateByPrimaryKeySelective(En record);
    
    int updateBatchByPrimaryKey(List<En> list);
    
    int updateBatchByPrimaryKeySelective(List<En> list);
    
}
