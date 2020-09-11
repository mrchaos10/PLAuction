package com.example.plauction.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.plauction.R;
import com.facebook.shimmer.ShimmerFrameLayout;

public class LeaderboardFragment extends Fragment {


    Context context;
    Activity activity;
    private ShimmerFrameLayout shimmerFrameLayout;
    private  View inflated_frag;
    private RelativeLayout relativeLayout;

    @Override
    public void onAttach(Context context) {
        this.context =context;
        activity = getActivity();
        super.onAttach(context);
    }

    @Override
    public void onDestroy() {
        this.context = null;
        activity = null;
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        this.context = null;
        activity = null;
        super.onDetach();
    }

    public LeaderboardFragment() {
        // Required empty public constructor
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //   eventDetailsLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        inflated_frag = inflater.inflate(R.layout.fragment_leaderboard, container, false);
        shimmerFrameLayout =(ShimmerFrameLayout)inflated_frag.findViewById(R.id.shimmer_leaderboard_frag);
        //relativeLayout = (RelativeLayout)activity.findViewById(R.id.relati);

        return inflated_frag;


    }
}
