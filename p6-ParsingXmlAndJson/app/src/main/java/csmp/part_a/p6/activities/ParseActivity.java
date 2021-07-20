package csmp.part_a.p6.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import csmp.part_a.p6.R;
import csmp.part_a.p6.models.City;

public class ParseActivity extends AppCompatActivity {

    private Element element;
    private City city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parse);

        TextView xmlListView = findViewById(R.id.parsed_xml_text_view);
        TextView jsonListView = findViewById(R.id.parsed_json_text_view);
        TextView textView = findViewById(R.id.textView);

        Intent intent = getIntent();
        boolean isXml = intent.getBooleanExtra("isXml", false);

        if (isXml) {
            textView.setText("PARSED XML DATA");
            parseXml(xmlListView);
        } else {
            textView.setText("PARSED JSON DATA");
            parseJson(jsonListView);
        }
    }

    private void parseXml(TextView xmlListView) {
        try {
            InputStream inputStream = getAssets().open("city.xml");

            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

            Document document = documentBuilder.parse(inputStream);
            NodeList nodeList = document.getElementsByTagName("city");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    element = (Element) node;

                    String cityName = getValue("City_name");
                    String latitude = getValue("Latitude");
                    String longitude = getValue("Longitude");
                    String temperature = getValue("Temperature");
                    String humidity = getValue("Humidity");

                    city = new City(cityName, humidity, latitude, longitude, temperature);
                    xmlListView.append(city.toString());
                }
            }
            inputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getValue(String tag) {
        return element.getElementsByTagName(tag).item(0).getChildNodes().item(0).getNodeValue();
    }

    private void parseJson(TextView jsonListView) {
        try {
            InputStream inputStream = getAssets().open("city.json");
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);

            // get JSONObject from JSON file
            JSONObject obj = new JSONObject(new String(buffer));
            // fetch JSONObject named cities
            JSONArray cityArray = obj.getJSONArray("cities");

            for (int i = 0; i < cityArray.length(); i++) {
                JSONObject cityObject = cityArray.getJSONObject(i);

                String cityName = cityObject.getString("City_name");
                String latitude = cityObject.getString("Latitude");
                String longitude = cityObject.getString("Longitude");
                String temperature = cityObject.getString("Temperature");
                String humidity = cityObject.getString("Humidity");

                city = new City(cityName, humidity, latitude, longitude, temperature);
                jsonListView.append(city.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}