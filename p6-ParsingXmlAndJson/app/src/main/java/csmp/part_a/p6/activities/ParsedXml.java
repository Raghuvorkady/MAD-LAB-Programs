package csmp.part_a.p6.activities;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import csmp.part_a.p6.R;
import csmp.part_a.p6.classes.City;

public class ParsedXml extends AppCompatActivity {

    private Element element;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parsed_json_xml);

        ListView xmlListView = findViewById(R.id.list_view);
        TextView textView = findViewById(R.id.textView);

        textView.setText("PARSED XML DATA");

        List<City> cities = new ArrayList<>();

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
                    City city = new City();
                    city.setCityName(getValue("City_name"));
                    city.setLatitude(Double.parseDouble(getValue("Latitude")));
                    city.setLongitude(Double.parseDouble(getValue("Longitude")));
                    city.setTemperature(Double.parseDouble(getValue("Temperature")));
                    city.setHumidity(getValue("Humidity"));
                    cities.add(city);
                }
            }
            inputStream.close();

            ArrayAdapter<City> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, cities);
            xmlListView.setAdapter(arrayAdapter);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }

    private String getValue(String tag) {
        return element.getElementsByTagName(tag).item(0).getChildNodes().item(0).getNodeValue();
    }
}