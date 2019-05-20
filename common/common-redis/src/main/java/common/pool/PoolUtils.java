package common.pool;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Properties;

public class PoolUtils {

    private static JedisPool jedisPool;
     private static Properties properties;
     private static String proPath = "redisConfig.properties";
     private static boolean authFlag;
     private static String password;

     public void instance()
     {
         // 配置连接池
         JedisPoolConfig poolConfig = new JedisPoolConfig();
         poolConfig.setMaxIdle(20);
         poolConfig.setMinIdle(10);
         poolConfig.setMaxTotal(30);
         poolConfig.setMaxWaitMillis(3000);
         poolConfig.setTestOnBorrow(true);
         poolConfig.setTestOnReturn(true);
         jedisPool = new JedisPool("192.168.192.128", 6379);
         Jedis jedis = jedisPool.getResource();
         System.out.println(jedisPool.getResource().get("test"));
     }
}
