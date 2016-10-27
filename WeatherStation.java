import javax.xml.parsers.*;
import javax.xml.xpath.*;
import org.w3c.dom.Document;
import java.util.regex.*;
import java.util.*;
import javax.swing.*;
import java.net.*;

/**
 * WeatherStation ist eine virtuelle Wetterstation, die Daten von Yahoo abruft.
 * Falls keine Internetverbindung zur Verf�gung steht, kann die Station
 * zum Testen auch offline benutzt werden und wird mit Zufallsdaten gef�llt.
 *
 * @author Thomas Karp
 * @version 10.10.2016. Umstellung auf neue Yahoo-Api. Ab jetzt mit beliebigen St�dtenamen.
 * 
 */
public class WeatherStation {

    private Document xmlData;
    private String cityName;
    private String yahooCityName;
    private int temperature;
    private int humidity;
    private String sunrise;
    private String sunset;
    private String imgURL;
    private ImageIcon icon;
    private boolean online;

    /**
     * Create a new WeatherStation. Default location is berlin.
     * Station is set online.
     */
    public WeatherStation() {
        setCityName("berlin");
        online = true;
    }

    /**
     * @return Name of the choosen city.
     */
    public String getCityName() {
        return cityName;
    }

    /**
     * @return Name returned by Yahoo weather service.
     */
    public String getYahooCityName() {
        return yahooCityName;
    }

    /**
     * Set the location of the WeaterStation.
     * @param name of the city.
     */
    public void setCityName(String city) {
        cityName = city;
    }

    /**
     * Get the temperature at the city.
     */
    public int getTemperature() {
        return temperature;
    }

    /**
     * Get the humidity at the city.
     */
    public int getHumidity() {
        return humidity;
    }

    /**
     * Get the sunrise at the city.
     */
    public String getSunrise() {
        return sunrise;
    }

    /**
     * Get the sunset at the city.
     */
    public String getSunset() {
        return sunset;
    }

    /**
     * Get an url of an icon that represents the weather.
     * @return URL of image representing the actual weather.
     */
    public String getImgURL() {
        return imgURL;
    }

    /**
     * Get an ImageIcon representing the weather.
     */
    public ImageIcon getIcon() {
        return icon;
    }

    /**
     * Set online for real data. Set Offline for testing purposes without internet connection.
     * When offline random values are generated.
     * @param New online status: true if online, false if offline
     */
    public void setOnline(boolean isOnline) {
        online = isOnline;
        if (!online) {
            setDummyValues();
        }
    }

    /**
     * Get the status of the station.
     * @return true if online. false if offline.
     */
    public boolean isOnline() {
        return online;
    }

    /**
     * Download the XML-Document from Yahoo weather.
     */
    private void loadXMLDocument() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            xmlData = builder.parse("https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D%22" + cityName + "%22)%20and%20u%3D%22C%22&format=xml&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Parse the XML-Document, that contains the information.
     */
    private void scanXMLDocument() {
        XPath xpath = XPathFactory.newInstance().newXPath();
        try {
            // get content by XPath
            yahooCityName = xpath.evaluate("/query/results/channel/location/@city", xmlData);
            humidity = Integer.valueOf(xpath.evaluate("/query/results/channel/atmosphere/@humidity", xmlData));
            sunrise = xpath.evaluate("/query/results/channel/astronomy/@sunrise", xmlData);
            sunset = xpath.evaluate("/query/results/channel/astronomy/@sunset", xmlData);
            temperature = Integer.valueOf(xpath.evaluate("/query/results/channel/item/condition/@temp", xmlData));
            // Since the description is not XML, the content has to be searched by hand
            String description = xpath.evaluate("/query/results/channel/item/description", xmlData);
            imgURL = find("<img src=\".*\"/>", description); // search first img-tag
            imgURL = imgURL.substring(10, imgURL.length() - 3); // get src
            icon = new ImageIcon(new URL(imgURL));
        } catch (XPathExpressionException | NumberFormatException ex) {
            // Mache im Fehlerfall einfach nichts!
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Update weather data. If not online, dummy values are set for testing purposes.
     */
    public void update() {
        if (online) {
            loadXMLDocument();
            scanXMLDocument();
        } else {
            setDummyValues();
        }
    }

    // Returns the first substring in input that matches the pattern.
    // Returns null if no match found.
    // Source: http://exampledepot.com/egs/java.util.regex/Greedy.html
    private String find(String patternStr, CharSequence input) {
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }

    /**
     * Set random values for testing.
     */
    private void setDummyValues() {
        Random random = new Random();
        cityName = "Offlinecity" + Math.abs(random.nextInt(1000));
        yahooCityName = cityName;
        temperature = random.nextInt(40);
        humidity = random.nextInt(101);
        sunrise = "morning";
        sunset = "evening";
        imgURL = null;
    }

}
