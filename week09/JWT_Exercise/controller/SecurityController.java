package week09.JWT_Exercise.controller;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.SignedJWT;
import io.javalin.http.Handler;
import io.javalin.http.HttpStatus;
import lombok.Getter;

import java.sql.Time;
import java.text.ParseException;
import week09.JWT_Exercise.DTO.UserDTO;
import week09.JWT_Exercise.Exceptions.ApiException;
import week09.JWT_Exercise.Exceptions.NotAuthorizedException;
import week09.JWT_Exercise.config.gsonFactory;
import week09.JWT_Exercise.utils.TokenUtils;
import week09.JWT_Exercise.utils.myJsonObject;
import week09.JWT_Exercise.utils.secretKey;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class SecurityController implements ISecurity {
    @Getter
    private static final SecurityController instance = new SecurityController();

    public static final String SECRET_KEY_STRING = secretKey.generateSecretKey(32);

    @Override
    public Handler register() {
        return UserC.registerUser;
    }

    @Override
    public Handler login() {
        return UserC.login;
    }

    public String createToken(UserDTO user) throws ApiException {
        String issuer;
        String token_expire_time;
        String secret_key;
        if(System.getenv("DEPLOYED") != null){
            issuer = System.getenv("ISSUER");
            token_expire_time = System.getenv("TOKEN_EXPIRE_TIME");
            secret_key = System.getenv("SECRET_KEY");
        } else {
            issuer = "Nicolai R";
            token_expire_time = "30000000";
            secret_key = SECRET_KEY_STRING;
        }
        return TokenUtils.createToken(user, issuer, token_expire_time, secret_key);
    }

    @Override
    public boolean authorize(UserDTO user, Set<String> allowedRoles) {
        if(allowedRoles.contains(UserC.Role.ANYONE.toString()))
            return true;
        for(String s: allowedRoles){
            if(user.getRoles().contains(s)){
                return true;
            }
        }
        return false;
    }

    @Override
    public Handler authenticate() {
        return authenticate;
    }

    public static void main(String[] args) {
        UserDTO testDTO = new UserDTO();
        testDTO.setUsername("Test");
        Set<String> roles = new HashSet<>();
        roles.add("USER");
        testDTO.setRoles(roles);
        SecurityController sc = SecurityController.getInstance(); //// because you cannot override a static function in an interface. createToken ha to not be static.

        System.out.println("json: " + gsonFactory.getGson().toJson(testDTO));
        System.out.println("token: " + sc.createToken(testDTO));
    }

    public static final Handler authenticate = (ctx) -> {
        //// options return
        if (ctx.method().toString().equals("OPTIONS")) {
            ctx.status(200);
            return;
        }
        if(ctx.routeRoles().contains(UserC.Role.ANYONE)){
            return;
        }
        myJsonObject returnObject = new myJsonObject(); //// my own as I am using Gson and I thought it faster to code things myself than recode everything to be jackson
        String header = ctx.header("Authorization");
        if(header == null){
            returnObject.put("msg", "Authorization header missing");
            ctx.status(HttpStatus.FORBIDDEN).json(returnObject);
            return;
        }
        String token = header.split(" ")[1];
        if (token == null) {
            returnObject.put("msg", "Authorization header missing");
            ctx.status(HttpStatus.FORBIDDEN).json(returnObject);
            return;
        }
        SecurityController sc = SecurityController.getInstance();
        UserDTO verifiedTokenUser = sc.verifyToken(token);
        if (verifiedTokenUser == null) {
            returnObject.put("msg", "Invalid User or Token");
            ctx.status(HttpStatus.FORBIDDEN).json(returnObject);
            return;
        }
        if(!ctx.routeRoles().isEmpty() && ctx.routeRoles().stream().noneMatch(x -> verifiedTokenUser.getRoles().contains(x.toString()))){
            returnObject.put("msg", "You don't have access to this part of the website");
            ctx.status(HttpStatus.FORBIDDEN).json(returnObject);
            return;
        }
        System.out.println("USER IN AUTHENTICATE: " + verifiedTokenUser);
        ctx.attribute("user", verifiedTokenUser);
    };

    public UserDTO verifyToken(String token){
        boolean is_deployed = (System.getenv("DEPLOYED") != null);
        String secret = is_deployed ? System.getenv("SECRET_KEY") : SECRET_KEY_STRING;
        try {
            if(TokenUtils.tokenIsValid(token, secret) && TokenUtils.tokenNotExpired(token)){
                return TokenUtils.getUserWithRolesFromToken(token);
            } else {
                return null;
            }
        } catch (Exception ignoreA){

        }
        return null;
    }

    @Override
    public boolean tokenIsValid(String token, String secret) throws ParseException, JOSEException, NotAuthorizedException {
        try {
            SignedJWT jwt = SignedJWT.parse(token);
            jwt.verify(new MACVerifier(secret));
        } catch (java.text.ParseException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public boolean tokenNotExpired(String token) throws ParseException, NotAuthorizedException {
        try {
            SignedJWT jwt = SignedJWT.parse(token);
            return jwt.getJWTClaimsSet().getExpirationTime().after(new Date());
        } catch (java.text.ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UserDTO getUserWithRolesFromToken(String token) throws ParseException {
        return null;
    }

    @Override
    public int timeToExpire(String token) throws ParseException, NotAuthorizedException {
        return 0;
    }
}
