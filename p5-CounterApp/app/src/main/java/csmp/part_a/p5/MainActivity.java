package csmp.part_a.p5;

import android.os.Bundle;
import android.os.Handler;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private TextView counterText;

    private int counter;
    private Handler handler;
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ToggleButton toggleButton = findViewById(R.id.toggleButton);
        counterText = findViewById(R.id.counterTextView);

        toggleButton.setOnCheckedChangeListener(this);

        counter = 0;
        handler = new Handler();
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        if (isChecked)
            handleCounter();
        else handler.removeCallbacks(runnable);
    }

    private void handleCounter() {
        runnable = new Runnable() {
            @Override
            public void run() {
                counter++;
                counterText.setText(String.valueOf(counter));
                handler.postDelayed(this, 100);
            }
        };
        handler.post(runnable);
    }
}