package com.yuyh.cavaliers.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.yuyh.cavaliers.R;
import com.yuyh.cavaliers.http.bean.news.NewsItem;
import com.yuyh.cavaliers.recycleview.NoDoubleClickListener;
import com.yuyh.cavaliers.recycleview.OnListItemClickListener;
import com.yuyh.cavaliers.utils.CircleTransform;
import com.yuyh.library.utils.DimenUtils;
import com.zengcanxiang.baseAdapter.recyclerView.HelperAdapter;
import com.zengcanxiang.baseAdapter.recyclerView.HelperViewHolder;

import java.util.List;

/**
 * Created by Kyrie.Y on 2016/6/6.
 */
public class BannerAdapter extends HelperAdapter<NewsItem.NewsItemBean> {

    private OnListItemClickListener mOnItemClickListener = null;

    /**
     * @param data     数据源
     * @param context  上下文
     * @param layoutId 布局Id
     */
    public BannerAdapter(List<NewsItem.NewsItemBean> data, Context context, int... layoutId) {
        super(data, context, layoutId);
    }

    @Override
    protected void HelperBindData(final HelperViewHolder viewHolder, final int position, final NewsItem.NewsItemBean item) {
        //viewHolder.setImageUrl(R.id.ivBannerImg, item.getImgurl());
        ImageView iv = viewHolder.getView(R.id.ivBannerImg);
        Picasso.with(mContext).load(item.getImgurl()).transform(new CircleTransform()).into(iv);
        viewHolder.setText(R.id.tvBannerTitle, item.getTitle())
                .setText(R.id.tvBannerTime, item.getPub_time());
        viewHolder.getItemView().setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                if (mOnItemClickListener != null)
                    mOnItemClickListener.onItemClick(viewHolder.getItemView(), position, item);
            }
        });
        ImageView image = (ImageView) viewHolder.getItemView().findViewById(R.id.ivBannerImg);
        ViewGroup.LayoutParams para = image.getLayoutParams();
        para.height = DimenUtils.getScreenWidth() / 2;
        para.width = ViewGroup.LayoutParams.MATCH_PARENT;
        image.setLayoutParams(para);
        showItemAnim(viewHolder.getItemView(), position);
    }

    public void setOnItemClickListener(OnListItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    private int mLastPosition = -1;

    public void showItemAnim(final View view, final int position) {
        if (position > mLastPosition) {
            Animation animation = AnimationUtils.loadAnimation(view.getContext(), R.anim.item_bottom_in);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    view.setAlpha(1);
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
            view.startAnimation(animation);
            mLastPosition = position;
        }
    }
}
