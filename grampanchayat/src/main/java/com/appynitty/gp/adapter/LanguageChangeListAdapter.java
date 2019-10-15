package com.appynitty.gp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.appynitty.gp.R;
import com.appynitty.gp.dialogs.LanguageChangeAlertDialog;
import com.appynitty.gp.utils.AUtils;

import java.util.ArrayList;

/**
 * Created by Ayan Dey on 9/8/19.
 */
public class LanguageChangeListAdapter extends RecyclerView.Adapter<LanguageChangeListAdapter.LanguageChangeViewHolder> {

    private Context mContext;
    private ArrayList<LanguageChangeAlertDialog.LanguagePojo> pojoList;
    private LanguageAdapterListner languageAdapterListner;

    public LanguageChangeListAdapter(Context context) {
        this.mContext = context;
    }

    public void setLanguageAdapterListner(LanguageAdapterListner languageAdapterListner) {
        this.languageAdapterListner = languageAdapterListner;
    }

    public ArrayList<LanguageChangeAlertDialog.LanguagePojo> getPojoList() {
        return pojoList;
    }

    public void setPojoList(ArrayList<LanguageChangeAlertDialog.LanguagePojo> pojoList) {
        this.pojoList = pojoList;
    }

    @Override
    public LanguageChangeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LanguageChangeViewHolder viewHolder = null;
        try{
            View view = LayoutInflater.from(mContext)
                    .inflate(R.layout.layout_display_language_list, parent,false);
            viewHolder = new LanguageChangeViewHolder(view);
        }catch (Exception e){
            e.printStackTrace();
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(LanguageChangeViewHolder holder, int position) {
        if(!AUtils.isNull(getPojoList()) && getPojoList().size()>0){
            final LanguageChangeAlertDialog.LanguagePojo pojo = getPojoList().get(position);
            holder.textView.setText(pojo.getName());
            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    languageAdapterListner.onLanguageChange(pojo.getId());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return getPojoList().size();
    }

    class LanguageChangeViewHolder extends RecyclerView.ViewHolder{

        LinearLayout linearLayout;
        TextView textView;
        public LanguageChangeViewHolder(View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.language_list_parent);
            textView = itemView.findViewById(R.id.language_list);
        }
    }

    public interface LanguageAdapterListner{
        void onLanguageChange(String languageId);
    }
}
