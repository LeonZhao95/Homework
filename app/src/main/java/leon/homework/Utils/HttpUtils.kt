package leon.homework.Utils

import android.util.Log
import leon.homework.Data.Const
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


/**
 * Created by BC on 2017/1/22 0022.
 */
object HttpUtils{
    fun RegisterStuUpdata(json: JSONObject) = PostJson(json, Const.RegisterStuUpdataUrl)
    fun LoginCheckStu(json: JSONObject) = PostJson(json, Const.LoginCheckUrl)
    fun RegisterStudent(json: JSONObject) = PostJson(json, Const.RegisterStuUrl)
    fun ForgetPwdStu(json: JSONObject) = PostJson(json,Const.ForgetPwdStuUrl)
    fun PostJson(json: JSONObject, url: String): String {
        var data :String = ""
        try {
            val content = json.toString()
            Log.d("JSON数据", ">>>>>>>>>>>>>>>>>>>>" + content)
            val result = URL(url)
            val conn = result.openConnection() as HttpURLConnection
            conn.connectTimeout = 5000
            conn.doOutput = true
            conn.requestMethod = "POST"
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8")
            val os = conn.outputStream
            os.write(content.toByteArray())
            os.close()
            val sb = StringBuilder()
            val Is = conn.inputStream
            val isr = InputStreamReader(Is)
            val br = BufferedReader(isr)
            var msg = br.readLine()
            while (msg != null) {
                sb.append(msg)
                msg = br.readLine()
            }
            data = sb.toString()
            Log.d("返回码", ">>>>>>>>>>>>>>>>>>>>" + data)
        } catch (e: Exception) {
            e.printStackTrace()
            return "ERROR:"+e.message
        }
        return data

    }

}