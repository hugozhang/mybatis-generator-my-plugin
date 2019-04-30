package org.mybatis.generator.my.service;

import java.util.List;

import org.mybatis.generator.my.mapper.Mapper;
import org.mybatis.generator.my.page.Page;
import org.mybatis.generator.my.page.PageRequestWrap;
import org.mybatis.generator.my.page.PageFunction;

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

    /**
     * 
     * @Title: pageOf   
     * @Description: TODO(这里用一句话描述这个方法的作用)   
     * @param: @param query    In:传过来的参数
     * @param: @param func      Out:返回的对象
     * @param: @return      
     * @return: Page<E>      
     * @throws
     */
    <In,Out> Page<Out> pageOf(PageRequestWrap<In> query,PageFunction<Out> func) {
        Page<Out> p = new Page<Out>();
        p.setPageNo(query.getPageNo());
        p.setPageSize(query.getPageSize());
        int total = func.ofTotal(query);
        p.setTotal(total);
        if (total != 0) {
            p.setResults(func.ofResults(query));
        }
        return p;
    }

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
