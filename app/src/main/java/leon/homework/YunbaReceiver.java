package leon.homework;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import io.yunba.android.manager.YunBaManager;
import leon.homework.Utils.YunbaUtil;

/**
 * Created by BC on 2017/2/4 0004.
 */

public class YunbaReceiver extends BroadcastReceiver {
    private final static String REPORT_MSG_SHOW_NOTIFICARION = "1000";
    private final static String REPORT_MSG_SHOW_NOTIFICARION_FAILED = "1001";
    @Override
    public void onReceive(Context context, Intent intent) {
        if (YunBaManager.MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {

            String topic = intent.getStringExtra(YunBaManager.MQTT_TOPIC);
            String msg = intent.getStringExtra(YunBaManager.MQTT_MSG);

            StringBuilder showMsg = new StringBuilder();
            showMsg.append("Received message from server: ").append(YunBaManager.MQTT_TOPIC)
                    .append(" = ").append(topic).append(" ")
                    .append(YunBaManager.MQTT_MSG).append(" = ").append(msg);
            boolean flag = YunbaUtil.showNotifation(context, topic, msg);
            //test

            Intent mintent = new Intent(); // Itent就是我们要发送的内容
            mintent.setAction("AddMsg"); // 设置你这个广播的action
            mintent.putExtra("content",msg);
            AppContext.Companion.getInstance().sendBroadcast(mintent); // 发送广播

            //上报显示通知栏状态， 以方便后台统计
            if (flag) YunBaManager.report(context, REPORT_MSG_SHOW_NOTIFICARION, topic);
            else  YunBaManager.report(context, REPORT_MSG_SHOW_NOTIFICARION_FAILED, topic);

            // send msg to app

        } else if(YunBaManager.PRESENCE_RECEIVED_ACTION.equals(intent.getAction())) {
            //msg from presence.
            String topic = intent.getStringExtra(YunBaManager.MQTT_TOPIC);
            String payload = intent.getStringExtra(YunBaManager.MQTT_MSG);
            String showMsg = "Received message presence: " + YunBaManager.MQTT_TOPIC +
                    " = " + topic + " " +
                    YunBaManager.MQTT_MSG + " = " + payload;
            Log.d("DemoReceiver", showMsg);

        }
    }
}
