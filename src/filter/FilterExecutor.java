package filter;

/** 
 * @name: FilterExecutor.java
 * @author:  sleepyocean
 * @date: created in 2018年3月25日 下午12:48:24 
 * @version: 1.0 
 * @description: TODO (write your description) 
 */

public class FilterExecutor {
	public static String exectue(IFilter filter,String fromPath,String toPath) {
		return filter.apply(fromPath,toPath);
	}
}
