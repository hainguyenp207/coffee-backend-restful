package com.coffeeinfinitive.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.coffeeinfinitive.constants.ResultCode;
import com.coffeeinfinitive.dao.entity.User;
import com.coffeeinfinitive.exception.CoffeeAuthException;
import com.coffeeinfinitive.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mobile.device.Device;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.xml.transform.Result;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jinz on 4/27/17.
 */
@Component
public final class JwtTokenUtil implements Serializable {
    private static final long serialVersionUID = -3301605591108950415L;

    static final String CLAIM_KEY_USERNAME = "sub";
    static final String CLAIM_KEY_AUDIENCE = "audience";
    static final String CLAIM_KEY_CREATED = "created";

    private static final String AUDIENCE_UNKNOWN = "unknown";
    private static final String AUDIENCE_WEB = "web";
    private static final String AUDIENCE_MOBILE = "mobile";
    private static final String AUDIENCE_TABLET = "tablet";

    @Value("${app.blog.jwt.secret}")
    private String secret;

    private Long expiration = 18000L;
    @Autowired
    private UserService userService;

    public String getUsernameFromToken(String token) {
        return parseToken(token).getSubject();
    }

    public Date getCreatedDateFromToken(JWT jwt) {
        return jwt.getIssuedAt();
    }

    public Date getExpirationDateFromToken(DecodedJWT jwt) {
       return jwt.getExpiresAt();
    }

    public String getAudienceFromToken(JWT jwt) {
        return jwt.getAudience().get(0);
    }

    private Map<String, Claim> getClaimsFromToken(String token) {

        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("auth0")
                    .build(); //Reusable verifier instance
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaims();
        } catch (UnsupportedEncodingException ex){
            throw new CoffeeAuthException(ResultCode.UTF8_UNSUPPORT.getCode(),ex.getMessage());
        } catch (JWTVerificationException ex){
            throw new CoffeeAuthException(ResultCode.INVALID_SIGNATURE.getCode(),ex.getMessage());
        }


    }
    public User getUserFromToken(String token){
        DecodedJWT jwt = parseToken(token);
        if(isTokenExpired(jwt)){
            throw new CoffeeAuthException(ResultCode.TOKEN_EXPIRED.getCode(), ResultCode.TOKEN_EXPIRED.getMessageVn());
        }
        String userId = parseToken(token).getSubject();
        User user = (User) userService.loadUserByUsername(userId);
        if(user==null)
            throw new CoffeeAuthException(ResultCode.INVALID_TOKEN.getCode(), ResultCode.INVALID_TOKEN.getMessageVn());
        return user;

    }
    private  DecodedJWT parseToken(String token){
        try {

            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(token);
            return jwt;
        }catch (UnsupportedEncodingException ex){
            throw new CoffeeAuthException(ResultCode.UTF8_UNSUPPORT.getCode(),ex.getMessage());
        }  catch (JWTVerificationException exception){
            //Invalid signature/claims
            throw new CoffeeAuthException(ResultCode.INVALID_TOKEN.getCode(), ResultCode.INVALID_TOKEN.getMessageVn());
        }
    }

    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis()
                + expiration * 1000);
    }

    private Boolean isTokenExpired(DecodedJWT jwt) {
        final Date expiration = getExpirationDateFromToken(jwt);
        return expiration.before(new Date());
    }

    private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }

    private String generateAudience(Device device) {
        String audience = AUDIENCE_UNKNOWN;
        if (device.isNormal()) {
            audience = AUDIENCE_WEB;
        } else if (device.isTablet()) {
            audience = AUDIENCE_TABLET;
        } else if (device.isMobile()) {
            audience = AUDIENCE_MOBILE;
        }
        return audience;
    }

    private Boolean ignoreTokenExpiration(JWT jwt) {
        String audience = getAudienceFromToken(jwt);
        return (AUDIENCE_TABLET.equals(audience) || AUDIENCE_MOBILE.equals(audience));
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

    String generateToken(Map<String, Object> claims) {
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withClaim(CLAIM_KEY_USERNAME,claims.get(CLAIM_KEY_USERNAME).toString())
                    .withClaim(CLAIM_KEY_CREATED,claims.get(CLAIM_KEY_CREATED).toString())
                    .withSubject(claims.get(CLAIM_KEY_USERNAME).toString())
                    .withExpiresAt(generateExpirationDate())
                    .withIssuedAt(new Date())
                    .sign(algorithm);
        }catch (UnsupportedEncodingException ex){
            //UTF-8 encoding not supported
            throw new CoffeeAuthException(ResultCode.UTF8_UNSUPPORT.getCode(),ex.getMessage());
        } catch (JWTCreationException ex){
            //Invalid Signing configuration / Couldn't convert Claims.
            throw new CoffeeAuthException(ResultCode.INVALID_SIGNATURE.getCode(),ex.getMessage());
        }

    }
}
