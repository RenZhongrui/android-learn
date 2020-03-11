package com.lib.base.home;

/**
 * Home插件配置信息
 */
public class HomePluginConfig {
  //插件包名
  private final static String PACKAGE_NAME = "com.ft.home";
  //插件名
  public static String PLUGIN_NAME = "ft_home";
  //对外提供方法binder
  public static String KEY_INTERFACE = PACKAGE_NAME + ".interface";

  /**
   * 插件对外暴露页面
   */
  public static class PAGE {

    public static final String PAGE_HOME = PACKAGE_NAME + ".view.home.HomeActivity";
  }

}
