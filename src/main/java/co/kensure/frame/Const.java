package co.kensure.frame;

/**
 * Const
 *
 * @author fankd
 * @date 2016/11/10
 */
public class Const {

	//程序 根路径
	public static String ROOT_PATH = "";

	public static String ROOT_URL = "";

	public static final String MESSAGE = "message";

	public final static String ENCODING = "utf-8";

	public final static String OPT_SUCCESS = "操作成功";

	public final static String SELECT_SUCCESS = "查询成功";

	public final static String SELECT_FAIL = "查询失败";

	public final static String SAVE_SUCCESS = "保存成功";

	public final static String SAVE_FAIL = "保存失败";

	public final static String DELETE_SUCCESS = "删除成功";

	public final static String DELETE_FAIL = "删除失败";

	public final static String VALIDATE_PASSED = "验证通过";

	public final static String CODE_EXISTED = "编号已存在";

	public final static String ID_IS_NULL = "id不得为空";

	public final static String CODE_IS_NULL = "编号不得为空";

	public final static String STATUS_IS_NULL = "状态不得为空";

	public final static String SORT_IS_NULL = "排序不得为空";

	public final static String CONTENT_IS_NULL = "正文不得为空";

	public final static String START_TIME_IS_NULL = "开始时间不得为空";

	public final static String END_TIME_IS_NULL = "结束时间不得为空";
	
	
	/**
	 * 是否开启定时器
	 * @return
	 */
	public static boolean isStart(){
		return Const.ROOT_PATH.startsWith("/web");
	}
}
