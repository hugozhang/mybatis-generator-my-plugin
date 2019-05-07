package org.mybatis.generator.my.mapper;

import java.util.List;

/**
 * 
 * @ClassName:   Mapper   
 * @Description: 通用Mapper接口
 * @author:      Administrator
 * @date:        2019年4月28日 下午2:44:55  
 *
 * @param <En> En = entity  实体类
 * @param <Ex> Ex = example   实体类对应的Example
 * @Copyright:   2019 www.jumapeisong.com Inc. All rights reserved.
 */
public interface Mapper<En, Ex> {

    int deleteByPrimaryKey(Integer primaryKey);

    long countByExample(Ex example);
    
    int insert(En record);

    int insertSelective(En record);
    
    int insertBatch(List<En> list);
    
    int updateByPrimaryKey(En record);
    
    int updateByPrimaryKeySelective(En record);

    int updateBatchByPrimaryKeySelective(List<En> list);
    
    int updateBatchByPrimaryKey(List<En> list);

    En selectByPrimaryKey(Integer primaryKey);
    
    List<En> selectByExample(Ex example);

}
