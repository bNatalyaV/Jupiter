package id.bnv.jupiter.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties({"cause", "stackTrace", "localizedMessage", "message", "suppressed"})
public class JupiterExeption extends RuntimeException implements Serializable {

    @JsonProperty("response")
    public String response;

    public JupiterExeption(String message) {
        super(message);

        response = message;
    }
}
