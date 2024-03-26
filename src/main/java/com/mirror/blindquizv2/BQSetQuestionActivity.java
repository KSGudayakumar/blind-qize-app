package com.mirror.blindquizv2;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class BQSetQuestionActivity extends Activity implements OnClickListener {
    /**
     * Called when the activity is first created.
     */

    private EditText medit_subject;
    private EditText medit_qno;
    private EditText medit_question;
    private EditText medit_optiona;
    private EditText medit_optionb;

    private EditText medit_optionc;
    private EditText medit_optiond;
    private EditText medit_answer;

    private Button mbutton_submit;

    String subject;
    String qno;
    String question;
    String optiona;
    String optionb;
    String optionc;
    String optiond;
    String answer;

    ProgressDialog progressDialog;



    Dialog dialog;
    String ip;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setquestion);


        medit_subject = (EditText) findViewById(R.id.edit_subject);
        medit_qno = (EditText) findViewById(R.id.edit_qno);
        medit_question = (EditText) findViewById(R.id.edit_question);
        medit_optiona = (EditText) findViewById(R.id.edit_optiona);
        medit_optionb = (EditText) findViewById(R.id.edit_optionb);
        medit_optionc = (EditText) findViewById(R.id.edit_optionc);
        medit_optiond = (EditText) findViewById(R.id.edit_optiond);
        medit_answer = (EditText) findViewById(R.id.edit_answer);

        mbutton_submit = (Button) findViewById(R.id.button_submit);

        mbutton_submit.setOnClickListener(this);


    }

    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub

        setQuestion();
  /*      subject = medit_subject.getText().toString();
        qno = medit_qno.getText().toString();
        question = medit_question.getText().toString();
        optiona = medit_optiona.getText().toString();
        optionb = medit_optionb.getText().toString();
        optionc = medit_optionc.getText().toString();
        optiond = medit_optiond.getText().toString();
        answer = medit_answer.getText().toString();
        //   Bundle bundle = new Bundle();
        //   bundle.putString("user", user);
        //  bundle.putString("pass", pass);





        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);


        String url ="http://mirrortech.in/quiz/register.php?pass=arun" ;
        url = url + "&subject="+subject ;
        url = url + "&question="+question ;
        url = url + "&qno="+qno;
        url = url + "&optiona="+optiona ;
        url = url + "&optionb="+optionb ;
        url = url + "&optionc="+optionc ;
        url = url + "&optiond="+optiond ;
        url = url + "&answer="+answer ;
        url = url + "&type=add" ;


        System.out.println("URL : " + url);
        url = url.replaceAll(" ", "%20");

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        // mTextView.setText("Response is: "+ response.substring(0,500));

                        Toast.makeText(BQSetQuestionActivity.this,"Question added successfully. You can add new question", Toast.LENGTH_SHORT).show();
                        System.out.println("Response :" + response);
                        medit_subject.setText("");
                        medit_qno.setText("");
                        medit_question.setText("");
                        medit_optiona.setText("");
                        medit_optionb.setText("");
                        medit_optionc.setText("");
                        medit_optiond.setText("");
                        medit_answer.setText("");
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Error in getting response");
                error.printStackTrace();
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);



*/
    }

    public void setQuestion(){

        RequestQueue queue = Volley.newRequestQueue(this);
     /*   String mobile_no = mmobile.getText().toString();
        String email_address = memail_address.getText().toString();
        String firstname = mfirstname.getText().toString();
        String latitude = mlatitude.getText().toString();
        String longitude = mlongitude.getText().toString();
        String password = motp.getText().toString(); */

        subject = medit_subject.getText().toString();
        qno = medit_qno.getText().toString();
        question = medit_question.getText().toString();
        optiona = medit_optiona.getText().toString();
        optionb = medit_optionb.getText().toString();
        optionc = medit_optionc.getText().toString();
        optiond = medit_optiond.getText().toString();
        answer = medit_answer.getText().toString();

        // String lastname = mlastname.getText().toString();

        JSONObject req = new JSONObject();
        try {
            req.put("subject", subject);
            req.put("qno", qno);
            req.put("question", question);
            req.put("optiona", optiona);
            req.put("optionb", optionb);
            req.put("optionc", optionc);
            req.put("optiond", optiond);
            req.put("answer", answer);
            //  req.put("last_name", lastname);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        String url ="https://d83arun.pythonanywhere.com/addQuestion";

        System.out.println("URL :" + url);

        JsonObjectRequest jsonobj = new JsonObjectRequest(Request.Method.POST, url,req,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d("Chatbot","Success response");
                        System.out.println("Response: " + response);
                        progressDialog.dismiss();

                        try {
                            if((response.getString("response")).equals("success")){
                                // sent_otp = response.getString("otp");
                                // System.out.println("OTP: " + sent_otp);
                                Toast.makeText(getApplicationContext(),"Question added successfully",Toast.LENGTH_SHORT).show();
                                //Toast.makeText(getApplicationContext(),"Login with registered mobile no",Toast.LENGTH_SHORT).show();
                                //finish();
                                medit_subject.setText("");
                                medit_qno.setText("");
                                medit_question.setText("");
                                medit_optiona.setText("");
                                medit_optionb.setText("");
                                medit_optionc.setText("");
                                medit_optiond.setText("");
                                medit_answer.setText("");


                                // mregister.setVisibility(View.VISIBLE);
                                // mbuttonotp.setVisibility(View.INVISIBLE);
                                //((Activity)getApplicationContext()).finish();
                                //Intent i = new Intent(getApplicationContext(),RegistrationActivity.class);
                                //startActivity(i);

                            }else{
                                Toast.makeText(getApplicationContext(),"Error while Registering User",Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }



                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),"Error while connecting",Toast.LENGTH_SHORT).show();

                    }
                }
        ){
            //here I want to post data to sever
        };

        System.out.println("In 1");
        // Add the request to the RequestQueue.
        int socketTimeout = 10000;//2 minutes - change to what you want
        //RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonobj.setRetryPolicy(policy);
        queue.add(jsonobj);

        System.out.println("In 2");
        //initialize the progress dialog and show it
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Adding question");
        progressDialog.show();


    }

}


