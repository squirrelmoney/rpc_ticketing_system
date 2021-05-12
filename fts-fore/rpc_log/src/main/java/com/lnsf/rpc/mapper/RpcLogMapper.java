package com.lnsf.rpc.mapper;

import com.lnsf.rpc.entity.RpcLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * (RpcLog)表数据库访问层
 *
 * @since 2021-04-20 21:49:51
 */
public interface RpcLogMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    RpcLog queryById(String id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<RpcLog> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param rpcLog 实例对象
     * @return 对象列表
     */
    List<RpcLog> queryAll(RpcLog rpcLog);

    /**
     * 新增数据
     *
     * @param rpcLog 实例对象
     * @return 影响行数
     */
    int insert(RpcLog rpcLog);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<RpcLog> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<RpcLog> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<RpcLog> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<RpcLog> entities);

    /**
     * 修改数据
     *
     * @param rpcLog 实例对象
     * @return 影响行数
     */
    int update(RpcLog rpcLog);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(String id);


}

