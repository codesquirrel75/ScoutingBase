/*

 ***************  Code Designed by Team 107 Team Robotics *********************
 ***************  Edited for Team 1918 By Nate and Ken    *********************


 */

package com.example.ncgears.scouting;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ncgears.scouting.data.TeamsDbHelper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import utils.FormatStringUtils;
import utils.PermissionUtils;
import utils.StringUtils;
import utils.ViewUtils;

/**
 * Created by Matt from Team 107 on 9/30/2017.
 * Borrowed by Ken from Team 1918 on 1/7/2019.
 */

public class PitActivity extends AppCompatActivity implements View.OnKeyListener {

    @BindView(R.id.pit_team_number_spinner)
    public Spinner pitTeamNumberSpinner;

    @BindView(R.id.pit_robot_weight)
    public EditText pitRobotWeight;

    @BindView(R.id.pit_drive_train_spinner)
    public Spinner pitDriveTrainSpinner;

    @BindView(R.id.drive_train_other)
    public EditText driveTrainOther;

    @BindView(R.id.pit_programming_language_spinner)
    public Spinner pitProgrammingLanguages;

    @BindView(R.id.programming_language_other)
    public EditText programmingLanguageOther;

    @BindView(R.id.pit_basic_plan)
    public EditText pitBasicPlan;

    @BindView(R.id.pit_auto_programs)
    public EditText pitAutoPrograms;

    @BindView(R.id.pit_shoot_target_zone)
    public CheckBox pitShootTargetZone;

    @BindView(R.id.pit_shoot_field_front)
    public CheckBox pitShootFieldFront;

    @BindView(R.id.pit_shoot_field_diagonal)
    public CheckBox pitShootFieldDiagonal;

    @BindView(R.id.pit_shoot_trench_near)
    public CheckBox pitShootTrenchNear;

    @BindView(R.id.pit_shoot_trench_far)
    public CheckBox pitShootTrenchFar;

    @BindView(R.id.pit_shoot_other)
    public CheckBox pitShootOther;

    public String shotString = "";

    @BindView(R.id.pit_fav_target_zone)
    public CheckBox pitFavTargetZone;

    @BindView(R.id.pit_fav_field_front)
    public CheckBox pitFavFieldFront;

    @BindView(R.id.pit_fav_field_diagonal)
    public CheckBox pitFavFieldDiagonal;

    @BindView(R.id.pit_fav_trench_near)
    public CheckBox pitFavTrenchNear;

    @BindView(R.id.pit_fav_trench_far)
    public CheckBox pitFavTrenchFar;

    @BindView(R.id.pit_fav_other)
    public CheckBox pitFavOther;

    public String favShotString = "";

    @BindView(R.id.pit_shot_location_inner)
    public CheckBox pitShotLocationInner;

    @BindView(R.id.pit_shot_location_outer)
    public CheckBox pitShotLocationOuter;

    @BindView(R.id.pit_shot_location_bottom)
    public CheckBox pitShotLocationBottom;

    @BindView(R.id.pit_shot_location_na)
    public CheckBox pitShotLocationNa;

    public String pitShotLocationString = "";

    @BindView(R.id.pit_control_panel_rotate_3to5)
    public CheckBox pitControlPanelRotate3to5;

    @BindView(R.id.pit_control_panel_position)
    public CheckBox pitControlPanelPosition;

    @BindView(R.id.pit_control_panel_nothing)
    public CheckBox pitControlPanelNothing;

    public String ControlPanelString = "";


    @BindView(R.id.pit_drive_thru_trench)
    public RadioGroup pitDriveThruTrench;

    @BindView(R.id.pit_endgame_hang)
    public RadioGroup pitEndgameHang;

    @BindView(R.id.scouterInitials_input)
    public EditText scouterInitialsInput;


    @BindView(R.id.take_photo_btn)
    public Button takePhotoBtn;

    @BindView(R.id.save_pit_btn)
    public Button savePitBtn;

    public  ArrayList<String> team_numbers = new ArrayList<>();
    private ArrayList<CharSequence> pitDataStringList;









    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TeamsDbHelper mDbHelper = new TeamsDbHelper(this);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        setContentView(R.layout.activity_pit);
        pitDataStringList = new ArrayList<>();

        team_numbers = TeamsDbHelper.getTeamNumbers(db);

        ButterKnife.bind(this);

        checkForPermissions();

        pitDriveTrainSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(pitDriveTrainSpinner.getSelectedItem().toString().equals("Other")){
                    driveTrainOther.setVisibility(view.VISIBLE);
                }
                else{
                    driveTrainOther.setVisibility(view.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        pitProgrammingLanguages.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(pitProgrammingLanguages.getSelectedItem().toString().equals("Other")){
                    programmingLanguageOther.setVisibility(view.VISIBLE);
                }
                else{
                    programmingLanguageOther.setVisibility(view.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        //  --- Drive Train spinner ---

       // Spinner drivetrainspinner = (Spinner) findViewById(R.id.pit_drive_train_spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> drivetrainadapter = ArrayAdapter.createFromResource(this,
                R.array.driveTrain, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        drivetrainadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        pitDriveTrainSpinner.setAdapter(drivetrainadapter);


        //  --- Team Numbers spinner ---

      //  Spinner teamnumberspinner = (Spinner) findViewById(R.id.pit_team_number_spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter teamnumberadapter = new ArrayAdapter<>(PitActivity.this,
                android.R.layout.simple_spinner_dropdown_item, team_numbers);
// Specify the layout to use when the list of choices appears
        teamnumberadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        pitTeamNumberSpinner.setAdapter(teamnumberadapter);


        //  --- Programming languages spinner  ---

       // Spinner languagespinner = (Spinner) findViewById(R.id.pit_programming_language_spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> languageadapter = ArrayAdapter.createFromResource(this,
                R.array.programmingLanguages, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        languageadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        pitProgrammingLanguages.setAdapter(languageadapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

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

    public void setStringShot(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        String s1;

        switch (view.getId()) {

            case R.id.pit_shoot_trench_near:
                s1 = pitShootTrenchNear.getText().toString() + " ,";
                if (checked) {
                    if (shotString.isEmpty()) {
                        shotString = s1;
                    } else {
                        shotString = shotString + s1;
                    }
                } else {
                    if (shotString.contains(s1)) {
                        int start = shotString.indexOf(s1);
                        shotString = shotString.substring(0, start) + shotString.substring(start + s1.length());
                    }
                }
                break;

            case R.id.pit_shoot_trench_far:
                s1 = pitShootTrenchFar.getText().toString() + " ,";
                if (checked) {
                    if (shotString.isEmpty()) {
                        shotString = s1;
                    } else {
                        shotString = shotString + s1;
                    }
                } else {
                    if (shotString.contains(s1)) {
                        int start = shotString.indexOf(s1);
                        shotString = shotString.substring(0, start) + shotString.substring(start + s1.length());
                    }
                }
                break;

            case R.id.pit_shoot_other:
                s1 = pitShootOther.getText().toString() + " ,";
                if (checked) {
                    if (shotString.isEmpty()) {
                        shotString = s1;
                    } else {
                        shotString = shotString + s1;
                    }
                } else {
                    if (shotString.contains(s1)) {
                        int start = shotString.indexOf(s1);
                        shotString = shotString.substring(0, start) + shotString.substring(start + s1.length());
                    }
                }
                break;

            case R.id.pit_shoot_target_zone:
                s1 = pitShootTargetZone.getText().toString() + " ,";
                if (checked) {
                    if (shotString.isEmpty()) {
                        shotString = s1;
                    } else {
                        shotString = shotString + s1;
                    }
                } else {
                    if (shotString.contains(s1)) {
                        int start = shotString.indexOf(s1);
                        shotString = shotString.substring(0, start) + shotString.substring(start + s1.length());
                    }
                }
                break;

            case R.id.pit_shoot_field_diagonal:
                s1 = pitShootFieldDiagonal.getText().toString() + " ,";
                if (checked) {
                    if (shotString.isEmpty()) {
                        shotString = s1;
                    } else {
                        shotString = shotString + s1;
                    }
                } else {
                    if (shotString.contains(s1)) {
                        int start = shotString.indexOf(s1);
                        shotString = shotString.substring(0, start) + shotString.substring(start + s1.length());
                    }
                }
                break;

            case R.id.pit_shoot_field_front:
                s1 = pitShootFieldFront.getText().toString() + " ,";
                if (checked) {
                    if (shotString.isEmpty()) {
                        shotString = s1;
                    } else {
                        shotString = shotString + s1;
                    }
                } else {
                    if (shotString.contains(s1)) {
                        int start = shotString.indexOf(s1);
                        shotString = shotString.substring(0, start) + shotString.substring(start + s1.length());
                    }
                }
                break;
        }
    }

    public void setStringFavShot(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        String s1;

        switch (view.getId()) {

            case R.id.pit_fav_trench_near:
                s1 = pitFavTrenchNear.getText().toString() + " ,";
                if (checked) {
                    if (favShotString.isEmpty()) {
                        favShotString = s1;
                    } else {
                        favShotString = favShotString + s1;
                    }
                } else {
                    if (favShotString.contains(s1)) {
                        int start = favShotString.indexOf(s1);
                        favShotString = favShotString.substring(0, start) + favShotString.substring(start + s1.length());
                    }
                }
                break;

            case R.id.pit_fav_trench_far:
                s1 = pitFavTrenchFar.getText().toString() + " ,";
                if (checked) {
                    if (favShotString.isEmpty()) {
                        favShotString = s1;
                    } else {
                        favShotString = favShotString + s1;
                    }
                } else {
                    if (favShotString.contains(s1)) {
                        int start = favShotString.indexOf(s1);
                        favShotString = favShotString.substring(0, start) + favShotString.substring(start + s1.length());
                    }
                }
                break;

            case R.id.pit_fav_other:
                s1 = pitFavOther.getText().toString() + " ,";
                if (checked) {
                    if (favShotString.isEmpty()) {
                        favShotString = s1;
                    } else {
                        favShotString = favShotString + s1;
                    }
                } else {
                    if (favShotString.contains(s1)) {
                        int start = favShotString.indexOf(s1);
                        favShotString = favShotString.substring(0, start) + favShotString.substring(start + s1.length());
                    }
                }
                break;

            case R.id.pit_fav_target_zone:
                s1 = pitFavTargetZone.getText().toString() + " ,";
                if (checked) {
                    if (favShotString.isEmpty()) {
                        favShotString = s1;
                    } else {
                        favShotString = favShotString + s1;
                    }
                } else {
                    if (favShotString.contains(s1)) {
                        int start = favShotString.indexOf(s1);
                        favShotString = favShotString.substring(0, start) + favShotString.substring(start + s1.length());
                    }
                }
                break;

            case R.id.pit_fav_field_diagonal:
                s1 = pitFavFieldDiagonal.getText().toString() + " ,";
                if (checked) {
                    if (favShotString.isEmpty()) {
                        favShotString = s1;
                    } else {
                        favShotString = favShotString + s1;
                    }
                } else {
                    if (favShotString.contains(s1)) {
                        int start = favShotString.indexOf(s1);
                        favShotString = favShotString.substring(0, start) + favShotString.substring(start + s1.length());
                    }
                }
                break;

            case R.id.pit_fav_field_front:
                s1 = pitFavFieldFront.getText().toString() + " ,";
                if (checked) {
                    if (favShotString.isEmpty()) {
                        favShotString = s1;
                    } else {
                        favShotString = favShotString + s1;
                    }
                } else {
                    if (favShotString.contains(s1)) {
                        int start = favShotString.indexOf(s1);
                        favShotString = favShotString.substring(0, start) + favShotString.substring(start + s1.length());
                    }
                }
                break;
        }
    }

    public void setPowerPortShotString(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        String s1;

        switch (view.getId()) {

            case R.id.pit_shoot_trench_near:
                s1 = pitShootTrenchNear.getText().toString() + " ,";
                if (checked) {
                    if (shotString.isEmpty()) {
                        shotString = s1;
                    } else {
                        shotString = shotString + s1;
                    }
                } else {
                    if (shotString.contains(s1)) {
                        int start = shotString.indexOf(s1);
                        shotString = shotString.substring(0, start) + shotString.substring(start + s1.length());
                    }
                }
                break;

            case R.id.pit_shoot_trench_far:
                s1 = pitShootTrenchFar.getText().toString() + " ,";
                if (checked) {
                    if (shotString.isEmpty()) {
                        shotString = s1;
                    } else {
                        shotString = shotString + s1;
                    }
                } else {
                    if (shotString.contains(s1)) {
                        int start = shotString.indexOf(s1);
                        shotString = shotString.substring(0, start) + shotString.substring(start + s1.length());
                    }
                }
                break;

            case R.id.pit_shoot_other:
                s1 = pitShootOther.getText().toString() + " ,";
                if (checked) {
                    if (shotString.isEmpty()) {
                        shotString = s1;
                    } else {
                        shotString = shotString + s1;
                    }
                } else {
                    if (shotString.contains(s1)) {
                        int start = shotString.indexOf(s1);
                        shotString = shotString.substring(0, start) + shotString.substring(start + s1.length());
                    }
                }
                break;

            case R.id.pit_shoot_target_zone:
                s1 = pitShootTargetZone.getText().toString() + " ,";
                if (checked) {
                    if (shotString.isEmpty()) {
                        shotString = s1;
                    } else {
                        shotString = shotString + s1;
                    }
                } else {
                    if (shotString.contains(s1)) {
                        int start = shotString.indexOf(s1);
                        shotString = shotString.substring(0, start) + shotString.substring(start + s1.length());
                    }
                }
                break;
        }
    }

            public void setControlPanelString(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        String s1;

        switch (view.getId()) {

            case R.id.pit_control_panel_rotate_3to5:
                s1 = pitControlPanelRotate3to5.getText().toString() + " ,";
                if (checked) {
                    if (ControlPanelString.isEmpty()) {
                        ControlPanelString = s1;
                    } else {
                        ControlPanelString = ControlPanelString + s1;
                    }
                } else {
                    if (ControlPanelString.contains(s1)) {
                        int start = ControlPanelString.indexOf(s1);
                        ControlPanelString = ControlPanelString.substring(0, start) + ControlPanelString.substring(start + s1.length());
                    }
                }
                break;

            case R.id.pit_control_panel_position:
                s1 = pitControlPanelPosition.getText().toString() + " ,";
                if (checked) {
                    if (ControlPanelString.isEmpty()) {
                        ControlPanelString = s1;
                    } else {
                        ControlPanelString = ControlPanelString + s1;
                    }
                } else {
                    if (ControlPanelString.contains(s1)) {
                        int start = ControlPanelString.indexOf(s1);
                        ControlPanelString = ControlPanelString.substring(0, start) + ControlPanelString.substring(start + s1.length());
                    }
                }
                break;

            case R.id.pit_control_panel_nothing:
                s1 = pitControlPanelNothing.getText().toString() + " ,";
                if (checked) {
                    if (ControlPanelString.isEmpty()) {
                        ControlPanelString = s1;
                    } else {
                        ControlPanelString = ControlPanelString + s1;
                    }
                } else {
                    if (ControlPanelString.contains(s1)) {
                        int start = ControlPanelString.indexOf(s1);
                        ControlPanelString = ControlPanelString.substring(0, start) + ControlPanelString.substring(start + s1.length());
                    }
                }
                break;
        }
    }

    public void setPitShotLocationString(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        String s1;

        switch (view.getId()) {

            case R.id.pit_shot_location_bottom:
                s1 = pitShotLocationBottom.getText().toString() + " ,";
                if (checked) {
                    if (pitShotLocationString.isEmpty()) {
                        pitShotLocationString = s1;
                    } else {
                        pitShotLocationString = pitShotLocationString + s1;
                    }
                } else {
                    if (pitShotLocationString.contains(s1)) {
                        int start = pitShotLocationString.indexOf(s1);
                        pitShotLocationString = pitShotLocationString.substring(0, start) + pitShotLocationString.substring(start + s1.length());
                    }
                }
                break;

            case R.id.pit_shot_location_inner:
                s1 = pitShotLocationInner.getText().toString() + " ,";
                if (checked) {
                    if (pitShotLocationString.isEmpty()) {
                        pitShotLocationString = s1;
                    } else {
                        pitShotLocationString = pitShotLocationString + s1;
                    }
                } else {
                    if (pitShotLocationString.contains(s1)) {
                        int start = pitShotLocationString.indexOf(s1);
                        pitShotLocationString = pitShotLocationString.substring(0, start) + pitShotLocationString.substring(start + s1.length());
                    }
                }
                break;

            case R.id.pit_shot_location_outer:
                s1 = pitShotLocationOuter.getText().toString() + " ,";
                if (checked) {
                    if (pitShotLocationString.isEmpty()) {
                        pitShotLocationString = s1;
                    } else {
                        pitShotLocationString = pitShotLocationString + s1;
                    }
                } else {
                    if (pitShotLocationString.contains(s1)) {
                        int start = pitShotLocationString.indexOf(s1);
                        pitShotLocationString = pitShotLocationString.substring(0, start) + pitShotLocationString.substring(start + s1.length());
                    }
                }
                break;

            case R.id.pit_shot_location_na:
                s1 = pitShotLocationNa.getText().toString() + " ,";
                if (checked) {
                    if (pitShotLocationString.isEmpty()) {
                        pitShotLocationString = s1;
                    } else {
                        pitShotLocationString = pitShotLocationString + s1;
                    }
                } else {
                    if (pitShotLocationString.contains(s1)) {
                        int start = pitShotLocationString.indexOf(s1);
                        pitShotLocationString = pitShotLocationString.substring(0, start) + pitShotLocationString.substring(start + s1.length());
                    }
                }
                break;
        }
    }



    @Override
    protected void onResume() {
        super.onResume();

        pitTeamNumberSpinner.setOnKeyListener(this);
        pitDriveTrainSpinner.setOnKeyListener(this);
        driveTrainOther.setOnKeyListener(this);
        pitRobotWeight.setOnKeyListener(this);
        pitProgrammingLanguages.setOnKeyListener(this);
        programmingLanguageOther.setOnKeyListener(this);
        pitAutoPrograms.setOnKeyListener(this);
        pitDriveThruTrench.setOnKeyListener(this);
        pitEndgameHang.setOnKeyListener(this);
        pitBasicPlan.setOnKeyListener(this);
    }


    @Override
    protected void onPause() {
        super.onPause();

        pitTeamNumberSpinner.setOnKeyListener(null);
        pitDriveTrainSpinner.setOnKeyListener(null);
        driveTrainOther.setOnKeyListener(null);
        pitRobotWeight.setOnKeyListener(null);
        pitProgrammingLanguages.setOnKeyListener(null);
        programmingLanguageOther.setOnKeyListener(null);
        pitAutoPrograms.setOnKeyListener(null);
        pitDriveThruTrench.setOnKeyListener(null);
        pitEndgameHang.setOnKeyListener(null);
        pitBasicPlan.setOnKeyListener(null);
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {


        return false;
    }

    public void savePitData(View view) throws IOException {
        String state = Environment.getExternalStorageState();
        boolean allInputsPassed = false;

        //  ******  Check Required fields set focus to field if it hasn't been filled out  ******


        if (pitTeamNumberSpinner.getSelectedItem().toString().equals("Select Team Number") ) {
           setSpinnerError(pitTeamNumberSpinner, "Select a Team Number.");
           ViewUtils.requestFocus(pitTeamNumberSpinner, this);
        }else if (pitProgrammingLanguages.getSelectedItem().toString().equals("") ) {
            setSpinnerError(pitProgrammingLanguages, "Select a Programming Language.");
            ViewUtils.requestFocus(pitProgrammingLanguages, this);
        }else if(pitDriveTrainSpinner.getSelectedItem().toString().equals("")){
            setSpinnerError(pitDriveTrainSpinner, "Select a drive train.");
            ViewUtils.requestFocus(pitDriveTrainSpinner, this);
        } else if (StringUtils.isEmptyOrNull(pitRobotWeight.getText().toString())) {
            pitRobotWeight.setError(getText(R.string.pitRobotWeightError));
            ViewUtils.requestFocus(pitRobotWeight, this);
        } else if (StringUtils.isEmptyOrNull(scouterInitialsInput.getText().toString())) {
            scouterInitialsInput.setError(getText(R.string.scouterInitialsError));
            ViewUtils.requestFocus(scouterInitialsInput, this);
        } else {
            allInputsPassed = true;
        }
        if (!allInputsPassed) {
            return;
        }


      final RadioButton pitDriveThruTrenchbtn = findViewById(pitDriveThruTrench.getCheckedRadioButtonId());
      final RadioButton pitEndgameHangbtn = findViewById(pitEndgameHang.getCheckedRadioButtonId());

        if(PermissionUtils.getPermissions(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            if (Environment.MEDIA_MOUNTED.equals(state)) {
                File dir = new File(Environment.getExternalStorageDirectory() + "/Scouting");
                //create csv file
                File file = new File(dir, "Pit" + Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID) + ".csv");

                pitDataStringList.add(pitTeamNumberSpinner.getSelectedItem().toString());
                pitDataStringList.add(pitRobotWeight.getText().toString());
                pitDataStringList.add(pitDriveTrainSpinner.getSelectedItem().toString());
                pitDataStringList.add(driveTrainOther.getText().toString());
                pitDataStringList.add(pitProgrammingLanguages.getSelectedItem().toString());
                pitDataStringList.add(programmingLanguageOther.getText().toString());
                pitDataStringList.add(pitAutoPrograms.getText().toString());
                pitDataStringList.add(shotString);
                pitDataStringList.add(favShotString);
                pitDataStringList.add(pitDriveThruTrenchbtn.getText().toString());
                pitDataStringList.add(pitShotLocationString);
                pitDataStringList.add(ControlPanelString);
                pitDataStringList.add(pitEndgameHangbtn.getText().toString());
                pitDataStringList.add(pitBasicPlan.getText().toString());
                pitDataStringList.add(scouterInitialsInput.getText().toString());



                String message = FormatStringUtils.addDelimiter(pitDataStringList, "|") + "\n";


                //Output data to file
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

            clearData();
            pitTeamNumberSpinner.requestFocus();
        }




    }

    public void takePhoto(View view) {
        String name = pitTeamNumberSpinner.getSelectedItem().toString();

        if(PermissionUtils.getPermissions(this, Manifest.permission.CAMERA) &&
                PermissionUtils.getPermissions(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) &&
                PermissionUtils.getPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            if (!StringUtils.isEmptyOrNull(name)) {
                File dir = new File(Environment.getExternalStorageDirectory() + "/Scouting/Photos");
                dir.mkdirs();

                File file = new File(dir, name + ".jpg");

                try {
                    file.createNewFile();
                } catch (IOException e) {
                    Log.d("Scouting", e.getMessage());
                }

                Uri outputUri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", file);

                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
                    startActivityForResult(takePictureIntent, 0);
                }
            } else {
                //setSpinnerError(pitTeamNumberInputLayout, "Select a Team Number.");
                ViewUtils.requestFocus(pitTeamNumberSpinner, this);
            }
        } else {
            checkForPermissions();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if(resultCode == RESULT_OK) {
                compressPhoto();
            }
        }
    }

    private void compressPhoto() {
        try {
            String name = pitTeamNumberSpinner.getSelectedItem().toString();

            File dir = new File(Environment.getExternalStorageDirectory() + "/Scouting/Photos");
            File file = new File(dir, name + ".jpg");

            FileInputStream inputStream = new FileInputStream(file);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 25, out);

            FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write(out.toByteArray());
            inputStream.close();
            out.close();
            outputStream.close();

            Toast.makeText(this, "Photo taken!", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Log.d("Scouting", e.getMessage());
            Toast.makeText(this, "Failed to save photo. Try again!", Toast.LENGTH_LONG).show();
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

    public void clearData() {
        pitTeamNumberSpinner.setSelection(0);
        pitRobotWeight.setText(null);
        pitDriveTrainSpinner.setSelection(0);
        driveTrainOther.setText(null);
        pitProgrammingLanguages.setSelection(0);
        programmingLanguageOther.setText(null);
        pitAutoPrograms.setText(null);
        scouterInitialsInput.setText(null);
        pitControlPanelNothing.setChecked(false);
        pitControlPanelPosition.setChecked(false);
        pitControlPanelRotate3to5.setChecked(false);
        pitShootTrenchNear.setChecked(false);
        pitShootTrenchFar.setChecked(false);
        pitShootOther.setChecked(false);
        pitShootFieldFront.setChecked(false);
        pitShootFieldDiagonal.setChecked(false);
        pitShootTargetZone.setChecked(false);
        pitEndgameHang.clearCheck();
        pitDriveThruTrench.clearCheck();
        pitShotLocationOuter.setChecked(false);
        pitShotLocationInner.setChecked(false);
        pitShotLocationBottom.setChecked(false);
        pitShotLocationNa.setChecked(false);
        pitBasicPlan.setText(null);
        ControlPanelString = "";
        shotString = "";
        pitShotLocationString = "";

        pitDataStringList.clear();
    }

    private void checkForPermissions() {
        int cameraPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
        }
    }



}
