/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uiGrafika;

import javafx.animation.StrokeTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import util.IHra;
import util.ObserverZmenyProstoru;

/**Třída nastavuje okno pro textový výstup hry a také přidává do hry aktivní mapu s hodinami.
 *
 * @author Jiří Mašek
 */
public class CenterPane implements ObserverZmenyProstoru{
    private IHra hra;
    private int hraAktivni;
    private TextArea centerTextArea;
    private FlowPane centerFlowPane;
    private AnchorPane mapaAnchorPane;
    private ImageView obrazekImageView;
    private Rectangle obdelnik;
    private StrokeTransition strokeTransition;
    private Label labelDigiHodiny;
    private ListChangeListener listenerDigiHodiny;
    
    /**
     *
     * @param hra
     * @param centerTextArea
     * @param hraAktivni
     */
    public CenterPane (IHra hra, TextArea centerTextArea, int hraAktivni) {        
        centerFlowPane = new FlowPane();
        mapaAnchorPane = new AnchorPane();
        labelDigiHodiny = new Label();
        this.hra = hra;
        this.centerTextArea = centerTextArea;
        this.hraAktivni = hraAktivni;
        hra.getHerniPlan().zaregistrujPozorovatele(this);
    }

    /**Metoda nastavující textové okno.
     *
     * @return 
     */    
    private TextArea nastavTextArea()
    {
        centerTextArea.setText(hra.vratUvitani());
        centerTextArea.setEditable(false);
        centerTextArea.setPrefHeight(400);
        centerTextArea.setPrefWidth(620);
        centerTextArea.setStyle("-fx-control-inner-background:#000000; -fx-font-family: Consolas; -fx-highlight-fill: #00ff00; -fx-highlight-text-fill: #000000; -fx-text-fill: #00ff00; -fx-border-color: #00ff00; -fx-border-width: 3px;");
        centerTextArea.setWrapText(true);
        return centerTextArea;
    }
 
    /**Metoda přidává do obrázku mapy nastavení pro aktuální pozici a čas.
     *
     * @return 
     */        
    private AnchorPane nastavMapaStackPane(){            
        mapaAnchorPane.getChildren().add(nastavMapu());       
        mapaAnchorPane.getChildren().add(nastavObdelnicek());
        mapaAnchorPane.getChildren().add(nastavDigiHodiny());
                 
        return mapaAnchorPane;
    }
    
    /**Metoda nastavuje obdelník pro aktualní pozici na mapě.
     *
     * @return 
     */     
    private Rectangle nastavObdelnicek() {
        obdelnik = new Rectangle(120, 50);
        obdelnik.setFill(Color.TRANSPARENT);
        obdelnik.setStyle("-fx-stroke: red;-fx-stroke-width: 5;");                             
        AnchorPane.setLeftAnchor(obdelnik, hra.getHerniPlan().getAktualniLokace().getPosLeft());
        AnchorPane.setTopAnchor(obdelnik, hra.getHerniPlan().getAktualniLokace().getPosTop());         
        strokeTransition = new StrokeTransition(Duration.millis(3000), obdelnik,Color.RED, Color.BLUE); //LIME
        strokeTransition.setCycleCount(Timeline.INDEFINITE);
        strokeTransition.setAutoReverse(true);
        
        return obdelnik; 
    }
 
    /**Metoda nastavuje obrázek mapy.
     *
     * @return 
     */     
    private ImageView nastavMapu() {
        Image obrazekImage = new Image(GrafickeRozhrani.class.getResourceAsStream("/zdroje/mapa.PNG"));
        obrazekImageView = new ImageView(obrazekImage);
        obrazekImageView.setPreserveRatio(true);
        obrazekImageView.setFitHeight(400);
        mapaAnchorPane.setStyle("-fx-border-color: #00ff00; -fx-border-width: 3px;");        
        AnchorPane.setTopAnchor(obrazekImageView, 0.0);
        AnchorPane.setLeftAnchor(obrazekImageView, 0.0);
        
        return obrazekImageView;
    }    
    
    /**Metoda nastavuje davá dohromady mapu a textové pole do jednoho grafického celku.
     *
     * @return 
     */ 
    public FlowPane nastavCenterFlowPane(){
        nastavDigiHodiny();
        
        centerFlowPane.getChildren().addAll(nastavTextArea(),nastavMapaStackPane());
        
        return centerFlowPane;
    }
       
    /**Metoda nastaví logiku hodin na mapě. Přidává listener na list historie hodin.
     *
     * @return
     */
    public Label nastavDigiHodiny(){
            AnchorPane.setTopAnchor(labelDigiHodiny, 50.0);
            AnchorPane.setLeftAnchor(labelDigiHodiny, 20.0);
            labelDigiHodiny.setStyle("-fx-border-color: #00ff00;-fx-border-width: 3px; -fx-font-size:60px; -fx-padding: 10 10 20 20;");
            labelDigiHodiny.setAlignment(Pos.CENTER);
            labelDigiHodiny.setVisible(false);
        
            hra.getSpravce().getDigiHodiny().addListener(listenerDigiHodiny = new ListChangeListener<String>() {
            @Override
            public void onChanged(ListChangeListener.Change<? extends String> c) {
            Platform.runLater(new Runnable() { //Pozor neni mozne menit promennou ve vlaknu a z jineho vlakna b
            @Override
            public void run() {
            labelDigiHodiny.setVisible(true);
            labelDigiHodiny.setText(hra.getSpravce().getDigiHodiny().get(hra.getSpravce().getDigiHodiny().size()-1));
            }
            });
            ;}});
            
            return labelDigiHodiny;
    }
    
    /**
     *
     * @return
     */
    public FlowPane getCenterFlowPane() {
        return centerFlowPane;
    }
    
    /**
     *
     * @return
     */
    public TextArea getCenterTextArea() {
        return centerTextArea;
    }  
    
    /**
     *
     * @return
     */
    public Label getLabelDigiHodiny() {
        return labelDigiHodiny;
    }    

    @Override
    public void aktualizuj() {        

        if(strokeTransition != null){
            strokeTransition.stop();
        }        
        double posLeft = hra.getHerniPlan().getAktualniLokace().getPosLeft();
        double posTop = hra.getHerniPlan().getAktualniLokace().getPosTop();          
        
        AnchorPane.setLeftAnchor(obdelnik, posLeft);
        AnchorPane.setTopAnchor(obdelnik, posTop);
        
        strokeTransition.play();
    }
    
     /**
     * Metoda zaregistruje pozorovatele k hernímu plánu při spuštění nové hry.
     * @param hra
     */
    public void nastaveniHernihoPlanu (IHra hra){
        this.hra = hra;
        hra.getHerniPlan().zaregistrujPozorovatele(this);
        nastavDigiHodiny();
        this.aktualizuj();
    }
}


