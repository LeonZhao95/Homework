package leon.homework

import android.content.Intent
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import leon.homework.Activities.WelcomeActivity
import leon.homework.Adapter.ViewPagerAdapter
import leon.homework.Data.SaveData
import leon.homework.Person.Student
import java.util.*

/**
 * Created by mjhzds on 2017/1/14.
 */

class Guide : AppCompatActivity(), ViewPager.OnPageChangeListener {
    private var vp: ViewPager? = null
    private var vpAdapter: ViewPagerAdapter? = null
    private var views: MutableList<View>? = null
    private var dots: ArrayList<ImageView>? = null
    private val ids = intArrayOf(R.id.iv1, R.id.iv2, R.id.iv3)
    private var btn_student: Button? = null
    private var btn_teacher: Button? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.guide)
        initViews()
        initDots()
    }

    private fun initViews() {
        val inflater = LayoutInflater.from(this)

        views = ArrayList<View>()
        views!!.add(inflater.inflate(R.layout.one, null))
        views!!.add(inflater.inflate(R.layout.two, null))
        views!!.add(inflater.inflate(R.layout.three, null))

        vpAdapter = ViewPagerAdapter(views, this)
        vp = findViewById(R.id.viewpager) as ViewPager
        vp!!.adapter = vpAdapter
        vp!!.addOnPageChangeListener(this)
        btn_student = views!![2].findViewById(R.id.btn_student) as Button
        btn_student!!.setOnClickListener {
            SaveData.isStudent = "1"
            Log.d("Guide","保存身份")
            AppContext.stuUser = Student()
            SaveData.isFirstIn = false
            startActivity(Intent(this@Guide, WelcomeActivity::class.java))
            finish()
        }
        btn_teacher = views!![2].findViewById(R.id.btn_teacher) as Button
        btn_teacher!!.setOnClickListener {
            SaveData.isStudent = "2"
            //AppContext.tchUser = Teacher()
            SaveData.isFirstIn = false
            startActivity(Intent(this@Guide, WelcomeActivity::class.java))
            finish()
        }
    }

    private fun initDots() {
        dots = ArrayList(views!!.size)
        for (i in views!!.indices) {
            dots!!.add(findViewById(ids[i]) as ImageView)
        }
    }


    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }

    override fun onPageSelected(position: Int) {
        for (i in views!!.indices) {
            if (position == i) {
                dots!![i].setImageResource(R.drawable.login_point_selected)
            } else {
                dots!![i].setImageResource(R.drawable.login_point)
            }
        }
    }

    override fun onPageScrollStateChanged(state: Int) {

    }
}
