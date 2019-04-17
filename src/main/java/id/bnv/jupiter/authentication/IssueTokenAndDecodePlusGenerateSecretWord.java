package id.bnv.jupiter.authentication;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

public class IssueTokenAndDecodePlusGenerateSecretWord {

    static Algorithm algorithm = Algorithm.HMAC256("jupiter");
    public Algorithm generateSecretWord() {
                
    }


    public static String issueToken(String email) {
        String token = JWT.create()
                .withIssuer("jupiter")
                .withClaim("email", email)
                .sign(algorithm);
        return token;
    }
    public static String decode(String token) {
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("me")
                .build();

        DecodedJWT verify = verifier.verify(token);
        Claim email = verify.getClaim("email");
        return email.asString();
    }
}
