package co.kensure.exception;


/**
 * 业务异常处理帮助类
 *
 * @author fankd
 */
public class BusinessExceptionUtil {

	public static void threwException(String e) throws BusinessException {
		throw new BusinessException(e);
	}

	public static void threwException(String e, Throwable cause) throws BusinessException {
		throw new BusinessException(e, cause);
	}

	public static void threwException(Throwable cause) throws BusinessException {
		throw new BusinessException(cause);
	}
	
}
