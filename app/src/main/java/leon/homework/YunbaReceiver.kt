package leon.homework

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import io.yunba.android.manager.YunBaManager
import leon.homework.Data.Const
import leon.homework.Utils.YunbaUtil
import org.json.JSONException
import org.json.JSONObject

/**
 * Created by BC on 2017/2/4 0004.
 */

class YunbaReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (YunBaManager.MESSAGE_RECEIVED_ACTION == intent.action) {
            val topic = intent.getStringExtra(YunBaManager.MQTT_TOPIC)
            val msg = intent.getStringExtra(YunBaManager.MQTT_MSG)
            val showMsg = "Received message from server: " + YunBaManager.MQTT_TOPIC +
                    " = " + topic + " " +
                    YunBaManager.MQTT_MSG + " = " + msg
            Log.d("YUNBARECEIVER", showMsg)
            /*val flag = */YunbaUtil.showNotifation(context, topic, msg)
            //test
            SendBroadcast(msg)


            /*val mintent = Intent() // Itent就是我们要发送的内容
            mintent.action = "AddMsg" // 设置你这个广播的action
            mintent.putExtra("content", msg)
            AppContext.instance!!.sendBroadcast(mintent) // 发送广播*/
            //上报显示通知栏状态， 以方便后台统计
            /*if (flag)
                YunBaManager.report(context, REPORT_MSG_SHOW_NOTIFICARION, topic)
            else
                YunBaManager.report(context, REPORT_MSG_SHOW_NOTIFICARION_FAILED, topic)*/
        } else if (YunBaManager.PRESENCE_RECEIVED_ACTION == intent.action) {
            //msg from presence.
            val topic = intent.getStringExtra(YunBaManager.MQTT_TOPIC)
            val payload = intent.getStringExtra(YunBaManager.MQTT_MSG)
            val showMsg = "Received message presence: " + YunBaManager.MQTT_TOPIC +
                    " = " + topic + " " +
                    YunBaManager.MQTT_MSG + " = " + payload
            Log.d("DemoReceiver", showMsg)

        }
    }

    fun SendBroadcast(msg:String) {
        try {
            val js = JSONObject(msg)
            val action = js.getString("ACTION")
            val mintent = Intent()
            when(action){
                Const.BROADCAST_HOMEWORK->{
                    Log.d("Receiver",msg)
                    mintent.action = Const.BROADCAST_HOMEWORK // 设置你这个广播的action
                    mintent.putExtra("content", msg)
                    AppContext.instance!!.sendBroadcast(mintent) // 发送广播*/
                }
                Const.BROADCAST_CHAT->{
                    mintent.action = Const.BROADCAST_CHAT
                    mintent.putExtra("content", msg)
                    AppContext.instance!!.sendBroadcast(mintent)
                }
                Const.BROADCAST_SYSTEM->{
                    mintent.action = Const.BROADCAST_SYSTEM
                    mintent.putExtra("content", msg)
                    AppContext.instance!!.sendBroadcast(mintent)
                }
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    /*companion object {
        private val REPORT_MSG_SHOW_NOTIFICARION = "1000"
        private val REPORT_MSG_SHOW_NOTIFICARION_FAILED = "1001"
    }*/
}
