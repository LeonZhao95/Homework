package leon.homework.Activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import java.util.ArrayList;
import java.util.List;

import leon.homework.Fragments.MessageFragment;
import leon.homework.Fragments.MineFragment;
import leon.homework.Fragments.WriteFragment;
import leon.homework.R;

public class MainActivity extends FragmentActivity implements BottomNavigationBar.OnTabSelectedListener {
    private List<Fragment> fragments;
    public final static String CONNECT_STATUS = "connect_status";
    public final static String MESSAGE_RECEIVED_ACTION = "msg_received_action";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragments = new ArrayList<>();
        initView();
        //registerMessageReceiver();
    }

    /**
     * 创建视图
     */
    private void initView() {

        //得到BottomNavigationBar控件
        BottomNavigationBar navigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        //设置BottomNavigationBar的模式，会在下面详细讲解
        navigationBar.setMode(BottomNavigationBar.MODE_DEFAULT);
        //设置BottomNavigationBar的背景风格，后面详细讲解
        navigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);

        //我这里增加了3个Fragment
        BottomNavigationItem BNI_message = new BottomNavigationItem(R.mipmap.ic_launcher, "消息");
        BottomNavigationItem BNI_write = new BottomNavigationItem(R.mipmap.ic_launcher, "作业");
        BottomNavigationItem BNI_mine = new BottomNavigationItem(R.mipmap.ic_launcher, "我的");
        BNI_message.setActiveColor(R.color.colorAccent);
        //BottomNavigationItem("底部导航ico","底部导航名字")
        navigationBar.addItem(BNI_message)
                .addItem(BNI_write)
                .addItem(BNI_mine)
                .setFirstSelectedPosition(0)//默认选择索引为0的菜单
                .initialise();//对导航进行重绘

        fragments = getFragments();
        setDefaultFragment();
        navigationBar.setTabSelectedListener(this);
    }

    /**
     * 设置默认显示的fragment
     */
    private void setDefaultFragment() {

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.id_content, MessageFragment.Companion.newInstance("消息"));
        transaction.commit();
    }

    /**
     * 获取fragment
     *
     * @return fragment列表
     */
    private List<Fragment> getFragments() {

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(MessageFragment.Companion.newInstance("首页"));
        fragments.add(WriteFragment.Companion.newInstance());
        fragments.add(MineFragment.Companion.newInstance("我的"));
        return fragments;
    }

    @Override
    public void onTabSelected(int position) {

        if (fragments != null) {
            if (position < fragments.size()) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Fragment fragment = fragments.get(position);
                ft.replace(R.id.id_content, fragment);
                ft.commit();
            }
        }
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }
    /*private MessageReceiver mMessageReceiver;
    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(YunBaManager.MESSAGE_RECEIVED_ACTION);
        filter.addCategory(getPackageName());
        registerReceiver(mMessageReceiver, filter);

        IntentFilter filterCon = new IntentFilter();
        filterCon.addAction(YunBaManager.MESSAGE_CONNECTED_ACTION);
        filterCon.addCategory(getPackageName());
        registerReceiver(mMessageReceiver, filterCon);

        IntentFilter filterDis = new IntentFilter();
        filterDis.addAction(YunBaManager.MESSAGE_DISCONNECTED_ACTION);
        filterDis.addCategory(getPackageName());
        registerReceiver(mMessageReceiver, filterDis);

        IntentFilter pres = new IntentFilter();
        pres.addAction(YunBaManager.PRESENCE_RECEIVED_ACTION);
        pres.addCategory(getPackageName());
        registerReceiver(mMessageReceiver, pres);

    }

    public class MessageReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i("", "Action - " + intent.getAction());
            if (YunBaManager.MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                String status = "YunBa - Connected";
                setTitleOfApp(status);
                String topic = intent.getStringExtra(YunBaManager.MQTT_TOPIC);
                String msg = intent.getStringExtra(YunBaManager.MQTT_MSG);
                StringBuilder showMsg = new StringBuilder();
                showMsg.append("[Message] ").append(YunBaManager.MQTT_TOPIC)
                        .append(" = ").append(topic).append(" ,")
                        .append(YunBaManager.MQTT_MSG).append(" = ").append(msg);
                //setCostomMsg(showMsg.toString());

            } else if(YunBaManager.MESSAGE_CONNECTED_ACTION.equals(intent.getAction())) {
                //setCostomMsg("[YunBa] Connected");
                String status = "YunBa - Connected";
                setTitleOfApp(status);
                SharePrefsHelper.setString(getApplicationContext(), CONNECT_STATUS, status);
            } else if(YunBaManager.MESSAGE_DISCONNECTED_ACTION.equals(intent.getAction())) {
                //setCostomMsg("[YunBa] DisConnected");
                String status = "YunBa - DisConnected";
                setTitleOfApp(status);
                SharePrefsHelper.setString(getApplicationContext(), CONNECT_STATUS, status);
            } else if (YunBaManager.PRESENCE_RECEIVED_ACTION.equals(intent.getAction())) {
                String status = "YunBa - Connected";
                setTitleOfApp(status);
                String topic = intent.getStringExtra(YunBaManager.MQTT_TOPIC);
                String msg = intent.getStringExtra(YunBaManager.MQTT_MSG);
                StringBuilder showMsg = new StringBuilder();
                showMsg.append("[Message from prensence] ").append(YunBaManager.MQTT_TOPIC)
                        .append(" = ").append(topic).append(" ,")
                        .append(YunBaManager.MQTT_MSG).append(" = ").append(msg);
                //setCostomMsg(showMsg.toString());

            }
        }
    }
    private void  setTitleOfApp(final String status) {

        Activity parent = this.getParent();
        if(!YunbaUtil.isEmpty(status) && null != parent) {
            this.getParent().setTitle(status);
        }
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }*/
}