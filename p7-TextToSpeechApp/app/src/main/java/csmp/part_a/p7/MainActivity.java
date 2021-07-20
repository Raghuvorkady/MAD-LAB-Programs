package csmp.part_a.p7;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editText;
    private TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.edit_text);
        Button button = findViewById(R.id.convert_button);

        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.US);
                }
            }
        });

        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String text = editText.getText().toString();

        if (text.isEmpty()) {
            text = "Please, Write something in the EditText...";
        }

        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }
}