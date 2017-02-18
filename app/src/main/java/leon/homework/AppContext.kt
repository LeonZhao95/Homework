package leon.homework

import android.app.Application
import android.os.Handler
import android.util.Log
import io.yunba.android.manager.YunBaManager
import leon.homework.Person.Student
import org.eclipse.paho.client.mqttv3.IMqttActionListener
import org.eclipse.paho.client.mqttv3.IMqttToken

/**
 * Created by BC on 2017/2/4 0004.
 */

class AppContext : Application() {
    private val TAG = "YunBaApplication"
    override fun onCreate() {
        super.onCreate()
        instance = this
        YunBaManager.start(applicationContext)
        YunBaManager.subscribe(applicationContext, arrayOf("aiye"), object : IMqttActionListener {
            override fun onSuccess(arg0: IMqttToken) {
                Log.d(TAG, "Subscribe topic succeed")
            }

            override fun onFailure(arg0: IMqttToken, arg1: Throwable) {
                Log.d(TAG, "Subscribe topic failed")
            }
        })
    }
    companion object{
        var instance: AppContext?= null
        var stuUser: Student? = null
        var downloadhandler:Handler? = null
    }

}
