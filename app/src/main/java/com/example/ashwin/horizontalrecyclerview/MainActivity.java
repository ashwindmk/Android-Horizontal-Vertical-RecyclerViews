package com.example.ashwin.horizontalrecyclerview;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Context mContext;
    private Activity mActivity;

    private RelativeLayout mRelativeLayout;
    private RecyclerView mHorizontalRecyclerView;
    private RecyclerView.LayoutManager mHorizontalLayoutManager;
    private RecyclerView.Adapter mHorizontalAdapter;

    private RecyclerView mVerticalRecyclerView;
    private RecyclerView.LayoutManager mVerticalLayoutManager;
    private RecyclerView.Adapter mVerticalAdapter;

    private Random mRandom = new Random();

    // Initialize a new BroadcastReceiver instance
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Receive the broadcast message
            int receivedColor = intent.getIntExtra("Color",Color.WHITE);
            mRelativeLayout.setBackgroundColor(receivedColor);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the application context
        mContext = getApplicationContext();

        // Get the activity
        mActivity = MainActivity.this;

        // Register the Local Broadcast
        LocalBroadcastManager.getInstance(mContext).registerReceiver(
                mBroadcastReceiver,
                new IntentFilter("BROADCAST_COLOR")
        );

        // Get the widgets reference from XML layout
        mRelativeLayout = (RelativeLayout) findViewById(R.id.rl);
        mHorizontalRecyclerView = (RecyclerView) findViewById(R.id.horizontal_recycler_view);
        mVerticalRecyclerView = (RecyclerView) findViewById(R.id.vertical_recycler_view);

        // Specify a layout for RecyclerView
        // Create a horizontal RecyclerView
        mHorizontalLayoutManager = new LinearLayoutManager(
                mContext,
                LinearLayoutManager.HORIZONTAL,
                false
        );
        mHorizontalRecyclerView.setLayoutManager(mHorizontalLayoutManager);

        // Vertical Recycler View
        mVerticalLayoutManager = new LinearLayoutManager(
                mContext,
                LinearLayoutManager.VERTICAL,
                false
        );
        mVerticalRecyclerView.setLayoutManager(mVerticalLayoutManager);

        // Initialize a new colors list
        List<String> colors = new ArrayList<>();

        // Put some colors on the list
        for (int i=0;i<50;i++){
            // Add a random color to the list
            colors.add(getRandomHSVColor() + "");
        }

        // Initialize a new Adapter for RecyclerView
        mHorizontalAdapter = new ColorsAdapter(mContext,colors);

        // Set an adapter for RecyclerView
        mHorizontalRecyclerView.setAdapter(mHorizontalAdapter);


        // Initialize a new Adapter for Vertical RecyclerView
        mVerticalAdapter = new ColorsAdapter(mContext,colors);

        // Set an adapter for RecyclerView
        mVerticalRecyclerView.setAdapter(mVerticalAdapter);
    }

    // Custom method to generate random HSV color
    public int getRandomHSVColor(){
        // Generate a random hue value between 0 to 360
        int hue = mRandom.nextInt(361);
        // We make the color depth full
        float saturation = 1.0f;
        // We make a full bright color
        float value = 1.0f;
        // We avoid color transparency
        int alpha = 255;
        // Finally, generate the color
        int color = Color.HSVToColor(alpha, new float[]{hue, saturation, value});
        // Return the color
        return color;
    }
}
