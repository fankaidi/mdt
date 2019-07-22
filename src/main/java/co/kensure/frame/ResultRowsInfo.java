package co.kensure.frame;

import java.util.Collection;

import co.kensure.mem.CollectionUtils;

/**
 * <p>
 * 返回成功列表消息体，只返回成功列表
 * 
 * ResultRowsInfo： 返回数据集
 * @see ResultRowsInfo
 * @author fankd
 *
 */
public class ResultRowsInfo extends ResultInfo {

	public ResultRowsInfo(Collection<?> rows, int count) {
		super(ResultType.SUCCESS, Const.SELECT_SUCCESS, null, rows, null, count);
	}
	
	public ResultRowsInfo(Collection<?> rows, Long count) {
		super(ResultType.SUCCESS, Const.SELECT_SUCCESS, null, rows, null, count.intValue());
	}
	
	public ResultRowsInfo(Collection<?> rows) {
		super(ResultType.SUCCESS, Const.SELECT_SUCCESS, null, rows, null, CollectionUtils.getSize(rows));
	}
}
