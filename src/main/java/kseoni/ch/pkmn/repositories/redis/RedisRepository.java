package kseoni.ch.pkmn.repositories.redis;

public interface RedisRepository<T> {
    void write(T object, String key);

    T load(String key);
}
