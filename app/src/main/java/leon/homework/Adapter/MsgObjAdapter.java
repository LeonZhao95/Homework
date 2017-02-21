package leon.homework.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;
import leon.homework.JavaBean.MsgObject;
import leon.homework.R;

/**
 * Created by mjhzds on 2017/2/14.
 */

public class MsgObjAdapter extends ArrayAdapter<MsgObject> {

    private int resourceId;

    public MsgObjAdapter(Context context, int resource, List<MsgObject> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        MsgObject msgObject = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.msgIcon = (ImageView) view.findViewById(R.id.msg_icon);
            viewHolder.msgTitle = (TextView) view.findViewById(R.id.msg_title);
            viewHolder.msgContent = (TextView)view.findViewById(R.id.msg_lastcontent);
            viewHolder.msgTime = (TextView)view.findViewById(R.id.msg_time);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.msgTitle.setText(msgObject.getTitle());
        viewHolder.msgIcon.setImageBitmap(msgObject.getBm());
        viewHolder.msgContent.setText(msgObject.getLastcontent());
        viewHolder.msgTime.setText(msgObject.getTime());
        return view;
    }

    private class ViewHolder {
        ImageView msgIcon;
        TextView msgTitle;
        TextView msgContent;
        TextView msgTime;
    }
}
