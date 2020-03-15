package com.learn.plugin;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class MyPlugin implements Plugin<Project> {


    @Override
    public void apply(Project project) {
        System.out.println("11111111111111");
    }
}