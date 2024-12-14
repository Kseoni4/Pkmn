package kseoni.ch.pkmn.repositories.redis;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.hash.HashMapper;
import org.springframework.data.redis.hash.ObjectHashMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Slf4j
@Repository
public class ImageCacheRedisRepository implements RedisRepository<String>
{

    private final String baseKey = "IMG";

    @Resource(name = "redisTemplate")
    HashOperations<String, String, String> hashOperations;

    @Scheduled(fixedDelay = 350000L)
    private void clearImageCache(){
        if(!hashOperations.getOperations().hasKey(baseKey)){
           log.info("Nothing to clear");
           return;
        }
        hashOperations.getOperations().delete(baseKey);
        log.info("Image cache cleared");
    }


    @Override
    public void write(String object, String key) {
        hashOperations.put(baseKey, key, object);
    }

    @Override
    public String load(String key) {
        String url = hashOperations.get(baseKey, key);
        log.info("Load image url {} of card {} from redis",url, key);
        return url;
    }
}
