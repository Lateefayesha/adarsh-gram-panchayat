package com.appynitty.gp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.appynitty.gp.R;
import com.appynitty.gp.pojo.CertificatePojo;
import com.appynitty.gp.utils.AUtils;

import java.util.List;


/**
 * Created by MiTHUN on 5/3/18.
 */

public class CertificateListAdapter extends ArrayAdapter<CertificatePojo> {

    private List<CertificatePojo> certificatePojoList;
    private Context context;
    private View view;
    private ViewHolder holder;

    public CertificateListAdapter(Context context, List<CertificatePojo> certificatePojoList) {

        super(context, android.R.layout.simple_list_item_1, certificatePojoList);
        this.context = context;
        this.certificatePojoList = certificatePojoList;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.certificate_list_adapter, null);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.titleTextView = view.findViewById(R.id.certificate__adp_title);

            view.setTag(viewHolder);
        } else {
            view = convertView;
        }
        holder = (ViewHolder) view.getTag();

        if (!AUtils.isNull(certificatePojoList) && !certificatePojoList.isEmpty()) {

            CertificatePojo certificatePojo = certificatePojoList.get(position);

            if (!AUtils.isNullString(certificatePojo.getDocumentName())) {
                holder.titleTextView.setText(certificatePojo.getDocumentName());
            } else {
                holder.titleTextView.setText("");
            }
        }
        return view;
    }

    class ViewHolder {
        private TextView titleTextView;
    }
}