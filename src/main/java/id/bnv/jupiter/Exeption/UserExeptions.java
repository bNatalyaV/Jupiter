package id.bnv.jupiter.Exeption;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties({"cause", "stackTrace", "localizedMessage", "message", "suppressed"})
public class UserExeptions extends JupiterExeptions implements Serializable {

    @JsonProperty("response")
    public String response;

    public UserExeptions(String message) {
        super(message);

        response = message;
    }
}
