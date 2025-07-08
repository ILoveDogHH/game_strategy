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
 * @since 2025-07-08
 */
@Getter
@Setter
@TableName("cfg_skill_projectile")
public class CfgSkillProjectile implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer projectile_id;

    private String projectile_desc;

    private String icon;
}
