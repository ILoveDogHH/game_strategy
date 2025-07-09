package jedi.game.servercfg.enity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhanglefeng
 * @since 2025-07-09
 */
@Getter
@Setter
@TableName("cfg_skill")
public class CfgSkill implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "skill_id", type = IdType.AUTO)
    private Integer skill_id;

    /**
     * 星级
     */
    private Integer star;

    /**
     * 技能名字
     */
    private String skill_name;

    /**
     * 技能类型
     */
    private Integer skil_type;

    /**
     * 品质
     */
    private Integer qua;

    /**
     * xx,xx  流派
     */
    private String sect;

    /**
     * LangKey(敌人身上每有{1}层冰冻，其受到的中毒伤害额外+{2}。)_LangArgs(10,1)
     */
    private String desc;

    /**
     * 10,1
     */
    private String params;

    /**
     * 伤害类型
     */
    private Integer effect;

    /**
     * 技能定期时间  >0 可定期
     */
    private Integer interval;

    /**
     * 技能飞行时间   0 瞬发
     */
    private Integer projectile_ts;

    /**
     * 技能飞行道具
     */
    private Integer projectile;
}
