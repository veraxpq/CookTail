package edu.neu.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.TableLayout;

import com.google.android.material.tabs.TabLayout;

import edu.neu.myapplication.adapter.ViewPagerAdapter;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    ViewPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        addTabs();

    }

    private void init() {

        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
    }

    private void addTabs() {

        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_learning_center));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_home));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_create));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_post));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_profile));

        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

//        tabLayout.getTabAt(1).setIcon(R.drawable.ic_home_fill);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

                switch (tab.getPosition()) {

                    case 0:
                        tabLayout.getTabAt(0).setIcon(R.drawable.ic_learning_center_fill);
                        break;
                    case 1:
                        tabLayout.getTabAt(1).setIcon(R.drawable.ic_home_fill);
                        break;
                    case 2:
                        tabLayout.getTabAt(2).setIcon(R.drawable.ic_create_fill);
                        break;
                    case 3:
                        tabLayout.getTabAt(3).setIcon(R.drawable.ic_post_fill);
                        break;
                    case 4:
                        tabLayout.getTabAt(4).setIcon(R.drawable.ic_profile_fill);
                        break;

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        tabLayout.getTabAt(0).setIcon(R.drawable.ic_learning_center);
                        break;
                    case 1:
                        tabLayout.getTabAt(1).setIcon(R.drawable.ic_home);
                        break;
                    case 2:
                        tabLayout.getTabAt(2).setIcon(R.drawable.ic_create);
                        break;
                    case 3:
                        tabLayout.getTabAt(3).setIcon(R.drawable.ic_post);
                        break;
                    case 4:
                        tabLayout.getTabAt(4).setIcon(R.drawable.ic_profile);
                        break;

                }

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {

                    case 0:
                        tabLayout.getTabAt(0).setIcon(R.drawable.ic_learning_center);
                        break;
                    case 1:
                        tabLayout.getTabAt(1).setIcon(R.drawable.ic_home);
                        break;
                    case 2:
                        tabLayout.getTabAt(2).setIcon(R.drawable.ic_create);
                        break;
                    case 3:
                        tabLayout.getTabAt(3).setIcon(R.drawable.ic_post);
                        break;
                    case 4:
                        tabLayout.getTabAt(4).setIcon(R.drawable.ic_profile);
                        break;

                }
            }
        });

    }
}