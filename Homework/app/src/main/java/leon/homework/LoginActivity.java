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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageButton imgbtn_back;
    private Button btn_login;
    private Button btn_register;
    private Button btn_forgetpwd;
    private EditText et_phone;
    private EditText et_pwd;
    private TextView tv_massage;

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
        tv_massage = (TextView)findViewById(R.id.tv_message);
    }

    public void checkpwd(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //注册驱动
                    Class.forName("com.mysql.jdbc.Driver");
                    Log.e(null, "run: >>>数据库驱动成功加载" );
                    String url = "jdbc:mysql://zz503379238.oicp.net:24125/appdata";
                    Connection conn = DriverManager.getConnection(url, "root", "123456");
                    Log.e(null, "run: 》》》》成功连接");
                    Statement stmt = conn.createStatement();
                    String sql = "select * from student";
                    ResultSet rs = stmt.executeQuery(sql);
                    while (rs.next()) {
                        Log.v("yzy", "field1-->"+rs.getString(1));
                    }
                    rs.close();
                    stmt.close();
                    conn.close();
                    Log.v("yzy", "success to connect!");
                }catch(ClassNotFoundException e) {
                    Log.v("yzy", "fail to connect!"+"  "+e.getMessage());
                }catch (SQLException e) {
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
                Intent intent = new Intent(LoginActivity.this,WelcomeActivity.class);
                startActivity(intent);
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
