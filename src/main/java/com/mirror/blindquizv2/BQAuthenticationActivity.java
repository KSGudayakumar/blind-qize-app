package com.mirror.blindquizv2;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class BQAuthenticationActivity extends Activity implements OnClickListener {
    /**
     * Called when the activity is first created.
     */

    private EditText medit_username;
    private EditText medit_password;
    private Button mbutton_submit;

    String user_name;
    String password;


    Dialog dialog;
    String ip;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authentication);


        medit_username = (EditText) findViewById(R.id.edit_user);
        medit_password = (EditText) findViewById(R.id.edit_password);
        mbutton_submit = (Button) findViewById(R.id.button_submit);

        mbutton_submit.setOnClickListener(this);


    }

    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub

        System.out.println("UserName :" + medit_username.getText().toString());
        System.out.println("Password :" + medit_password.getText().toString());
        user_name = medit_username.getText().toString();
        password = medit_password.getText().toString();

        //   Bundle bundle = new Bundle();
        //   bundle.putString("user", user);
        //  bundle.putString("pass", pass);

        if (user_name.equals("admin") && password.equals("admin")) {

            Intent l = new Intent(this, BQUserOptionsActivity.class);
            startActivity(l);




        } else {


            Toast.makeText(this, "Incorrect UserName or Password", Toast.LENGTH_SHORT).show();

            //Intent l = new Intent(this,ScanItemActivity.class);
            //startActivity(l);

                           }


    }


}


