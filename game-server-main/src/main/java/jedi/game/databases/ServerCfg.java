package jedi.game.databases;

public class ServerCfg extends GameObject{
    public ServerCfg(int dbid){
        this.dbid = dbid;
        setDbid(dbid);
    }

    @Override
    protected String getDaoNamePrefix() {
        return "server_cfg";
    }
}
