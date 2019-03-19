package com.example.tier;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: wuxinxin
 * @date: 2019/3/19
 */
public class HeapTest {

    public static void main(String[] args) {
        //创建一个CacheManager
        CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder().build(true);
        //创建配置构建器，使用配置构建器去创建一个Cache,设置存储键值对的数量为2，超过了的将开始移除多余的
        CacheConfiguration<String, String> cacheConfiguration = CacheConfigurationBuilder.
                newCacheConfigurationBuilder(String.class, String.class, ResourcePoolsBuilder.heap(2)).build();
        cacheManager.createCache("cache1", cacheConfiguration);

        //使用cache
        Cache<String, String> cache1 = cacheManager.getCache("cache1",String.class, String.class);
        cache1.put("name","wxx");
        cache1.put("age","17");
        cache1.put("address","江西省抚州市");

        //获取,
        System.out.println(cache1.get("name"));
        System.out.println(cache1.get("age"));
        System.out.println(cache1.get("address"));

        cacheManager.close();
    }
}
