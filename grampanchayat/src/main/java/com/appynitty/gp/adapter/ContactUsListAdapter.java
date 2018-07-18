package com.appynitty.gp.adapter;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.appynitty.gp.R;
import com.appynitty.gp.pojo.ContactUsTeamMember;
import com.appynitty.gp.utils.AUtils;

import java.util.List;


/**
 * Created by MiTHUN on 5/3/18.
 */

public class ContactUsListAdapter extends ArrayAdapter<ContactUsTeamMember> {

    private List<ContactUsTeamMember> contactUsPojoList;
    private Context context;
    private View view;
    private ViewHolder holder;

    public ContactUsListAdapter(Context context, List<ContactUsTeamMember> contactUsPojoList) {

        super(context, android.R.layout.simple_list_item_1, contactUsPojoList);
        this.context = context;
        this.contactUsPojoList = contactUsPojoList;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.contact_us_list_adapter, null);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.titleTextView = view.findViewById(R.id.youn_business_out_adp_title);
            viewHolder.descTextView = view.findViewById(R.id.youn_business_out_adp_desc);

            view.setTag(viewHolder);
        } else {
            view = convertView;
        }
        holder = (ViewHolder) view.getTag();

        if (!AUtils.isNull(contactUsPojoList) && !contactUsPojoList.isEmpty()) {
            final ContactUsTeamMember contactUsTeamMember = contactUsPojoList.get(position);

            if (!AUtils.isNullString(contactUsTeamMember.getMemberName())) {
                holder.titleTextView.setText(contactUsTeamMember.getMemberName());

                if (!AUtils.isNullString(contactUsTeamMember.getJobTitle())) {
                    holder.titleTextView.setText(holder.titleTextView.getText() + " (" + contactUsTeamMember.getJobTitle() + ")");
                }
            } else {
                holder.titleTextView.setText("");
            }
            if (!AUtils.isNullString(contactUsTeamMember.getPhoneNumber())) {
                holder.descTextView.setText(contactUsTeamMember.getPhoneNumber());
            } else {
                holder.descTextView.setText("");
            }

            holder.descTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (!AUtils.isNullString(contactUsTeamMember.getPhoneNumber())) {

                        try {
                            Intent callIntent = new Intent(Intent.ACTION_DIAL);
                            callIntent.setData(Uri.parse("tel:" + contactUsTeamMember.getPhoneNumber()));
                            context.startActivity(callIntent);
                        } catch (ActivityNotFoundException activityException) {
                            Toast.makeText(context, "Call has failed", Toast.LENGTH_LONG).show();
                        }
                    }

                }
            });
        }
        return view;
    }

    class ViewHolder {
        private TextView titleTextView;
        private TextView descTextView;
    }
}