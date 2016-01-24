package com.example.rishab.teamregistrations;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;


import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
//this activity is the main activity where the team will enter data

public class MainActivity extends ActionBarActivity {

    //Array to store the team info
    String[] team_details = new String[6];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //initialize the team detals arrary
        for (int i = 0; i < team_details.length; i++) {
            team_details[i] = "";
        }
        EditText editText = (EditText) findViewById(R.id.team_name);  //
        editText.setSingleLine(false);
        editText.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_EMAIL_SUBJECT);
        //Setting the onclick listener to the image button signifying the user and calling
        //start activity for result to call an Activity that returns the data entered by the
        //user
        ImageButton imageButton1 = (ImageButton) findViewById(R.id.member_1);
        imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent first_user_intent = new Intent(MainActivity.this, DataEntryPopup.class);   //Creating an intent for a new activity
                first_user_intent.putExtra("user_no", "Name of First Student");
                first_user_intent.putExtra("user_ent_no", "Entry Number of First Student");
                startActivityForResult(first_user_intent, 1);   //request code = 1 for 1st user
            }
        });
        ImageButton imageButton2 = (ImageButton) findViewById(R.id.member_2);
        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent second_user_intent = new Intent(MainActivity.this, DataEntryPopup.class);
                second_user_intent.putExtra("user_no", "Name of Second Student");
                second_user_intent.putExtra("user_ent_no", "Entry Number of Second Student");
                startActivityForResult(second_user_intent, 2);   //request code = 2 for 2nd user
            }
        });
        ImageButton imageButton3 = (ImageButton) findViewById(R.id.member_3);
        imageButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent third_user_intent = new Intent(MainActivity.this, DataEntryPopup.class);
                third_user_intent.putExtra("user_no", "Name of Third Student");
                third_user_intent.putExtra("user_ent_no", "Entry Number of Third Student");
                startActivityForResult(third_user_intent, 3);   //request code = 3 for 3rd user
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    //send function sends the team data to the preview  activity so that the users can confirm
    //their details
    public void send(View view) {
        EditText editText = (EditText) findViewById(R.id.team_name);
        String team = editText.getText().toString();
        int no_data = 0;  //checks if the result activity ended because of back click or exit
        for (int i = 0; i < team_details.length; i++) {
            if (team_details[i].length() == 0) {
                no_data = 1;
                break;
            }
        }
        //check if the user has filled all the field
        if (no_data == 0) {
            Intent preview_intent = new Intent(this, PreviewActivity.class);
            preview_intent.putExtra("team_name", team);
            preview_intent.putExtra("user1", team_details[0]);
            preview_intent.putExtra("ent_no1", team_details[1]);
            preview_intent.putExtra("user2", team_details[2]);
            preview_intent.putExtra("ent_no2", team_details[3]);
            preview_intent.putExtra("user3", team_details[4]);
            preview_intent.putExtra("ent_no3", team_details[5]);
            startActivity(preview_intent);
        } else {
            //alerting the user to fill all the details
            AlertDialog alertDialog = new AlertDialog.Builder(
                    MainActivity.this).create();

            // Setting Dialog Title
            alertDialog.setTitle("Data Not Filled");

            // Setting Dialog Message
            alertDialog.setMessage("Please add complete information");

            // Setting Icon to Dialog
            alertDialog.setIcon(R.drawable.wrong);

            // Setting OK Button
            alertDialog.setButton("Back", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // Write your code here to execute after dialog closed
                    Toast.makeText(getApplicationContext(), "You clicked on BACK", Toast.LENGTH_SHORT).show();
                }
            });

            // Showing Alert Message
            alertDialog.show();
        }

    }

    @Override
    public void onActivityResult(int reqcode, int rescode, Intent data) {
        //got the results back
        //map for reqcode(request code) to user - mentioned above while starting result activity
        super.onActivityResult(reqcode, rescode, data);
        if (data.getStringExtra("back_triggered").matches("0")) {
            if (reqcode == 1) {
                //update the details of 1st user/member
                team_details[0] = data.getStringExtra("name");
                team_details[1] = data.getStringExtra("ent_no");
                TextView textView = (TextView) findViewById(R.id.user1);
                textView.setText(team_details[0]);
                textView = (TextView) findViewById(R.id.ent_no1);
                textView.setText(team_details[1]);
            } else if (reqcode == 2) {
                //update the details for 2nd user/member
                team_details[2] = data.getStringExtra("name");
                team_details[3] = data.getStringExtra("ent_no");
                TextView textView = (TextView) findViewById(R.id.user2);
                textView.setText(team_details[2]);
                textView = (TextView) findViewById(R.id.ent_no2);
                textView.setText(team_details[3]);
            } else {
                //update the details for 3rd user/member
                team_details[4] = data.getStringExtra("name");
                team_details[5] = data.getStringExtra("ent_no");
                TextView textView = (TextView) findViewById(R.id.user3);
                textView.setText(team_details[4]);
                textView = (TextView) findViewById(R.id.ent_no3);
                textView.setText(team_details[5]);
            }
        }
    }

    //the following function resets all the fields
    public void clearScreen(View view) {
        TextView textView = (TextView) findViewById(R.id.user1);
        textView.setText("Menber - 1");
        textView = (TextView) findViewById(R.id.ent_no1);
        textView.setText("Entry No. - 1");
        textView = (TextView) findViewById(R.id.user2);
        textView.setText("Menber - 2");
        textView = (TextView) findViewById(R.id.ent_no2);
        textView.setText("Entry No. - 2");
        textView = (TextView) findViewById(R.id.user3);
        textView.setText("Menber - 3");
        textView = (TextView) findViewById(R.id.ent_no3);
        textView.setText("Entry No. - 3");
        EditText editText = (EditText) findViewById(R.id.team_name);
        editText.setText("");
        editText.setHint("Enter Team Name");
        for (int index = 0; index < team_details.length; index++) {
            team_details[index] = "";
        }
    }

    public void focus(View v) {
        EditText editText = (EditText) findViewById(R.id.team_name);
        editText.setEnabled(true);
        editText.requestFocus();
    }
}


