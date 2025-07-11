package jedi.game;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

public class cfgGenerator {


    public static void main(String[] args){
        FastAutoGenerator.create("jdbc:mysql://outside-dev-main.jedi-games.com:3306/game_strategy_dev", "game_strategy_dev", "1HNVkhuC7Q6vAeak")
                .globalConfig(builder -> {
                    builder.author("zhanglefeng") // 作者
                            .outputDir("E:\\github\\game_strategy\\dao-servercfg\\src\\main\\java"); // 输出路径
                })
                .packageConfig(builder -> {
                    builder.parent("jedi.game.servercfg") // 包名
                            .entity("enity");
                })
                .strategyConfig(builder -> {
                    builder
                            .addInclude("cfg_skill")
                            .addInclude("cfg_skill_effect")// 替换成你的表
                            .addInclude("cfg_soldier")
                            .addInclude("cfg_hero")
                            .entityBuilder()
                            .enableLombok()
                            .enableFileOverride()
                            .naming(NamingStrategy.underline_to_camel)  // 表名驼峰
                            .columnNaming(NamingStrategy.no_change)      // 字段名不变
                            .mapperBuilder()
                            .enableFileOverride(); // ✅ 生成 Mapper.java（继承 BaseMapper）

                })
                .templateConfig(builder -> {
                    builder
                            .controller(null)
                            .service(null)
                            .serviceImpl(null)
                            .xml(null)
                            .mapper(null); // ✅ 禁止生成 mapper.xml 文件
                })
                .execute();

    }

}
