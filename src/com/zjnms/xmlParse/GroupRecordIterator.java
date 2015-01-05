package com.zjnms.xmlParse;

import java.util.ArrayList;
import java.util.List;

/**
 * 表示 组结构（同一实体的多条记录） 数据记录的迭代器 数据表中的多条数据或同一种元素的多个元素实体的属性集合都是一个组记录
 * 
 * @author llj
 * 
 */
public class GroupRecordIterator {
	private TreeRecord tree;
	private List<TreeRecord> currTrees = new ArrayList<TreeRecord>();

	// private boolean hasNext = true;
	public GroupRecordIterator(TreeRecord tree) {
		this.tree = tree;
		
		currTrees.add(this.tree);
		if (tree.isVirutalRootTree() && this.hasNext()) {
			this.nextGroup();
		}
	}

	/**
	 * 判断树中是否还有下一组记录
	 * 
	 * @return
	 */
	public boolean hasNext() {
		for (int i = 0; i < currTrees.size(); ++i) {
			TreeRecord tree = currTrees.get(i);
			List<TreeRecord> branchChilds = tree.getChildTrees();
			if (branchChilds != null && branchChilds.size() != 0) {
				return true;
			}
		}// TODO 所有分支都没有子树时失败
		return false;
	}

	/**
	 * 迭进到下一组
	 * 
	 * @return
	 */
	public boolean nextGroup() {
		if(!readNextChildTrees()){
			this.currTrees = new ArrayList<TreeRecord>();
			return false;
		}
		else
			return true;
	}

	/**
	 * 获取当前组记录
	 * 
	 * @return
	 */
	public List<Record> getCurrRecords() {
		List<Record> group = new ArrayList<Record>();
		for (int i = 0; i < currTrees.size(); ++i) {
			group.add(currTrees.get(i).getRoot());
		}
		return group;
	}

	/**
	 * 获取以当前 组记录 为根的 树集
	 * 
	 * @return
	 */
	public List<TreeRecord> getCurrTrees() {
		return currTrees;
	}

	/**
	 * 下一级子树
	 */
	private boolean readNextChildTrees() {
		if (!this.hasNext())
			return false;
		List<TreeRecord> childs = new ArrayList<TreeRecord>();
		for (int i = 0; i < currTrees.size(); ++i) {
			TreeRecord tree = currTrees.get(i);
			List<TreeRecord> branchChilds = tree.getChildTrees();
			if (branchChilds == null || branchChilds.size() == 0) {
				continue;
			}
			for (int j = 0; j < branchChilds.size(); ++j)
				childs.add(branchChilds.get(j));
		}
		currTrees.clear();
		currTrees = childs;
		return true;
	}
}
