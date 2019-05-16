package id.bnv.jupiter.authentication;

import java.io.Serializable;

public class Response implements Serializable {
    public String response;
    public Status status;

    public enum Status {
        ok, smthWrong
    }

    public Response(String response, Status status) {
        this.response = response;
        this.status = status;
    }
}
