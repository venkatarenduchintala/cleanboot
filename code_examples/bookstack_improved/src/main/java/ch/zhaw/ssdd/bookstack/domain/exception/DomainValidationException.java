package ch.zhaw.ssdd.bookstack.domain.exception;

public class DomainValidationException extends RuntimeException {

    public DomainValidationException(String msg) {
        super(msg);
    }
    
}
