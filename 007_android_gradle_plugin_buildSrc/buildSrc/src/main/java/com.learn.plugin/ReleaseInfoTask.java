package com.learn.plugin;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

/**
 * create: Ren Zhongrui
 * date: 2020-03-15
 * description: 自定义task
 */
public class ReleaseInfoTask extends DefaultTask {

    public ReleaseInfoTask() {

    }

    // TaskAction注解的方法，会在gradle build执行阶段执行
    @TaskAction
    public void doAction() {
        // 将内容写入文件
        Param param = (Param) getProject().getExtensions().getByName("testGradlePlugin");
        System.out.println("doAction task"+param.getName());
    }

}
