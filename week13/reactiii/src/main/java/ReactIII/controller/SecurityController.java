package ReactIII.controller;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import ReactIII.DTO.UserDTO;
import ReactIII.Exceptions.ApiException;
import ReactIII.Exceptions.NotAuthorizedException;
import ReactIII.config.gsonFactory;
import sir.rolin.my_library.utils.TokenUtils;
import sir.rolin.my_library.utils.secretKey;
import ReactIII.utils.myJsonObject;
import io.javalin.http.Handler;
import io.javalin.http.HttpStatus;
import lombok.Getter;

import java.text.ParseException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

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
        Map<String,String> claims = new HashMap<String, String>();
        claims.put("username", user.getUsername());
        claims.put("roles", user.getRoles().size() == 0 ? "" :  user.getRoles().stream().reduce((s1, s2) -> s1 + "," + s2).get());
        try {
            return sir.rolin.my_library.utils.TokenUtils.createToken(issuer, token_expire_time, secret_key, "Sir Rolin", claims);
        } catch (JOSEException e) {
            throw new ApiException(500, e.toString());
        }
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
                return getUserWithRolesFromToken(token);
            }
        } catch (Exception ignoreA){

        }
        return null;
    }

    @Override
    public boolean tokenIsValid(String token, String secret) throws ParseException, JOSEException, NotAuthorizedException {
        return TokenUtils.tokenIsValid(token, secret);
    }

    @Override
    public boolean tokenNotExpired(String token) throws ParseException, NotAuthorizedException {
        return TokenUtils.tokenNotExpired(token);
    }

    @Override
    public UserDTO getUserWithRolesFromToken(String token) throws ParseException {
        Function<JWTClaimsSet, UserDTO> extractor = claims -> {
            try {
                String user = claims.getStringClaim("username");
                String roles = claims.getStringClaim("roles");
                return new UserDTO(user, Set.of(roles.split(",")));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        };
        return (UserDTO) TokenUtils.extractFromToken(token, extractor);
    }

    @Override
    public int timeToExpire(String token) throws ParseException, NotAuthorizedException {
        return TokenUtils.timeToExpire(token);
    }
}
