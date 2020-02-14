/*

 ***************  Code Designed by Team 107 Team Robotics *********************
 ***************  Edited for Team 1918 By Nate and Ken    *********************


 */

package com.example.ncgears.scouting;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ncgears.scouting.data.TeamsDbHelper;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import utils.FormatStringUtils;
import utils.StringUtils;
import utils.ViewUtils;

public class AutonActivity extends AppCompatActivity implements View.OnKeyListener {

    /*This area sets and binds all of the variables that we will use in the auton activity*/
    public static String AUTON_STRING_EXTRA = "auton_extra";

    /* These are the names of the match number and team number extras that will be passed into teleop */
    public static final String MATCH_STRING_EXTRA = "match_extra";
    public static final String TEAMNUMBER_STRING_EXTRA = "teamnumber_extra";


    @BindView(R.id.team_number_spinner)
    public Spinner TeamNumberInputLayout;

    @BindView(R.id.auto_high_attempt_input_layout)
    public TextInputLayout AutoHighAttemptInputLayout;

    @BindView(R.id.auto_high_attempt_input)
    public TextInputEditText AutoHighAttemptInput;

    @BindView(R.id.auto_high_made_layout)
    public TextInputLayout AutoHighMadeLayout;

    @BindView(R.id.auto_high_made_input)
    public TextInputEditText AutoHighMadeInput;

    @BindView(R.id.auto_low_attempt_input_layout)
    public TextInputLayout AutoLowAttemptInputLayout;

    @BindView(R.id.auto_low_attempt_input)
    public TextInputEditText AutoLowAttemptInput;

    @BindView(R.id.auto_low_made_layout)
    public TextInputLayout AutoLowMadeLayout;

    @BindView(R.id.auto_low_made_input)
    public TextInputEditText AutoLowMadeInput;

    @BindView(R.id.matchNumber_input_layout)
    public TextInputLayout matchNumberInputLayout;

    @BindView(R.id.matchNumber_input)
    public EditText matchNumberInput;

    @BindView(R.id.starting_location)
    public Spinner startingLocation;

    @BindView(R.id.auto_initiation_line)
    RadioGroup autoInitiationLine;

    @BindView(R.id.next_button)
    public Button nextButton;

    int HighAttempt = 0;
    int HighMissed =0;
    int HighMade = 0;
    int LowAttempt = 0;
    int LowMissed = 0;
    int LowMade = 0;

    public ArrayList<String> team_numbers = new ArrayList<>();
    private ArrayList<CharSequence> autonDataStringList;
    public static final int REQUEST_CODE = 1;


    /*When this activity is first called,
     *we will call the activity_auton layout so we can display
     *the user interface
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TeamsDbHelper mDbHelper = new TeamsDbHelper(this);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        setContentView(R.layout.activity_auton);
        ButterKnife.bind(this);
        autonDataStringList = new ArrayList<>();
        team_numbers = TeamsDbHelper.getTeamNumbers(db);

        checkForPermissions();



        displayAutoHighAttemptInput(HighMissed);
        displayAutoHighMadeInput(HighMade);
        displayAutoLowAttemptInput(LowMissed);
        displayAutoLowMadeInput(LowMade);

        //  --- Team Numbers spinner ---

        Spinner teamnumberspinner = (Spinner) findViewById(R.id.team_number_spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter teamnumberadapter = new ArrayAdapter<String>(AutonActivity.this,
                android.R.layout.simple_spinner_dropdown_item, team_numbers);
// Specify the layout to use when the list of choices appears
        teamnumberadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        teamnumberspinner.setAdapter(teamnumberadapter);


    }

    /*If this activity is resumed from a paused state the data
     *will be set to what they previously were set to
     */
    @Override
    protected void onResume() {
        super.onResume();

        autonDataStringList.clear();

        TeamNumberInputLayout.setOnKeyListener(this);
        AutoHighAttemptInputLayout.setOnKeyListener(this);
        AutoHighMadeLayout.setOnKeyListener(this);
        AutoLowAttemptInputLayout.setOnKeyListener(this);
        AutoLowMadeLayout.setOnKeyListener(this);
        matchNumberInput.setOnKeyListener(this);
        startingLocation.setOnKeyListener(this);


    }

    /*If this activity enters a paused state the data will be set to null*/
    @Override
    protected void onPause() {
        super.onPause();

        TeamNumberInputLayout.setOnKeyListener(null);
        AutoHighAttemptInputLayout.setOnKeyListener(null);
        AutoHighMadeLayout.setOnKeyListener(null);
        AutoLowAttemptInputLayout.setOnKeyListener(null);
        AutoLowMadeLayout.setOnKeyListener(null);
        matchNumberInput.setOnKeyListener(null);
        startingLocation.setOnKeyListener(null);

    }

    /* This method will display the options menu when the icon is pressed
     * and this will inflate the menu options for the user to choose
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    /*This method will launch the correct activity
     *based on the menu option user presses
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_activity:
                startActivity(new Intent(this, MainActivity.class));
                return true;
            case R.id.send_data:
                startActivity(new Intent(this, SendDataActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    /*This method will look at all of the text/number input fields and set error
     *for validation of data entry
     */
    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode != KeyEvent.KEYCODE_SPACE && keyCode != KeyEvent.KEYCODE_TAB) {
            TextInputEditText inputEditText = (TextInputEditText) v;

            if (inputEditText != null) {

                switch (inputEditText.getId()) {


                    case R.id.matchNumber_input:
                        matchNumberInputLayout.setError(null);
                        break;
                }
            }
        }
        return false;
    }


    /*This method takes place when the Show teleop button is pressed
     *This will first check if the text fields are empty and highlight
     * The area not completed as well as put that text input as the focus
     * to the user in the app. If everything passes by being filled in,
     * The value of the radio buttons will be obtained.
     * Then all of the values of this activity are added to the autonDataStringList
     * delimited by a comma. This method will then launch the teleop activity while sending
     * over our list of data. A request on result is requested so we can clear this aplication
     * after the teleop activity closes
     */
    public void onShowTeleop(View view) {
        boolean allInputsPassed = true;

        final RadioButton autoInitiationLineRadioBtn = findViewById(autoInitiationLine.getCheckedRadioButtonId());

        if (TeamNumberInputLayout.getSelectedItem().toString().equals("Select Team Number")) {
            setSpinnerError(TeamNumberInputLayout, "Select a Team Number.");
            ViewUtils.requestFocus(TeamNumberInputLayout, this);
        } else if (StringUtils.isEmptyOrNull(getTextInputLayoutString(matchNumberInputLayout)) || Integer.valueOf(getTextInputLayoutString(matchNumberInputLayout)) == 0 || Integer.valueOf(getTextInputLayoutString(matchNumberInputLayout)) >= 150) {
            matchNumberInput.setText("");
            matchNumberInputLayout.setError(getText(R.string.matchNumberError));
            ViewUtils.requestFocus(matchNumberInputLayout, this);
        } else {
            allInputsPassed = true;
        }

        if (!allInputsPassed) {
            return;
        }

        autonDataStringList.add(getTextInputLayoutString(AutoHighAttemptInputLayout));
        autonDataStringList.add(getTextInputLayoutString(AutoHighMadeLayout));
        autonDataStringList.add(getTextInputLayoutString(AutoLowAttemptInputLayout));
        autonDataStringList.add(getTextInputLayoutString(AutoLowMadeLayout));
        autonDataStringList.add(autoInitiationLineRadioBtn.getText().toString());
        autonDataStringList.add(TeamNumberInputLayout.getSelectedItem().toString());
        autonDataStringList.add(getTextInputLayoutString(matchNumberInputLayout));
        autonDataStringList.add(startingLocation.getSelectedItem().toString());


        //autonDataStringList.add(playStyle.getSelectedItem().toString());

        final Intent intent = new Intent(this, TeleopActivity.class);
        intent.putExtra(AUTON_STRING_EXTRA, FormatStringUtils.addDelimiter(autonDataStringList, "|"));
        intent.putExtra(MATCH_STRING_EXTRA, getTextInputLayoutString(matchNumberInputLayout));
        intent.putExtra(TEAMNUMBER_STRING_EXTRA, TeamNumberInputLayout.getSelectedItem().toString());

        startActivityForResult(intent, REQUEST_CODE);


        matchNumberInputLayout.setError(null);

        matchNumberInput.requestFocus();
        clearData();
    }


    /*This method will check for the result code from the teleop activity
     *so we can clear the data before the next match scouting starts
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
                clearData();
            }
        } catch (Exception ex) {
            Toast.makeText(this, ex.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    /*This method will clear all of the text entry fields as well
     * as reset the checkboxes and reset the radio buttons to their default*/
    public void clearData() {
        TeamNumberInputLayout.setSelection(0);
        matchNumberInput.setText("");
        startingLocation.setSelection(0);
        AutoHighAttemptInput.setText("" + HighMissed);
        AutoHighMadeInput.setText("" + HighMade);
        AutoLowAttemptInput.setText("" + LowMissed);
        AutoLowMadeInput.setText("" + LowMade);
        HighAttempt = 0;
        HighMissed =0;
        HighMade = 0;
        LowAttempt = 0;
        LowMissed = 0;
        LowMade = 0;
        autoInitiationLine.check(0);
        displayAutoHighAttemptInput(HighMissed);
        displayAutoHighMadeInput(HighMade);
        displayAutoLowAttemptInput(LowMissed);
        displayAutoLowMadeInput(LowMade);


    }


    private void setSpinnerError(Spinner spinner, String error) {
        View selectedView = spinner.getSelectedView();
        if (selectedView instanceof TextView) {
            spinner.requestFocus();
            TextView selectedTextView = (TextView) selectedView;
            selectedTextView.setError("error");
            selectedTextView.setTextColor(Color.RED);
            selectedTextView.setText(error);

        }
    }


    /* This method will change the text entered into the app into a string if it is not already*/
    private String getTextInputLayoutString(@NonNull TextInputLayout textInputLayout) {
        final EditText editText = textInputLayout.getEditText();
        return editText != null && editText.getText() != null ? editText.getText().toString() : "";
    }

    private void checkForPermissions() {
        int writePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (writePermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
    }

    public void decreaseAutoHighAttemptInput(View view) {
        if (HighMissed != 0) {
            HighMissed -= 1;
            HighAttempt -=1;

            displayAutoHighAttemptInput(HighMissed);

        }
    }

    public void increaseAutoHighAttemptInput(View view) {
        if (HighMissed <= 800) {
            HighMissed += 1;
            HighAttempt +=1;
            displayAutoHighAttemptInput(HighMissed);
        }

    }

    private void displayAutoHighAttemptInput(int number) {

        AutoHighAttemptInput.setText("" + number);
    }

    public void decreaseAutoHighMadeInput(View view) {
        if (HighMade != 0) {
            HighMade -= 1;
            HighAttempt -= 1;
            displayAutoHighMadeInput(HighMade);
        }
    }

    public void increaseAutoHightMadeInput(View view) {
        if (HighMade <= 800) {
            HighMade +=1;
            HighAttempt += 1;
            displayAutoHighMadeInput(HighMade);
        }

    }

    private void displayAutoHighMadeInput(int number) {

        AutoHighMadeInput.setText("" + number);
    }

    public void decreaseAutoLowAttemptInput(View view) {
        if (LowMissed != 0) {
            LowMissed -= 1;
            LowAttempt -=1;

            displayAutoLowAttemptInput(LowMissed);
        }
    }

    public void increaseAutoLowAttemptInput(View view) {
        if (LowMissed <= 800) {
            LowMissed += 1;
            LowAttempt += 1;
            displayAutoLowAttemptInput(LowMissed);
        }

    }

    private void displayAutoLowAttemptInput(int number) {

        AutoLowAttemptInput.setText("" + number);
    }

    public void decreaseAutoLowMadeInput(View view) {
        if (LowMade != 0) {
            LowMade -= 1;
            LowAttempt -=1;
            displayAutoLowMadeInput(LowMade);
        }
    }

    public void increaseAutoLowMadeInput(View view) {
        if (LowMade <= 800) {
            LowMade += 1;
            LowAttempt +=1;
            displayAutoLowMadeInput(LowMade);

        }

    }

    private void displayAutoLowMadeInput(int number) {

        AutoLowMadeInput.setText("" + number);
    }
}