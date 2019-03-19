package com.example.config;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.junit.Test;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: wuxinxin
 * @date: 2019/3/19
 */
public class JavaConfigTest {

    @Test
    public void hello(){
        //获取CacheManageer构建器，用来构建CacheManage
        CacheManagerBuilder<CacheManager> cacheManagerBuilder = CacheManagerBuilder.newCacheManagerBuilder();
        //添加构建CacheManager需要的参数,先配置一个Cache,取别名为preConfigured
        cacheManagerBuilder.withCache("preConfigured", CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class, String.class, ResourcePoolsBuilder.heap(10)));
        //创建CacheManager,同时创建一个Cache
        CacheManager cacheManager = cacheManagerBuilder.build();

        cacheManager.init();

        //通过别名来获取CacheManager中获取Cache
        Cache<Long, String> preConfigured = cacheManager.getCache("preConfigured", Long.class, String.class);

        //一个CacheManage可以对Cache进行管理，即进行Cache的创建，删除，关闭等
        Cache<Long, String> cache2 = cacheManager.createCache("cache2", CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class, String.class, ResourcePoolsBuilder.heap(10)));

        //使用创建的cache来存储数据
        cache2.put(1L,"wuxinxin");

        //检索cache中的值
        String s = cache2.get(1L);

        System.out.println(s);
        //移除一个Cache,并且关闭它，所有对你这个cache的相关资源将不可用
        cacheManager.removeCache("preConfigured");

        //关闭CacheManager,将会同时关闭所有的cache
        cacheManager.close();


    }
}
