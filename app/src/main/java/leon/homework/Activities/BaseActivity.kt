package leon.homework.Activities

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v7.app.AppCompatActivity
import leon.homework.Data.Const
import leon.homework.Dialogs.LoadingDialog
import org.jetbrains.anko.AnkoLogger

/**
 * Created by vslimit on 16/1/12.
 */
abstract class BaseActivity : AppCompatActivity(), AnkoLogger {

    abstract val layoutResourceId: Int
    var loadingDialog: LoadingDialog? = null
    open var handler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            //定义一个Handler，用于处理下载线程与UI间通讯
            if (!Thread.currentThread().isInterrupted) {
                when (msg.what) {
                    Const.SHOW -> loadingDialog!!.show()//显示进度对话框
                    Const.HIDE -> loadingDialog!!.hide()//隐藏进度对话框，不可使用dismiss()、cancel(),否则再次调用show()时，显示的对话框小圆圈不会动。
                }
            }
            super.handleMessage(msg)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutResourceId)
        loadingDialog = LoadingDialog(this)
    }

    override fun onResume() {
        super.onResume()
//        Bus.register(this)
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
//        Bus.unregister(this)
    }


    override fun onStop() {
        super.onStop()
        loadingDialog!!.dismiss()
    }


}