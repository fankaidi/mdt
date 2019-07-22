/*
 * 文件名称: MapUtils.java
 * 版权信息: Copyright 2015-2017 jingshu technology Co., LTD. All right reserved.
 * ----------------------------------------------------------------------------------------------
 * 修改历史:
 * ----------------------------------------------------------------------------------------------
 * 修改原因: 新增
 * 修改人员: fankd
 * 修改日期: 2017-5-8
 * 修改内容: 
 */
package co.kensure.mem;

import java.util.Collection;


/**
 * 集合工具类
 * 
 * @author fankd
 * @since SHK Framework 1.0
 */
public final class CollectionUtils {
    
	
    /**
     * 判断集合是否为空
     * 
     * @param coll 集合类型, 包括List, Set等
     * @return true: 集合空; false: 集合非空
     */
    public static boolean isEmpty(Collection<?> coll) {
        return coll == null || coll.isEmpty();
    }

    /**
     * 判断集合是否非空
     * 
     * @param coll 集合类型, 包括List, Set等
     * @return true: 集合非空; false: 集合空
     */
    public static boolean isNotEmpty(Collection<?> coll) {
        return !isEmpty(coll);
    }

    

    /**
     * 集合空的时候返回0，否则返回长度
     * 
     * @param coll 集合类型, 包括List, Set等
     * @return int 
     */
    public static int getSize(Collection<?> coll) {
    	if(isEmpty(coll)){
    		return 0;
    	}
        return coll.size();
    }
    
   
}
