package com.appynitty.gp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.appynitty.gp.R;
import com.appynitty.gp.pojo.GramPanchayatPojo;
import com.appynitty.gp.utils.AUtils;

import java.util.List;

import quickutils.core.QuickUtils;


/**
 * Created by MiTHUN on 5/3/18.
 */

public class GramPanchayatListAdapter extends ArrayAdapter<GramPanchayatPojo> {

    private List<GramPanchayatPojo> gramPanchayatPojoList;
    private Context context;
    private View view;
    private ViewHolder holder;

    public GramPanchayatListAdapter(Context context, List<GramPanchayatPojo> gramPanchayatPojoList) {

        super(context, android.R.layout.simple_list_item_1, gramPanchayatPojoList);
        this.context = context;
        this.gramPanchayatPojoList = gramPanchayatPojoList;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.gram_panchayat_list_adapter, null);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.titleTextView = view.findViewById(R.id.work_check_out_adp_title);
            viewHolder.descTextView = view.findViewById(R.id.work_check_out_adp_desc);

            view.setTag(viewHolder);
        } else {
            view = convertView;
        }
        holder = (ViewHolder) view.getTag();

        if (!AUtils.isNull(gramPanchayatPojoList) && !gramPanchayatPojoList.isEmpty()) {
            GramPanchayatPojo gramPanchayatPojo = gramPanchayatPojoList.get(position);

            if (QuickUtils.prefs.getString(AUtils.LANGUAGE_ID, AUtils.DEFAULT_LANGUAGE_ID).equals("1")) {

                if (!AUtils.isNullString(gramPanchayatPojo.getAppName())) {
                    holder.titleTextView.setText(gramPanchayatPojo.getAppName());
                } else {
                    holder.titleTextView.setText("");
                }
//            if (!AUtils.isNullString(gramPanchayatPojo.getState())) {
//                holder.descTextView.setText(context.getString(R.string.state) + " " + gramPanchayatPojo.getState() + " |");
//            } else {
//                holder.descTextView.setText("");
//            }
                if (!AUtils.isNullString(gramPanchayatPojo.getDistrict())) {
//                holder.descTextView.setText(holder.descTextView.getText() + context.getString(R.string.district) + " : " + gramPanchayatPojo.getDistrict() + " | ");
                    holder.descTextView.setText(context.getString(R.string.district) + " : " + gramPanchayatPojo.getDistrict() + " | ");
                } else {
                    holder.descTextView.setText("");
                }
                if (!AUtils.isNullString(gramPanchayatPojo.getTehsil())) {
                    holder.descTextView.setText(holder.descTextView.getText() + context.getString(R.string.tahsil) + " : " + gramPanchayatPojo.getTehsil());
                } else {
                    holder.descTextView.setText("");
                }
            } else {

                if (!AUtils.isNullString(gramPanchayatPojo.getAppNamemar())) {
                    holder.titleTextView.setText(gramPanchayatPojo.getAppNamemar());
                } else {
                    holder.titleTextView.setText("");
                }
//                if (!AUtils.isNullString(gramPanchayatPojo.getStateMar())) {
//                    holder.descTextView.setText(context.getString(R.string.state) + " " + gramPanchayatPojo.getStateMar() + " |");
//                } else {
//                    holder.descTextView.setText("");
//                }
                if (!AUtils.isNullString(gramPanchayatPojo.getDistrictMar())) {
//                holder.descTextView.setText(holder.descTextView.getText() + context.getString(R.string.district) + " : " + gramPanchayatPojo.getDistrict() + " | ");
                    holder.descTextView.setText(context.getString(R.string.district) + " : " + gramPanchayatPojo.getDistrictMar() + " | ");
                } else {
                    holder.descTextView.setText("");
                }
                if (!AUtils.isNullString(gramPanchayatPojo.getTehsilMar())) {
                    holder.descTextView.setText(holder.descTextView.getText() + context.getString(R.string.tahsil) + " : " + gramPanchayatPojo.getTehsilMar());
                } else {
                    holder.descTextView.setText("");
                }

            }
        }
        return view;
    }

    class ViewHolder {
        private TextView titleTextView;
        private TextView descTextView;
    }
}