package co.kensure.mem;

public class PageInfo {

	/**
	 * 页数
	 */
	private int pageNo = 1;
	/**
	 * 每页个数
	 */
	private int pageSize = 20;

	public PageInfo() {

	}

	public PageInfo(int pageNo, int pageSize) {
		setPageNo(pageNo);
		setPageSize(pageSize);
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageOffset() {
		return (pageNo - 1) * pageSize;
	}

	public void setPage(int page) {
		this.pageNo = page;
	}

	public void setRows(int rows) {
		this.pageSize = rows;
	}

}
