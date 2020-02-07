package com.appynitty.ghantagaditracker.controller;

import android.content.Context;
import android.util.Log;

import com.appynitty.ghantagaditracker.pojo.ActiveUserListPojo;
import com.appynitty.ghantagaditracker.pojo.AreaListPojo;
import com.appynitty.ghantagaditracker.pojo.CityPeeListPojo;
import com.appynitty.ghantagaditracker.pojo.CleaningCompleantPojo;
import com.appynitty.ghantagaditracker.pojo.CollectionHistoryPojo;
import com.appynitty.ghantagaditracker.pojo.ComplaintTypePojo;
import com.appynitty.ghantagaditracker.pojo.ComplentStatusPojo;
import com.appynitty.ghantagaditracker.pojo.FcmIdPojo;
import com.appynitty.ghantagaditracker.pojo.LeageaAnswerDetailsPojo;
import com.appynitty.ghantagaditracker.pojo.LeagueQuestionPojo;
import com.appynitty.ghantagaditracker.pojo.ResultPojo;
import com.appynitty.ghantagaditracker.utils.AUtils;
import com.appynitty.ghantagaditracker.webservices.CollectionHistoryWebService;
import com.appynitty.ghantagaditracker.webservices.CompleantWebservice;
import com.appynitty.ghantagaditracker.webservices.FcmIdWebservice;
import com.appynitty.ghantagaditracker.webservices.GhantaGadiTrackerWebservice;
import com.appynitty.ghantagaditracker.webservices.LeagueWebservice;
import com.appynitty.ghantagaditracker.webservices.VersionCheckWebService;
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

    public void saveFcmIdOnServer(FcmIdPojo fcmIdPojo) {
//    public void saveFcmIdOnServer(FcmIdPojo fcmIdPojo) {

        ResultPojo resultPojo = null;

        try {

            FcmIdWebservice service = AUtils.createService(FcmIdWebservice.class, AUtils.SERVER_URL);
            resultPojo = service.saveFcmId(Prefs.getString(AUtils.APP_ID, ""), fcmIdPojo).execute().body();

            if (resultPojo.getStatus().equals(AUtils.STATUS_SUCCESS)) {
                Log.d(TAG, "saveFcmIdOnServer: is success");
//                return true;
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
//        return false;

    }

    public Boolean getAreaList() {

        List<AreaListPojo> areaListPojos = null;

        try {
            GhantaGadiTrackerWebservice webservice = AUtils.createService(GhantaGadiTrackerWebservice.class, AUtils.SERVER_URL_SBA);
            areaListPojos = webservice.fetchAreaList(Prefs.getString(AUtils.APP_ID_GG, "")).execute().body();

            if (!AUtils.isNull(areaListPojos)) {

                Type type = new TypeToken<List<AreaListPojo>>() {
                }.getType();
                Prefs.putString(AUtils.PREFS.SBA_AREA_LIST, gson.toJson(areaListPojos, type));

                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;

    }

    public Boolean getAllActiveUsers() {

        List<ActiveUserListPojo> userListPojos = null;

        try {
            GhantaGadiTrackerWebservice webservice = AUtils.createService(GhantaGadiTrackerWebservice.class, AUtils.SERVER_URL_SBA);
            userListPojos = webservice.fetchAllActiveUserList(Prefs.getString(AUtils.APP_ID_GG, "")).execute().body();

            if (!AUtils.isNull(userListPojos)) {

                Type type = new TypeToken<List<ActiveUserListPojo>>() {
                }.getType();
                Prefs.putString(AUtils.PREFS.SBA_ALL_USER_LIST, gson.toJson(userListPojos, type));

                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public Boolean getActiveUserAreaWise(String area) {

        List<ActiveUserListPojo> areaListPojos = null;

        try {

            GhantaGadiTrackerWebservice webservice = AUtils.createService(GhantaGadiTrackerWebservice.class, AUtils.SERVER_URL_SBA);
            areaListPojos = webservice.fetchAreawiseUserList(Prefs.getString(AUtils.APP_ID_GG, ""), area).execute().body();

            if (!AUtils.isNull(areaListPojos)) {

                Type type = new TypeToken<List<ActiveUserListPojo>>() {
                }.getType();
                Prefs.putString(AUtils.PREFS.SBA_USER_LIST, gson.toJson(areaListPojos, type));

                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public Boolean checkVersionUpdate() {

        Boolean doUpdate = false;

        VersionCheckWebService checkService = AUtils.createService(VersionCheckWebService.class, AUtils.SERVER_URL);

        try {
            ResultPojo resultPojo = checkService.checkVersion(Prefs.getString(AUtils.APP_ID, ""),
                    Prefs.getInt(AUtils.VERSION_CODE, 0)).execute().body();

            if (resultPojo != null) {
                doUpdate = Boolean.parseBoolean(resultPojo.getStatus());
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return doUpdate;
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

    public boolean pullMyComplentStatusListFromServer() {

        List<ComplentStatusPojo> complentStatusPojoList = null;

        try {

            CompleantWebservice service = AUtils.createService(CompleantWebservice.class, AUtils.SERVER_URL);
            complentStatusPojoList = service.pullMyStatusList(Prefs.getString(AUtils.APP_ID, ""),
                    Prefs.getString(AUtils.USER_ID, "")).execute().body();

            if (!AUtils.isNull(complentStatusPojoList) && !complentStatusPojoList.isEmpty()) {

                Type type = new TypeToken<List<ComplentStatusPojo>>() {
                }.getType();
                Prefs.putString(AUtils.PREFS.MY_COMPLENT_STATUS_POJO_LIST + Prefs.getString(AUtils.LANGUAGE_NAME, AUtils.DEFAULT_LANGUAGE_NAME), gson.toJson(complentStatusPojoList, type));

                return true;
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
        return false;
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

    public List<CollectionHistoryPojo> fetchCollectionHistory(String startdate, String endDate) {
        List<CollectionHistoryPojo> collectionHistoryList = null;
        try {
            CollectionHistoryWebService service = AUtils.createService(CollectionHistoryWebService.class, AUtils.SERVER_URL_SBA);
            collectionHistoryList = service.getCollectionHistory(Prefs.getString(AUtils.APP_ID_GG, ""),
                    startdate, endDate, "", "0", "1000", "0",
                    Prefs.getString(AUtils.PREFS.REFERENCE_ID, "")).execute().body();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return collectionHistoryList;
    }

    public Boolean getAllCityPee() {

        List<CityPeeListPojo> cityPeeListPojos = null;

        try {
            LeagueWebservice webservice = AUtils.createService(LeagueWebservice.class, AUtils.SERVER_URL_SBA);
            cityPeeListPojos = webservice.fetchCityPeeLocation(Prefs.getString(AUtils.APP_ID_GG, "")).execute().body();

            if (!AUtils.isNull(cityPeeListPojos)) {

                Type type = new TypeToken<List<CityPeeListPojo>>() {
                }.getType();
                Prefs.putString(AUtils.PREFS.SBA_ALL_USER_LIST, gson.toJson(cityPeeListPojos, type));

                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

}