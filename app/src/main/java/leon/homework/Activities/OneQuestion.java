package leon.homework.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import leon.homework.Adapter.FragmentAdapter;
import leon.homework.Fragments.ChoiFragment;
import leon.homework.Fragments.JudgeFragment;
import leon.homework.Fragments.ShortFragment;
import leon.homework.JavaBean.ChoiceExercise;
import leon.homework.JavaBean.JudgExercise;
import leon.homework.JavaBean.ShortExercise;
import leon.homework.R;
import leon.homework.Sqlite.WorkQuesDao;

public class OneQuestion extends FragmentActivity {

    private List<ChoiceExercise> choiceExercises = new ArrayList<>();
    private List<JudgExercise> judgExercises = new ArrayList<>();
    private List<ShortExercise> shortExercises = new ArrayList<>();
    private List<Fragment> fragmentList = new ArrayList<>();
    private int choiNum;
    private int judgeNum;
    private int shortNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_fragment);
        Intent intent = getIntent();
        int id = intent.getIntExtra("number", 0)%1000;
        String jString = intent.getStringExtra("exercise");
        initJson(jString);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentAdapter adapter = new FragmentAdapter(fragmentManager,fragmentList);
        ViewPager vp = (ViewPager) findViewById(R.id.viewpager);
        vp.setAdapter(adapter);
//        TODO  跳转到指定页面,页面号是id
//        Fragment fragment = fragmentManager.findFragmentById(R.id.frame_ques);
//        if (fragment == null) {
//            fragment = fragmentList.get(id);
//            fragmentManager.beginTransaction()
//                    .add(R.id.frame_ques, fragment)
//                    .commit();
//        } else {
//            fragment = fragmentList.get(id);
//            fragmentManager.beginTransaction()
//                    .replace(R.id.frame_ques, fragment)
//                    .commit();
//        }
    }

    private void initJson(String jString) {
        try {
            JSONObject choJson;
            JSONObject judJson;
            JSONObject shoJson;
            JSONObject choContent;
            JSONObject judContent;
            JSONObject shorContent;
            JSONObject exerciseJson = new JSONObject(jString);
            choJson = exerciseJson.getJSONObject("cho");
            judJson = exerciseJson.getJSONObject("jud");
            shoJson = exerciseJson.getJSONObject("sho");
            choiNum = choJson.getInt("num");
            judgeNum = judJson.getInt("num");
            shortNum = shoJson.getInt("num");
            if(choiNum>0){
                choContent = choJson.getJSONObject("content");
                for (int i = 1; i <= choiNum; i++) {
                    String choId = choContent.getString(String.valueOf(i));
                    ChoiceExercise choiceExercise = new WorkQuesDao(this).selectChoice(choId);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("object",choiceExercise);
                    ChoiFragment choiceFragment = new ChoiFragment();
                    choiceFragment.setArguments(bundle);
                    choiceExercises.add(choiceExercise);
                    fragmentList.add(choiceFragment);
                }
            }
            if(judgeNum>0){
                judContent = judJson.getJSONObject("content");
                for (int i = 1; i <= judgeNum; i++) {
                    String judgeId = judContent.getString(String.valueOf(i));
                    JudgExercise judgExercise = new WorkQuesDao(this).selectJudg(judgeId);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("object",judgExercise);
                    JudgeFragment judgeFragment = new JudgeFragment();
                    judgeFragment.setArguments(bundle);
                    judgExercises.add(judgExercise);
                    fragmentList.add(judgeFragment);
                }
            }
            if(shortNum>0){
                shorContent = shoJson.getJSONObject("content");
                for (int i = 1; i <= shortNum; i++) {
                    String shortId = shorContent.getString(String.valueOf(i));
                    ShortExercise shortExercise = new WorkQuesDao(this).selectSho(shortId);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("object",shortExercise);
                    ShortFragment shortFragment = new ShortFragment();
                    shortFragment.setArguments(bundle);
                    shortExercises.add(shortExercise);
                    fragmentList.add(shortFragment);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
