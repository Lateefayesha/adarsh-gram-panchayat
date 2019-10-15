package com.appynitty.gp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.appynitty.gp.R;
import com.appynitty.gp.pojo.MandiPojo;
import com.appynitty.gp.utils.AUtils;

import java.util.List;

/**
 * Created by Richali Pradhan Gupte on 02-10-2018.
 */
public class MandiListAdapter extends ArrayAdapter<MandiPojo> {

    private List<MandiPojo> mandiPojoList;
    private Context context;
    private View view;
    private ViewHolder holder;

    public MandiListAdapter(Context context, List<MandiPojo> mandiPojoList) {

        super(context, android.R.layout.simple_list_item_1, mandiPojoList);
        this.context = context;
        this.mandiPojoList = mandiPojoList;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.mandi_list_adapter, null);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.varietyTextView = view.findViewById(R.id.mandi_adp_variety);
            viewHolder.subVarietyTextView = view.findViewById(R.id.mandi_adp_subvariety);
            viewHolder.unitTextView = view.findViewById(R.id.mandi_adp_units);
            viewHolder.minPriceTextView = view.findViewById(R.id.mandi_adp_min_price_value);
            viewHolder.maxPriceTextView = view.findViewById(R.id.mandi_adp_max_price_value);

            view.setTag(viewHolder);
        } else {
            view = convertView;
        }
        holder = (ViewHolder) view.getTag();

        if (!AUtils.isNull(mandiPojoList) && !mandiPojoList.isEmpty()) {
            MandiPojo mandiPojo = mandiPojoList.get(position);
            String VarietyName = null;
            if(!AUtils.isNullString(mandiPojo.getCommodity()))
            {
                VarietyName = mandiPojo.getCommodity();
            }

            if (!AUtils.isNullString(mandiPojo.getVariety())) {
                if(!VarietyName.isEmpty())
                {
                    VarietyName = VarietyName +" - " + mandiPojo.getVariety();
                }
                else
                {
                    VarietyName = mandiPojo.getVariety();
                }
                holder.varietyTextView.setText(VarietyName);
            } else {
                holder.varietyTextView.setText("");
            }
            if (!AUtils.isNullString(mandiPojo.getSubvariety1())) {
                holder.subVarietyTextView.setText(mandiPojo.getSubvariety1());
            } else {
                holder.subVarietyTextView.setText("");
            }
            if (!AUtils.isNullString(mandiPojo.getUnit())) {
                holder.unitTextView.setText(mandiPojo.getUnit());
            } else {
                holder.unitTextView.setText("");
            }
            if (!AUtils.isNullString(mandiPojo.getMin())) {
                holder.minPriceTextView.setText(mandiPojo.getMin());
            } else {
                holder.minPriceTextView.setText("");
            }
            if (!AUtils.isNullString(mandiPojo.getMax())) {
                holder.maxPriceTextView.setText(mandiPojo.getMax());
            } else {
                holder.maxPriceTextView.setText("");
            }
        }
        return view;
    }

    class ViewHolder {
        private TextView varietyTextView;
        private TextView subVarietyTextView;
        private TextView unitTextView;
        private TextView minPriceTextView;
        private TextView maxPriceTextView;
    }
}
