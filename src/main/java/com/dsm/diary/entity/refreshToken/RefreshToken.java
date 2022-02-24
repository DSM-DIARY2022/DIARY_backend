package com.dsm.diary.entity.refreshToken;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash
public class RefreshToken {
    @Id
    private String username;

    @Indexed
    private String refreshToken;

    @TimeToLive
    private String ttl;

    public RefreshToken update(String username, String refreshToken){
        this.refreshToken = refreshToken;
        this.ttl = ttl;
        return this;
    }
}
