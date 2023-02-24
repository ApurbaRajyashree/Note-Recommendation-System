package com.note.NoteRecommender.helper;



import com.note.NoteRecommender.security.CustomUserDetailService;
import com.note.NoteRecommender.security.UserPrincipal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JwtHelper {




    @Autowired
    private CustomUserDetailService customUserDetailService;
    @Value("${jwt.secretKey}")
    private String secretKey;

    /*public String generateToken(UserPrincipal userPrincipal){
        Map<String,Object> claims=new HashMap<>();
        this.setCustomClaims(claims,userPrincipal);
        return this.doGenerateToken(claims,userPrincipal.getUsername());
    }
    private void setCustomClaims(Map<String,Object> claims,UserPrincipal userPrincipal){
        List<String> authorities=new ArrayList<>();
        for (GrantedAuthority authority: userPrincipal.getAuthorities()){
            authorities.add(authority.getAuthority());
        }
        claims.put("authorities",authorities);
    }
    private String doGenerateToken(Map<String,Object> claims,String subject){
       return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis())).
                setExpiration(new Date(System.currentTimeMillis()+(5*60*60*1000)))
                .signWith(SignatureAlgorithm.HS512,secretKey).compact();
    }
    private Claims getAllClaimsFromToken(String token){
        return Jwts.parser().setSigningKey(this.secretKey).parseClaimsJws(token).getBody();
    }
    public <T> T getClaimFromToken(String token, Function<Claims,T> claimsResolver){
        Claims claims=this.getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }
    public List<GrantedAuthority> getAuthoritiesClaimsFromToken(String token){
        Claims claims=this.getAllClaimsFromToken(token);
        List<GrantedAuthority> returnValue=null;
        List<GrantedAuthority> authorities=(List)claims.get("authorities");
        if(authorities==null) return returnValue;
        returnValue=new ArrayList<>();
        return authorities;
    }
    public String getUserNameFromToken(String token){
        return this.getClaimFromToken(token,Claims::getSubject);
    }
    public Date getExpirationDateFromToken(String token){
        return this.getClaimFromToken(token,Claims::getExpiration);
    }
    private Boolean isTokenExpired(String token){
        Date expiration=this.getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }
    public Boolean validateToken(String token, UserDetails userPrincipal) {
        final String username = getUserNameFromToken(token);
        return (username.equals(userPrincipal.getUsername()) && !isTokenExpired(token));
    }*/



    public String generateToken(UserPrincipal principal) {

        Map<String , Object> claims = new HashMap<>();

        this.setCustomClaims(claims , principal);

        return this.doGenerateToken(claims, principal.getUsername());
    }

    private void setCustomClaims(Map<String, Object> claims, UserPrincipal principal) {
        List<String> authorities = new ArrayList<>();
        for(GrantedAuthority auth : principal.getAuthorities()) {
            authorities.add(auth.getAuthority());
        }
        claims.put("authorities",authorities);

    }
    private String doGenerateToken(Map<String, Object> claims, String subject) {
        /*return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + (5 * 60 * 60 * 1000)))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();*/
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+(5*60*60*1000)))
                .signWith(SignatureAlgorithm.HS256,secretKey)
                .compact();

    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(this.secretKey).parseClaimsJws(token).getBody();
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        Claims claims = this.getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);

    }

    public List<GrantedAuthority> getAuthoritiesClaimFromToken(String token){
        Claims claims = this.getAllClaimsFromToken(token);
        List<GrantedAuthority> returnValue = null;
        List<String>  authorities = (List)claims.get("authorities");

        if(authorities == null) return returnValue;

        returnValue = new ArrayList<>();

        return authorities.stream().map(SimpleGrantedAuthority :: new) .collect(Collectors.toList());

    }

    public String getUserNameFromToken(String token) {
        return this.getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return this.getClaimFromToken(token, Claims::getExpiration);
    }

    private Boolean isTokenExpired(String token) {

        Date expiration = this.getExpirationDateFromToken(token);

        return expiration.before(new Date());
    }

    public Boolean validatToken(String token, UserPrincipal principal) {
        String userName = this.getUserNameFromToken(token);
        return StringUtils.hasText(userName) && userName.equals(principal.getUsername()) && !this.isTokenExpired(token);

    }
    public Boolean validateToken(String token, String principal) {
        String userName = this.getUserNameFromToken(token);
        return StringUtils.hasText(userName) && userName.equals(principal) && !this.isTokenExpired(token);

    }


    public Authentication getAthentication(String userName, List<GrantedAuthority> authorities, HttpServletRequest request) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userName, null, authorities);

        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        return authenticationToken;
    }

}
