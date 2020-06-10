package app.service;

public class ServiceException extends RuntimeException {
    public ServiceException(String str, Exception e){
        super(str, e);
    }
    public ServiceException(String str){
        super(str);
    }
}
