package co.kensure.exception;

/**
 * com.kensure.exception
 *
 * @author Yingjie yao
 */
public class BusinessException extends RuntimeException{

    public BusinessException( String e ){
        super( e );
    }

    public BusinessException( String e,Throwable cause ){
        super( e,cause );
    }

    public BusinessException( Throwable cause ){
        super( cause );
    }

}
