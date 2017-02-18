package leon.homework.Fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import leon.homework.Activities.ChineseHomework
import leon.homework.Adapter.SubjectAdapter
import leon.homework.JavaBean.Subject
import leon.homework.R
import org.jetbrains.anko.async
import org.jetbrains.anko.uiThread
import java.util.*




class WriteFragment : BaseFragment(),SwipeRefreshLayout.OnRefreshListener {

    override val layoutResourceId: Int =R.layout.fragment_write
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    companion object {
        fun newInstance(): WriteFragment {
            return WriteFragment()
        }
    }

    private val subjectList = ArrayList<Subject>()
    private var swiperefresh: SwipeRefreshLayout? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_write, container, false)
        val objAdapter = SubjectAdapter(activity, R.layout.works_list_item, subjectList)
        val listview = view.findViewById(R.id.list_view) as ListView
        subjectList.clear();
        swiperefresh = view.findViewById(R.id.SwipeRL_write) as SwipeRefreshLayout
        swiperefresh!!.setOnRefreshListener(this)
        listview.setOnItemClickListener({ parent, view, position, id ->
            val subject = subjectList[position]
            val context = activity
            if (subject.subjectName === "语文") {
                val intent = Intent(context, ChineseHomework::class.java)
                context.startActivity(intent)
            }
        })
        objAdapter.setNotifyOnChange(true)
        listview.adapter = objAdapter
        initObjects()
        return view
    }

    private fun initObjects() {
        val chinese = Subject("语文", R.drawable.chinese, "11.8", 5)
        subjectList.add(chinese)
        subjectList.add(chinese)
        subjectList.add(chinese)
        subjectList.add(chinese)
    }
    override fun onRefresh() {
        async() {

            /*val request = DownloadManager.Request(Uri.parse("http://pic2.cxtuku.com/00/02/31/b945758fd74d.jpg"))
//指定下载路径和下载文件名
            request.setDestinationInExternalFilesDir(AppContext.instance, Environment.DIRECTORY_DOWNLOADS,"temp.json")
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setTitle("通知标题XXX");
//获取下载管理器
//将下载任务加入下载队列，否则不会进行下载
            val dm:DownloadManager = AppContext.instance!!.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            val id = dm.enqueue(request)
            val query = DownloadManager.Query()
            query.setFilterById(id)
            val cursor = dm.query(query)
            if (cursor.moveToFirst()) {
                val status = cursor.getInt (cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
                when(status) {
                    DownloadManager . STATUS_PAUSED -> Log.d("a",">>>下载暂停")
                    DownloadManager . STATUS_PENDING -> Log.d("a",">>>下载延迟")
                    DownloadManager . STATUS_RUNNING -> Log.d("a",">>>正在下载")
                    DownloadManager . STATUS_SUCCESSFUL-> {
                        Log.d("a",">>>下载完成")
                        val filePath =AppContext.instance!!.getExternalFilesDir(null).path+"/Download/temp.json"
                        Log.d("a",filePath)
                        var result = ""
                        try {
                            val f = FileInputStream(filePath)
                            val bis = BufferedReader(InputStreamReader(f))
                            var line =bis.readLine()
                            while (line!= null) {
                                result += line
                                line=bis.readLine()
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                        Log.d("a",result)
                    }
                    DownloadManager . STATUS_FAILED -> Log.d("a",">>>下载失败")
                }
            }*/

            uiThread {

            }
        }
    }
    /*fun GetQuestions(){
        async() {
            val js:JSONObject = JSONObject()
            js.put("tf_id", "105010100")
            *//*js.put("tpye","1") // 1按数据库顺序(除9位tf_id（开始题号）和type外 参数还有题目数量)
                                // 2随机出题 （除7位tf_id（没有题号）和type外 参数还有题目数量）
                                // 3自己选题 （除7位tf_id（没有题号）和type外 参数还有题目数量 和 从1~题量数)
            js.put("num","2")*//*
            //info(HttpUtils.GetQuestions(js))
            var s:String = HttpUtils.GetQuestions(js)
            s=s.substring(1,s.length)
            val jjs: JSONObject = JSONObject(s)
            info(jjs.get("tf_sbj"))
        }
    }*/
}
