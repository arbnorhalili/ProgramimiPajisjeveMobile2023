package com.fiek.helloworldfiek_2;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyCallback;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;

public class OnlineRadioActivity extends AppCompatActivity {

    ImageView imgPlayStop;
    MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_radio);

        imgPlayStop = findViewById(R.id.imgPlayStop);

        imgPlayStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaPlayer==null || mediaPlayer.isPlaying()==false)
                {
                    imgPlayStop.setImageResource(R.drawable.baseline_stop_circle_24);
                    mediaPlayer = new MediaPlayer();
                    try {
                        mediaPlayer.setDataSource("http://mediaserv30.live-streams.nl:8086/live");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mediaPlayer) {
                            mediaPlayer.start();
                        }
                    });
                    mediaPlayer.prepareAsync();
                }
                else
                {
                    imgPlayStop.setImageResource(R.drawable.baseline_play_circle_filled_24);
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                }
            }
        });
    }

    private void InitializeTelephonyManager()
    {
        TelephonyManager telephonyManager =
                (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
        {
            telephonyManager.registerTelephonyCallback(
                    getApplicationContext().getMainExecutor(),
                    new CallStateListener()
            );
        }
        else
        {
            PhoneStateListener phoneStateListener = new PhoneStateListener()
            {
                @Override
                public void onCallStateChanged(int state, String phoneNumber) {
                    if(state == TelephonyManager.CALL_STATE_IDLE)
                    {
                        mediaPlayer.setVolume(1,1);
                    }
                    else if (state == TelephonyManager.CALL_STATE_OFFHOOK)
                    {
                        mediaPlayer.setVolume(0,0);
                    }
                    else if (state == TelephonyManager.CALL_STATE_RINGING)
                    {
                        mediaPlayer.setVolume(0.2f, 0.2f);
                    }
                }
            };
            telephonyManager.listen(phoneStateListener,
                    PhoneStateListener.LISTEN_CALL_STATE);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.S)
    public class CallStateListener extends TelephonyCallback
            implements TelephonyCallback.CallStateListener
    {

        @Override
        public void onCallStateChanged(int i) {
            if(i == TelephonyManager.CALL_STATE_IDLE)
            {
                mediaPlayer.setVolume(1,1);
            }
            else if(i==TelephonyManager.CALL_STATE_OFFHOOK)
            {
                mediaPlayer.setVolume(0,0);
            }
            else if (i==TelephonyManager.CALL_STATE_RINGING)
            {
                mediaPlayer.setVolume(0.2f,0.2f);
            }
        }
    }
}