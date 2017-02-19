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

    companion object {
        fun newInstance(): WriteFragment {
            return WriteFragment()
        }
    }
    override val layoutResourceId: Int =R.layout.fragment_write
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

            uiThread {

            }
        }
    }
}
