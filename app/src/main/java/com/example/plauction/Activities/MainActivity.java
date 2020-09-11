package com.example.plauction.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.viewpager.widget.ViewPager;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.example.plauction.Adapters.PageAdapter;
import com.example.plauction.Common.CenteringTabLayout;
import com.example.plauction.Fragments.TeamsFragment;
import com.example.plauction.Fragments.LeaderboardFragment;
import com.example.plauction.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;


import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private ShimmerFrameLayout shimmerFrameLayout;
    private RelativeLayout relativeLayout;
    private CoordinatorLayout coordinatorLayout;
    private PageAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    TextView pageTitle;
    private AppBarLayout appBarLayout;

    private ArrayList<String> teamsList=new ArrayList<>();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // clear FLAG_TRANSLUCENT_STATUS flag:

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        setContentView(R.layout.activity_main);
        coordinatorLayout = findViewById(R.id.main_coordinate);
        shimmerFrameLayout = findViewById(R.id.shimmer_main);

        appBarLayout = (AppBarLayout) findViewById(R.id.htab_appbar);
        shimmerFrameLayout.startShimmer();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tabLayout =  findViewById(R.id.tab_layout);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager = (ViewPager) findViewById(R.id.pager);

        adapter = new PageAdapter(getSupportFragmentManager());

        // Add 2 Fragments

        // Teams Fragment
        TeamsFragment teamsFragment = new TeamsFragment();
        adapter.addFragment(teamsFragment, "TEAMS", 0);
        tabLayout.addTab(tabLayout.newTab().setText(adapter.getPageTitle(0)));

        // Leader board Fragment
        LeaderboardFragment leaderboardFragment = new LeaderboardFragment();
        adapter.addFragment(leaderboardFragment, "LEADERBOARD", 1);
        tabLayout.addTab(tabLayout.newTab().setText(adapter.getPageTitle(1)));
        // viewpager

        viewPager.setAdapter(adapter);

        pageTitle = (TextView) findViewById(R.id.page_title);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.addOnPageChangeListener(new CenteringTabLayout.TabLayoutOnPageChangeListener(tabLayout));

    }

    public void onAdapterLoaded() {
        Log.e("ON adapter","LOADED");
        shimmerFrameLayout.stopShimmer();
        shimmerFrameLayout.setVisibility(View.GONE);
        coordinatorLayout.setVisibility(View.VISIBLE);
    }
}