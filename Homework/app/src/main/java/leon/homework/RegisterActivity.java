package leon.homework;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

import static cn.smssdk.SMSSDK.getVerificationCode;
import static cn.smssdk.SMSSDK.submitVerificationCode;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener,Handler.Callback {
    private EditText et_phonenumber;
    private EditText et_verification;
    private EditText et_fpwd;
    private EditText et_spwd;
    private TextView tv_message;
    private TextView tv_message_pwd;
    private Button btn_verification;
    private Button btn_confirm;
    private Button btn_login;
    private Boolean iscreateable1=false;
    private Boolean iscreateable2=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        btn_verification = (Button) findViewById(R.id.btn_verification);
        btn_confirm = (Button) findViewById(R.id.btn_confirm);
        btn_login = (Button) findViewById(R.id.btn_login);
        et_phonenumber = (EditText) findViewById(R.id.et_phonenumber);
        et_verification = (EditText) findViewById(R.id.et_verification);
        et_fpwd = (EditText) findViewById(R.id.et_fpwd);
        et_spwd = (EditText) findViewById(R.id.et_spwd);
        tv_message = (TextView)findViewById(R.id.tv_message);
        tv_message_pwd = (TextView)findViewById(R.id.tv_message_pwd);
        assert btn_verification != null;
        btn_verification.setOnClickListener(this);
        assert btn_confirm != null;
        btn_confirm.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        et_fpwd.addTextChangedListener(txtfwatcher);
        et_spwd.addTextChangedListener(txtwatcher2);
        initSDK();
    }

    private void initSDK(){
        String APPKEY = "1aa15859c4d8a";
        String APPSECRET = "e9628d66a1bdbba7bca9d4266cfeb293";
        SMSSDK.initSDK(this, APPKEY, APPSECRET,true);
        final Handler handler = new Handler(this);
        EventHandler eventHandler = new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                handler.sendMessage(msg);
            }
        };
        SMSSDK.registerEventHandler(eventHandler);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_verification:
                getVerificationCode("86", et_phonenumber.getText().toString());
                break;
            case R.id.btn_confirm:
                if(iscreateable2&&iscreateable1)
                submitVerificationCode("86",et_phonenumber.getText().toString(),et_verification.getText().toString());
                break;
            case R.id.btn_login:
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
        }
    }

    @Override
    public boolean handleMessage(Message msg) {
        int event = msg.arg1;
        int result = msg.arg2;
        Object data = msg.obj;
        if (result == SMSSDK.RESULT_COMPLETE) {
            if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    Toast.makeText(RegisterActivity.this,"注册成功", Toast.LENGTH_SHORT).show();

            }else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                showTime();
                Toast.makeText(RegisterActivity.this,"验证码已发送",Toast.LENGTH_SHORT).show();
            }
        }else{
            try {
                JSONObject Json = new JSONObject(((Throwable) data).getMessage());
                setMessage(Json.getString("status"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public void setMessage(String s){
        switch (s){
            case "456":tv_message.setText("请输入手机号");break;
            case "457":tv_message.setText("手机号格式错误");break;
            case "466":tv_message.setText("请输入验证码");break;
            case "467":tv_message.setText("校验验证码请求频繁");break;
            case "468":tv_message.setText("验证码错误");break;
            case "601":tv_message.setText("短信发送受限");break;
            case "603":tv_message.setText("请填写正确的手机号码");break;
            case "604":tv_message.setText("当前服务暂不支持此国家");break;
            default:tv_message.setText("未知错误，错误代码:"+s);break;
        }
    }

    public void showTime() {
        new Thread(new Runnable() {
            int time=60,count=0;
            boolean result=true;
            @Override
            public void run() {
                while (result) {
                    time--;
                    try {
                        btn_verification.post(new Runnable() {
                            @Override
                            public void run() {
                                btn_verification.setText(time + "s后重新获取");
                                btn_verification.setClickable(false);
                                btn_verification.setBackgroundColor(Color.GRAY);//disClickable颜色
                            }
                        });
                        if (time <= 1) {
                            count=0;
                            result = false;
                            btn_verification.post(new Runnable() {
                                @Override
                                public void run() {
                                    btn_verification.setText("重新获取验证码");
                                    btn_verification.setClickable(true);
                                    btn_verification.setBackgroundColor(Color.LTGRAY);//Clickable颜色
                                }
                            });
                        }
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                result = true;
                time = 60;
            }
        }).start();
    }
    TextWatcher txtfwatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if(s.length()<8||s.length()>16){
                tv_message_pwd.setText("请输入8-16位密码");
                iscreateable1=false;
            }else{
                tv_message_pwd.setText("");
                iscreateable1=true;
            }
        }
    };
    TextWatcher txtwatcher2 = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if(s.length()>=8){
                if(s.equals(et_fpwd.getText())){
                    iscreateable2=true;
                }else{
                    tv_message_pwd.setText("两次密码不一致");
                }
            }
        }
    };
}
