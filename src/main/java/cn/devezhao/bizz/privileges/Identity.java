package cn.devezhao.bizz.privileges;

import java.io.Serializable;

/**
 * Identity
 * 
 * @author <a href="mailto:zhaofang123@gmail.com">Zhao Fangfang</a>
 * @since 0.2, 2010-10-4
 * @version $Id: Identity.java 10 2015-06-08 09:10:43Z zhaofang123@gmail.com $
 */
public interface Identity extends Serializable {

    /**
     * ID
     *
     * @return
     */
	Serializable getIdentity();

    /**
     * 名称
     *
     * @return
     */
	String getName();

    /**
     * 掩码
     *
     * @return
     */
	int getMask();
}
