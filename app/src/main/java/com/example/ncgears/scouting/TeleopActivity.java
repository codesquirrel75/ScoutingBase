/*

***************  Code Designed by Team 107 Team Robotics *********************
***************  Edited for Team 1918 By Nate and Ken    *********************


 */

package com.example.ncgears.scouting;

import android.Manifest;
import android.graphics.Color;
import android.os.Environment;

import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.support.design.widget.TextInputLayout;
import android.content.Intent;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import utils.FormatStringUtils;
import utils.PermissionUtils;
import utils.StringUtils;
import utils.ViewUtils;

import static android.R.attr.value;
import static com.example.ncgears.scouting.AutonActivity.AUTON_STRING_EXTRA;
import static com.example.ncgears.scouting.AutonActivity.MATCH_STRING_EXTRA;
import static com.example.ncgears.scouting.AutonActivity.TEAMNUMBER_STRING_EXTRA;


public class TeleopActivity extends AppCompatActivity implements View.OnKeyListener {
    /*This area sets and binds all of the variables that we will use in the auton activity*/

    @BindView(R.id.teleop_high_attempt_input_layout)
    public TextInputLayout teleopHighAttemptInputLayout;

    @BindView(R.id.teleop_high_made_input_layout)
    public TextInputLayout teleopHighMadeInputLayout;

    @BindView(R.id.teleop_low_attempt_input_layout)
    public TextInputLayout teleopLowAttemptInputLayout;

    @BindView(R.id.teleop_low_made_input_layout)
    public TextInputLayout teleopLowMadeInputLayout;

    @BindView(R.id.teleop_high_attempts_input)
    public TextInputEditText teleopHighAttemptsInput;

    @BindView(R.id.teleop_high_made_input)
    public TextInputEditText teleopHighMadeInput;

    @BindView(R.id.teleop_low_attempt_input)
    public TextInputEditText teleopLowAttemptInput;

    @BindView(R.id.teleop_low_made_input)
    public TextInputEditText teleopLowMadeInput;

    @BindView(R.id.defense_rating_radio_group)
    public RadioGroup defenseRatingRadioGroup;

    @BindView(R.id.power_cell_pickup_radio_group)
    public RadioGroup powerCellPickupRadioGroup;

    @BindView(R.id.low_port_dump_radio_group)
    public RadioGroup lowPortDumpRadioGroup;

    @BindView(R.id.under_trench_radio_group)
    public RadioGroup underTrenchRadioGroup;

    @BindView(R.id.power_cell_placement_rating_radio_group)
    public RadioGroup powerCellPlacementRatingRadioGroup;

    @BindView(R.id.control_panel_rating_radio_group)
    public RadioGroup controlPanelRadingRadioGroup;

    @BindView(R.id.counter_defense_effectiveness)
    public RadioGroup counterDefenseEffectiveness;

    @BindView(R.id.end_game_location_spinner)
    public Spinner endGameLocationSpinner;

    @BindView(R.id.climb_time_radio_group)
    public RadioGroup climbTimeRadioGroup;

    //@BindView(R.id.cycle_time_spinner)
  //  public Spinner cycleTimeSpinner;

    @BindView(R.id.cycle_time_radio_group)
    public RadioGroup cycleTimeRadioGroup;

    @BindView(R.id.overall_effectiveness_radio_group)
    public RadioGroup overallEffectivenessRadioGoup;

    @BindView(R.id.trained_drive_team_radio_group)
    public RadioGroup trainedDriveTeamRadioGoup;

    @BindView(R.id.observe_Power_Cell_Pickup)
    public CheckBox observPowerCellPickup;

    @BindView(R.id.observ_died_back)
    public CheckBox observDiedBack;

    @BindView(R.id.observ_died_mid)
    public CheckBox observDiedMid;

    @BindView(R.id.observ_dns)
    public CheckBox observDns;

    @BindView(R.id.observ_fast)
    public CheckBox observFast;

    @BindView(R.id.observ_fell_apart)
    public CheckBox observFellApart;

    @BindView(R.id.observ_fell_over)
    public CheckBox observFellOver;

    @BindView(R.id.control_panel_issues)
    public CheckBox observPanelIssues;

    @BindView(R.id.observ_jerky)
    public CheckBox observJerky;

    @BindView(R.id.observ_not_much)
    public CheckBox observNotMuch;

    @BindView(R.id.observ_penalties)
    public CheckBox observPenalties;

    @BindView(R.id.observ_played_defense)
    public CheckBox observPlayedDefense;

    @BindView(R.id.observ_slow)
    public CheckBox observSlow;

    @BindView(R.id.observ_slowed_by_robot)
    public CheckBox observSlowedByRobot;

    @BindView(R.id.observ_smooth)
    public CheckBox observsmooth;

    @BindView(R.id.observ_average_speed)
    public CheckBox observAverageSpeed;

    @BindView(R.id.observ_power_cell_stuck_intake)
    public CheckBox observPowerCellStuckIntake;

    @BindView(R.id.observ_hung_up_power_cell)
    public CheckBox observHungUpPowerCell;

    @BindView(R.id.observ_fell_off_switch)
    public CheckBox observFellOffSwitch;

    @BindView(R.id.observ_hung_up)
    public  CheckBox observHungUp;

    @BindView(R.id.type_high_shooter)
    public  CheckBox typeHighShooter;

    @BindView(R.id.type_low_shooter)
    public  CheckBox typeLowShooter;

    @BindView(R.id.type_feeder_bot)
    public  CheckBox typeFeederBot;

    @BindView(R.id.type_control_panel)
    public  CheckBox typeControlPanel;

    @BindView(R.id.type_defense_bot)
    public  CheckBox typeDefenseBot;

    @BindView(R.id.type_counter_defense_bot)
    public  CheckBox typeCounterDefenseBot;

    @BindView(R.id.tele_shoot_target_zone)
    public  CheckBox teleShootTargetZone;

    @BindView(R.id.tele_shoot_field_front)
    public  CheckBox teleShootFieldFront;

    @BindView(R.id.tele_shoot_field_diagonal)
    public  CheckBox teleShootFieldDiagonal;

    @BindView(R.id.tele_shoot_trench_near)
    public  CheckBox teleShootTrenchNear;

    @BindView(R.id.tele_shoot_trench_far)
    public  CheckBox teleShootTrenchFar;

    @BindView(R.id.tele_shoot_other)
    public  CheckBox teleShootOther;


   // @BindView(R.id.type_of_bot_spinner)
   // public Spinner typeOfBotSpinner;

    @BindView(R.id.summary_input)
    public EditText summaryInput;

    @BindView(R.id.issues_input)
    public EditText issuesInput;

    public String observations = "";
    public String typeOfBot = "";
    public String shotFrom = "";

    @BindView(R.id.save_btn)
    public Button saveBtn;

    int teleopHighAttempt = 0;
    int teleopHighMissed = 0;
    int teleopHighMade = 0;
    int teleopLowAttempt = 0;
    int teleopLowMissed = 0;
    int teleopLowMade = 0;





    public String auton;
    public String matchNumber;
    public String teamNumber;

    private ArrayList<CharSequence> teleopDataStringList;
/*
 *When this activity is first called,
 *we will call the activity_auton layout so we can display
 *the user interface
 */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teleop);

        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        auton = bundle.getString(AUTON_STRING_EXTRA);
        matchNumber = bundle.getString(MATCH_STRING_EXTRA);
        teamNumber = bundle.getString(TEAMNUMBER_STRING_EXTRA);

        getSupportActionBar().setTitle("Match: " + matchNumber + " - Team: " + teamNumber);

        teleopDataStringList = new ArrayList<>();

        displayHighAttemptInput(teleopHighAttempt);
        displayHighMade(teleopHighMade);
        displayLowAttemptInput(teleopLowAttempt);
        displayLowMadeInput(teleopLowMade);


        //  --- End Game Location spinner ---

        Spinner endgamelocationspinner = (Spinner) findViewById(R.id.end_game_location_spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> endgamelocationadapter = ArrayAdapter.createFromResource(this,
                R.array.endgame_location, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        endgamelocationadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        endgamelocationspinner.setAdapter(endgamelocationadapter);


        //  --- Cycle Time spinner ---

/*        Spinner cycletimespinner = (Spinner) findViewById(R.id.cycle_time_spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> cycletimeadapter = ArrayAdapter.createFromResource(this,
                R.array.cycle_time_spinner, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        cycletimeadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        cycletimespinner.setAdapter(cycletimeadapter);
*/
        //    ---  Type of Bot Spinner  ---

/*        Spinner typeofbotspinner = (Spinner) findViewById(R.id.type_of_bot_spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> typeofbotadapter = ArrayAdapter.createFromResource(this,
                R.array.bot_type_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        typeofbotadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        typeofbotspinner.setAdapter(typeofbotadapter);
*/
    }


    /*If this activity is resumed from a paused state the data
     *will be set to what they previously were set to
     */
    @Override
    protected void onResume() {
        super.onResume();

        teleopHighAttemptsInput.setOnKeyListener(this);
        teleopHighMadeInput.setOnKeyListener(this);
        teleopLowAttemptInput.setOnKeyListener(this);
        teleopLowMadeInput.setOnKeyListener(this);
        controlPanelRadingRadioGroup.setOnKeyListener(this);
        counterDefenseEffectiveness.setOnKeyListener(this);
        endGameLocationSpinner.setOnKeyListener(this);
        climbTimeRadioGroup.setOnKeyListener(this);
        cycleTimeRadioGroup.setOnKeyListener(this);
        overallEffectivenessRadioGoup.setOnKeyListener(this);
        defenseRatingRadioGroup.setOnKeyListener(this);
        powerCellPlacementRatingRadioGroup.setOnKeyListener(this);
        trainedDriveTeamRadioGoup.setOnKeyListener(this);
        powerCellPickupRadioGroup.setOnKeyListener(this);
        lowPortDumpRadioGroup.setOnKeyListener(this);
        underTrenchRadioGroup.setOnKeyListener(this);
        observsmooth.setOnKeyListener(this);
        observSlowedByRobot.setOnKeyListener(this);
        observSlow.setOnKeyListener(this);
        observPlayedDefense.setOnKeyListener(this);
        observPenalties.setOnKeyListener(this);
        observNotMuch.setOnKeyListener(this);
        observJerky.setOnKeyListener(this);
        observPanelIssues.setOnKeyListener(this);
        observFellOver.setOnKeyListener(this);
        observFellApart.setOnKeyListener(this);
        observFast.setOnKeyListener(this);
        observDns.setOnKeyListener(this);
        observDiedMid.setOnKeyListener(this);
        observDiedBack.setOnKeyListener(this);
        observPowerCellPickup.setOnKeyListener(this);
        observAverageSpeed.setOnKeyListener(this);
        observPowerCellStuckIntake.setOnKeyListener(this);
        observHungUpPowerCell.setOnKeyListener(this);
        observFellOffSwitch.setOnKeyListener(this);
        observHungUp.setOnKeyListener(this);
        typeHighShooter.setOnKeyListener(this);
        typeLowShooter.setOnKeyListener(this);
        typeFeederBot.setOnKeyListener(this);
        typeControlPanel.setOnKeyListener(this);
        typeCounterDefenseBot.setOnKeyListener(this);
        typeDefenseBot.setOnKeyListener(this);
        teleShootTargetZone.setOnKeyListener(this);
        teleShootFieldFront.setOnKeyListener(this);
        teleShootFieldDiagonal.setOnKeyListener(this);
        teleShootTrenchNear.setOnKeyListener(this);
        teleShootTrenchFar.setOnKeyListener(this);
        teleShootOther.setOnKeyListener(this);
       // typeOfBotSpinner.setOnKeyListener(this);
        summaryInput.setOnKeyListener(this);
        issuesInput.setOnKeyListener(this);

    }

    /*If this activity enters a paused state the data will be set to null*/
    @Override
    protected void onPause() {
        super.onPause();

        teleopHighAttemptsInput.setOnKeyListener(null);
        teleopHighMadeInput.setOnKeyListener(null);
        teleopLowAttemptInput.setOnKeyListener(null);
        teleopLowMadeInput.setOnKeyListener(null);
        controlPanelRadingRadioGroup.setOnKeyListener(null);
        counterDefenseEffectiveness.setOnKeyListener(null);
        endGameLocationSpinner.setOnKeyListener(null);
        climbTimeRadioGroup.setOnKeyListener(null);
        cycleTimeRadioGroup.setOnKeyListener(null);
        overallEffectivenessRadioGoup.setOnKeyListener(null);
        defenseRatingRadioGroup.setOnKeyListener(null);
        powerCellPlacementRatingRadioGroup.setOnKeyListener(null);
        trainedDriveTeamRadioGoup.setOnKeyListener(null);
        powerCellPickupRadioGroup.setOnKeyListener(null);
        lowPortDumpRadioGroup.setOnKeyListener(null);
        underTrenchRadioGroup.setOnKeyListener(null);
        observsmooth.setOnKeyListener(null);
        observSlowedByRobot.setOnKeyListener(null);
        observSlow.setOnKeyListener(null);
        observPlayedDefense.setOnKeyListener(null);
        observPenalties.setOnKeyListener(null);
        observNotMuch.setOnKeyListener(null);
        observJerky.setOnKeyListener(null);
        observPanelIssues.setOnKeyListener(null);
        observFellOver.setOnKeyListener(null);
        observFellApart.setOnKeyListener(null);
        observFast.setOnKeyListener(null);
        observDns.setOnKeyListener(null);
        observDiedMid.setOnKeyListener(null);
        observDiedBack.setOnKeyListener(null);
        observPowerCellPickup.setOnKeyListener(null);
        observAverageSpeed.setOnKeyListener(null);
        observPowerCellStuckIntake.setOnKeyListener(null);
        observHungUpPowerCell.setOnKeyListener(null);
        observFellOffSwitch.setOnKeyListener(null);
        observHungUp.setOnKeyListener(null);
        typeHighShooter.setOnKeyListener(null);
        typeLowShooter.setOnKeyListener(null);
        typeFeederBot.setOnKeyListener(null);
        typeControlPanel.setOnKeyListener(null);
        typeCounterDefenseBot.setOnKeyListener(null);
        typeDefenseBot.setOnKeyListener(null);
        teleShootTargetZone.setOnKeyListener(null);
        teleShootFieldFront.setOnKeyListener(null);
        teleShootFieldDiagonal.setOnKeyListener(null);
        teleShootTrenchNear.setOnKeyListener(null);
        teleShootTrenchFar.setOnKeyListener(null);
        teleShootOther.setOnKeyListener(null);
        //typeOfBotSpinner.setOnKeyListener(null);
        summaryInput.setOnKeyListener(null);
        issuesInput.setOnKeyListener(null);

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

    private void setSpinnerError(Spinner spinner, String error){
        View selectedView = spinner.getSelectedView();
        if (selectedView instanceof TextView){
            spinner.requestFocus();
            TextView selectedTextView = (TextView) selectedView;
            selectedTextView.setError("error");
            selectedTextView.setTextColor(Color.RED);
            selectedTextView.setText(error);

        }
    }

    //Teleop high shots
    public void decreaseHighAttempts(View view) {
        if (teleopHighMissed != 0) {
            teleopHighMissed -= 1;
            teleopHighAttempt -=1;

            displayHighAttemptInput(teleopHighMissed);
        }
    }

    public void increaseHighAttempts(View view) {
        if (teleopHighMissed <= 800) {
            teleopHighMissed += 1;
            teleopHighAttempt += 1;
            displayHighAttemptInput(teleopHighMissed);
        }
    }

    private void displayHighAttemptInput(int number) {
        teleopHighAttemptsInput.setText("" + number);
    }

    //Teleop High Made
    public void decreaseHighMadeInput(View view) {
        if (teleopHighMade != 0) {
            teleopHighMade -= 1;
            teleopHighAttempt -= 1;
            displayHighMade(teleopHighMade);
        }
    }

    public void increaseHighMadeInput(View view) {
        if (teleopHighMade <= 800) {
            teleopHighMade += 1;
            teleopHighAttempt += 1;
            displayHighMade(teleopHighMade);

        }
    }

    private void displayHighMade(int number) {
        teleopHighMadeInput.setText("" + number);
    }

    //Teleop Low Missed

    public void decreaseLowAttempt(View view) {
        if (teleopLowMissed != 0) {
            teleopLowMissed -= 1;
            teleopLowAttempt -= 1;

            displayLowAttemptInput(teleopLowMissed);
        }
    }

    public void increaseLowAttempt(View view) {
        if (teleopLowMissed <= 100) {
            teleopLowMissed += 1;
            teleopLowAttempt += 1;
            displayLowAttemptInput(teleopLowMissed);
        }
    }

    private void displayLowAttemptInput(int number) {
        teleopLowAttemptInput.setText("" + number);
    }


    //Teleop Low Made

    public void decreaseLowMadeInput(View view) {
        if (teleopLowMade != 0) {
            teleopLowMade -= 1;
            teleopLowAttempt -=1;
            displayLowMadeInput(teleopLowMade);
        }
    }

    public void increaseLowMadeInput(View view) {
        if (teleopLowMade <= 800) {
            teleopLowMade += 1;
            teleopLowAttempt +=1;

            displayLowMadeInput(teleopLowMade);

        }
    }

    private void displayLowMadeInput(int number) {
        teleopLowMadeInput.setText("" + number);
    }


    // this method sets up a string for a group of checkBoxes

    public void setString(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        String s1;

        switch (view.getId()) {
            case R.id.observe_Power_Cell_Pickup:
                s1 = observPowerCellPickup.getText().toString() + " ,";
                if (checked) {
                    if (observations.isEmpty()) {
                        observations = s1;
                    } else {
                        observations = observations + s1;
                    }
                } else {
                    if (observations.contains(s1)) {
                        int start = observations.indexOf(s1);
                        observations = observations.substring(0, start) + observations.substring(start + s1.length());
                    }
                }
                break;
            case R.id.observ_died_back:
                s1 = observDiedBack.getText().toString() + " ,";
                if (checked) {
                    if (observations.isEmpty()) {
                        observations = s1;
                    } else {
                        observations = observations + s1;
                    }
                } else {
                    if (observations.contains(s1)) {
                        int start = observations.indexOf(s1);
                        observations = observations.substring(0, start) + observations.substring(start + s1.length());
                    }
                }
                break;
            case R.id.observ_died_mid:
                s1 = observDiedMid.getText().toString() + " ,";
                if (checked) {
                    if (observations.isEmpty()) {
                        observations = s1;
                    } else {
                        observations = observations + s1;
                    }
                } else {
                    if (observations.contains(s1)) {
                        int start = observations.indexOf(s1);
                        observations = observations.substring(0, start) + observations.substring(start + s1.length());
                    }
                }
                break;
            case R.id.observ_dns:
                s1 = observDns.getText().toString() + " ,";
                if (checked) {
                    if (observations.isEmpty()) {
                        observations = s1;
                    } else {
                        observations = observations + s1;
                    }
                } else {
                    if (observations.contains(s1)) {
                        int start = observations.indexOf(s1);
                        observations = observations.substring(0, start) + observations.substring(start + s1.length());
                    }
                }
                break;
            case R.id.observ_fast:
                s1 = observFast.getText().toString() + " ,";
                if (checked) {
                    if (observations.isEmpty()) {
                        observations = s1;
                    } else {
                        observations = observations + s1;
                    }
                } else {
                    if (observations.contains(s1)) {
                        int start = observations.indexOf(s1);
                        observations = observations.substring(0, start) + observations.substring(start + s1.length());
                    }
                }
                break;
            case R.id.observ_fell_apart:
                s1 = observFellApart.getText().toString() + " ,";
                if (checked) {
                    if (observations.isEmpty()) {
                        observations = s1;
                    } else {
                        observations = observations + s1;
                    }
                } else {
                    if (observations.contains(s1)) {
                        int start = observations.indexOf(s1);
                        observations = observations.substring(0, start) + observations.substring(start + s1.length());
                    }
                }
                break;
            case R.id.observ_fell_over:
                s1 = observFellOver.getText().toString() + " ,";
                if (checked) {
                    if (observations.isEmpty()) {
                        observations = s1;
                    } else {
                        observations = observations + s1;
                    }
                } else {
                    if (observations.contains(s1)) {
                        int start = observations.indexOf(s1);
                        observations = observations.substring(0, start) + observations.substring(start + s1.length());
                    }
                }
                break;
            case R.id.control_panel_issues:
                s1 = observPanelIssues.getText().toString() + " ,";
                if (checked) {
                    if (observations.isEmpty()) {
                        observations = s1;
                    } else {
                        observations = observations + s1;
                    }
                } else {
                    if (observations.contains(s1)) {
                        int start = observations.indexOf(s1);
                        observations = observations.substring(0, start) + observations.substring(start + s1.length());
                    }
                }
                break;
            case R.id.observ_jerky:
                s1 = observJerky.getText().toString() + " ,";
                if (checked) {
                    if (observations.isEmpty()) {
                        observations = s1;
                    } else {
                        observations = observations + s1;
                    }
                } else {
                    if (observations.contains(s1)) {
                        int start = observations.indexOf(s1);
                        observations = observations.substring(0, start) + observations.substring(start + s1.length());
                    }
                }
                break;
            case R.id.observ_not_much:
                s1 = observNotMuch.getText().toString() + " ,";
                if (checked) {
                    if (observations.isEmpty()) {
                        observations = s1;
                    } else {
                        observations = observations + s1;
                    }
                } else {
                    if (observations.contains(s1)) {
                        int start = observations.indexOf(s1);
                        observations = observations.substring(0, start) + observations.substring(start + s1.length());
                    }
                }
                break;
            case R.id.observ_penalties:
                s1 = observPenalties.getText().toString() + " ,";
                if (checked) {
                    if (observations.isEmpty()) {
                        observations = s1;
                    } else {
                        observations = observations + s1;
                    }
                } else {
                    if (observations.contains(s1)) {
                        int start = observations.indexOf(s1);
                        observations = observations.substring(0, start) + observations.substring(start + s1.length());
                    }
                }
                break;
            case R.id.observ_played_defense:
                s1 = observPlayedDefense.getText().toString() + " ,";
                if (checked) {
                    if (observations.isEmpty()) {
                        observations = s1;
                    } else {
                        observations = observations + s1;
                    }
                } else {
                    if (observations.contains(s1)) {
                        int start = observations.indexOf(s1);
                        observations = observations.substring(0, start) + observations.substring(start + s1.length());
                    }
                }
                break;
            case R.id.observ_slow:
                s1 = observSlow.getText().toString() + " ,";
                if (checked) {
                    if (observations.isEmpty()) {
                        observations = s1;
                    } else {
                        observations = observations + s1;
                    }
                } else {
                    if (observations.contains(s1)) {
                        int start = observations.indexOf(s1);
                        observations = observations.substring(0, start) + observations.substring(start + s1.length());
                    }
                }
                break;
            case R.id.observ_slowed_by_robot:
                s1 = observSlowedByRobot.getText().toString() + " ,";
                if (checked) {
                    if (observations.isEmpty()) {
                        observations = s1;
                    } else {
                        observations = observations + s1;
                    }
                } else {
                    if (observations.contains(s1)) {
                        int start = observations.indexOf(s1);
                        observations = observations.substring(0, start) + observations.substring(start + s1.length());
                    }
                }
                break;
            case R.id.observ_smooth:
                s1 = observsmooth.getText().toString() + " ,";
                if (checked) {
                    if (observations.isEmpty()) {
                        observations = s1;
                    } else {
                        observations = observations + s1;
                    }
                } else {
                    if (observations.contains(s1)) {
                        int start = observations.indexOf(s1);
                        observations = observations.substring(0, start) + observations.substring(start + s1.length());
                    }
                }
                break;
            case R.id.observ_average_speed:
                s1 = observAverageSpeed.getText().toString() + " ,";
                if (checked) {
                    if (observations.isEmpty()) {
                        observations = s1;
                    } else {
                        observations = observations + s1;
                    }
                } else {
                    if (observations.contains(s1)) {
                        int start = observations.indexOf(s1);
                        observations = observations.substring(0, start) + observations.substring(start + s1.length());
                    }
                }
                break;
            case R.id.observ_power_cell_stuck_intake:
                s1 = observPowerCellStuckIntake.getText().toString() + " ,";
                if (checked) {
                    if (observations.isEmpty()) {
                        observations = s1;
                    } else {
                        observations = observations + s1;
                    }
                } else {
                    if (observations.contains(s1)) {
                        int start = observations.indexOf(s1);
                        observations = observations.substring(0, start) + observations.substring(start + s1.length());
                    }
                }
                break;
            case R.id.observ_hung_up_power_cell:
                s1 = observHungUpPowerCell.getText().toString() + " ,";
                if (checked) {
                    if (observations.isEmpty()) {
                        observations = s1;
                    } else {
                        observations = observations + s1;
                    }
                } else {
                    if (observations.contains(s1)) {
                        int start = observations.indexOf(s1);
                        observations = observations.substring(0, start) + observations.substring(start + s1.length());
                    }
                }
                break;
            case R.id.observ_fell_off_switch:
                s1 = observFellOffSwitch.getText().toString() + " ,";
                if (checked) {
                    if (observations.isEmpty()) {
                        observations = s1;
                    } else {
                        observations = observations + s1;
                    }
                } else {
                    if (observations.contains(s1)) {
                        int start = observations.indexOf(s1);
                        observations = observations.substring(0, start) + observations.substring(start + s1.length());
                    }
                }
                break;
            case R.id.observ_hung_up:
                s1 = observHungUp.getText().toString() + " ,";
                if (checked) {
                    if (observations.isEmpty()) {
                        observations = s1;
                    } else {
                        observations = observations + s1;
                    }
                } else {
                    if (observations.contains(s1)) {
                        int start = observations.indexOf(s1);
                        observations = observations.substring(0, start) + observations.substring(start + s1.length());
                    }
                }
                break;
        }
    }

    /*public void setTypeOfBotString(View view) {   this is the original line of code before I copied and modified the following on 2/15/2020  ATB*/
    public void setStringTypeBot(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        String s1;

        switch (view.getId()) {
            case R.id.type_high_shooter:
                s1 = typeHighShooter.getText().toString() + " ,";
                if (checked) {
                    if (typeOfBot.isEmpty()) {
                        typeOfBot = s1;
                    } else {
                        typeOfBot = typeOfBot + s1;
                    }
                } else {
                    if (typeOfBot.contains(s1)) {
                        int start = typeOfBot.indexOf(s1);
                        typeOfBot = typeOfBot.substring(0, start) + typeOfBot.substring(start + s1.length());
                    }
                }
                break;
            case R.id.type_low_shooter:
                s1 = typeLowShooter.getText().toString() + " ,";
                if (checked) {
                    if (typeOfBot.isEmpty()) {
                        typeOfBot = s1;
                    } else {
                        typeOfBot = typeOfBot + s1;
                    }
                } else {
                    if (typeOfBot.contains(s1)) {
                        int start = typeOfBot.indexOf(s1);
                        typeOfBot = typeOfBot.substring(0, start) + typeOfBot.substring(start + s1.length());
                    }
                }
                break;
            case R.id.type_feeder_bot:
                s1 = typeFeederBot.getText().toString() + " ,";
                if (checked) {
                    if (typeOfBot.isEmpty()) {
                        typeOfBot = s1;
                    } else {
                        typeOfBot = typeOfBot + s1;
                    }
                } else {
                    if (typeOfBot.contains(s1)) {
                        int start = typeOfBot.indexOf(s1);
                        typeOfBot = typeOfBot.substring(0, start) + typeOfBot.substring(start + s1.length());
                    }
                }
                break;
            case R.id.type_control_panel:
                s1 = typeControlPanel.getText().toString() + " ,";
                if (checked) {
                    if (typeOfBot.isEmpty()) {
                        typeOfBot = s1;
                    } else {
                        typeOfBot = typeOfBot + s1;
                    }
                } else {
                    if (typeOfBot.contains(s1)) {
                        int start = typeOfBot.indexOf(s1);
                        typeOfBot = typeOfBot.substring(0, start) + typeOfBot.substring(start + s1.length());
                    }
                }
                break;
            case R.id.type_counter_defense_bot:
                s1 = typeCounterDefenseBot.getText().toString() + " ,";
                if (checked) {
                    if (typeOfBot.isEmpty()) {
                        typeOfBot = s1;
                    } else {
                        typeOfBot = typeOfBot + s1;
                    }
                } else {
                    if (typeOfBot.contains(s1)) {
                        int start = typeOfBot.indexOf(s1);
                        typeOfBot = typeOfBot.substring(0, start) + typeOfBot.substring(start + s1.length());
                    }
                }
                break;
            case R.id.type_defense_bot:
                s1 = typeDefenseBot.getText().toString() + " ,";
                if (checked) {
                    if (typeOfBot.isEmpty()) {
                        typeOfBot = s1;
                    } else {
                        typeOfBot = typeOfBot + s1;
                    }
                } else {
                    if (typeOfBot.contains(s1)) {
                        int start = typeOfBot.indexOf(s1);
                        typeOfBot = typeOfBot.substring(0, start) + typeOfBot.substring(start + s1.length());
                    }
                }
                break;
        }
    }

    public void setStringShot(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        String s1;

        switch (view.getId()) {
            case R.id.tele_shoot_target_zone:
                s1 = teleShootTargetZone.getText().toString() + " ,";
                if (checked) {
                    if (shotFrom.isEmpty()) {
                        shotFrom = s1;
                    } else {
                        shotFrom = shotFrom + s1;
                    }
                } else {
                    if (shotFrom.contains(s1)) {
                        int start = shotFrom.indexOf(s1);
                        shotFrom = shotFrom.substring(0, start) + shotFrom.substring(start + s1.length());
                    }
                }
                break;
            case R.id.tele_shoot_field_front:
                s1 = teleShootFieldFront.getText().toString() + " ,";
                if (checked) {
                    if (shotFrom.isEmpty()) {
                        shotFrom = s1;
                    } else {
                        shotFrom = shotFrom + s1;
                    }
                } else {
                    if (shotFrom.contains(s1)) {
                        int start = shotFrom.indexOf(s1);
                        shotFrom = shotFrom.substring(0, start) + shotFrom.substring(start + s1.length());
                    }
                }
                break;
            case R.id.tele_shoot_field_diagonal:
                s1 = teleShootFieldDiagonal.getText().toString() + " ,";
                if (checked) {
                    if (shotFrom.isEmpty()) {
                        shotFrom = s1;
                    } else {
                        shotFrom = shotFrom + s1;
                    }
                } else {
                    if (shotFrom.contains(s1)) {
                        int start = shotFrom.indexOf(s1);
                        shotFrom = shotFrom.substring(0, start) + shotFrom.substring(start + s1.length());
                    }
                }
                break;
            case R.id.tele_shoot_trench_near:
                s1 = teleShootTrenchNear.getText().toString() + " ,";
                if (checked) {
                    if (shotFrom.isEmpty()) {
                        shotFrom = s1;
                    } else {
                        shotFrom = shotFrom + s1;
                    }
                } else {
                    if (shotFrom.contains(s1)) {
                        int start = shotFrom.indexOf(s1);
                        shotFrom = shotFrom.substring(0, start) + shotFrom.substring(start + s1.length());
                    }
                }
                break;
            case R.id.tele_shoot_trench_far:
                s1 = teleShootTrenchFar.getText().toString() + " ,";
                if (checked) {
                    if (shotFrom.isEmpty()) {
                        shotFrom = s1;
                    } else {
                        shotFrom = shotFrom + s1;
                    }
                } else {
                    if (shotFrom.contains(s1)) {
                        int start = shotFrom.indexOf(s1);
                        shotFrom = shotFrom.substring(0, start) + shotFrom.substring(start + s1.length());
                    }
                }
                break;
            case R.id.tele_shoot_other:
                s1 = teleShootOther.getText().toString() + " ,";
                if (checked) {
                    if (shotFrom.isEmpty()) {
                        shotFrom = s1;
                    } else {
                        shotFrom = shotFrom + s1;
                    }
                } else {
                    if (shotFrom.contains(s1)) {
                        int start = shotFrom.indexOf(s1);
                        shotFrom = shotFrom.substring(0, start) + shotFrom.substring(start + s1.length());
                    }
                }
                break;
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

                    case R.id.teleop_high_attempts_input:
                        teleopHighAttemptInputLayout.setError(null);
                        break;

                    case R.id.teleop_high_made_input:
                        teleopHighMadeInputLayout.setError(null);
                        break;

                    case R.id.teleop_low_attempt_input:
                        teleopLowAttemptInputLayout.setError(null);
                        break;

                    case R.id.teleop_low_made_input:
                        teleopLowMadeInputLayout.setError(null);
                        break;

                }
            }
        }
        return false;
    }

    /*
    * This method will verify that all fields are filled and highlight error to user
    * along with change focus to first blank input area. The radio button values are obtained
    * A file is created on the dvice to send the data to. We add the teleop data to the arraylist
    * delimited by commas. We create our message by concatenating the teleop data to the end of
    * the auton data. The data is then output to the file we created. We send a message to the user
    * about the saved message. We send a result back to the auton activity upon completion.
    * We then clear the data of the teleop activity and finish it to close and return
    * to the auton activty to clear its data*/

    public void saveData(View view) throws IOException {
        String state = Environment.getExternalStorageState();
        boolean allInputsPassed = false;

        if (StringUtils.isEmptyOrNull(getTextInputLayoutString(teleopHighAttemptInputLayout))) {
            teleopHighAttemptInputLayout.setError(getText(R.string.teleopCargoShipHatchPanelError));
            ViewUtils.requestFocus(teleopHighAttemptInputLayout, this);
        } else if (endGameLocationSpinner.getSelectedItem().toString().isEmpty()){
            setSpinnerError(endGameLocationSpinner, "Please select an end game location.");
        } else if (StringUtils.isEmptyOrNull(getTextInputLayoutString(teleopHighMadeInputLayout))) {
            teleopHighMadeInputLayout.setError(getText(R.string.teleopCargoInCargoShipError));
            ViewUtils.requestFocus(teleopHighMadeInputLayout, this);
        } else if (StringUtils.isEmptyOrNull(getTextInputLayoutString(teleopLowAttemptInputLayout))) {
            teleopLowAttemptInputLayout.setError(getText(R.string.hatchPanelTopError));
            ViewUtils.requestFocus(teleopLowAttemptInputLayout, this);
        } else if (StringUtils.isEmptyOrNull(getTextInputLayoutString(teleopLowMadeInputLayout))) {
            teleopLowMadeInputLayout.setError(getText(R.string.cargoTopError));
            ViewUtils.requestFocus(teleopLowMadeInputLayout, this);
        } /*else if(typeOfBotSpinner.getSelectedItem().toString( ).equals("")){

            setSpinnerError(typeOfBotSpinner,"Select bot type");
            ViewUtils.requestFocus(typeOfBotSpinner, this);

        }*/ else {
            allInputsPassed = true;
        }
        if (!allInputsPassed) {
            return;
        }

        final RadioButton controlPanelRadingRadioBtn = findViewById(controlPanelRadingRadioGroup.getCheckedRadioButtonId());
        final RadioButton counterDefenseEffectivenessRadiobtn = findViewById(counterDefenseEffectiveness.getCheckedRadioButtonId());
        final RadioButton overallEffectivenessRadiobtn = findViewById(overallEffectivenessRadioGoup.getCheckedRadioButtonId());
        final RadioButton trainedDriveTeamRadiobtn = findViewById(trainedDriveTeamRadioGoup.getCheckedRadioButtonId());
        final RadioButton powerCellPickUpRadioBtn = findViewById(powerCellPickupRadioGroup.getCheckedRadioButtonId());
        final RadioButton lowPortDumpRadioBtn = findViewById(lowPortDumpRadioGroup.getCheckedRadioButtonId());
        final RadioButton underTrenchRadioBtn = findViewById(underTrenchRadioGroup.getCheckedRadioButtonId());
        final RadioButton defenseRatingRadioBtn = findViewById(defenseRatingRadioGroup.getCheckedRadioButtonId());
        final RadioButton powerCellPlacementRatingRadioBtn = findViewById(powerCellPlacementRatingRadioGroup.getCheckedRadioButtonId());
        final RadioButton climbTimeRadiobtn = findViewById(climbTimeRadioGroup.getCheckedRadioButtonId());
        final RadioButton cycleTimeRadiobtn = findViewById(cycleTimeRadioGroup.getCheckedRadioButtonId());



        if(PermissionUtils.getPermissions(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            if (Environment.MEDIA_MOUNTED.equals(state)) {
                File dir = new File(Environment.getExternalStorageDirectory() + "/Scouting");
                dir.mkdirs();

                File file = new File(dir, "Match" + Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID) + ".csv");

                teleopDataStringList.add(getTextInputLayoutString(teleopHighAttemptInputLayout));
                teleopDataStringList.add(getTextInputLayoutString(teleopHighMadeInputLayout));
                teleopDataStringList.add(getTextInputLayoutString(teleopLowAttemptInputLayout));
                teleopDataStringList.add(getTextInputLayoutString(teleopLowMadeInputLayout));
                teleopDataStringList.add(cycleTimeRadiobtn.getText().toString());
                teleopDataStringList.add(endGameLocationSpinner.getSelectedItem().toString());
                teleopDataStringList.add(climbTimeRadiobtn.getText().toString());
                teleopDataStringList.add(typeOfBot);
                teleopDataStringList.add(shotFrom);
                teleopDataStringList.add(powerCellPickUpRadioBtn.getText().toString());
                teleopDataStringList.add(lowPortDumpRadioBtn.getText().toString());
                teleopDataStringList.add(underTrenchRadioBtn.getText().toString());
                teleopDataStringList.add(defenseRatingRadioBtn.getText().toString());
                teleopDataStringList.add(counterDefenseEffectivenessRadiobtn.getText().toString());
                teleopDataStringList.add(powerCellPlacementRatingRadioBtn.getText().toString());
                teleopDataStringList.add(controlPanelRadingRadioBtn.getText().toString());

               // teleopDataStringList.add(cycleTimeSpinner.getSelectedItem().toString());
                teleopDataStringList.add(overallEffectivenessRadiobtn.getText().toString());
                teleopDataStringList.add(trainedDriveTeamRadiobtn.getText().toString());

                teleopDataStringList.add(observations);

                //teleopDataStringList.add(typeOfBotSpinner.getSelectedItem().toString());
                teleopDataStringList.add(summaryInput.getText().toString());
                teleopDataStringList.add(issuesInput.getText().toString());

                teleopDataStringList.add(ScouterInitialsActivity.getInitials());

                String message = auton + "|" + FormatStringUtils.addDelimiter(teleopDataStringList, "|") + "\n";

                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(file, true);
                    fileOutputStream.write(message.getBytes());
                    fileOutputStream.close();

                    Toast.makeText(getApplicationContext(), "Saved!", Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    Toast.makeText(getApplicationContext(), "IOException! Go talk to the programmers!", Toast.LENGTH_LONG).show();
                    Log.d("Scouting", e.getMessage());
                }
            } else {
                Toast.makeText(getApplicationContext(), "SD card not found", Toast.LENGTH_LONG).show();
            }

            Intent intent = getIntent();
            intent.putExtra("Key", value);
            setResult(RESULT_OK, intent);

            clearData(view);

            finish();
        }

        teleopHighAttemptInputLayout.setError(null);
        teleopHighMadeInput.setError(null);
        teleopLowAttemptInputLayout.setError(null);
        teleopLowMadeInputLayout.setError(null);


    }

    /*The method will clear all the data in the text fields, checkboxes, and
    * set radio buttons to default*/
    public void clearData(View view) {
        teleopHighAttemptsInput.setText("" + teleopHighMissed);
        teleopHighMadeInput.setText("" + teleopHighMade);
        teleopLowAttemptInput.setText("" + teleopLowMissed);
        teleopLowMadeInput.setText("" + teleopLowMade);

        controlPanelRadingRadioGroup.clearCheck();
        counterDefenseEffectiveness.clearCheck();
        endGameLocationSpinner.setSelection(0);
        climbTimeRadioGroup.clearCheck();
        //cycleTimeSpinner.setSelection(0);
        cycleTimeRadioGroup.clearCheck();
        defenseRatingRadioGroup.clearCheck();
        powerCellPlacementRatingRadioGroup.clearCheck();
        overallEffectivenessRadioGoup.clearCheck();
        trainedDriveTeamRadioGoup.clearCheck();
        powerCellPickupRadioGroup.clearCheck();
        lowPortDumpRadioGroup.clearCheck();
        underTrenchRadioGroup.clearCheck();
        observsmooth.setChecked(false);
        observSlowedByRobot.setChecked(false);
        observSlow.setChecked(false);
        observPlayedDefense.setChecked(false);
        observPenalties.setChecked(false);
        observNotMuch.setChecked(false);
        observJerky.setChecked(false);
        observPanelIssues.setChecked(false);
        observFellOver.setChecked(false);
        observFellApart.setChecked(false);
        observFast.setChecked(false);
        observDns.setChecked(false);
        observDiedMid.setChecked(false);
        observDiedBack.setChecked(false);
        observPowerCellPickup.setChecked(false);
        observAverageSpeed.setChecked(false);
        observPowerCellStuckIntake.setChecked(false);
        observHungUpPowerCell.setChecked(false);
        observFellOffSwitch.setChecked(false);
        observHungUp.setChecked(false);
        typeLowShooter.setChecked(false);
        typeFeederBot.setChecked(false);
        typeControlPanel.setChecked(false);
        typeCounterDefenseBot.setChecked(false);
        typeDefenseBot.setChecked(false);
        teleShootTargetZone.setChecked(false);
        teleShootFieldFront.setChecked(false);
        teleShootFieldDiagonal.setChecked(false);
        teleShootTrenchNear.setChecked(false);
        teleShootTrenchFar.setChecked(false);
        teleShootOther.setChecked(false);
       // typeOfBotSpinner.setSelection(0);
        summaryInput.setText(null);
        issuesInput.setText(null);
        teleopHighAttempt = 0;
        teleopHighMissed =0;
        teleopHighMade = 0;
        teleopLowAttempt = 0;
        teleopLowMissed = 0;
        teleopLowMade = 0;

    }

    /* This method will change the text entered into the app into a string if it is not already*/
    private String getTextInputLayoutString(@NonNull TextInputLayout textInputLayout) {
        final EditText editText = textInputLayout.getEditText();
        return editText != null && editText.getText() != null ? editText.getText().toString() : "";
    }
}
