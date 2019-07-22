package co.kensure.mem;

import org.apache.commons.lang3.StringUtils;

/**
 * ks 工具类
 * 
 * @author fankd created on 2017-5-8
 */
public class StringKSUtils {

	/**
	 * 首字母转小写
	 * 
	 * @param s
	 * @return
	 */
	public static String toLowerCaseFirstOne(String s) {
		if (StringUtils.isBlank(s)) {
			return null;
		}
		if (Character.isLowerCase(s.charAt(0)))
			return s;
		else
			return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
	}

	/**
	 * 信息脱敏，对于小于6位的数字，进行全部脱敏，大于6位的信息，只显示前三位和后三位
	 * 
	 * @param s
	 * @return
	 */
	public static String doMed(String s) {
		if (StringUtils.isBlank(s)) {
			return null;
		}
		if (s.length() <= 6)
			return "*****";
		else {
			String i1 = s.substring(0, 3);
			String i2 = s.substring(s.length() - 4, s.length() - 1);
			int size = s.length() - 6;
			String str = i1;
			for (int i = 0; i < size; i++) {
				str += "*";
			}
			str += i2;
			return str;
		}
	}

	/**
	 * 信息脱敏，偶数位转为*
	 * 
	 * @param s
	 * @return
	 */
	public static String doMedDouble(String s) {
		if (StringUtils.isBlank(s)) {
			return null;
		}
		char[] ss = s.toCharArray();
		String str = "";
		for (int i = 0; ss.length > i; i++) {
			char v = ss[i];
			if (i % 2 == 0) {
				str += v;
			} else {
				str += "*";
			}
		}
		return str;
	}
	
	/**
     * 6位补0
     * @param code
     * @return
     */
	public static String autoGenericCode6(String code) {
    	int size = code.length()-6;
    	if(size<0){
    		String result = "000000"+ code;
    		result = result.substring(code.length());
    		return  result;
    	}else{
    		return code;
    	}
    }
	
	/**
     * 去除回车和换行符
     * @param code
     * @return
     */
	public static String replacern(String code) {
		if(StringUtils.isNotBlank(code)){
			code = code.replaceAll("\n","");
		}
		return code;
    }

}
