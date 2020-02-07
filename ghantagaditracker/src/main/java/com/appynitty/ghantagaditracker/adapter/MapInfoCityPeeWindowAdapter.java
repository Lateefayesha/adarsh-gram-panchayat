
package com.appynitty.ghantagaditracker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.appynitty.ghantagaditracker.R;
import com.appynitty.ghantagaditracker.pojo.ActiveUserListPojo;
import com.appynitty.ghantagaditracker.pojo.CityPeeListPojo;
import com.appynitty.ghantagaditracker.utils.AUtils;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.util.Objects;

/**
 * Created by Ayan Dey on 19/11/18.
 */
public class MapInfoCityPeeWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private Context mContext;

    public MapInfoCityPeeWindowAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_info_window_details_city_pee, null);
        ViewElements elements = new ViewElements();

        elements.peeImage = view.findViewById(R.id.location_img);
        elements.peeName = view.findViewById(R.id.pee_name);
        elements.peeId = view.findViewById(R.id.pee_id);
        elements.peeAddress = view.findViewById(R.id.pee_address);
        elements.peeNameImage = view.findViewById(R.id.pee_name_icon);

        CityPeeListPojo cityPeeListPojo = (CityPeeListPojo) marker.getTag();

        try{
            if(!AUtils.isNull(cityPeeListPojo)){

                if(cityPeeListPojo.getName() != null && !cityPeeListPojo.getName().isEmpty()) {
                    elements.peeNameImage.setVisibility(View.VISIBLE);
                    elements.peeName.setVisibility(View.VISIBLE);
                    elements.peeName.setText(cityPeeListPojo.getName());
                } else {
                    elements.peeNameImage.setVisibility(View.GONE);
                    elements.peeName.setVisibility(View.GONE);
                }

                elements.peeId.setText(cityPeeListPojo.getSauchalayID());

                elements.peeAddress.setText(cityPeeListPojo.getAddress());
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return view;
    }

    private class ViewElements{
        ImageView peeImage;
        ImageView peeNameImage;
        TextView peeName;
        TextView peeId;
        TextView peeAddress;
    }
}