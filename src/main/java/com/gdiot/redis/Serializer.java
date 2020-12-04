package com.gdiot.redis;

/**
 * @author tanshuai
 */
public interface Serializer {

    /**
     * 序列化
     *
     * @param t
     * @return
     */
    byte[] serialize(Object t);

    /**
     * 反序列化
     *
     * @param bytes
     * @param <T>
     * @return
     */
    <T> T deserialize(byte[] bytes);
}
