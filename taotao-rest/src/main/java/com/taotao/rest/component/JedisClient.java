package com.taotao.rest.component;

public interface JedisClient {
    String get(String var1);

    String set(String var1, String var2);

    long incr(String var1);

    Long hset(String var1, String var2, String var3);

    String hget(String var1, String var2);

    Long del(String var1);

    Long hdel(String var1, String var2);

    Long expire(String var1, int var2);

}
