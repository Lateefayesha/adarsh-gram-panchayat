package com.appynitty.gp.controller;

import android.content.Context;
import android.util.Log;

import com.appynitty.gp.pojo.ActiveUserListPojo;
import com.appynitty.gp.pojo.ApplyBusinessPojo;
import com.appynitty.gp.pojo.ApplyJobPojo;
import com.appynitty.gp.pojo.AreaListPojo;
import com.appynitty.gp.pojo.CertificatePojo;
import com.appynitty.gp.pojo.ClassificationPojo;
import com.appynitty.gp.pojo.CleaningCompleantPojo;
import com.appynitty.gp.pojo.ComplaintTypePojo;
import com.appynitty.gp.pojo.ComplentStatusPojo;
import com.appynitty.gp.pojo.ContactUs;
import com.appynitty.gp.pojo.ContactUsPojo;
import com.appynitty.gp.pojo.ContactUsTeamMember;
import com.appynitty.gp.pojo.ContructionCompleantPojo;
import com.appynitty.gp.pojo.DistrictPojo;
import com.appynitty.gp.pojo.FcmIdPojo;
import com.appynitty.gp.pojo.GalleryPojo;
import com.appynitty.gp.pojo.GramPanchayatPojo;
import com.appynitty.gp.pojo.LeageaAnswerDetailsPojo;
import com.appynitty.gp.pojo.LeagueQuestionPojo;
import com.appynitty.gp.pojo.MandiPojo;
import com.appynitty.gp.pojo.OurGramPanchayatPojo;
import com.appynitty.gp.pojo.PhotoGalleryImages;
import com.appynitty.gp.pojo.PhotoGalleryVideo;
import com.appynitty.gp.pojo.PropertyTaxPojo;
import com.appynitty.gp.pojo.ResultPojo;
import com.appynitty.gp.pojo.SamajBavanBookingPojo;
import com.appynitty.gp.pojo.SchemesPojo;
import com.appynitty.gp.pojo.SocialNetworkPojo;
import com.appynitty.gp.pojo.StatePojo;
import com.appynitty.gp.pojo.SuggestionPojo;
import com.appynitty.gp.pojo.TahsilPojo;
import com.appynitty.gp.pojo.TankerBookingPojo;
import com.appynitty.gp.pojo.UpcomingEventsPojo;
import com.appynitty.gp.pojo.WorkCheckOutPojo;
import com.appynitty.gp.pojo.YoungBusinessPojo;
import com.appynitty.gp.utils.AUtils;
import com.appynitty.gp.webservices.CertificateWebservice;
import com.appynitty.gp.webservices.ClassificationWebservice;
import com.appynitty.gp.webservices.CompleantWebservice;
import com.appynitty.gp.webservices.ContactUsWebservice;
import com.appynitty.gp.webservices.FcmIdWebservice;
import com.appynitty.gp.webservices.GalleryWebservice;
import com.appynitty.gp.webservices.GhantaGadiTrackerWebservice;
import com.appynitty.gp.webservices.LeagueWebservice;
import com.appynitty.gp.webservices.MandiWebservice;
import com.appynitty.gp.webservices.OurGramPanchayatWebservice;
import com.appynitty.gp.webservices.PropertyTaxDetailsWebservice;
import com.appynitty.gp.webservices.SamajBavanBookingWebservice;
import com.appynitty.gp.webservices.SchemesWebservice;
import com.appynitty.gp.webservices.SmartGpWebservice;
import com.appynitty.gp.webservices.SocialNetworkWebservice;
import com.appynitty.gp.webservices.SuggestionWebservice;
import com.appynitty.gp.webservices.TankerBookingWebservice;
import com.appynitty.gp.webservices.UpcomingEventWebservice;
import com.appynitty.gp.webservices.VersionCheckWebService;
import com.appynitty.gp.webservices.WorkCheckOutWebservice;
import com.appynitty.gp.webservices.YoungBusinessWebservice;
import com.appynitty.gp.webservices.YoungJobWebservice;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pixplicity.easyprefs.library.Prefs;

import java.io.File;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

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
            workCheckOutPojoList = service.pullWorkCheckOutList(Prefs.getString(AUtils.APP_ID, ""),
                    Prefs.getString(AUtils.LANGUAGE_ID, AUtils.DEFAULT_LANGUAGE_ID),
                    "").execute().body();

            if (!AUtils.isNull(workCheckOutPojoList) && !workCheckOutPojoList.isEmpty()) {

                Type type = new TypeToken<List<WorkCheckOutPojo>>() {
                }.getType();
                Prefs.putString(AUtils.PREFS.WORK_CHECK_OUT_POJO_LIST + Prefs.getString(AUtils.LANGUAGE_ID, AUtils.DEFAULT_LANGUAGE_ID), gson.toJson(workCheckOutPojoList, type));

                return true;
            } else {

                Prefs.putString(AUtils.PREFS.WORK_CHECK_OUT_POJO_LIST + Prefs.getString(AUtils.LANGUAGE_ID, AUtils.DEFAULT_LANGUAGE_ID), null);
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
            youngBusinessPojoList = service.pullYoungBusinessList(Prefs.getString(AUtils.APP_ID, ""),
                    Prefs.getString(AUtils.LANGUAGE_ID, AUtils.DEFAULT_LANGUAGE_ID),
                    "").execute().body();

            if (!AUtils.isNull(youngBusinessPojoList) && !youngBusinessPojoList.isEmpty()) {

                Type type = new TypeToken<List<YoungBusinessPojo>>() {
                }.getType();
                Prefs.putString(AUtils.PREFS.YOUNG_BUSINESS_POJO_LIST + Prefs.getString(AUtils.LANGUAGE_ID, AUtils.DEFAULT_LANGUAGE_ID), gson.toJson(youngBusinessPojoList, type));

                return true;
            } else {

                Prefs.putString(AUtils.PREFS.YOUNG_BUSINESS_POJO_LIST + Prefs.getString(AUtils.LANGUAGE_ID, AUtils.DEFAULT_LANGUAGE_ID), null);
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
            youngBusinessPojoList = service.pullSchemesList(Prefs.getString(AUtils.APP_ID, ""),
                    Prefs.getString(AUtils.LANGUAGE_ID, AUtils.DEFAULT_LANGUAGE_ID),
                    "").execute().body();

            if (!AUtils.isNull(youngBusinessPojoList) && !youngBusinessPojoList.isEmpty()) {

                Type type = new TypeToken<List<YoungBusinessPojo>>() {
                }.getType();
                Prefs.putString(AUtils.PREFS.SCHEMES_POJO_LIST + Prefs.getString(AUtils.LANGUAGE_ID, AUtils.DEFAULT_LANGUAGE_ID), gson.toJson(youngBusinessPojoList, type));

                return true;
            } else {

                Prefs.putString(AUtils.PREFS.SCHEMES_POJO_LIST + Prefs.getString(AUtils.LANGUAGE_ID, AUtils.DEFAULT_LANGUAGE_ID), null);
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
            youngBusinessPojoList = service.pullOurGramPanchayatList(Prefs.getString(AUtils.APP_ID, ""),
                    Prefs.getString(AUtils.LANGUAGE_ID, AUtils.DEFAULT_LANGUAGE_ID),
                    "").execute().body();

            if (!AUtils.isNull(youngBusinessPojoList) && !youngBusinessPojoList.isEmpty()) {

                Type type = new TypeToken<List<YoungBusinessPojo>>() {
                }.getType();
                Prefs.putString(AUtils.PREFS.OUR_GRAM_PANCHAYT_POJO_LIST + Prefs.getString(AUtils.LANGUAGE_ID, AUtils.DEFAULT_LANGUAGE_ID), gson.toJson(youngBusinessPojoList, type));

                return true;
            } else {

                Prefs.putString(AUtils.PREFS.OUR_GRAM_PANCHAYT_POJO_LIST + Prefs.getString(AUtils.LANGUAGE_ID, AUtils.DEFAULT_LANGUAGE_ID), null);
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
            galleryPojo = service.pullGalleryList(Prefs.getString(AUtils.APP_ID, ""),
                    Prefs.getString(AUtils.LANGUAGE_ID, AUtils.DEFAULT_LANGUAGE_ID),
                    "").execute().body();

            if (!AUtils.isNull(galleryPojo)) {

                List<PhotoGalleryImages> photoGalleryImages = galleryPojo.getPhotoGalleryImages();
                List<PhotoGalleryVideo> photoGalleryVideo = galleryPojo.getPhotoGalleryVideo();


                Type typeImages = new TypeToken<List<PhotoGalleryImages>>() {
                }.getType();
                Prefs.putString(AUtils.PREFS.IMAGES_GALLERY_POJO_LIST, gson.toJson(photoGalleryImages, typeImages));

                Type typeVedio = new TypeToken<List<PhotoGalleryVideo>>() {
                }.getType();
                Prefs.putString(AUtils.PREFS.VEDIO_GALLERY_POJO_LIST, gson.toJson(photoGalleryVideo, typeVedio));

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
            socialNetworkPojoList = service.pullSocialNetworkList(Prefs.getString(AUtils.APP_ID, ""),
                    "1",
                    "").execute().body();

            if (!AUtils.isNull(socialNetworkPojoList) && !socialNetworkPojoList.isEmpty()) {

                Type type = new TypeToken<List<SocialNetworkPojo>>() {
                }.getType();
                Prefs.putString(AUtils.PREFS.SOCIAL_NETWORK_POJO_LIST, gson.toJson(socialNetworkPojoList, type));

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
            contactUsPojo = service.pullContactUsList(Prefs.getString(AUtils.APP_ID, ""),
                    Prefs.getString(AUtils.LANGUAGE_ID, AUtils.DEFAULT_LANGUAGE_ID),
                    "").execute().body();

            if (!AUtils.isNull(contactUsPojo)) {

                List<ContactUsTeamMember> contactUsTeamMember = contactUsPojo.getContactUsTeamMember();
                ContactUs contactUs = contactUsPojo.getContactUs();


                Type typeImages = new TypeToken<List<ContactUsTeamMember>>() {
                }.getType();
                Prefs.putString(AUtils.PREFS.CONTACT_US_MEMBER_POJO_LIST + Prefs.getString(AUtils.LANGUAGE_ID, AUtils.DEFAULT_LANGUAGE_ID), gson.toJson(contactUsTeamMember, typeImages));

                Prefs.putString(AUtils.PREFS.CONTACT_US_POJO + Prefs.getString(AUtils.LANGUAGE_ID, AUtils.DEFAULT_LANGUAGE_ID), gson.toJson(contactUs, ContactUs.class));

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
            RequestBody latLog1 = RequestBody.create(okhttp3.MultipartBody.FORM, Prefs.getString(AUtils.LOCATION, ""));


            if (!AUtils.isNull(applyBusinessPojo.getImageFilePath1())) {
                File startImageFile = new File(applyBusinessPojo.getImageFilePath1());
                requestBody1 = RequestBody.create(MediaType.parse("multipart/form-data"), startImageFile);
                imageFileMultiBody1 = MultipartBody.Part.createFormData("vmImage1", startImageFile.getName(), requestBody1);
            }

            RequestBody requestBody2 = null;
            MultipartBody.Part imageFileMultiBody2 = null;
            RequestBody latLog2 = RequestBody.create(okhttp3.MultipartBody.FORM, Prefs.getString(AUtils.LOCATION, ""));

            if (!AUtils.isNull(applyBusinessPojo.getImageFilePath2())) {
                File startImageFile = new File(applyBusinessPojo.getImageFilePath2());
                requestBody2 = RequestBody.create(MediaType.parse("multipart/form-data"), startImageFile);
                imageFileMultiBody2 = MultipartBody.Part.createFormData("vmImage1", startImageFile.getName(), requestBody2);
            }

            RequestBody requestBody3 = null;
            MultipartBody.Part imageFileMultiBody3 = null;
            RequestBody latLog3 = RequestBody.create(okhttp3.MultipartBody.FORM, Prefs.getString(AUtils.LOCATION, ""));

            if (!AUtils.isNull(applyBusinessPojo.getImageFilePath3())) {
                File startImageFile = new File(applyBusinessPojo.getImageFilePath3());
                requestBody3 = RequestBody.create(MediaType.parse("multipart/form-data"), startImageFile);
                imageFileMultiBody3 = MultipartBody.Part.createFormData("vmImage1", startImageFile.getName(), requestBody3);
            }

            RequestBody requestBody4 = null;
            MultipartBody.Part imageFileMultiBody4 = null;
            RequestBody latLog4 = RequestBody.create(okhttp3.MultipartBody.FORM, Prefs.getString(AUtils.LOCATION, ""));

            if (!AUtils.isNull(applyBusinessPojo.getImageFilePath4())) {
                File startImageFile = new File(applyBusinessPojo.getImageFilePath4());
                requestBody4 = RequestBody.create(MediaType.parse("multipart/form-data"), startImageFile);
                imageFileMultiBody4 = MultipartBody.Part.createFormData("vmImage1", startImageFile.getName(), requestBody4);
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

            resultPojo = service.saveBusinessApplication(Prefs.getString(AUtils.APP_ID, ""), yBId,
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
            resultPojo = service.saveTankerBooking(Prefs.getString(AUtils.APP_ID, ""), tankerBookingPojo).execute().body();

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
            latLog = RequestBody.create(okhttp3.MultipartBody.FORM, Prefs.getString(AUtils.LOCATION, ""));


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
            if (!AUtils.isNullString(Prefs.getString(AUtils.USER_ID, ""))) {
                Log.e(TAG, Prefs.getString(AUtils.USER_ID, ""));
                userId = RequestBody.create(okhttp3.MultipartBody.FORM, Prefs.getString(AUtils.USER_ID, ""));
            }

            RequestBody wardNo = RequestBody.create(okhttp3.MultipartBody.FORM, cleaningCompleantPojo.getWardNo());
            RequestBody location = RequestBody.create(okhttp3.MultipartBody.FORM, cleaningCompleantPojo.getLocation());
            RequestBody typeId = RequestBody.create(okhttp3.MultipartBody.FORM, cleaningCompleantPojo.getTypeId());
            RequestBody details = RequestBody.create(okhttp3.MultipartBody.FORM, cleaningCompleantPojo.getDetails());
            RequestBody languageId = RequestBody.create(okhttp3.MultipartBody.FORM, "1");
            RequestBody createdDate = RequestBody.create(okhttp3.MultipartBody.FORM, AUtils.getCurrentDateTime());

            CompleantWebservice service = AUtils.createService(CompleantWebservice.class, AUtils.SERVER_URL);
            resultPojo = service.saveCleaningCompleant(Prefs.getString(AUtils.APP_ID, ""),
                    name, number, address, tip, email, wardNo, location, details, languageId, createdDate,
                    latLog, userId, typeId, imageFileMultiBody1).execute().body();


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
            latLog = RequestBody.create(okhttp3.MultipartBody.FORM, Prefs.getString(AUtils.LOCATION, ""));

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
            if (!AUtils.isNullString(Prefs.getString(AUtils.USER_ID, ""))) {
                userId = RequestBody.create(okhttp3.MultipartBody.FORM, Prefs.getString(AUtils.USER_ID, ""));
            }

            RequestBody wardNo = RequestBody.create(okhttp3.MultipartBody.FORM, cleaningCompleantPojo.getWardNo());
            RequestBody location = RequestBody.create(okhttp3.MultipartBody.FORM, cleaningCompleantPojo.getLocation());
            RequestBody typeId = RequestBody.create(okhttp3.MultipartBody.FORM, cleaningCompleantPojo.getTypeId());
            RequestBody details = RequestBody.create(okhttp3.MultipartBody.FORM, cleaningCompleantPojo.getDetails());
            RequestBody languageId = RequestBody.create(okhttp3.MultipartBody.FORM, "1");
            RequestBody createdDate = RequestBody.create(okhttp3.MultipartBody.FORM, AUtils.getCurrentDateTime());

            CompleantWebservice service = AUtils.createService(CompleantWebservice.class, AUtils.SERVER_URL);
            resultPojo = service.saveDrinkingWaterCompleant(Prefs.getString(AUtils.APP_ID, ""),
                    name, number, address, tip, email, wardNo, location, details, languageId, createdDate,
                    latLog, userId, typeId, imageFileMultiBody1).execute().body();


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
            latLog = RequestBody.create(okhttp3.MultipartBody.FORM, Prefs.getString(AUtils.LOCATION, ""));

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
            if (!AUtils.isNullString(Prefs.getString(AUtils.USER_ID, ""))) {
                userId = RequestBody.create(okhttp3.MultipartBody.FORM, Prefs.getString(AUtils.USER_ID, ""));
            }

            RequestBody wardNo = RequestBody.create(okhttp3.MultipartBody.FORM, cleaningCompleantPojo.getWardNo());
            RequestBody location = RequestBody.create(okhttp3.MultipartBody.FORM, cleaningCompleantPojo.getLocation());
            RequestBody typeId = RequestBody.create(okhttp3.MultipartBody.FORM, cleaningCompleantPojo.getTypeId());
            RequestBody details = RequestBody.create(okhttp3.MultipartBody.FORM, cleaningCompleantPojo.getDetails());
            RequestBody languageId = RequestBody.create(okhttp3.MultipartBody.FORM, "1");
            RequestBody createdDate = RequestBody.create(okhttp3.MultipartBody.FORM, AUtils.getCurrentDateTime());

            CompleantWebservice service = AUtils.createService(CompleantWebservice.class, AUtils.SERVER_URL);
            resultPojo = service.saveLightCompleant(Prefs.getString(AUtils.APP_ID, ""),
                    name, number, address, tip, email, wardNo, location, details, languageId, createdDate,
                    latLog, userId, typeId,
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
            latLog = RequestBody.create(okhttp3.MultipartBody.FORM, Prefs.getString(AUtils.LOCATION, ""));

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
            if (!AUtils.isNullString(Prefs.getString(AUtils.USER_ID, ""))) {
                userId = RequestBody.create(okhttp3.MultipartBody.FORM, Prefs.getString(AUtils.USER_ID, ""));
            }

            RequestBody wardNo = RequestBody.create(okhttp3.MultipartBody.FORM, cleaningCompleantPojo.getWardNo());
            RequestBody location = RequestBody.create(okhttp3.MultipartBody.FORM, cleaningCompleantPojo.getLocation());
            RequestBody typeId = RequestBody.create(okhttp3.MultipartBody.FORM, cleaningCompleantPojo.getTypeId());
            RequestBody details = RequestBody.create(okhttp3.MultipartBody.FORM, cleaningCompleantPojo.getDetails());
            RequestBody languageId = RequestBody.create(okhttp3.MultipartBody.FORM, "1");
            RequestBody createdDate = RequestBody.create(okhttp3.MultipartBody.FORM, AUtils.getCurrentDateTime());

            CompleantWebservice service = AUtils.createService(CompleantWebservice.class, AUtils.SERVER_URL);
            resultPojo = service.saveMaintenanceCompleant(Prefs.getString(AUtils.APP_ID, ""),
                    name, number, address, tip, email, wardNo, location, details, languageId, createdDate,
                    latLog, userId, typeId, imageFileMultiBody1).execute().body();


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
            RequestBody latLog1 = RequestBody.create(okhttp3.MultipartBody.FORM, Prefs.getString(AUtils.LOCATION, ""));

            if (!AUtils.isNull(applyJobPojo.getImageFilePath1())) {
                File startImageFile = new File(applyJobPojo.getImageFilePath1());
                requestBody1 = RequestBody.create(MediaType.parse("multipart/form-data"), startImageFile);
                imageFileMultiBody1 = MultipartBody.Part.createFormData("vmImage1", startImageFile.getName(), requestBody1);
            }

            RequestBody requestBody2 = null;
            MultipartBody.Part imageFileMultiBody2 = null;
            RequestBody latLog2 = RequestBody.create(okhttp3.MultipartBody.FORM, Prefs.getString(AUtils.LOCATION, ""));

            if (!AUtils.isNull(applyJobPojo.getImageFilePath2())) {
                File startImageFile = new File(applyJobPojo.getImageFilePath2());
                requestBody2 = RequestBody.create(MediaType.parse("multipart/form-data"), startImageFile);
                imageFileMultiBody2 = MultipartBody.Part.createFormData("vmImage1", startImageFile.getName(), requestBody2);
            }

            RequestBody requestBody3 = null;
            MultipartBody.Part imageFileMultiBody3 = null;
            RequestBody latLog3 = RequestBody.create(okhttp3.MultipartBody.FORM, Prefs.getString(AUtils.LOCATION, ""));

            if (!AUtils.isNull(applyJobPojo.getImageFilePath3())) {
                File startImageFile = new File(applyJobPojo.getImageFilePath3());
                requestBody3 = RequestBody.create(MediaType.parse("multipart/form-data"), startImageFile);
                imageFileMultiBody3 = MultipartBody.Part.createFormData("vmImage1", startImageFile.getName(), requestBody3);
            }

            RequestBody requestBody4 = null;
            MultipartBody.Part imageFileMultiBody4 = null;
            RequestBody latLog4 = RequestBody.create(okhttp3.MultipartBody.FORM, Prefs.getString(AUtils.LOCATION, ""));

            if (!AUtils.isNull(applyJobPojo.getImageFilePath4())) {
                File startImageFile = new File(applyJobPojo.getImageFilePath4());
                requestBody4 = RequestBody.create(MediaType.parse("multipart/form-data"), startImageFile);
                imageFileMultiBody4 = MultipartBody.Part.createFormData("vmImage1", startImageFile.getName(), requestBody4);
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

            resultPojo = service.saveYoungJobApplication(Prefs.getString(AUtils.APP_ID, ""),
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
            certificatePojoList = service.pullCertificateList(Prefs.getString(AUtils.APP_ID, ""),
                    Prefs.getString(AUtils.LANGUAGE_ID, AUtils.DEFAULT_LANGUAGE_ID),
                    "").execute().body();

            if (!AUtils.isNull(certificatePojoList) && !certificatePojoList.isEmpty()) {

                Type type = new TypeToken<List<CertificatePojo>>() {
                }.getType();
                Prefs.putString(AUtils.PREFS.CERTIFICATE_POJO_LIST + Prefs.getString(AUtils.LANGUAGE_ID, AUtils.DEFAULT_LANGUAGE_ID), gson.toJson(certificatePojoList, type));

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
                Prefs.putString(AUtils.PREFS.STATE_POJO_LIST, gson.toJson(statePojoList, type));

            } else {
                Prefs.putString(AUtils.PREFS.STATE_POJO_LIST, null);
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
                Prefs.putString(AUtils.PREFS.DISTRICT_POJO_LIST, gson.toJson(districtPojoList, type));

            } else {

                Prefs.putString(AUtils.PREFS.DISTRICT_POJO_LIST, null);
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
                Prefs.putString(AUtils.PREFS.TAHSIL_POJO_LIST, gson.toJson(tahsilPojoList, type));

            } else {

                Prefs.putString(AUtils.PREFS.TAHSIL_POJO_LIST, null);
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
                Prefs.putString(AUtils.PREFS.GRAM_PANCHAYAT_LIST, gson.toJson(gramPanchayatPojoList, type));

            } else {

                Prefs.putString(AUtils.PREFS.GRAM_PANCHAYAT_LIST, null);
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
            resultPojo = service.saveFcmId(Prefs.getString(AUtils.APP_ID, ""), fcmIdPojo).execute().body();

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
            resultPojo = service.saveSuggestion(Prefs.getString(AUtils.APP_ID, ""), suggestionPojo).execute().body();

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
            complentStatusPojoList = service.pullMyStatusList(Prefs.getString(AUtils.APP_ID, ""),
                    Prefs.getString(AUtils.USER_ID, "")).execute().body();

            if (!AUtils.isNull(complentStatusPojoList) && !complentStatusPojoList.isEmpty()) {

                Type type = new TypeToken<List<ComplentStatusPojo>>() {
                }.getType();
                Prefs.putString(AUtils.PREFS.MY_COMPLENT_STATUS_POJO_LIST + Prefs.getString(AUtils.LANGUAGE_ID, AUtils.DEFAULT_LANGUAGE_ID), gson.toJson(complentStatusPojoList, type));

                return true;
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
        return false;
    }

    public ResultPojo saveSamajBavanBooking(SamajBavanBookingPojo samajBavanBookingPojo) {

        ResultPojo resultPojo = null;
        try {

            SamajBavanBookingWebservice service = AUtils.createService(SamajBavanBookingWebservice.class, AUtils.SERVER_URL);
            resultPojo = service.saveSamajBavanBooking(Prefs.getString(AUtils.APP_ID, ""), samajBavanBookingPojo).execute().body();

        } catch (Exception e) {

            resultPojo = null;
            e.printStackTrace();
        }
        return resultPojo;
    }

    public ResultPojo saveConstructionCompleant(ContructionCompleantPojo contructionCompleantPojo) {

        ResultPojo resultPojo = null;

        try {

            RequestBody requestBody1 = null;
            MultipartBody.Part imageFileMultiBody1 = null;
            RequestBody latLog = null;

            File startImageFile = new File(contructionCompleantPojo.getImgUrl());

            requestBody1 = RequestBody.create(MediaType.parse("multipart/form-data"), startImageFile);
            imageFileMultiBody1 = MultipartBody.Part.createFormData("vmImage1", startImageFile.getName(), requestBody1);
            latLog = RequestBody.create(okhttp3.MultipartBody.FORM, Prefs.getString(AUtils.LOCATION, ""));

            RequestBody name = RequestBody.create(okhttp3.MultipartBody.FORM, contructionCompleantPojo.getName());
            RequestBody number = RequestBody.create(okhttp3.MultipartBody.FORM, contructionCompleantPojo.getNumber());

            RequestBody address = null;
            if (!AUtils.isNullString(contructionCompleantPojo.getAddress())) {
                address = RequestBody.create(okhttp3.MultipartBody.FORM, contructionCompleantPojo.getAddress());
            }

            RequestBody tip = null;
            if (!AUtils.isNullString(contructionCompleantPojo.getTip())) {
                tip = RequestBody.create(okhttp3.MultipartBody.FORM, contructionCompleantPojo.getTip());
            }

            RequestBody email = null;
            if (!AUtils.isNullString(contructionCompleantPojo.getEmail())) {
                email = RequestBody.create(okhttp3.MultipartBody.FORM, contructionCompleantPojo.getEmail());
            }

            RequestBody userId = null;
            if (!AUtils.isNullString(Prefs.getString(AUtils.USER_ID, ""))) {
                userId = RequestBody.create(okhttp3.MultipartBody.FORM, Prefs.getString(AUtils.USER_ID, ""));
            }

            RequestBody wardNo = RequestBody.create(okhttp3.MultipartBody.FORM, contructionCompleantPojo.getWardNo());
            RequestBody location = RequestBody.create(okhttp3.MultipartBody.FORM, contructionCompleantPojo.getLocation());
            RequestBody typeId = null;
            RequestBody details = RequestBody.create(okhttp3.MultipartBody.FORM, contructionCompleantPojo.getDetails());
            RequestBody languageId = RequestBody.create(okhttp3.MultipartBody.FORM, "1");
            RequestBody createdDate = RequestBody.create(okhttp3.MultipartBody.FORM, AUtils.getCurrentDateTime());

            CompleantWebservice service = AUtils.createService(CompleantWebservice.class, AUtils.SERVER_URL);
            resultPojo = service.saveConstructionCompleant(Prefs.getString(AUtils.APP_ID, ""),
                    name, number, address, tip, email, wardNo, location, details, languageId, createdDate,
                    latLog, userId, typeId, imageFileMultiBody1).execute().body();


        } catch (Exception e) {

            e.printStackTrace();
        }
        return resultPojo;
    }

    public boolean pullTypeListFromServer(String typeId) {

        List<ComplaintTypePojo> complaintTypePojoList = null;

        try {

            CompleantWebservice service = AUtils.createService(CompleantWebservice.class, AUtils.SERVER_URL);
            complaintTypePojoList = service.pullTypeList(Prefs.getString(AUtils.APP_ID, ""),
                    typeId).execute().body();

            if (!AUtils.isNull(complaintTypePojoList) && !complaintTypePojoList.isEmpty()) {

                Type type = new TypeToken<List<ComplaintTypePojo>>() {
                }.getType();
                Prefs.putString(AUtils.PREFS.COMPLENT_TYPE_POJO_LIST + typeId, gson.toJson(complaintTypePojoList, type));
            }
            return true;
        } catch (Exception e) {

            e.printStackTrace();
        }
        return false;
    }

    public boolean pullMandiListFromServer(String selectedDate) {

        List<MandiPojo> mandiPojoList = null;

        try {

            MandiWebservice service = AUtils.createService(MandiWebservice.class, AUtils.SERVER_URL);
            mandiPojoList = service.pullMandiList(Prefs.getString(AUtils.APP_ID, ""),
                    selectedDate
            ).execute().body();

            if (!AUtils.isNull(mandiPojoList) && !mandiPojoList.isEmpty()) {

                Type type = new TypeToken<List<MandiPojo>>() {
                }.getType();
                Prefs.putString(AUtils.PREFS.MANDI_POJO_LIST + Prefs.getString(AUtils.LANGUAGE_ID, AUtils.DEFAULT_LANGUAGE_ID), gson.toJson(mandiPojoList, type));

                return true;
            } else {

                Prefs.putString(AUtils.PREFS.MANDI_POJO_LIST + Prefs.getString(AUtils.LANGUAGE_ID, AUtils.DEFAULT_LANGUAGE_ID), null);
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
        return false;
    }

    public boolean pullDefaultMandiListFromServer() {

        List<MandiPojo> mandiPojoList = null;

        try {

            MandiWebservice service = AUtils.createService(MandiWebservice.class, AUtils.SERVER_URL);
            mandiPojoList = service.pullDefaultMandiList(Prefs.getString(AUtils.APP_ID, "")).execute().body();

            if (!AUtils.isNull(mandiPojoList) && !mandiPojoList.isEmpty()) {

                Type type = new TypeToken<List<MandiPojo>>() {
                }.getType();
                Prefs.putString(AUtils.PREFS.MANDI_POJO_LIST + Prefs.getString(AUtils.LANGUAGE_ID, AUtils.DEFAULT_LANGUAGE_ID), gson.toJson(mandiPojoList, type));

                return true;
            } else {

                Prefs.putString(AUtils.PREFS.MANDI_POJO_LIST + Prefs.getString(AUtils.LANGUAGE_ID, AUtils.DEFAULT_LANGUAGE_ID), null);
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
        return false;
    }

    public boolean pullUpcomingEventListFromServer() {

        List<UpcomingEventsPojo> upcomingEventsPojoList = null;

        try {

            UpcomingEventWebservice service = AUtils.createService(UpcomingEventWebservice.class, AUtils.SERVER_URL);
            upcomingEventsPojoList = service.pullUpcomingEventList(Prefs.getString(AUtils.APP_ID, "")
            ).execute().body();

            if (!AUtils.isNull(upcomingEventsPojoList) && !upcomingEventsPojoList.isEmpty()) {

                Type type = new TypeToken<List<UpcomingEventsPojo>>() {
                }.getType();
                Prefs.putString(AUtils.PREFS.UPCOMING_EVENT_POJO_LIST + Prefs.getString(AUtils.LANGUAGE_ID, AUtils.DEFAULT_LANGUAGE_ID), gson.toJson(upcomingEventsPojoList, type));

                return true;
            } else {

                Prefs.putString(AUtils.PREFS.UPCOMING_EVENT_POJO_LIST + Prefs.getString(AUtils.LANGUAGE_ID, AUtils.DEFAULT_LANGUAGE_ID), null);
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
        return false;
    }

    public boolean pullClassificationListFromServer() {

        List<ClassificationPojo> classificationPojoList = null;

        try {

            ClassificationWebservice service = AUtils.createService(ClassificationWebservice.class, AUtils.SERVER_URL);
            classificationPojoList = service.pullClassificationList(Prefs.getString(AUtils.APP_ID, "")
            ).execute().body();

            if (!AUtils.isNull(classificationPojoList) && !classificationPojoList.isEmpty()) {

                Type type = new TypeToken<List<MandiPojo>>() {
                }.getType();
                Prefs.putString(AUtils.PREFS.CLASSIFICATION_POJO_LIST + Prefs.getString(AUtils.LANGUAGE_ID, AUtils.DEFAULT_LANGUAGE_ID), gson.toJson(classificationPojoList, type));

                return true;
            } else {

                Prefs.putString(AUtils.PREFS.CLASSIFICATION_POJO_LIST + Prefs.getString(AUtils.LANGUAGE_ID, AUtils.DEFAULT_LANGUAGE_ID), null);
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
        return false;
    }

    public Boolean getDetailsUsingId(String propertyNumber){

        PropertyTaxPojo propertyTaxPojo = null;

        try{

            PropertyTaxDetailsWebservice service = AUtils.createService(PropertyTaxDetailsWebservice.class, AUtils.SERVER_URL);

            propertyTaxPojo = service.fetchPropertyDetails(Prefs.getString(AUtils.APP_ID, "1"), propertyNumber)
                .execute().body();

            if(!AUtils.isNull(propertyTaxPojo)){

                Type type = new TypeToken<PropertyTaxPojo>() {}.getType();
                Prefs.putString(AUtils.PREFS.PROPERTY_TAX_DETAILS_POJO_LIST, gson.toJson(propertyTaxPojo, type));
                return true;

            }else{

                return false;
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return false;
    }

    public Boolean getAreaList(){

        List<AreaListPojo> areaListPojos = null;

        try{
            GhantaGadiTrackerWebservice webservice = AUtils.createService(GhantaGadiTrackerWebservice.class, AUtils.SERVER_URL_SBA);
            areaListPojos = webservice.fetchAreaList("1").execute().body();

            if(!AUtils.isNull(areaListPojos)){

                Type type = new TypeToken<List<AreaListPojo>>() {
                }.getType();
                Prefs.putString(AUtils.PREFS.SBA_AREA_LIST , gson.toJson(areaListPojos, type));

                return true;
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return false;

    }

    public Boolean getAllActiveUsers(){

        List<ActiveUserListPojo> userListPojos = null;

        try{
            GhantaGadiTrackerWebservice webservice = AUtils.createService(GhantaGadiTrackerWebservice.class, AUtils.SERVER_URL_SBA);
            userListPojos = webservice.fetchAllActiveUserList(Prefs.getString(AUtils.APP_ID_GG, "")).execute().body();

            if(!AUtils.isNull(userListPojos)){

                Type type = new TypeToken<List<ActiveUserListPojo>>() {
                }.getType();
                Prefs.putString(AUtils.PREFS.SBA_ALL_USER_LIST , gson.toJson(userListPojos, type));

                return true;
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return false;
    }

    public Boolean getActiveUserAreaWise(String area){

        List<ActiveUserListPojo> areaListPojos = null;

        try{

            GhantaGadiTrackerWebservice webservice = AUtils.createService(GhantaGadiTrackerWebservice.class, AUtils.SERVER_URL_SBA);
            areaListPojos = webservice.fetchAreawiseUserList(Prefs.getString(AUtils.APP_ID_GG, ""), area).execute().body();

            if(!AUtils.isNull(areaListPojos)){

                Type type = new TypeToken<List<ActiveUserListPojo>>() {
                }.getType();
                Prefs.putString(AUtils.PREFS.SBA_USER_LIST , gson.toJson(areaListPojos, type));

                return true;
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return false;
    }

    public Boolean checkVersionUpdate(){

        Boolean doUpdate = false;

        VersionCheckWebService checkService = AUtils.createService(VersionCheckWebService.class, AUtils.SERVER_URL);

        try {
            ResultPojo resultPojo = checkService.checkVersion(Prefs.getString(AUtils.APP_ID, "1"),
                    Prefs.getInt(AUtils.VERSION_CODE, 0)).execute().body();

            if (resultPojo != null) {
                doUpdate = Boolean.parseBoolean(resultPojo.getStatus());
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return doUpdate;
    }

    public List<LeagueQuestionPojo> fetchLeagueQuestion() {

        List<LeagueQuestionPojo> questionPojos = null;

        try {

            LeagueWebservice webservice = AUtils.createService(LeagueWebservice.class, AUtils.SERVER_URL);
            questionPojos = webservice.getLeagueQuestions(
                    Prefs.getString(AUtils.APP_ID, ""),
                    AUtils.getLanguageId(Prefs.getString(AUtils.LANGUAGE_NAME, AUtils.DEFAULT_LANGUAGE_NAME)))
                    .execute().body();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return questionPojos;
    }

    public ResultPojo submitLeagueAnswer(LeageaAnswerDetailsPojo pojo) {

        ResultPojo resultPojo = null;

        try {

            LeagueWebservice webservice = AUtils.createService(LeagueWebservice.class, AUtils.SERVER_URL);
            resultPojo = webservice.submitLeagueAnswer(Prefs.getString(AUtils.APP_ID, ""),
                    Prefs.getString(AUtils.PREFS.REFERENCE_ID, ""),
                    Prefs.getString(AUtils.APP_ID_GG, ""),
                    AUtils.CONTENT_TYPE, pojo).execute().body();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultPojo;
    }

}