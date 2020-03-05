package com.learn.music.model.discory;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author: vision
 * @function:
 * @date: 19/6/2
 */
public class RecommandHeadValue implements Serializable {

    public ArrayList<String> ads;
    public ArrayList<RecommandMiddleValue> middle;
    public ArrayList<RecommandFooterValue> footer;

}
