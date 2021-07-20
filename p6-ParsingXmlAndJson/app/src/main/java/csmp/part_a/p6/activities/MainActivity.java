package csmp.part_a.p6.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import csmp.part_a.p6.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button parseJsonButton = findViewById(R.id.parse_json_button);
        Button parseXmlButton = findViewById(R.id.parse_xml_button);

        parseJsonButton.setOnClickListener(this);
        parseXmlButton.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.parse_xml_button:
                Intent xmlIntent = new Intent(this, ParseActivity.class);
                xmlIntent.putExtra("isXml", true);
                startActivity(xmlIntent);
                break;
            case R.id.parse_json_button:
                Intent jsonIntent = new Intent(this, ParseActivity.class);
                startActivity(jsonIntent);
                break;
        }
    }
}