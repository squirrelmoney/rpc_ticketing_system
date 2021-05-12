package com.lnsf.rpc.service;

import com.lnsf.rpc.entity.RpcLog;

import java.util.List;
import java.util.Map;

/**
 * (RpcLog)表服务接口
 *
 * @author makejava
 * @since 2021-04-20 21:49:53
 */
public interface RpcLogService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    RpcLog queryById(String id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<RpcLog> queryAllByLimit(int offset, int limit);

    /**
     * 查询多条数据
     *
     * @return 对象列表
     */
    List<RpcLog> queryAll(RpcLog rpcLog);
    /**
     * 新增数据
     *
     * @param rpcLog 实例对象
     * @return 实例对象
     */
    RpcLog insert(RpcLog rpcLog);

    /**
     * 修改数据
     *
     * @param rpcLog 实例对象
     * @return 实例对象
     */
    RpcLog update(RpcLog rpcLog);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(String id);

    Map<String, List> getrpctime();
}
