package jedi.game.cache;

public class CacheTable {
    private int tableType;

    private String tableName;


    public CacheTable (int tableType, String tableName){
        this.tableType = tableType;
        this.tableName = tableName;
    }

    @Override
    public int hashCode() {
        return (tableType +tableName).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof CacheTable) {
            return this.tableType == ((CacheTable) obj).getTableType() &&
                    this.tableName.equals(((CacheTable) obj).getTableName());
        }
        return false;
    }

    @Override
    public String toString() {
        return "CacheTable [tableType=" + tableType + ", tableName=" + tableName + "]";
    }


    public int getTableType() {
        return tableType;
    }

    public String getTableName() {
        return tableName;
    }
}
