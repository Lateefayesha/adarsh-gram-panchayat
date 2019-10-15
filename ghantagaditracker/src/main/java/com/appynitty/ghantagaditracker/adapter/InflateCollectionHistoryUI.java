package com.appynitty.ghantagaditracker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;

import com.appynitty.ghantagaditracker.R;
import com.appynitty.ghantagaditracker.pojo.CollectionHistoryPojo;
import com.appynitty.ghantagaditracker.utils.AUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ayan Dey on 13/7/19.
 */
public class InflateCollectionHistoryUI extends RecyclerView.Adapter<InflateCollectionHistoryUI.ViewHolder> {

    private Context context;
    private List<CollectionHistoryPojo> historyPojos;

    public InflateCollectionHistoryUI(Context context) {
        this.context = context;
        historyPojos = new ArrayList<>();
    }

    public List<CollectionHistoryPojo> getHistoryPojos() {
        return historyPojos;
    }

    public void setHistoryPojos(List<CollectionHistoryPojo> historyPojos) {
        this.historyPojos = historyPojos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder holder = null;
        try {
            View view = LayoutInflater.from(this.context)
                    .inflate(R.layout.layout_history_detail_card, null);
            holder = new ViewHolder(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {

            if (!AUtils.isNull(getHistoryPojos())) {
                CollectionHistoryPojo pojo = getHistoryPojos().get(position);
                holder.collectorName.setText(pojo.getEmployee());
                holder.collectorVehicleNo.setText(String.format("%s %s",
                        context.getResources().getString(R.string.vehicle_number_txt), pojo.getVehicleNumber()));
                holder.collectionDateTime.setText(pojo.getAttandDate());

                switch (pojo.getType1()) {
                    case AUtils.GarbageType.garbageMixed:
                        holder.collectorType.setText(context.getText(R.string.mixed_garbage));
                        holder.collectorType.setBackground(AppCompatResources.getDrawable(context, R.drawable.rounded_mixed_garbage_button));
                        break;
                    case AUtils.GarbageType.garbageSegregated:
                        holder.collectorType.setText(context.getText(R.string.bifurcate_garbage));
                        holder.collectorType.setBackground(AppCompatResources.getDrawable(context, R.drawable.rounded_segregated_garbage_button));
                        break;
                    case AUtils.GarbageType.garbageNotReceived:
                        holder.collectorType.setText(context.getText(R.string.no_garbage));
                        holder.collectorType.setBackground(AppCompatResources.getDrawable(context, R.drawable.rounded_not_received_garbage_button));
                        break;
                    case AUtils.GarbageType.garbageNotSpecified:
                        holder.collectorType.setText(context.getText(R.string.not_specified_garbage));
                        holder.collectorType.setBackground(AppCompatResources.getDrawable(context, R.drawable.rounded_not_specified_garbage_button));
                        break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return getHistoryPojos().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView collectorName, collectorVehicleNo, collectionDateTime;
        Button collectorType;

        ViewHolder(View itemView) {
            super(itemView);
            collectorName = itemView.findViewById(R.id.history_details_name);
            collectorVehicleNo = itemView.findViewById(R.id.history_details_vehicle_no);
            collectionDateTime = itemView.findViewById(R.id.history_details_date_time);
            collectorType = itemView.findViewById(R.id.history_details_type);
        }
    }
}
