package com.example.rishab.teamregistrations;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class PreviewActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        overridePendingTransition(R.anim.move,0);
        setContentView(R.layout.activity_preview);
        //getting the intent
        Intent register_data_intent = getIntent();
        //using the data sent from the previous intent to fill appropriate
        String name=register_data_intent.getStringExtra("user1");
        String ent_no = register_data_intent.getStringExtra("ent_no1");
        TextView view = (TextView) findViewById(R.id.user1);
        view.setText(name);
        view = (TextView) findViewById(R.id.ent_no1);
        view.setText(ent_no);
        name=register_data_intent.getStringExtra("user2");
        ent_no=register_data_intent.getStringExtra("ent_no2");
        view = (TextView) findViewById(R.id.user2);
        view.setText(name);
        view=(TextView)findViewById(R.id.ent_no2);
        view.setText(ent_no);
        name=register_data_intent.getStringExtra("user3");
        ent_no=register_data_intent.getStringExtra("ent_no3");
        view = (TextView) findViewById(R.id.user3);
        view.setText(name);
        view=(TextView)findViewById(R.id.ent_no3);
        view.setText(ent_no);
        name=register_data_intent.getStringExtra("team_name");
        TextView team = (TextView)findViewById(R.id.team_name);
        team.setText(name);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_preview, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void send_confirmation(View view){
        //url to register team details to
        String URL="http://agni.iitd.ernet.in/cop290/assign0/register/";
        //setting the variable to get text from the text field
        TextView text=(TextView) findViewById(R.id.user1);
        Log.e("enter confirmation", "true");
        //following variables i.e. text,text1 etc are the required fields to be sent to the server
        final String text1= text.getText().toString();
        text=(TextView) findViewById(R.id.ent_no1);
        final String text2= text.getText().toString();
        text=(TextView) findViewById(R.id.user2);
        final String text3= text.getText().toString();
        text=(TextView) findViewById(R.id.ent_no2);
        final String text4= text.getText().toString();
        text=(TextView) findViewById(R.id.user3);
        final String text5= text.getText().toString();
        text=(TextView) findViewById(R.id.ent_no3);
        final String text6= text.getText().toString();
        text=(TextView) findViewById(R.id.team_name);
        final String team=text.getText().toString();
        System.out.print(text1 + " " + text2 + " " + text3 + " " + text4 + " " + text5 + " " + text6 + " ");

        //making a string request to register the user data
        StringRequest stringRequest=new StringRequest(com.android.volley.Request.Method.POST,URL, new Response.Listener<String>(){
            @Override
            public void onResponse(String string){
                //on getting the response start an activity that shows the message recieved
                // from the server given
                AlertDialog alertDialog = new AlertDialog.Builder(
                        PreviewActivity.this).create();

                // Setting Dialog Title
                alertDialog.setTitle("Response");

                // Setting Dialog Message
                alertDialog.setMessage(handle_response(string));


                // Setting OK Button
                alertDialog.setButton("Register Another Team", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog closed
                        Intent intent = new Intent(PreviewActivity.this,MainActivity.class);
                        startActivity(intent);

                    }
                });

                // Showing Alert Message
                alertDialog.show();
//                Intent confirmation_intent = new Intent(PreviewActivity.this, ConfirmationActivity.class);
//                confirmation_intent.putExtra("response",handle_response(string));
//                startActivity(confirmation_intent);

            }
        },new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError string){
                System.out.print(string);

            };
        }){
            @Override
            protected Map<String,String> getParams(){
                //this function converts the the entered data into the format to be sent to
                // the server

                Map<String,String> req=new HashMap<String, String>();
                req.put("teamname" , team);
                req.put("entry1" , text2);
                req.put("entry2" , text4);
                req.put("entry3" , text6);
                req.put("name1",text1);
                req.put("name2",text3);
                req.put("name3",text5);
                return req;
            }};
        //the following is the global request queue to prevent construction of
        // request queue again and again
        SingletonNetworkClass.getInstance(this).addToRequestQueue(stringRequest);
//        queue.add(stringRequest);


    }
    //the following function finishes the activity and takes back to edit the entered details
    public void edit(View view){
        this.finish();
    }
    public String handle_response(String responseText)
    {

        try {
            JSONObject response = new JSONObject(responseText);
            String message = response.getString("RESPONSE_MESSAGE");
            //String response_success = response.getString("RESPONSE_SUCCESS");

            if(message.matches("Data not posted!")) return "The Required Fields are Missing.\nRegistration is Unsuccessful!";
            else if(message.matches("Registration completed"))  return "Registration is Successful\nGood luck for the course!";
            else if(message.matches("User already registered")) return "One or more users with given details have already registered!\nRegistration is Unsuccessful";

        }
        catch (JSONException e){
            String a="";
        }
        return "Unknown Response";
    }

}
