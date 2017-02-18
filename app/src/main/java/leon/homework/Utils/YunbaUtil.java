package leon.homework.Utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Looper;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.widget.Toast;

import java.util.Random;

import io.yunba.android.manager.YunBaManager;
import leon.homework.Activities.MainActivity;
import leon.homework.R;

/**
 * Created by BC on 2017/2/4 0004.
 */

public class YunbaUtil {
    private static boolean isEmpty(String s) {
        return null == s || s.length() == 0 || s.trim().length() == 0;
    }

    public static boolean showNotifation(Context context, String topic, String msg) {
        try {
            Uri alarmSound = RingtoneManager
                    .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            long[] pattern = { 500, 500, 500 };
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                    .setSmallIcon(R.drawable.back_light)
                    .setContentTitle(topic)//设置通知栏标题
                    .setContentText(msg)
                    .setAutoCancel(true)//设置这个标志当用户单击面板就可以让通知将自动取消
                    .setSound(alarmSound) //铃声
                    .setVibrate(pattern); //震动
            // Creates an explicit intent for an Activity in your app
            Intent resultIntent = new Intent(context, MainActivity.class);

            if (!YunbaUtil.isEmpty(topic))
                resultIntent.putExtra(YunBaManager.MQTT_TOPIC, topic);
            if (!YunbaUtil.isEmpty(msg))
                resultIntent.putExtra(YunBaManager.MQTT_MSG, msg);
            // The stack builder object will contain an artificial back stack
            // for the
            // started Activity.
            // This ensures that navigating backward from the Activity leads

            // of
            // your application to the Home screen.
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
            // Adds the back stack for the Intent (but not the Intent itself)
            stackBuilder.addParentStack(MainActivity.class);

            // Adds the Intent that starts the Activity to the top of the

            stackBuilder.addNextIntent(resultIntent);
            PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            mBuilder.setContentIntent(resultPendingIntent);//设置通知栏点击意图
            NotificationManager mNotificationManager = (NotificationManager)
                    context.getSystemService(Context.NOTIFICATION_SERVICE);
            // mId allows you to update the notification later on.
            Random r = new Random();
            mNotificationManager.notify(r.nextInt(), mBuilder.build());

//			NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
//					context).setSmallIcon(R.drawable.ic_launcher) // notification
//																	// icon
//					.setContentTitle(topic) // title for notification
//					.setContentText(msg) // message for notification
//					.setAutoCancel(true); // clear notification after click
//			Intent intent = new Intent(context, YunBaTabActivity.class);
//
//			 if (!DemoUtil.isEmpty(topic))
//				 intent.putExtra(YunBaManager.MQTT_TOPIC, topic);
//			 if (!DemoUtil.isEmpty(msg))
//				 intent.putExtra(YunBaManager.MQTT_MSG, msg);
//			PendingIntent pi = PendingIntent.getActivity(context, 0, intent,
//					Intent.FLAG_ACTIVITY_NEW_TASK);
//			mBuilder.setContentIntent(pi);
//			NotificationManager mNotificationManager = (NotificationManager) context
//					.getSystemService(Context.NOTIFICATION_SERVICE);
//			mNotificationManager.notify(0, mBuilder.build());
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    public static void showToast(final String toast, final Context context) {
         new Thread(new Runnable() {

         @Override
         public void run() {
         Looper.prepare();
         Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
         Looper.loop();
         }
         }).start();
    }
    public static <T> String join(T[] array, String cement) {
        StringBuilder builder = new StringBuilder();

        if (array == null || array.length == 0) {
            return null;
        }
        for (T t : array) {
            builder.append(t).append(cement);
        }

        builder.delete(builder.length() - cement.length(), builder.length());

        return builder.toString();
    }
}
