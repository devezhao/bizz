package cn.devezhao.bizz.privileges;

import cn.devezhao.bizz.BizzException;

/**
 * 权限异常
 * 
 * @author <a href="mailto:zhaofang123@gmail.com">Zhao Fangfang</a>
 * @since 0.2, 2010-10-4
 * @version $Id: PrivilegesException.java 10 2015-06-08 09:10:43Z zhaofang123@gmail.com $
 */
public class PrivilegesException extends BizzException {
	private static final long serialVersionUID = 3613672604473429661L;

	public PrivilegesException(String message) {
		super(message);
	}
	
	public PrivilegesException(String message, Throwable ex) {
		super(message, ex);
	}
}
