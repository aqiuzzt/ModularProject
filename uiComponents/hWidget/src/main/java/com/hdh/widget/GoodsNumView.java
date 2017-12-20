package com.hdh.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class GoodsNumView extends RelativeLayout {


    private ImageView mAddBtn;
    private ImageView mMinusBtn;
    private TextView mNumText;
    private long mNum = 0;
    private Context mContext;
    private OnChangeNumListener mListener;

    public GoodsNumView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        View view = inflate(context, R.layout.goods_num_view_layout, this);
        mAddBtn = view.findViewById(R.id.add_btn);
        mMinusBtn = view.findViewById(R.id.minus_btn);
        mNumText = view.findViewById(R.id.num_text);

        onClick();

    }

    public void setNum(long num) {
        mNum = num;
        mNumText.setText(num + "");
    }

    public void setChangeNumListener(OnChangeNumListener l) {
        mListener = l;
    }

    public void onClick() {
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
                } else {
                    Toast.makeText(mContext, "商品数量不能小于1", Toast.LENGTH_SHORT);
                }
            }
        });



    }

    public static interface OnChangeNumListener {
        void onChangeNum(boolean isAdd);
    }
}
