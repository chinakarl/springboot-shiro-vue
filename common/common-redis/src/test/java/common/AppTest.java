package common;

import common.clientservice.RedisService;
import common.conf.RedisConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertTrue;

/**
 * Unit test for simple App.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RedisConfig.class})
public class AppTest 
{

    @Autowired
    RedisService redisService;
//    @Autowired
//    RedisTemplate<String,String> redisTemplate;
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    @Test
    public void redisTest()
    {
        //System.out.println(redisService.set("test","1",-1));
        redisService.incr("incrtest",1);
        System.out.println(redisService.get("incrtest"));
//        Map<String, Object> properties = new HashMap<>();
//        properties.put("123", "hello");
//        properties.put("abc", 456);
//
//        redisTemplate.opsForHash().putAll("hash", properties);
//
//        Map<Object, Object> ans = redisTemplate.opsForHash().entries("hash");
//        System.out.println("ans: " + ans);
    }
}
