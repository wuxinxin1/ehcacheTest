package com.example.tier;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.PersistentCacheManager;
import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.CacheManagerConfiguration;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;

import java.io.File;

/**
 * Created by IntelliJ IDEA.
 *  缓存数据存储在disk中
 * @author: wuxinxin
 * @date: 2019/3/19
 *
 */
public class DiskTest {

    public static void main(String[] args) {
        //创建一个CacheManager
        CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder().
                with(CacheManagerBuilder.persistence(new File("d:/test", "myData"))).build(true);

        //创建配置构建器，使用配置构建器去创建一个Cache,设置存储键值对的数量为2，超过了的将开始移除多余的
        CacheConfigurationBuilder<String, String> cacheConfiguration = CacheConfigurationBuilder.
                newCacheConfigurationBuilder(String.class, String.class, ResourcePoolsBuilder.newResourcePoolsBuilder().disk(3, MemoryUnit.MB,true));

        cacheManager.createCache("cache1", cacheConfiguration);

        //使用cache
        Cache<String, String> cache1 = cacheManager.getCache("cache1",String.class, String.class);
        cache1.put("name","wxx");


        //获取
        System.out.println(cache1.get("name"));

        cacheManager.close();
    }
}
