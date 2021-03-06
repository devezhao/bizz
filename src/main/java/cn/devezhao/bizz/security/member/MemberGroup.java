package cn.devezhao.bizz.security.member;

import java.io.Serializable;
import java.security.Principal;
import java.security.acl.Group;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

/**
 * 成员组
 * 
 * @author <a href="mailto:zhaofang123@gmail.com">Zhao Fangfang</a>
 * @since 0.2, 2010-10-4
 * @version $Id: MemberGroup.java 10 2015-06-08 09:10:43Z zhaoff@wisecrm.com $
 */
public class MemberGroup extends Member implements Group {
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

	@Override
    public boolean addMember(Principal user) {
		if (isMember(user)) {
			return false;
		}
		return allMembers.add(user);
	}
	
	@Override
    public boolean removeMember(Principal user) {
		if (!isMember(user)) {
			return false;
		}
		return allMembers.remove(user);
	}
	
	@Override
    public boolean isMember(Principal user) {
		return allMembers.contains(user);
	}
	
	public boolean isMember(Serializable identity) {
		for (Principal user : allMembers) {
			if (((User) user).getIdentity().equals(identity)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
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
