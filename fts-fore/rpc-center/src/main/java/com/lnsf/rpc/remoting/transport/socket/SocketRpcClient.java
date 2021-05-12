package com.lnsf.rpc.remoting.transport.socket;

import com.lnsf.rpc.entity.RpcLog;
import com.lnsf.rpc.entity.RpcServiceProperties;
import com.lnsf.rpc.enums.RpcConfigEnum;
import com.lnsf.rpc.exception.RpcException;
import com.lnsf.rpc.extension.ExtensionLoader;
import com.lnsf.rpc.registry.ServiceDiscovery;
import com.lnsf.rpc.remoting.dto.RpcRequest;
import com.lnsf.rpc.remoting.dto.RpcResponse;
import com.lnsf.rpc.remoting.transport.RpcRequestTransport;
import com.lnsf.rpc.service.RpcLogService;
import com.lnsf.rpc.utils.PropertiesFileUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @program: rpc-framwork
 * @description: 基于 Socket 传输 RpcRequest
 * @author: money
 * @create: 2021-03-25 11:33
 **/
@AllArgsConstructor
@Slf4j
@Component
public class SocketRpcClient implements RpcRequestTransport {
    private final ServiceDiscovery serviceDiscovery;

    @Autowired
    private RpcLogService logService;

    private static  RpcLogService rpcLogService;  //静态对象

    @PostConstruct
    public void init(){
        rpcLogService = this.logService;  //将注入的对象交给静态对象管理
    }

    public SocketRpcClient() {
        this.serviceDiscovery = ExtensionLoader.getExtensionLoader(ServiceDiscovery.class).
                getExtension(PropertiesFileUtil.readPropertiesFile(RpcConfigEnum.RPC_CONFIG_PATH.getPropertyValue()).
                        getProperty(RpcConfigEnum.RPC_REGISTAER_CENTER.getPropertyValue()));
    }

    @Override
    public Object sendRpcRequest(RpcRequest rpcRequest) throws Exception {
        String requestId = rpcRequest.getRequestId();
        RpcLog rpcLog =new RpcLog();
        rpcLog.setId(requestId);
        rpcLog.setCodec("socket");
        rpcLog.setReceiveMsg(rpcRequest.toString());
        rpcLog.setServiceName(rpcRequest.getInterfaceName()+":"+rpcRequest.getMethodName());
        rpcLog.setCreateTime(System.currentTimeMillis());

        // 通过rpcRequest构建rpc服务名称
        String rpcServiceName = RpcServiceProperties.builder().serviceName(rpcRequest.getInterfaceName())
                .group(rpcRequest.getGroup()).version(rpcRequest.getVersion()).build().toRpcServiceName();
        InetSocketAddress inetSocketAddress = serviceDiscovery.lookupService(rpcServiceName);
        try (Socket socket = new Socket()) {
            socket.connect(inetSocketAddress,1500);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            // 通过输出流将数据发送到服务器
            objectOutputStream.writeObject(rpcRequest);
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            Object object = objectInputStream.readObject();
            RpcResponse<Object> rpcResponse = (RpcResponse<Object>) object;
            rpcLog.setResponMsg(rpcResponse.toString());
            rpcLog.setCostTime(System.currentTimeMillis()-rpcLog.getCreateTime());
            rpcLogService.insert(rpcLog);
            //从输入流读取RpcResponse
            return object;
        } catch (IOException | ClassNotFoundException e) {
            throw new RpcException("调用服务失败:", e);
        }
    }
}
