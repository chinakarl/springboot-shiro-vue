package common.redis;

import common.conf.RedisConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author: zhaihx
 * @description:
 * @date:2019/5/22
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RedisConfig.class})
public class RedisHashTest {

//    @Autowired
//    RedisHash redisHashUtils;
    @Test
    public void redisHashPut()
    {
//        redisHashUtils.hset("hash","name","翟海翔");
//        redisHashUtils.hset("hash","age",28);
//        Map<String,Object> map = new HashMap<>();
//        map.put("name","翟海翔");
//        map.put("age",1);
//        redisHashUtils.hmset("hashm",map);
//        redisHashUtils.hset("hash","mobile","13851555614");
//        Object[] objects = new Object[]{"age","mobile"};
//        redisHashUtils.hdel("hash",objects);
//        System.out.println(redisHashUtils.hmget("hash"));

//        System.out.println(redisHashUtils.hget("hash","name"));
//        System.out.println(JSON.toJSONString(redisHashUtils.hmget("hashm")));
//        System.out.println(redisHashUtils.hget("hashm","name"));

    }
}
