package com.mirror.blindquizv2;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

public class BQDetailedExamActivity extends Activity implements OnClickListener {
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

    TextToSpeech t1;

    ProgressDialog progressDialog;

    boolean rec = false ;
    boolean flag_first = true;

    private final int REQ_CODE_SPEECH_INPUT = 100;


    private LinearLayout ml1;
    private LinearLayout ml2;
    private ScrollView ms1;

    private Button mbutton_submit;

    JSONArray res;

    String subject;
    String qno;
    String question;
    String optiona;
    String optionb;
    String optionc;
    String optiond;
    String answer;

    String s;

    int q = 0;
    int no_correct_answers = 0;



    Dialog dialog;
    String ip;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailedexam);

        ml1 = (LinearLayout) findViewById(R.id.linear1);
        ml2 = (LinearLayout) findViewById(R.id.linear2);

        ms1 = (ScrollView) findViewById(R.id.scroll1);

        ml1.setOnClickListener(this);
        ml2.setOnClickListener(this);
        ms1.setOnClickListener(this);

        Bundle extras = getIntent().getExtras();
        s = (String) extras.getString("subject");



        medit_subject = (EditText) findViewById(R.id.edit_subject);
        medit_qno = (EditText) findViewById(R.id.edit_qno);
        medit_question = (EditText) findViewById(R.id.edit_question);
        medit_optiona = (EditText) findViewById(R.id.edit_optiona);
       /* medit_optionb = (EditText) findViewById(R.id.edit_optionb);
        medit_optionc = (EditText) findViewById(R.id.edit_optionc);
        medit_optiond = (EditText) findViewById(R.id.edit_optiond); */

        medit_subject.setEnabled(false);
        medit_question.setEnabled(false);
        medit_qno.setEnabled(false);
        medit_question.setEnabled(false);
        medit_optiona.setEnabled(false);
     /*   medit_optionb.setEnabled(false);
        medit_optionc.setEnabled(false);
        medit_optiond.setEnabled(false); */

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
                        //speakOut();
                    }

                } else {
                    Log.e("TTS", "Initilization Failed!");
                }
            }

        });

        //retrieveQuestion();
        getQuestions();






    }

    public void speakQuestion(){

        try{
            System.out.println("In Speak Question");
            q++;
            boolean next_question = false;
            for (int i=0; i<res.length(); i++) {
                JSONObject obj = res.getJSONObject(i);

                 qno = obj.getString("qno");
                 subject = obj.getString("subject");
                 question = obj.getString("question");
                 /*optiona = obj.getString("optiona");
                 optionb = obj.getString("optionb");
                 optionc = obj.getString("optionc");
                 optiond = obj.getString("optiond");

                answer = obj.getString("answer"); */


                System.out.println("Subject : " + subject) ;
                System.out.println("Question : " + qno) ;
                System.out.println("Q : " + q) ;

                if (subject.equals(s) && Integer.parseInt(qno) == q){

                    next_question = true;
                    medit_subject.setText(subject);
                    medit_qno.setText(qno);
                    medit_question.setText(question);
                    medit_optiona.setText("");
                    /*medit_optionb.setText(optionb);
                    medit_optionc.setText(optionc);
                    medit_optiond.setText(optiond);*/
                    String text;
                   if(rec || flag_first){
//                    text    = "Question. " + question  + " \n Option A \n " + optiona  + " \n Option B \n " + optionb  + " \n Option C \n " + optionc +  "\n Option D \n " + optiond ;
                    text    = "Question. " + question ;
                       flag_first = false;
                   }
                    else {
                        text = "Answer not recognized. Repearting question \n + Question. " + question  + " \n Option A \n " + optiona  + " \n Option B \n " + optionb  + " \n Option C \n " + optionc +  "\n Option D \n " + optiond ;
                   }


                    t1.speak(text, TextToSpeech.QUEUE_FLUSH, null);


                }

            }

            if(!next_question){

                System.out.println("No more questions");
                System.out.println("No.of correct answers :" + no_correct_answers);
                q--;
                System.out.println("Total no of questions :" + q);

                Bundle extras = new Bundle();
                extras.putString("total",q+"");
                extras.putString("type","advanced");
                extras.putString("correct",no_correct_answers+"");

                Intent i = new Intent(this, BQMainActivity.class);
                i.putExtras(extras);
                startActivity(i);
            }

            }catch(Exception e) {
            e.printStackTrace();
        }



    }

    public void getQuestions(){

        RequestQueue queue = Volley.newRequestQueue(this);


        JSONObject req = new JSONObject();
        try {
            req.put("subject", s);

            //  req.put("last_name", lastname);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        String url ="https://d83arun.pythonanywhere.com/getDetailedQuestions";

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
                                Toast.makeText(getApplicationContext(),"Question retrieved successfully",Toast.LENGTH_SHORT).show();

                                String questions_res = response.getString("questions");
                                res = new JSONArray(questions_res);
                                System.out.println(res);
                                speakQuestion();
                                //Toast.makeText(getApplicationContext(),"Login with registered mobile no",Toast.LENGTH_SHORT).show();
                                //finish();
                            /*    medit_subject.setText("");
                                medit_qno.setText("");
                                medit_question.setText("");
                                medit_optiona.setText("");
                                medit_optionb.setText("");
                                medit_optionc.setText("");
                                medit_optiond.setText("");
                                medit_answer.setText(""); */


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

    public void retrieveQuestion(){


        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.INTERNET}, 1);



        RequestQueue queue = Volley.newRequestQueue(this);


        String url ="http://mirrortech.in/quiz/register.php?pass=arun&type=get&subject=test3" ;


        System.out.println("URL : " + url);
        url = url.replaceAll(" ", "%20");

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        // mTextView.setText("Response is: "+ response.substring(0,500));


                        Toast.makeText(BQDetailedExamActivity.this,"User Data received successfully", Toast.LENGTH_SHORT).show();
                        System.out.println("Response :" + response);
                        try{
                            res =  new JSONArray(response);
                         /*   for (int i=0; i<res.length(); i++) {
                                JSONObject obj = res.getJSONObject(i);

                                name = obj.getString("name");
                                email = obj.getString("email");
                                dob = obj.getString("dob");
                                gender = obj.getString("gender");


                                if (name.equals(user_name)){

                                    medit_name.setText(name);
                                    medit_email.setText(email);
                                    medit_dob.setText(dob);
                                    medit_gender.setText(gender);


                                }

                            } */

                            speakQuestion();
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }    }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Error in getting response");
                error.printStackTrace();
            }
        });

        queue.add(stringRequest);


    }


    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub

        System.out.println("OnClick");

        promptSpeechInput();
    }



    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }







     /*   subject = medit_subject.getText().toString();
        qno = medit_qno.getText().toString();
        question = medit_question.getText().toString();
        optiona = medit_optiona.getText().toString();
        optionb = medit_optionb.getText().toString();
        optionc = medit_optionc.getText().toString();
        optiond = medit_optiond.getText().toString();
        answer = medit_answer.getText().toString(); */
        //   Bundle bundle = new Bundle();
        //   bundle.putString("user", user);
        //  bundle.putString("pass", pass);











    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    System.out.println(result.get(0));
                    medit_optiona.setText(result.get(0));
                    rec= true;
                    speakQuestion();
                  /*  String ans = "";
                    if(result.get(0).equalsIgnoreCase("option A")){
                        ans = "a";
                        rec = true ;
                    }

                    if(result.get(0).equalsIgnoreCase("option B")){
                        ans = "b";
                        rec = true ;
                    }

                    if(result.get(0).equalsIgnoreCase("option C")){
                        ans = "c";
                        rec = true ;
                    }

                    if(result.get(0).equalsIgnoreCase("option D")){
                        ans = "d";
                        rec = true ;
                    } */

                 /*   if (rec) {
                        if(ans.equalsIgnoreCase(answer))
                            no_correct_answers++;
                            speakQuestion();
                    }
                    else{
                        q--;
                        t1.speak("Answer not recognised", TextToSpeech.QUEUE_FLUSH, null);
                        speakQuestion();
                    } */




                }
                break;
            }

        }
    }


}


