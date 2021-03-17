package csmp.part_a.p2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button zeroBtn, oneBtn, twoBtn, threeBtn, fourBtn, fiveBtn, sixBtn, sevenBtn, eightBtn, nineBtn;
    Button equalsBtn, dotBtn, clearBtn, backspaceBtn, addBtn, subBtn, multiplyBtn, divideBtn;
    private TextView resultTextView;
    private TextView expressionTextView;

    float lhs, rhs, answer;
    String lhsString, rhsString, answerString;

    Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultTextView = findViewById(R.id.outputView);
        expressionTextView = findViewById(R.id.expression);

        zeroBtn = (Button) findViewById(R.id.zero);
        oneBtn = findViewById(R.id.one);
        twoBtn = findViewById(R.id.two);
        threeBtn = findViewById(R.id.three);
        fourBtn = findViewById(R.id.four);
        fiveBtn = findViewById(R.id.five);
        sixBtn = findViewById(R.id.six);
        sevenBtn = findViewById(R.id.seven);
        eightBtn = findViewById(R.id.eight);
        nineBtn = findViewById(R.id.nine);

        equalsBtn = findViewById(R.id.equals);
        dotBtn = findViewById(R.id.dot);
        clearBtn = findViewById(R.id.clear);
        backspaceBtn = findViewById(R.id.backspace);
        addBtn = findViewById(R.id.plus);
        subBtn = findViewById(R.id.minus);
        multiplyBtn = findViewById(R.id.multiply);
        divideBtn = findViewById(R.id.divide);

        zeroBtn.setOnClickListener(this);
        oneBtn.setOnClickListener(this);
        twoBtn.setOnClickListener(this);
        threeBtn.setOnClickListener(this);
        fourBtn.setOnClickListener(this);
        fiveBtn.setOnClickListener(this);
        sixBtn.setOnClickListener(this);
        sevenBtn.setOnClickListener(this);
        eightBtn.setOnClickListener(this);
        nineBtn.setOnClickListener(this);

        equalsBtn.setOnClickListener(this);
        dotBtn.setOnClickListener(this);
        clearBtn.setOnClickListener(this);
        backspaceBtn.setOnClickListener(this);
        addBtn.setOnClickListener(this);
        subBtn.setOnClickListener(this);
        multiplyBtn.setOnClickListener(this);
        divideBtn.setOnClickListener(this);

        vibrator = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
    }

    @Override
    public void onClick(View view) {
        vibrator.vibrate(10); //used to get haptic feedback for every button click
        int id = view.getId();
        String exp = expressionTextView.getText().toString();

        switch (id) {
            case R.id.equals:
                calculate();
                break;
            case R.id.zero:
                generateExpression("0");
                break;
            case R.id.one:
                generateExpression("1");
                break;
            case R.id.two:
                generateExpression("2");
                break;
            case R.id.three:
                generateExpression("3");
                break;
            case R.id.four:
                generateExpression("4");
                break;
            case R.id.five:
                generateExpression("5");
                break;
            case R.id.six:
                generateExpression("6");
                break;
            case R.id.seven:
                generateExpression("7");
                break;
            case R.id.eight:
                generateExpression("8");
                break;
            case R.id.nine:
                generateExpression("9");
                break;
            case R.id.plus:
                generateExpression("+");
                break;
            case R.id.minus:
                generateExpression("-");
                break;
            case R.id.multiply:
                generateExpression("*");
                break;
            case R.id.divide:
                generateExpression("/");
                break;
            case R.id.clear:
                expressionTextView.setText("");
                resultTextView.setText("");
                break;
            case R.id.backspace:
                int expLength = exp.length();
                if (expLength > 0) {
                    expressionTextView.setText(exp.substring(0, expLength - 1));
                }
                break;
            case R.id.dot:
                String dotExpression = "[0-9]+\\.[0-9]+";
                generateExpression(".");
                break;
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Are you sure ?")
                .setMessage("You want to exit ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("No", null).show();
    }

    private void calculate() {
        String mathExpression = "[+-]?[0-9]+(\\.[0-9]+)?[\\*+\\-/][+-]?[0-9]+(\\.[0-9]+)?";
        String exp = expressionTextView.getText().toString();

        if (exp.matches("[0-9]+")) {
            Log.i("MATCH", "FOUND: ");
            return;
        } else Log.i("MATCH", "NOT FOUND: ");

        if (exp.equals("") || exp.equals(".") || isSpecialChar("" + exp.charAt(exp.length() - 1))) {
            return;
        }
        if (!exp.matches(mathExpression)) {
            return;
        }

        rhsString = exp.substring(lhsString.length() + 1);
        rhs = Float.parseFloat(rhsString);
        Log.i("RHS", "Value of rhs: " + rhs);

        char operator = exp.charAt(lhsString.length());
        Log.i("OPERATOR", "operator: " + operator);
        switch (operator) {
            case '+':
                answer = lhs + rhs;
                break;
            case '-':
                answer = lhs - rhs;
                break;
            case '*':
                answer = lhs * rhs;
                break;
            case '/':
                answer = lhs / rhs;
                break;
            default:
                Log.i("Invalid", "Invalid answer");
        }
        Log.i("ANSWER", "answer: " + answer);

        if (answer % 1 == 0) // to convert a float to int if the float is having zeroes in their decimal, Ex: 3.0 to 3
            answerString = String.valueOf((int) answer);
        else answerString = String.valueOf(answer);

        resultTextView.setText(answerString);
    }

    public void generateExpression(String val) {
        String exp = expressionTextView.getText().toString();
        String previousOut = resultTextView.getText().toString();
        String mathExpression = "[+-]?[0-9]+(\\.[0-9]+)?[\\*+\\-/][+-]?[0-9]+(\\.[0-9]+)?";

        //if condition test

        if (isSpecialChar(val)) {
            if (exp.matches(mathExpression) && previousOut.equals("")) {//to check whether, the expression is valid or not using RegEx(error handling)
                return;
            }
            if (!previousOut.equals("")) { //if previous output is not empty then to append the special character at the end of the previous output
                resultTextView.setText("");
                expressionTextView.setText(String.format("%s%s", previousOut, val));
                lhsString = previousOut;
                lhs = Float.parseFloat(lhsString);
                Log.i("LHS", "Value of lhs: " + lhs);
                return;
            }

            lhsString = exp; // to store the lhs part after getting the operator
            lhs = Float.parseFloat(lhsString);
            Log.i("LHS", "Value of lhs: " + lhs);
        }

        if (previousOut.equals("")) {
            expressionTextView.setText(String.format("%s%s", exp, val));
        } else {
            resultTextView.setText("");
            expressionTextView.setText(String.format("%s%s", "", val));
        }
    }

    public boolean isSpecialChar(String str) {
        return Pattern.compile("[$&+,:;=\\\\?@#|/'<>.^*()%!-]").matcher(str).find();
    }

}
