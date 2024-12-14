package kseoni.ch.pkmn.dao;

import kseoni.ch.pkmn.repositories.redis.RedisRepository;
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
