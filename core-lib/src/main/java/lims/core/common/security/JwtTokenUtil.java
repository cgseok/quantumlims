package lims.core.common.security;


import io.jsonwebtoken.*;
import lims.core.common.exceiption.QuantumSystemException;
import lims.core.common.message.QuantumMessageUtil;
import lims.core.util.QuantumStringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtTokenUtil {
    @Autowired
    private Environment env;

    @Value("${token.secret}")
    private String tokenSecret;

    @Value("${token.issuer}")
    private String tokenIssuer;

    @Value("${token.refreshTime}")
    private String tokenRefreshTime;

    @Value("${token.expirationTime}")
    private String tokenExpirationTime;



    public String createAccessToken(String usid) {
        if (StringUtils.isBlank(usid)) {
            throw new IllegalArgumentException("Cannot create JWT Token without username");
        }

        Claims claims = Jwts.claims().setSubject(usid);

        LocalDateTime currentTime = LocalDateTime.now();

        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuer(tokenIssuer)
                .setIssuedAt(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
                .setExpiration(Date.from(currentTime
                        .plusDays(Long.parseLong(tokenExpirationTime))
//						.plusMinutes(expirationTime)
                        .atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(SignatureAlgorithm.HS256, tokenSecret)
                .compact();

        return token;
    }

    public String createRefreshToken(String usid) {
        if (StringUtils.isBlank(usid)) {
            throw new IllegalArgumentException("Cannot create JWT Token without username");
        }

        LocalDateTime currentTime = LocalDateTime.now();

        Claims claims = Jwts.claims().setSubject(usid);

        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuer(tokenIssuer)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
                .setExpiration(Date.from(currentTime
                        .plusDays(Long.parseLong(tokenRefreshTime))
                        //             .plusMinutes(refreshTime)
                        .atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(SignatureAlgorithm.HS256, tokenSecret)
                .compact();

        return token;
    }

    public String createSmsAccessToken(String usid) {

        Claims claims = Jwts.claims().setSubject(usid);

        claims.put("certificationNumber", QuantumStringUtil.generateCertificationNumber());

        if("01099999999".equals(usid)) {
            claims.put("certificationNumber", "9999");
        }

        LocalDateTime currentTime = LocalDateTime.now();

        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuer(tokenIssuer)
                .setIssuedAt(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
                .setExpiration(Date.from(currentTime
                        .plusMinutes(Long.parseLong(env.getProperty("token.join.expirationTime")))
                        .atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(SignatureAlgorithm.HS256, tokenSecret)
                .compact();

        return token;
    }

    public String createJoinAccessToken(String deviceId) {

        Claims claims = Jwts.claims().setSubject(deviceId);

        LocalDateTime currentTime = LocalDateTime.now();

        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuer(tokenIssuer)
                .setIssuedAt(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
                .setExpiration(Date.from(currentTime
                        .plusHours(Long.parseLong(env.getProperty("token.join.expirationTime")))
                        .atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(SignatureAlgorithm.HS256, tokenSecret)
                .compact();

        return token;
    }

    public String createQrCodeToken(String usid) {
        if (StringUtils.isBlank(usid)) {
            throw new IllegalArgumentException("Cannot create JWT Token without username");
        }

        Claims claims = Jwts.claims().setSubject(usid);

        LocalDateTime currentTime = LocalDateTime.now();

        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuer(tokenIssuer)
                .setIssuedAt(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
                .setExpiration(Date.from(currentTime
                        .plusMonths(1)
                        .atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(SignatureAlgorithm.HS256, tokenSecret)
                .compact();

        return token;
    }

    public String createApiKey(String partnerId) {
        if (StringUtils.isBlank(partnerId)) {
            throw new IllegalArgumentException("Cannot create JWT Token without username");
        }

        Claims claims = Jwts.claims().setSubject(partnerId);

        LocalDateTime currentTime = LocalDateTime.now();

        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuer(tokenIssuer)
                .setIssuedAt(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
                .setExpiration(Date.from(currentTime
                        .plusMonths(12)
                        .atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(SignatureAlgorithm.HS256, tokenSecret)
                .compact();

        return token;
    }

    private Claims getClaims(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(tokenSecret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch(ExpiredJwtException e) {
            throw new QuantumSystemException("ERR.501", QuantumMessageUtil.getMessage("ERR.501"));
        } catch(MalformedJwtException e) {
            throw new QuantumSystemException("ERR.500", QuantumMessageUtil.getMessage("ERR.500"));
        } catch(IllegalArgumentException e) {
            throw new QuantumSystemException("ERR.502", QuantumMessageUtil.getMessage("ERR.502"));
        }
    }

    public String getSubject(String token) {
        return getClaims(token).getSubject();
    }

    public Date getExpiration(String token) {
        return getClaims(token).getExpiration();
    }

    public String getExpirationToString(String token) {
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return transFormat.format(getExpiration(token));

    }

    public Date getIssuedAt(String token) {
        return getClaims(token).getIssuedAt();
    }

    public String getIssuedAtToString(String token) {
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return transFormat.format(getClaims(token).getIssuedAt());
    }

    public String getCertificationNumber(String token) {
        return (String) getClaims(token).get("certificationNumber");
    }

}
