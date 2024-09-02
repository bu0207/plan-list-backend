package com.bnt.plan.generator;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 代码生成器
 *
 * @author bnt
 * @version 1.0.0
 * @create 2022/11/23 14:42 bnt
 * @history
 */
public class AutoGeneratorCode {
    /**
     * 数据源配置
     */
    private static final DataSourceConfig.Builder DATA_SOURCE_CONFIG = new DataSourceConfig
            .Builder("jdbc:mysql://localhost:3306/plan_list?useUnicode=true&characterEncoding=utf-8&autoReconnect=true&useSSL=false", "bnt", "bnt");

    public static void main(String[] args) {
        FastAutoGenerator.create(DATA_SOURCE_CONFIG)
                .globalConfig(builder -> {
                    // 设置作者
                    builder.author("bnt")
                            // 开启 swagger 模式
                            .enableSwagger()
                            // 覆盖已生成文件
                            .fileOverride()
                            // 指定输出目录
                            .outputDir("F://");
                })
                .packageConfig(builder -> {
                    // 设置包名
                    builder.parent("com.bnt.plan")
                            .entity("model.entity")
                            .service("service")
                            .serviceImpl("service.impl")
                            .mapper("mapper")
                            .controller("controller");
                })
                // 策略配置
                .strategyConfig((scanner, builder) -> builder.addInclude(getTables(scanner.apply("请输入表名，多个英文逗号分隔？所有输入 all")))
                        .controllerBuilder().enableRestStyle().enableHyphenStyle()
                        .entityBuilder()
                        //开启生成实体时生成字段注解
                        .enableTableFieldAnnotation()
                        // 开启 lombok 模型
                        .enableLombok()
                        // 添加表字段填充
                        .addTableFills(new Column("create_date", FieldFill.INSERT))
                        .addTableFills(new Column("update_date", FieldFill.INSERT_UPDATE))
                        .addTableFills(new Column("del_flag", FieldFill.INSERT))
                        .idType(IdType.ASSIGN_ID)
                        .serviceBuilder()
                        .formatServiceFileName("%sService")
                        .mapperBuilder()
                        .enableMapperAnnotation()
                        .build())

                // 模板引擎配置，默认 Velocity 可选模板引擎 Beetl 或 Freemarker
                .templateEngine(new FreemarkerTemplateEngine())
                // 执行
                .execute();
    }

    // 处理 all 情况
    protected static List<String> getTables(String tables) {
        return "all".equals(tables) ? Collections.emptyList() : Arrays.asList(tables.split(","));
    }
}
