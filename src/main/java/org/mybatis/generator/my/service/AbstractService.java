package org.mybatis.generator.my.service;

import java.util.List;

import org.mybatis.generator.my.mapper.Mapper;

/**
 * 
 * @ClassName: AbstractService
 * @Description: 通用服务接口
 * @author: Administrator
 * @date: 2019年4月28日 下午2:12:42
 *
 * @param <En> En = entity
 * @param <Ex> Ex = example
 * @Copyright: 2019 www.jumapeisong.com Inc. All rights reserved.
 */
public abstract class AbstractService<En, Ex> {

    abstract Mapper<En, Ex> getMapper();

    long countByExample(Ex example) {
        return getMapper().countByExample(example);
    }

    int insert(En record) {
        return getMapper().insert(record);
    }

    int insertSelective(En record) {
        return getMapper().insertSelective(record);
    }

    int insertBatch(List<En> list) {
        return getMapper().insertBatch(list);
    }

    List<En> selectByExample(Ex example) {
        return getMapper().selectByExample(example);
    }

    En selectByPrimaryKey(Integer primaryKey) {
        return getMapper().selectByPrimaryKey(primaryKey);
    }

    int updateByPrimaryKeySelective(En record) {
        return getMapper().updateByPrimaryKeySelective(record);
    }

    int updateBatchByPrimaryKey(List<En> list) {
        return getMapper().updateBatchByPrimaryKey(list);
    }

    int updateBatchByPrimaryKeySelective(List<En> list) {
        return getMapper().updateBatchByPrimaryKeySelective(list);
    }
}
