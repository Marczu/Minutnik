package com.marcinmejner.test2timers;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private SeekBar pasekCzasu;
    private TextView licznik;
    private Button przycisk;
    MediaPlayer mplayer;
    long milisekundy = 30000;
    boolean counterIsActive = false;
    TextView tekstUstawCzas;
    CountDownTimer countDownTimer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pasekCzasu = findViewById(R.id.pasekCzasu);
        licznik = findViewById(R.id.licznik);
        przycisk = findViewById(R.id.przycisk);
        tekstUstawCzas = findViewById(R.id.tekstUstawCzas);
        mplayer = MediaPlayer.create(this, R.raw.bell);

        pasekCzasu.setMax(600);
        pasekCzasu.setProgress(30);





        pasekCzasu.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                int minuty = i / 60;
                int sekundy = i - minuty * 60;
                if(sekundy <10){
                licznik.setText(minuty + ":0" + sekundy);
                }else{
                    licznik.setText(minuty + ":" + sekundy);

                }
                milisekundy = i * 1000;

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        przycisk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(counterIsActive == false){
                counterIsActive = true;
                pasekCzasu.setEnabled(false);
                tekstUstawCzas.setVisibility(View.INVISIBLE);

                startLicznika(milisekundy);

                przycisk.setText("Stop");
                }else{
                    licznik.setText("0:30");
                    pasekCzasu.setProgress(30);
                    countDownTimer.cancel();
                    przycisk.setText("START");
                    pasekCzasu.setEnabled(true);
                    tekstUstawCzas.setVisibility(View.VISIBLE);
                    counterIsActive = false;
                }
            }
        });
    }

    public void startLicznika(long odliczanie){
        countDownTimer = new CountDownTimer(odliczanie + 100, 1000) {

            public void onTick(long milisecondsUntilDone) {

                Log.d("sekundy", "sekund do konca:" + String.valueOf(milisecondsUntilDone / 1000));

                pasekCzasu.setProgress((int) milisecondsUntilDone/1000);
//                licznik.setText(String.valueOf(milisecondsUntilDone/1000));

            }

            @Override
            public void onFinish() {

                mplayer.start();
                pasekCzasu.setProgress(0);
                przycisk.setText("START");

                counterIsActive = false;
                pasekCzasu.setEnabled(true);
                tekstUstawCzas.setVisibility(View.VISIBLE);



                //Uruchomione gdy odliczanie zostanie zakonczone
            }
        }.start();
    }
}
