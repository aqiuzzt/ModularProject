package com.hdh.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hdh.common.R2;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 红星参与数量控件 红色
 * Created by albert on 2017/8/1.
 */

public class RedstarGoodsNumView extends RelativeLayout {

    ImageView mAddBtn;
    ImageView mMinusBtn;
    TextView mNumText;
    int mNum = 0;
    private RedstarGoodsNumView.OnChangeNumListener mListener;

    public RedstarGoodsNumView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View view = inflate(context, R.layout.redstar_goods_num_view_layout, this);
        mAddBtn = (ImageView) view.findViewById(R.id.red_star_multiple_add_iv);
        mMinusBtn = (ImageView) view.findViewById(R.id.red_star_multiple_sub_iv);
        mNumText = (TextView) view.findViewById(R.id.red_star_multiple_count_tv);


        mAddBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onChangeNum(true);
                }
            }
        });

        mMinusBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mNum > 1 && mListener != null) {
                    mListener.onChangeNum(false);
                }
            }
        });
    }

    public void setNum(int num) {
        mNum = num;
        mNumText.setText(num + "");
    }

    public int getNum() {
        return Integer.valueOf(mNumText.getText().toString());
    }

    public void setChangeNumListener(RedstarGoodsNumView.OnChangeNumListener l) {
        mListener = l;
    }


    public interface OnChangeNumListener {
        void onChangeNum(boolean isAdd);
    }
}
