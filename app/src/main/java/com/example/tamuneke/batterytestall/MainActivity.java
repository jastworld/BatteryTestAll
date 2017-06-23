package com.example.tamuneke.batterytestall;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    ScrollView scrollView;
    Intent batteryIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        scrollView = (ScrollView)findViewById(R.id.scrollPane);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        TextView header = new TextView(this);
        header.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        header.setText("Battery API display");
        header.setTextColor(getResources().getColor(R.color.white));
        header.setGravity(Gravity.CENTER| Gravity.TOP);

        header.setBackgroundResource(R.color.fbblue);
        header.setTextSize(30);


        IntentFilter batteryIntentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        batteryIntent = registerReceiver(null, batteryIntentFilter);



        linearLayout.addView(header);
        linearLayout.addView(placeString("ACTION CHARGING",action_charging()+""));
        linearLayout.addView(placeString("ACTION DISCHARGING",action_discharging()+""));
        linearLayout.addView(placeString("ACTION NOT CHARGING",action_not_charging()+""));
        linearLayout.addView(placeString("ACTION FULL",action_full()+""));
        linearLayout.addView(placeString("BATTERY HEALTH DEAD",convHealth(battery_health())));
        linearLayout.addView(placeString("BATTERY PRESENT",extra_present()+""));












        scrollView.addView(linearLayout);


    }


    public TextView placeString(String description, String txt){


        TextView actionCharging1 = new TextView(this);
        actionCharging1.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        actionCharging1.setText(description+" "+txt);
        actionCharging1.setGravity(Gravity.CENTER);
        actionCharging1.setTextColor(getResources().getColor(R.color.white));
        actionCharging1.setBackgroundResource(R.color.darkgrey);
        actionCharging1.setTextSize(20);

        return actionCharging1;
    }

    private String convHealth(int health){
        String result;
        switch(health){
            case BatteryManager.BATTERY_HEALTH_COLD:
                result = "BATTERY_HEALTH_COLD";
                break;
            case BatteryManager.BATTERY_HEALTH_DEAD:
                result = "BATTERY_HEALTH_DEAD";
                break;
            case BatteryManager.BATTERY_HEALTH_GOOD:
                result = "BATTERY_HEALTH_GOOD";
                break;
            case BatteryManager.BATTERY_HEALTH_OVERHEAT:
                result = "BATTERY_HEALTH_OVERHEAT";
                break;
            case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
                result = "BATTERY_HEALTH_OVER_VOLTAGE";
                break;
            case BatteryManager.BATTERY_HEALTH_UNKNOWN:
                result = "BATTERY_HEALTH_UNKNOWN";
                break;
            case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE:
                result = "BATTERY_HEALTH_UNSPECIFIED_FAILURE";
                break;
            default:
                result = "UNCLASSIFIED";
        }

        return result;
    }

    /**
     * These methods indicate if a battery is charging, discharging, full, or not charging
     * */
    public boolean action_charging(){
        int status = batteryIntent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);;
        return status == BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_FULL;
    }


    public boolean action_discharging(){
        int status = batteryIntent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);;
        return status == BatteryManager.BATTERY_STATUS_DISCHARGING;
    }

    public boolean action_full(){
        int status = batteryIntent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);;
        return status == BatteryManager.BATTERY_STATUS_FULL;
    }


    public boolean action_not_charging(){
        int status = batteryIntent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);;
        return status == BatteryManager.BATTERY_STATUS_NOT_CHARGING;
    }


    /***/
    public int battery_health(){
        return  batteryIntent.getIntExtra(BatteryManager.EXTRA_HEALTH,0);
    }


    /**
     * Battery Plugged State to be used to test for plugged to usb,ac or wireless
     *
     * */
    public int pluggedState(){
        return batteryIntent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
    }
    public boolean pluggedToUSB(){
        return pluggedState() == BatteryManager.BATTERY_PLUGGED_USB;
    }

    public boolean pluggedToAC(){
        return pluggedState() == BatteryManager.BATTERY_PLUGGED_AC;
    }

    public boolean pluggedToWIRELESS(){
        return pluggedState() == BatteryManager.BATTERY_PLUGGED_WIRELESS;
    }



    /**
     * Indactes whether battery is present
     * */
    public boolean extra_present(){
        boolean  present= batteryIntent.getExtras().getBoolean(BatteryManager.EXTRA_PRESENT);
        return present;
    }





}
