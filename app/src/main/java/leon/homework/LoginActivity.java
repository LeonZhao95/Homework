package leon.homework;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageButton imgbtn_back;
    private Button btn_login;
    private Button btn_register;
    private Button btn_forgetpwd;
    private EditText et_phone;
    private EditText et_pwd;
    private TextView tv_massage_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        imgbtn_back = (ImageButton)findViewById(R.id.imgbtn_back);
        btn_login = (Button)findViewById(R.id.btn_login);
        btn_register = (Button)findViewById(R.id.btn_register);
        btn_forgetpwd = (Button)findViewById(R.id.btn_forgetpwd);
        imgbtn_back.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        btn_register.setOnClickListener(this);
        btn_forgetpwd.setOnClickListener(this);
        et_phone = (EditText)findViewById(R.id.et_phone);
        et_pwd = (EditText)findViewById(R.id.et_pwd);
        tv_massage_phone = (TextView)findViewById(R.id.tv_message_phone);
    }

    public void checkpwd(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String data = null;
                    String params = "act=1";
                    params = params +"&vid="+2;
                    URL url = new URL("http://zz503379238.oicp.net/index.php");
                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String msg = "";
                    while((msg = br.readLine())!=null){
                        sb.append(msg);
                    }
                    data = sb.toString();
                    Toast.makeText(null,data,Toast.LENGTH_SHORT).show();
                    Log.d(null, "run: >>>>>>>>>>"+data);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Message msg = new Message();
                msg.what=1;
                handler.sendMessage(msg);
            }
        }).start();
    }

    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            if (msg.what==1){
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgbtn_back:
                finish();
                break;
            case R.id.btn_login:
                checkpwd();
                break;
            case R.id.btn_register:
                Intent intent2 = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent2);
                finish();
                break;
            case R.id.btn_forgetpwd:
                break;
        }
    }
}
