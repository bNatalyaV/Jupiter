package id.bnv.jupiter.Exeption;

import id.bnv.jupiter.pojo.Journey;

import java.io.Serializable;

public class JourneyExeptions extends JupiterExeptions implements Serializable {

    public String response;

    public JourneyExeptions(String message) {
        super(message);

        response=message;
    }
}
