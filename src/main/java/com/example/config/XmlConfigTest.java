package com.example.config;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.xml.XmlConfiguration;

import java.net.URL;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: wuxinxin
 * @date: 2019/3/19
 */
public class XmlConfigTest {

    public static void main(String[] args) {

        //通过xml文件去构建CacheManager
        URL resource = new Object().getClass().getResource("/ehcacheConfig.xml");

        XmlConfiguration xmlConfiguration = new XmlConfiguration(resource);

        CacheManager cacheManager = CacheManagerBuilder.newCacheManager(xmlConfiguration);

        cacheManager.init();

        //使用配置中的cache
        Cache<String, String> cach1 = cacheManager.getCache("cach1", String.class, String.class);
        cach1.put("name","wuxinxin");
        System.out.println(cach1.get("name"));

        //使用另外一个cache
        Cache<Integer, String> aDefault = cacheManager.getCache("bar", Integer.class, String.class);

        aDefault.put(1,"乒乓球");
        aDefault.put(2,"羽毛球");

        System.out.println(aDefault.get(2));

        cacheManager.close();

    }

}
