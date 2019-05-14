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

    static Algorithm algorithm = Algorithm.HMAC256("jupiter");//secret word

    public String issueToken(int userId) {
        Calendar calendar=new GregorianCalendar();
        Date dateIssue= calendar.getTime();
        calendar.add(Calendar.MINUTE, 5);
        Date dateExpire=calendar.getTime();
        String token = JWT.create()
                .withIssuer("jupiter")
                .withClaim("userId", userId)
                .withClaim("dateIssue", dateIssue)
                .withClaim("dateExpire", dateExpire)
                .sign(algorithm);
        return token;
    }
    public  ForDecode decode(String token) {
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("jupiter")
                .build();

        DecodedJWT verify = verifier.verify(token);
        Claim userId=verify.getClaim("userId");
        Claim dateIssue=verify.getClaim("dateIssue");
        Claim dateExpire=verify.getClaim("dateExpire");
        ForDecode forDecode=new ForDecode(userId.asInt(), dateIssue.asDate(),
                                           dateExpire.asDate());

        return forDecode;
    }
}
