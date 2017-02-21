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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragments = new ArrayList<>();
        initView();
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
}