package id.bnv.jupiter.authentication;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

public class IssueAndDecodeToken {

    static Algorithm algorithm = Algorithm.HMAC256("jupiter");


    public static String issueToken(String email) {

        Date dateIssue=new Date();
        Date dateExpire=dateIssue;//need to add 15 days
        String token = JWT.create()
                .withIssuer("jupiter")
                .withClaim("email", email)
                .sign(algorithm);
        return token;
    }
    public static String decode(String token) {
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("jupiter")
                .build();

        DecodedJWT verify = verifier.verify(token);
        Claim email = verify.getClaim("email");
        return email.asString();
    }
}
