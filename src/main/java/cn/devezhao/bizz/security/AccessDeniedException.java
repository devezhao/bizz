package cn.devezhao.bizz.security;

import cn.devezhao.bizz.privileges.PrivilegesException;

/**
 * 访问禁止
 * 
 * @author <a href="mailto:zhaofang123@gmail.com">Zhao Fangfang</a>
 * @since 0.2, 2010-10-4
 * @version $Id: AccessDeniedException.java 10 2015-06-08 09:10:43Z zhaofang123@gmail.com $
 */
public class AccessDeniedException extends PrivilegesException {
	private static final long serialVersionUID = -3830463402634555692L;

	public AccessDeniedException(String message) {
		super(message);
	}

	public AccessDeniedException(String message, Throwable ex) {
		super(message, ex);
	}
}
