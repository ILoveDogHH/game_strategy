package jedi.game.servercfg.enity;

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
 * @since 2025-07-11
 */
@Getter
@Setter
@TableName("cfg_hero")
public class CfgHero implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer h_id;

    private String h_name;

    /**
     * 武将类型
     */
    private Integer h_type;

    /**
     * 品质
     */
    private Integer h_qua;

    /**
     * 武将阵营（魏，蜀，吴，群雄 ，1,2,3,0）
     */
    private Integer state;

    /**
     * 武将性别  (0 男 1女)
     */
    private Integer gender;

    /**
     * 气力上限
     */
    private Integer energy_max;

    /**
     * 气力恢复
     */
    private Integer energy_recovery;

    /**
     * 血气转化率（每损失1%最大生命回复2点气力
）
     */
    private String hp_to_energy_ratio;

    /**
     * 前军id
     */
    private Integer front_soldier;

    /**
     * 后军id
     */
    private Integer back_soldier;

    /**
     * 被动id
     */
    private Integer passive_id;

    /**
     * 大招id
     */
    private Integer ultimate_id;

    /**
     * 客户端用。 武将资源模型
     */
    private String icon;
}
