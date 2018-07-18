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

import java.util.List;


/**
 * Created by mithun on 15/9/17.
 */

public class JobListAdapter extends ArrayAdapter<String> {

    Integer icons[] = {

            R.drawable.img_employment_news,
            R.drawable.img_sarkarinaukariblog,
            R.drawable.img_sarkari_naukari_india1,
            R.drawable.img_e_govtjobs,
            R.drawable.img_indian_goverment_jobs,
            R.drawable.img_sarkari_exam_com,
            R.drawable.img_jagranjosh,
            R.drawable.img_careesma,
            R.drawable.img_jobsarkari,
            R.drawable.img_naukaricom,
            R.drawable.img_shine,
            R.drawable.img_timesjob,
            R.drawable.img_monster,
            R.drawable.img_glassdoor,
            R.drawable.img_freshworld,
            R.drawable.img_indeed
    };
    private List<String> mainMenuPojoList;
    private Context context;
    private View view;
    private ViewHolder holder;

    public JobListAdapter(Context context, List<String> mainMenuPojoList) {
        super(context, android.R.layout.simple_list_item_1, mainMenuPojoList);
        this.context = context;
        this.mainMenuPojoList = mainMenuPojoList;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.newspaper_list_view_adapter, null);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.iconImageView = view.findViewById(R.id.setting_adp_iv);
            viewHolder.titleTextView = view.findViewById(R.id.setting_adp_tv);
            viewHolder.outerLayout = view.findViewById(R.id.newspaperImg);

            view.setTag(viewHolder);
        } else {
            view = convertView;
        }
        holder = (ViewHolder) view.getTag();

        holder.outerLayout.setBackgroundResource(icons[position]);

        return view;
    }

    class ViewHolder {
        private ImageView iconImageView;
        private TextView titleTextView;
        private LinearLayout outerLayout;
    }
}