package com.lnsf.rpc.spring;

import com.lnsf.rpc.annotation.RpcReference;
import com.lnsf.rpc.entity.RpcServiceProperties;
import com.lnsf.rpc.enums.RpcConfigEnum;
import com.lnsf.rpc.extension.ExtensionLoader;
import com.lnsf.rpc.factory.SingletonFactory;
import com.lnsf.rpc.provider.ServiceProvider;
import com.lnsf.rpc.provider.ServiceProviderImpl;
import com.lnsf.rpc.proxy.RpcClientProxy;
import com.lnsf.rpc.remoting.transport.RpcRequestTransport;
import com.lnsf.rpc.utils.PropertiesFileUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Field;


@Configuration
@ConditionalOnClass(RpcReference.class)
public class ConsumerAutoConfiguration {


    private final ServiceProvider serviceProvider;
    private final RpcRequestTransport rpcClient;

    public ConsumerAutoConfiguration() {
        this.serviceProvider = SingletonFactory.getInstance(ServiceProviderImpl.class);
        this.rpcClient = ExtensionLoader.getExtensionLoader(RpcRequestTransport.class).
                getExtension(PropertiesFileUtil.readPropertiesFile(RpcConfigEnum.RPC_CONFIG_PATH.getPropertyValue()).
                        getProperty(RpcConfigEnum.RPC_TRANSPORT.getPropertyValue()));
    }


    /**
     * 设置动态代理
     * @return
     */
    @Bean
    public BeanPostProcessor beanPostProcessor() {
        return new BeanPostProcessor() {
            @Override
            public Object postProcessBeforeInitialization(Object bean, String beanName)
                    throws BeansException {
                Class<?> targetClass = bean.getClass();
                Field[] declaredFields = targetClass.getDeclaredFields();
                for (Field declaredField : declaredFields) {
                    RpcReference rpcReference = declaredField.getAnnotation(RpcReference.class);
                    if (rpcReference != null) {
                        RpcServiceProperties rpcServiceProperties = RpcServiceProperties.builder()
                                .group(rpcReference.group()).version(rpcReference.version()).build();
                        RpcClientProxy rpcClientProxy = new RpcClientProxy(rpcClient, rpcServiceProperties);
                        Object clientProxy = rpcClientProxy.getProxy(declaredField.getType());
                        declaredField.setAccessible(true);
                        try {
                            declaredField.set(bean, clientProxy);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }

                }
                return bean;
            }
        };
    }

}
