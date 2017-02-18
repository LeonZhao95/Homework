package leon.homework.Activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_login.*
import leon.homework.AppContext
import leon.homework.Data.Const
import leon.homework.Data.SaveData
import leon.homework.Extensions.loading
import leon.homework.Person.Student
import leon.homework.R
import org.jetbrains.anko.async
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread

class LoginActivity : BaseActivity(), View.OnClickListener {
    var stu_user: Student? = null
    override val layoutResourceId: Int = R.layout.activity_login

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        imgbtn_back.setOnClickListener(this)
        btn_login.setOnClickListener(this)
        btn_register.setOnClickListener(this)
        btn_forgetpwd.setOnClickListener(this)
        if(AppContext.stuUser!=null){
            stu_user = AppContext.stuUser!!
        }else{
            AppContext.stuUser = Student()
            stu_user = AppContext.stuUser!!
        }
    }

    override fun onClick(v: View) {
        val id = v.id
        when (id) {
            R.id.imgbtn_back -> finish()
            R.id.btn_login -> {
                if(et_phone.text.length==11&&et_pwd.text.length>=8){
                    loading(Const.SHOW)
                    CheckPwd()
                }else
                    toast("请输入正确格式手机号或密码")
            }
            R.id.btn_register -> {
                startActivity(Intent(LoginActivity@this, RegisterActivity::class.java))
                finish()
            }
            R.id.btn_forgetpwd -> {
                startActivity(Intent(LoginActivity@this, ForgetPwdActivity::class.java))
                finish()
            }
        }
    }

    fun CheckPwd(){
        async() {
            val result = stu_user!!.Login(et_phone.text.toString(),et_pwd.text.toString())
            uiThread {
                loading(Const.HIDE)
                when(result){
                    Const.LOGIN_SUCCESS->{
                        SaveData.isLogined = true
                        sendBroadcast(Intent("FinishWelcome"));
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        finish()
                    }
                    Const.LOGIN_NEED_COMPLETE->{
                        startActivity(Intent(this@LoginActivity, CompleteInfoActivity::class.java))
                        finish()
                    }
                    Const.LOGIN_NOEXIST->{
                        toast("此手机号不存在")
                    }
                    Const.LOGIN_WRONGPWD->{
                        toast("密码错误")
                    }
                    Const.LOGIN_ERROR->{
                        toast("服务器比较辣鸡，连接服务器失败")
                    }
               }
            }
        }
    }
}
