package com.learn.music.model.discory;

import com.learn.music.model.BaseModel;
import com.learn.music.model.discory.RecommandMiddleValue;

import java.util.ArrayList;

/**
 * @author: vision
 * @function:
 * @date: 19/6/2
 */
public class RecommandHeadValue extends BaseModel {

    public ArrayList<String> ads;
    public ArrayList<RecommandMiddleValue> middle;
    public ArrayList<RecommandFooterValue> footer;

}
