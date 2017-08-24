package com.taotao.redis;

import com.taotao.rest.component.JedisClient;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.*;

import java.util.HashSet;
import java.util.Set;

public class RedisTest {

    @Test
    public void testJedisSingle() throws Exception {
        //创建一个Jedis对象
        Jedis jedis = new Jedis("192.168.25.153", 6379);
        jedis.set("test", "hello jedis");
        String string = jedis.get("test");
        System.out.println(string);
        jedis.close();
    }


    @Test
    public void testJedisPool() {

        JedisPoolConfig config = new JedisPoolConfig();
        //设置最大连接数
        config.setMaxTotal(50);
        //定义连接池
        JedisPool pool = new JedisPool(config, "192.168.56.2", 6379, 2000, "123456");
        Jedis jedis = null;
        try {
            //从连接池获得Jedis实例
            jedis = pool.getResource();
            //执行操作
            String stringKey = "scoreboard";
            System.out.println(jedis.zrange(stringKey, 0, -1));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //把Jedis对象放回连接池中
            if (null != pool && null != jedis) {
                pool.returnResourceObject(jedis);
            }
        }
    }

    //连接redis集群
    @Test
    public void testJedisCluster() throws Exception {
        //创建一个JedisCluster对象
        Set<HostAndPort> nodes = new HashSet<>();
        nodes.add(new HostAndPort("192.168.25.153", 7001));
        nodes.add(new HostAndPort("192.168.25.153", 7002));
        nodes.add(new HostAndPort("192.168.25.153", 7003));
        nodes.add(new HostAndPort("192.168.25.153", 7004));
        nodes.add(new HostAndPort("192.168.25.153", 7005));
        nodes.add(new HostAndPort("192.168.25.153", 7006));
        //在nodes中指定每个节点的地址
        //jedisCluster在系统中是单例的。
        JedisCluster jedisCluster = new JedisCluster(nodes);
        jedisCluster.set("name", "zhangsan");
        jedisCluster.set("value", "100");
        String name = jedisCluster.get("name");
        String value = jedisCluster.get("value");
        System.out.println(name);
        System.out.println(value);


        //系统关闭时关闭jedisCluster
        jedisCluster.close();
    }

    @Test
    public void testJedisClientSpring() throws Exception{
        //创建Spring容器
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
        //从容器获得jedisClient对象
        JedisClient jedisClient= applicationContext.getBean(JedisClient.class);
        //使用
        jedisClient.set("wirt1","101");
        String wirt = jedisClient.get("wirt");
        System.out.println(wirt);
    }

}
