package common.redis;

import common.clientservice.RedisZSet;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author: zhaihx
 * @description:
 * @date:2019/5/23
 */
public class RedisZSetTest extends ApplicationBase{
    @Autowired
    RedisZSet redisZSet;
    @Test
    public void test()
    {
//        redisZSet.szAdd("test1","A",1.0);
//        redisZSet.szAdd("test1","B",2.0);
//        redisZSet.szAdd("test1","C",3.0);
//        redisZSet.szAdd("test1","D",4.0);
//        redisZSet.szAdd("test1","E",5.0);
//        System.out.println(redisZSet.szRang("test1",0L,0L));
//        System.out.println(redisZSet.szRangByScore("test1",1,4));
          System.out.println(redisZSet.szRangByScore("test1",1,4,3,2));
//        RedisZSetCommands.Range range = new RedisZSetCommands.Range();
//        //range.gt("B");
//        range.lt("E");
//        System.out.println(redisZSet.szRangByLex("test1",range));
//        RedisZSetCommands.Limit limit = new RedisZSetCommands.Limit();
//        limit.count(2);
//        limit.offset(0);
//        System.out.println(redisZSet.szRangByLex("test1",range,limit));

//        ZSetOperations.TypedTuple<Object> typedTuple1 = new DefaultTypedTuple<Object>("E",6.0);
//        ZSetOperations.TypedTuple<Object> typedTuple2 = new DefaultTypedTuple<Object>("F",7.0);
//        ZSetOperations.TypedTuple<Object> typedTuple3 = new DefaultTypedTuple<Object>("G",5.0);
//        Set<ZSetOperations.TypedTuple<Object>> typedTupleSet = new HashSet<ZSetOperations.TypedTuple<Object>>();
//        typedTupleSet.add(typedTuple1);
//        typedTupleSet.add(typedTuple2);
//        typedTupleSet.add(typedTuple3);
//        redisZSet.szAdd("typedUtpleAdd",typedTupleSet);
//        System.out.println(redisZSet.szRang("typedUtpleAdd",0L,-1L));
    }
}
