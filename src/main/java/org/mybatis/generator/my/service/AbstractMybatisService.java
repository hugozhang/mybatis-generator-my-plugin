package org.mybatis.generator.my.service;

import java.util.List;

import org.mybatis.generator.my.mapper.Mapper;
import org.mybatis.generator.my.page.Page;
import org.mybatis.generator.my.page.PageFunction;

/**
 * 
 * @ClassName: AbstractService
 * @Description: 通用服务接口
 * @author: Hugozxh
 * @date: 2019年4月28日 下午2:12:42
 *
 * @param <En> En = entity  实体类
 * @param <Ex> Ex = example   实体类对应的Example
 * @Copyright: 2019 www.jumapeisong.com Inc. All rights reserved.
 */
public abstract class AbstractMybatisService<En, Ex> implements MybatisService<En, Ex> {

    public abstract Mapper<En, Ex> getMapper();

    /**
     * 
     * @Title: pageOf   
     * @Description: 分页
     * @param: @param query    In:传过来的参数
     * @param: @param func      Out:返回的对象
     * @param: @return      
     * @return: Page<E>      
     * @throws
     */
    public <Out> Page<Out> pageOf(int pageSize,int pageNo,PageFunction<Out> func) {
        Page<Out> p = new Page<Out>();
        if(func == null) return p;
        p.setPageNo(pageNo);
        p.setPageSize(pageSize);
        int total = func.ofTotal();
        p.setTotal(total);
        if (total != 0) {
            p.setResults(func.ofResults());
        }
        return p;
    }

    public int deleteByPrimaryKey(Integer primaryKey) {
        return getMapper().deleteByPrimaryKey(primaryKey);
    }
    
    public long countByExample(Ex example) {
        return getMapper().countByExample(example);
    }

    public int insert(En record) {
        return getMapper().insert(record);
    }

    public int insertSelective(En record) {
        return getMapper().insertSelective(record);
    }
    
    public int insertBatchSelective(List<En> list) {
        return getMapper().insertBatchSelective(list);
    }

    public int insertBatch(List<En> list) {
        return getMapper().insertBatch(list);
    }

    public List<En> selectByExample(Ex example) {
        return getMapper().selectByExample(example);
    }

    public En selectByPrimaryKey(Integer primaryKey) {
        return getMapper().selectByPrimaryKey(primaryKey);
    }

    public int updateByPrimaryKeySelective(En record) {
        return getMapper().updateByPrimaryKeySelective(record);
    }

    public int updateBatchByPrimaryKey(List<En> list) {
        return getMapper().updateBatchByPrimaryKey(list);
    }

    public int updateBatchByPrimaryKeySelective(List<En> list) {
        return getMapper().updateBatchByPrimaryKeySelective(list);
    }
}
