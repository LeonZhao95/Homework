package leon.homework.Activities

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_chat.*
import leon.homework.Adapter.MsgAdapter
import leon.homework.JavaBean.Msg
import leon.homework.R
import java.util.*

class ChatActivity : BaseActivity() {
    override val layoutResourceId: Int = R.layout.activity_chat
    var layout : LinearLayout? = null
    var swiperefresh: SwipeRefreshLayout? = null
    private var msgAdapter: MsgAdapter? = null
    private var msgList = ArrayList<Msg>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutResourceId)
        initMsg()
        msgAdapter = MsgAdapter(this, R.layout.msg_item, msgList)
        msg_list_view.adapter = msgAdapter
        send!!.setOnClickListener({
            val content = input_text.text.toString()
            if ("" != content) {
                val msg = Msg(content, Msg.TYPE_SENT)
                msgList.add(msg)
                msgAdapter!!.notifyDataSetChanged()    //当有新消息时刷新ListView中的显示
                msg_list_view.setSelection(msgList.size)   //将ListView定位到最后一行
                input_text.setText("")
            }
            /*YunBaManager.publish(AppContext.instance, "leon", content,
                    object : IMqttActionListener {
                        override fun onSuccess(asyncActionToken: IMqttToken) {
                            *//*val topic = DemoUtil.join(asyncActionToken.topics, ", ")
                            val msgLog = "Publish succeed : " + topic
                            DemoUtil.showToast(msgLog, getApplicationContext())*//*
                        }

                        override fun onFailure(asyncActionToken: IMqttToken, exception: Throwable) {
                            if (exception is MqttException) {
                                val msg = "publish failed with error code : " + exception.reasonCode
                                //DemoUtil.showToast(msg, getApplicationContext())
                            }
                        }
                    }
            )*/
        })
    }

    private fun initMsg() {
        val msg1 = Msg("Hello guy", Msg.TYPE_RECEIVED)
        msgList.add(msg1)
        val msg2 = Msg("Hello", Msg.TYPE_SENT)
        msgList.add(msg2)
    }
}
