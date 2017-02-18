package leon.homework.Activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v7.app.AppCompatActivity
import android.util.Log
import leon.homework.AppContext
import leon.homework.Data.SaveData
import leon.homework.Guide
import leon.homework.Person.Student
import leon.homework.R

/**
 * Created by mjhzds on 2017/1/15.
 */

class InitActivity : AppCompatActivity() {
    private val TIME = 2000
    private val GO_WELCOME = 1000
    private val GO_GUIDE = 1001
    private val GO_MAIN = 1002
    private val mHandler = object : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                GO_WELCOME -> goWelcome()
                GO_GUIDE -> goGuide()
                GO_MAIN -> goMain()
            }
        }
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.welcome)
        if(SaveData.isStudent=="1"){
            initStu()
        }else if(SaveData.isStudent=="2"){
            //AppContext.tchUser = Teacher()
            //initTch()
        }else{
            Log.d("InitAct",SaveData.isStudent)
            mHandler.sendEmptyMessageDelayed(GO_GUIDE, TIME.toLong())
        }
    }

    private fun initStu() {
        AppContext.stuUser = Student()
        if(!SaveData.isLogined){
            mHandler.sendEmptyMessageDelayed(GO_WELCOME, TIME.toLong())
        }else{
            AppContext.stuUser!!.phonenum = SaveData.phonenum        //登录过
            AppContext.stuUser!!.alia = SaveData.alia
            AppContext.stuUser!!.Alia(SaveData.alia)
            AppContext.stuUser!!.Stu_Name = SaveData.Stu_Name
            AppContext.stuUser!!.Stu_Id = SaveData.Stu_Id
            AppContext.stuUser!!.avatar_path = SaveData.avatar_path
            AppContext.stuUser!!.birthday = SaveData.birthday
            AppContext.stuUser!!.sex = SaveData.sex
            AppContext.stuUser!!.cls = SaveData.cls
            AppContext.stuUser!!.Subscribe(SaveData.cls)
            mHandler.sendEmptyMessageDelayed(GO_MAIN, TIME.toLong())
        }
    }

    private fun goWelcome() {
        startActivity(Intent(this@InitActivity, WelcomeActivity::class.java))
        finish()
    }
    private fun goMain(){
        startActivity(Intent(this@InitActivity, MainActivity::class.java))
        finish()
    }
    private fun goGuide() {
        startActivity(Intent(this@InitActivity, Guide::class.java))
        finish()
    }
}
