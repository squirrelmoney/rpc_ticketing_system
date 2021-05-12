package com.lnsf.rpc.remoting.dto;

import com.lnsf.rpc.entity.RpcServiceProperties;
import lombok.*;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @program: rpc-framwork
 * @description: RPC远程调用 请求体
 * @author: money
 * @create: 2021-02-18 17:54
 **/
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class RpcRequest implements Serializable {
    private static final long serialVersionUID = 1905122041950251207L;
    private String requestId;
    private String interfaceName;
    private String methodName;
    private Object[] parameters;
    private Class<?>[] paramTypes;
    private String version;
    private String group;

    public RpcServiceProperties toRpcProperties() {
        return RpcServiceProperties.builder().serviceName(this.getInterfaceName())
                .version(this.getVersion())
                .group(this.getGroup()).build();
    }

    @Override
    public String toString() {
        return "RpcRequest{" +
                "requestId='" + requestId + '\'' +
                ", interfaceName='" + interfaceName + '\'' +
                ", methodName='" + methodName + '\'' +
                ", paramTypes=" + Arrays.toString(paramTypes) +
                ", version='" + version + '\'' +
                ", group='" + group + '\'' +
                '}';
    }
}
