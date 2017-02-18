package leon.homework.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import leon.homework.JavaBean.Work;
import leon.homework.R;

/**
 * Created by mjhzds on 2017/2/8.
 */

public class WorkAdapter extends ArrayAdapter<Work> {
    private static final int ELECTRICAL_WORK = 0;
    private static final int PAPER_WORK = 1;
    private static final int VOICE_WORK = 2;

    private int resourceId;

    public WorkAdapter(Context context, int resource, List<Work> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Work work = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) view.findViewById(R.id.worktitleTv);
            viewHolder.time = (TextView) view.findViewById(R.id.timeTv);
            viewHolder.write = (ImageButton) view.findViewById(R.id.write);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        if(work.getType()==ELECTRICAL_WORK){
            viewHolder.write.setImageResource(R.drawable.paper);
        }else if(work.getType()==PAPER_WORK){
            viewHolder.write.setImageResource(R.drawable.picture);
        }else {
            viewHolder.write.setImageResource(R.drawable.voice);
        }
        viewHolder.title.setText(work.getTitle());
        viewHolder.time.setText("已耗时38min");
        viewHolder.write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Context context = getContext();
                Intent intent = new Intent(context, ElecWork.class);
                context.startActivity(intent);*/
            }
        });
        return view;
    }

    private class ViewHolder {
        TextView title;
        TextView time;
        ImageButton write;
    }
}
