package com.crux.crowd.reverse;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

public class MenuTableGenerator{

	public static void main(String[] args){
		FastAutoGenerator.create("jdbc:mysql://localhost:3306/project_crowd", "root", "SAIERHAO123")
				.globalConfig(builder -> {
					builder.fileOverride();
					builder.outputDir("D:\\JavaCourse\\15-尚筹网\\尚筹网\\crowdfunding-common-reverse\\src\\main\\java");
				}).packageConfig(builder -> {
					builder.parent("com.crux.crowd.reverse");
					//builder.moduleName("crowdfunding-common-reverse");
					builder.pathInfo(Collections.singletonMap(OutputFile.mapperXml, "D:\\JavaCourse\\15-尚筹网\\尚筹网\\crowdfunding-common-reverse\\src\\main\\resources"));
				}).strategyConfig(builder -> {
					builder.addInclude("t_menu");
					builder.addTablePrefix("t_");
					builder.entityBuilder().enableLombok();
					builder.mapperBuilder().enableMapperAnnotation();
				}).templateEngine(new FreemarkerTemplateEngine()).execute();
	}
}
