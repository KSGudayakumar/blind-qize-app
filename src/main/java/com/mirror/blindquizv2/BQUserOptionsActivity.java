package com.mirror.blindquizv2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class BQUserOptionsActivity extends Activity implements OnClickListener {


    Button mbutton_setquestion;
    Button mbutton_setdetailedquestion;
    Button mbutton_examhome;
    Button mbutton_advancedexam;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_options);


        mbutton_setquestion = (Button) findViewById(R.id.button_setquestion);
        mbutton_setdetailedquestion = (Button) findViewById(R.id.button_setdetailedquestion);

        mbutton_examhome = (Button) findViewById(R.id.button_examhome);
        mbutton_advancedexam = (Button) findViewById(R.id.button_advancedexam);

        mbutton_examhome.setOnClickListener(this);
        mbutton_setquestion.setOnClickListener(this);
        mbutton_setdetailedquestion.setOnClickListener(this);
        mbutton_advancedexam.setOnClickListener(this);



    }

    public void onClick(View v) {
        // TODO Auto-generated method stub

        switch (v.getId()){

            case R.id.button_setquestion:

                Intent i = new Intent(this, BQSetQuestionActivity.class);
                startActivity(i);
                break;

            case R.id.button_examhome:

                Bundle extras1 = new Bundle();
                extras1.putString("type","normal");

                Intent ii = new Intent(this, BQExamHomeActivity.class);
                ii.putExtras(extras1);
                startActivity(ii);
                break;

            case R.id.button_setdetailedquestion:

                Intent jj = new Intent(this, BQDetailedQuestionActivity.class);
                startActivity(jj);
                break;

            case R.id.button_advancedexam:

                Bundle extras = new Bundle();
                extras.putString("type","advanced");
                Intent kk = new Intent(this, BQExamHomeActivity.class);
                kk.putExtras(extras);
                startActivity(kk);
                break;

        }




    }


}
