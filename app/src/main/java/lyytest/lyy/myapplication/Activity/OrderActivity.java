package lyytest.lyy.myapplication.Activity;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


import lyytest.lyy.myapplication.Fragment.NewsFragment;
import lyytest.lyy.myapplication.Fragment.NewsFragment2;
import lyytest.lyy.myapplication.Fragment.NewsFragment3;
import lyytest.lyy.myapplication.Fragment.NewsFragment4;
import lyytest.lyy.myapplication.Fragment.NewsFragment5;
import lyytest.lyy.myapplication.R;
import lyytest.lyy.myapplication.adapter.Adapter;

public class OrderActivity extends AppCompatActivity {
    private android.support.v7.widget.Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ArrayList<String> list;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        toolbar = findViewById(R.id.toolbar);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        list = new ArrayList<>();
        NewsFragment newsFragment=new NewsFragment();
        NewsFragment2 newsFragment2 = new NewsFragment2();
        NewsFragment3 newFragment3 = new NewsFragment3();
        NewsFragment4 newsFragment4 = new NewsFragment4();
        NewsFragment5 newsFragment5 = new NewsFragment5();

        /* toolbar.setLogo(R.drawable.icon);//设置图片logo,你可以添加自己的图片*/
        toolbar.setTitle("用户订单情况:");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //通过HomeAsUp来让导航按钮显示出来
            actionBar.setDisplayHomeAsUpEnabled(true);
            //设置Indicator来添加一个点击图标
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_24dp);
        }

        list.add("未发订单");
        list.add("配送订单");
        list.add("完成订单");
        list.add("退钱订单");
        list.add("退款申请中");

        mFragments.add(newsFragment);
        mFragments.add(newsFragment2);
        mFragments.add(newFragment3);
        mFragments.add(newsFragment4);
        mFragments.add(newsFragment5);

        viewPager.setAdapter(new Adapter(getSupportFragmentManager(),mFragments,list));
        tabLayout.setupWithViewPager(viewPager);
    }

    protected void onStart() {
        super.onStart();

    }
}






