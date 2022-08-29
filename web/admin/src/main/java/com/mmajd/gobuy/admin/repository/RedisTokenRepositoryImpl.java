package com.mmajd.gobuy.admin.repository;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Repository
@Slf4j
public class RedisTokenRepositoryImpl implements PersistentTokenRepository {
    private final RedisTemplate redisTemplate;
    private static final long TOKEN_VALID_DAYS = 14;

    private enum KEYS {
        USERNAME("USERNAME_KEY", 0),
        TOKEN_VALUE("TOKEN_VALUE_KEY", 1),
        DATE("DATE_KEY", 2),
        SERIES("SERIES", 3);

        @Getter
        private final String value;
        @Getter
        private final int idx;

        KEYS(String v, int i) {
            value = v;
            idx = i;
        }
    }

    private static final String[] HASH_KEYS = {
            KEYS.USERNAME.getValue(),
            KEYS.TOKEN_VALUE.getValue(),
            KEYS.DATE.getValue(),
            KEYS.SERIES.getValue()
    };


    @Override
    public void createNewToken(PersistentRememberMeToken token) {
        String key = generateKey(token.getSeries());
        String usernameKey = generateKey(token.getUsername());

        log.info(key, usernameKey);

        redisTemplate.opsForValue().set(usernameKey, token.getSeries());
        redisTemplate.expire(usernameKey, TOKEN_VALID_DAYS, TimeUnit.DAYS);

        Map<String, String> map = new HashMap<>() {{
            put(KEYS.USERNAME.getValue(), token.getUsername());
            put(KEYS.TOKEN_VALUE.getValue(), token.getTokenValue());
            put(KEYS.DATE.getValue(), String.valueOf(token.getDate().getTime()));
            put(KEYS.DATE.getValue(), token.getSeries());
        }};

        redisTemplate.opsForHash().putAll(key, map);
        redisTemplate.expire(key, TOKEN_VALID_DAYS, TimeUnit.DAYS);
    }

    @Override
    public void updateToken(String series, String tokenValue, Date date) {
        String key = generateKey(series);
        if (redisTemplate.hasKey(key)) {
            redisTemplate.opsForHash().put(key, KEYS.TOKEN_VALUE.getValue(), tokenValue);
            redisTemplate.opsForHash().put(key, KEYS.DATE.getValue(), String.valueOf(date.getTime()));
        }

    }

    @Override
    public PersistentRememberMeToken getTokenForSeries(String series) {
        String key = generateKey(series);
        List<String> hashValues = redisTemplate.opsForHash().multiGet(key, List.of(HASH_KEYS));

        String username = hashValues.get(KEYS.USERNAME.getIdx());
        String tokenValue = hashValues.get(KEYS.TOKEN_VALUE.getIdx());
        String date = hashValues.get(KEYS.DATE.getIdx());

        if (null == username || null == tokenValue || null == date) {
            return null;
        }

        Long timestamp = Long.valueOf(date);
        Date time = new Date(timestamp);

        return new PersistentRememberMeToken(username, series, tokenValue, time);
    }


    @Override
    public void removeUserTokens(String username) {
        String usernameKey = generateKey(username);
        Object o = redisTemplate.opsForValue().get(usernameKey);

        String key = generateKey(String.valueOf(o));

        if (o != null) {
            redisTemplate.delete(usernameKey);
            redisTemplate.delete(key);
        }
    }

    private final String generateKey(String series) {
        return "gobuy:security:token" + series;
    }
}
