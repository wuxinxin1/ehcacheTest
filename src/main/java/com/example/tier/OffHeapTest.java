package com.example.tier;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;

/**
 * Created by IntelliJ IDEA.
 *  缓存数据存储在off-heap中，这个依据直接内存而定,
 * @author: wuxinxin
 * @date: 2019/3/19
 *
 * The example above allocates a very small amount of off-heap. You will normally use a much bigger space.
 * Remember that data stored off-heap will have to be serialized and deserialized - and is thus slower than heap.
 * 这种方式会比heap方式内存慢，因为需要序列化和反序列化（heap方式不需要序列化和反序列化，因为java虚拟机中才能识别java对象，off-heap不在java虚拟机中）
 * You should thus favor off-heap for large amounts of data where on-heap would have too severe an impact on garbage collection.
 * 因此，当缓存数据量大的时候会严重影响垃圾收集器，应该选用堆外（直接内存）内存方式
 * Do not forget to define in the java options the -XX:MaxDirectMemorySize option, according to the off-heap size you intend to use.
 * 不要忘记设置直接内存的大小，设置虚拟机参数，设置你希望的堆外内存的大小
 */
public class OffHeapTest {

    public static void main(String[] args) {
        //创建一个CacheManager
        CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder().build(true);
        //创建配置构建器，使用配置构建器去创建一个Cache,设置存储键值对的数量为2，超过了的将开始移除多余的
        CacheConfiguration<String, String> cacheConfiguration = CacheConfigurationBuilder.
                newCacheConfigurationBuilder(String.class, String.class, ResourcePoolsBuilder.newResourcePoolsBuilder().offheap(3, MemoryUnit.MB)).build();
        cacheManager.createCache("cache2", cacheConfiguration);

        //使用cache
        Cache<String, String> cache1 = cacheManager.getCache("cache2",String.class, String.class);
        cache1.put("name","wxx");


        //获取
        System.out.println(cache1.get("name"));

        cacheManager.close();
    }
}
