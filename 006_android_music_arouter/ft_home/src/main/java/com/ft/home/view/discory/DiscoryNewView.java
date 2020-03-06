package com.ft.home.view.discory;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ft.home.R;
import com.ft.home.model.discory.RecommandFooterValue;
import com.ft.home.model.discory.RecommandHeadValue;
import com.lib.image.loader.app.ImageLoaderManager;
import com.lib.ui.recyclerview.CommonAdapter;
import com.lib.ui.recyclerview.base.ViewHolder;


public class DiscoryNewView extends RelativeLayout {
    private Context mContext;

    /*
     * UI
     */
    private RecyclerView mRecyclerView;
    /*
     * Data
     */
    private RecommandHeadValue mHeaderValue;

    public DiscoryNewView(Context context, RecommandHeadValue recommandHeadValue) {
        this(context, null, recommandHeadValue);
    }

    public DiscoryNewView(Context context, AttributeSet attrs,
                          RecommandHeadValue recommandHeadValue) {
        super(context, attrs);
        mContext = context;
        mHeaderValue = recommandHeadValue;
        initView();
    }

    private void initView() {
        View rootView =
                LayoutInflater.from(mContext).inflate(R.layout.item_discory_head_recommand_layout, this);
        TextView titleView = rootView.findViewById(R.id.title_view);
        titleView.setText("新碟");
        mRecyclerView = rootView.findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
        mRecyclerView.setAdapter(new CommonAdapter<RecommandFooterValue>(mContext,
                R.layout.item_discory_head_recommand_recycler_layout, mHeaderValue.footer) {
            @Override
            protected void convert(ViewHolder holder, RecommandFooterValue value, int position) {
                holder.setText(R.id.text_view, value.info);
                ImageView imageView = holder.getView(R.id.image_view);
                ImageLoaderManager.getInstance().displayImageForView(imageView, value.imageUrl);
            }
        });
    }
}
