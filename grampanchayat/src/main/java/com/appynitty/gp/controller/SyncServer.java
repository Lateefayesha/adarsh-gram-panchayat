package com.appynitty.gp.controller;

import android.content.Context;

import com.appynitty.gp.pojo.ApplyBusinessPojo;
import com.appynitty.gp.pojo.ApplyJobPojo;
import com.appynitty.gp.pojo.CertificatePojo;
import com.appynitty.gp.pojo.CleaningCompleantPojo;
import com.appynitty.gp.pojo.ComplentStatusPojo;
import com.appynitty.gp.pojo.ContactUs;
import com.appynitty.gp.pojo.ContactUsPojo;
import com.appynitty.gp.pojo.ContactUsTeamMember;
import com.appynitty.gp.pojo.DistrictPojo;
import com.appynitty.gp.pojo.FcmIdPojo;
import com.appynitty.gp.pojo.GalleryPojo;
import com.appynitty.gp.pojo.GramPanchayatPojo;
import com.appynitty.gp.pojo.OurGramPanchayatPojo;
import com.appynitty.gp.pojo.PhotoGalleryImages;
import com.appynitty.gp.pojo.PhotoGalleryVideo;
import com.appynitty.gp.pojo.ResultPojo;
import com.appynitty.gp.pojo.SchemesPojo;
import com.appynitty.gp.pojo.SocialNetworkPojo;
import com.appynitty.gp.pojo.StatePojo;
import com.appynitty.gp.pojo.SuggestionPojo;
import com.appynitty.gp.pojo.TahsilPojo;
import com.appynitty.gp.pojo.TankerBookingPojo;
import com.appynitty.gp.pojo.WorkCheckOutPojo;
import com.appynitty.gp.pojo.YoungBusinessPojo;
import com.appynitty.gp.utils.AUtils;
import com.appynitty.gp.webservices.CertificateWebservice;
import com.appynitty.gp.webservices.CompleantWebservice;
import com.appynitty.gp.webservices.ContactUsWebservice;
import com.appynitty.gp.webservices.FcmIdWebservice;
import com.appynitty.gp.webservices.GalleryWebservice;
import com.appynitty.gp.webservices.OurGramPanchayatWebservice;
import com.appynitty.gp.webservices.SchemesWebservice;
import com.appynitty.gp.webservices.SmartGpWebservice;
import com.appynitty.gp.webservices.SocialNetworkWebservice;
import com.appynitty.gp.webservices.SuggestionWebservice;
import com.appynitty.gp.webservices.TankerBookingWebservice;
import com.appynitty.gp.webservices.WorkCheckOutWebservice;
import com.appynitty.gp.webservices.YoungBusinessWebservice;
import com.appynitty.gp.webservices.YoungJobWebservice;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import quickutils.core.QuickUtils;


/**
 * Created by MiTHUN on 7/2/18.
 */

public class SyncServer {

    private static final String TAG = "SyncServer";
    private Gson gson;
    private Context context;

    public SyncServer(Context context) {

        this.context = context;
        gson = new Gson();
    }

    public boolean pullWorkCheckOutListFromServer() {

        List<WorkCheckOutPojo> workCheckOutPojoList = null;

        try {

            WorkCheckOutWebservice service = AUtils.createService(WorkCheckOutWebservice.class, AUtils.SERVER_URL);
            workCheckOutPojoList = service.pullWorkCheckOutList(QuickUtils.prefs.getString(AUtils.APP_ID, ""),
                    QuickUtils.prefs.getString(AUtils.LANGUAGE_ID, AUtils.DEFAULT_LANGUAGE_ID),
                    "").execute().body();

            if (!AUtils.isNull(workCheckOutPojoList) && !workCheckOutPojoList.isEmpty()) {

                Type type = new TypeToken<List<WorkCheckOutPojo>>() {
                }.getType();
                QuickUtils.prefs.save(AUtils.PREFS.WORK_CHECK_OUT_POJO_LIST + QuickUtils.prefs.getString(AUtils.LANGUAGE_ID, AUtils.DEFAULT_LANGUAGE_ID), gson.toJson(workCheckOutPojoList, type));

                return true;
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
        return false;
    }

    public boolean pullYoungBusinessListFromServer() {

        List<YoungBusinessPojo> youngBusinessPojoList = null;

        try {

            YoungBusinessWebservice service = AUtils.createService(YoungBusinessWebservice.class, AUtils.SERVER_URL);
            youngBusinessPojoList = service.pullYoungBusinessList(QuickUtils.prefs.getString(AUtils.APP_ID, ""),
                    QuickUtils.prefs.getString(AUtils.LANGUAGE_ID, AUtils.DEFAULT_LANGUAGE_ID),
                    "").execute().body();

            if (!AUtils.isNull(youngBusinessPojoList) && !youngBusinessPojoList.isEmpty()) {

                Type type = new TypeToken<List<YoungBusinessPojo>>() {
                }.getType();
                QuickUtils.prefs.save(AUtils.PREFS.YOUNG_BUSINESS_POJO_LIST + QuickUtils.prefs.getString(AUtils.LANGUAGE_ID, AUtils.DEFAULT_LANGUAGE_ID), gson.toJson(youngBusinessPojoList, type));

                return true;
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
        return false;
    }

    public boolean pullSchemesListFromServer() {

        List<SchemesPojo> youngBusinessPojoList = null;

        try {

            SchemesWebservice service = AUtils.createService(SchemesWebservice.class, AUtils.SERVER_URL);
            youngBusinessPojoList = service.pullSchemesList(QuickUtils.prefs.getString(AUtils.APP_ID, ""),
                    QuickUtils.prefs.getString(AUtils.LANGUAGE_ID, AUtils.DEFAULT_LANGUAGE_ID),
                    "").execute().body();

            if (!AUtils.isNull(youngBusinessPojoList) && !youngBusinessPojoList.isEmpty()) {

                Type type = new TypeToken<List<YoungBusinessPojo>>() {
                }.getType();
                QuickUtils.prefs.save(AUtils.PREFS.SCHEMES_POJO_LIST + QuickUtils.prefs.getString(AUtils.LANGUAGE_ID, AUtils.DEFAULT_LANGUAGE_ID), gson.toJson(youngBusinessPojoList, type));

                return true;
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
        return false;
    }


    public boolean pullOurGramPanchayatListFromServer() {

        List<OurGramPanchayatPojo> youngBusinessPojoList = null;

        try {

            OurGramPanchayatWebservice service = AUtils.createService(OurGramPanchayatWebservice.class, AUtils.SERVER_URL);
            youngBusinessPojoList = service.pullOurGramPanchayatList(QuickUtils.prefs.getString(AUtils.APP_ID, ""),
                    QuickUtils.prefs.getString(AUtils.LANGUAGE_ID, AUtils.DEFAULT_LANGUAGE_ID),
                    "").execute().body();

            if (!AUtils.isNull(youngBusinessPojoList) && !youngBusinessPojoList.isEmpty()) {

                Type type = new TypeToken<List<YoungBusinessPojo>>() {
                }.getType();
                QuickUtils.prefs.save(AUtils.PREFS.OUR_GRAM_PANCHAYT_POJO_LIST + QuickUtils.prefs.getString(AUtils.LANGUAGE_ID, AUtils.DEFAULT_LANGUAGE_ID), gson.toJson(youngBusinessPojoList, type));

                return true;
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
        return false;
    }

    public boolean pullGalleryListFromServer() {

        GalleryPojo galleryPojo = null;

        try {

            GalleryWebservice service = AUtils.createService(GalleryWebservice.class, AUtils.SERVER_URL);
            galleryPojo = service.pullGalleryList(QuickUtils.prefs.getString(AUtils.APP_ID, ""),
                    QuickUtils.prefs.getString(AUtils.LANGUAGE_ID, AUtils.DEFAULT_LANGUAGE_ID),
                    "").execute().body();

            if (!AUtils.isNull(galleryPojo)) {

                List<PhotoGalleryImages> photoGalleryImages = galleryPojo.getPhotoGalleryImages();
                List<PhotoGalleryVideo> photoGalleryVideo = galleryPojo.getPhotoGalleryVideo();


                Type typeImages = new TypeToken<List<PhotoGalleryImages>>() {
                }.getType();
                QuickUtils.prefs.save(AUtils.PREFS.IMAGES_GALLERY_POJO_LIST, gson.toJson(photoGalleryImages, typeImages));

                Type typeVedio = new TypeToken<List<PhotoGalleryVideo>>() {
                }.getType();
                QuickUtils.prefs.save(AUtils.PREFS.VEDIO_GALLERY_POJO_LIST, gson.toJson(photoGalleryVideo, typeVedio));

                return true;
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
        return false;
    }

    public boolean pullSocialNetworkListFromServer() {

        List<SocialNetworkPojo> socialNetworkPojoList = null;

        try {

            SocialNetworkWebservice service = AUtils.createService(SocialNetworkWebservice.class, AUtils.SERVER_URL);
            socialNetworkPojoList = service.pullSocialNetworkList(QuickUtils.prefs.getString(AUtils.APP_ID, ""),
                    "1",
                    "").execute().body();

            if (!AUtils.isNull(socialNetworkPojoList) && !socialNetworkPojoList.isEmpty()) {

                Type type = new TypeToken<List<SocialNetworkPojo>>() {
                }.getType();
                QuickUtils.prefs.save(AUtils.PREFS.SOCIAL_NETWORK_POJO_LIST, gson.toJson(socialNetworkPojoList, type));

                return true;
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
        return false;
    }

    public boolean pullContactUsListFromServer() {

        ContactUsPojo contactUsPojo = null;

        try {

            ContactUsWebservice service = AUtils.createService(ContactUsWebservice.class, AUtils.SERVER_URL);
            contactUsPojo = service.pullContactUsList(QuickUtils.prefs.getString(AUtils.APP_ID, ""),
                    QuickUtils.prefs.getString(AUtils.LANGUAGE_ID, AUtils.DEFAULT_LANGUAGE_ID),
                    "").execute().body();

            if (!AUtils.isNull(contactUsPojo)) {

                List<ContactUsTeamMember> contactUsTeamMember = contactUsPojo.getContactUsTeamMember();
                ContactUs contactUs = contactUsPojo.getContactUs();


                Type typeImages = new TypeToken<List<ContactUsTeamMember>>() {
                }.getType();
                QuickUtils.prefs.save(AUtils.PREFS.CONTACT_US_MEMBER_POJO_LIST + QuickUtils.prefs.getString(AUtils.LANGUAGE_ID, AUtils.DEFAULT_LANGUAGE_ID), gson.toJson(contactUsTeamMember, typeImages));

                QuickUtils.prefs.save(AUtils.PREFS.CONTACT_US_POJO + QuickUtils.prefs.getString(AUtils.LANGUAGE_ID, AUtils.DEFAULT_LANGUAGE_ID), gson.toJson(contactUs, ContactUs.class));

                return true;
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
        return false;
    }

    public ResultPojo saveYoungBusinessApplication(ApplyBusinessPojo applyBusinessPojo) {

        ResultPojo resultPojo = null;

        try {

            RequestBody requestBody1 = null;
            MultipartBody.Part imageFileMultiBody1 = null;
            RequestBody latLog1 = null;


            if (!AUtils.isNull(applyBusinessPojo.getImageFilePath1())) {
                File startImageFile = new File(applyBusinessPojo.getImageFilePath1());
                requestBody1 = RequestBody.create(MediaType.parse("multipart/form-data"), startImageFile);
                imageFileMultiBody1 = MultipartBody.Part.createFormData("vmImage1", startImageFile.getName(), requestBody1);
                latLog1 = RequestBody.create(okhttp3.MultipartBody.FORM, QuickUtils.prefs.getString(AUtils.LOCATION, ""));
            }

            RequestBody requestBody2 = null;
            MultipartBody.Part imageFileMultiBody2 = null;
            RequestBody latLog2 = null;

            if (!AUtils.isNull(applyBusinessPojo.getImageFilePath2())) {
                File startImageFile = new File(applyBusinessPojo.getImageFilePath2());
                requestBody2 = RequestBody.create(MediaType.parse("multipart/form-data"), startImageFile);
                imageFileMultiBody2 = MultipartBody.Part.createFormData("vmImage1", startImageFile.getName(), requestBody2);
                latLog2 = RequestBody.create(okhttp3.MultipartBody.FORM, QuickUtils.prefs.getString(AUtils.LOCATION, ""));
            }

            RequestBody requestBody3 = null;
            MultipartBody.Part imageFileMultiBody3 = null;
            RequestBody latLog3 = null;

            if (!AUtils.isNull(applyBusinessPojo.getImageFilePath3())) {
                File startImageFile = new File(applyBusinessPojo.getImageFilePath3());
                requestBody3 = RequestBody.create(MediaType.parse("multipart/form-data"), startImageFile);
                imageFileMultiBody3 = MultipartBody.Part.createFormData("vmImage1", startImageFile.getName(), requestBody3);
                latLog3 = RequestBody.create(okhttp3.MultipartBody.FORM, QuickUtils.prefs.getString(AUtils.LOCATION, ""));
            }

            RequestBody requestBody4 = null;
            MultipartBody.Part imageFileMultiBody4 = null;
            RequestBody latLog4 = null;

            if (!AUtils.isNull(applyBusinessPojo.getImageFilePath4())) {
                File startImageFile = new File(applyBusinessPojo.getImageFilePath4());
                requestBody4 = RequestBody.create(MediaType.parse("multipart/form-data"), startImageFile);
                imageFileMultiBody4 = MultipartBody.Part.createFormData("vmImage1", startImageFile.getName(), requestBody4);
                latLog4 = RequestBody.create(okhttp3.MultipartBody.FORM, QuickUtils.prefs.getString(AUtils.LOCATION, ""));
            }

            RequestBody yBId = RequestBody.create(okhttp3.MultipartBody.FORM, "0");
            RequestBody name = RequestBody.create(okhttp3.MultipartBody.FORM, applyBusinessPojo.getName());
            RequestBody number = RequestBody.create(okhttp3.MultipartBody.FORM, applyBusinessPojo.getNumber());
            RequestBody address = RequestBody.create(okhttp3.MultipartBody.FORM, applyBusinessPojo.getAddress());
            RequestBody education = RequestBody.create(okhttp3.MultipartBody.FORM, applyBusinessPojo.getEducationQulification());

            RequestBody email = null;
            if (!AUtils.isNullString(applyBusinessPojo.getEmail())) {
                email = RequestBody.create(okhttp3.MultipartBody.FORM, applyBusinessPojo.getEmail());
            }

            RequestBody adharCardNo = null;
            if (!AUtils.isNullString(applyBusinessPojo.getAdharCardNo())) {
                adharCardNo = RequestBody.create(okhttp3.MultipartBody.FORM, applyBusinessPojo.getAdharCardNo());
            }

            RequestBody otherSkill = null;
            if (!AUtils.isNullString(applyBusinessPojo.getOtherSkill())) {
                otherSkill = RequestBody.create(okhttp3.MultipartBody.FORM, applyBusinessPojo.getOtherSkill());
            }

            RequestBody interestedBusiness = null;
            if (!AUtils.isNullString(applyBusinessPojo.getInterestedBusiness())) {
                interestedBusiness = RequestBody.create(okhttp3.MultipartBody.FORM, applyBusinessPojo.getInterestedBusiness());
            }

            RequestBody aboutYourself = null;
            if (!AUtils.isNullString(applyBusinessPojo.getAboutYourself())) {
                aboutYourself = RequestBody.create(okhttp3.MultipartBody.FORM, applyBusinessPojo.getAboutYourself());
            }

            RequestBody resourceAvailable = null;
            if (!AUtils.isNullString(applyBusinessPojo.getResourceAvailable())) {
                resourceAvailable = RequestBody.create(okhttp3.MultipartBody.FORM, applyBusinessPojo.getResourceAvailable());
            }

            RequestBody helpRequired = null;
            if (!AUtils.isNullString(applyBusinessPojo.getHelpRequired())) {
                helpRequired = RequestBody.create(okhttp3.MultipartBody.FORM, applyBusinessPojo.getHelpRequired());
            }

            RequestBody languageId = RequestBody.create(okhttp3.MultipartBody.FORM, "1");
            RequestBody createdDate = RequestBody.create(okhttp3.MultipartBody.FORM, AUtils.getCurrentDateTime());

            YoungBusinessWebservice service = AUtils.createService(YoungBusinessWebservice.class, AUtils.SERVER_URL);

            resultPojo = service.saveBusinessApplication(QuickUtils.prefs.getString(AUtils.APP_ID, ""), yBId,
                    name, number, address, education, email, adharCardNo, otherSkill,
                    interestedBusiness, aboutYourself, languageId, createdDate,
                    resourceAvailable, helpRequired,
                    latLog1, latLog2, latLog3, latLog4,
                    imageFileMultiBody1, imageFileMultiBody2, imageFileMultiBody3, imageFileMultiBody4).execute().body();

        } catch (Exception e) {

            e.printStackTrace();
        }
        return resultPojo;
    }

    public ResultPojo saveTankerBooking(TankerBookingPojo tankerBookingPojo) {

        ResultPojo resultPojo = null;
        try {

            TankerBookingWebservice service = AUtils.createService(TankerBookingWebservice.class, AUtils.SERVER_URL);
            resultPojo = service.saveTankerBooking(QuickUtils.prefs.getString(AUtils.APP_ID, ""), tankerBookingPojo).execute().body();

        } catch (Exception e) {

            resultPojo = null;
            e.printStackTrace();
        }
        return resultPojo;
    }

    public ResultPojo saveCleaningCompleant(CleaningCompleantPojo cleaningCompleantPojo) {

        ResultPojo resultPojo = null;

        try {

            RequestBody requestBody1 = null;
            MultipartBody.Part imageFileMultiBody1 = null;
            RequestBody latLog = null;

            File startImageFile = new File(cleaningCompleantPojo.getImgUrl());

            requestBody1 = RequestBody.create(MediaType.parse("multipart/form-data"), startImageFile);
            imageFileMultiBody1 = MultipartBody.Part.createFormData("vmImage1", startImageFile.getName(), requestBody1);
            latLog = RequestBody.create(okhttp3.MultipartBody.FORM, QuickUtils.prefs.getString(AUtils.LOCATION, ""));


            RequestBody name = RequestBody.create(okhttp3.MultipartBody.FORM, cleaningCompleantPojo.getName());
            RequestBody number = RequestBody.create(okhttp3.MultipartBody.FORM, cleaningCompleantPojo.getNumber());

            RequestBody tip = null;
            if (!AUtils.isNullString(cleaningCompleantPojo.getTip())) {
                tip = RequestBody.create(okhttp3.MultipartBody.FORM, cleaningCompleantPojo.getTip());
            }

            RequestBody address = null;
            if (!AUtils.isNullString(cleaningCompleantPojo.getAddress())) {
                address = RequestBody.create(okhttp3.MultipartBody.FORM, cleaningCompleantPojo.getAddress());
            }

            RequestBody email = null;
            if (!AUtils.isNullString(cleaningCompleantPojo.getEmail())) {
                email = RequestBody.create(okhttp3.MultipartBody.FORM, cleaningCompleantPojo.getEmail());
            }

            RequestBody userId = null;
            if (!AUtils.isNullString(QuickUtils.prefs.getString(AUtils.USER_ID, ""))) {
                userId = RequestBody.create(okhttp3.MultipartBody.FORM, QuickUtils.prefs.getString(AUtils.USER_ID, ""));
            }

            RequestBody wardNo = RequestBody.create(okhttp3.MultipartBody.FORM, cleaningCompleantPojo.getWardNo());
            RequestBody location = RequestBody.create(okhttp3.MultipartBody.FORM, cleaningCompleantPojo.getLocation());
            RequestBody details = RequestBody.create(okhttp3.MultipartBody.FORM, cleaningCompleantPojo.getDetails());
            RequestBody languageId = RequestBody.create(okhttp3.MultipartBody.FORM, "1");
            RequestBody createdDate = RequestBody.create(okhttp3.MultipartBody.FORM, AUtils.getCurrentDateTime());

            CompleantWebservice service = AUtils.createService(CompleantWebservice.class, AUtils.SERVER_URL);
            resultPojo = service.saveCleaningCompleant(QuickUtils.prefs.getString(AUtils.APP_ID, ""),
                    name, number, address, tip, email, wardNo, location, details, languageId, createdDate,
                    latLog, userId, imageFileMultiBody1).execute().body();


        } catch (Exception e) {

            e.printStackTrace();
        }
        return resultPojo;
    }

    public ResultPojo saveDrinkingWaterCompleant(CleaningCompleantPojo cleaningCompleantPojo) {

        ResultPojo resultPojo = null;

        try {

            RequestBody requestBody1 = null;
            MultipartBody.Part imageFileMultiBody1 = null;
            RequestBody latLog = null;

            File startImageFile = new File(cleaningCompleantPojo.getImgUrl());

            requestBody1 = RequestBody.create(MediaType.parse("multipart/form-data"), startImageFile);
            imageFileMultiBody1 = MultipartBody.Part.createFormData("vmImage1", startImageFile.getName(), requestBody1);
            latLog = RequestBody.create(okhttp3.MultipartBody.FORM, QuickUtils.prefs.getString(AUtils.LOCATION, ""));

            RequestBody name = RequestBody.create(okhttp3.MultipartBody.FORM, cleaningCompleantPojo.getName());
            RequestBody number = RequestBody.create(okhttp3.MultipartBody.FORM, cleaningCompleantPojo.getNumber());

            RequestBody address = null;
            if (!AUtils.isNullString(cleaningCompleantPojo.getAddress())) {
                address = RequestBody.create(okhttp3.MultipartBody.FORM, cleaningCompleantPojo.getAddress());
            }

            RequestBody tip = null;
            if (!AUtils.isNullString(cleaningCompleantPojo.getTip())) {
                tip = RequestBody.create(okhttp3.MultipartBody.FORM, cleaningCompleantPojo.getTip());
            }

            RequestBody email = null;
            if (!AUtils.isNullString(cleaningCompleantPojo.getEmail())) {
                email = RequestBody.create(okhttp3.MultipartBody.FORM, cleaningCompleantPojo.getEmail());
            }

            RequestBody userId = null;
            if (!AUtils.isNullString(QuickUtils.prefs.getString(AUtils.USER_ID, ""))) {
                userId = RequestBody.create(okhttp3.MultipartBody.FORM, QuickUtils.prefs.getString(AUtils.USER_ID, ""));
            }

            RequestBody wardNo = RequestBody.create(okhttp3.MultipartBody.FORM, cleaningCompleantPojo.getWardNo());
            RequestBody location = RequestBody.create(okhttp3.MultipartBody.FORM, cleaningCompleantPojo.getLocation());
            RequestBody details = RequestBody.create(okhttp3.MultipartBody.FORM, cleaningCompleantPojo.getDetails());
            RequestBody languageId = RequestBody.create(okhttp3.MultipartBody.FORM, "1");
            RequestBody createdDate = RequestBody.create(okhttp3.MultipartBody.FORM, AUtils.getCurrentDateTime());

            CompleantWebservice service = AUtils.createService(CompleantWebservice.class, AUtils.SERVER_URL);
            resultPojo = service.saveDrinkingWaterCompleant(QuickUtils.prefs.getString(AUtils.APP_ID, ""),
                    name, number, address, tip, email, wardNo, location, details, languageId, createdDate,
                    latLog, userId, imageFileMultiBody1).execute().body();


        } catch (Exception e) {

            e.printStackTrace();
        }
        return resultPojo;
    }

    public ResultPojo saveLightCompleant(CleaningCompleantPojo cleaningCompleantPojo) {

        ResultPojo resultPojo = null;

        try {

            RequestBody requestBody1 = null;
            MultipartBody.Part imageFileMultiBody1 = null;
            RequestBody latLog = null;

            File startImageFile = new File(cleaningCompleantPojo.getImgUrl());

            requestBody1 = RequestBody.create(MediaType.parse("multipart/form-data"), startImageFile);
            imageFileMultiBody1 = MultipartBody.Part.createFormData("vmImage1", startImageFile.getName(), requestBody1);
            latLog = RequestBody.create(okhttp3.MultipartBody.FORM, QuickUtils.prefs.getString(AUtils.LOCATION, ""));

            RequestBody name = RequestBody.create(okhttp3.MultipartBody.FORM, cleaningCompleantPojo.getName());
            RequestBody number = RequestBody.create(okhttp3.MultipartBody.FORM, cleaningCompleantPojo.getNumber());

            RequestBody address = null;
            if (!AUtils.isNullString(cleaningCompleantPojo.getAddress())) {
                address = RequestBody.create(okhttp3.MultipartBody.FORM, cleaningCompleantPojo.getAddress());
            }

            RequestBody tip = null;
            if (!AUtils.isNullString(cleaningCompleantPojo.getTip())) {
                tip = RequestBody.create(okhttp3.MultipartBody.FORM, cleaningCompleantPojo.getTip());
            }

            RequestBody email = null;
            if (!AUtils.isNullString(cleaningCompleantPojo.getEmail())) {
                email = RequestBody.create(okhttp3.MultipartBody.FORM, cleaningCompleantPojo.getEmail());
            }

            RequestBody userId = null;
            if (!AUtils.isNullString(QuickUtils.prefs.getString(AUtils.USER_ID, ""))) {
                userId = RequestBody.create(okhttp3.MultipartBody.FORM, QuickUtils.prefs.getString(AUtils.USER_ID, ""));
            }

            RequestBody wardNo = RequestBody.create(okhttp3.MultipartBody.FORM, cleaningCompleantPojo.getWardNo());
            RequestBody location = RequestBody.create(okhttp3.MultipartBody.FORM, cleaningCompleantPojo.getLocation());
            RequestBody details = RequestBody.create(okhttp3.MultipartBody.FORM, cleaningCompleantPojo.getDetails());
            RequestBody languageId = RequestBody.create(okhttp3.MultipartBody.FORM, "1");
            RequestBody createdDate = RequestBody.create(okhttp3.MultipartBody.FORM, AUtils.getCurrentDateTime());

            CompleantWebservice service = AUtils.createService(CompleantWebservice.class, AUtils.SERVER_URL);
            resultPojo = service.saveLightCompleant(QuickUtils.prefs.getString(AUtils.APP_ID, ""),
                    name, number, address, tip, email, wardNo, location, details, languageId, createdDate,
                    latLog, userId,
                    imageFileMultiBody1).execute().body();


        } catch (Exception e) {

            e.printStackTrace();
        }
        return resultPojo;
    }

    public ResultPojo saveMaintenanceCompleant(CleaningCompleantPojo cleaningCompleantPojo) {

        ResultPojo resultPojo = null;

        try {

            RequestBody requestBody1 = null;
            MultipartBody.Part imageFileMultiBody1 = null;
            RequestBody latLog = null;

            File startImageFile = new File(cleaningCompleantPojo.getImgUrl());

            requestBody1 = RequestBody.create(MediaType.parse("multipart/form-data"), startImageFile);
            imageFileMultiBody1 = MultipartBody.Part.createFormData("vmImage1", startImageFile.getName(), requestBody1);
            latLog = RequestBody.create(okhttp3.MultipartBody.FORM, QuickUtils.prefs.getString(AUtils.LOCATION, ""));

            RequestBody name = RequestBody.create(okhttp3.MultipartBody.FORM, cleaningCompleantPojo.getName());
            RequestBody number = RequestBody.create(okhttp3.MultipartBody.FORM, cleaningCompleantPojo.getNumber());

            RequestBody address = null;
            if (!AUtils.isNullString(cleaningCompleantPojo.getAddress())) {
                address = RequestBody.create(okhttp3.MultipartBody.FORM, cleaningCompleantPojo.getAddress());
            }

            RequestBody tip = null;
            if (!AUtils.isNullString(cleaningCompleantPojo.getTip())) {
                tip = RequestBody.create(okhttp3.MultipartBody.FORM, cleaningCompleantPojo.getTip());
            }

            RequestBody email = null;
            if (!AUtils.isNullString(cleaningCompleantPojo.getEmail())) {
                email = RequestBody.create(okhttp3.MultipartBody.FORM, cleaningCompleantPojo.getEmail());
            }

            RequestBody userId = null;
            if (!AUtils.isNullString(QuickUtils.prefs.getString(AUtils.USER_ID, ""))) {
                userId = RequestBody.create(okhttp3.MultipartBody.FORM, QuickUtils.prefs.getString(AUtils.USER_ID, ""));
            }

            RequestBody wardNo = RequestBody.create(okhttp3.MultipartBody.FORM, cleaningCompleantPojo.getWardNo());
            RequestBody location = RequestBody.create(okhttp3.MultipartBody.FORM, cleaningCompleantPojo.getLocation());
            RequestBody details = RequestBody.create(okhttp3.MultipartBody.FORM, cleaningCompleantPojo.getDetails());
            RequestBody languageId = RequestBody.create(okhttp3.MultipartBody.FORM, "1");
            RequestBody createdDate = RequestBody.create(okhttp3.MultipartBody.FORM, AUtils.getCurrentDateTime());

            CompleantWebservice service = AUtils.createService(CompleantWebservice.class, AUtils.SERVER_URL);
            resultPojo = service.saveMaintenanceCompleant(QuickUtils.prefs.getString(AUtils.APP_ID, ""),
                    name, number, address, tip, email, wardNo, location, details, languageId, createdDate,
                    latLog, userId, imageFileMultiBody1).execute().body();


        } catch (Exception e) {

            e.printStackTrace();
        }
        return resultPojo;
    }

    public ResultPojo saveJobApplication(ApplyJobPojo applyJobPojo) {

        ResultPojo resultPojo = null;

        try {

            RequestBody requestBody1 = null;
            MultipartBody.Part imageFileMultiBody1 = null;
            RequestBody latLog1 = null;

            if (!AUtils.isNull(applyJobPojo.getImageFilePath1())) {
                File startImageFile = new File(applyJobPojo.getImageFilePath1());
                requestBody1 = RequestBody.create(MediaType.parse("multipart/form-data"), startImageFile);
                imageFileMultiBody1 = MultipartBody.Part.createFormData("vmImage1", startImageFile.getName(), requestBody1);
                latLog1 = RequestBody.create(okhttp3.MultipartBody.FORM, QuickUtils.prefs.getString(AUtils.LOCATION, ""));
            }

            RequestBody requestBody2 = null;
            MultipartBody.Part imageFileMultiBody2 = null;
            RequestBody latLog2 = null;

            if (!AUtils.isNull(applyJobPojo.getImageFilePath2())) {
                File startImageFile = new File(applyJobPojo.getImageFilePath2());
                requestBody2 = RequestBody.create(MediaType.parse("multipart/form-data"), startImageFile);
                imageFileMultiBody2 = MultipartBody.Part.createFormData("vmImage1", startImageFile.getName(), requestBody2);
                latLog2 = RequestBody.create(okhttp3.MultipartBody.FORM, QuickUtils.prefs.getString(AUtils.LOCATION, ""));
            }

            RequestBody requestBody3 = null;
            MultipartBody.Part imageFileMultiBody3 = null;
            RequestBody latLog3 = null;

            if (!AUtils.isNull(applyJobPojo.getImageFilePath3())) {
                File startImageFile = new File(applyJobPojo.getImageFilePath3());
                requestBody3 = RequestBody.create(MediaType.parse("multipart/form-data"), startImageFile);
                imageFileMultiBody3 = MultipartBody.Part.createFormData("vmImage1", startImageFile.getName(), requestBody3);
                latLog3 = RequestBody.create(okhttp3.MultipartBody.FORM, QuickUtils.prefs.getString(AUtils.LOCATION, ""));
            }

            RequestBody requestBody4 = null;
            MultipartBody.Part imageFileMultiBody4 = null;
            RequestBody latLog4 = null;

            if (!AUtils.isNull(applyJobPojo.getImageFilePath4())) {
                File startImageFile = new File(applyJobPojo.getImageFilePath4());
                requestBody4 = RequestBody.create(MediaType.parse("multipart/form-data"), startImageFile);
                imageFileMultiBody4 = MultipartBody.Part.createFormData("vmImage1", startImageFile.getName(), requestBody4);
                latLog4 = RequestBody.create(okhttp3.MultipartBody.FORM, QuickUtils.prefs.getString(AUtils.LOCATION, ""));
            }

            RequestBody resumRequestBody = null;
            MultipartBody.Part resumeFileMultiBody = null;

            if (!AUtils.isNull(applyJobPojo.getResumeFilePath())) {
                File startImageFile = new File(applyJobPojo.getResumeFilePath());
                resumRequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), startImageFile);
                resumeFileMultiBody = MultipartBody.Part.createFormData("vmResume", startImageFile.getName(), resumRequestBody);
            }

            RequestBody name = RequestBody.create(okhttp3.MultipartBody.FORM, applyJobPojo.getName());
            RequestBody number = RequestBody.create(okhttp3.MultipartBody.FORM, applyJobPojo.getNumber());
            RequestBody address = RequestBody.create(okhttp3.MultipartBody.FORM, applyJobPojo.getAddress());
            RequestBody education = RequestBody.create(okhttp3.MultipartBody.FORM, applyJobPojo.getEducationQulification());

            RequestBody email = null;
            if (!AUtils.isNullString(applyJobPojo.getEmail())) {
                email = RequestBody.create(okhttp3.MultipartBody.FORM, applyJobPojo.getEmail());
            }

            RequestBody adharCardNo = null;
            if (!AUtils.isNullString(applyJobPojo.getAdharCardNo())) {
                adharCardNo = RequestBody.create(okhttp3.MultipartBody.FORM, applyJobPojo.getAdharCardNo());
            }

            RequestBody otherSkill = null;
            if (!AUtils.isNullString(applyJobPojo.getOtherSkill())) {
                otherSkill = RequestBody.create(okhttp3.MultipartBody.FORM, applyJobPojo.getOtherSkill());
            }

            RequestBody interestedJob = null;
            if (!AUtils.isNullString(applyJobPojo.getInterestedJob())) {
                interestedJob = RequestBody.create(okhttp3.MultipartBody.FORM, applyJobPojo.getInterestedJob());
            }

            RequestBody aboutYourself = null;
            if (!AUtils.isNullString(applyJobPojo.getAboutYourself())) {
                aboutYourself = RequestBody.create(okhttp3.MultipartBody.FORM, applyJobPojo.getAboutYourself());
            }

            RequestBody imageUrl = null;
            if (!AUtils.isNullString(applyJobPojo.getImageFilePath1())) {
                imageUrl = RequestBody.create(okhttp3.MultipartBody.FORM, applyJobPojo.getImageFilePath1());
            }

            RequestBody resumeUrl = null;
            if (!AUtils.isNullString(applyJobPojo.getResumeFilePath())) {
                resumeUrl = RequestBody.create(okhttp3.MultipartBody.FORM, applyJobPojo.getResumeFilePath());
            }

            RequestBody specialization = null;
//            if (!AUtils.isNullString(applyJobPojo.getResumeFilePath())) {
//                specialization = RequestBody.create(okhttp3.MultipartBody.FORM, applyJobPojo.getResumeFilePath());
//            }

            RequestBody languageId = RequestBody.create(okhttp3.MultipartBody.FORM, "1");
            RequestBody createdDate = RequestBody.create(okhttp3.MultipartBody.FORM, AUtils.getCurrentDateTime());

            YoungJobWebservice service = AUtils.createService(YoungJobWebservice.class, AUtils.SERVER_URL);

            resultPojo = service.saveYoungJobApplication(QuickUtils.prefs.getString(AUtils.APP_ID, ""),
                    name, number, address, education, email, adharCardNo, otherSkill,
                    interestedJob, aboutYourself, languageId, createdDate,
                    specialization,
                    latLog1, latLog2, latLog3, latLog4,
                    imageFileMultiBody1, imageFileMultiBody2, imageFileMultiBody3, imageFileMultiBody4,
                    resumeFileMultiBody).execute().body();

        } catch (Exception e) {

            e.printStackTrace();
        }
        return resultPojo;
    }

    public boolean pullCertificateListFromServer() {

        List<CertificatePojo> certificatePojoList = null;

        try {

            CertificateWebservice service = AUtils.createService(CertificateWebservice.class, AUtils.SERVER_URL);
            certificatePojoList = service.pullCertificateList(QuickUtils.prefs.getString(AUtils.APP_ID, ""),
                    QuickUtils.prefs.getString(AUtils.LANGUAGE_ID, AUtils.DEFAULT_LANGUAGE_ID),
                    "").execute().body();

            if (!AUtils.isNull(certificatePojoList) && !certificatePojoList.isEmpty()) {

                Type type = new TypeToken<List<CertificatePojo>>() {
                }.getType();
                QuickUtils.prefs.save(AUtils.PREFS.CERTIFICATE_POJO_LIST + QuickUtils.prefs.getString(AUtils.LANGUAGE_ID, AUtils.DEFAULT_LANGUAGE_ID), gson.toJson(certificatePojoList, type));

                return true;
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
        return false;
    }

    public boolean pullStateListFromServer() {

        List<StatePojo> statePojoList = null;

        try {

            SmartGpWebservice service = AUtils.createService(SmartGpWebservice.class, AUtils.SERVER_URL);
            statePojoList = service.pullStateList().execute().body();

            if (!AUtils.isNull(statePojoList) && !statePojoList.isEmpty()) {

                Type type = new TypeToken<List<StatePojo>>() {
                }.getType();
                QuickUtils.prefs.save(AUtils.PREFS.STATE_POJO_LIST, gson.toJson(statePojoList, type));

            } else {
                QuickUtils.prefs.save(AUtils.PREFS.STATE_POJO_LIST, null);
            }
            return true;
        } catch (Exception e) {

            e.printStackTrace();
        }
        return false;
    }

    public boolean pullDistrictListFromServer(String stateId) {

        List<DistrictPojo> districtPojoList = null;

        try {

            SmartGpWebservice service = AUtils.createService(SmartGpWebservice.class, AUtils.SERVER_URL);
            districtPojoList = service.pullDistrictList(stateId).execute().body();

            if (!AUtils.isNull(districtPojoList) && !districtPojoList.isEmpty()) {

                Type type = new TypeToken<List<DistrictPojo>>() {
                }.getType();
                QuickUtils.prefs.save(AUtils.PREFS.DISTRICT_POJO_LIST, gson.toJson(districtPojoList, type));

            } else {

                QuickUtils.prefs.save(AUtils.PREFS.DISTRICT_POJO_LIST, null);
            }
            return true;
        } catch (Exception e) {

            e.printStackTrace();
        }
        return false;
    }

    public boolean pullTahsilListFromServer(String districtId) {

        List<TahsilPojo> tahsilPojoList = null;

        try {

            SmartGpWebservice service = AUtils.createService(SmartGpWebservice.class, AUtils.SERVER_URL);
            tahsilPojoList = service.pullTahsilList(districtId).execute().body();

            if (!AUtils.isNull(tahsilPojoList) && !tahsilPojoList.isEmpty()) {

                Type type = new TypeToken<List<TahsilPojo>>() {
                }.getType();
                QuickUtils.prefs.save(AUtils.PREFS.TAHSIL_POJO_LIST, gson.toJson(tahsilPojoList, type));

            } else {

                QuickUtils.prefs.save(AUtils.PREFS.TAHSIL_POJO_LIST, null);
            }
            return true;
        } catch (Exception e) {

            e.printStackTrace();
        }
        return false;
    }

    public boolean pullGramPanchayatListFromServer(String stateId, String districtId, String tahsilId) {

        List<GramPanchayatPojo> gramPanchayatPojoList = null;

        try {

            SmartGpWebservice service = AUtils.createService(SmartGpWebservice.class, AUtils.SERVER_URL);
            gramPanchayatPojoList = service.pullGramPanchayatList(stateId, districtId, tahsilId, "gp").execute().body();

            if (!AUtils.isNull(gramPanchayatPojoList) && !gramPanchayatPojoList.isEmpty()) {

                Type type = new TypeToken<List<GramPanchayatPojo>>() {
                }.getType();
                QuickUtils.prefs.save(AUtils.PREFS.GRAM_PANCHAYAT_LIST, gson.toJson(gramPanchayatPojoList, type));

            } else {

                QuickUtils.prefs.save(AUtils.PREFS.GRAM_PANCHAYAT_LIST, null);
            }
            return true;
        } catch (Exception e) {

            e.printStackTrace();
        }
        return false;
    }

    public boolean saveFcmIdOnServer(FcmIdPojo fcmIdPojo) {

        ResultPojo resultPojo = null;

        try {

            FcmIdWebservice service = AUtils.createService(FcmIdWebservice.class, AUtils.SERVER_URL);
            resultPojo = service.saveFcmId(QuickUtils.prefs.getString(AUtils.APP_ID, ""), fcmIdPojo).execute().body();

            if (resultPojo.getStatus().equals(AUtils.STATUS_SUCCESS)) {
                return true;
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
        return false;

    }

    public ResultPojo saveSuggestion(SuggestionPojo suggestionPojo) {

        ResultPojo resultPojo = null;
        try {

            SuggestionWebservice service = AUtils.createService(SuggestionWebservice.class, AUtils.SERVER_URL);
            resultPojo = service.saveSuggestion(QuickUtils.prefs.getString(AUtils.APP_ID, ""), suggestionPojo).execute().body();

        } catch (Exception e) {

            resultPojo = null;
            e.printStackTrace();
        }
        return resultPojo;
    }

    public boolean pullMyComplentStatusListFromServer() {

        List<ComplentStatusPojo> complentStatusPojoList = null;

        try {

            CompleantWebservice service = AUtils.createService(CompleantWebservice.class, AUtils.SERVER_URL);
            complentStatusPojoList = service.pullMyStatusList(QuickUtils.prefs.getString(AUtils.APP_ID, ""),
                    QuickUtils.prefs.getString(AUtils.USER_ID, "")).execute().body();

            if (!AUtils.isNull(complentStatusPojoList) && !complentStatusPojoList.isEmpty()) {

                Type type = new TypeToken<List<ComplentStatusPojo>>() {
                }.getType();
                QuickUtils.prefs.save(AUtils.PREFS.MY_COMPLENT_STATUS_POJO_LIST + QuickUtils.prefs.getString(AUtils.LANGUAGE_ID, AUtils.DEFAULT_LANGUAGE_ID), gson.toJson(complentStatusPojoList, type));

                return true;
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
        return false;
    }
}