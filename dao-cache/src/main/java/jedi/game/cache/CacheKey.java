package jedi.game.cache;

import java.util.Arrays;
import java.util.Objects;

/**
 * key, 里面是一组完整的keys
 * 
 * @author @cc
 *
 */
public class CacheKey {
	private final Object[] key;
	private int tableType;
	
	private CacheTable table;
	private String tableName;
	private int hash;

	public CacheKey(String tableName, int tableType, Object[] key) {
		this.key = key;
		this.tableType = tableType;
		this.tableName = tableName;
		this.table = new CacheTable(tableType, tableName);
	}
	
	public CacheKey(CacheTable table, Object[] key) {
		this.key = key;
		this.tableType = table.getTableType();
		this.tableName = table.getTableName();
		this.table = table;
	}

	public Object[] getKey() {
		return key;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj instanceof CacheKey) {
			if(this.tableType != ((CacheKey) obj).getTableType() ||
					!this.tableName.equals(((CacheKey) obj).getTableName()))
				return false;
			return Objects.deepEquals(this.key, ((CacheKey) obj).key);
		}
		return false;
	}

	@Override
	public int hashCode() {
		int h = hash;
		if (h == 0 && key.length > 0) {
			h =(tableType +tableName).hashCode();
			h += Arrays.hashCode(key);
			hash = h;
		}
		return h;
	}

	public int getTableType() {
		return tableType;
	}

	public void setTableType(int tableType) {
		this.tableType = tableType;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	//获得表全名 库类型_表明
	public String getAllTableName()
	{
		return String.format("%d_%s",tableType,tableName);
	}

	@Override
	public String toString() {
		return "CacheKey [key=" + Arrays.toString(key) + ", tableType=" + tableType + ", tableName=" + tableName
				+ ", hash=" + hash + "]";
	}

	public CacheTable getTable() {
		return table;
	}

}
