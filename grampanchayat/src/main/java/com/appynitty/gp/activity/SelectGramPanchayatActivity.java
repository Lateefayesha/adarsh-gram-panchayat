package com.appynitty.gp.activity;


import android.content.Intent;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.appynitty.gp.R;
import com.appynitty.gp.adapter.GramPanchayatListAdapter;
import com.appynitty.gp.pojo.GramPanchayatPojo;
import com.appynitty.gp.utils.AUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mithsoft.lib.activity.BaseActivity;
import com.mithsoft.lib.componants.MyNoDataView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import quickutils.core.QuickUtils;


public class SelectGramPanchayatActivity extends BaseActivity {

    List<GramPanchayatPojo> gramPanchayatPojoList;

    private GramPanchayatListAdapter gramPanchayatListAdapter;
    private ListView listView;
    private MyNoDataView noDataView;

    @Override
    protected void generateId() {

        setContentView(R.layout.select_gram_panchyat_activity);

        listView = findViewById(R.id.content_lv);
        noDataView = findViewById(R.id.no_data_view);
//        noDataView.setNoDataImage(R.drawable.defaut);
        noDataView.setMessage(getString(R.string.noData));
        initToolbar();
    }

    private void initToolbar() {

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.smart_gram_panchayat);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_launcher);
    }

    @Override
    protected void registerEvents() {

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                GramPanchayatPojo gramPanchayatPojo = (GramPanchayatPojo) parent.getItemAtPosition(position);

                listViewOnClick(gramPanchayatPojo);
            }
        });
    }

    private void listViewOnClick(GramPanchayatPojo gramPanchayatPojo) {

        if (!AUtils.isNullString(gramPanchayatPojo.getAppId())) {

            QuickUtils.prefs.save(AUtils.GP_ID, gramPanchayatPojo.getAppId());
            QuickUtils.prefs.save(AUtils.GP_NAME, gramPanchayatPojo.getAppName());
            startActivity(new Intent(SelectGramPanchayatActivity.this, HomeActivity.class));

        } else {

            Toast.makeText(SelectGramPanchayatActivity.this, "Gram Panchayat details not found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void initData() {

        initListData();
    }

    private void initListData() {

        Type type = new TypeToken<List<GramPanchayatPojo>>() {
        }.getType();

        gramPanchayatPojoList = new Gson().fromJson(
                QuickUtils.prefs.getString(AUtils.PREFS.GRAM_PANCHAYAT_LIST, null), type);

        if (!AUtils.isNull(gramPanchayatPojoList) && !gramPanchayatPojoList.isEmpty()) {

            noDataView.setVisibility(View.GONE);
            gramPanchayatListAdapter = new GramPanchayatListAdapter(SelectGramPanchayatActivity.this, gramPanchayatPojoList);
            listView.setAdapter(gramPanchayatListAdapter);

        } else {

            noDataView.setVisibility(View.VISIBLE);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.serch_menu, menu);

        final MenuItem myActionMenuItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Toast like print
//                Toast.makeText(HomeActivity.this, "query = " + query, Toast.LENGTH_SHORT).show();
                if (!searchView.isIconified()) {
                    searchView.setIconified(true);
                }
                myActionMenuItem.collapseActionView();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

//                Intent intent = new Intent(AUtils.SEARCH_TEXT);
//                intent.putExtra(AUtils.SEARCH_TEXT, s);
//                sendBroadcast(intent);

//                gramPanchayatListAdapter = new GramPanchayatListAdapter(SelectGramPanchayatActivity.this, gramPanchayatPojoList);
//                listView.setAdapter(gramPanchayatListAdapter);

                List<GramPanchayatPojo> gramPanchayatPojoSearch = new ArrayList<GramPanchayatPojo>();

                if (!AUtils.isNullString(s)) {

                    for (int i = 0; i < gramPanchayatPojoList.size(); i++) {

                        GramPanchayatPojo gramPanchayatPojo = gramPanchayatPojoList.get(i);

                        if (gramPanchayatPojo.getAppName().toLowerCase().contains(s)) {

                            gramPanchayatPojoSearch.add(gramPanchayatPojo);
                        }
                    }
                } else {

                    gramPanchayatPojoSearch = gramPanchayatPojoList;
                }

                gramPanchayatListAdapter = new GramPanchayatListAdapter(SelectGramPanchayatActivity.this, gramPanchayatPojoSearch);
                listView.setAdapter(gramPanchayatListAdapter);

                return false;
            }
        });
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                onBackPressed();
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

}
