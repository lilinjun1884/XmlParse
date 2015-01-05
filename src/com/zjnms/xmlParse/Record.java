package com.zjnms.xmlParse;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
/**
 * 表示一条记录，记录由字段组成
 * 
 * @author llj
 *
 */
public class Record {
	private HashMap<String, String> map = new HashMap<String, String>();

	public String getRecordFieldValue(String fieldName) {
		return this.map.get(fieldName);
	}

	public void addRecordField(String fieldName, String fieldValue) {
		this.map.put(fieldName, fieldValue);
	}
	public void deleteRecordField(String fieldName){
		this.map.remove(fieldName);
	}
	public Set<String> getKeys(){
		return this.map.keySet();
	}
	/**
	 * 判断两条记录是否相同
	 * 
	 * @param record
	 * @return
	 */
	public boolean equalValue(Record record) {//revise6
		Set<String> keys = map.keySet();
		for (Iterator<String> iter = keys.iterator(); iter.hasNext();) {
			String key = iter.next();
			String value = record.getRecordFieldValue(key);
			if (value == null)
				return false;
			if (!value.equals(this.getRecordFieldValue(key)))
				return false;
		}
		return true;
	}
	/**
	 * 判断指定的字符串是否是记录中的一个属性的键值
	 * @param key
	 * @return
	 */
	public boolean isKey(String str){
		return map.containsKey(str);
	}
	/**
	 * 判断指定集合是否是键集的子集
	 * 
	 * @param keys
	 * @return
	 */
	public boolean isKeys(List<String> keys) {
//		if (this.map.size() != keys.size())
//			return false;
		for (int i = 0; i < keys.size(); ++i)
			if (!this.map.containsKey(keys.get(i)))
				return false;
		return true;
	}
	public String toString(){
		return this.map.toString();
	}
}
