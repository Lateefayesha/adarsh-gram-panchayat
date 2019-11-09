package com.appynitty.gp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.drawable.DrawableCompat;

import com.appynitty.gp.R;
import com.appynitty.gp.pojo.MenuPojo;
import com.appynitty.gp.utils.AUtils;

import java.util.List;
/**
 * Created by MiTHUN on 8/2/18.
 */

public class MainMenuAdapter extends ArrayAdapter<MenuPojo> {
    private List<MenuPojo> menuList;
    private Context context;
    private View view;
    private ViewHolder holder;

    private MenuItemClickListner mListner;

    public void setMenuItemClickListner(MenuItemClickListner listner){
        this.mListner = listner;
    }

    public MenuItemClickListner getMenuItemClickListner(){
        return this.mListner;
    }

    public MainMenuAdapter(Context context, List<MenuPojo> menuList) {
        super(context, android.R.layout.simple_list_item_1, menuList);
        this.context = context;
        this.menuList = menuList;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

//            view = inflater.inflate(R.layout.menu_adapter, null);
            view = inflater.inflate(R.layout.layout_local_menu, null);

            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.menuNameTextView = view.findViewById(R.id.menuNameTV);
            viewHolder.menuCardView = view.findViewById(R.id.menu_cv);

            viewHolder.imageMenuIV = view.findViewById(R.id.imageMenuIV);
            viewHolder.title_background = view.findViewById(R.id.title_background);

            view.setTag(viewHolder);
        } else {
            view = convertView;
        }
        holder = (ViewHolder) view.getTag();

        if (!AUtils.isNull(menuList) && !menuList.isEmpty()) {

            final MenuPojo menuPojo = menuList.get(position);
            holder.menuNameTextView.setText(context.getString(menuPojo.getMenuName()));
//            holder.menuCardView.setCardBackgroundColor(Color.parseColor(menuPojo.getColor()));

            holder.title_background.setBackgroundColor(Color.parseColor(menuPojo.getColor()));
            Drawable background = AppCompatResources.getDrawable(context, R.drawable.round_background_white);
            Drawable customBackground = DrawableCompat.wrap(background);
            DrawableCompat.setTint(customBackground, Color.parseColor(menuPojo.getColor()));
            holder.imageMenuIV.setBackground(customBackground);
            holder.imageMenuIV.setImageDrawable(menuPojo.getMenuIcon());

            holder.menuCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getMenuItemClickListner().onItemClick(menuPojo.getMenuId(), position);
                }
            });
        }

        return view;
    }

    class ViewHolder {

        private TextView menuNameTextView;
        private CardView menuCardView;

        private ImageView imageMenuIV;
        private LinearLayout title_background;
    }

    public interface MenuItemClickListner{
        void onItemClick(String menuId, int position);
    }

    public String getMenuId(int position){
        return menuList.get(position).getMenuId();
    }
}