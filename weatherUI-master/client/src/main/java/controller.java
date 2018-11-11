import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import java.io.*;
import java.net.Socket;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javafx.fxml.Initializable;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;  
import javafx.fxml.FXMLLoader;
import javafx.stage.Window;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TabPane;

public class controller {
    
    private static weather weatherData; // variable for the class containing data
    private int counter = 0;
    @FXML private AnchorPane mainAnchorPane;
    @FXML private TabPane tabPane;
    @FXML private Pane backgroundGif;
    @FXML private Label locationLabel,tempLabel,tempNameLabel,tempDescriptionLabel,humidityLabel,minTempLabel,maxTempLabel,windspeedLabel,cloudLabel, datetimeLabel;
    @FXML private ComboBox cityMenu;
    ObservableList<String> cityMenuItems = FXCollections.observableArrayList("Mississauga, CA", "Guelph, CA", "Ottawa, CA","Kingston, CA", "Markham, CA", "London, CA", "Biysk, RU", "Novinki, RU", "Hong Kong, HK", "Northampton, GB", "Dubai, AE", "Akita, JP");

    public void initialize() {

        // Set the hours based on the different time zones
        long totalMilliseconds = System.currentTimeMillis();
        long totalSeconds = totalMilliseconds / 1000;
        long totalMinutes = totalSeconds / 60;
        long totalHours = totalMinutes / 60;
        long currentSeconds = totalSeconds % 60;
        long currentMinutes = totalMinutes % 60;
        long currentHours = totalHours % 24;
        if (weatherData.getCountryCode().equals("CA")){
            currentHours = getESTHour(currentHours);
        }else if (weatherData.getCountryCode().equals("RU")){
            currentHours = getMSKHour(currentHours);
        }else if (weatherData.getCountryCode().equals("HK")){
            currentHours = getHKTHour(currentHours);
        }else if (weatherData.getCountryCode().equals("GB")){
            currentHours = getNGBHour(currentHours);
        }else if (weatherData.getCountryCode().equals("AE")){
            currentHours = getDAEHour(currentHours);
        }else if (weatherData.getCountryCode().equals("JP")){
            currentHours = getAJPHour(currentHours);
        }
        // Add the contents to the city drop down menu
        cityMenu.setItems(cityMenuItems);
        // set these values for all instances:
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        // Formatting
        if (currentMinutes < 10){
            datetimeLabel.setText(dateFormat.format(date) + ", " + Math.abs(currentHours) + ":" + "0" + currentMinutes + ":" + currentSeconds);
        }
        else if (currentSeconds < 10){
            datetimeLabel.setText(dateFormat.format(date) + ", " +Math.abs(currentHours) + ":" + currentMinutes + ":" + "0"+currentSeconds);
        }
        else if (currentMinutes < 10 && currentSeconds < 10){
            datetimeLabel.setText(dateFormat.format(date) + ", " +Math.abs(currentHours) + ":" + "0" + currentMinutes + ":" + "0" +currentSeconds);
        }else{
            datetimeLabel.setText(dateFormat.format(date) + ", " +Math.abs(currentHours) + ":" + currentMinutes + ":" +currentSeconds);
        }
        // Add the data to the UI accordingly.
        locationLabel.setText(weatherData.getCityName() + ", " + weatherData.getCountryCode());
        tempLabel.setText(weatherData.getTemperature() + "\u00b0" + "C");
        tempDescriptionLabel.setText(weatherData.getWeatherDesc());
        humidityLabel.setText("Humidity:       " + weatherData.getHumidity() + "%");
        minTempLabel.setText("Low:      " + weatherData.getTemp_min() + "\u00b0" + "C");
        maxTempLabel.setText("High:      " + weatherData.getTemp_max() + "\u00b0" + "C");
        windspeedLabel.setText("Wind Speed:     " + weatherData.getWindSpeed() + "km");
        cloudLabel.setText("Clouds:        " + weatherData.getClouds());
        String image;
        boolean isDay = Boolean.parseBoolean((weatherData.getDayOrNight()));
        System.out.println("Day:" + isDay);
        // conditional values (depends on weatherID). Also depends on day time or night time
        if (weatherData.getDayOrNight().equals("true")) {
            if (weatherData.getWeatherID().startsWith("2")) {
                image = this.getClass().getResource("res/gifs/lightning.gif").toExternalForm();
            } else if (weatherData.getWeatherID().startsWith("3")) {
                image = this.getClass().getResource("res/gifs/drizzle.gif").toExternalForm();
            } else if (weatherData.getWeatherID().startsWith("5")) {
                image = this.getClass().getResource("res/gifs/rain_Day.gif").toExternalForm();
            } else if (weatherData.getWeatherID().startsWith("6")) {
                image = this.getClass().getResource("res/gifs/snowy_Day.gif").toExternalForm();
            } else if (weatherData.getWeatherID().startsWith("7")) {
                image = this.getClass().getResource("res/gifs/cloud_Day.gif").toExternalForm();
            } else if (weatherData.getWeatherID().startsWith("8")) {
                image = this.getClass().getResource("res/gifs/clear_Day.gif").toExternalForm();
            } else {
                image = null;
            }
            backgroundGif.setStyle(" -fx-background-image: url('" + image + "'); " +
                    " -fx-background-size:cover;");
        } else if (weatherData.getDayOrNight().equals("false")) {
            if (weatherData.getWeatherID().startsWith("2")) {
                image = this.getClass().getResource("res/gifs/lightning.gif").toExternalForm();
            } else if (weatherData.getWeatherID().startsWith("3")) {
                image = this.getClass().getResource("res/gifs/drizzle.gif").toExternalForm();
            } else if (weatherData.getWeatherID().startsWith("5")) {
                image = this.getClass().getResource("res/gifs/rain_Night.gif").toExternalForm();
            } else if (weatherData.getWeatherID().startsWith("6")) {
                image = this.getClass().getResource("res/gifs/snowy_Night.gif").toExternalForm();
            } else if (weatherData.getWeatherID().startsWith("7")) {
                image = this.getClass().getResource("res/gifs/cloud_Night.gif").toExternalForm();
            } else if (weatherData.getWeatherID().startsWith("8")) {
                image = this.getClass().getResource("res/gifs/clear_Night.gif").toExternalForm();
            }else{
                image = null;
            }
            backgroundGif.setStyle(" -fx-background-image: url('" + image + "'); " +
                    " -fx-background-size:cover;");
        }
    }
        // Will send the new city to the server and update the values in the UI
        public void cityConfirm (ActionEvent event){
            try {
                Socket clientSocket = new Socket("localhost", 1521);
                System.out.println("Connecting to server");
                PrintStream clientPrint = new PrintStream(clientSocket.getOutputStream());
                InputStreamReader ir = new InputStreamReader(clientSocket.getInputStream());
                BufferedReader br = new BufferedReader(ir);
                clientPrint.println(cityMenu.getValue().toString());

                String weatherID = br.readLine();
                System.out.println("Weather ID: " + weatherID);
                // main weather description
                String weatherMainDesc = br.readLine();
                System.out.println("Main description: " + weatherMainDesc);
                // weather description
                String weatherDesc = br.readLine();
                System.out.println("Description: " + weatherDesc);
                // Temperature
                String temperature = br.readLine();
                System.out.println("Temperature: " + temperature + " celsius");
                // Humidity
                String humidity = br.readLine();
                System.out.println("Humidity: " + humidity + "%");
                // Min temp
                String temp_min = br.readLine();
                System.out.println("Min Temp: " + temp_min + " celsius");
                // Max temp
                String temp_max = br.readLine();
                System.out.println("Max Temp: " + temp_max + " celsius");
                // Wind speed
                String windSpeed = br.readLine();
                System.out.println("Wind speed: " + windSpeed + " km");
                // Country code
                String countryCode = br.readLine();
                System.out.println("Country: " + countryCode);
                // Clouds
                String clouds = br.readLine();
                System.out.println("Clouds: " + clouds + "%");
                // City name
                String cityName = br.readLine();
                System.out.println("City name: " + cityName);
                // Day or night
                String dayOrNight = br.readLine();
                System.out.println(dayOrNight);
                // Add the weather data into an instance of the weather class
                weather weatherData = new weather(cityName, countryCode, weatherID, weatherMainDesc, weatherDesc, temperature, humidity, temp_min, temp_max, clouds + "%", windSpeed, dayOrNight);

                // Update the new values
                setWeather(weatherData);
                initialize();

                // close socket
                clientSocket.close();
            } catch (IOException e) {
                System.err.println("ERROR unable to switch cities");
                e.printStackTrace();
            }
        }

    // Saves the weather details to a txt document. Starts with a file chooser then the user can select the directory for where to save the file to
    public void saveButton(ActionEvent event){
        System.out.println("Save button clicked");
        int counter = 1;
        DateFormat date = new SimpleDateFormat("EE/dd/MMM/" + "20" + "yy"); // EE gives the day of the week, dd gives the date, MMM gives the month name, yy gives the year
        DateFormat time = new SimpleDateFormat("HH:mm:aa"); // HH gives the hour, mm gives the minutes, aa gives AM or PM
        Date dateobj = new Date();

        // Start the directory chooser
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File("."));
        File dir = directoryChooser.showDialog(null);
        String fileName = "weatherDetails" + counter;
        File file = new File(dir.toString() + "/" + fileName+".txt");
        try{
            // Write to the file
            PrintWriter writer = new PrintWriter(new FileOutputStream(file.toString()));
            writer.println("Date: " + date.format(dateobj));
            writer.println("Time : " + time.format(dateobj));
            writer.println("City: " + weatherData.getCityName());
            writer.println("Country: " +weatherData.getCountryCode());
            writer.println("Weather: " + weatherData.getWeatherDesc());
            writer.println("Temperature: " +weatherData.getTemperature() + " \u00b0" + "C");
            writer.println("Humidity: " + weatherData.getHumidity() + "%");
            writer.println("Low: " + weatherData.getTemp_min()+ "\u00b0" + "C");
            writer.println("High: " + weatherData.getTemp_max() + "\u00b0" + "C");
            writer.println("Clouds: " + weatherData.getClouds() + "\u00b0" + "C");
            writer.println("Wind Speed: " + weatherData.getWindSpeed()+ "km");
            writer.close();
        }catch (IOException ex){
            System.err.println("ERROR unable to save file ");
            ex.printStackTrace();
        }
        counter ++;
        System.out.println("Save complete");
    }
    // Changes to widget mode
    public void widgetButton(ActionEvent event) throws IOException{
        System.out.println("Entering widget mode!");
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("res/widget.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        }catch (Exception e){
            e.printStackTrace();
        }
        // Parent widgeMode = FXMLLoader.load(getClass().getResource("widget.fxml"));
        // Scene widgetMode = newScene(widgetMode);
        // Stage widgetStage = (Stage) ((Node))

    }
    public void darkButton(ActionEvent event) throws IOException{
        if (counter % 2 == 0){
            System.out.println("Dark mode activated!");
            mainAnchorPane.setStyle("-fx-background-color: #1c1c1c;");
            tabPane.setStyle("-fx-background-color: #1c1c1c;");
        }else{
            System.out.println("Light mode activated!");
            mainAnchorPane.setStyle("-fx-background-color: #FFFFFF;");
            tabPane.setStyle("-fx-background-color: #d3d3d3;");
        }
        counter ++;
    }
    public String sendToWidget(){
        return null;    
    }
    // Changes to home mode
    public void homeButton(ActionEvent event){
        System.out.println("Returning to home mode!");
    }

    // This function is called inside of the Main.java, which passes all the data onto the controller's instance of the weather class
    public static void setWeather(weather mainWeather){
        weatherData = mainWeather;
    }
    // returns the time in EST
    public long getESTHour(long currentHoursGMT){
        long currentHoursEST = currentHoursGMT-4;
        if (currentHoursEST < 0) {
            long difference = 4 - currentHoursGMT;
            currentHoursEST = 24 - difference;
            if (currentHoursEST == 24) {
                currentHoursEST = 0;
            }
        }
        return currentHoursEST;
    }
    // Returns the time in Moscow, Russia
    public long getMSKHour(long currentHoursGMT){
        long currentHoursMSK = currentHoursGMT +3;
        if (currentHoursMSK >= 24){
            long difference = 24 - currentHoursMSK;
            currentHoursMSK = difference;
            if (currentHoursMSK == 24){
                currentHoursMSK = 0;
            }
        }
        return currentHoursMSK;
    }
    // Returns the time in Hong Kong
    public long getHKTHour(long currentHoursGMT){
        long currentHoursHKT = currentHoursGMT + 8;
        if (currentHoursHKT > 24){
            long difference = 24 - currentHoursHKT;
            currentHoursHKT = difference;
            if (currentHoursHKT == 24){
                currentHoursHKT = 0;
            }
        }
        return currentHoursHKT;
    }
    // Returns the time in Northampton, Great Britain
    public long getNGBHour(long currentHoursGMT){
        long currentHoursNGB = currentHoursGMT + 1;
        if (currentHoursNGB > 23){
            long difference = 24 - currentHoursNGB;
            currentHoursNGB = difference;
            if (currentHoursNGB == 24){
                currentHoursNGB = 0;
            }
        }
        return currentHoursNGB;
    }
    // Returns the time in Dubai, Arab Emirates
    public long getDAEHour(long currentHoursGMT){
        long currentHoursDAE = currentHoursGMT + 4;
        if (currentHoursDAE > 23){
            long difference = 24 - currentHoursDAE;
            currentHoursDAE = difference;
            if (currentHoursDAE == 24){
                currentHoursDAE = 0;
            }
        }
        return currentHoursDAE;
    }
    // Returns time in Akita, Japan
    public long getAJPHour(long currentHoursGMT){
        long currentHoursAJP = currentHoursGMT+9;
        if (currentHoursAJP > 24){
            long difference = 24 - currentHoursAJP;
            currentHoursAJP = 13 + difference;
            if (currentHoursAJP == 24){
                currentHoursAJP = 0;
            }
        }
        return currentHoursAJP;
    }
    // Check if the time is in day or in night
    public boolean dayCheck(long time) {
        // From 7 am to 6 pm, the ui will use day time gifs
        if (time >= 7 && time < 18) {
            return true;
        } else {// From 7 pm to 6 am, the ui will use night time gifs
            return false;
        }
    }
}