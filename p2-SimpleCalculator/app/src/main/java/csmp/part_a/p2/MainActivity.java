package csmp.part_a.p2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = MainActivity.class.getSimpleName();
    private final String PLUS = "+";
    private final String MINUS = "-";
    private final String MULTIPLY = "*";
    private final String DIVIDE = "/";

    private TextView resultTextView;
    private TextView expressionTextView;

    private float lhs, rhs, answer;
    private String lhsString, rhsString, answerString;

    private Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAppTheme();
        setContentView(R.layout.activity_main);

        resultTextView = findViewById(R.id.outputView);
        expressionTextView = findViewById(R.id.expression);

        Button buttonZero = findViewById(R.id.zero);
        Button buttonOne = findViewById(R.id.one);
        Button buttonTwo = findViewById(R.id.two);
        Button buttonThree = findViewById(R.id.three);
        Button buttonFour = findViewById(R.id.four);
        Button buttonFive = findViewById(R.id.five);
        Button buttonSix = findViewById(R.id.six);
        Button buttonSeven = findViewById(R.id.seven);
        Button buttonEight = findViewById(R.id.eight);
        Button buttonNine = findViewById(R.id.nine);

        Button equalsButton = findViewById(R.id.equals);
        Button dotButton = findViewById(R.id.dot);
        Button clearButton = findViewById(R.id.clear);
        Button backspaceButton = findViewById(R.id.backspace);
        Button addButton = findViewById(R.id.plus);
        Button subtractButton = findViewById(R.id.minus);
        Button multiplyButton = findViewById(R.id.multiply);
        Button divideButton = findViewById(R.id.divide);

        buttonZero.setOnClickListener(this);
        buttonOne.setOnClickListener(this);
        buttonTwo.setOnClickListener(this);
        buttonThree.setOnClickListener(this);
        buttonFour.setOnClickListener(this);
        buttonFive.setOnClickListener(this);
        buttonSix.setOnClickListener(this);
        buttonSeven.setOnClickListener(this);
        buttonEight.setOnClickListener(this);
        buttonNine.setOnClickListener(this);

        equalsButton.setOnClickListener(this);
        dotButton.setOnClickListener(this);
        clearButton.setOnClickListener(this);
        backspaceButton.setOnClickListener(this);
        addButton.setOnClickListener(this);
        subtractButton.setOnClickListener(this);
        multiplyButton.setOnClickListener(this);
        divideButton.setOnClickListener(this);

        vibrator = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        //String initialisations
        final String ZERO = "0";
        final String ONE = "1";
        final String TWO = "2";
        final String THREE = "3";
        final String FOUR = "4";
        final String FIVE = "5";
        final String SIX = "6";
        final String SEVEN = "7";
        final String EIGHT = "8";
        final String NINE = "9";
        final String DOT = ".";
        final String EMPTY_STRING = "";

        vibrator.vibrate(10); //used to get haptic feedback for every button click
        int id = view.getId();
        String expression = expressionTextView.getText().toString();

        switch (id) {
            case R.id.equals:
                calculate();
                break;
            case R.id.zero:
                generateExpression(ZERO);
                break;
            case R.id.one:
                generateExpression(ONE);
                break;
            case R.id.two:
                generateExpression(TWO);
                break;
            case R.id.three:
                generateExpression(THREE);
                break;
            case R.id.four:
                generateExpression(FOUR);
                break;
            case R.id.five:
                generateExpression(FIVE);
                break;
            case R.id.six:
                generateExpression(SIX);
                break;
            case R.id.seven:
                generateExpression(SEVEN);
                break;
            case R.id.eight:
                generateExpression(EIGHT);
                break;
            case R.id.nine:
                generateExpression(NINE);
                break;
            case R.id.plus:
                generateExpression(PLUS);
                break;
            case R.id.minus:
                generateExpression(MINUS);
                break;
            case R.id.multiply:
                generateExpression(MULTIPLY);
                break;
            case R.id.divide:
                generateExpression(DIVIDE);
                break;
            case R.id.clear:
                expressionTextView.setText(EMPTY_STRING);
                resultTextView.setText(EMPTY_STRING);
                break;
            case R.id.backspace:
                int expressionLength = expression.length();
                if (expressionLength > 0)
                    expressionTextView.setText(expression.substring(0, expressionLength - 1));
                break;
            case R.id.dot:
                //String dotExpression = "[0-9]+\\.[0-9]+";
                generateExpression(DOT);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Are you sure ?")
                .setMessage("You want to exit ?")
                .setPositiveButton("Yes", (dialog, which) -> finish())
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
        switch (String.valueOf(operator)) {
            case PLUS:
                answer = lhs + rhs;
                break;
            case MINUS:
                answer = lhs - rhs;
                break;
            case MULTIPLY:
                answer = lhs * rhs;
                break;
            case DIVIDE:
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

        // conditions for error handling

        if (isSpecialChar(val)) {
            // to check whether, the expression is valid or not using RegEx(error handling)
            if (exp.matches(mathExpression) && previousOut.equals("")) {
                return;
            }
            // if previous output is not empty then to append the special character at the end of the previous output
            if (!previousOut.equals("")) {
                resultTextView.setText("");
                expressionTextView.setText(String.format("%s%s", previousOut, val));
                lhsString = previousOut;
                lhs = Float.parseFloat(lhsString);
                Log.i("LHS", "Value of lhs: " + lhs);
                return;
            }

            lhsString = exp; // to store the LHS part after getting the operator
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

    private void setAppTheme() {
        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        //bitwise AND operator(&) is used for binary digits or bits of input values.
        //logical AND operator(&&) is used for boolean expressions
        Log.i(TAG, "onCreate: " + currentNightMode);
        switch (currentNightMode) {
            case Configuration.UI_MODE_NIGHT_YES:
                // Night mode is active, we're using dark theme
                setTheme(R.style.DarkTheme);
                break;
            case Configuration.UI_MODE_NIGHT_NO:
                // Night mode is not active, we're using the light theme
            case Configuration.UI_MODE_NIGHT_UNDEFINED:
                // If mode is not defined
                setTheme(R.style.LightTheme);
                break;
        }
    }
}
