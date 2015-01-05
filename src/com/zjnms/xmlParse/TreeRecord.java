package com.zjnms.xmlParse;

import java.util.ArrayList; //import java.util.HashMap;
import java.util.List;

//import java.sql.Connection;
//import java.sql.ResultSet;
//import org.dom4j.Element;

/**
 * 表示 树结构 的数据记录集合
 * 
 * @author llj
 * 
 */
public class TreeRecord {
	// 保存树中一条记录的所有字段
	// private HashMap<String, String> map = new HashMap<String, String>();
	private Record root;
	private boolean isVirtualRoot = false;
	// 值类型
	// public String valueType = "string";
	// 当前记录的所有直接子记录
	private List<TreeRecord> childs;

	private GroupRecordIterator iter;

	// private boolean hasParent = false;

	// 该标签的父标签下标
	// public int pIndex = -1;
	// 记录是否已被访问
	// public boolean hasVisit = false;

	// 标签是否是一个父标签
	// public boolean isParent = false;
	// }
	public TreeRecord(Record root, boolean isVirtualRoot) {
		this.root = root;
		this.isVirtualRoot = isVirtualRoot;
	}

	public TreeRecord() {

	}

	/**
	 * 判断树是否是一个虚根树
	 * 
	 * @return
	 */
	public boolean isVirutalRootTree() {
		return isVirtualRoot;
	}

	public void setRoot(Record root) {
		if (!this.isEmptyTree())
			return;
		this.root = root;
	}

	/**
	 * 判断是否是空树（不含记录）
	 * 
	 * @return
	 */
	public boolean isEmptyTree() {
		if (this.root == null)
			return true;
		return false;
	}

	/**
	 * 获取树的根记录
	 * 
	 * @return
	 */
	public Record getRoot() {
		return this.root;
	}

	/**
	 * 获取树记录的组记录迭代器，该迭代器是唯一实例，保留了树记录的当前迭代位置
	 * 
	 * @return
	 */
	public GroupRecordIterator getGroupRecordIterator() {
		if (this.isEmptyTree())
			return null;
		if (this.isVirtualRoot && this.childs.size() == 0) {
			System.out.println("树记录的根为虚根时，该树必须有子记录");
			return null;
		}
		if (iter == null)
			iter = new GroupRecordIterator(this);
		return iter;
	}

	// public boolean isLeafRecord() {
	// if (this.childs == null || this.childs.size() == 0)
	// return true;
	// return false;
	// }
	/**
	 * 在树的根记录下增加一个子树
	 */
	public TreeRecord addChildTreeInRoot(TreeRecord child) {
		if (this.childs == null)
			this.childs = new ArrayList<TreeRecord>();
		this.childs.add(child);
		// child.hasParent = true;
		return child;
	}

	/**
	 * 获取指定层指定下标位置的记录的字段值
	 * 
	 * @param level
	 *            记录所在层 =1,2,3,..
	 * @param index
	 *            在某一层中，记录的下标位置 =0,1,2,3,..
	 * @param fieldname
	 *            记录中指定的字段名
	 * @return
	 */
	public String getRecordFieldValue(int level, int index, String fieldname) {
		GroupRecordIterator iter = new GroupRecordIterator(this);
		for (int i = 1; i < level; ++i)
			iter.nextGroup();
		return iter.getCurrRecords().get(index).getRecordFieldValue(fieldname);
	}

	/**
	 * 在树的根记录下增加 一片叶子
	 * <P>
	 * 叶子是只有一条记录的树
	 * 
	 * @param child
	 *            叶子的记录
	 * @return 新增的叶子
	 */
	public TreeRecord addLeafInRoot(Record child) {
		if (child == null)
			return this;
		TreeRecord tree = new TreeRecord(child, false);
		return this.addChildTreeInRoot(tree);
	}

	/**
	 * 获取根记录下的直接子树的数量
	 * 
	 * @return
	 */
	public int getChildTreeAmount() {
		if (this.childs == null)
			return 0;
		return this.childs.size();
	}

	/**
	 * 获取根记录下的直接子树集合
	 * 
	 * @return
	 */
	public List<TreeRecord> getChildTrees() {
		if (this.childs == null)
			this.childs = new ArrayList<TreeRecord>();
		return this.childs;
	}

	/**
	 * 获取 树记录 的所有 叶子
	 * 
	 * @return
	 */
	public List<TreeRecord> getLeafs() {
		GroupRecordIterator iter = new GroupRecordIterator(this);
		while (iter.hasNext())
			iter.nextGroup();
		return iter.getCurrTrees();
	}

	/**
	 * 从 树记录 中检索 组记录
	 * 
	 * @param keys
	 * @return
	 */
	public List<Record> getGroupRecord(List<String> keys) {
		List<Record> rcds = new ArrayList<Record>();
		getGroup(this, keys, rcds);
		return rcds;
	}

	private void getGroup(TreeRecord tree, List<String> keys, List<Record> rcds) {

		if (tree.root.isKeys(keys)) {
			rcds.add(tree.root);
			return;
		} else {
			List<TreeRecord> childTrees = tree.getChildTrees();
			for (int i = 0; i < childTrees.size(); ++i) {
				this.getGroup(childTrees.get(i), keys, rcds);
			}
		}
	}

	/**
	 * 写记录（从叶子生长出新的叶子）
	 * 
	 * @param elmtModel
	 *            元素描述模板的指向对象
	 * @param elmts
	 *            包含记录的元素
	 */
	public void writeRecords(ElmtModel elmtModel, List<MyElement> elmts) {//revise8
		if (!elmtModel.hasOutputAccompanyModel())
			return;
		if (this.root == null) {
			if (elmts.size() == 1)
				this.setRoot(this.createRecord(elmtModel, elmts.get(0)));
			else
				System.out.println("树记录的根节点只能有一条记录");
		} else {
			List<TreeRecord> trees = this.getLeafs();
			int leafIndex = 0;
			TreeRecord currLeaf = trees.get(leafIndex);
			// for (int i = 0; i < elmts.size(); ++i) {
			// if (i != 0 && !elmts.get(i).isSameGroup(elmts.get(i - 1))) {
			// ++leafIndex;
			// if (leafIndex >= trees.size())
			// throw new Exception("无法写树记录，记录的种类数超过了承载他们的叶子节点的数量");
			// else
			// currLeaf = trees.get(leafIndex);
			// }
			// if (elmtModel.isRepeat()) {
			// List<String> names = elmtModel.getOutputKeyNames();
			// for (int j = 0; j < names.size(); ++j) {
			// String name = names.get(j);
			// String value = elmts.get(i).getValue(name);
			// if (value != null)
			// currLeaf.getRoot().addRecordField(name, value);
			// }
			// //currLeaf = trees.get(++leafIndex);
			// } else {
			// Record rcd = this.createRecord(elmtModel, elmts.get(i));
			// if (rcd == null)
			// return;
			// else {
			// currLeaf.addLeafInRoot(rcd);
			// //currLeaf = trees.get(++leafIndex);
			// }
			// }
			// }

			boolean needCreateRecord = false;
			if (!elmtModel.isRepeat())
				needCreateRecord = true;

			for (int j = 0; j < elmts.size(); ++j) {
				MyElement iterElmt = elmts.get(j);
				if (needCreateRecord) {
					Record rcd = this.createRecord(elmtModel, iterElmt);
					currLeaf.addLeafInRoot(rcd);
				} else {
					List<String> names = elmtModel.getOutputKeyNames();
					for (int z = 0; z < names.size(); ++z) {
						String name = names.get(z);
						String value = iterElmt.getValue(name);
						if (value != null)
							currLeaf.getRoot().addRecordField(name, value);
					}
				}
				if (iterElmt.getName().equals("nameValue")
						&& iterElmt.getAttributeValue("sitePlatId") != null)
					System.out.print("");

				if (leafIndex < trees.size() - 1 && j < elmts.size() - 1) {
					// 当元素的父元素或祖父元素改变，更新当前叶子节点
					MyElement nextElmt = elmts.get(j + 1);
					// 元素数<=叶子数，看父元素
					if (elmts.size() <= trees.size()) {
						if (!MyElement.equalParent(nextElmt, iterElmt))
							currLeaf = trees.get(++leafIndex);
					} else {
					// 元素数>叶子数，看祖父元素
						if (!MyElement.equalGrandParent(nextElmt, iterElmt))
							currLeaf = trees.get(++leafIndex);
					}
				}
			}

		}
	}

	/**
	 * 根据包含记录的元素产生一条记录
	 * 
	 * @param elmtModel
	 *            描述元素的指向对象
	 * @param elmt
	 *            包含记录的元素
	 * @return 产生的记录
	 */
	private Record createRecord(ElmtModel elmtModel, MyElement elmt) {
		List<EntityModel> attrs = elmtModel.getAccompanyModels();
		if (attrs.size() == 0)
			return null;
		Record rcd = new Record();
		for (int j = 0; j < attrs.size(); ++j) {
			EntityModel entity = attrs.get(j);
			if (entity instanceof AttrModel) {
				AttrModel attr = (AttrModel) entity;
				if (attr.hasOutValue()) {
					//revise7
					String key = attr.getOutputKey();
					String value = elmt.getValue(key);
					// System.out.println("不存在标记名为 " + key + " 的值");
					if (value != null) {
						rcd.addRecordField(key, value);
					} else {
						System.out.print("");
					}
				}
			}
		}
		return rcd;
	}

	// public int[] grouping(List<MyElement> elmts) {
	//
	// }
}
