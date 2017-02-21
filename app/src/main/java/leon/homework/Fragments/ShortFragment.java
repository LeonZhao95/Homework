package leon.homework.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import leon.homework.JavaBean.ShortExercise;
import leon.homework.R;


public class ShortFragment extends Fragment {

    private TextView title;
    private EditText answer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_short, container, false);
        Bundle bundle = new Bundle();
        ShortExercise shortExercise = bundle.getParcelable("object");
        title = (TextView) view.findViewById(R.id.question);
        answer = (EditText) view.findViewById(R.id.answer);
        title.setText(shortExercise.getTitle());
        return view;
    }

}
