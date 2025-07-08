package jedi.game.databases;


import jedi.game.db.DBInterface;
import jedi.game.db.DatabaseAccesser;
import jedi.game.exception.DaoException;

public enum GameDatabaseAccesser {
	SERVER_CFG(new DatabaseAccesser() {
		@Override
		public DBInterface getDbByUniqueKey(Object uniqueKey) throws DaoException {
			DBInterface db = DatabasesManager.INSTANCE.getServerCfgDataBases();
			if (db == null) {
				throw new DaoException("can't get map-db");
			}
			return db;
		}

		@Override
		public Object getUniqueKey(Object[] key) throws DaoException {
			return key;
		}
	}
	);

	DatabaseAccesser dbAccesser;
	private GameDatabaseAccesser(DatabaseAccesser dbAccesser) {
		this.dbAccesser = dbAccesser;
	}

	public DatabaseAccesser getAccesser() {
		return dbAccesser;
	}
}
