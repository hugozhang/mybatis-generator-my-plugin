package org.mybatis.generator.my.mapper.v2;

import org.mybatis.generator.my.where.Example;

import java.util.List;

/**
 * 
 * @ClassName:   Mapper   
 * @Description: 通用Mapper接口
 * @author:      Administrator
 * @date:        2019年4月28日 下午2:44:55  
 *
 * @param <En> En = entity  实体类
 * @Copyright:   2019 www.jumapeisong.com Inc. All rights reserved.
 */
public interface Mapper<En> {

    int deleteByPrimaryKey(Integer primaryKey);

    long countByExample(Example example);
    
    int insert(En record);

    int insertSelective(En record);
    
    int insertBatch(List<En> list);
    
    int insertBatchSelective(List<En> list);
    
    int updateByPrimaryKey(Integer primaryKey);
    
    int updateByPrimaryKeySelective(En record);

    int updateBatchByPrimaryKeySelective(List<En> list);
    
    int updateBatchByPrimaryKey(List<En> list);

    En selectByPrimaryKey(Integer primaryKey);
    
    List<En> selectByExample(Example example);

}
