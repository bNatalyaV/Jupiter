package hello;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import id.bnv.jupiter.authentication.IssueAndDecodeToken;
import id.bnv.jupiter.pojo.ForDecode;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

import static jdk.nashorn.internal.objects.NativeMath.random;

public class Test {
    public static void main(String[] args) throws Exception {
        // nohup java -jar jupiter.jar

        String user = "root";
        String password = "Bilfo_05";
        String remoteHost = "138.197.187.9";
        int localPort = 7777;
        int remotePort = 3306;
        Session session = null;
        try {
            JSch jsch = new JSch();
            session = jsch.getSession(user, remoteHost);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();
            session.setPortForwardingL(localPort, "127.0.0.1", remotePort);
            System.out.println("Waiting for connections…");
            new BufferedReader(new InputStreamReader(System.in)).readLine();
        } catch (Exception ex) {
            System.out.println("Failed to setup tunnel");
            ex.printStackTrace();
        } finally {
            try {
                session.disconnect();
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }

    @org.junit.Test
    public void test() {
        String s = issueToken(46);
        System.out.println(s);

//        String a = IssueAndDecodeToken.issueToken(5);
//        System.out.println(a);
//        ForDecode b=IssueAndDecodeToken.decode(a);
//        System.out.println(b.userId+"\n"+b.dateIssue+"\n"+b.dateExpire);
//        double a;
//        for (int i = 0; i < 5; i++) {
//            Random random=new Random();
//            a = -100 + random.nextInt(200);
//            System.out.println(a);
//        }
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

    static Algorithm algorithm = Algorithm.HMAC256("jupiter");

    public String issueToken(int userId) {
        Calendar calendar=new GregorianCalendar();
        Date dateIssue= calendar.getTime();
        calendar.add(Calendar.MINUTE, 5);
        Date dateExpire = new Date(new Date().getTime() + 1000 * 60 * 60 * 10);
        String token = JWT.create()
                .withIssuer("jupiter")
                .withClaim("userId", userId)
                .withClaim("dateIssue", dateIssue)
                .withClaim("dateExpire", dateExpire)
                .sign(algorithm);
        return token;
    }

//        for (String s: new String[]{"natasha","ne","nata","sha","a","b"}) {
//            System.out.println(str.hashCode());
//            str += s;
//
//        }
//    }
//
//    public Integer a() {
//        return 5;
//    }

//    Algorithm algo;
//
//    @org.junit.Test
//    public void test() {
//        String s = issueToken("id_bnv123@icloud.com");
//        System.out.println(s);
//
//        String email = decode(s);
//        System.out.println(email);
//
//    }
//
//    //https://github.com/auth0/java-jwt
//    private String issueToken(String email) {
//        algo = Algorithm.HMAC256("natasha");
//        String token = JWT.create()
//                .withIssuer("me")
//                .withClaim("email", email)
//                .sign(algo);
//        return token;
//    }
//
//    private String decode(String token) {
//        JWTVerifier verifier = JWT.require(algo)
//                .withIssuer("me")
//                //  .acceptExpiresAt(5)
//                .build();
//
//        DecodedJWT verify = verifier.verify(token);
//
//        Claim email = verify.getClaim("email");
//
//        return email.asString();
//    }
    }