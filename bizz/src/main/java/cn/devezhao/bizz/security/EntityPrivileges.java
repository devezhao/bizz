package cn.devezhao.bizz.security;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import cn.devezhao.bizz.privileges.DepthEntry;
import cn.devezhao.bizz.privileges.Permission;
import cn.devezhao.bizz.privileges.Privileges;
import cn.devezhao.bizz.privileges.impl.BizzDepthEntry;
import cn.devezhao.bizz.privileges.impl.BizzPermission;

/**
 * 实体权限
 * 
 * @author <a href="mailto:zhaofang123@gmail.com">Zhao Fangfang</a>
 * @since 0.2, 2010-10-4
 * @version $Id: EntityPrivileges.java 10 2015-06-08 09:10:43Z zhaoff@wisecrm.com $
 */
public class EntityPrivileges implements Privileges {
	private static final long serialVersionUID = -8141823128069571526L;

	private final Integer entity;
	private final String definition;
	
	private final Map<DepthEntry, Permission[]> dePermissions = new HashMap<DepthEntry, Permission[]>();
	private final Set<Permission> allPermissions = new HashSet<Permission>();
	
	/**
	 * @param entity
	 * @param definition
	 * 			权限定义，各层级之间使用 <tt>,</tt> 分割，层级与权限值之间使用 <tt>:</tt> 分割。
	 * 			<pre>
	 * 			如 <i>1:15,2:15,3:8,4:0</i> 其意为：
	 * 			<i>1:15</i> 其中 <i>1</i> 表示私人，<i>15</i> 是动作值的累加（本例中的动作是 15 = 1+2+4+8），即对应 <i>增 + 删 + 改 + 查</i>
	 * 			以此类推，具体值定义参考类 {@link BizzPermission}, {@link BizzDepthEntry}
	 * 			</pre>
	 */
	public EntityPrivileges(Integer entity, String definition) {
		this.entity = entity;
		this.definition = definition;
		
		String depthValues[] = definition.split(",");
		for (String dv : depthValues) {
			String dpVal[] = dv.split(":");
			
			Permission[] perms = BizzPermission.parse(Integer.valueOf(dpVal[1]));
			for (Permission p : perms) {
				allPermissions.add(p);
			}
			dePermissions.put(
					BizzDepthEntry.parse(Integer.valueOf(dpVal[0])), perms);
		}
	}
	
	@Override
	public Serializable getIdentity() {
		return getEntity();
	}
	
	@Override
	public boolean allowed(Permission action) {
		return allPermissions.contains(action);
	}

	/**
	 * Always throw UnsupportedOperationException
	 */
	@Override
	public boolean allowed(Permission action, Serializable targetGuard) {
		throw new UnsupportedOperationException();
	}

	@Override
	public DepthEntry superlative(Permission action) {
		Set<DepthEntry> set = new HashSet<DepthEntry>(dePermissions.size());
		for (Map.Entry<DepthEntry, Permission[]> e : dePermissions.entrySet()) {
			for (Permission p : e.getValue()) {
				if (action.equals(p)) {
					set.add(e.getKey());
					break;
				}
			}
		}
		
		if (set.isEmpty()) {
			return BizzDepthEntry.NONE;
		}
		
		DepthEntry sup = BizzDepthEntry.NONE;
		for (DepthEntry de : set) {
			if (de.getMask() > sup.getMask()) {
				sup = de;
			}
			
			if (BizzDepthEntry.GLOBAL.equals(sup)) {
				return BizzDepthEntry.GLOBAL;
			}
		}
		return sup;
	}
	
	/**
	 * 获取此权限所属的实体
	 * 
	 * @return
	 */
	public Integer getEntity() {
		return entity;
	}
	
	/**
	 * 获取表示此权限的原始字符串
	 * 
	 * @return
	 */
	public String getDefinition() {
		return definition;
	}
}
