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

public class NewsPaperListAdapter extends ArrayAdapter<String> {

    Integer icons[] = {

            R.drawable.img_lokmat,
            R.drawable.img_tarunbharat,
            R.drawable.img_navbharat,
            R.drawable.img_dainik_bhaskar,
            R.drawable.img_loksatta,
            R.drawable.img_sakal,
            R.drawable.img_prahaar,
            R.drawable.img_ekmat,
            R.drawable.img_pudhari,
            R.drawable.img_maharashtra_times,
            R.drawable.img_dainik_hindustan,
            R.drawable.img_divya_marathi,
            R.drawable.img_sanchar,
            R.drawable.img_navbharat_times,
            R.drawable.img_live_hindustan,
            R.drawable.img_the_times_ofindia,
            R.drawable.img_economictimes,
            R.drawable.img_the_hitvada,
    };
    private List<String> mainMenuPojoList;
    private Context context;
    private View view;
    private ViewHolder holder;

    public NewsPaperListAdapter(Context context, List<String> mainMenuPojoList) {
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