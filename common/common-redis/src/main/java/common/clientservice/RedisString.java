package common.clientservice;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author: zhaihx
 * @description:
 * @date:2019/5/22
 */
@Service
public class RedisString extends RedisBase {

    final private Long LOCK_EXPIRE = 15L;

    final private String LOCK_PREFIX = "REDIS_LOCK";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 获取缓存
     * @param key 键
     * @return 值
     */
    public Object get(String key){
        return StringUtils.isEmpty(key)?null:redisTemplate.opsForValue().get(key);
    }
    /**
     * 添加缓存
     * @param key 键
     * @param value 值
     * @return true成功 false失败
     */
    public boolean set(String key,String value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 添加缓存并设置过期时间
     * @param key 键
     * @param value 值
     * @param time 时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    public boolean set(String key,String value,long time){
        try {
            if(time>0)
            {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            }
            else {
                redisTemplate.opsForValue().set(key, value);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 递增
     * @param key 键
     * @return
     */
    public long incr(String key, long delta){
        if(delta<0){
            throw new RuntimeException("递增因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 递减
     * @param key 键
     * @return
     */
    public long decr(String key, long delta){
        if(delta>=0){
            throw new RuntimeException("递增因子必须小于0");
        }
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 获取increment值
     * @param key 键
     * @return
     */
    public long getIncrementByKey(String key) {
        if(StringUtils.isEmpty(key))
        {
            return 0L;
        }
        return NumberUtils.toLong(redisTemplate.boundValueOps(key).get(0,-1));
    }
}
