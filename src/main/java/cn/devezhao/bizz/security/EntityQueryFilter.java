package cn.devezhao.bizz.security;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Properties;

import cn.devezhao.bizz.BizzException;
import cn.devezhao.bizz.privileges.DepthEntry;
import cn.devezhao.bizz.privileges.Privileges;
import cn.devezhao.bizz.privileges.impl.BizzDepthEntry;
import cn.devezhao.bizz.privileges.impl.BizzPermission;
import cn.devezhao.bizz.security.member.BusinessUnit;
import cn.devezhao.bizz.security.member.Role;
import cn.devezhao.bizz.security.member.User;

/**
 * EntityQueryFilter
 * 
 * @author <a href="mailto:zhaofang123@gmail.com">Zhao Fangfang</a>
 * @since 0.2, 2010-10-4
 * @version $Id: EntityQueryFilter.java 10 2015-06-08 09:10:43Z zhaoff@wisecrm.com $
 */
public class EntityQueryFilter implements QueryFilter {

	private static String F_OwnUser;
	private static String F_OwnBizUnit;
	private static String V_RootUser;
	private static String V_RootRole;
	static {
		String cfgFile = "bizz.properties";
		InputStream in = EntityQueryFilter.class.getClassLoader().getResourceAsStream(cfgFile);
		if (in == null) {
			in = EntityQueryFilter.class.getClassLoader().getResourceAsStream("cn/devezhao/bizz/bizz-default.properties");
		}
		Properties props = new Properties();
		try {
			props.load(in);
		} catch (IOException e) {
			throw new BizzException("Could't load config file!", e);
		}
		
		F_OwnUser = props.getProperty("field.own-user");
		F_OwnBizUnit = props.getProperty("field.own-biz-unit");
		V_RootUser = props.getProperty("value.root-user");
		V_RootRole = props.getProperty("value.root-role");
	}
	
	// -----------------------------------------------------------------------------------
	
	/**
	 * 总是拒绝 */
	public static final QueryFilter DENIED = entity -> "( 1 = 0 )";
	
	/**
	 * 总是允许 */
	public static final QueryFilter ALLOWED = entity -> "( 1 = 1 )";
	
	// -----------------------------------------------------------------------------------
	
	static final String FV_FORMAT = "( {0} = ''{1}'' )";
	
	protected final User user;
	protected final Role role;
	
	/**
	 * @param user
	 */
	public EntityQueryFilter(User user) {
		this(user, user.getOwningRole());
	}
	
	/**
	 * @param user
	 * @param role
	 */
	public EntityQueryFilter(User user, Role role) {
		this.user = user;
		this.role = role;
	}
	
	@Override
    public String evaluate(int entity) {
		if (role.getIdentity().toString().equals(V_RootRole)
				|| user.getIdentity().toString().equals(V_RootUser)) {
			return ALLOWED.evaluate(entity);
		}
		
		Privileges p;
		try {
			p = role.getPrivileges(entity);
		} catch (AccessDeniedException denied) {
			return DENIED.evaluate(entity);
		}
		
		DepthEntry de = p.superlative(BizzPermission.READ);
		if (de == BizzDepthEntry.GLOBAL) {
			return ALLOWED.evaluate(entity);
		}
		
		StringBuffer filter = new StringBuffer();
		
		if (de == BizzDepthEntry.PRIVATE) {
			filter.append(MessageFormat.format(FV_FORMAT, F_OwnUser, user.getIdentity()));
			return evaluate(entity, filter);
		} else if (de == BizzDepthEntry.LOCAL) {
			filter.append(MessageFormat.format(FV_FORMAT, F_OwnBizUnit, user.getOwningBizUnit().getIdentity()));
			return evaluate(entity, filter);
		} else if (de == BizzDepthEntry.DEEPDOWN) {
			BusinessUnit biz = user.getOwningBizUnit();
			filter.append("( ");
			filter.append(MessageFormat.format(FV_FORMAT, F_OwnBizUnit, biz.getIdentity()));
			
			for (BusinessUnit cBiz : biz.getChildren()) {
				filter.append(" OR ").append(MessageFormat.format(FV_FORMAT, F_OwnBizUnit, cBiz.getIdentity()));
			}
			return evaluate(entity, filter.append(" )"));
		}
		
		return DENIED.evaluate(entity);
	}
	
	/**
	 * 过滤条件的二次处理
	 * 
	 * @param entity
	 * @param filtered
	 * @return
	 */
	protected String evaluate(int entity, StringBuffer filtered) {
		return filtered.toString();
	}
}
