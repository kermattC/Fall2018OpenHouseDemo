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

public class widgetController {
    private static weather weatherData;
    
    @FXML private Pane backgroundGif;
    @FXML private Label widgetTempLabel;

    public void initialize(){
        // System.out.println("Widget Temperature: " + weatherData.getTemperature());
        // widgetTempLabel.setText(widgetWeatherData.getTemperature());
    }
    public static void setWeather(weather mainWeather){
        weatherData = mainWeather;
    }
}