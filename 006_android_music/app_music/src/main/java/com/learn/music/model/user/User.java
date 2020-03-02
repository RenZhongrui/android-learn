package com.learn.music.model.user;

import com.learn.music.model.BaseModel;
import com.learn.music.model.user.UserContent;

/**
 * 用户数据协议
 */
public class User extends BaseModel {
  public int ecode;
  public String emsg;
  public UserContent data;
}
