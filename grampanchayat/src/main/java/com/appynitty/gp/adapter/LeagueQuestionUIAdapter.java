package com.appynitty.gp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appynitty.gp.R;
import com.appynitty.gp.pojo.LeagueQuestionPojo;
import com.appynitty.gp.utils.AUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ayan Dey on 2/7/19.
 */
public class LeagueQuestionUIAdapter extends RecyclerView.Adapter<LeagueQuestionUIAdapter.ViewHolder> {

    private static final String TAG = "LeagueQuestionUIAdapter";
    private static final String ANSWER_YES = "Yes";
    private static final String ANSWER_NO = "No";

    private Context context;
    private List<LeagueQuestionPojo> questionPojos;
    private Map<String, String> ansMap;

    public LeagueQuestionUIAdapter(Context context) {
        this.context = context;
        ansMap = new HashMap<>();
    }

    public void setQuestionPojos(List<LeagueQuestionPojo> questionPojos) {
        this.questionPojos = questionPojos;
    }

    public Map<String, String> getAnsMap() {
        return this.ansMap;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder holder = null;
        try{
            LayoutInflater inflater = LayoutInflater.from(context);
            View v = inflater.inflate(R.layout.layout_question, null);
            holder = new ViewHolder(v);
        }catch (Exception e){
            e.printStackTrace();
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(!AUtils.isNull(questionPojos) && questionPojos.size()>0){
            LeagueQuestionPojo pojo = questionPojos.get(position);
            holder.srNo.setText(String.valueOf(position+1));
            holder.question.setText(pojo.getQuestion());
            holder.yesRadio.setTag(pojo.getQuestionId());
            holder.noRadio.setTag(pojo.getQuestionId());

            System.out.println(ansMap);

            if(!ansMap.containsKey(pojo.getQuestionId())){
//                holder.yesRadio.setChecked(false);
//                holder.noRadio.setChecked(false);
                holder.dummy.setChecked(true);
            }else {
                switch (ansMap.get(pojo.getQuestionId())){
                    case ANSWER_YES:
//                        holder.noRadio.setChecked(true);
                        holder.yesRadio.setChecked(true);
                        break;
                    case ANSWER_NO:
//                        holder.yesRadio.setChecked(true);
                        holder.noRadio.setChecked(true);
                        break;
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return questionPojos.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{

        TextView srNo, question;
        RadioButton yesRadio, noRadio, dummy;

        ViewHolder(View itemView) {
            super(itemView);
            srNo = itemView.findViewById(R.id.sr_no);
            question = itemView.findViewById(R.id.question_text);
            yesRadio = itemView.findViewById(R.id.radio_yes);
            noRadio = itemView.findViewById(R.id.radio_no);
            dummy = itemView.findViewById(R.id.radio_dummy);

            yesRadio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String queId = (String) view.getTag();
                    ansMap.put(queId, ANSWER_YES);
                }
            });

            noRadio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String queId = (String) view.getTag();
                    ansMap.put(queId, ANSWER_NO);
                }
            });

        }
    }
}
