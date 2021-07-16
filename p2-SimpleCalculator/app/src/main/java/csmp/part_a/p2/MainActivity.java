package csmp.part_a.p2;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final String PLUS = "+";
    private final String MINUS = "-";
    private final String MULTIPLY = "*";
    private final String DIVIDE = "/";
    private final String PERCENTAGE = "%";

    private TextView outputTextView;
    private TextView inputTextView;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        outputTextView = findViewById(R.id.output_view);
        inputTextView = findViewById(R.id.expression);

        Button btnZero = findViewById(R.id.zero);
        Button btnOne = findViewById(R.id.one);
        Button btnTwo = findViewById(R.id.two);
        Button btnThree = findViewById(R.id.three);
        Button btnFour = findViewById(R.id.four);
        Button btnFive = findViewById(R.id.five);
        Button btnSix = findViewById(R.id.six);
        Button btnSeven = findViewById(R.id.seven);
        Button btnEight = findViewById(R.id.eight);
        Button btnNine = findViewById(R.id.nine);
        Button btnZeroZero = findViewById(R.id.doubleZero);

        Button equalsBtn = findViewById(R.id.equals);
        Button dotBtn = findViewById(R.id.dot);
        Button percentageBtn = findViewById(R.id.percentage);
        Button clearBtn = findViewById(R.id.clear);
        Button backspaceBtn = findViewById(R.id.backspace);
        Button addBtn = findViewById(R.id.plus);
        Button subtractBtn = findViewById(R.id.minus);
        Button multiplyBtn = findViewById(R.id.multiply);
        Button divideBtn = findViewById(R.id.divide);

        btnZero.setOnClickListener(this);
        btnOne.setOnClickListener(this);
        btnTwo.setOnClickListener(this);
        btnThree.setOnClickListener(this);
        btnFour.setOnClickListener(this);
        btnFive.setOnClickListener(this);
        btnSix.setOnClickListener(this);
        btnSeven.setOnClickListener(this);
        btnEight.setOnClickListener(this);
        btnNine.setOnClickListener(this);
        btnZeroZero.setOnClickListener(this);

        equalsBtn.setOnClickListener(this);
        dotBtn.setOnClickListener(this);
        percentageBtn.setOnClickListener(this);
        clearBtn.setOnClickListener(this);
        backspaceBtn.setOnClickListener(this);
        addBtn.setOnClickListener(this);
        subtractBtn.setOnClickListener(this);
        multiplyBtn.setOnClickListener(this);
        divideBtn.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        String expression = inputTextView.getText().toString();

        switch (view.getId()) {
            case R.id.equals:
                calculate();
                break;
            case R.id.zero:
                inputTextView.append("0");
                break;
            case R.id.one:
                inputTextView.append("1");
                break;
            case R.id.two:
                inputTextView.append("2");
                break;
            case R.id.three:
                inputTextView.append("3");
                break;
            case R.id.four:
                inputTextView.append("4");
                break;
            case R.id.five:
                inputTextView.append("5");
                break;
            case R.id.six:
                inputTextView.append("6");
                break;
            case R.id.seven:
                inputTextView.append("7");
                break;
            case R.id.eight:
                inputTextView.append("8");
                break;
            case R.id.nine:
                inputTextView.append("9");
                break;
            case R.id.doubleZero:
                inputTextView.append("00");
                break;
            case R.id.plus:
                inputTextView.append(PLUS);
                break;
            case R.id.minus:
                inputTextView.append(MINUS);
                break;
            case R.id.multiply:
                inputTextView.append(MULTIPLY);
                break;
            case R.id.divide:
                inputTextView.append(DIVIDE);
                break;
            case R.id.clear:
                inputTextView.setText("");
                outputTextView.setText("");
                break;
            case R.id.backspace:
                int expressionLength = expression.length();
                if (expressionLength > 0)
                    inputTextView.setText(expression.substring(0, expressionLength - 1));

                if (!outputTextView.getText().toString().isEmpty())
                    outputTextView.setText(null);

                break;
            case R.id.dot:
                inputTextView.append(".");
                break;
            case R.id.percentage:
                inputTextView.append(PERCENTAGE);
                break;
        }
    }

    private void calculate() {
        String expression = inputTextView.getText().toString();
        float answer = 0;

        String[] operands;
        String leftOperand;
        String rightOperand;
        try {
            // to split the string to get the operands acc. to any Math operator
            operands = expression.split("[*+\\-/%]");
            leftOperand = operands[0];
            rightOperand = operands[1];
        } catch (ArrayIndexOutOfBoundsException e) {
            makeToast("Invalid expression: " + e);
            return;
        }

        float n1, n2;
        try {
            n1 = Float.parseFloat(leftOperand);
            n2 = Float.parseFloat(rightOperand);
        } catch (Exception e) {
            makeToast("Currently the app doesn't this type of expression...☹");
            return;
        }

        if (expression.contains(PLUS)) {
            answer = n1 + n2;
        } else if (expression.contains(MINUS)) {
            answer = n1 - n2;
        } else if (expression.contains(MULTIPLY)) {
            answer = n1 * n2;
        } else if (expression.contains(DIVIDE)) {
            if (n2 == 0) {
                String INFINITY = "infinity";
                outputTextView.setText(INFINITY);
                return;
            } else
                answer = n1 / n2;
        } else if (expression.contains(PERCENTAGE)) {
            answer = n1 / 100 * n2;
        } else {
            Log.i("Invalid", "Invalid answer");
        }

        DecimalFormat decimalFormat = new DecimalFormat("#.####");
        outputTextView.setText(decimalFormat.format(answer));
    }

    public void makeToast(String toastMessage) {
        Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_SHORT).show();
    }
}