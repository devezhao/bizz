package cn.devezhao.bizz.security.member;

import java.io.Serializable;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import cn.devezhao.bizz.privileges.Privileges;
import cn.devezhao.bizz.security.AccessDeniedException;
import cn.devezhao.bizz.security.EntityPrivileges;

/**
 * 角色
 * 
 * @author <a href="mailto:zhaofang123@gmail.com">Zhao Fangfang</a>
 * @since 0.2, 2010-10-4
 * @version $Id: Role.java 10 2015-06-08 09:10:43Z zhaoff@wisecrm.com $
 */
public class Role extends MemberGroup {
	private static final long serialVersionUID = -8635993188721946096L;

	private Map<Integer, Privileges> allPrivileges = new HashMap<Integer, Privileges>();
	
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
		if (!super.addMember(user))
			return false;
		((User) user).setOwningRole(this);
		return true;
	}
	
	@Override
	public boolean removeMember(Principal user) {
		if (!super.removeMember(user))
			return false;
		((User) user).setOwningRole(null);
		return true;
	}
	
	/**
	 * 添加实体权限
	 * 
	 * @param priv
	 */
	public void addPrivileges(Privileges priv) {
		int e = ((EntityPrivileges) priv).getEntity();
		allPrivileges.put(e, priv);
	}
	
	/**
	 * 是否包含指定实体权限
	 * 
	 * @param entity
	 * @return
	 */
	public boolean hasPrivileges(int entity) {
		return allPrivileges.containsKey(entity);
	}
	
	/**
	 * 获取指定实体权限
	 * 
	 * @param entity
	 * @return
	 * @throws AccessDeniedException If {@link #hasPrivileges(int)} returns <tt>false</tt>
	 */
	public Privileges getPrivileges(int entity) throws AccessDeniedException {
		if (!hasPrivileges(entity))
			throw new AccessDeniedException("No such entity privileges: " + entity);
		return allPrivileges.get(entity);
	}
}
