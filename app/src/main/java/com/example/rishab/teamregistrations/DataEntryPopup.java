package com.example.rishab.teamregistrations;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class DataEntryPopup extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_entry_popup);
        disableEnter();
        Intent intent=getIntent();
        EditText editext_hint=(EditText) findViewById(R.id.name);
        editext_hint.setHint(intent.getStringExtra("user_no"));
        editext_hint=(EditText) findViewById(R.id.ent_no);
        editext_hint.setHint(intent.getStringExtra("user_ent_no"));

        // Setting the size of the the enabled pop up
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width * 0.8), (int) (height * 0.63));

        // Parsing the entered user data
        final EditText editText1 = (EditText) findViewById(R.id.name);
        final EditText editText2 = (EditText) findViewById(R.id.ent_no);
        Button button = (Button) findViewById(R.id.Next_Button);
        // Setting on click listener to the button which parses the data and acts appropriately
        button.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                String Name = editText1.getText().toString();
                String Entry_Number = editText2.getText().toString();
                MyStringValidator validator = new MyStringValidator();   //object of this class
                String If_Error = validator.validate(Name, Entry_Number);  //invoking method to check correctness of the data format
//                String k = "";
                if (!(If_Error.matches(""))) {                                 // if the data is in incorrect format

                    // initialize an alert dialog with the appropriate message
                    AlertDialog alertDialog = new AlertDialog.Builder(
                            DataEntryPopup.this).create();

                    // Setting Dialog Title
                    alertDialog.setTitle("Data is Incorrect!");

                    // Setting Dialog Message
                    alertDialog.setMessage(If_Error);

                    // Setting Icon to Dialog
                    alertDialog.setIcon(R.drawable.wrong);

                    // Setting OK Button
                    alertDialog.setButton("Back", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Write your code here to execute after dialog closed
                            Toast.makeText(getApplicationContext(), "You clicked on Next", Toast.LENGTH_SHORT).show();
                        }
                    });

                    // Showing Alert Message
                    alertDialog.show();
                }
                else {
                    //The code below sends the data entered by the user back to the main activity
                    Intent send_back=new Intent();
                    send_back.putExtra("name",Name);
                    send_back.putExtra("ent_no",Entry_Number);
                    send_back.putExtra("back_triggered","0");
                    setResult(2, send_back);
                    finish();
                }

            }


        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_data_entry_popup, menu);
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
    @Override
    public boolean onKeyDown(int keycode, KeyEvent event){
        //checck if user pressed back button and went back without filling the data
        if (keycode == KeyEvent.KEYCODE_BACK){
            Intent intent = new Intent();
            intent.putExtra("back_triggered", "1");
            setResult(RESULT_OK, intent);
            finish();
            return true;
        }
        return true;
    }
    public void disableEnter(){
        //name and ent no dont contain enter, the function disables the same
        EditText editText = (EditText) findViewById(R.id.name);
        editText.setSingleLine(false);
        editText.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_EMAIL_SUBJECT);
        editText = (EditText) findViewById(R.id.ent_no);
        editText.setSingleLine(false);
        editText.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_EMAIL_SUBJECT);
    }
}
