package com.appynitty.gp.fragment;

import android.Manifest;
import android.app.Activity;
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
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.appynitty.gp.R;
import com.appynitty.gp.activity.HomeActivity;
import com.appynitty.gp.controller.SyncServer;
import com.appynitty.gp.pojo.CleaningCompleantPojo;
import com.appynitty.gp.pojo.ComplaintTypePojo;
import com.appynitty.gp.pojo.ResultPojo;
import com.appynitty.gp.pojo.StatePojo;
import com.appynitty.gp.utils.AUtils;
import com.appynitty.gp.utils.MyAsyncTask;
import com.appynitty.gp.utils.MyFragemtV4;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mithsoft.lib.componants.Toasty;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import quickutils.core.QuickUtils;


/**
 * Created by MiTHUN on 8/2/18.
 */

public class LightCompleantFragment extends MyFragemtV4 {

    private final static String fragmentMenuId = AUtils.MenuIdConstants.Light_Complaints_Register_Form;
    private final static String prevFragmentMenuId = AUtils.MenuIdConstants.Light_Complaints;

    private static final String TAG = "LightCompleantFragment";
    private static final int REQUEST_CAMERA = 22;
    private static final int SELECT_FILE = 33;
    private View view;
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
    private Uri picUri;
    private String imageFilePath;
    private TextView typeSpinnerHintTitleTextView;
    private Spinner typeSpinner;
    private List<ComplaintTypePojo> complaintTypePojoList;

    private static LightCompleantFragment fragment = null;

    public static LightCompleantFragment newInstance() {

        fragment = new LightCompleantFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.cleaning_compleant_fragment, container, false);
        context = container.getContext();
        initComponents();
        return view;
    }

    public void initComponents() {

        genrateId();
        registerEvents();
        initData();
    }

    private void genrateId() {

        ((HomeActivity) getActivity()).setTitleActionBar(getString(R.string.light_compleant));
        ((HomeActivity) getActivity()).setTitleIcon(R.drawable.ic_arrow_back);
        QuickUtils.prefs.save(AUtils.FRAGMENT_COUNT, 0);

        nameTextView = view.findViewById(R.id.cc_name_et);
        mobileNoTextView = view.findViewById(R.id.cc_number_et);
        emailTextView = view.findViewById(R.id.cc_email_et);
        addressTextView = view.findViewById(R.id.cc_address_et);
        tipTextView = view.findViewById(R.id.cc_tip_et);
        wardNoTextView = view.findViewById(R.id.cc_ward_no_et);
        locationTextView = view.findViewById(R.id.cc_place_et);
        compleantDetailsTextView = view.findViewById(R.id.cc_details_et);
        captureImageView = view.findViewById(R.id.cc_cature_img);
        typeSpinnerHintTitleTextView = view.findViewById(R.id.type_sp_title_hint);
        saveButton = view.findViewById(R.id.cc_save_btn);
        typeSpinner = view.findViewById(R.id.cc_type_sp);
    }

    private void initData() {

        if (!AUtils.isNullString(QuickUtils.prefs.getString(AUtils.PREFS.COMPLENT_TYPE_POJO_LIST + "1", null))) {

            initTypeSpinner();
            getTypeList(false, "3");
        } else {
            getTypeList(true, "3");
        }
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

    private void registerEvents() {

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

    private void typeSpinnerOnItemSelected(ComplaintTypePojo complaintTypePojo) {

        if (complaintTypePojo.getId().equals("0")) {

            typeSpinnerHintTitleTextView.setVisibility(View.INVISIBLE);
        } else {
            typeSpinnerHintTitleTextView.setVisibility(View.VISIBLE);
        }
    }

    private void isCameraPermissionGiven() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CAMERA)) {
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

    private void isStoragePermissionGiven() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
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

    private void isLocationPermissionGiven() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)) {
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
            } else if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CAMERA)) {

                AUtils.showPermissionDialog(context, "CAMERA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            requestPermissions(new String[]{Manifest.permission.CAMERA}, AUtils.MY_PERMISSIONS_REQUEST_CAMERA);
                        }
                    }
                });
            } else if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

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
            } else if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

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
            } else if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)) {

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


    private void saveButtonOnClick() {

        if (validateForm()) {
            getFormData();

            new MyAsyncTask(context, true, new MyAsyncTask.AsynTaskListener() {
                public ResultPojo resultPojo = null;

                @Override
                public void doInBackgroundOpration(SyncServer syncServer) {

                    resultPojo = syncServer.saveLightCompleant(cleaningCompleantPojo);
                }

                @Override
                public void onFinished() {

                    if (!AUtils.isNull(resultPojo)) {

                        if (resultPojo.getStatus().equals(AUtils.STATUS_SUCCESS)) {

                            Toasty.success(context, "" + getString(R.string.submit_done), Toast.LENGTH_SHORT).show();
                            AUtils.deleteAllImagesInTheFolder();
                            getFragmentManager().popBackStack();
                        } else {
                            Toasty.error(context, "" + getString(R.string.submit_error), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toasty.error(context, "" + getString(R.string.serverError), Toast.LENGTH_SHORT).show();
                    }

                }
            }).execute();
        }

    }

    private boolean validateForm() {

        if (AUtils.isNullString(imageFilePath)) {
            Toasty.warning(context, getString(R.string.plz_capture_img), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (AUtils.isNullString(wardNoTextView.getText().toString())) {
            Toasty.warning(context, getString(R.string.plz_ent_ward_no), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (AUtils.isNullString(locationTextView.getText().toString())) {
            Toasty.warning(context, getString(R.string.plz_ent_location), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (typeSpinner.getSelectedItemPosition() == 0) {
            Toasty.warning(context, context.getString(R.string.plz_select_complaint_type), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (AUtils.isNullString(compleantDetailsTextView.getText().toString())) {
            Toasty.warning(context, getString(R.string.plz_ent_complent_dtl), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (AUtils.isNullString(nameTextView.getText().toString())) {
            Toasty.warning(context, getString(R.string.plz_ent_name), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (AUtils.isNullString(mobileNoTextView.getText().toString())) {
            Toasty.warning(context, getString(R.string.plz_ent_mobile_no), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (mobileNoTextView.getText().toString().length() < 10) {
            Toasty.warning(context, getString(R.string.plz_ent_valid_mobile_no), Toast.LENGTH_SHORT).show();
            return false;
        }

//        if (AUtils.isNullString(emailTextView.getText().toString())) {
//            Toasty.warning(context, getString(R.string.plz_ent_email), Toast.LENGTH_SHORT).show();
//            return false;
//        }

        if (!AUtils.isNullString(emailTextView.getText().toString()) && !AUtils.isEmailValid(emailTextView.getText().toString())) {
            Toasty.warning(context, getString(R.string.plz_ent_valid_email), Toast.LENGTH_SHORT).show();
            return false;
        }

//        if (AUtils.isNullString(addressTextView.getText().toString())) {
//            Toasty.warning(context, getString(R.string.plz_ent_address), Toast.LENGTH_SHORT).show();
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(!AUtils.menuNavigationListHasItem(fragmentMenuId)){
            AUtils.setMenuNavigationList(fragmentMenuId);
        }else
        if(getFragmentManager() != null)
            getFragmentManager().popBackStack();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if(!AUtils.isRecreate){
            AUtils.removeMenuNavigationValue(prevFragmentMenuId,
                    fragmentMenuId);
        }
        AUtils.removeMenuNavigationListValue(fragmentMenuId);
    }
}