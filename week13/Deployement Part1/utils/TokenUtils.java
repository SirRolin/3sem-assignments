package week13.ReactIII.utils;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import week13.ReactIII.DAO.concrete.UserDAO;
import week13.ReactIII.DTO.UserDTO;
import week13.ReactIII.Exceptions.ApiException;
import week13.ReactIII.Exceptions.NotAuthorizedException;
import week13.ReactIII.model.User;

import java.text.ParseException;
import java.util.*;

public class TokenUtils {

    public static String createToken(UserDTO user, String issuer, String token_expire_time, String secret_key) throws ApiException {
        try {
            String roleString = user.getRoles().size() == 0 ? "" :  user.getRoles().stream().reduce((s1, s2) -> s1 + "," + s2).get();

            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .subject(user.getUsername())
                    .issuer(issuer)
                    .claim("username", user.getUsername())
                    .claim("roles", roleString)
                    .expirationTime(new Date(new Date().getTime() + Integer.parseInt(token_expire_time)))
                    .build();

            Payload payload = new Payload(claimsSet.toJSONObject());

            JWSSigner signer = new MACSigner(secret_key);
            JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS256);
            JWSObject jwsobject = new JWSObject(jwsHeader, payload);
            jwsobject.sign(signer);
            return jwsobject.serialize();

        } catch (JOSEException|NoSuchElementException e) {
            e.printStackTrace();
            throw new ApiException(500, "Could not create token");
        }
    }
    public static boolean tokenNotExpired(String token) throws ParseException, NotAuthorizedException {
        if(timeToExpire(token) > 0){
            return true;
        } else {
            throw new NotAuthorizedException(403, "Token has Expired");
        }
    }

    private static int timeToExpire(String token) throws ParseException, NotAuthorizedException {
        SignedJWT jwt = SignedJWT.parse(token);
        return (int) (jwt.getJWTClaimsSet().getExpirationTime().getTime() - new Date().getTime());
    }

    public static boolean tokenIsValid(String token, String secret) throws ParseException, NotAuthorizedException, JOSEException {
        SignedJWT jwt = SignedJWT.parse(token);
        if (jwt.verify(new MACVerifier(secret)))
        {
            return true;
        }
        else
        {
            throw new NotAuthorizedException(403, "Token is not valid");
        }
    }

    public static UserDTO getUserWithRolesFromToken(String token) {
        try {
            JWTClaimsSet claims = SignedJWT.parse(token).getJWTClaimsSet();
            String user = claims.getStringClaim("username");
            String roles = claims.getStringClaim("roles");
            return new UserDTO(user, Set.of(roles.split(",")));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
