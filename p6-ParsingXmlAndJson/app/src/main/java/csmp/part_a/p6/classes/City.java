package csmp.part_a.p6.classes;

public class City {
    private String cityName, humidity;
    private double latitude, longitude, temperature;

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    @Override
    public String toString() {
        return "\n City_Name \t : \t" + cityName +
                "\n Latitude \t : \t" + latitude +
                "\n Longitude \t : \t" + longitude +
                "\n Temperature \t : \t" + temperature +
                "\n Humidity \t : \t" + humidity + "\n";
    }
}