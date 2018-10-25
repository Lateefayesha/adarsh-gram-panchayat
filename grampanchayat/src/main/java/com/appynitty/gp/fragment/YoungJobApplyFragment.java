package com.appynitty.gp.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.LocationManager;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.appynitty.gp.R;
import com.appynitty.gp.activity.HomeActivity;
import com.appynitty.gp.controller.SyncServer;
import com.appynitty.gp.pojo.ApplyJobPojo;
import com.appynitty.gp.pojo.ResultPojo;
import com.appynitty.gp.utils.AUtils;
import com.appynitty.gp.utils.MyAsyncTask;
import com.appynitty.gp.utils.MyFragemtV4;
import com.mithsoft.lib.componants.Toasty;
import com.mithsoft.lib.filepicker.Constants;
import com.mithsoft.lib.filepicker.FileChooserActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import quickutils.core.QuickUtils;


/**
 * Created by MiTHUN on 8/2/18.
 */

public class YoungJobApplyFragment extends MyFragemtV4 {

    private static final String TAG = "YoungBusinessApplyFragment";
    private static final int REQUEST_CAMERA = 22;
    private static final int SELECT_FILE = 33;
    private static final int FILE_CHOOSER = 44;
    private View view;
    private Context context;
    private EditText nameTextView;
    private EditText mobileNoTextView;
    private EditText emailTextView;
    private EditText addressTextView;
    private EditText educationQulificationTextView;
    private EditText adharCardNoTextView;
    private EditText otherSkillTextView;
    private EditText intrestedJobTextView;
    private EditText aboutYourselfTextView;
    private TextView resumeFileNameTextView;
    private Button uploadResumeButton;
    private Button saveButton;
    private ApplyJobPojo applyJobPojo;
    private String imageFilePath1 = "";
    private String imageFilePath2 = "";
    private String imageFilePath3 = "";
    private String imageFilePath4 = "";
    private String resumeFilePath = "";
    private ImageView captureImageView1;
    private ImageView captureImageView2;
    private ImageView captureImageView3;
    private ImageView captureImageView4;
    private int imageViewNo = 0;
    private String mPreviousLanguage = "";
    private static YoungJobApplyFragment fragment = null;

    public static YoungJobApplyFragment newInstance() {

        fragment = new YoungJobApplyFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.young_job_apply_fragment, container, false);
        context = container.getContext();
        initComponents();
        return view;
    }

    public void initComponents() {

        if (!mPreviousLanguage.equals(QuickUtils.prefs.getString(AUtils.LANGUAGE_ID, AUtils.DEFAULT_LANGUAGE_ID))) {

            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.detach(fragment);
            fragmentTransaction.attach(fragment);
            fragmentTransaction.commit();
            mPreviousLanguage = QuickUtils.prefs.getString(AUtils.LANGUAGE_ID, AUtils.DEFAULT_LANGUAGE_ID);
        }

        genrateId();
        registerEvents();
    }

    private void genrateId() {

        ((HomeActivity) getActivity()).setTitleActionBar(getString(R.string.young_jobs));
        ((HomeActivity) getActivity()).setTitleIcon(R.drawable.ic_arrow_back);
        QuickUtils.prefs.save(AUtils.FRAGMENT_COUNT, 0);

        captureImageView1 = view.findViewById(R.id.yj_cature_img1);
        captureImageView2 = view.findViewById(R.id.yj_cature_img2);
        captureImageView3 = view.findViewById(R.id.yj_cature_img3);
        captureImageView4 = view.findViewById(R.id.yj_cature_img4);
        nameTextView = view.findViewById(R.id.yj_name_et);
        mobileNoTextView = view.findViewById(R.id.yj_number_et);
        emailTextView = view.findViewById(R.id.yj_email_et);
        addressTextView = view.findViewById(R.id.yj_address_et);
        educationQulificationTextView = view.findViewById(R.id.yj_education_qulification_et);
        adharCardNoTextView = view.findViewById(R.id.yj_adhar_card_no_et);
        otherSkillTextView = view.findViewById(R.id.yj_other_skill_et);
        intrestedJobTextView = view.findViewById(R.id.yj_intrest_job_et);
        aboutYourselfTextView = view.findViewById(R.id.yj_about_yourself_et);
        resumeFileNameTextView = view.findViewById(R.id.yj_resume_file_name);
        uploadResumeButton = view.findViewById(R.id.yj_upload_resume_btn);

        saveButton = view.findViewById(R.id.yj_save_btn);
    }

    private void registerEvents() {

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveButtonOnClick();
            }
        });


        captureImageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imageViewNo = 1;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    // Do something for marshmallow and above versions
                    isCameraPermissionGiven();
                } else {
                    // do something for phones running an SDK before marshmallow
                    checkGpsStatus();
                }
            }
        });

        captureImageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imageViewNo = 2;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    // Do something for marshmallow and above versions
                    isCameraPermissionGiven();
                } else {
                    // do something for phones running an SDK before marshmallow
                    checkGpsStatus();
                }
            }
        });

        captureImageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imageViewNo = 3;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    // Do something for marshmallow and above versions
                    isCameraPermissionGiven();
                } else {
                    // do something for phones running an SDK before marshmallow
                    checkGpsStatus();
                }
            }
        });

        captureImageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imageViewNo = 4;

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    // Do something for marshmallow and above versions
                    isCameraPermissionGiven();
                } else {
                    // do something for phones running an SDK before marshmallow

                    checkGpsStatus();
                }
            }
        });

        uploadResumeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uploadResumeOnClick();
            }
        });

    }

    private void uploadResumeOnClick() {


        ArrayList<String> extensions = new ArrayList<>();
        extensions.add(".doc");
        extensions.add(".docx");
        extensions.add(".pdf");
        Intent intent = new Intent(context, FileChooserActivity.class);

        Bundle mBundle = new Bundle();


        mBundle.putStringArrayList(Constants.KEY_FILTER_FILES_EXTENSIONS, extensions);
        intent.putExtras(mBundle);

        startActivityForResult(intent, FILE_CHOOSER);
    }


    private void saveButtonOnClick() {

        if (validateForm()) {
            getFormData();

            new MyAsyncTask(context, true, new MyAsyncTask.AsynTaskListener() {
                public ResultPojo resultPojo = null;

                @Override
                public void doInBackgroundOpration(SyncServer syncServer) {

                    resultPojo = syncServer.saveJobApplication(applyJobPojo);
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

        if (!AUtils.isNullString(emailTextView.getText().toString()) && !AUtils.isEmailValid(emailTextView.getText().toString())) {
            Toasty.warning(context, getString(R.string.plz_ent_valid_email), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (AUtils.isNullString(addressTextView.getText().toString())) {
            Toasty.warning(context, getString(R.string.plz_ent_address), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (AUtils.isNullString(educationQulificationTextView.getText().toString())) {
            Toasty.warning(context, getString(R.string.plz_ent_edu_quli), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!AUtils.isNullString(adharCardNoTextView.getText().toString()) && adharCardNoTextView.getText().toString().length() < 12) {

            Toasty.warning(context, getString(R.string.plz_ent_valid_aadhar_no), Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void getFormData() {

        applyJobPojo = new ApplyJobPojo();
        applyJobPojo.setyBId("0");
        applyJobPojo.setName(nameTextView.getText().toString());
        applyJobPojo.setAddress(addressTextView.getText().toString());
        applyJobPojo.setEducationQulification(educationQulificationTextView.getText().toString());
        applyJobPojo.setNumber(mobileNoTextView.getText().toString());
        applyJobPojo.setCreatedDate(AUtils.getCurrentDateTime());
        applyJobPojo.setLanguageId("1");
        if (!AUtils.isNullString(emailTextView.getText().toString())) {
            applyJobPojo.setEmail(emailTextView.getText().toString());
        }
        if (!AUtils.isNullString(adharCardNoTextView.getText().toString())) {
            applyJobPojo.setAdharCardNo(adharCardNoTextView.getText().toString());
        }
        if (!AUtils.isNullString(otherSkillTextView.getText().toString())) {
            applyJobPojo.setOtherSkill(otherSkillTextView.getText().toString());
        }
        if (!AUtils.isNullString(intrestedJobTextView.getText().toString())) {
            applyJobPojo.setInterestedJob(intrestedJobTextView.getText().toString());
        }
        if (!AUtils.isNullString(aboutYourselfTextView.getText().toString())) {
            applyJobPojo.setAboutYourself(aboutYourselfTextView.getText().toString());
        }
        if (!AUtils.isNullString(imageFilePath1)) {
            applyJobPojo.setImageFilePath1(imageFilePath1);
        }
        if (!AUtils.isNullString(imageFilePath2)) {
            applyJobPojo.setImageFilePath2(imageFilePath2);
        }
        if (!AUtils.isNullString(imageFilePath3)) {
            applyJobPojo.setImageFilePath3(imageFilePath3);
        }
        if (!AUtils.isNullString(imageFilePath4)) {
            applyJobPojo.setImageFilePath4(imageFilePath4);
        }
        if (!AUtils.isNullString(resumeFilePath)) {
            applyJobPojo.setResumeFilePath(resumeFilePath);
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
            checkGpsStatus();
        }
    }

    private void checkGpsStatus() {

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
            } else if (requestCode == FILE_CHOOSER) {

                resumeFilePath = data.getStringExtra(Constants.KEY_FILE_SELECTED);
                resumeFileNameTextView.setVisibility(View.VISIBLE);
                resumeFileNameTextView.setText(new File(resumeFilePath).getName());
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

        } catch (Exception e) {

            e.printStackTrace();
            Toasty.error(context, "Unable to add image", Toast.LENGTH_SHORT).show();
        }

        switch (imageViewNo) {

            case 1:
                captureImageView1.setImageBitmap(thumbnail);
                imageFilePath1 = destination.getAbsolutePath();
                break;
            case 2:
                captureImageView2.setImageBitmap(thumbnail);
                imageFilePath2 = destination.getAbsolutePath();
                break;
            case 3:
                captureImageView3.setImageBitmap(thumbnail);
                imageFilePath3 = destination.getAbsolutePath();
                break;
            case 4:
                captureImageView4.setImageBitmap(thumbnail);
                imageFilePath4 = destination.getAbsolutePath();
                break;
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

        }
    }
}