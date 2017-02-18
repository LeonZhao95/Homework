package leon.homework.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import leon.homework.JavaBean.Subject;
import leon.homework.R;

/**
 * Created by mjhzds on 2017/2/6.
 */

public class SubjectAdapter extends ArrayAdapter<Subject> {

    private int resourceId;

    public SubjectAdapter(Activity context, int textViewResourceId, List<Subject> objects) {
        super(context,textViewResourceId,objects);
        this.resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Subject subject = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.subjectImage = (ImageView) view.findViewById(R.id.icon);
            viewHolder.subjectName = (TextView) view.findViewById(R.id.tvSub);
            viewHolder.subjectNum = (TextView) view.findViewById(R.id.tvComp);
            viewHolder.deadLine = (TextView) view.findViewById(R.id.tvDate);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.subjectImage.setImageResource(subject.getSubjectImage());
        viewHolder.subjectName.setText(subject.getSubjectName());
        viewHolder.subjectNum.setText(String.valueOf(subject.getSubjectNum()));
        viewHolder.deadLine.setText(subject.getDeadLine());
        return view;
    }

    private class ViewHolder {
        ImageView subjectImage;
        TextView subjectName;
        TextView subjectNum;
        TextView deadLine;
    }
}
