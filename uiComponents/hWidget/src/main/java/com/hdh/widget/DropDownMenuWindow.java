package com.hdh.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.ListPopupWindow;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hdh.common.util.view.UIUtil;

import java.util.List;

/**
 * @author albert  <a href="mailto:zhengting.zhou@gzhjhc.com">Contact me.</a>
 * @since 2017/12/5 12:57
 */

public class DropDownMenuWindow<T extends DropDownMenuWindow.ItemDelegate> extends ListPopupWindow {
    private ItemAdapter mAdapter;
    private Context mContext;

    public DropDownMenuWindow(@NonNull Context context) {
        super(context);
        this.mContext = context;
    }

    @Override public void setAnchorView(@Nullable View anchor) {
        super.setAnchorView(anchor);
        setModal(true);
        setDropDownGravity(Gravity.END);
        setForceIgnoreOutsideTouch(false);
        setBackgroundDrawable(new ColorDrawable(Color.parseColor("#474747")));
        setWidth(UIUtil.getScreenWidth(mContext) / 5 * 1);
        setHorizontalOffset(-20);
    }

    public void setData(List<T> data) {
        if (mAdapter == null) {
            mAdapter = new ItemAdapter(mContext, data);
            setAdapter(mAdapter);
        } else {
            mAdapter.setNotifyOnChange(false);
            mAdapter.clear();
            mAdapter.addAll(data);
            mAdapter.notifyDataSetChanged();
        }
    }


    private class ItemAdapter extends ArrayAdapter<T> {
        public ItemAdapter(@NonNull Context context, @NonNull List<T> data) {
            super(context, R.layout.item_simple_drop_down_item, data);
        }

        @NonNull @Override public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_simple_drop_down_item, parent, false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            ItemDelegate item = getItem(position);
            holder.contentView.setText(item.getContent());
            if (position == getCount() - 1) {
                holder.lineView.setVisibility(View.GONE);
            } else {
                holder.lineView.setVisibility(View.VISIBLE);
            }
            return convertView;
        }

        private class ViewHolder {
            public View lineView;
            public TextView contentView;

            public ViewHolder(View itemView) {
                contentView = (TextView) itemView.findViewById(R.id.text);
                lineView = itemView.findViewById(R.id.line);
            }
        }
    }

    public interface ItemDelegate {
        String getContent();
    }
}

