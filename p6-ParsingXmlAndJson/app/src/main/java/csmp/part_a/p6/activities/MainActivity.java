package csmp.part_a.p6.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import csmp.part_a.p6.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button parseXmlButton, parseJsonButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        parseJsonButton = (Button) findViewById(R.id.parseJsonButton);
        parseXmlButton = (Button) findViewById(R.id.parseXmlButton);

        parseJsonButton.setOnClickListener(this);
        parseXmlButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.parseXmlButton:
                Intent xmlIntent = new Intent(MainActivity.this, ParsedXml.class);
                startActivity(xmlIntent);
                break;
            case R.id.parseJsonButton:
                Intent jsonIntent = new Intent(MainActivity.this, ParsedJson.class);
                startActivity(jsonIntent);
                break;
        }
    }
}