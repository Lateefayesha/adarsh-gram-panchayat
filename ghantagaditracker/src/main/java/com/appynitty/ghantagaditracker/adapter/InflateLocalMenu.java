package com.appynitty.ghantagaditracker.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.appynitty.ghantagaditracker.R;
import com.appynitty.ghantagaditracker.pojo.LocalMenuPojo;
import com.appynitty.ghantagaditracker.utils.AUtils;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Ayan Dey on 11/7/19.
 */
public class InflateLocalMenu extends RecyclerView.Adapter<InflateLocalMenu.ViewHolder> {

    private Context mContext;
    private List<LocalMenuPojo> menuPojoList;
    private LocalMenuClickListener menuClickListener;

    public InflateLocalMenu(Context mContext) {
        this.mContext = mContext;
    }

    public List<LocalMenuPojo> getMenuPojoList() {
        return menuPojoList;
    }

    public void setMenuPojoList(List<LocalMenuPojo> menuPojoList) {
        this.menuPojoList = menuPojoList;
    }

    public LocalMenuClickListener getMenuClickListener() {
        return menuClickListener;
    }

    public void setMenuClickListener(LocalMenuClickListener menuClickListener) {
        this.menuClickListener = menuClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder holder = null;
        try{
            View view = LayoutInflater.from(mContext).inflate(R.layout.layout_local_menu, null);
            holder = new ViewHolder(view);
        }catch (Exception e){
            e.printStackTrace();
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try{
            if(!AUtils.isNull(getMenuPojoList()) && getItemCount()>0){
                LocalMenuPojo menuPojo = menuPojoList.get(position);
                holder.textView.setText(menuPojo.getMenuName());

                holder.cardView.setBackgroundColor(Color.parseColor(menuPojo.getMenuColor()));
                holder.cardView.setTag(menuPojo);

                holder.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        LocalMenuPojo pojo = (LocalMenuPojo) view.getTag();
                        if(!AUtils.isNull(getMenuClickListener())){
                            getMenuClickListener().onLocalMenuItemClick(pojo);
                        }
                    }
                });
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return menuPojoList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView textView;
        ImageView imageView;

        ViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.menu_cv);
            textView = itemView.findViewById(R.id.menuNameTV);
        }
    }

    public interface LocalMenuClickListener{
        void onLocalMenuItemClick(LocalMenuPojo menuPojo);
    }
}
