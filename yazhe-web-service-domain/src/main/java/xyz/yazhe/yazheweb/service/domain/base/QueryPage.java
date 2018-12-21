package xyz.yazhe.yazheweb.service.domain.base;



import xyz.yazhe.yazheweb.service.domain.base.validation.group.DeviceValidatedGroup.QueryDeviceGroup;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

/**
 * 分页查询的参数
 * @author BeFondOfTaro
 * Created at 11:32 2018/5/15
 */
public class QueryPage {

    /**
     * 页码
     */
    @Min(value = 1,message = "最小页数为1",groups = {QueryDeviceGroup.class})
    private Integer pageNum = 1;

    /**
     * 每页记录数量
     */
	@Min(value = 1,message = "每页最小个数为1",groups = {QueryDeviceGroup.class})
    private Integer pageSize = 10;

	@Pattern(regexp = "^[A-Za-z0-9_]+$",message = "排序字段名只能是字母、数字、下划线的组合",groups = {QueryDeviceGroup.class})
	private String orderBy;

	@Pattern(regexp = "asc|desc",message = "排序方向只能是asc,desc两种方向",groups = {QueryDeviceGroup.class})
	private String orderDirection;

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getOrderDirection() {
		return orderDirection;
	}

	public void setOrderDirection(String orderDirection) {
		this.orderDirection = orderDirection;
	}

	/**
	 * 转化成page helper的分页参数对象
	 * @return
	 */
	public PageHelperParam toPageHelperParam(){
		PageHelperParam pageHelperParam = new PageHelperParam();
		pageHelperParam.setPageNum(pageNum);
		pageHelperParam.setPageSize(pageSize);
		if (orderBy != null){
			pageHelperParam.setOrderBy(orderBy + " " + orderDirection);
		}
		return pageHelperParam;
	}

	@Override
	public String toString() {
		return "QueryPage{" +
				"pageNum=" + pageNum +
				", pageSize=" + pageSize +
				", orderBy='" + orderBy + '\'' +
				", orderDirection='" + orderDirection + '\'' +
				'}';
	}
}
