package leon.homework.Fragments

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import leon.homework.Activities.ExerciseActivity
import leon.homework.Adapter.SubjectAdapter
import leon.homework.AppContext
import leon.homework.Data.Const
import leon.homework.JavaBean.TodayWork
import leon.homework.R
import leon.homework.Sqlite.WorkDao
import org.jetbrains.anko.async
import org.jetbrains.anko.info
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.uiThread
import org.json.JSONObject
import java.util.*




class WriteFragment : BaseFragment(),SwipeRefreshLayout.OnRefreshListener {
    val workreceiver = getWorkReceiver()
    companion object {
        fun newInstance(): WriteFragment {
            return WriteFragment()
        }
    }
    override val layoutResourceId: Int =R.layout.fragment_write
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerBroadCast()
    }

    private var subjectList = ArrayList<TodayWork>()
    private var swiperefresh: SwipeRefreshLayout? = null
    var objAdapter:SubjectAdapter? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_write, container, false)
        objAdapter = SubjectAdapter(activity, R.layout.works_list_item, subjectList)
        val listview = view.findViewById(R.id.list_view) as ListView
        subjectList.clear();
        swiperefresh = view.findViewById(R.id.SwipeRL_write) as SwipeRefreshLayout
        swiperefresh!!.setOnRefreshListener(this)
        listview.setOnItemClickListener({ parent, view, position, id ->
            val subject = subjectList[position]
            info(subject.subjectName)
            val intent = Intent(activity, ExerciseActivity::class.java)
            intent.putExtra("exercise",subject.js.toString())
            startActivity(intent)
        })
        objAdapter!!.setNotifyOnChange(true)
        listview.adapter = objAdapter
        subjectList.addAll(WorkDao(activity).select1())
        return view
    }
    fun addSubject(js:JSONObject){
        val subject = js.getString("subject")
        val deadline = js.getString("deadline")
        js.remove("subject")
        js.remove("deadline")
        val work= TodayWork(subject, deadline, System.currentTimeMillis().toString(),js)
        //WorkDao(activity).insert1(work)
        subjectList.add(work)
        objAdapter!!.notifyDataSetChanged()
    }
    override fun onRefresh() {
        async() {
            val jjjsc = JSONObject()
            jjjsc.put("1","405010200")
            val jjjsj = JSONObject()
            jjjsj.put("1","105010101")
            jjjsj.put("2","105010103")
            val jjs1 = JSONObject()
            jjs1.put("num",1)
            jjs1.put("content",jjjsc)
            val jjs2 = JSONObject()
            jjs2.put("num",0)
            val jjs3 = JSONObject()
            jjs3.put("num",2)
            jjs3.put("content",jjjsj)
            val js = JSONObject()
            js.put("subject","语文")
            js.put("deadline","2017-02-20")
            js.put("cho",jjs1)
            js.put("sho",jjs2)
            js.put("jud",jjs3)
            //subjectList.clear()
            //subjectList.addAll(WorkDao(activity).select1())
            swiperefresh!!.isRefreshing = false
            addSubject(js)
            uiThread {
                toast("停")
            }
        }
    }

    fun registerBroadCast(){
        val intentFilter = IntentFilter()
        intentFilter.addAction(Const.BROADCAST_HOMEWORK)
        AppContext.instance!!.registerReceiver(workreceiver,intentFilter)
    }
    inner class getWorkReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val msg = intent.extras.getString("content")
            val js = JSONObject(msg)
            js.remove("ACTION")
            addSubject(js)
            Log.i("WriteRecevier", "接收到:" + msg)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        AppContext.instance!!.unregisterReceiver(workreceiver)
    }
}
