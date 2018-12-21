package xyz.yazhe.yazheweb.service.domain.base;

/**
 * page helper的参数类
 * @author Yazhe
 * Created at 15:43 2018/12/21
 */
public class PageHelperParam {

	private int pageSize;

	private int pageNum;

	private String orderBy;

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
}
