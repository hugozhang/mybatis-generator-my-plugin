package org.mybatis.generator.my.service.v2;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.my.mapper.v2.Mapper;
import org.mybatis.generator.my.page.Page;
import org.mybatis.generator.my.where.Example;

/**
 * 
 * @ClassName: AbstractService
 * @Description: 通用服务接口
 * @author: Hugozxh
 * @date: 2019年8月8日 下午2:12:42
 *
 * @param <En> En = entity  实体类
 * @Copyright: 2019 www.jumapeisong.com Inc. All rights reserved.
 */
public abstract class AbstractMybatisService<En> implements MybatisService<En> {

    public abstract Mapper<En> getMapper();

    public Page<En> ofPage(Example example) {
        int total = (int)getMapper().countByExample(example);
        List<En> results = new ArrayList<>();
        if (total != 0 ) {
            results = getMapper().selectByExample(example);
        }
        return new Page<>(example.getPageNo(),example.getPageSize(),total,results);
    }

    public int deleteByPrimaryKey(Integer primaryKey) {
        return getMapper().deleteByPrimaryKey(primaryKey);
    }
    
    public long countByExample(Example example) {
        return getMapper().countByExample(example);
    }

    public int insert(En record) {
        return getMapper().insert(record);
    }

    public int insertSelective(En record) {
        return getMapper().insertSelective(record);
    }
    
    public int insertBatch(List<En> list) {
        return getMapper().insertBatch(list);
    }

    public List<En> selectByExample(Example example) {
        return getMapper().selectByExample(example);
    }

    public En selectByPrimaryKey(Integer primaryKey) {
        return getMapper().selectByPrimaryKey(primaryKey);
    }

    public int updateByPrimaryKey(En record) {
        return getMapper().updateByPrimaryKey(record);
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
