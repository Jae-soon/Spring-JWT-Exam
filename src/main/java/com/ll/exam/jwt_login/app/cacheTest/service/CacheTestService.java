package com.ll.exam.jwt_login.app.cacheTest.service;

import com.ll.exam.jwt_login.app.cacheTest.dto.Person;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class CacheTestService {

    @Cacheable("key1")
    public int getCachedInt() {
        System.out.println("getCachedInt 호출됨");
        return 5;
    }

    @CacheEvict("key1")
    public void deleteCacheKey1() {
    }

    @CachePut("key1")
    public int putCacheKey1() {
        return 10;
    }

    @Cacheable("plus")
    public int plus(int a, int b) {
        System.out.println("== plus 실행 ==");
        return a + b;
    }

    @Cacheable(value = "getName", key = "#p.id") // person의 id에 따라 cache Key값 생성(만약 id가 같다면 같은 Key)
    public String getName(Person p, int random) {
        System.out.println("== getName 실행됨 ==");
        return p.getName();
    }
}