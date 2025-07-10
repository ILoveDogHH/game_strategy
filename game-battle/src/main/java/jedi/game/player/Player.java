package jedi.game.player;

import java.util.Arrays;
import java.util.List;

// 玩家类，表示游戏中的玩家角色
public class Player {
    // 玩家名称
    public String name;
    // 玩家前排士兵
    public Soldier frontSoldier;
    // 玩家后排士兵
    public Soldier backSoldier;
    // 玩家将领
    public Hero general;

    // 构造方法，用于初始化玩家的属性
    public Player(String name, Soldier frontSoldier, Soldier backSoldier, Hero general) {
        this.name = name;
        this.frontSoldier = frontSoldier;
        this.backSoldier = backSoldier;
        this.general = general;
    }

    // 获取玩家的所有士兵（前排和后排）
    public List<IEntity> getAllSoldiers() {
        return Arrays.asList(frontSoldier, backSoldier);
    }


    // 获取玩家的所有士兵（前排和后排）
    public List<IEntity> getAllEnity() {
        return Arrays.asList(frontSoldier, backSoldier, general);
    }

    // 判断玩家的所有士兵是否全部阵亡
    public boolean isAllDead() {
        return !frontSoldier.isAlive() && !backSoldier.isAlive();
    }

    public int getCurrentHp(){
        return frontSoldier.getCurrentHp() + backSoldier.getCurrentHp();
    }
}