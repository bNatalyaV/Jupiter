package hello;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import javax.crypto.SecretKey;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.security.Key;

public class Test {
    public static void main(String[] args) throws Exception {
        String user = "kitty";
        String password = "qwerty";
        String remoteHost = "134.209.252.155";
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

    Algorithm algo;

    @org.junit.Test
    public void test() {
        String s = issueToken("id_bnv123@icloud.com");
        System.out.println(s);

        String email = decode(s);
        System.out.println(email);

        //KgZU3jAQXR_F2qDJDsbAEfs6vZ6qzledhKoaq8eVpAw
        //Ll76P8tNsuhmySwfGId7Wx7amX-2JDACCWXaL32rAto
        //Ll76P8tNsuhmySwfGId7Wx7amX-2JDACCWXaL32rAto
    }

    //https://github.com/auth0/java-jwt
    private String issueToken(String email) {
        algo = Algorithm.HMAC256("natasha");
       String token = JWT.create()
               .withIssuer("me")
               .withClaim("email", email)
               .sign(algo);
       return token;
    }

    private String decode(String token) {
        JWTVerifier verifier = JWT.require(algo)
                .withIssuer("me")
                .build();

        DecodedJWT verify = verifier.verify(token);

        Claim email = verify.getClaim("email");

        return email.asString();
    }
}