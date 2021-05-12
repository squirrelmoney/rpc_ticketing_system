package com.lnsf.rpc.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * (RpcLog)实体类
 *
 * @author makejava
 * @since 2021-04-20 21:49:48
 */
@Data
public class RpcLog implements Serializable {
    private static final long serialVersionUID = -44167736934258653L;
    /**
     * 主键
     */
    private String id;
    /**
     * 接口名
     */
    private String serviceName;
    /**
     * 通信方式
     */
    private String codec;
    /**
     * 接收信息
     */
    private String receiveMsg;
    /**
     * 返回信息
     */
    private String responMsg;
    /**
     * 花费时间
     */
    private Long costTime;
    /**
     * 创建时间
     */
    private Long createTime;



}
