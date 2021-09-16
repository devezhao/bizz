package cn.devezhao.bizz.security;


/**
 * 查询过滤器
 * 
 * @author <a href="mailto:zhaofang123@gmail.com">Zhao Fangfang</a>
 * @since 0.2, 2010-10-4
 * @version $Id: QueryFilter.java 10 2015-06-08 09:10:43Z zhaofang123@gmail.com $
 */
public interface QueryFilter {
	
	/**
	 * 构造查询条件
	 * 
	 * @param entity
	 * @return
	 */
	String evaluate(int entity);
}
