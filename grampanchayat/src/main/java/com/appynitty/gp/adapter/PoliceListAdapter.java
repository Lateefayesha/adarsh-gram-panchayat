package com.appynitty.gp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appynitty.gp.R;
import com.appynitty.gp.utils.AUtils;

import java.util.List;


/**
 * Created by mithun on 15/9/17.
 */

public class PoliceListAdapter extends ArrayAdapter<String> {

    Integer icons[] = {
            R.drawable.ic_police_website,
            R.drawable.ic_call,
    };
    private List<String> mainMenuPojoList;
    private Context context;
    private View view;
    private ViewHolder holder;

    public PoliceListAdapter(Context context, List<String> mainMenuPojoList) {
        super(context, android.R.layout.simple_list_item_1, mainMenuPojoList);
        this.context = context;
        this.mainMenuPojoList = mainMenuPojoList;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.utility_list_view_adapter, null);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.iconImageView = view.findViewById(R.id.setting_adp_iv);
            viewHolder.titleTextView = view.findViewById(R.id.setting_adp_tv);
//            viewHolder.outerLayout = view.findViewById(R.id.call_details_adapterCV);

            view.setTag(viewHolder);
        } else {
            view = convertView;
        }
        holder = (ViewHolder) view.getTag();

        if (!AUtils.isNull(mainMenuPojoList) && !mainMenuPojoList.isEmpty()) {
            String title = mainMenuPojoList.get(position);

            if (!AUtils.isNullString(title)) {
                holder.titleTextView.setText(title);
                holder.iconImageView.setImageResource(icons[position]);
            }
        }
        return view;
    }

    class ViewHolder {
        private ImageView iconImageView;
        private TextView titleTextView;
        private LinearLayout outerLayout;
    }
}