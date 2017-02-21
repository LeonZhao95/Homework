package leon.homework.Fragments

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import leon.homework.Activities.ChatActivity
import leon.homework.Adapter.MsgObjAdapter
import leon.homework.AppContext
import leon.homework.Data.Const
import leon.homework.JavaBean.MsgObject
import leon.homework.R
import org.json.JSONObject
import java.io.File
import java.util.*


class MessageFragment : BaseFragment() {
    override val layoutResourceId: Int = R.layout.fragment_message
    private val msgObjList = ArrayList<MsgObject>()
    private var mParam1: String? = null
    val messagereceiver = getMessageReceiver()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments.getString(ARG_PARAM1)
        }
        registerBroadCast()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val view = inflater.inflate(layoutResourceId, container, false)
        msgObjList.clear()
        init()
        val adapter = MsgObjAdapter(activity, R.layout.message_item, msgObjList)
        val list_view_message = view.findViewById(R.id.list_view_message) as ListView
        list_view_message.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val msgObject = msgObjList[position]
            if (msgObject.title == "系统消息") {
            } else if (msgObject.title == "老师") {
                startActivity(Intent(activity, ChatActivity::class.java))
            }
        }
        list_view_message.adapter = adapter
        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        AppContext.instance!!.unregisterReceiver(messagereceiver)
    }

    private fun init() {
        val sysMsg = MsgObject("系统消息",getAvatarBm("1"),"你好啊","14:42")
        msgObjList.add(sysMsg)
        val teaMsg = MsgObject("老师",getAvatarBm("1"),"你好啊","14:42")
        msgObjList.add(teaMsg)
    }

    fun getAvatarBm(filename:String):Bitmap{
        val f = File(filename)
        if(f.exists()){
            return BitmapFactory.decodeFile(filename)
        }else{
            return BitmapFactory.decodeFile(Const.DATA_PATH+"img/1.jpg")
        }
    }

    fun registerBroadCast(){
        val intentFilter = IntentFilter()
        intentFilter.addAction(Const.BROADCAST_CHAT)
        AppContext.instance!!.registerReceiver(messagereceiver,intentFilter)
    }
    inner class getMessageReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val msg = intent.extras.getString("content")
            val js = JSONObject(msg)

            Log.i("Recevier", "接收到:" + msg)
        }
    }
    companion object {
        private val ARG_PARAM1 = "param1"
        fun newInstance(param1: String): MessageFragment {
            val fragment = MessageFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            fragment.arguments = args
            return fragment
        }
    }
}
