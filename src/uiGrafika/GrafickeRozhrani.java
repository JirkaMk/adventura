/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uiGrafika;

import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import logika.Hra;
import util.IHra;

/**Hlavní třída (startovací) pro grafické rozhraní hry
 *
 * @author Jiří Mašek
 */
public class GrafickeRozhrani extends Application{
    
    private IHra hra;
    private int hraAktivni;
    private BorderPane border;
    private CenterPane centerPane;
    private BottomPane bottomPane;
    private TextArea centerTextArea;
    //OknoProstoru oknoPrstoru;
    //PanelVychodu panelVychodu;
    //private MenuBar menuBar; 
    private VrchniMenu vrchniMenu;

    @Override
    public void start(Stage primaryStage) throws IOException
      {
        //nastavTextArea();
        hraAktivni = 0;  
        hra = new Hra();
        border = new BorderPane();
        centerTextArea = new TextArea();
        vrchniMenu = new VrchniMenu(hra);
        
        hra.getSpravce().pauzaSpravce();
        
        border.setStyle("-fx-background-color: black");
        border.setPrefSize(1325, 600);
        border.setPadding(new Insets(5, 5, 5, 5));
        
        centerPane = new CenterPane(hra, centerTextArea, hraAktivni);
        bottomPane = new BottomPane(hra, centerTextArea, hraAktivni);        
        
        border.setCenter(centerPane.nastavCenterFlowPane());

        border.setBottom(bottomPane.nastavDolniPanel());


        //initMenu();
        VBox vBox = new VBox();
        vBox.getChildren().addAll(vrchniMenu.getMenuBar(), border);

        Scene scene = new Scene(vBox, 1325, 625);
        //scene.getStylesheets().add("zdroje/styleSheet.css");
        scene.getStylesheets().add(getClass().getResource("/zdroje/styleSheet.css").toExternalForm());
        primaryStage.setTitle("Adventura");
        primaryStage.setScene(scene);
        //prikazTextField.requestFocus
        primaryStage.setResizable(false);
        nastavNovouHru();
        primaryStage.show();
      }

    /**Metoda nastavuje tlačítko pro novou hru a logiku spuštění nové hry.
     *
     * @return 
     */     
    public void nastavNovouHru(){
        vrchniMenu.getNovaHra().setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent t)
              {
                hra.getSpravce().zastavSpravce();
                hra = new Hra();
                hra.getSpravce().zastavSpravce();
                centerPane.nastaveniHernihoPlanu(hra);               
                bottomPane.nastaveniHernihoPlanu(hra);
                bottomPane.getPanelSkryse().nastaveniHernihoPlanu(hra);
                bottomPane.getPanelVychodu().nastaveniHernihoPlanu(hra);
                bottomPane.setHraAktivni(0);
                hra.getHerniPlan().upozorniPozorovatele();
                centerTextArea.setText(hra.vratUvitani());
                centerTextArea.requestFocus();
              }
        });        
    }
    
    public static void main(String ... args) throws IOException {
            launch(args);
    }    
}


       

