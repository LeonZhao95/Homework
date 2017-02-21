package leon.homework.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import leon.homework.JavaBean.JudgExercise;
import leon.homework.R;


public class JudgeFragment extends Fragment {

    private TextView title;
    private RadioButton correct;
    private RadioButton wrong;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_judgeques, container, false);
        Bundle bundle = getArguments();
        JudgExercise judgExercise = bundle.getParcelable("object");
        title = (TextView) view.findViewById(R.id.question);
        correct = (RadioButton)view.findViewById(R.id.correct);
        wrong = (RadioButton) view.findViewById(R.id.wrong);
        //if(judgExercise!=null)
        title.setText(judgExercise.getTitle());
        return view;
    }


}
