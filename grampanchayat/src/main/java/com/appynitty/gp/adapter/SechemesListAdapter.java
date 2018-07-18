package com.appynitty.gp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.appynitty.gp.R;
import com.appynitty.gp.pojo.SchemesPojo;
import com.appynitty.gp.utils.AUtils;

import java.util.List;


/**
 * Created by MiTHUN on 5/3/18.
 */

public class SechemesListAdapter extends ArrayAdapter<SchemesPojo> {

    private List<SchemesPojo> schemesPojoList;
    private Context context;
    private View view;
    private ViewHolder holder;

    public SechemesListAdapter(Context context, List<SchemesPojo> schemesPojoList) {

        super(context, android.R.layout.simple_list_item_1, schemesPojoList);
        this.context = context;
        this.schemesPojoList = schemesPojoList;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.schemes_list_adapter, null);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.titleTextView = view.findViewById(R.id.schemes_adp_title);
            viewHolder.descTextView = view.findViewById(R.id.schemes_adp_desc);

            view.setTag(viewHolder);
        } else {
            view = convertView;
        }
        holder = (ViewHolder) view.getTag();

        if (!AUtils.isNull(schemesPojoList) && !schemesPojoList.isEmpty()) {
            SchemesPojo schemesPojo = schemesPojoList.get(position);

            if (!AUtils.isNullString(schemesPojo.getTitle())) {
                holder.titleTextView.setText(schemesPojo.getTitle());
            } else {
                holder.titleTextView.setText("");
            }
            if (!AUtils.isNullString(schemesPojo.getDescription())) {
                holder.descTextView.setText(schemesPojo.getDescription());
            } else {
                holder.descTextView.setText("");
            }
        }
        return view;
    }

    class ViewHolder {
        private TextView titleTextView;
        private TextView descTextView;
    }
}