package common.clientservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * @author: zhaihx
 * @description:
 * @date:2019/5/23
 */
@Service
public class RedisZSet extends RedisBase {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 向zset中添加元素
     * @param key 键
     * @param value 值
     * @param score 排序值
     * @return
     */
    public boolean szAdd(String key,String value,Double score){
        try {
            return redisTemplate.opsForZSet().add(key,value,score);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 向zset中添加元素
     * @param key 键
     * @param set 值
     * @return java.lang.Long
     */
    public Long szAdd(String key, Set<ZSetOperations.TypedTuple<Object>> set){
        try {
            return redisTemplate.opsForZSet().add(key,set);
        } catch (Exception e) {
            return 0L;
        }
    }

    /**
     *获取指定区域的信息
     * @param key 键
     * @param start 开始
     * @param end 结束
     * @return java.util.Set<java.lang.Object>
     */
    public Set<Object> szRang(String key,Long start,Long end){
        try {
            return redisTemplate.opsForZSet().range(key,start,end);
        } catch (Exception e) {
            return new HashSet<>();
        }
    }

    /**
     * 根据score获取指定区域的信息
     * @param key 键
     * @param start 开始
     * @param end 结束
     * @return java.util.Set<java.lang.Object>
     */
    public Set<Object> szRangByScore(String key,double start,double end){
        try {
            return redisTemplate.opsForZSet().rangeByScore(key,start,end);
        } catch (Exception e) {
            return new HashSet<>();
        }
    }

    /**
     *
     * @param key 键
     * @param start 开始
     * @param end 结束
     * @param offset 启始下标
     * @param limit 个数
     * @return java.util.Set<java.lang.Object>
     */
    public Set<Object> szRangByScore(String key,double start,double end,long offset,long limit){
        try {
            return redisTemplate.opsForZSet().rangeByScore(key,start,end,offset,limit);
        } catch (Exception e) {
            return new HashSet<>();
        }
    }

    /**
     * 用于获取满足非score的排序取值
     * @param key 键
     * @param range 区域
     * @return java.util.Set<java.lang.Object>
     */
    public Set<Object> szRangByLex(String key, RedisZSetCommands.Range range){
        try {
            return redisTemplate.opsForZSet().rangeByLex(key,range);
        } catch (Exception e) {
            return new HashSet<>();
        }
    }

    /**
     * 用于获取满足非score的排序取值
     * @param key 键
     * @param range 区间
     * @param limit 大小
     * @return java.util.Set<java.lang.Object>
     */
    public Set<Object> szRangByLex(String key, RedisZSetCommands.Range range, RedisZSetCommands.Limit limit){
        try {
            return redisTemplate.opsForZSet().rangeByLex(key,range,limit);
        } catch (Exception e) {
            return new HashSet<>();
        }
    }


}
