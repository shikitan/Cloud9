package com.example.yunita.tradiogc;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

public class MainActivity extends TabActivity {

    private String textArray[] = {"Notification", "Friends", "Market", "Profile"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        manageTab();


    }

    public void manageTab() {
        TabHost tabHost = this.getTabHost();
        TabHost.TabSpec spec;
        Intent intent;
        //Class activityArray[] = {NotificationActivity.class, FriendsActivity.class,
        //        MarketActivity.class, ProfileActivity.class};
        Class activityArray[] = {LoginActivity.class, LoginActivity.class,
                LoginActivity.class, LoginActivity.class};

        for(int i = 0; i < 4; i++){
            intent=new Intent().setClass(this, activityArray[i]);
            spec = tabHost.newTabSpec(textArray[i]).setIndicator(getTabItemView(i)).setContent(intent);
            tabHost.addTab(spec);
            tabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.selector_tab_background);
        }

        tabHost.setCurrentTab(0);
    }

    private View getTabItemView(int index){
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View view = layoutInflater.inflate(R.layout.bottom_tab_button, null);
        int imageViewArray[] = {R.drawable.bottom_message_button,R.drawable.bottom_friends_button,R.drawable.bottom_market_button,
                R.drawable.bottom_profile_button};

        ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
        imageView.setImageResource(imageViewArray[index]);

        TextView textView = (TextView) view.findViewById(R.id.textview);
        textView.setText(textArray[index]);

        return view;
    }


}
