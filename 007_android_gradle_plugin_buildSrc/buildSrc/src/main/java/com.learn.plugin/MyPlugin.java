package com.learn.plugin;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class MyPlugin implements Plugin<Project> {


    @Override
    public void apply(Project project) {
        System.out.println("11111111111111");
        // 创建扩展
        Param param = project.getExtensions().create("testGradlePlugin", Param.class);
        // gradle build完之后可以获取配置的参数value
        project.afterEvaluate(new Action<Project>() {
            @Override
            public void execute(Project project) {
                System.out.println(param.getVersion());
                System.out.println(param.getName());
            }
        });

    }
}