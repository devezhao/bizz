package cn.devezhao.bizz;

import org.apache.commons.lang.exception.NestableRuntimeException;

/**
 * Bizz Root Exception
 * 
 * @author <a href="mailto:zhaofang123@gmail.com">Zhao Fangfang</a>
 * @since 0.2, 2010-10-4
 * @version $Id: BizzException.java 10 2015-06-08 09:10:43Z zhaoff@wisecrm.com $
 */
public class BizzException extends NestableRuntimeException {
	private static final long serialVersionUID = -1382759102893752967L;

	public BizzException() {
		super();
	}

	public BizzException(String message) {
		super(message);
	}

	public BizzException(Throwable ex) {
		super(ex);
	}

	public BizzException(String message, Throwable ex) {
		super(message, ex);
	}
}
