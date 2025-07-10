package jedi.game.servercfg.enity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 武将战斗属性表，type区分前后军（0=前军，1=后军）
 * </p>
 *
 * @author zhanglefeng
 * @since 2025-07-10
 */
@Getter
@Setter
@TableName("cfg_soldier")
public class CfgSoldier implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 武将ID，对应 cfg_general 表
     */
    private Integer soldier_id;

    /**
     * 0=前军，1=后军
     */
    private Byte type;

    /**
     * 生命值上限
     */
    private Integer hp_max;

    /**
     * 攻击力
     */
    private Integer attack;

    /**
     * 攻速（次/s）
     */
    private Double attack_speed;

    /**
     * 暴击率（点）
     */
    private Integer crit_rate;

    /**
     * 暴击失效率（0~1）
     */
    private Double crit_fail_rate;

    /**
     * 暴击伤害倍率
     */
    private Double crit_damage_bonus;

    /**
     * 闪避率（点）
     */
    private Integer dodge_rate;

    /**
     * 闪避失效率（0~1）
     */
    private Double dodge_fail_rate;

    /**
     * 物理伤害增幅%
     */
    private Double physical_damage_boost;

    /**
     * 计策伤害增幅%
     */
    private Double magic_damage_boost;

    /**
     * 所有伤害增幅%
     */
    private Double damage_boost_percent;

    /**
     * 固定伤害加值
     */
    private Integer damage_flat_bonus;

    /**
     * 普攻失效率（0~1）
     */
    private Double normal_attack_fail_rate;

    /**
     * 普攻连击率（0~1）
     */
    private Double normal_attack_chain_rate;

    /**
     * 计策暴击率（点）
     */
    private Integer magic_crit_rate;

    /**
     * 燃烧暴击率（点）
     */
    private Integer burn_crit_rate;

    /**
     * 天命暴击率（点）
     */
    private Integer destiny_crit_rate;

    /**
     * 计策闪避率（点）
     */
    private Integer magic_dodge_rate;

    /**
     * 燃烧闪避率（点）
     */
    private Integer burn_dodge_rate;

    /**
     * 天命闪避率（点）
     */
    private Integer destiny_dodge_rate;
}
