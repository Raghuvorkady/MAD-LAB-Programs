package csmp.part_a.p5;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button startButton, stopButton;
    TextView counterValueText;

    int counter;
    Handler handler;

    Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = (Button) findViewById(R.id.startButton);
        stopButton = (Button) findViewById(R.id.stopButton);
        counterValueText = (TextView) findViewById(R.id.counterValueTextView);

        startButton.setOnClickListener(this);
        stopButton.setOnClickListener(this);

        counter = 0;
        handler = new Handler();
    }

    public void handleCounter() {
        runnable = new Runnable() {
            @Override
            public void run() {
                counter++;
                counterValueText.setText(String.valueOf(counter));
                handler.postDelayed(this, 100);
            }
        };
        handler.post(runnable);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.startButton:
                handleCounter();
                startButton.setEnabled(false);
                stopButton.setEnabled(true);
                break;
            case R.id.stopButton:
                handler.removeCallbacks(runnable);
                startButton.setEnabled(true);
                stopButton.setEnabled(false);
                break;
        }
    }
}