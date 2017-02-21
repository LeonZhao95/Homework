package leon.homework.Fragments

import android.annotation.TargetApi
import android.app.Fragment
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Build
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RelativeLayout
import leon.homework.Activities.OneQuestion
import leon.homework.R


class Questions : Fragment() {
    internal var position: Int = 0
    internal var num: Int = 0
    internal var idBeginWith: Int = 0
    internal var foreNum: Int = 0
    internal var jString: String?=null

    // 从mainActivity传人题的数量、起始id、前面的题数、fragment数组
    fun quesSetter(num: Int, idBeginWith: Int, foreNum: Int, jString: String) {
        this.num = num
        this.idBeginWith = idBeginWith
        this.foreNum = foreNum
        this.jString = jString
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup,
                              savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        var cFragment = inflater.inflate(R.layout.fragment_questions, container, false)
        cFragment = course(cFragment)
        return cFragment
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    fun course(cFragment: View): View {
        //自定义layout组件
        val layout = cFragment.findViewById(R.id.littleFrame) as RelativeLayout

        //创建button
        val dm = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(dm)
        val width = dm.widthPixels
        val loc = (width - 164) / 5
        //        int height = dm.heightPixels;
        //这里创建num个按钮，每行放置6个按钮
        var j = -1
        val context = cFragment.context
        val Btn = arrayOfNulls<Button>(num)
        for (i in 0..num - 1) {
            Btn[i] = Button(context)
            //            设置ID
            Btn[i]!!.id = idBeginWith + i
            Btn[i]!!.text = (i + 1 + foreNum).toString()
            Btn[i]!!.setPadding(0, 0, 0, 3)
            Btn[i]!!.textSize = 15f
            val xrp = resources.getXml(R.drawable.selector_textcolor)
            try {
                val csl = ColorStateList.createFromXml(resources, xrp)
                Btn[i]!!.setTextColor(csl)
            } catch (e: Exception) {

            }

            position = i
            Btn[i]!!.background = ContextCompat.getDrawable(activity,R.drawable.selector_button_green_cir)
            Btn[i]!!.setOnClickListener({ v ->
                val intent = Intent(activity, OneQuestion::class.java)
                intent.putExtra("number", v.id)
                intent.putExtra("exercise", jString)
                startActivity(intent)
            })
            val btParams = RelativeLayout.LayoutParams(100, 100)  //设置按钮的宽度和高度
            if (i % 6 == 0) {
                j++
            }
            btParams.leftMargin = 32 + loc * (i % 6)   //横坐标定位
            btParams.topMargin = 32 + 164 * j   //纵坐标定位
            layout.addView(Btn[i], btParams)   //将按钮放入layout组件
        }

        return cFragment
    }
}
