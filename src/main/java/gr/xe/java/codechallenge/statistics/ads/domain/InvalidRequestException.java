package gr.xe.java.codechallenge.statistics.ads.domain;

public class InvalidRequestException extends RuntimeException {

    public InvalidRequestException(String message) {
        super(message);
    }
}
