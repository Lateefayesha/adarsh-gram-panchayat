package com.appynitty.gp.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by Ayan Dey on 19/11/18.
 */
public class MapInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    Context mContext;

    public MapInfoWindowAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
//        View view = ((Activity)mContext).getLayoutInflater().inflate()
        return null;
    }
}
