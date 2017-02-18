package leon.homework.Person

import android.util.Log
import io.yunba.android.manager.YunBaManager
import leon.homework.AppContext
import leon.homework.Data.Const
import leon.homework.Data.SaveData
import leon.homework.Utils.HttpUtils
import leon.homework.Utils.ImgHelper
import leon.homework.Utils.Utils.setid
import leon.homework.Utils.YunbaUtil
import org.eclipse.paho.client.mqttv3.IMqttActionListener
import org.eclipse.paho.client.mqttv3.IMqttToken
import org.eclipse.paho.client.mqttv3.MqttException
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream


/**
 * Created by BC on 2017/2/3 0003.
 */

class Student() {

    var phonenum: String = SaveData.phonenum
    var Stu_Name: String = SaveData.Stu_Name
    var Stu_Id: String = SaveData.Stu_Id
    var alia: String = SaveData.alia
    var avatar_path: String = SaveData.avatar_path
    var birthday: String = SaveData.birthday
    var sex: String = SaveData.sex
    var cls: String = SaveData.cls
    /*var Stu_Name: String? = null
    var Stu_Id: String? = null   //学号
    var alia: String? = null
    var avatar_path: String? = null
    var birthday: String? = null
    var sex: String? = null
    var cls: String? = null*/
    fun Register(phonenum: String, pwd: String):Int {
        val id = setid()
        val js = JSONObject()
        js.put("Stu_pho",phonenum)
        js.put("pswd",pwd)
        js.put("ay_id",id)
        return when(HttpUtils.RegisterStudent(js)){
            "0" ->{
                Const.REGISTER_FAIL
            }
            "1" ->{
                this.phonenum = phonenum
                this.alia = id
                SaveData.phonenum = phonenum
                SaveData.alia = alia
                Log.d("Student","已保存手机，别名")
                Alia(this.alia)
                Const.REGISTER_SUCCESS
            }
            else ->{
                Const.REGISTER_ERROR
            }
        }
    }
    fun Login(phonenum: String,pwd: String):Int{
        val js = JSONObject()
        js.put("Stu_pho",phonenum)
        js.put("pswd",pwd)
        val result = HttpUtils.LoginCheckStu(js)
        return when(result.get(0).toString()){
            "0" ->{
                Const.LOGIN_NOEXIST
            }
            "1" ->{
                Const.LOGIN_WRONGPWD
            }
            "2"->{
                if(phonenum == SaveData.phonenum){         //在本机注册过再登录的情况
                    if(SaveData.Stu_Id==""){
                        Log.d("Student_if","if")
                        this.alia = SaveData.alia
                        this.phonenum = SaveData.phonenum
                        Const.LOGIN_NEED_COMPLETE
                    }else{
                        Log.d("Student_if","else")
                        this.phonenum = SaveData.phonenum
                        this.alia = SaveData.alia
                        Alia(SaveData.alia)
                        this.Stu_Name = SaveData.Stu_Name
                        this.Stu_Id = SaveData.Stu_Id
                        this.avatar_path = SaveData.avatar_path
                        this.birthday = SaveData.birthday
                        this.sex = SaveData.sex
                        this.cls = SaveData.cls
                        Subscribe(SaveData.cls)
                        Log.d("Student-95","订阅频道")
                        Const.LOGIN_SUCCESS
                    }
                }else{
                    val Js: JSONObject = JSONObject(result.substring(1,result.length))
                    this.phonenum = phonenum
                    this.alia = Js.getString("ay_id")
                    SaveData.alia = this.alia
                    Alia(this.alia)
                    if(Js.getString("Stu_nam").isNullOrEmpty()){
                        Log.d("Student_else","if")
                        Const.LOGIN_NEED_COMPLETE
                    }else{
                        Log.d("Student_else","else")
                        this.Stu_Name = Js.getString("Stu_nam")
                        this.Stu_Id = Js.getString("Stu_id")
                        this.avatar_path = getImgPath(Js.getString("Stu_img"),this.alia)
                        this.birthday = Js.getString("Stu_bir")
                        this.sex = Js.getString("Stu_sex")
                        this.cls = Js.getString("Stu_cls")
                        Subscribe(this.cls)
                        Log.d("Student-114","订阅频道")
                        SaveStuData()
                        Const.LOGIN_SUCCESS
                    }
                }
            }
            else ->{
                Const.LOGIN_ERROR
            }
        }
    }
    fun CompleteInfo(name:String,Stu_Id:String,avatar_path:String,birthday:String,sex:String,cls:String):Int{
        val js = JSONObject()
        js.put("Stu_id",Stu_Id)
        js.put("ay_id",this.alia)
        js.put("Stu_pho",this.phonenum)
        js.put("Stu_nam",name)
        js.put("Stu_bir",birthday)
        js.put("Stu_sex",sex)
        js.put("Stu_cls",cls)
        js.put("Stu_img", ImgHelper.encodeImage(avatar_path))  //base64
        return when(HttpUtils.RegisterStuUpdata(js)){
                "1"->{
                    this.Stu_Name=name
                    this.Stu_Id=Stu_Id
                    this.avatar_path=avatar_path
                    this.birthday=birthday
                    this.sex=sex
                    this.cls=cls
                    Subscribe(cls)
                    Log.d("Student-143","订阅频道")
                    SaveStuData()
                    Const.COMPLETEINFO_SUCCESS
                }
                "0"->{
                    Const.COMPLETEINFO_FAIL
                }
                else->{
                    Const.COMPLETEINFO_ERROR
                }
        }
    }
    fun RetrievePwd(phonenum: String,pwd: String):Int{
        val js = JSONObject()
        js.put("Stu_pho",phonenum)
        js.put("pswd",pwd)
        return when(HttpUtils.ForgetPwdStu(js)){
            "0" ->{
                Const.Retrieve_FAIL
            }
            "1" ->{
                Const.Retrieve_SUCCESS
            }
            else ->{
                Const.Retrieve_ERROR
            }
        }
    }
    fun Alia(Alia :String){
        YunBaManager.setAlias(AppContext.instance, Alia,
                object : IMqttActionListener {
                    override fun onSuccess(asyncActionToken: IMqttToken) {
                        //YunbaUtil.showToast("success", applicationContext)
                        Log.d("Student","设置别名成功")
                    }
                    override fun onFailure(asyncActionToken: IMqttToken, exception: Throwable) {
                        if (exception is MqttException) {
                            val msg = "setAlias failed with error code : " + exception.reasonCode
                            YunbaUtil.showToast(msg, AppContext.instance)
                        }
                    }
                }
        )
    }
    fun Subscribe(topic:String){
        YunBaManager.subscribe(AppContext.instance, topic,
                object : IMqttActionListener {
                    override fun onSuccess(asyncActionToken: IMqttToken) {
                        Log.d("Student","订阅频道成功")
                    }
                    override fun onFailure(asyncActionToken: IMqttToken, exception: Throwable) {
                        if (exception is MqttException) {
                            val msg = "Subscribe failed with error code : " + exception.reasonCode
                            YunbaUtil.showToast(msg, AppContext.instance)
                        }

                    }
                }
        )
    }
    fun SaveStuData(){
        SaveData.phonenum = this.phonenum
        SaveData.alia = this.alia
        SaveData.Stu_Id = this.Stu_Id
        SaveData.Stu_Name = this.Stu_Name
        SaveData.avatar_path = this.avatar_path
        SaveData.birthday = this.birthday
        SaveData.sex = this.sex
        SaveData.cls = this.cls
        Log.d("Student","已保存学生数据")
    }
    fun getImgPath(base64: String,filename:String):String{
        val imgFilePath = AppContext.instance!!.filesDir.absolutePath
        val bts = ImgHelper.decode(base64)
        val file = File(imgFilePath, filename+".jpg")
        val fos = FileOutputStream(file)
        fos.write(bts)
        fos.close()
        return file.path
    }
}
