package com.lnsf.rpc.service.impl;

import com.lnsf.rpc.mapper.RpcLogMapper;
import com.lnsf.rpc.entity.RpcLog;
import com.lnsf.rpc.service.RpcLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.LongStream;

/**
 * (RpcLog)表服务实现类
 *
 * @author money
 * @since 2021-04-20 21:49:54
 */
@Service("rpcLogService")
public class RpcLogServiceImpl implements RpcLogService {
    @Resource
    private RpcLogMapper rpcLogDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public RpcLog queryById(String id) {
        return this.rpcLogDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<RpcLog> queryAllByLimit(int offset, int limit) {
        return this.rpcLogDao.queryAllByLimit(offset, limit);
    }

    @Override
    public List<RpcLog> queryAll(RpcLog rpcLog) {
        return this.rpcLogDao.queryAll(rpcLog);
    }

    /**
     * 新增数据
     *
     * @param rpcLog 实例对象
     * @return 实例对象
     */
    @Override
    public RpcLog insert(RpcLog rpcLog) {
        this.rpcLogDao.insert(rpcLog);
        return rpcLog;
    }

    /**
     * 修改数据
     *
     * @param rpcLog 实例对象
     * @return 实例对象
     */
    @Override
    public RpcLog update(RpcLog rpcLog) {
        this.rpcLogDao.update(rpcLog);
        return this.queryById(rpcLog.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String id) {
        return this.rpcLogDao.deleteById(id) > 0;
    }

    /**
     * 获取rpc效率
     * @return
     */
    @Override
    public Map<String, List> getrpctime() {
        Map<String,List> map = new HashMap<>();
        List<RpcLog> list = this.rpcLogDao.queryAll(null);
        double netty = 0;
        double socket = 0;
        Long nettySum = 0l;
        Long socketSum = 0l;
        Long nettyMax = 0l;
        Long socketMax = 0l;

        List average = new ArrayList<>();
        List sum = new ArrayList<>();
        List max = new ArrayList<>();
        List num = new ArrayList<>();
        list.stream().filter(rpcLog -> ("netty").equals(rpcLog.getCodec()))
                .mapToLong(RpcLog::getCostTime);
        try{
            long nettycount = list.stream().filter(rpcLog -> ("netty").equals(rpcLog.getCodec()))
                    .mapToLong(RpcLog::getCostTime).count();
            // netty平均数
            netty = list.stream().filter(rpcLog -> ("netty").equals(rpcLog.getCodec()))
                    .mapToLong(RpcLog::getCostTime).average().getAsDouble();
            nettySum = list.stream().filter(rpcLog -> ("netty").equals(rpcLog.getCodec()))
                    .mapToLong(RpcLog::getCostTime).sum();
            nettyMax = list.stream().filter(rpcLog -> ("netty").equals(rpcLog.getCodec()))
                    .mapToLong(RpcLog::getCostTime).max().getAsLong();
            average.add(netty);
            sum.add(nettySum);
            max.add(nettyMax);
            num.add(nettycount);
        }catch (Exception e)  {
            average.add(0);
            sum.add(0);
            max.add(0);
            num.add(0);
        }
        try{
            // netty平均数
            // socket平均数
            long socketcount = list.stream().filter(rpcLog -> ("socket").equals(rpcLog.getCodec()))
                    .mapToLong(RpcLog::getCostTime).count();
            socket = list.stream().filter(rpcLog -> ("socket").equals(rpcLog.getCodec()))
                    .mapToLong(RpcLog::getCostTime).average().getAsDouble();
            socketSum = list.stream().filter(rpcLog -> ("socket").equals(rpcLog.getCodec()))
                    .mapToLong(RpcLog::getCostTime).sum();
            socketMax =list.stream().filter(rpcLog -> ("socket").equals(rpcLog.getCodec()))
                    .mapToLong(RpcLog::getCostTime).max().getAsLong();
            average.add(socket);
            sum.add(socketSum);
            max.add(socketMax);
            num.add(socketcount);
        }catch (Exception e){
            average.add(0);
            sum.add(0);
            max.add(0);
            num.add(0);
        }






        map.put("avg",average);
        map.put("sum",sum);
        map.put("max",max);
        map.put("num",num);
        return map;
    }

//    @Override
//    public Map<String, List> getrpctime() {
//        Map<String,List> map = new HashMap<>();
//        List<RpcLog> list = this.rpcLogDao.queryAll(null);
//        double netty = 0;
//        double socket = 0;
//        Long nettySum = 0l;
//        Long socketSum = 0l;
//        Long nettyMax = 0l;
//        Long socketMax = 0l;
//
//        List nettyList = new ArrayList<>();
//        List socketList = new ArrayList<>();
//        long nettycount = list.stream().filter(rpcLog -> ("netty").equals(rpcLog.getCodec()))
//                .mapToLong(RpcLog::getCostTime).count();
//        long socketcount = list.stream().filter(rpcLog -> ("socket").equals(rpcLog.getCodec()))
//                .mapToLong(RpcLog::getCostTime).count();
//        if(nettycount > 0){
//            // netty平均数
//            netty = list.stream().filter(rpcLog -> ("netty").equals(rpcLog.getCodec()))
//                    .mapToLong(RpcLog::getCostTime).average().getAsDouble();
//            nettySum = list.stream().filter(rpcLog -> ("netty").equals(rpcLog.getCodec()))
//                    .mapToLong(RpcLog::getCostTime).sum();
//            nettyMax = list.stream().filter(rpcLog -> ("netty").equals(rpcLog.getCodec()))
//                    .mapToLong(RpcLog::getCostTime).max().getAsLong();
//            nettyList.add(netty);
//            nettyList.add(nettySum);
//            nettyList.add(nettyMax);
//        }else {
//            nettyList.add(0);
//            nettyList.add(0);
//            nettyList.add(0);
//        }
//        if(socketcount>0){
//            // netty平均数
//            // socket平均数
//            socket = list.stream().filter(rpcLog -> ("socket").equals(rpcLog.getCodec()))
//                    .mapToLong(RpcLog::getCostTime).average().getAsDouble();
//            socketSum = list.stream().filter(rpcLog -> ("socket").equals(rpcLog.getCodec()))
//                    .mapToLong(RpcLog::getCostTime).sum();
//            socketMax =list.stream().filter(rpcLog -> ("socket").equals(rpcLog.getCodec()))
//                    .mapToLong(RpcLog::getCostTime).max().getAsLong();
//            socketList.add(socket);
//            socketList.add(socketSum);
//            socketList.add(socketMax);
//        }else {
//            socketList.add(0);
//            socketList.add(0);
//            socketList.add(0);
//        }
//
//
//        nettyList.add(nettycount);
//        socketList.add(socketcount);
//
//        map.put("netty",nettyList);
//        map.put("socket",socketList);
//        return map;
//    }
}
