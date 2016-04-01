package com.example.jameskempf.calculator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private String operator = "";
    private double answer = Double.NaN,
        input = Double.NaN;
    int decimalPlace = 0;
    private Button b1, b2, b3, b4, b5, b6, b7, b8, b9, b0,
        bAC, bDec, bNegate,
        bAdd, bSubtract, bMultiply, bDivide,
        bEqual;
    private TextView inputText;
    private RelativeLayout content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        Log.v("PREFS ", prefs.toString());
        String str = prefs.getString(getString(R.string.night_mode_key),
                String.valueOf(R.string.backgroundDay));
        Log.v("STRING ", str);

        init();
    }

    private void init() {
        // Set buttons
        b1 = (Button)findViewById(R.id.button1);
        b2 = (Button)findViewById(R.id.button2);
        b3 = (Button)findViewById(R.id.button3);
        b4 = (Button)findViewById(R.id.button4);
        b5 = (Button)findViewById(R.id.button5);
        b6 = (Button)findViewById(R.id.button6);
        b7 = (Button)findViewById(R.id.button7);
        b8 = (Button)findViewById(R.id.button8);
        b9 = (Button)findViewById(R.id.button9);
        b0 = (Button)findViewById(R.id.button0);

        bAC = (Button)findViewById(R.id.buttonAC);
        bDec = (Button)findViewById(R.id.buttonDec);
        bNegate = (Button)findViewById(R.id.buttonNegate);

        bAdd = (Button)findViewById(R.id.buttonAdd);
        bSubtract = (Button)findViewById(R.id.buttonSubtract);
        bMultiply = (Button)findViewById(R.id.buttonMultiply);
        bDivide = (Button)findViewById(R.id.buttonDivide);

        bEqual = (Button)findViewById(R.id.buttonEqual);
;
        // Set button click listeners
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        b4.setOnClickListener(this);
        b5.setOnClickListener(this);
        b6.setOnClickListener(this);
        b7.setOnClickListener(this);
        b8.setOnClickListener(this);
        b9.setOnClickListener(this);
        b0.setOnClickListener(this);

        bAC.setOnClickListener(this);
        bDec.setOnClickListener(this);
        bNegate.setOnClickListener(this);

        bAdd.setOnClickListener(this);
        bSubtract.setOnClickListener(this);
        bMultiply.setOnClickListener(this);
        bDivide.setOnClickListener(this);

        bEqual.setOnClickListener(this);

        // Set text input
        inputText = (TextView)findViewById(R.id.textInput);

        // Set content
        content = (RelativeLayout)findViewById(R.id.contentView);

        final Switch nightSwitch = (Switch)findViewById(R.id.nightSwitch);

        nightSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    content.setBackgroundColor(Color.DKGRAY);
                    nightSwitch.setTextColor(Color.WHITE);
                    inputText.setTextColor(Color.WHITE);
                    Toast.makeText(getApplicationContext(), "Night Mode On", Toast.LENGTH_SHORT).show();
                } else {
                    content.setBackgroundColor(Color.WHITE);
                    nightSwitch.setTextColor(Color.BLACK);
                    inputText.setTextColor(Color.BLACK);
                    Toast.makeText(getApplicationContext(), "Night Mode Off", Toast.LENGTH_SHORT).show();
                }
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
        //if (id == R.id.action_settings) {
        //    return true;
        //}
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.button1:
                setNumber(1);
                break;
            case R.id.button2:
                setNumber(2);
                break;
            case R.id.button3:
                setNumber(3);
                break;
            case R.id.button4:
                setNumber(4);
                break;
            case R.id.button5:
                setNumber(5);
                break;
            case R.id.button6:
                setNumber(6);
                break;
            case R.id.button7:
                setNumber(7);
                break;
            case R.id.button8:
                setNumber(8);
                break;
            case R.id.button9:
                setNumber(9);
                break;
            case R.id.button0:
                setNumber(0);
                break;

            case R.id.buttonAC:
                clear();
                break;
            case R.id.buttonDec:
                Decimal();
                break;
            case R.id.buttonNegate:
                negate();
                break;

            case R.id.buttonAdd:
                setOperator("+");
                break;
            case R.id.buttonSubtract:
                setOperator("-");
                break;
            case R.id.buttonMultiply:
                setOperator("*");
                break;
            case R.id.buttonDivide:
                setOperator("/");
                break;

            case R.id.buttonEqual:
                evaluate();
                break;
        }
        Log.v("END SWITCH", "asd");
        updateText();
    }

    private void setNumber(double n) {
        if (!(!Double.isNaN(answer) && operator.equals(""))) {
            if (decimalPlace > 0) {
                input = input + (n / Math.pow(10,decimalPlace));
                decimalPlace ++;
            }
            else if (Double.isNaN(input)) {
                input = n;
            } else {
                input = input * 10 + n;
            }
            Log.v("input", Double.toString(input));
        }
    }

    private void setOperator(String op) {
        if (Double.isNaN(answer)) {
            answer = input;
            input = Double.NaN;
        }
        operator = op;
        decimalPlace = 0;
    }

    private void evaluate() {
        switch(operator) {
            case "+":
                answer = answer + input;
                break;
            case "-":
                answer = answer - input;
                break;
            case "*":
                answer = answer * input;
                break;
            case "/":
                answer = answer / input;
                break;
            case "":
                break;
        }
        operator = "";
        input = Double.NaN;
        decimalPlace = 0;
    }

    private void clear() {
        answer = Double.NaN;
        input = Double.NaN;
        operator = "";
        decimalPlace = 0;
        Log.v("CLEAR", Double.toString(answer) + " " + Double.toString(input) + " " + operator);
    }

    private void Decimal() {
        decimalPlace = 1;
    }

    private void negate() {
        if (Double.isNaN(input)) {
            answer = 0 - answer;
        }
        else {
            input = 0 - input;
        }
    }

    private void updateText() {
        Log.v("BEGIN UPDATE", "asd");
        if (input == (int)input) {
            Log.v("TRUE", "asd");
        }
        else {
            Log.v("FALSE", "asd");
        }
        String text = "";
        if (!Double.isNaN(answer)) {
            if (answer == (int)answer) {
                text += Double.toString((int)answer);
                Log.v("INTEGER", "asd");
            }
            else
                text += Double.toString(answer);
        }
        if (operator != "") {
            text += " " + operator + " ";
        }
        if (!Double.isNaN(input)) {
            if (input == (int)input) {
                text += Double.toString((int)input);
                Log.v("INTEGER", "asd");
            }
            else
                text += Double.toString(input);

        }
        Log.v("UPDATE", "update text: " + text);
        if (text.equals("")) {
            inputText.setText("0");
        }
        else {
            inputText.setText(text);
        }
    }
}
