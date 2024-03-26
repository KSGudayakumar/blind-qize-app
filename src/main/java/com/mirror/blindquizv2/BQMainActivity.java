package com.mirror.blindquizv2;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class BQMainActivity extends AppCompatActivity {

    TextView mscore;

    String tot;
    String correct;

    TextToSpeech t1;
    String type;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Bundle extras = getIntent().getExtras();
        tot = (String) extras.getString("total");
        correct = (String) extras.getString("correct");
        type = (String) extras.getString("type");


        mscore = (TextView) findViewById(R.id.text_score);
        if(type.equalsIgnoreCase("advanced")){
            //do nothing
            mscore.setText("");

        }else{
            mscore.setText("You have answered " + correct + " out of " + tot + "questions");
        }




        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {

                    int result = t1.setLanguage(Locale.US);

                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "This Language is not supported");
                    } else {
                        //btnSpeak.setEnabled(true);
                       // speakOut();
                        if(type.equalsIgnoreCase("advanced")){
                            t1.speak("You have completed your exam successfully", TextToSpeech.QUEUE_FLUSH, null);
                        }else{
                            t1.speak("You have completed your exam successfully.\n You have answered " + correct + " out of " + tot + "questions", TextToSpeech.QUEUE_FLUSH, null);
                        }

                    }

                } else {
                    Log.e("TTS", "Initilization Failed!");
                }
            }

        });








    }


}
