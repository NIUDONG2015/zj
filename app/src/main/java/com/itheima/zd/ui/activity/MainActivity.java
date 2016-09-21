package com.itheima.zd.ui.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;

import com.itheima.zd.R;
import com.itheima.zd.adapter.MainPagerAdapter;
import com.itheima.zd.ui.widget.PagerSlidingTab;


public class MainActivity extends ActionBarActivity {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private PagerSlidingTab slidingTab;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();//初始化VIew
        setActionBar();//设置ActionBar
    }

    /**
     * 初始化View
     */
    public void initView() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        slidingTab = (PagerSlidingTab) findViewById(R.id.slidingTab);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        //1.填充ViewPager
        MainPagerAdapter adapter = new MainPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        //2.绑定ViewPager和Indicator
        slidingTab.setViewPager(viewPager);
//		System.setOut();


        //该方法有弊有利
        //利：非常方便的帮助我们缓存所有的page
        //弊：会预加载所有的页数，会过多消耗用户流量，同时很可能造成卡顿
//		viewPager.setOffscreenPageLimit(adapter.getCount());
    }

    /**
     * 设置ActionBar
     */
    public void setActionBar() {
        //1.获取ActionBar对象
        ActionBar actionBar = getSupportActionBar();
        //2.设置图标和标题
        actionBar.setIcon(R.drawable.ic_launcher);
        actionBar.setTitle(R.string.app_name);
        //3.启用ActionBar的home按钮，
        actionBar.setDisplayHomeAsUpEnabled(true);//显示home按钮
        actionBar.setDisplayShowHomeEnabled(true);//设置home按钮可以被点击
        //4.将home按钮的返回箭头替换为汉堡包按钮
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, 0, 0);
        drawerToggle.syncState();//同步ActionBar和DrawerLayout的状态

        //5.吃掉汉堡包，就是给3条线增加动画
//		drawerLayout.setDrawerListener(new DrawerListener() {
//			@Override
//			public void onDrawerStateChanged(int newState) {
//				drawerToggle.onDrawerStateChanged(newState);
//			}
//			@Override
//			public void onDrawerSlide(View drawerView, float slideOffset) {
//				drawerToggle.onDrawerSlide(drawerView, slideOffset);
//			}
//			@Override
//			public void onDrawerOpened(View drawerView) {
//				drawerToggle.onDrawerOpened(drawerView);
//			}
//			@Override
//			public void onDrawerClosed(View drawerView) {
//				drawerToggle.onDrawerClosed(drawerView);
//			}
//		});
        drawerLayout.setDrawerListener(drawerToggle);
    }

    /**
     * 点击ActionBar的home按钮会执行该方法，所以在该方法中做判断
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //如果左边的菜单是打开的
//			if(drawerLayout.isDrawerOpen(Gravity.START)){
//				drawerLayout.closeDrawer(Gravity.START);//关闭左边的菜单
//			}else {
//				drawerLayout.openDrawer(Gravity.START);//打开左边的菜单
//			}
                drawerToggle.onOptionsItemSelected(item);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
