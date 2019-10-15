package com.appynitty.ghantagaditracker.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.appynitty.ghantagaditracker.R;
import com.appynitty.ghantagaditracker.pojo.LeageaAnswerDetailsPojo;
import com.appynitty.ghantagaditracker.utils.AUtils;

/**
 * Created by Ayan Dey on 16/7/19.
 */
public class UserDetailsBulderAdapter {

    private Context mContext;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private TextView userName, userContact;

    private UserDetailsBulderlistner bulderlistner;

    public UserDetailsBulderAdapter(Context mContext) {
        this.mContext = mContext;
        builder = new AlertDialog.Builder(mContext);
    }

    public void setBulderlistner(UserDetailsBulderlistner bulderlistner) {
        this.bulderlistner = bulderlistner;
    }

    public void openUserDetailsDialog(Boolean isCancellable){

        builder.setCancelable(isCancellable);

        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_league_user_details, null);

        userName = view.findViewById(R.id.ss_user_name);
        userContact = view.findViewById(R.id.ss_user_contact);


        view.findViewById(R.id.ss_details_save_btn)
                .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateInput()){
                    LeageaAnswerDetailsPojo.AnswersDetails pojo = new LeageaAnswerDetailsPojo.AnswersDetails();
                    pojo.setMobile(userContact.getText().toString().trim());
                    pojo.setName(userName.getText().toString().trim());
                    bulderlistner.onSubmitClickSuccess(pojo);
                    dialog.dismiss();
                }
            }
        });

        dialog = builder.setView(view).create();
        dialog.show();
    }

    private Boolean validateInput(){
        String name = userName.getText().toString();
        String mobile = userContact.getText().toString();

        if (AUtils.isNullString(name)) {
            userName.setError(mContext.getString(R.string.plz_ent_name));
            AUtils.warning(mContext, mContext.getString(R.string.plz_ent_name), Toast.LENGTH_SHORT);
            return false;
        }

        if (AUtils.isNullString(mobile)) {
            userContact.setError(mContext.getString(R.string.plz_ent_mobile_no));
            AUtils.warning(mContext, mContext.getString(R.string.plz_ent_mobile_no), Toast.LENGTH_SHORT);
            return false;
        }

        if (mobile.length() < 10) {
            userContact.setError(mContext.getString(R.string.plz_ent_valid_mobile_no));
            AUtils.warning(mContext, mContext.getString(R.string.plz_ent_valid_mobile_no), Toast.LENGTH_SHORT);
            return false;
        }
        return true;
    }

    public interface UserDetailsBulderlistner{
        void onSubmitClickSuccess(LeageaAnswerDetailsPojo.AnswersDetails details);
    }
}
