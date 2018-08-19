package xyz.yazhe.yazheweb.util.tree;

import java.util.List;

/**
 * 树在数据库操作的接口
 * @author Yazhe
 * Created at 11:23 2018/8/1
 */
public interface TreeDao {

	/**
	 * 插入节点集合
	 * @param nodeList 节点集
	 * @return
	 */
	int insertBasicTreeNodeList(List<? extends BasicTreeNode> nodeList);

	/**
	 * 根据id查询其所有子节点id
	 * @param id locationId
	 * @return
	 */
	List<String> getChildrenIdById(String id);

	/**
	 * 根据id删除节点
	 * @return
	 */
	int deleteNodeById(String id);

	/**
	 * 查询所有的地点
	 * @return
	 */
	List<BasicTreeNode> getNodeList();
}
