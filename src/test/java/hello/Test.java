package hello;


import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLDecoder;

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
}