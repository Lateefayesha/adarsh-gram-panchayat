
package com.appynitty.ghantagaditracker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.appynitty.ghantagaditracker.R;
import com.appynitty.ghantagaditracker.pojo.ActiveUserListPojo;
import com.appynitty.ghantagaditracker.utils.AUtils;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.util.Objects;

/**
 * Created by Ayan Dey on 19/11/18.
 */
public class MapInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private Context mContext;

    public MapInfoWindowAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_info_window_details, null);
        ViewElements elements = new ViewElements();

        elements.userName = view.findViewById(R.id.user_name);
        elements.vehicleNo = view.findViewById(R.id.vehicle_no);
        elements.dateTime = view.findViewById(R.id.location_date_time);
        elements.vehicleAddress = view.findViewById(R.id.vehicle_address);
        elements.contactNo = view.findViewById(R.id.contact_no);

        ActiveUserListPojo userListPojo = (ActiveUserListPojo) marker.getTag();

        try{
            if(!AUtils.isNull(userListPojo)){

                elements.userName.setText(Objects.requireNonNull(userListPojo).getUserName());

                elements.vehicleNo.setText(userListPojo.getVehcileNumber().toUpperCase());

                String displayTime = AUtils.formatLocationTime(userListPojo.getTime());
                String datetTime = userListPojo.getDate()+" "+displayTime;
                elements.dateTime.setText(datetTime);

                elements.vehicleAddress.setText(userListPojo.getAddress());

                elements.contactNo.setText(userListPojo.getUserMobile());
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return view;
    }

    private class ViewElements{
        TextView userName;
        TextView vehicleNo;
        TextView dateTime;
        TextView vehicleAddress;
        TextView contactNo;
    }
}