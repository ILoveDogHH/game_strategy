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
@TableName("cfg_skill_effect")
public class CfgSkillEffect implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer effect_id;

    private String effect_desc;
}
