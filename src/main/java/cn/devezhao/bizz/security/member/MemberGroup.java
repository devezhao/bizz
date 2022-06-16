package cn.devezhao.bizz.security.member;

import java.io.Serializable;
import java.security.Principal;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

/**
 * 成员组
 * 
 * @author <a href="mailto:zhaofang123@gmail.com">Zhao Fangfang</a>
 * @since 0.2, 2010-10-4
 * @version $Id: MemberGroup.java 10 2015-06-08 09:10:43Z zhaofang123@gmail.com $
 */
public class MemberGroup extends Member {
	private static final long serialVersionUID = 4050659655955435119L;
	
	/**
	 * 成员 */
	protected final Set<Principal> allMembers = new HashSet<>();
	
	/**
	 * @param identity
	 * @param name
	 * @param disabled
	 */
	public MemberGroup(Serializable identity, String name, boolean disabled) {
		super(identity, name, disabled);
	}

	/**
	 * @param user
	 * @return
	 */
    public boolean addMember(Principal user) {
		if (isMember(user)) {
			return false;
		}
		return allMembers.add(user);
	}

	/**
	 * @param user
	 * @return
	 */
    public boolean removeMember(Principal user) {
		if (!isMember(user)) {
			return false;
		}
		return allMembers.remove(user);
	}

	/**
	 * @param user
	 * @return
	 */
    public boolean isMember(Principal user) {
		return allMembers.contains(user);
	}

	/**
	 * @param identity
	 * @return
	 */
	public boolean isMember(Serializable identity) {
		for (Principal user : allMembers) {
			if (((User) user).getIdentity().equals(identity)) {
				return true;
			}
		}
		return false;
	}

	@Deprecated
	public Enumeration<? extends Principal> members() {
		throw new UnsupportedOperationException("Deprecated! Please using #getMembers");
	}

	/**
	 * 获取成员列表
	 * 
	 * @return
	 */
	public Set<Principal> getMembers() {
		return Collections.unmodifiableSet(allMembers);
	}
}
