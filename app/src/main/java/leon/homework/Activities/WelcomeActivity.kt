package leon.homework.Activities

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_welcome.*
import leon.homework.R


class WelcomeActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        btn_login.setOnClickListener(this)
        btn_register.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_login -> {
                val intent = Intent(this@WelcomeActivity, LoginActivity::class.java)
                startActivity(intent)
            }
            R.id.btn_register -> {
                val intent1 = Intent(this@WelcomeActivity, RegisterActivity::class.java)
                startActivity(intent1)
            }
        }
    }

    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            // TODO Auto-generated method stub
            finish()
        }
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(broadcastReceiver)
    }
    override fun onResume() {
        super.onResume()
        val intentFilter:IntentFilter  =IntentFilter();
        intentFilter.addAction("FinishWelcome");
        registerReceiver(broadcastReceiver, intentFilter);
    }
}
