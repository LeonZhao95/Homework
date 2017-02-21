package leon.homework.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import leon.homework.JavaBean.ChoiceExercise;
import leon.homework.R;


public class ChoiFragment extends Fragment {

    private TextView title;
    private RadioButton optionA;
    private RadioButton optionB;
    private RadioButton optionC;
    private RadioButton optionD;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_choice, container, false);
        Bundle bundle = getArguments();
        ChoiceExercise choiceExercise = bundle.getParcelable("object");
        title = (TextView) view.findViewById(R.id.question);
        optionA = (RadioButton) view.findViewById(R.id.optionA);
        optionB = (RadioButton) view.findViewById(R.id.optionB);
        optionC = (RadioButton) view.findViewById(R.id.optionC);
        optionD = (RadioButton) view.findViewById(R.id.optionD);
        title.setText(choiceExercise.getTitle());
        String[] options = choiceExercise.getChoices();
        optionA.setText(options[0]);
        optionB.setText(options[1]);
        optionC.setText(options[2]);
        optionD.setText(options[3]);
        return view;
    }
}
