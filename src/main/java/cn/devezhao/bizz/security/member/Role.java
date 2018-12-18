package cn.devezhao.bizz.security.member;

import java.io.Serializable;
import java.security.Principal;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import cn.devezhao.bizz.privileges.Privileges;

/**
 * 角色
 * 
 * @author <a href="mailto:zhaofang123@gmail.com">Zhao Fangfang</a>
 * @since 0.2, 2010-10-4
 * @version $Id: Role.java 10 2015-06-08 09:10:43Z zhaoff@wisecrm.com $
 */
public class Role extends MemberGroup {
	private static final long serialVersionUID = -8635993188721946096L;

	private Map<Serializable, Privileges> allPrivileges = new HashMap<Serializable, Privileges>();
	
	/**
	 * @param identity
	 * @param name
	 * @param disabled
	 */
	public Role(Serializable identity, String name, boolean disabled) {
		super(identity, name, disabled);
	}
	
	@Override
	public boolean addMember(Principal user) {
		if (!super.addMember(user)) {
			return false;
		}
		((User) user).setOwningRole(this);
		return true;
	}
	
	@Override
	public boolean removeMember(Principal user) {
		if (!super.removeMember(user)) {
			return false;
		}
		((User) user).setOwningRole(null);
		return true;
	}
	
	/**
	 * 添加权限
	 * 
	 * @param priv
	 */
	public void addPrivileges(Privileges priv) {
		allPrivileges.put(priv.getIdentity(), priv);
	}
	
	/**
	 * 是否包含指定权限
	 * 
	 * @param identity
	 * @return
	 */
	public boolean hasPrivileges(Serializable identity) {
		return allPrivileges.containsKey(identity);
	}
	
	/**
	 * 获取指定实体权限
	 * 
	 * @param identity
	 * @return
	 */
	public Privileges getPrivileges(Serializable identity) {
		if (!hasPrivileges(identity)) {
			return Privileges.NONE;
		}
		return allPrivileges.get(identity);
	}
	
	/**
	 * 权限明细
	 * 
	 * @return
	 */
	public Collection<Privileges> getAllPrivileges() {
		return Collections.unmodifiableCollection(allPrivileges.values());
	}
}
