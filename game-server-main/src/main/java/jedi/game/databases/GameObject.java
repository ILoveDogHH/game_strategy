package jedi.game.databases;

public abstract class GameObject {
	public int dbid;
	public String daoname;

	protected abstract String getDaoNamePrefix();

	public void setDbid(int dbid) {
		this.dbid = dbid;
		this.daoname = String.format("%s_%d", getDaoNamePrefix(), dbid);
	}

	public int getDbid() {
		return dbid;
	}

	public String getDaoName() {
		return daoname;
	}
}
