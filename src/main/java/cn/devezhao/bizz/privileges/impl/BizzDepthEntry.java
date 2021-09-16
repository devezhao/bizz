package cn.devezhao.bizz.privileges.impl;

import cn.devezhao.bizz.privileges.DepthEntry;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * BizzDepthEntry
 * 
 * @author <a href="mailto:zhaofang123@gmail.com">Zhao Fangfang</a>
 * @since 0.2, 2010-10-4
 * @version $Id: BizzDepthEntry.java 10 2015-06-08 09:10:43Z zhaofang123@gmail.com $
 */
public class BizzDepthEntry implements DepthEntry {
	private static final long serialVersionUID = 7612024724703543451L;
	
	// 权限深度

	/**
	 * 全局 */
	public static final DepthEntry GLOBAL 	= new BizzDepthEntry("G", 4, null);
	/**
	 * 本部门及其所有下级部门 */
	public static final DepthEntry DEEPDOWN = new BizzDepthEntry("D", 3, GLOBAL);
	/**
	 * 本部门 */
	public static final DepthEntry LOCAL 	= new BizzDepthEntry("L", 2, DEEPDOWN);
	/**
	 * 本人私有 */
	public static final DepthEntry PRIVATE 	= new BizzDepthEntry("P", 1, LOCAL);
	/**
	 * 无 */
	public static final DepthEntry NONE 	= new BizzDepthEntry("N", 0, PRIVATE);

	private static final Map<Integer, DepthEntry> DEPTH_ENTRIES_BY_MASK = new HashMap<>(5);
	static {
		DEPTH_ENTRIES_BY_MASK.put(GLOBAL.getMask(), GLOBAL);
		DEPTH_ENTRIES_BY_MASK.put(DEEPDOWN.getMask(), DEEPDOWN);
		DEPTH_ENTRIES_BY_MASK.put(LOCAL.getMask(), LOCAL);
		DEPTH_ENTRIES_BY_MASK.put(PRIVATE.getMask(), PRIVATE);
		DEPTH_ENTRIES_BY_MASK.put(NONE.getMask(), NONE);
	}

	/**
	 * @param mask
	 * @return
	 * @throws IllegalArgumentException If illegal mask value
	 */
	public static DepthEntry parse(final int mask) throws IllegalArgumentException {
		DepthEntry de = DEPTH_ENTRIES_BY_MASK.get(mask);
		if (de == null) {
			throw new IllegalArgumentException("No DepthEntry defined: " + mask);
		}
		return de;
	}

	// -----------------------------------------------------------------------------------

	private final String name;
	private final int mask;

	private DepthEntry next;

	/**
	 * @param name
	 * @param mask
	 * @param previous
	 */
	public BizzDepthEntry(String name, int mask, DepthEntry previous) {
		this.name = name;
		this.mask = mask;

		if (previous != null) {
			BizzDepthEntry bde = (BizzDepthEntry) previous;
			if (bde.next != null) {
				throw new IllegalArgumentException("Cannot re-defined next DepthEntry for BizzDepthEnter!");
			}
			bde.next = this;
		}
	}

	@Override
    public Serializable getIdentity() {
		return getName() + ':' + getMask();
	}

	@Override
    public int getMask() {
		return mask;
	}

	@Override
    public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return getIdentity() + ":BizzDepthEntry@" + super.hashCode();
	}
}
