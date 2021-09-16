package cn.devezhao.bizz.security.member;

import java.io.Serializable;
import java.security.Principal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * 部门
 * 
 * @author <a href="mailto:zhaofang123@gmail.com">Zhao Fangfang</a>
 * @since 0.2, 2010-10-4
 * @version $Id: BusinessUnit.java 10 2015-06-08 09:10:43Z zhaofang123@gmail.com $
 */
public class BusinessUnit extends MemberGroup {
	private static final long serialVersionUID = -1751170855052911017L;

	private User principal;
	
	private BusinessUnit parentBizUnit;
	private final Set<BusinessUnit> childBizUnits = new HashSet<>();
	
	/**
	 * @param identity
	 * @param name
	 * @param disabled
	 */
	public BusinessUnit(Serializable identity, String name, boolean disabled) {
		super(identity, name, disabled);
	}
	
	@Override
    public boolean addMember(Principal user) {
		if (!super.addMember(user)) {
			return false;
		}
		((User) user).setOwningBizUnit(this);
		return true;
	}
	
	@Override
	public boolean removeMember(Principal user) {
		if (!super.removeMember(user)) {
			return false;
		}
		((User) user).setOwningBizUnit(null);
		return true;
	}
	
	/**
	 * @return
	 */
	public User getPrincipal() {
		return principal;
	}
	
	/**
	 * @param principal
	 */
	public void setPrincipal(User principal) {
		this.principal = principal;
	}
	
	/**
	 * 获取父级部门
	 * 
	 * @return
	 */
	public BusinessUnit getParent() {
		return parentBizUnit;
	}
	
	/**
	 * 添加子部门
	 * 
	 * @param child
	 */
	public void addChild(BusinessUnit child) {
		if (childBizUnits.contains(child)) {
			return;
		}
		childBizUnits.add(child);
		child.setParent(this);
	}
	
	/**
	 * 移除子部门
	 * 
	 * @param child
	 */
	public void removeChild(BusinessUnit child) {
		if (!childBizUnits.contains(child)) {
			return;
		}
		childBizUnits.remove(child);
		child.setParent(null);
	}
	
	/**
	 * 获取子部门列表
	 * 
	 * @return
	 */
	public Set<BusinessUnit> getChildren() {
		return Collections.unmodifiableSet(childBizUnits);
	}
	
	/**
	 * 设置父级部门
	 * 
	 * @param parent
	 */
	protected void setParent(BusinessUnit parent) {
		this.parentBizUnit = parent;
	}
}
