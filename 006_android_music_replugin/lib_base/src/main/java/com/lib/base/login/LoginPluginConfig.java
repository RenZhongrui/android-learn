package com.lib.base.login;

/**
 * login插件信息类
 */
public class LoginPluginConfig {

  public static final String PLUGIN_NAME = "ft_login"; //插件名

  public static final String PACKAGE_NAME = "com.ft.login"; //插件包名

  public static final String KEY_INTERFACE = PACKAGE_NAME + ".interface"; //interface名

  /**
   * 插件对外暴露的所有页面
   */
  public static class PAGE {

    public static final String PAGE_LOGIN = PACKAGE_NAME + ".LoginActivity";
  }

  /**
   * 插件对外暴露的所有Action
   */
  public static class ACTION {
    public static final String KEY_DATA = PACKAGE_NAME + ".key.data";
    public static final String LOGIN_SUCCESS_ACTION = PACKAGE_NAME + ".action.LOGIN_SUCCESS";
  }
}
