package com.mirror.blindquizv2;

/**
 * Created by arunkumar on 4/4/18.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class BQExamHomeActivity extends Activity implements View.OnClickListener {


    Button mbutton_submit;
    EditText medit_subject;

    String tot;
    String correct;

    String type;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.examhome);

        Bundle extras = getIntent().getExtras();
        type = (String) extras.getString("type");


        mbutton_submit = (Button) findViewById(R.id.button_submit);
        medit_subject = (EditText) findViewById(R.id.edit_subject);


        mbutton_submit.setOnClickListener(this);



    }

    public void onClick(View v) {
        // TODO Auto-generated method stub

        switch (v.getId()){

            case R.id.button_submit:

                String subject = medit_subject.getText().toString();
                System.out.println("Subject Name : " + subject);

                Bundle extras = new Bundle();
                extras.putString("subject",subject);
                Intent i = null;
                if (type.equalsIgnoreCase("advanced")){
                    i = new Intent(this, BQDetailedExamActivity.class);
                }else{
                    i = new Intent(this, BQExamActivity.class);
                }

                i.putExtras(extras);
                startActivity(i);
                break;


        }




    }


}
