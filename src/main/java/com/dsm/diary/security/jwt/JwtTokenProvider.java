package com.dsm.diary.security.jwt;

import com.dsm.diary.Exception.UnauthorizedException;
import com.dsm.diary.entity.refreshToken.RefreshToken;
import com.dsm.diary.entity.refreshToken.RefreshTokenRepository;
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
import javax.validation.constraints.NotNull;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    private final JwtProperties jwtProperties;
    private final AccountDetailsService accountDetailsService;
    private final RefreshTokenRepository refreshTokenRepository;

    public TokenResponse generateToken(String accountId){
        String access = generateAccessToken(accountId);
        String refresh =  generateRefreshToken(accountId);

        refreshTokenRepository.save(
                new RefreshToken(accountId, refresh)
        );

        return new TokenResponse(access, refresh);
    }

    public String generateAccessToken(String accountId){
        return Jwts.builder()
                .setSubject(accountId)
                .claim("type", "access")
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
                .setExpiration(
                        new Date(System.currentTimeMillis() + jwtProperties.getAccessExp() * 1000)
                )
                .setIssuedAt(new Date())
                .compact();
    }

    public String generateRefreshToken(String accountId){
        return Jwts.builder()
                .setSubject(accountId)
                .claim("type", "refresh")
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
                .setExpiration(
                        new Date(System.currentTimeMillis() + jwtProperties.getRefreshExp() * 1000)
                )
                .setIssuedAt(new Date())
                .compact();
    }

    public String resolveToken(HttpServletRequest request){
        String bearerToken = request.getHeader(jwtProperties.getHeader());
        if(bearerToken != null && bearerToken.startsWith(jwtProperties.getPrefix()) && bearerToken.length() > 7){
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token){
        return getBody(token)
                .getExpiration().after(new Date());
    }

    public Authentication getAuthentication(String token){
        Claims body = getBody(token);

        if (body.getExpiration().before(new Date())){
            throw new UnauthorizedException();
        }
        UserDetails userDetails = getDetails(body);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public Claims getBody(String token){
        if(token == null){
            throw new UnauthorizedException();
        }

        try{
            return Jwts.parser()
                    .setSigningKey(jwtProperties.getSecretKey())
                    .parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException | MalformedJwtException e){
            throw new UnauthorizedException();
        }
    }

    public boolean isRefresh(String token){
        try{
            return getBody(token).get("type").equals("refresh");
        } catch (ExpiredJwtException | MalformedJwtException e){
            throw new UnauthorizedException();
        }
    }

    @Nullable
    public UserDetails getDetails(Claims body){
        return accountDetailsService.loadUserByUsername(body.getSubject());
    }
}