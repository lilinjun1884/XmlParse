package com.zjnms.xmlParse;

import java.util.List;

/**
 * EntityModel对象的迭代器，迭代访问实例类型为EntityModel的对象
 * 
 * @author llj
 * 
 */
public class EntityModelIterator {
	protected List<EntityModel> coll;
	protected int next_index = -1;
	private boolean hasReadNext = false;

	public EntityModelIterator(List<EntityModel> entities) {
		coll = entities;
	}

	/**
	 * 判断是否存在下一个实体
	 * 
	 * @return
	 */
	public boolean hasNext() {
		readNextIndex();
		hasReadNext = true;
		return this.next_index != -1;
	}

	/**
	 * 获取下一个实体的下标，失败时返回-1
	 */
	protected void readNextIndex() {
		if (hasReadNext)
			return;
		++next_index;
		if (next_index >= coll.size())
			next_index = -1;
	}

	/**
	 * 迭代到下一个实体
	 * 
	 * @return
	 */
	public EntityModel next() {
		if (!hasReadNext)
			readNextIndex();
		else
			hasReadNext = false;
		return coll.get(next_index);
	}
}

/**
 * AttrModel的迭代对象
 * 
 * @author llj
 * 
 */
class AttrModelIterator extends EntityModelIterator {
	// protected AttrModel nextItem = null;

	public AttrModelIterator(List<EntityModel> entities) {
		super(entities);
	}

	protected void readNextIndex() {
		super.readNextIndex();
		if (this.next_index == -1)
			return;
		// 从集合中查找下一个AttrModel，设置下标
		for (int i = this.next_index; i < this.coll.size(); ++i) {
			if (coll.get(i) instanceof AttrModel) {
				this.next_index = i;
				return;
			}
		}
		this.next_index = -1;
	}
}

/**
 * ElmtModel的迭代对象
 * 
 * @author llj
 * 
 */
class ElmtModelIterator extends EntityModelIterator {
	public ElmtModelIterator(List<EntityModel> entities) {
		super(entities);
	}

}

/**
 * SqlparamModel的迭代对象
 * 
 * @author llj
 * 
 */
class SqlparamIterator extends EntityModelIterator {
	public SqlparamIterator(List<EntityModel> entities) {
		super(entities);
	}

	protected void readNextIndex() {
		super.readNextIndex();
		if (this.next_index == -1)
			return;
		// 从集合中查找下一个SqlparamModel，设置下标
		for (int i = this.next_index; i < this.coll.size(); ++i) {
			if (coll.get(i) instanceof SqlparamModel) {
				this.next_index = i;
				return;
			}
		}
		this.next_index = -1;
	}
}

/**
 * SqlInparamModel的迭代对象
 * 
 * @author llj
 * 
 */
class SqlInparamModelIterator extends SqlparamIterator {
	public SqlInparamModelIterator(List<EntityModel> entities) {
		super(entities);
	}

	protected void readNextIndex() {
		super.readNextIndex();
		if (this.next_index == -1)
			return;
		for (int i = this.next_index; i < this.coll.size(); ++i) {
			if (coll.get(i) instanceof SqlInparamModel) {
				this.next_index = i;
				return;
			}
		}
		this.next_index = -1;
	}
}

/**
 * SqlOutparamModel的迭代对象
 * 
 * @author llj
 * 
 */
class SqlOutparamModelIterator extends SqlparamIterator {
	public SqlOutparamModelIterator(List<EntityModel> entities) {
		super(entities);
	}

	protected void readNextIndex() {
		super.readNextIndex();
		if (this.next_index == -1)
			return;
		// // 从集合中查找下一个SqlOutparamModel，设置下标
		for (int i = this.next_index; i < this.coll.size(); ++i) {
			if (coll.get(i) instanceof SqlOutparamModel) {
				this.next_index = i;
				return;
			}
		}
		this.next_index = -1;
	}
}

/**
 * DataFromPrgmAttrModel的迭代对象
 * 
 * @author llj
 * 
 */
class DataFromPrgmAttrModelIterator extends AttrModelIterator {
	public DataFromPrgmAttrModelIterator(List<EntityModel> entities) {
		super(entities);
	}

	protected void readNextIndex() {
		super.readNextIndex();
		if (this.next_index == -1)
			return;
		for (int i = this.next_index; i < this.coll.size(); ++i) {
			EntityModel entity = coll.get(i);
			if (entity instanceof AttrModel) {
				if (((AttrModel) entity).dataFromPrgm()) {
					this.next_index = i;
					return;
				}
			}
		}
		this.next_index = -1;
	}
}
