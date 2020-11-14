package cn.devezhao.bizz.privileges.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.devezhao.bizz.privileges.Permission;

/**
 * BizzPermission
 * 
 * @author <a href="mailto:zhaofang123@gmail.com">Zhao Fangfang</a>
 * @since 0.2, 2010-10-4
 * @version $Id: BizzPermission.java 10 2015-06-08 09:10:43Z zhaoff@wisecrm.com $
 */
public class BizzPermission implements Permission {
	private static final long serialVersionUID = -3736314513059920238L;
	
	// 动作值定义
	
	/** 
	 * 创建 */
	public static final Permission CREATE = new BizzPermission("C", (1), false);
	/** 
	 * 删除 */
	public static final Permission DELETE = new BizzPermission("D", (1 << 1), true);
	/** 
	 * 更新 */
	public static final Permission UPDATE = new BizzPermission("U", (1 << 2), true);
	/** 
	 * 读取 */
	public static final Permission READ   = new BizzPermission("R", (1 << 3), true);
	/** 
	 * 分配 */
	public static final Permission ASSIGN = new BizzPermission("A", (1 << 4), true);
	/** 
	 * 共享 */
	public static final Permission SHARE  = new BizzPermission("S", (1 << 5), true);
	/** 
	 * 无 */
	public static final Permission NONE   = new BizzPermission("N", (0), false);

	/**
	 * 获取指定<tt>masks</tt>值所表示的动作列表
	 * 
	 * @param masks
	 * @return
	 */
	public static Permission[] parse(final int masks) {
		List<Permission> bps = new ArrayList<>(3);
		if ((masks & CREATE.getMask()) != 0) {
			bps.add(CREATE);
		}
		if ((masks & DELETE.getMask()) != 0) {
			bps.add(DELETE);
		}
		if ((masks & UPDATE.getMask()) != 0) {
			bps.add(UPDATE);
		}
		if ((masks & READ.getMask()) != 0) {
			bps.add(READ);
		}
		if ((masks & ASSIGN.getMask()) != 0) {
			bps.add(ASSIGN);
		}
		if ((masks & SHARE.getMask()) != 0) {
			bps.add(SHARE);
		}
		return bps.toArray(new Permission[0]);
	}
	
	// -----------------------------------------------------------------------------------

	private final int mask;
	private final String name;
	private final boolean needGuard;

	/**
	 * @param name
	 * @param mask
	 */
	public BizzPermission(String name, int mask) {
		this(name, mask, Boolean.FALSE);
	}

	/**
	 * @param name
	 * @param mask
	 * @param needGuard
	 */
	public BizzPermission(String name, int mask, boolean needGuard) {
		this.name = name;
		this.mask = mask;
		this.needGuard = needGuard;
	}

	@Override
    public Serializable getIdentity() {
		return getName() + ':' + getMask() + ':' + (isNeedGuard() ? 1 : 0);
	}

	@Override
    public int getMask() {
		return mask;
	}

	@Override
    public String getName() {
		return name;
	}

	/**
	 * 操作是否需要目标用户，如用户A在删除B的记录时
	 * 
	 * @return
	 */
	public boolean isNeedGuard() {
		return needGuard;
	}
	
	@Override
	public String toString() {
		return getIdentity() + ":BizzPermission@" + super.hashCode();
	}
}
