package com.appynitty.ghantagaditracker.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.appynitty.ghantagaditracker.R;
import com.appynitty.ghantagaditracker.pojo.LeagueQuestionPojo;
import com.appynitty.ghantagaditracker.utils.AUtils;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

/**
 * Created by Ayan Dey on 2/7/19.
 */
public class LeagueQuestionUIAdapter extends RecyclerView.Adapter<LeagueQuestionUIAdapter.ViewHolder> {

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
            holder.answerRadio.setTag(pojo.getQuestionId());

            holder.answerRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    String queId;
                    try{
                        queId = (String) radioGroup.getTag();
                        if (radioGroup.getCheckedRadioButtonId() == R.id.radio_yes)
                            ansMap.put(queId, "yes");
                        else if(radioGroup.getCheckedRadioButtonId() == R.id.radio_no)
                            ansMap.put(queId, "no");
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return questionPojos.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{

        TextView srNo, question;
        RadioGroup answerRadio;
        RadioButton yesRadio, noRadio;

        ViewHolder(View itemView) {
            super(itemView);
            srNo = itemView.findViewById(R.id.sr_no);
            question = itemView.findViewById(R.id.question_text);
            answerRadio = itemView.findViewById(R.id.answer_radio_group);
            yesRadio = itemView.findViewById(R.id.radio_yes);
            noRadio = itemView.findViewById(R.id.radio_no);
        }
    }
}
