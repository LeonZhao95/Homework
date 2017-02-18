package leon.homework.Activities

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import cn.smssdk.EventHandler
import cn.smssdk.SMSSDK
import kotlinx.android.synthetic.main.activity_forget_pwd.*
import leon.homework.AppContext
import leon.homework.Data.Const
import leon.homework.Extensions.loading
import leon.homework.Person.Student
import leon.homework.R
import org.jetbrains.anko.async
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread
import org.json.JSONException
import org.json.JSONObject


class ForgetPwdActivity : BaseActivity(), View.OnClickListener,Handler.Callback {
    val stu_user: Student=AppContext.stuUser!!
    private var iscorrcet = false
    override val layoutResourceId: Int = R.layout.activity_forget_pwd
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSDK()
        btn_confirm_forget.setOnClickListener(this)
        btn_verification_forget.setOnClickListener(this)
        et_fpwd_forget.addTextChangedListener(tw)
        et_spwd_forget.addTextChangedListener(tw)
    }

    private fun initSDK() {
        val APPKEY = "1aa15859c4d8a"
        val APPSECRET = "e9628d66a1bdbba7bca9d4266cfeb293"
        SMSSDK.initSDK(this, APPKEY, APPSECRET, true)
        val handler = Handler(this)
        val eventHandler = object : EventHandler() {
            override fun afterEvent(event: Int, result: Int, data: Any?) {
                val msg = Message()
                msg.arg1 = event
                msg.arg2 = result
                msg.obj = data
                handler.sendMessage(msg)
            }
        }
        SMSSDK.registerEventHandler(eventHandler)
    }
    override fun handleMessage(msg: Message): Boolean {
        val event = msg.arg1
        val result = msg.arg2
        val data = msg.obj
        if (result == SMSSDK.RESULT_COMPLETE) {
            if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                ChangePwd()
            } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                showTime()
                toast("验证码已发送")
            }
        } else {
            try {
                val Json = JSONObject((data as Throwable).message)
                tv_message_ver_forget.text = Const.SMSCode(Json.getString("status"))
            } catch (e: JSONException) {
                e.printStackTrace()
            }

        }
        return false
    }
    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_verification_forget -> {
                SMSSDK.getVerificationCode("86", et_phone_forget.text.toString())
            }
            R.id.btn_confirm_forget -> {
                if(et_phone_forget.text.isEmpty()) tv_message_ver_forget.text = "请输入手机号"
                else{
                    if(iscorrcet){
                        loading(Const.SHOW)
                        ChangePwd()
                    }else{
                        tv_message_ver_forget.text = "请输入密码"
                    }
                }
            }
        }
    }

    fun ChangePwd(){
        async() {
            val result = stu_user.RetrievePwd(et_phone_forget.text.toString(),et_fpwd_forget.text.toString())
            uiThread {
                loading(Const.HIDE)
                when(result){
                    Const.REGISTER_SUCCESS ->{
                        toast("重置成功")
                        startActivity(Intent(this@ForgetPwdActivity, CompleteInfoActivity::class.java))
                        finish()
                    }
                    Const.REGISTER_FAIL ->{
                        toast("不存在手机号")
                    }
                    Const.REGISTER_ERROR->{
                        toast("服务器比较辣鸡，连接服务器失败")
                    }
                }
            }
        }
    }

    fun showTime() {
        Thread(object : Runnable {
            internal var time = 60
            internal var count = 0
            internal var result = true
            override fun run() {
                while (result) {
                    time--
                    try {
                        btn_verification_forget.post {
                            btn_verification_forget.text = time.toString() + "s后重新获取"
                            btn_verification_forget.isClickable = false
                            btn_verification_forget.setBackgroundColor(Color.GRAY)//disClickable颜色
                        }
                        if (time <= 1) {
                            count = 0
                            result = false
                            btn_verification_forget.post {
                                btn_verification_forget.text = "重新获取验证码"
                                btn_verification_forget.isClickable = true
                                btn_verification_forget.setBackgroundColor(Color.LTGRAY)//Clickable颜色
                            }
                        }
                        Thread.sleep(1000)
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                }
                result = true
                time = 60
            }
        }).start()
    }
    internal var tw: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable) {
            if (!(et_fpwd_forget.text.length >= 8 && et_fpwd_forget.text.length <= 16)) {
                tv_message_ver_forget.text = "请输入8-16位密码"
            } else if (et_fpwd_forget.text.toString() != et_spwd_forget.text.toString() && et_spwd_forget.text.length >= 8) {
                tv_message_ver_forget.text = "两次密码不一致"
            } else {
                tv_message_ver_forget.text = ""
                if (et_spwd_forget.text.length >= 8) {
                    iscorrcet = true
                }
            }
        }
    }
}
