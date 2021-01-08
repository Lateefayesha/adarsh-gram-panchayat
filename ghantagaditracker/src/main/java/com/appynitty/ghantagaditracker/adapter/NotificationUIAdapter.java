package com.appynitty.ghantagaditracker.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appynitty.ghantagaditracker.R;
import com.appynitty.ghantagaditracker.controller.Notification;
import com.appynitty.ghantagaditracker.utils.AUtils;
import com.appynitty.ghantagaditracker.utils.DatabaseHelper;

import java.util.List;

/**
 * Created by Ayan Dey on 28/6/19.
 */
public class NotificationUIAdapter extends RecyclerView.Adapter<NotificationUIAdapter.ViewHolder> {

    private Context mContext;
    private List<Notification> notificationList;
    private NotificationAdapterListner adapterListner;

    public NotificationUIAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setNotificationList(List<Notification> notificationList) {
        this.notificationList = notificationList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = null;
        try {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            View view = inflater.inflate(R.layout.layout_notification, null);
            viewHolder = new ViewHolder(view);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (!AUtils.isNull(notificationList) && notificationList.size() > 0) {
            final Notification notification = notificationList.get(position);
            holder.message.setText(notification.getText());
            holder.dateTime.setText(notification.getDateTime());
            int status = notification.getStatus();
            if (status == Notification.STATUS_UNREAD) {
                holder.message.setTypeface(null, Typeface.BOLD);
            } else if (status == Notification.STATUS_READ) {
                holder.message.setTypeface(null, Typeface.NORMAL);
            }

            holder.clearBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DatabaseHelper helper = new DatabaseHelper(mContext);
                    helper.deleteNotification(notification.getId());
                    adapterListner.onClear();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    public void setAdapterListner(NotificationAdapterListner adapterListner) {
        this.adapterListner = adapterListner;
    }

    public interface NotificationAdapterListner {
        void onClear();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView message;
        TextView dateTime;
        ImageButton clearBtn;

        ViewHolder(View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.notification_text);
            dateTime = itemView.findViewById(R.id.notification_date_time);
            clearBtn = itemView.findViewById(R.id.notification_clear);
        }
    }


}
