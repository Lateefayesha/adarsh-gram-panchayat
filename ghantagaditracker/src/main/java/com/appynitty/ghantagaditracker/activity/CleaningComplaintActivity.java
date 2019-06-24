package com.appynitty.ghantagaditracker.activity;

import android.Manifest;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.appynitty.ghantagaditracker.R;
import com.appynitty.ghantagaditracker.controller.SyncServer;
import com.appynitty.ghantagaditracker.pojo.CleaningCompleantPojo;
import com.appynitty.ghantagaditracker.pojo.ComplaintTypePojo;
import com.appynitty.ghantagaditracker.pojo.ResultPojo;
import com.appynitty.ghantagaditracker.pojo.StatePojo;
import com.appynitty.ghantagaditracker.utils.AUtils;
import com.appynitty.ghantagaditracker.utils.MyAsyncTask;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mithsoft.lib.componants.Toasty;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import quickutils.core.QuickUtils;

public class CleaningComplaintActivity extends AppCompatActivity {

    private static final String TAG = "CleaningComplaintActivity";
    private static final int REQUEST_CAMERA = 22;
    private static final int SELECT_FILE = 33;
    private Context context;
    private EditText wardNoTextView;
    private EditText locationTextView;
    private EditText compleantDetailsTextView;
    private EditText nameTextView;
    private EditText mobileNoTextView;
    private EditText emailTextView;
    private EditText addressTextView;
    private EditText tipTextView;
    private ImageView captureImageView;
    private Button saveButton;
    private CleaningCompleantPojo cleaningCompleantPojo;
    private String imageFilePath;
    private TextView typeSpinnerHintTitleTextView;
    private Spinner typeSpinner;
    private List<ComplaintTypePojo> complaintTypePojoList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initComponents();
    }

    public void initComponents() {
        generateId();
        registerEvents();
        initData();
    }

    private void generateId(){

        setContentView(R.layout.activity_cleaning_complaint);
        context = CleaningComplaintActivity.this;
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nameTextView = findViewById(R.id.cc_name_et);
        mobileNoTextView = findViewById(R.id.cc_number_et);
        emailTextView = findViewById(R.id.cc_email_et);
        addressTextView = findViewById(R.id.cc_address_et);
        tipTextView = findViewById(R.id.cc_tip_et);
        wardNoTextView = findViewById(R.id.cc_ward_no_et);
        locationTextView = findViewById(R.id.cc_place_et);
        compleantDetailsTextView = findViewById(R.id.cc_details_et);
        captureImageView = findViewById(R.id.cc_cature_img);
        saveButton = findViewById(R.id.cc_save_btn);
        typeSpinnerHintTitleTextView = findViewById(R.id.type_sp_title_hint);
        typeSpinner = findViewById(R.id.cc_type_sp);
    }

    private void registerEvents(){
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveButtonOnClick();
            }
        });

        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                ComplaintTypePojo complaintTypePojo = (ComplaintTypePojo) parent.getItemAtPosition(position);
//                stateId = statePojo.getId();
                typeSpinnerOnItemSelected(complaintTypePojo);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        captureImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    // Do something for marshmallow and above versions
                    isCameraPermissionGiven();
                } else {
                    // do something for phones running an SDK before marshmallow
                    CheckGpsStatus();
                }
            }
        });
    }

    private void isCameraPermissionGiven() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.CAMERA)) {
                //Show Information about why you need the permission

                AUtils.showPermissionDialog(context, "CAMERA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            requestPermissions(new String[]{Manifest.permission.CAMERA}, AUtils.MY_PERMISSIONS_REQUEST_CAMERA);
                        }
                    }
                });

            } else if (QuickUtils.prefs.getBoolean(Manifest.permission.CAMERA, false)) {
                //Previously Permission Request was cancelled with 'Dont Ask Again',
                // Redirect to Settings after showing Information about why you need the permission

                AUtils.showPermissionDialog(context, "CAMERA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();
                        AUtils.goToAppSettings(context);
                    }
                });

            } else {
                //just request the permission
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, AUtils.MY_PERMISSIONS_REQUEST_CAMERA);
                }
            }

            QuickUtils.prefs.save(Manifest.permission.CAMERA, true);
        } else {
            //You already have the permission, just go ahead.
            isStoragePermissionGiven();
        }
    }

    private void typeSpinnerOnItemSelected(ComplaintTypePojo complaintTypePojo) {

        if (complaintTypePojo.getId().equals("0")) {

            typeSpinnerHintTitleTextView.setVisibility(View.INVISIBLE);
        } else {
            typeSpinnerHintTitleTextView.setVisibility(View.VISIBLE);
        }
    }

    private void initData(){
        if (!AUtils.isNullString(QuickUtils.prefs.getString(AUtils.PREFS.COMPLENT_TYPE_POJO_LIST + "1", null))) {

            initTypeSpinner();
            getTypeList(false, "1");
        } else {
            getTypeList(true, "1");
        }
    }

    private void getTypeList(boolean isShowPrgressDialog, final String typeId) {

        new MyAsyncTask(context, isShowPrgressDialog, new MyAsyncTask.AsynTaskListener() {
            public boolean isDataPull = false;

            @Override
            public void doInBackgroundOpration(SyncServer syncServer) {


                isDataPull = syncServer.pullTypeListFromServer(typeId);
            }

            @Override
            public void onFinished() {

                Type type = new TypeToken<List<ComplaintTypePojo>>() {
                }.getType();

                complaintTypePojoList = new Gson().fromJson(
                        QuickUtils.prefs.getString(AUtils.PREFS.COMPLENT_TYPE_POJO_LIST + typeId, null), type);

                initTypeSpinner();
            }
        }).execute();
    }

    private void initTypeSpinner() {

        if (AUtils.isNull(complaintTypePojoList) || complaintTypePojoList.isEmpty()) {
            complaintTypePojoList = new ArrayList<ComplaintTypePojo>();
        }

        ComplaintTypePojo complaintTypePojo = new ComplaintTypePojo();
        complaintTypePojo.setId("0");
        complaintTypePojo.setDescription(context.getString(R.string.select_type));
        complaintTypePojo.setDescriptionMar(context.getString(R.string.select_type));
        complaintTypePojoList.add(0, complaintTypePojo);

        ArrayAdapter<StatePojo> statePojoArrayAdapter = new ArrayAdapter(
                context, R.layout.spinner_text_view, complaintTypePojoList);
        typeSpinner.setAdapter(statePojoArrayAdapter);
    }

    private void isStoragePermissionGiven() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                //Show Information about why you need the permission

                AUtils.showPermissionDialog(context, "EXTERNAL STORAGE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, AUtils.MY_PERMISSIONS_REQUEST_STORAGE);
                        }
                    }
                });

            } else if (QuickUtils.prefs.getBoolean(Manifest.permission.WRITE_EXTERNAL_STORAGE, false)) {
                //Previously Permission Request was cancelled with 'Dont Ask Again',
                // Redirect to Settings after showing Information about why you need the permission

                AUtils.showPermissionDialog(context, "EXTERNAL STORAGE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();
                        AUtils.goToAppSettings(context);
                    }
                });

            } else {
                //just request the permission
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, AUtils.MY_PERMISSIONS_REQUEST_STORAGE);
                }
            }

            QuickUtils.prefs.save(Manifest.permission.WRITE_EXTERNAL_STORAGE, true);
        } else {
            //You already have the permission, just go ahead.
            isLocationPermissionGiven();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == AUtils.MY_PERMISSIONS_REQUEST_CAMERA) {
            //check if all permissions are granted
            boolean allgranted = false;
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    allgranted = true;
                } else {
                    allgranted = false;
                    break;
                }
            }

            if (allgranted) {
                isStoragePermissionGiven();
            } else if (ActivityCompat.shouldShowRequestPermissionRationale((Activity)context, Manifest.permission.CAMERA)) {

                AUtils.showPermissionDialog(context, "CAMERA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            requestPermissions(new String[]{Manifest.permission.CAMERA}, AUtils.MY_PERMISSIONS_REQUEST_CAMERA);
                        }
                    }
                });
            } else if (ActivityCompat.shouldShowRequestPermissionRationale((Activity)context, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                AUtils.showPermissionDialog(context, "EXTERNAL STORAGE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, AUtils.MY_PERMISSIONS_REQUEST_CAMERA);
                        }
                    }
                });
            } else {
//                Toast.makeText(getActivity(), "Unable to get Permission", Toast.LENGTH_LONG).show();
            }
        } else if (requestCode == AUtils.MY_PERMISSIONS_REQUEST_STORAGE) {
            //check if all permissions are granted
            boolean allgranted = false;
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    allgranted = true;
                } else {
                    allgranted = false;
                    break;
                }
            }

            if (allgranted) {
                isLocationPermissionGiven();
            } else if (ActivityCompat.shouldShowRequestPermissionRationale((Activity)context, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                AUtils.showPermissionDialog(context, "EXTERNAL STORAGE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, AUtils.MY_PERMISSIONS_REQUEST_STORAGE);
                        }
                    }
                });
            } else {
//                Toast.makeText(getActivity(), "Unable to get Permission", Toast.LENGTH_LONG).show();
            }

        } else if (requestCode == AUtils.MY_PERMISSIONS_REQUEST_LOCATION) {
            //check if all permissions are granted
            boolean allgranted = false;
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    allgranted = true;
                } else {
                    allgranted = false;
                    break;
                }
            }

            if (allgranted) {
                CheckGpsStatus();
            } else if (ActivityCompat.shouldShowRequestPermissionRationale((Activity)context, Manifest.permission.ACCESS_FINE_LOCATION)) {

                AUtils.showPermissionDialog(context, "LOCATION", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, AUtils.MY_PERMISSIONS_REQUEST_LOCATION);
                        }
                    }
                });
            } else {
//                Toast.makeText(getActivity(), "Unable to get Permission", Toast.LENGTH_LONG).show();
            }

        }
    }

    private void isLocationPermissionGiven() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity)context, Manifest.permission.ACCESS_FINE_LOCATION)) {
                //Show Information about why you need the permission

                AUtils.showPermissionDialog(context, "LOCATION", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, AUtils.MY_PERMISSIONS_REQUEST_LOCATION);
                        }
                    }
                });

            } else if (QuickUtils.prefs.getBoolean(Manifest.permission.ACCESS_FINE_LOCATION, false)) {
                //Previously Permission Request was cancelled with 'Dont Ask Again',
                // Redirect to Settings after showing Information about why you need the permission

                AUtils.showPermissionDialog(context, "LOCATION", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();
                        AUtils.goToAppSettings(context);
                    }
                });

            } else {
                //just request the permission
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, AUtils.MY_PERMISSIONS_REQUEST_LOCATION);
                }
            }

            QuickUtils.prefs.save(Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else {
            //You already have the permission, just go ahead.
            CheckGpsStatus();
        }
    }


    private void CheckGpsStatus() {

        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        boolean GpsStatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (GpsStatus) {

            takePhotoImageViewOnClick();
        } else {

            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
        }
    }

    private void takePhotoImageViewOnClick() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE) {

//                onSelectFromGalleryResult(data);

            } else if (requestCode == REQUEST_CAMERA) {

                onCaptureImageResult(data);
            }
        }
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        File destination = null;
        try {

            File dir = new File(Environment.getExternalStorageDirectory()
                    .toString() + "/Gram Panchayat");

            if (!dir.exists()) {
                dir.mkdirs();
            }

            destination = new File(dir, System.currentTimeMillis() + ".jpg");

            FileOutputStream fOut = new FileOutputStream(destination);
            thumbnail.compress(Bitmap.CompressFormat.PNG, 100, fOut);

            imageFilePath = destination.getAbsolutePath();

        } catch (Exception e) {

            e.printStackTrace();
            Toasty.error(context, "Unable to add image", Toast.LENGTH_SHORT).show();
        }

        captureImageView.setImageBitmap(thumbnail);
    }

    private boolean validateForm() {

        if (AUtils.isNullString(imageFilePath)) {
            Toasty.warning(context, context.getString(R.string.plz_capture_img), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (AUtils.isNullString(wardNoTextView.getText().toString())) {
            Toasty.warning(context, context.getString(R.string.plz_ent_ward_no), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (AUtils.isNullString(locationTextView.getText().toString())) {
            Toasty.warning(context, context.getString(R.string.plz_ent_location), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (typeSpinner.getSelectedItemPosition() == 0) {
            Toasty.warning(context, context.getString(R.string.plz_select_complaint_type), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (AUtils.isNullString(compleantDetailsTextView.getText().toString())) {
            Toasty.warning(context, context.getString(R.string.plz_ent_complent_dtl), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (AUtils.isNullString(nameTextView.getText().toString())) {
            Toasty.warning(context, context.getString(R.string.plz_ent_name), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (AUtils.isNullString(mobileNoTextView.getText().toString())) {
            Toasty.warning(context, context.getString(R.string.plz_ent_mobile_no), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (mobileNoTextView.getText().toString().length() < 10) {
            Toasty.warning(context, context.getString(R.string.plz_ent_valid_mobile_no), Toast.LENGTH_SHORT).show();
            return false;
        }

//        if (AUtils.isNullString(emailTextView.getText().toString())) {
//            Toasty.warning(context, context.getString(R.string.plz_ent_email), Toast.LENGTH_SHORT).show();
//            return false;
//        }

        if (!AUtils.isNullString(emailTextView.getText().toString()) && !AUtils.isEmailValid(emailTextView.getText().toString())) {
            Toasty.warning(context, context.getString(R.string.plz_ent_valid_email), Toast.LENGTH_SHORT).show();
            return false;
        }

//        if (AUtils.isNullString(addressTextView.getText().toString())) {
//            Toasty.warning(context, context.getString(R.string.plz_ent_address), Toast.LENGTH_SHORT).show();
//            return false;
//        }

        return true;
    }

    private void getFormData() {

        cleaningCompleantPojo = new CleaningCompleantPojo();
        cleaningCompleantPojo.setName(nameTextView.getText().toString());
        cleaningCompleantPojo.setNumber(mobileNoTextView.getText().toString());
        cleaningCompleantPojo.setTypeId(((ComplaintTypePojo) typeSpinner.getSelectedItem()).getId());
        if (!AUtils.isNullString(emailTextView.getText().toString())) {
            cleaningCompleantPojo.setEmail(emailTextView.getText().toString());
        }
        if (!AUtils.isNullString(addressTextView.getText().toString())) {
            cleaningCompleantPojo.setAddress(addressTextView.getText().toString());
        }
        if (!AUtils.isNullString(tipTextView.getText().toString())) {
            cleaningCompleantPojo.setTip(tipTextView.getText().toString());
        }
        cleaningCompleantPojo.setWardNo(wardNoTextView.getText().toString());
        cleaningCompleantPojo.setLocation(locationTextView.getText().toString());
        cleaningCompleantPojo.setDetails(compleantDetailsTextView.getText().toString());
        cleaningCompleantPojo.setImgUrl(imageFilePath);
    }

    private void saveButtonOnClick() {

        if (validateForm()) {
            getFormData();

            new MyAsyncTask(context, true, new MyAsyncTask.AsynTaskListener() {
                public ResultPojo resultPojo = null;

                @Override
                public void doInBackgroundOpration(SyncServer syncServer) {

                    resultPojo = syncServer.saveCleaningCompleant(cleaningCompleantPojo);
                }

                @Override
                public void onFinished() {

                    if (!AUtils.isNull(resultPojo)) {

                        if (resultPojo.getStatus().equals(AUtils.STATUS_SUCCESS)) {

                            Toasty.success(context, "" + context.getString(R.string.submit_done), Toast.LENGTH_SHORT).show();
                            AUtils.deleteAllImagesInTheFolder();
                            CleaningComplaintActivity.this.finish();
                        } else {
                            Toasty.error(context, "" + context.getString(R.string.submit_error), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toasty.error(context, "" + context.getString(R.string.serverError), Toast.LENGTH_SHORT).show();
                    }

                }
            }).execute();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}