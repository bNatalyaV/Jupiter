package id.bnv.jupiter.json;

import java.io.Serializable;

public class Response implements Serializable {
    public String response;

    public Response(String message) {
        response = message;
    }
}
