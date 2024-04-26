package week08.security_wed_thur.controller;

import com.nimbusds.jose.JOSEException;
import io.javalin.http.Handler;
import io.javalin.http.HttpStatus;
import lombok.Getter;
import net.minidev.json.parser.ParseException;
import week08.security_wed_thur.DTO.UserDTO;
import week08.security_wed_thur.Exceptions.ApiException;
import week08.security_wed_thur.Exceptions.NotAuthorizedException;
import week08.security_wed_thur.config.gsonFactory;
import week08.security_wed_thur.utils.TokenUtils;
import week08.security_wed_thur.utils.myJsonObject;
import week08.security_wed_thur.utils.secretKey;

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
            token_expire_time = "3000000";
            secret_key = SECRET_KEY_STRING;
        }
        return TokenUtils.createToken(user, issuer, token_expire_time, secret_key);
    }

    @Override
    public boolean authorize(UserDTO user, Set<String> allowedRoles) {
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
        testDTO.setPassword("IkkeGodt");
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
        return false;
    }

    @Override
    public boolean tokenNotExpired(String token) throws ParseException, NotAuthorizedException {
        return false;
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
