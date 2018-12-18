package cn.devezhao.bizz.security.member;

import cn.devezhao.bizz.privileges.PrivilegesException;

/**
 * 找不到成员
 * 
 * @author <a href="mailto:zhaofang123@gmail.com">Zhao Fangfang</a>
 * @since 0.2, 2010-10-5
 * @version $Id: NoMemberFoundException.java 10 2015-06-08 09:10:43Z zhaoff@wisecrm.com $
 */
public class NoMemberFoundException extends PrivilegesException {
	private static final long serialVersionUID = 6677379073353445746L;

	public NoMemberFoundException(String message) {
		super(message);
	}
	
	public NoMemberFoundException(String message, Throwable ex) {
		super(message, ex);
	}
}
