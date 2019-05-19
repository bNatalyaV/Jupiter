package id.bnv.jupiter.authentication;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import id.bnv.jupiter.pojo.ForDecode;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class IssueAndDecodeToken {

    private static Algorithm algorithm = Algorithm.HMAC256("jupiter");

    String issueToken(int userId) {
        Calendar calendar = new GregorianCalendar();
        Date dateIssue = calendar.getTime();
        calendar.add(Calendar.MINUTE, 5);
        Date dateExpire = calendar.getTime();
        return JWT.create()
                .withIssuer("jupiter")
                .withClaim("userId", userId)
                .withClaim("dateIssue", dateIssue)
                .withClaim("dateExpire", dateExpire)
                .sign(algorithm);
    }

    ForDecode decode(String token) {
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("jupiter")
                .build();

        DecodedJWT verify = verifier.verify(token);
        Claim userId = verify.getClaim("userId");
        Claim dateIssue = verify.getClaim("dateIssue");
        Claim dateExpire = verify.getClaim("dateExpire");

        return new ForDecode(
                userId.asInt(),
                dateIssue.asDate(),
                dateExpire.asDate()
        );
    }
}
