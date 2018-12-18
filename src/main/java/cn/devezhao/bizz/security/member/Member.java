package cn.devezhao.bizz.security.member;

import java.io.Serializable;

import cn.devezhao.bizz.privileges.Identity;

/**
 * 成员
 * 
 * @author <a href="mailto:zhaofang123@gmail.com">Zhao Fangfang</a>
 * @since 0.2, 2010-10-4
 * @version $Id: Member.java 10 2015-06-08 09:10:43Z zhaoff@wisecrm.com $
 */
public class Member implements Identity {
	private static final long serialVersionUID = -108698555460795334L;
	
	private Serializable identity;
	private String name;
	private boolean disabled;
	
	/**
	 * @param identity
	 * @param name
	 */
	public Member(Serializable identity, String name) {
		this(identity, name, Boolean.FALSE);
	}
	
	/**
	 * @param identity
	 * @param name
	 * @param disabled
	 */
	public Member(Serializable identity, String name, boolean disabled) {
		this.identity = identity;
		this.name = name;
		this.disabled = disabled;
	}
	
	public Serializable getIdentity() {
		return identity;
	}

	/**
	 * Returns 0
	 */
	public int getMask() {
		return 0;
	}

	public String getName() {
		return name;
	}
	
	/**
	 * @param disabled
	 */
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
	
	/**
	 * @return
	 */
	public boolean isDisabled() {
		return disabled;
	}
	
	@Override
	public int hashCode() {
		return getIdentity().toString().hashCode();
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		return hashCode() == o.hashCode();
	}
	
	/**
	 * Returns NAME:ID:DISABLED@super#toString
	 */
	@Override
	public String toString() {
		return new StringBuffer(getName()).append(':').append(getIdentity())
				.append(':').append(isDisabled() ? 0 : 1).append('@').append(
						super.toString()).toString();
	}
}
