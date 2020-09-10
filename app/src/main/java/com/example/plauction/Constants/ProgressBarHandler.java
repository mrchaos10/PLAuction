package com.example.plauction.Constants;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Transformation;
import android.widget.ProgressBar;

import com.example.plauction.R;


class ProgressBarAnimation extends Animation{
    private ProgressBar pb;
    private float to;
    private float from;
    private  float currentValue;
    private boolean flag;

    public  ProgressBarAnimation(ProgressBar pb, float from, float to, boolean flag){
        this.pb = pb;
        this.from = from;
        this.to = to;
        this.flag = flag;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t){
        super.applyTransformation(interpolatedTime,t);
        float value = from + (to-from) * interpolatedTime;
        currentValue = value;
        if(!flag && value <=700){
            pb.setProgress((int)value);
        }else  if(flag){
            pb.setProgress((int)value);
        }
    }

    public float getCurrentValue() {
        return currentValue;
    }
}

public class ProgressBarHandler {

    private ProgressBar pb;
    private final int MAX_WIDTH = 1000;
    private final int TIMER_RUNTIME = 5000;
    private boolean pbActive;
    private  ProgressBarAnimation pba;
    private ViewGroup layout;
    private  View progressLayout;
    private Activity activity;

    public ProgressBarHandler(Context context, View v,Activity activity) {

        // set class variables
        layout = (ViewGroup) v;
        LayoutInflater inflater = LayoutInflater.from(context);
        progressLayout = inflater.inflate(R.layout.progress_bar, null);
        pb = (ProgressBar)progressLayout.findViewById(R.id.progressBar);
        this.activity = activity;
        // Progress bar properties
        pb.setMax(MAX_WIDTH);
        pb.setVisibility(View.INVISIBLE);
        pb.setIndeterminate(false);

    }

    public  void show(){
        // Remove existing view from parent ( Clicking button multiple times )
        if(activity.findViewById(R.id.progressBar)!=null){
            ((ViewGroup)progressLayout.getParent()).removeView(progressLayout);
        }

        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        activity.addContentView(progressLayout,layoutParams);

        // Start progress
        pb.setProgress(0);
        pb.setVisibility(View.VISIBLE);
        pba  = new ProgressBarAnimation(this.pb, 0, 1000, false);
        pba.setDuration(5000);

        // Animation properties
        pba.setFillAfter(true);
        pb.startAnimation(pba);


    }
    public void hide(){
        // Get last value visited
        float currentValue = pba.getCurrentValue();
        ProgressBarAnimation hideAnim = new ProgressBarAnimation(pb, currentValue, 1000, true);
        hideAnim.setDuration(500);
        pb.setInterpolator(new DecelerateInterpolator());

        // New animation to end progress
        hideAnim.setAnimationListener(new Animation.AnimationListener(){
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // Hide the progress bar
                ((ViewGroup)progressLayout.getParent()).removeView(progressLayout);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        pb.startAnimation(hideAnim);





    }





}
