package leon.homework.Activities

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import cn.smssdk.EventHandler
import cn.smssdk.SMSSDK
import cn.smssdk.SMSSDK.getVerificationCode
import cn.smssdk.SMSSDK.submitVerificationCode
import kotlinx.android.synthetic.main.activity_register.*
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


class RegisterActivity : BaseActivity(), View.OnClickListener, Handler.Callback{
    override val layoutResourceId: Int = R.layout.activity_register
    var stu_user: Student? = null
    private var iscreateable: Boolean = false
    private var iscreateable2: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        btn_verification.setOnClickListener(this)
        btn_confirm.setOnClickListener(this)
        btn_login.setOnClickListener(this)
        btn_back.setOnClickListener(this)
        et_fpwd.addTextChangedListener(tw)
        et_spwd.addTextChangedListener(tw)
        if(AppContext.stuUser!=null){
            stu_user = AppContext.stuUser!!
        }else{
            AppContext.stuUser = Student()
            stu_user = AppContext.stuUser!!
        }
        initSDK()
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

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_verification -> getVerificationCode("86", et_phonenumber.text.toString())
            R.id.btn_confirm -> {
                if(iscreateable){
                    loading(Const.SHOW)
                    if(!iscreateable2){
                        submitVerificationCode("86", et_phonenumber.text.toString(), et_verification.text.toString())
                    }else{
                        Register()
                    }
                }
                else{
                    toast("请检查手机号和密码格式正确")
                }
            }
            R.id.btn_login -> {
                val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.btn_back ->{
                loading(Const.SHOW)
                Register()
                //finish()
            }
        }
    }

    override fun handleMessage(msg: Message): Boolean {
        val event = msg.arg1
        val result = msg.arg2
        val data = msg.obj
        loading(Const.HIDE)
        if (result == SMSSDK.RESULT_COMPLETE) {
            if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                iscreateable2=true
                Register()
            } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                showTime()
                Toast.makeText(this@RegisterActivity, "验证码已发送", Toast.LENGTH_SHORT).show()
            }
        } else {
            try {
                val Json = JSONObject((data as Throwable).message)
                tv_message_ver.text = Const.SMSCode(Json.getString("status"))
            } catch (e: JSONException) {
                e.printStackTrace()
            }

        }
        return false
    }
    fun Register(){
        async() {
            val result = stu_user!!.Register(et_phonenumber.text.toString(),et_fpwd.text.toString())
            uiThread {
                loading(Const.HIDE)
                when(result){
                    Const.REGISTER_SUCCESS ->{
                        toast("注册成功")
                        val intent = Intent(this@RegisterActivity, CompleteInfoActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    Const.REGISTER_FAIL ->{
                        toast("已存在手机号")
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
                        btn_verification.post {
                            btn_verification.text = time.toString() + "s后重新获取"
                            btn_verification.isClickable = false
                            btn_verification.setBackgroundColor(Color.GRAY)//disClickable颜色
                        }
                        if (time <= 1) {
                            count = 0
                            result = false
                            btn_verification.post {
                                btn_verification.text = "重新获取验证码"
                                btn_verification.isClickable = true
                                btn_verification.setBackgroundColor(Color.LTGRAY)//Clickable颜色
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
            if (!(et_fpwd!!.text.length >= 8 && et_fpwd.text.length <= 16)) {
                tv_message_pwd.text = "请输入8-16位密码"
            } else if (et_fpwd.text.toString() != et_spwd.text.toString() && et_spwd.text.length >= 8) {
                tv_message_pwd.text = "两次密码不一致"
            } else {
                tv_message_pwd.text = ""
                if (et_spwd.text.length >= 8) {
                    iscreateable = true
                }
            }
        }
    }


}
