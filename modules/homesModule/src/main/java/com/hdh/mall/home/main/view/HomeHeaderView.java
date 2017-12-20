package com.hdh.mall.home.main.view;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.hdh.android.mail.base.BaseApplication;
import com.hdh.android.mail.base.bean.AccountExtendDTO;
import com.hdh.android.mail.base.modular.module.homes.HomesIntent;
import com.hdh.common.util.DateUtil;
import com.hdh.common.util.LogUtil;
import com.hdh.mall.home.R;
import com.hdh.mall.home.R2;
import com.hdh.mall.home.main.adapter.HomePageItemAdapter;
import com.hdh.mall.home.main.bean.AnnouncementBean;
import com.hdh.mall.home.main.bean.HomeCategoryItem;
import com.hdh.widget.GridDecoration;
import com.hdh.widget.UPMarqueeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by albert on 2017/10/16.
 */

public class  HomeHeaderView extends LinearLayout {
    private static final String LOG_TAG = "HomePageHeaderView";

    @BindView(R2.id.home_category_rcv)
    RecyclerView homeCategoryRcv;
    private AccountExtendDTO mAccountExtendDTO;

    @BindView(R2.id.home_notice_uv)
    UPMarqueeView upMarqueeView;

    @BindView(R2.id.home_glod_ll)
    LinearLayout mGlodLl;
    @BindView(R2.id.home_silver_ll)
    LinearLayout mSilverLl;
    @BindView(R2.id.home_consumption_ll)
    LinearLayout mConsumptionLl;
    @BindView(R2.id.home_gold_integral_tv)
    TextView mGoldIntegralTv;
    @BindView(R2.id.home_silver_integral_tv)
    TextView mSilverIntegralTv;


    @BindView(R2.id.home_finance_info_value_tv)
    TextView mUserFinanceValueTv;
    @BindView(R2.id.home_finance_info_name_tv)
    TextView mUserFinanceNameTv;
    @BindView(R2.id.home_today_index_tv)
    TextView mTodayIndexTv;
    @BindView(R2.id.home_today_time_tv)
    TextView mIndexTimeTv;
    @BindView(R2.id.home_glod_dis_iv)
    ImageView mGoldDiscIv;


    private boolean isRefresh;
    private Context mContext;
    private HomePageItemAdapter homeCategoryAdater;
    private List<AnnouncementBean> noticeData = new ArrayList<>();
    private List<View> noticeViewList = new ArrayList<>();
    private OnCategoryClickListener onCategoryClickListener;


    public HomeHeaderView(Context context, int type) {
        super(context);

        this.mContext = context;
        noticeData = new ArrayList<>();
        inflate(context, R.layout.layout_home_header, this);
        ButterKnife.bind(this);
        mAccountExtendDTO = BaseApplication.get().getUserManager().getAccountProvider();

        List<HomeCategoryItem> homeCategoryItems = new ArrayList<>();
        GridLayoutManager layoutManager = new GridLayoutManager(mContext, 2);
        homeCategoryRcv.setLayoutManager(layoutManager);
        GridDecoration itemDecoration = new GridDecoration(context,GridDecoration.VERTICAL, true, true);
        homeCategoryRcv.addItemDecoration(itemDecoration);
        homeCategoryItems.add(new HomeCategoryItem(R.string.home_category_recharge,
                R.drawable.ic_recharge, HomeCategoryItem.TYPE_RECHARGE,
                R.drawable.item_home_recharge_category_bg));
        homeCategoryItems.add(new HomeCategoryItem(R.string.home_category_buyback,
                R.drawable.ic_home_buy_back, HomeCategoryItem.TYPE_BUYBACK,
                R.drawable.item_home_buyback_category_bg));


        homeCategoryAdater = new HomePageItemAdapter();
        homeCategoryAdater.addData(homeCategoryItems);
        homeCategoryRcv.setAdapter(homeCategoryAdater);

        homeCategoryRcv.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void SimpleOnItemClick(BaseQuickAdapter helper, View view, int position) {
                HomeCategoryItem item = (HomeCategoryItem) helper.getData().get(position);
                if (onCategoryClickListener != null) {
                    onCategoryClickListener.onCategoryClickListener(item.type);
                }

            }
        });








        mIndexTimeTv.setText(DateUtil.format(BaseApplication.getServerTimestamp(), DateUtil.YYYY_MM_DD));


    }


    public void setOnCategoryClickListener(OnCategoryClickListener l) {
        onCategoryClickListener = l;
    }

    public void setInitInfo() {
        mUserFinanceNameTv.setText("蚂蚁积分");
        mGoldIntegralTv.setText("- -");
        mSilverIntegralTv.setText("- -");
        mUserFinanceValueTv.setText("蚂蚁积分");
    }

    public void setInitInfoZero() {
        mGoldIntegralTv.setText("0");
        mSilverIntegralTv.setText("0");
    }




    /**
     * 更新消费 或者业绩 信息
     */
    public void bindAccountConsumFinance(String type, String consumValue) {
        if (type.equals("1")) {//消费
            mUserFinanceNameTv.setText("蚂蚁积分");
            mUserFinanceValueTv.setText(consumValue);
        } else {
            mUserFinanceNameTv.setText("蚂蚁积分");
            mUserFinanceValueTv.setText(consumValue);
        }
    }
    public void bindAccountConsumFinance(String consumValue) {

        mUserFinanceNameTv.setText("蚂蚁积分");
        mUserFinanceValueTv.setText(consumValue);

    }




    public void setNoticeView(List<AnnouncementBean> list) {


        noticeData = list;
        setView(list);

        upMarqueeView.setViews(noticeViewList);
        LogUtil.i(LOG_TAG, "noticeViewList size:" + noticeViewList.size());

        upMarqueeView.setOnItemClickListener(new UPMarqueeView.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                LogUtil.i(LOG_TAG, "noticeViewList position:" + position);
                if (!noticeData.get(position).getParentId().equals("test")) {
                    HomesIntent.gotoAnnounvement(noticeData.get(position).getId());
                }
            }
        });

    }

    private void setView(List<AnnouncementBean> list) {
        noticeViewList = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i = i + 1) {
                LinearLayout noticeView = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.item_home_notice, null);
                TextView noDataTv = (TextView) noticeView.findViewById(R.id.item_home_notice_data_tv);
                noDataTv.setText(list.get(i).getTitle());
                noticeViewList.add(noticeView);
            }
        }
    }




    public interface OnCategoryClickListener {

        void onCategoryClickListener(int type);

        void onIntergalClickListener(int type);
    }


}
