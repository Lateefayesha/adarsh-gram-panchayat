package com.appynitty.gp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appynitty.gp.R;
import com.appynitty.gp.activity.HomeActivity;
import com.appynitty.gp.utils.AUtils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import quickutils.core.QuickUtils;


/**
 * Created by MiTHUN on 8/2/18.
 */

public class MapShowFragment extends Fragment implements OnMapReadyCallback {

    private View view;
    private Context context;

    public static MapShowFragment newInstance() {

        MapShowFragment fragment = new MapShowFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.activity_maps_marker, container, false);
        context = container.getContext();
        initComponents();
        return view;
    }

    public void initComponents() {

        genrateId();
        registerEvents();
        ininData();
    }

    private void genrateId() {

        ((HomeActivity) getActivity()).setTitleActionBar(getString(R.string.certificate));
        ((HomeActivity) getActivity()).setTitleIcon(R.drawable.ic_arrow_back);
        QuickUtils.prefs.save(AUtils.FRAGMENT_COUNT, 0);

        SupportMapFragment supportMapFragment = (SupportMapFragment) getFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(this);
    }

    private void registerEvents() {


    }

    private void ininData() {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {


        LatLng latLngValue1 = new LatLng(21.139323, 79.059619);//office
        googleMap.addMarker(new MarkerOptions().position(latLngValue1).title("Appynitty").snippet("Office space"));//title to the marker


        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(21.136693, 79.060577))
                .title("Haldiram's")
                .snippet("Meeting Sheduled at 2:15 PM"));
//                .icon(icon));//title to the marker

        LatLng latLngValue3 = new LatLng(21.134842, 79.076595);//office
        googleMap.addMarker(new MarkerOptions().position(latLngValue3).title("Center Point Hotel"));//title to the marker


        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngValue1, 14));// LatLag,Zoom value
//        googleMap.animateCamera(CameraUpdateFactory.zoomBy(15));// set the zoom in map value
        googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN); // set the map type


    }
}