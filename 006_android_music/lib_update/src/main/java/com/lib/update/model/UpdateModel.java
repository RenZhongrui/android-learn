package com.lib.update.model;

import java.io.Serializable;

/**
 * Created by renzhiqiang on 16/8/23.
 */
public class UpdateModel implements Serializable {

  private static final long serialVersionUID = -5161684897150460361L;

  public int ecode;
  public String emsg;
  public UpdateInfo data;
}
