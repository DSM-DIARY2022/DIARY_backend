package com.dsm.diary.security.jwt;

import com.dsm.diary.entity.refreshToken.RefreshToken;
import com.dsm.diary.entity.refreshToken.RefreshTokenRepository;
import com.dsm.diary.exception.UnauthorizedException;
import com.dsm.diary.security.auth.AccountDetailsService;
import com.dsm.diary.security.jwt.dto.TokenResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import reactor.util.annotation.Nullable;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final JwtProperties jwtProperties;
    private final AccountDetailsService accountDetailsService;
    private final RefreshTokenRepository refreshTokenRepository;

    public TokenResponse generateToken(String accountId) {
        return new TokenResponse(generateAccessToken(accountId), generateRefreshToken(accountId));
    }

    private String setToken(String accountId, String type, Long exp) {
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
                .setSubject(accountId)
                .claim("typ", type)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + exp * 1000))
                .compact();
    }

    public String generateAccessToken(String accountId) {
        return setToken(accountId, "access", jwtProperties.getAccessExp());
    }

    public String generateRefreshToken(String accountId) {
        String refresh = setToken(accountId, "refresh", jwtProperties.getRefreshExp());

        refreshTokenRepository.save(
                RefreshToken.builder()
                        .id(accountId)
                        .refreshToken(refresh)
                        .ttl(jwtProperties.getRefreshExp())
                        .build()
        );

        return refresh;
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(jwtProperties.getHeader());
        if (bearerToken != null && bearerToken.startsWith(jwtProperties.getPrefix()) && bearerToken.length() > 7) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token) {
        return getBody(token)
                .getExpiration().after(new Date());
    }

    public Authentication getAuthentication(String token) {
        Claims body = getBody(token);

        if (body.getExpiration().before(new Date())) {
            throw new UnauthorizedException();
        }
        UserDetails userDetails = getDetails(body);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public Claims getBody(String token) {
        if (token == null) {
            throw new UnauthorizedException();
        }

        try {
            return Jwts.parser()
                    .setSigningKey(jwtProperties.getSecretKey())
                    .parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException | MalformedJwtException e) {
            throw new UnauthorizedException();
        }
    }

    @Nullable
    public UserDetails getDetails(Claims body) {
        return accountDetailsService.loadUserByUsername(body.getSubject());
    }
}