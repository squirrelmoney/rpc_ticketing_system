package com.lnsf.rpc.remoting.transport.netty.client;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.lnsf.rpc.entity.RpcLog;
import com.lnsf.rpc.enums.CompressTypeEnum;
import com.lnsf.rpc.enums.SerializationTypeEnum;
import com.lnsf.rpc.factory.SingletonFactory;
import com.lnsf.rpc.remoting.constants.RpcConstants;
import com.lnsf.rpc.remoting.dto.RpcMessage;
import com.lnsf.rpc.remoting.dto.RpcRequest;
import com.lnsf.rpc.remoting.dto.RpcResponse;
import com.lnsf.rpc.service.RpcLogService;
import io.netty.channel.*;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.InetSocketAddress;
import java.util.Date;
import java.util.HashMap;


/**
 * @description: 定制客户端ChannelHandler以处理服务器发送的数据
 * @author: money
 *
 * 如果继承自 SimpleChannelInboundHandler 的话就不要考虑 ByteBuf 的释放 ，{@link SimpleChannelInboundHandler} 内部的
 * channelRead 方法会替你释放 ByteBuf ，避免可能导致的内存泄露问题。详见《Netty进阶之路 跟着案例学 Netty》
 *
 * @time: 2021/4/5 19:08
 */
@Slf4j
@Component
public class NettyRpcClientHandler extends ChannelInboundHandlerAdapter {
    private final UnprocessedRequests unprocessedRequests;
    private final NettyRpcClient nettyRpcClient;

    @Autowired
    private RpcLogService logService;

    private static  RpcLogService rpcLogService;  //静态对象

    @PostConstruct
    public void init(){
        rpcLogService = this.logService;  //将注入的对象交给静态对象管理
    }
    /**
     * 创建唯一实例，避免频繁创建销毁
     */
    public NettyRpcClientHandler() {
        this.unprocessedRequests = SingletonFactory.getInstance(UnprocessedRequests.class);
        this.nettyRpcClient = SingletonFactory.getInstance(NettyRpcClient.class);
    }

    /**
     * 读取服务器传输的消息
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {
            log.info("客户端收到信息: [{}]", msg);
            RpcMessage rpcMessage = (RpcMessage) msg;
            RpcResponse data = new RpcResponse<>();
            try{
                 data = (RpcResponse) rpcMessage.getData();
                //TODO:log
                if ( data.getRequestId() != null){
                    String requestId = data.getRequestId();
                    RpcLog rpcLog = rpcLogService.queryById(requestId);
                    rpcLog.setResponMsg(data.toString());
                    rpcLog.setCostTime(System.currentTimeMillis()-rpcLog.getCreateTime());
                    rpcLogService.update(rpcLog);
                }
            }catch (Exception e){
                log.info("心跳信息: [{}]", msg);
            }



            if (msg instanceof RpcMessage) {
                RpcMessage tmp = (RpcMessage) msg;
                byte messageType = tmp.getMessageType();
                if (messageType == RpcConstants.HEARTBEAT_RESPONSE_TYPE) {
                    log.info("心跳 [{}]", tmp.getData());
                } else if (messageType == RpcConstants.RESPONSE_TYPE) {
                    RpcResponse<Object> rpcResponse = (RpcResponse<Object>) tmp.getData();
                    unprocessedRequests.complete(rpcResponse);
                }
            }
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleState state = ((IdleStateEvent) evt).state();
            if (state == IdleState.WRITER_IDLE) {
                log.info("写入空闲 [{}]", ctx.channel().remoteAddress());
                Channel channel = nettyRpcClient.getChannel((InetSocketAddress) ctx.channel().remoteAddress());
                RpcMessage rpcMessage = new RpcMessage();
                rpcMessage.setCodec(SerializationTypeEnum.PROTOSTUFF.getCode());
                rpcMessage.setCompress(CompressTypeEnum.GZIP.getCode());
                rpcMessage.setMessageType(RpcConstants.HEARTBEAT_REQUEST_TYPE);
                rpcMessage.setData(RpcConstants.PING);
                channel.writeAndFlush(rpcMessage).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    /**
     * 在处理客户端消息时发生异常时调用
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error("客户端捕获异常：", cause);
        cause.printStackTrace();
        ctx.close();
    }

}

