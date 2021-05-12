package com.lnsf.rpc.serialize.original;

import com.lnsf.rpc.serialize.Serializer;

/**
 * @description:
 * @author: money
 * @time: 2021/4/21 1:29
 */
public class originalSerializer implements Serializer {

    @Override
    public byte[] serialize(Object obj) {
        return new byte[0];
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) {
        return null;
    }
}
