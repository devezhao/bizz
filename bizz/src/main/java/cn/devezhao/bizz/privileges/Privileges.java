package cn.devezhao.bizz.privileges;

import java.io.Serializable;
import cn.devezhao.bizz.privileges.impl.BizzDepthEntry;

/**
 * 权限定义
 * 
 * @author <a href="mailto:zhaofang123@gmail.com">Zhao Fangfang</a>
 * @since 0.2, 2010-10-4
 * @version $Id: Privileges.java 10 2015-06-08 09:10:43Z zhaoff@wisecrm.com $
 */
public interface Privileges extends Serializable {

	/**
	 * 最高权限定义 */
	final Privileges ROOT = new Privileges() {
		private static final long serialVersionUID = 4001796962013737440L;

		public boolean allowed(Permission action) {
			return true;
		}
		public boolean allowed(Permission action, Serializable targetGuard) {
			return true;
		}
		public DepthEntry superlative(Permission action) {
			return BizzDepthEntry.GLOBAL;
		}
	};

	// -----------------------------------------------------------------------------------

	/**
	 * 是否允许指定动作
	 * 
	 * @param action
	 * @return
	 */
	boolean allowed(Permission action);

	/**
	 * 是否允许对指定成员(Guard)的指定动作
	 * 
	 * @param action
	 * @param targetGuard
	 * @return
	 */
	boolean allowed(Permission action, Serializable targetGuard);

	/**
	 * 获取指定动作的最高操作深度
	 * 
	 * @param action
	 * @return
	 */
	DepthEntry superlative(Permission action);
}
