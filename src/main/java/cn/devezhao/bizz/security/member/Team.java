package cn.devezhao.bizz.security.member;

import java.io.Serializable;
import java.security.Principal;

/**
 * 团队
 * 
 * @author <a href="mailto:zhaofang123@gmail.com">Zhao Fangfang</a>
 * @since 0.2, 2010-10-4
 * @version $Id: Team.java 10 2015-06-08 09:10:43Z zhaofang123@gmail.com $
 */
public class Team extends MemberGroup {
	private static final long serialVersionUID = 1557485019536221272L;

	/**
	 * @param identity
	 * @param name
	 * @param disabled
	 */
	public Team(Serializable identity, String name, boolean disabled) {
		super(identity, name, disabled);
	}

	@Override
	public boolean addMember(Principal user) {
		if (!super.addMember(user)) {
			return false;
		}
		((User) user).joinTeam(this);
		return true;
	}

	@Override
	public boolean removeMember(Principal user) {
		if (!super.removeMember(user)) {
			return false;
		}
		((User) user).exitTeam(this);
		return true;
	}
}
