package me.elhakimi.vroom.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import me.elhakimi.vroom.domain.AppUser;
import me.elhakimi.vroom.service.UserService;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
@AllArgsConstructor
public class JwtService {

    private final UserService userService ;

    private final String SECRET_KEY =
            "a0b43ab577e9f303702760138c95484646c8cb8503764b9bef3ecadfb3b10fe6dc61b42c6dba51af06ee24b53a3353b1eb868a69b6ec3504bf40aeca55ac98ae7647a9f9bf84027d45e09a8e88bb2545b458438fe50031b68f9522402d078eda7f820d71a2a7f60f7b499e6d5778380518efeb6cf08687f4b956437caff7f9437d4f775b98d69dcae6a208601105c52348450faea5b67b443ee40b56f52b5db87ad62b696f2a4928e3e01aca39ed4c8f12ae02f9516e88a36ef8ece50801e8e7ec914a886314151f64104809fe6b61316b1cab2efda93c30701696f53a1e194e2e885f5b960435440001c56ce5642937025f3185ec6f499ffcc0551ce8a8cc43";

    public Map<String , String> generateToken(String username) {
        AppUser appUser = userService.loadUserByUsername(username);
        return this.generateJwt(appUser);
    }

    private Map<String, String> generateJwt(AppUser appUser) {

        long currentTime = System.currentTimeMillis();

        final Map<String, String> claims = Map.of(
                                "username", appUser.getUsername(),
                                "email" , appUser.getEmail()
                        );

        final String bearer = Jwts.builder()
                .issuedAt(new Date(currentTime))
//                .expiration(new Date(currentTime + 15 * 60 * 1000))
                .expiration(new Date(currentTime + 5 * 1000))
                .subject(appUser.getUsername())
                .claims(claims)
                .signWith(getKeySecretKey())
                .compact();

        return Map.of("token", bearer);

    }

    private Key getKeySecretKey() {

        final  byte[] decode = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(decode);
    }

    public String extractUsername(String token) {
        return  getClaim(token , Claims::getSubject);

    }

    public boolean isTokenExpired(String token) {
        Date expirationDate = getExpirationDateFromToken(token);
        return expirationDate.before(new Date());
    }

    private Date getExpirationDateFromToken(String token) {
        return this.getClaim(token , Claims::getExpiration);
    }

    private <T> T getClaim(String token , Function<Claims , T> claimsResolver){
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
            return Jwts
                    .parser()
                    .setSigningKey(getKeySecretKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

    }

    //Refresh Token

    public Map<String, String> generateRefreshToken(String username) {

        long currentTime = System.currentTimeMillis();

        String rToken =   Jwts.builder()
                .issuedAt(new Date(currentTime))
                .subject(username)
                .expiration(new Date(currentTime + 30L * 24 * 60 * 60 * 1000))
                .signWith(getKeySecretKey())
                .compact();

        return Map.of("refreshToken" , rToken);

    }

    public Map<String, String> getRefreshTokenAndAccessToken(String username, HttpServletResponse response) {
        Map<String, String> refreshToken = this.generateRefreshToken(username);
        AppUser appUser = userService.loadUserByUsername(username);

        appUser.setRefreshToken(refreshToken.get("refreshToken"));
        userService.saveRefreshToken(appUser);

        Map<String, String> accessToken = this.generateToken(username);

        // Créez le cookie refreshToken
        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken.get("refreshToken"));
        refreshTokenCookie.setHttpOnly(true);  // Cookie uniquement accessible par HTTP
        refreshTokenCookie.setSecure(true);   // Mettre à true en production (si HTTPS)
        refreshTokenCookie.setPath("/");       // Le cookie est disponible sur toute l'application
        refreshTokenCookie.setMaxAge(7 * 24 * 60 * 60);  // Durée de vie 7 jours
        refreshTokenCookie.setAttribute("SameSite", "None");  // Autoriser les cookies cross-origin en mode CORS

        // Ajoute le cookie dans la réponse
        response.addCookie(refreshTokenCookie);

        return Map.of("token", accessToken.get("token"));
    }


    public Map<String, String> generateNewToken(String rToken) {

        if(rToken == null){
            throw new IllegalArgumentException("Refresh token is null");
        }

        if(isTokenExpired(rToken)){
            throw new IllegalArgumentException("Refresh token is expired");
        }

        AppUser appUser = userService.getByRefreshToken(rToken);
        return this.generateJwt(appUser);

    }


}
