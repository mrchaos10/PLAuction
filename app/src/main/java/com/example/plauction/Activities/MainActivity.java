package com.example.plauction.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.android.volley.VolleyError;
import com.example.plauction.Adapters.PageAdapter;
import com.example.plauction.Adapters.PlayersAdapter;
import com.example.plauction.Adapters.TeamListSpinnerAdapter;
import com.example.plauction.Common.CenteringTabLayout;
import com.example.plauction.Common.CommonFunctions;
import com.example.plauction.Entities.AuctionTeamsEntity;
import com.example.plauction.Entities.BootstrapEntity;
import com.example.plauction.Entities.Elements;
import com.example.plauction.Entities.Playerinfo;
import com.example.plauction.Fragments.TeamsFragment;
import com.example.plauction.Fragments.LeaderboardFragment;
import com.example.plauction.R;
import com.example.plauction.RestClientImpl.RESTClientImplementation;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    private ShimmerFrameLayout shimmerFrameLayout;
    private RelativeLayout relativeLayout;
    private CoordinatorLayout coordinatorLayout;
    private PageAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    TextView pageTitle;
    private AppBarLayout appBarLayout;
    Gson gson = new Gson();
    private ArrayList<String> teamsList=new ArrayList<>();
    ArrayList<Elements> elements;
    private Bundle bundle = new Bundle();
    private boolean isPagersSet=false;

    private void loadViewPager()
    {
        if(isPagersSet)
            return;

        isPagersSet = true;
        adapter = new PageAdapter(getSupportFragmentManager());

        // Add 2 Fragments

        // Teams Fragment
        TeamsFragment teamsFragment = new TeamsFragment();
        teamsFragment.setArguments(bundle);
        adapter.addFragment(teamsFragment, "TEAMS", 0);
        tabLayout.addTab(tabLayout.newTab().setText(adapter.getPageTitle(0)));

        // Leader board Fragment
        LeaderboardFragment leaderboardFragment = new LeaderboardFragment();
        adapter.addFragment(leaderboardFragment, "LEADERBOARD", 1);
        leaderboardFragment.setArguments(bundle);
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

    private void loadData(){
        shimmerFrameLayout.startShimmer();
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        RESTClientImplementation.getBootstrapStatic(new BootstrapEntity.OnListLoad() {
            @Override
            public void onListLoaded(int code, BootstrapEntity bootstrapEntity, VolleyError volleyError) {
                if(code == 200 && volleyError!=null){
                    elements=bootstrapEntity.getElements();
                    Map<Integer,Elements> map = new HashMap<Integer,Elements>();
                    for (Elements e : elements){
                        map.put(e.getId(),e);
                    }
                    // Populate player id to info map
                    CommonFunctions.setPlayerIdToElementMap_(map);
                    // This is a synchronous call as we need both data for our app
                    RESTClientImplementation.getAuctionTeams(new AuctionTeamsEntity.OnListLoad() {
                        @Override
                        public void onListLoaded(int code, final AuctionTeamsEntity[] auctionTeamsEntities, VolleyError volleyError) {
                            teamsList.add("SELECT A TEAM");
                            if(code == 200 && volleyError!=null){
                                // Set auction team players in bundle
                                Type listType = new TypeToken<List<AuctionTeamsEntity>>() {}.getType();
                                String teamsJson=gson.toJson(Arrays.asList(auctionTeamsEntities),listType);
                                bundle.putString("AUCTION_TEAMS",teamsJson);
                                loadViewPager();
                                CommonFunctions.makeSnackBar("Teams Data Fetched",coordinatorLayout).show();

                            }else if(code == 700){
                                shimmerFrameLayout.stopShimmer();
                                shimmerFrameLayout.setVisibility(View.GONE);
                                CommonFunctions.makeToast("No internet connectivity",getApplicationContext());
                            }else {
                                shimmerFrameLayout.stopShimmer();
                                shimmerFrameLayout.setVisibility(View.GONE);
                                CommonFunctions.makeToast(CommonFunctions.getErrorMessage(code, getApplicationContext()),getApplicationContext());
                            }
                        }
                    }, getApplicationContext());
                }
                else if(code == 700){
                    shimmerFrameLayout.stopShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);
                    CommonFunctions.makeToast("No internet connectivity",getApplicationContext());
                }
                else
                {
                    shimmerFrameLayout.stopShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);
                    CommonFunctions.makeToast(CommonFunctions.getErrorMessage(code, getApplicationContext()),getApplicationContext());

                }
            }
        },getApplicationContext());


    }



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

        loadData();

    }


    public void onAdapterLoaded() {
        Log.e("ON adapter","LOADED");
        shimmerFrameLayout.stopShimmer();
        shimmerFrameLayout.setVisibility(View.GONE);
        coordinatorLayout.setVisibility(View.VISIBLE);
    }
}