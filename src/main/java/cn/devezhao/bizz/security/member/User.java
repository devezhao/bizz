package cn.devezhao.bizz.security.member;

import java.io.Serializable;
import java.security.Principal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * 用户
 * 
 * @author <a href="mailto:zhaofang123@gmail.com">Zhao Fangfang</a>
 * @since 0.2, 2010-10-4
 * @version $Id: User.java 10 2015-06-08 09:10:43Z zhaoff@wisecrm.com $
 */
public class User extends Member implements Principal {
	private static final long serialVersionUID = -5924320591430994214L;

	private Role owningRole;
	private BusinessUnit owningBizUnit;
	private Set<Team> owningTeams = new HashSet<>(2);

	/**
	 * @param identity
	 * @param name
	 * @param disabled
	 */
	public User(Serializable identity, String name, boolean disabled) {
		super(identity, name, disabled);
	}

	/**
	 * @return
	 */
	public Role getOwningRole() {
		return owningRole;
	}

	/**
	 * @return
	 */
	public BusinessUnit getOwningBizUnit() {
		return owningBizUnit;
	}
	
	/**
	 * @return
	 */
	public Set<Team> getOwningTeams() {
		return Collections.<Team>unmodifiableSet(owningTeams);
	}
	
	/**
	 * 用户是否可用，需满足以下全部条件
	 * <ul>
	 * <li>isDisabled() == false</li>
	 * <li>owningRole != null</li>
	 * <li>owningBizUnit != null</li>
	 * </ul>
	 * 
	 * @return
	 */
	public boolean isActive() {
		return !isDisabled() && (owningRole != null && owningBizUnit != null);
	}
	
	/**
	 * @param owningRole
	 */
	protected void setOwningRole(Role owningRole) {
		this.owningRole = owningRole;
	}
	
	/**
	 * @param owningBizUnit
	 */
	protected void setOwningBizUnit(BusinessUnit owningBizUnit) {
		this.owningBizUnit = owningBizUnit;
	}
	
	/**
	 * 加入指定团队
	 * 
	 * @param team
	 */
	protected void joinTeam(Team team) {
		owningTeams.add(team);
	}
	
	/**
	 * 退出指团队
	 * 
	 * @param team
	 */
	protected void exitTeam(Team team) {
		owningTeams.remove(team);
	}
}
