package com.fiek.helloworldfiek_2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.widget.Toast;

public class AirplaneModeChangeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(isAirplaneModeActive(context))
        {
            Toast.makeText(context, "AirplaneMode is ON", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context, "AirplaneMode is OFF", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isAirplaneModeActive(Context context)
    {
        return Settings.System.getInt(context.getContentResolver(),
                Settings.Global.AIRPLANE_MODE_ON,0) != 0;
    }
}
