package com.hdh.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

import com.amap.api.maps2d.model.LatLng;
import com.hdh.common.util.app.AppUtil;
import com.hdh.common.util.LogUtil;
import com.hdh.common.util.view.ToastUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * 导航方式选择页面
 *
 */
public class NavigateDialog extends DialogFragment {
    private static final String LOG_TAG = "NavigateDialog";
    private LatLng origin;
    private LatLng destination;
    private String startDesc;
    private String endDesc;
    private List<String> mapList;

    public static NavigateDialog newInstance(LatLng origin, LatLng destination) {
        Bundle args = new Bundle();
        args.putParcelable("origin", origin);
        args.putParcelable("destination", destination);
        NavigateDialog fragment = new NavigateDialog();
        fragment.setArguments(args);
        return fragment;
    }

    public static NavigateDialog newInstance(LatLng origin, LatLng destination, String startDesc, String endDesc) {
        Bundle args = new Bundle();
        args.putParcelable("origin", origin);
        args.putParcelable("destination", destination);
        args.putString("startDesc", startDesc);
        args.putString("endDesc", endDesc);
        NavigateDialog fragment = new NavigateDialog();
        fragment.setArguments(args);
        return fragment;
    }

    public static NavigateDialog newInstance(ArrayList<String> mapList, LatLng origin, LatLng destination, String startDesc, String endDesc) {
        Bundle args = new Bundle();
        args.putParcelable("origin", origin);
        args.putParcelable("destination", destination);
        args.putString("startDesc", startDesc);
        args.putString("endDesc", endDesc);
        args.putStringArrayList("mapList", mapList);
        NavigateDialog fragment = new NavigateDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        origin = getArguments().getParcelable("origin");
        destination = getArguments().getParcelable("destination");
        startDesc = getArguments().getString("startDesc");
        endDesc = getArguments().getString("endDesc");
        mapList = getArguments().getStringArrayList("mapList");
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setItems(new CharSequence[]{"高德地图", "百度地图", "谷歌地图"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                if (!AppUtil.isInstalled(getContext(), AppUtil.mapPaks[0])) {
                                    ToastUtil.show(getActivity(), "请安装高德地图");
                                } else {
                                    goToNaviChooseActivity(getContext(),origin.latitude,origin.longitude,
                                            startDesc,destination.latitude,destination.longitude,endDesc,"1","0");
                                }
                                break;
                            case 1:
                                if (!AppUtil.isInstalled(getContext(), AppUtil.mapPaks[1])) {
                                    ToastUtil.show(getActivity(), "请安装百度地图");
                                } else {
                                    onBDMapNavigateClick();
                                }
                                break;
                            case 2:
                                if (!AppUtil.isInstalled(getContext(), AppUtil.mapPaks[2])) {
                                    ToastUtil.show(getActivity(), "请安装谷歌地图");
                                } else {
                                    onGoogleMapNavigateClick();
                                }
                                break;
                        }
                    }
                }).setTitle("选择导航方式").setCancelable(true).create();
        return dialog;
    }


    private void onBDMapNavigateClick() {
        Intent intent = new Intent();
        intent.setData(Uri.parse("baidumap://map/direction?origin=" + origin.latitude + "," + origin.longitude + "&destination=" + destination.latitude + "," + destination.longitude + "&mode=driving"));
        startActivity(intent);
    }

    private void onGoogleMapNavigateClick() {
        Uri gmmIntentUri = Uri.parse("google.navigation:q=" + destination.latitude + "," + destination.longitude);
//        Uri gmmIntentUri = Uri.parse("http://maps.google.com/maps?"
//                + "saddr=" + origin.latitude + "," + origin.longitude
//                + "&daddr=" + destination.latitude + "," + destination.longitude
//                + "&language=zh-CN")
        Intent intent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        intent.setPackage("com.google.android.apps.maps");
        startActivity(intent);
    }

    private void onAMapNavigateClick() {
        //URLName: http://uri.amap.com/navigation?from=116.478346,39.997361,startpoint&to=116.3246,39.966577,endpoint&via=116.402796,39.936915,midwaypoint&mode=car&policy=1&src=mypage&coordinate=gaode&callnative=0
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        LogUtil.d(LOG_TAG, "http://uri.amap.com/navigation?from="
                + origin.longitude + ","
                + origin.latitude + ","
                + startDesc + "&to="
                + destination.longitude
                + ","
                + destination.latitude
                + ","
                + endDesc + "&mode=car&policy=1&src=机会多数据移动&coordinate=gaode&callnative=1");
        intent.setData(Uri.parse("http://uri.amap.com/navigation?from=" + origin.longitude + "," + origin.latitude + "," + startDesc + "&to=" + destination.longitude + "," + destination.latitude + "," + endDesc + "&mode=car&policy=1&src=机会多数据移动&coordinate=gaode&callnative=1"));
        startActivity(intent);
    }

    /**
     * 启动高德App进行直接导航
     * @param context
     * @param poiname
     * @param lat 维度
     * @param lon 进度
     * @param dev  是否偏移(0:lat 和 lon 是已经加密后的,不需要国测加密; 1:需要国测加密)
     * @param style  导航方式(0 速度快; 1 费用少; 2 路程短; 3 不走高速；4 躲避拥堵；5 不走高速且避免收费；6 不走高速且躲避拥堵；7 躲避收费和拥堵；8 不走高速躲避收费和拥堵)
     */
    public static void goToNaviActivity(Context context, String poiname, String lat, String lon, String dev, String style) {
        StringBuffer stringBuffer = new StringBuffer("androidamap://navi?sourceApplication=")
                .append("");
        if (!TextUtils.isEmpty(poiname)) {
            stringBuffer.append("&poiname=").append(poiname);
        }
        stringBuffer.append("&lat=").append(lat)
                .append("&lon=").append(lon)
                .append("&dev=").append(dev)
                .append("&style=").append(style);

        Intent intent = new Intent("android.intent.action.VIEW", android.net.Uri.parse(stringBuffer.toString()));
        intent.setPackage("com.autonavi.minimap");
        context.startActivity(intent);
    }


    /**
     *  线路规划
     * @param context
     * @param slat 起点纬度。如果不填写此参数则自动将用户当前位置设为起点纬度。
     * @param slon 起点经度。 如果不填写此参数则自动将用户当前位置设为起点经度。
     * @param sname 起点名称
     * @param dlat 终点纬度
     * @param dlon 终点经度
     * @param dname 终点名称
     * @param dev 起终点是否偏移(0:lat 和 lon 是已经加密后的,不需要国测加密; 1:需要国测加密)
     * @param t t = 0（驾车）= 1（公交）= 2（步行）= 3（骑行）= 4（火车）= 5（长途客车）
     */
    public static void goToNaviChooseActivity(Context context,double slat,double slon,String sname,double dlat,double dlon ,String dname,String dev,String t){

        StringBuffer stringBuffer = new StringBuffer("amapuri://route/plan/?sid=")
                .append("");
        stringBuffer.append("&slat=").append(slat)
                .append("&slon=").append(slon)
                .append("&sname=").append(sname)
                .append("&dlat=").append(dlat)
                .append("&dlon=").append(dlon)
                .append("&dname=").append(dname)
                .append("&dev=").append(dev)
                .append("&t=").append(t);

        Intent intent = new Intent("android.intent.action.VIEW", android.net.Uri.parse(stringBuffer.toString()));
        intent.setPackage("com.autonavi.minimap");
        context.startActivity(intent);

    }
}
