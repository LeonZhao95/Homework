package leon.homework.Activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import leon.homework.Fragments.Questions;
import leon.homework.R;

public class ExerciseActivity extends AppCompatActivity {
//    选择、判断、简答题数量
private int choiNum;
    private int judgeNum;
    private int shortNum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.work_enter);
        Intent intent = getIntent();
        String jString = intent.getStringExtra("exercise");
        initJson(jString);
        loadButtons(choiNum,judgeNum,shortNum,jString);
    }

    private void initJson(String jString) {
        try {
            JSONObject choJson;
            JSONObject judJson;
            JSONObject shoJson;
            JSONObject exerciseJson = new JSONObject(jString);
            choJson = exerciseJson.getJSONObject("cho");
            judJson = exerciseJson.getJSONObject("jud");
            shoJson = exerciseJson.getJSONObject("sho");
            choiNum = choJson.getInt("num");
            judgeNum = judJson.getInt("num");
            shortNum = shoJson.getInt("num");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void loadButtons(int choiNum, int judgeNum, int shortNum, String jString){
        createButton(choiNum,2000,0,R.id.frameChoi,jString);
        createButton(judgeNum,3000+choiNum,choiNum,R.id.frameBlank,jString);
        createButton(shortNum,4000+choiNum+judgeNum,choiNum+judgeNum,R.id.frameShort,jString);
    }

    public void createButton(int num, int id, int before, int layout, String jString){
        Questions ques=new Questions();
//        设置选择题数量
        ques.quesSetter(num,id,before,jString);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(layout,ques);
        fragmentTransaction.commit();
    }
}

