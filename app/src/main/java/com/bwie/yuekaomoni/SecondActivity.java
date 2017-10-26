package com.bwie.yuekaomoni;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.bwie.yuekaomoni.fragments.Fragmeent1;
import com.bwie.yuekaomoni.fragments.Fragmeent2;
import com.bwie.yuekaomoni.fragments.Fragmeent3;
import com.bwie.yuekaomoni.fragments.Fragmeent4;

import java.util.ArrayList;

/**
 * 作者：张玉轲
 * 时间：2017/10/25
 */

public class SecondActivity extends AppCompatActivity {

    private TabLayout tab;
    private ViewPager vp;
    private ArrayList<Fragment> fragments;
    private String[] titlearr;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_item);
        titlearr = new String[]{"最新日报","专栏","热门","主题日报"};
        tab = (TabLayout) findViewById(R.id.tab);
        vp = (ViewPager) findViewById(R.id.vp);

        fragments = new ArrayList<>();
        Fragmeent1 fragmeent1 = new Fragmeent1();
        Fragmeent2 fragmeent2 = new Fragmeent2();
        Fragmeent3 fragmeent3 = new Fragmeent3();
        Fragmeent4 fragmeent4 = new Fragmeent4();
        fragments.add(fragmeent1);
        fragments.add(fragmeent2);
        fragments.add(fragmeent3);
        fragments.add(fragmeent4);


        vp.setAdapter(new MyFragmentVp(getSupportFragmentManager()));
        //tab.setTabMode(TabLayout.MODE_SCROLLABLE);
        tab.setupWithViewPager(vp);
    }



    class MyFragmentVp extends FragmentPagerAdapter{

        public MyFragmentVp(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titlearr[position];
        }
    }
}
