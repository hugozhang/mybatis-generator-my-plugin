package org.mybatis.generator.my.mapper;

import java.util.List;

public interface Mapper<En, Ex> {

    int deleteByPrimaryKey(Integer primaryKey);

    long countByExample(Ex example);
    
    int insert(En record);

    int insertSelective(En record);
    
    int insertBatch(List<En> list);
    
    int updateByPrimaryKey(En record);
    
    int updateByPrimaryKeySelective(En record);

    int updateBatchByPrimaryKeySelective(List<En> list);

    En selectByPrimaryKey(Integer primaryKey);
    
    List<En> selectByExample(Ex example);

}
