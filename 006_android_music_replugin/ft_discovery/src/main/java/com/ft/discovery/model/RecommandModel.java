package com.ft.discovery.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @文件描述：产品实体
 */
public class RecommandModel implements Serializable {

  public ArrayList<RecommandBodyValue> list;
  public RecommandHeadValue head;
}
