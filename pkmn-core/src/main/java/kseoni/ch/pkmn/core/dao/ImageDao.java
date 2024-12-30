package kseoni.ch.pkmn.core.dao;

import kseoni.ch.pkmn.core.repositories.redis.RedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ImageDao {

    private final RedisRepository<String> redisRepository;

    public String loadImgUrl(String cardName){
        return redisRepository.load(cardName);
    }

    public void writeImgUrlToCache(String cardName, String imageUrl){
        redisRepository.write(imageUrl, cardName);
    }

}
