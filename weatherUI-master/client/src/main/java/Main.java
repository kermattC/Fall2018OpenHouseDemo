import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.*;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            Socket clientSocket = new Socket("localhost", 1521);
            PrintStream clientPrint = new PrintStream(clientSocket.getOutputStream());
            InputStreamReader ir = new InputStreamReader(clientSocket.getInputStream());
            BufferedReader br = new BufferedReader(ir);
            primaryStage.setTitle("Weather stuff");
            Parent root = FXMLLoader.load(getClass().getResource("res/sample.fxml"));
            Scene scene = new Scene(root, 490, 630);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            // open sockets and streams
            Socket clientSocket = new Socket("localhost", 1521);
            PrintStream clientPrint = new PrintStream(clientSocket.getOutputStream());
            InputStreamReader ir = new InputStreamReader(clientSocket.getInputStream());
            BufferedReader br = new BufferedReader(ir);

            clientPrint.println("Oshawa, CA");
            //boolean isUpdated = Boolean.parseBoolean(br.readLine());
            // if (isUpdated == true) {
            // Get weather details from the server
            // id
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
            System.out.println("(Client)Day: " +dayOrNight);
            // Add the weather data into an instance of the weather class
            weather weatherData = new weather(cityName, countryCode, weatherID, weatherMainDesc, weatherDesc, temperature, humidity, temp_min, temp_max, clouds + "%", windSpeed, dayOrNight);
            updateValues(weatherData);
            // close sockets and streams
            clientSocket.close();
            launch(args);
            //maybe make this whole main a serperate function called when you need to update a city
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Updates the data to main controller 
    public static void updateValues(weather weatherData) {
        controller.setWeather(weatherData);
    }
    public static void updateWidget(weather weatherData){
        widgetController.setWeather(weatherData);
    }
}