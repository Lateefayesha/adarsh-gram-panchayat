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
import com.mithsoft.lib.componants.Toasty;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import quickutils.core.QuickUtils;


/**
 * Created by MiTHUN on 8/2/18.
 */

public class CleaningCompleantFragment extends MyFragemtV4 {

    private static final String TAG = "CleaningCompleantFragment";
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

    public static CleaningCompleantFragment newInstance() {

        CleaningCompleantFragment fragment = new CleaningCompleantFragment();
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
    }

    private void genrateId() {

        ((HomeActivity) getActivity()).setTitleActionBar(getString(R.string.cleaning_compleant));
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
        saveButton = view.findViewById(R.id.cc_save_btn);
        typeSpinnerHintTitleTextView = view.findViewById(R.id.type_sp_title_hint);
        typeSpinner = view.findViewById(R.id.cc_type_sp);
        initTypeSpinner();
    }

    private void initTypeSpinner() {

        List<ComplaintTypePojo> complaintTypePojoList = new ArrayList<ComplaintTypePojo>();

        ComplaintTypePojo complaintTypePojo = new ComplaintTypePojo();
        complaintTypePojo.setId("0");
        complaintTypePojo.setDescription(getString(R.string.select_type));
        complaintTypePojo.setDescriptionMar(getString(R.string.select_type));
        complaintTypePojoList.add(complaintTypePojo);

        ComplaintTypePojo complaintTypePojo1 = new ComplaintTypePojo();
        complaintTypePojo1.setId("1");
        complaintTypePojo1.setDescription("Choke of Nala, Nali / Cleaning of Nala, Nali");
        complaintTypePojo1.setDescriptionMar("नाला, नाली चोक / नाली, नाल्याची साफसफाई");
        complaintTypePojoList.add(complaintTypePojo1);

        ComplaintTypePojo complaintTypePojo2 = new ComplaintTypePojo();
        complaintTypePojo2.setId("2");
        complaintTypePojo2.setDescription("Complaint regarding not collecting garbage");
        complaintTypePojo2.setDescriptionMar("कचरा गोळा न करण्याबद्दल तक्रार");
        complaintTypePojoList.add(complaintTypePojo2);

        ComplaintTypePojo complaintTypePojo3 = new ComplaintTypePojo();
        complaintTypePojo3.setId("3");
        complaintTypePojo3.setDescription("Complaint regarding poring of garbage at public place by any person");
        complaintTypePojo3.setDescriptionMar("कोणत्याही व्यक्तीकडून सार्वजनिक ठिकाणी कचरा टाकण्याशी संबंधित तक्रार");
        complaintTypePojoList.add(complaintTypePojo3);

        ComplaintTypePojo complaintTypePojo4 = new ComplaintTypePojo();
        complaintTypePojo4.setId("4");
        complaintTypePojo4.setDescription("Complaint regarding poring of garbage in Nala,Nali by any person");
        complaintTypePojo4.setDescriptionMar("कोणत्याही व्यक्तीने नाला, नाली मधील कचरा फोडण्याबाबत तक्रार");
        complaintTypePojoList.add(complaintTypePojo4);

        ComplaintTypePojo complaintTypePojo5 = new ComplaintTypePojo();
        complaintTypePojo5.setId("8");
        complaintTypePojo5.setDescription("Complaint regarding not taking bio medical");
        complaintTypePojo5.setDescriptionMar("जैव-वैद्यकीय उपचार न करण्याबद्दल तक्रार");
        complaintTypePojoList.add(complaintTypePojo5);

        ComplaintTypePojo complaintTypePojo6 = new ComplaintTypePojo();
        complaintTypePojo6.setId("10");
        complaintTypePojo6.setDescription("Complaint regarding poring bio medical waste from Hospital  / Nursing / Home to Nala or garbage collection center / public place");
        complaintTypePojo6.setDescriptionMar("हॉस्पिटल / नर्सिंग / होम ते नाला किंवा कचरा संकलन केंद्र / सार्वजनिक ठिकाणापासून पिअर्स बायो-मेडिकल कचराबाबत तक्रार");
        complaintTypePojoList.add(complaintTypePojo6);

        ComplaintTypePojo complaintTypePojo7 = new ComplaintTypePojo();
        complaintTypePojo7.setId("11");
        complaintTypePojo7.setDescription("Infection Diseases");
        complaintTypePojo7.setDescriptionMar("संक्रमण रोग");
        complaintTypePojoList.add(complaintTypePojo7);

        ComplaintTypePojo complaintTypePojo8 = new ComplaintTypePojo();
        complaintTypePojo8.setId("12");
        complaintTypePojo8.setDescription("Not picking of primary garbage / cleaning");
        complaintTypePojo8.setDescriptionMar("प्राथमिक कचरा / साफसफाईची निवड नाही");
        complaintTypePojoList.add(complaintTypePojo8);

        ComplaintTypePojo complaintTypePojo9 = new ComplaintTypePojo();
        complaintTypePojo9.setId("15");
        complaintTypePojo9.setDescription("Problem in Drainage because of Blockage in Nala");
        complaintTypePojo9.setDescriptionMar("नाला मधील अडथळामुळे ड्रेनेज मध्ये समस्या");
        complaintTypePojoList.add(complaintTypePojo9);

        ComplaintTypePojo complaintTypePojo10 = new ComplaintTypePojo();
        complaintTypePojo10.setId("16");
        complaintTypePojo10.setDescription("Removal of Dead Animals body");
        complaintTypePojo10.setDescriptionMar("मृत प्राण्यांचे शरीर काढून टाकणे");
        complaintTypePojoList.add(complaintTypePojo10);

        ComplaintTypePojo complaintTypePojo11 = new ComplaintTypePojo();
        complaintTypePojo11.setId("17");
        complaintTypePojo11.setDescription("Removing of Building Material from public roads & footpath");
        complaintTypePojo11.setDescriptionMar("सार्वजनिक रस्ते व पदपथावरील बांधकाम साहित्य काढणे");
        complaintTypePojoList.add(complaintTypePojo11);

        ComplaintTypePojo complaintTypePojo12 = new ComplaintTypePojo();
        complaintTypePojo12.setId("18");
        complaintTypePojo12.setDescription("Street Animals");
        complaintTypePojo12.setDescriptionMar("स्ट्रीट जनावरे");
        complaintTypePojoList.add(complaintTypePojo12);

        ArrayAdapter<StatePojo> statePojoArrayAdapter = new ArrayAdapter(
                context, R.layout.spinner_text_view, complaintTypePojoList);
        typeSpinner.setAdapter(statePojoArrayAdapter);
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

                    resultPojo = syncServer.saveCleaningCompleant(cleaningCompleantPojo);
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
}