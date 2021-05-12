package csmp.part_a.p2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private final String TAG = MainActivity.class.getSimpleName();
    private final String PLUS = "+";
    private final String MINUS = "-";
    private final String MULTIPLY = "*";
    private final String DIVIDE = "/";
    private final String PERCENTAGE = "%";
    private final String INFINITY = "infinity";

    private TextView resultTextView;
    private TextView expressionTextView;

    private SwitchCompat appThemeSwitch;

    private Vibrator vibrator;
    private boolean isDarkThemeEnabled;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
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
        Button buttonZeroZero = findViewById(R.id.doubleZero);

        Button equalsButton = findViewById(R.id.equals);
        Button dotButton = findViewById(R.id.dot);
        Button percentageButton = findViewById(R.id.percentage);
        Button clearButton = findViewById(R.id.clear);
        Button backspaceButton = findViewById(R.id.backspace);
        Button addButton = findViewById(R.id.plus);
        Button subtractButton = findViewById(R.id.minus);
        Button multiplyButton = findViewById(R.id.multiply);
        Button divideButton = findViewById(R.id.divide);

        appThemeSwitch = findViewById(R.id.appThemeSwitch);

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
        buttonZeroZero.setOnClickListener(this);

        equalsButton.setOnClickListener(this);
        dotButton.setOnClickListener(this);
        percentageButton.setOnClickListener(this);
        clearButton.setOnClickListener(this);
        backspaceButton.setOnClickListener(this);
        addButton.setOnClickListener(this);
        subtractButton.setOnClickListener(this);
        multiplyButton.setOnClickListener(this);
        divideButton.setOnClickListener(this);

        //android:tooltipText="Enable or disable dark mode"
        appThemeSwitch.setChecked(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            appThemeSwitch.setTooltipText("Enable dark mode");
        }
        appThemeSwitch.setOnCheckedChangeListener(this);

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
        final String ZERO_ZERO = "00";
        final String DOT = ".";
        final String EMPTY_STRING = "";

        vibrator.vibrate(10); //used to get haptic feedback for every button click
        String expression = expressionTextView.getText().toString();

        switch (view.getId()) {
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
            case R.id.doubleZero:
                generateExpression(ZERO_ZERO);
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

                if (!resultTextView.getText().toString().isEmpty())
                    resultTextView.setText(null);

                break;
            case R.id.dot:
                generateExpression(DOT);
                break;
            case R.id.percentage:
                generateExpression(PERCENTAGE);
                break;
        }
    }

    public void generateExpression(String value) {
        String expression = expressionTextView.getText().toString();
        String previousOutput = resultTextView.getText().toString();
        String text;

        // conditions for error handling
        if (previousOutput.isEmpty()) {
            text = expression + value;
            expressionTextView.setText(text);
        } else {
            if (previousOutput.equalsIgnoreCase(INFINITY)) { // to not append while displaying infinity
                return;
            } else {
                text = previousOutput + value;
                expressionTextView.setText(text); // to append output to the expression
                resultTextView.setText("");
            }
        }
    }

    private void calculate() {
        String mathExpression = "[+-]?[0-9]+(\\.[0-9]+)?[*+\\-/%][+-]?[0-9]+(\\.[0-9]+)?";
        String operators = "[*+\\-/%]";
        String expression = expressionTextView.getText().toString();
        float answer = 0;

        if (expression.matches(mathExpression)) {
            String[] operands = expression.split(operators); // to split the string to get the operands acc to any Math operator
            String leftOperand = operands[0];
            String rightOperand = operands[1];
            float n1, n2;
            try {
                n1 = Float.parseFloat(leftOperand);
                n2 = Float.parseFloat(rightOperand);
            } catch (Exception e) {
                makeToast("Currently the app doesn't this type of expression...☹");
                return;
            }

            Pattern pattern = Pattern.compile(operators);
            Matcher matcher = pattern.matcher(expression);  // Regex

            if (matcher.find()) { // tries to find the matching character
                int index = matcher.start(); // returns the first index of the previous matched character
                char operator = expression.charAt(index);

                switch (String.valueOf(operator)) {
                    case PLUS:
                        answer = n1 + n2;
                        break;
                    case MINUS:
                        answer = n1 - n2;
                        break;
                    case MULTIPLY:
                        answer = n1 * n2;
                        break;
                    case DIVIDE:
                        if (n2 == 0) {
                            resultTextView.setText(INFINITY);
                            return;
                        } else
                            answer = n1 / n2;
                        break;
                    case PERCENTAGE:
                        answer = n1 / 100 * n2;
                        break;
                    default:
                        Log.i("Invalid", "Invalid answer");
                }
            }

            DecimalFormat decimalFormat = new DecimalFormat("#.####");
            resultTextView.setText(decimalFormat.format(answer));
        }
    }

    private void setAppTheme() {
        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        //bitwise AND operator(&) is used for binary digits or bits of input values.
        //logical AND operator(&&) is used for boolean expressions
        Log.i(TAG, "onCreate: " + currentNightMode);
        switch (currentNightMode) {
            case Configuration.UI_MODE_NIGHT_YES:
                // Night mode is active, we're using dark theme
                isDarkThemeEnabled = true;
                setTheme(R.style.DarkTheme);
                break;
            case Configuration.UI_MODE_NIGHT_NO:
                // Night mode is not active, we're using the light theme
            case Configuration.UI_MODE_NIGHT_UNDEFINED:
                // If mode is not defined
                isDarkThemeEnabled = false;
                setTheme(R.style.LightTheme);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(isDarkThemeEnabled ? R.drawable.ic_warning_light : R.drawable.ic_warning_dark)
                .setTitle("Are you sure ?")
                .setMessage("You want to exit ?")
                .setPositiveButton("Yes", (dialog, which) -> finish())
                .setNegativeButton("No", null).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        if (isChecked) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            appThemeSwitch.setTooltipText("Disable dark mode");
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            appThemeSwitch.setTooltipText("Enable dark mode");
        }
    }

    public void makeToast(String toastMessage) {
        Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_SHORT).show();
    }
}