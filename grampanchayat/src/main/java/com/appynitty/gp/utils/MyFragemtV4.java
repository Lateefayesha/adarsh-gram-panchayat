package com.appynitty.gp.utils;


import androidx.fragment.app.Fragment;

interface UpdateFragment {

    void updateDataForLanguage();
}

/**
 * Created by MiTHUN on 29/5/18.
 */
public abstract class MyFragemtV4 extends Fragment implements UpdateFragment {


    public abstract void initComponents();


    public void updateDataForLanguage() {
        initComponents();
    }


}