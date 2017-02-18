package leon.homework.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.List;
import leon.homework.JavaBean.Msg;
import leon.homework.R;

/**
 * Created by mjhzds on 2017/2/14.
 */

public class MsgAdapter extends ArrayAdapter<Msg> {

    private int resourceId;

    public MsgAdapter(Context context, int textViewResource, List<Msg> objects) {
        super(context, textViewResource, objects);
        resourceId = textViewResource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Msg msg = getItem(position);
        View view;
        ViewHolder viewHolder;
        if(convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId,null);
            viewHolder = new ViewHolder();
            viewHolder.leftLayout = (LinearLayout) view.findViewById(R.id.left_layout);
            viewHolder.rightLayout = (LinearLayout) view.findViewById(R.id.right_layout);
            viewHolder.leftHead = (ImageView) view.findViewById(R.id.left_head);
            viewHolder.leftHead.setImageResource(R.drawable.chinese);
            viewHolder.rightHead = (ImageView) view.findViewById(R.id.right_head);
            viewHolder.rightHead.setImageResource(R.drawable.english);
            viewHolder.leftMsg = (TextView) view.findViewById(R.id.left_msg);
            viewHolder.rightMsg = (TextView) view.findViewById(R.id.right_msg);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        if (msg.getMsgType() == Msg.TYPE_RECEIVED){
            viewHolder.rightLayout.setVisibility(View.GONE);
            viewHolder.leftLayout.setVisibility(View.VISIBLE);
            viewHolder.leftMsg.setText(msg.getContent());
        } else if(msg.getMsgType() == Msg.TYPE_SENT){
            viewHolder.leftLayout.setVisibility(View.GONE);
            viewHolder.rightLayout.setVisibility(View.VISIBLE);
            viewHolder.rightMsg.setText(msg.getContent());
        }
        return view;
    }

    private class ViewHolder {
        LinearLayout leftLayout;
        LinearLayout rightLayout;
        TextView leftMsg;
        TextView rightMsg;
        ImageView leftHead;
        ImageView rightHead;
    }
}
