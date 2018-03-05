/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uiGrafika;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import util.IHra;
import logika.Lokace;
import logika.Postava;
import logika.Predmet;
import util.ObserverZmenyProstoru;

/**Třída nastavuje dolní ovládací panel. 
 * Přidává tlačítkové ovládání do hry. 
 * Grafickou podobou skrýše a aktuální východy z lokace.
 *
 * @author Jiří Mašek
 */
public class BottomPane implements ObserverZmenyProstoru{

    private IHra hra;
    private int hraAktivni;
    private FlowPane dolniFlowPane;
    private GridPane gridPaneOvladani;
    private TextArea centerTextArea;
    private PanelVychodu panelVychodu;
    private PanelSkryse panelSkryse;
    
    private Button prikazHra;
    private Button prikazPauza;    
    private Button prikazJdi;
    private ComboBox<Lokace> comboxPrikazJdi;
    private Button prikazVezmi;
    private ComboBox<Predmet> comboxPrikazVezmi;        
    private Button prikazPouzij;
    private ComboBox<Predmet> comboxPrikazPouzijCo;
    private ComboBox<Predmet> comboxPrikazPouzijNa;
    private Button prikazPromluv;
    private ComboBox<Postava> comboxPrikazPromluv;        
    private Button prikazProzkoumej;
    private ComboBox<String> comboxPrikazProzkoumej;       
    
    private ListChangeListener listenerPredmety;
    private ListChangeListener listenerSkrys;
    private ListChangeListener listenerPostavy;
    
    /**Konstruktor třídy BottomPane.
     *
     * @param hra
     * @param centerTextArea
     * @param hraAktivni
     */
    public BottomPane(IHra hra, TextArea centerTextArea, int hraAktivni) {
        dolniFlowPane = new FlowPane();
        gridPaneOvladani = new GridPane();
        this.hra = hra;
        this.centerTextArea = centerTextArea;
        this.hraAktivni = hraAktivni;
        this.panelVychodu = new PanelVychodu(this.hra);
        this.panelSkryse = new PanelSkryse(this.hra,this.centerTextArea);
        hra.getHerniPlan().zaregistrujPozorovatele(this);
    }

    /**Metoda nastaví dolní panel s ovládaním, seznamem východů a grafickým batohem.
     *
     * @return
     */
    public FlowPane nastavDolniPanel() {

        dolniFlowPane.setPadding(new Insets(10,10,10,10));
        dolniFlowPane.setVgap(5);
        dolniFlowPane.setHgap(20);
        
        nastavPrikazHraj();
        nastavPrikazPauza();
        nastavPrikazJdi();
        nastavPrikazVezmi();
        nastavPrikazPouzij();
        nastavPrikazPromluv();
        nastavPrikazProzkoumej();
        hBoxOvladani();
        nastavPanelVychodu();
        nastavPanelSkryse();
        
        dolniFlowPane.setAlignment(Pos.CENTER_LEFT);
        return dolniFlowPane;
    }
 
    /**Metoda nastaví tlačítko a příkaz do jdi do grafické podoby a připne na něj event handler.
     *
     */    
    private void nastavPrikazJdi(){
        prikazJdi = new Button("Jdi");
        comboxPrikazJdi = new ComboBox();        
        
        prikazJdi.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                
                if(listenerPredmety != null){
                hra.getHerniPlan().getAktualniLokace().getPredmety().removeListener(listenerPredmety);}

                if(listenerPredmety != null){
                hra.getHerniPlan().getAktualniLokace().getPostavy().removeListener(listenerPostavy);}                
                
                String prikaz = "jdi ";
                
                if(!comboxPrikazJdi.getSelectionModel().isEmpty()){
                String parametr = comboxPrikazJdi.getValue().getNazev();
                prikaz = prikaz + parametr;}
                
                String text = hra.zpracujPrikaz(prikaz);
                centerTextArea.appendText("\n" + prikaz + "\n");
                centerTextArea.appendText("\n" + text + "\n");
                
                    hra.getHerniPlan().getAktualniLokace().getPredmety().addListener(listenerPredmety = new ListChangeListener<Predmet>() {
                    @Override
                    public void onChanged(ListChangeListener.Change<? extends Predmet> c) {
                    hra.getHerniPlan().upozorniPozorovatele(); 
                    }});
                    hra.getHerniPlan().getAktualniLokace().getPostavy().addListener(listenerPostavy = new ListChangeListener<Postava>() {
                    @Override
                    public void onChanged(ListChangeListener.Change<? extends Postava> c) {
                    hra.getHerniPlan().upozorniPozorovatele();
                    }});                       
                    
                hra.getHerniPlan().upozorniPozorovatele();
            }
        });
        
        prikazJdi.setVisible(false);
        comboxPrikazJdi.setVisible(false);
               
        gridPaneOvladani.add(prikazJdi,0,1);
        gridPaneOvladani.add(comboxPrikazJdi,1,1);
    }       

    /**Metoda nastaví tlačítko a příkaz do vezmi do grafické podoby a připne na něj event handler.
     *
     */       
    private void nastavPrikazVezmi(){
        prikazVezmi = new Button("Vezmi");
        comboxPrikazVezmi = new ComboBox();
                        
        prikazVezmi.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                String prikaz = "vezmi ";
                String parametr = comboxPrikazVezmi.getValue().getNazev();
                String input = prikaz + parametr;
                String text = hra.zpracujPrikaz(input);
                System.out.print(text);
                centerTextArea.appendText("\n\n" + input + "\n");
                centerTextArea.appendText("\n" + text + "\n");
                hra.getHerniPlan().upozorniPozorovatele();
            }
        });        
            
        prikazVezmi.setVisible(false);
        comboxPrikazVezmi.setVisible(false);
        
        gridPaneOvladani.add(prikazVezmi,2,1);
        gridPaneOvladani.add(comboxPrikazVezmi,3,1);        
    }

    /**Metoda nastaví tlačítko a příkaz do použij do grafické podoby a připne na něj event handler.
     * Dále také připne listener na změny ve skrýši.
     *
     */       
    private void nastavPrikazPouzij(){
        prikazPouzij = new Button("Pouzij");
        comboxPrikazPouzijCo = new ComboBox();
        comboxPrikazPouzijNa = new ComboBox();
        
        hra.getHerniPlan().getSkrys().vratSeznamPredmetu().addListener(listenerSkrys = new ListChangeListener<Predmet>() {
            @Override
            public void onChanged(ListChangeListener.Change<? extends Predmet> c) {
            hra.getHerniPlan().upozorniPozorovatele();
            }});         
        
        prikazPouzij.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                                
                String prikaz = "pouzij ";
                
                if(!comboxPrikazPouzijCo.getSelectionModel().isEmpty() && comboxPrikazPouzijNa.getSelectionModel().isEmpty()){
                String parametr = comboxPrikazPouzijCo.getValue().getNazev();
                prikaz = prikaz + parametr;}
                
                if(comboxPrikazPouzijCo.getSelectionModel().isEmpty() && !comboxPrikazPouzijNa.getSelectionModel().isEmpty()){
                String parametr = comboxPrikazPouzijNa.getValue().getNazev();
                prikaz = prikaz + parametr;}
                
                if((!comboxPrikazPouzijCo.getSelectionModel().isEmpty()) && (!comboxPrikazPouzijNa.getSelectionModel().isEmpty())){
                String parametrCo = comboxPrikazPouzijCo.getValue().getNazev();
                String parametrNa = comboxPrikazPouzijNa.getValue().getNazev();
                prikaz = prikaz + parametrCo + " " + parametrNa;}                
                
                String text = hra.zpracujPrikaz(prikaz);
                centerTextArea.appendText("\n" + prikaz + "\n");
                centerTextArea.appendText("\n" + text + "\n");      
                    
                hra.getHerniPlan().upozorniPozorovatele();
            }
        });
        
        prikazPouzij.setVisible(false);
        comboxPrikazPouzijCo.setVisible(false);
        comboxPrikazPouzijNa.setVisible(false);
        
        //gridPaneOvladani.getChildren().addAll(prikazPouzij,comboxPrikazPouzijCo,comboxPrikazPouzijNa);
        gridPaneOvladani.add(prikazPouzij,0,3);
        gridPaneOvladani.add(comboxPrikazPouzijCo,1,3);
        gridPaneOvladani.add(comboxPrikazPouzijNa,2,3);
    }    

    /**Metoda nastaví tlačítko a příkaz do promluv do grafické podoby a připne na něj event handler.
     *
     */   
    private void nastavPrikazPromluv(){
        prikazPromluv = new Button("Promluv");
        comboxPrikazPromluv = new ComboBox();        
        
        prikazPromluv.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {                
                
                String prikaz = "promluv ";
                
                if(!comboxPrikazPromluv.getSelectionModel().isEmpty()){
                String parametr = comboxPrikazPromluv.getValue().getNazev();
                prikaz = prikaz + parametr;}
                
                String text = hra.zpracujPrikaz(prikaz);
                System.out.print(text);
                centerTextArea.appendText("\n" + prikaz + "\n");
                centerTextArea.appendText("\n" + text + "\n");      
                    
                hra.getHerniPlan().upozorniPozorovatele();
            }
        });
        
        prikazPromluv.setVisible(false);
        comboxPrikazPromluv.setVisible(false);
        gridPaneOvladani.add(prikazPromluv,0,2);
        gridPaneOvladani.add(comboxPrikazPromluv,1,2);        
    }

    /**Metoda nastaví tlačítko a příkaz do prozkoumej do grafické podoby a připne na něj event handler.
     *
     */     
    private void nastavPrikazProzkoumej(){
        prikazProzkoumej = new Button("Prozkoumej");
        comboxPrikazProzkoumej = new ComboBox();        
        
        prikazProzkoumej.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {                
                
                String prikaz = "prozkoumej ";
                
                if(!comboxPrikazProzkoumej.getSelectionModel().isEmpty()){
                String parametr = comboxPrikazProzkoumej.getValue();
                prikaz = prikaz + parametr;}
                
                String text = hra.zpracujPrikaz(prikaz);
                centerTextArea.appendText("\n" + prikaz + "\n");
                centerTextArea.appendText("\n" + text + "\n");      
                    
                hra.getHerniPlan().upozorniPozorovatele();
            }
        });
        
        prikazProzkoumej.setVisible(false);
        comboxPrikazProzkoumej.setVisible(false);
        gridPaneOvladani.add(prikazProzkoumej,2,2);
        gridPaneOvladani.add(comboxPrikazProzkoumej,3,2); 
    }               

    /**Metoda nastaví tlačítko pro pokračovaní/spuštění hry.
     *
     */     
    private void nastavPrikazHraj(){
        prikazHra = new Button("Hra");    
        prikazHra.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                hraAktivni = 1;
                hra.getSpravce().obnovSpravce();
                hra.getHerniPlan().upozorniPozorovatele();
            }
        });
        
        gridPaneOvladani.add(prikazHra,0,0);
    }
    
    /**Metoda nastaví tlačítko pro pauzu ve hře.
     *
     */     
    private void nastavPrikazPauza(){
        prikazPauza = new Button("Pauza");   
        prikazPauza.setVisible(false);
        prikazPauza.setDisable(true);
        
        prikazPauza.setOnAction(new EventHandler<ActionEvent>() {        
            @Override
            public void handle(ActionEvent event) {
                hraAktivni = 0;
                hra.getSpravce().pauzaSpravce();
                hra.getHerniPlan().upozorniPozorovatele();
            }
        });
        
        gridPaneOvladani.add(prikazPauza,1,0);
    }

    /**Metoda připne panel Vychodu na dolní ovládací panel.
     *
     */    
    private void nastavPanelVychodu() {
        dolniFlowPane.getChildren().add(panelVychodu.getList());
        panelVychodu.getList().setVisible(false);        
    }

    /**Metoda připne panel Skrýše na dolní ovládací panel.
     *
     */       
    private void nastavPanelSkryse() {
        dolniFlowPane.getChildren().add(panelSkryse.getFlowPaneSkryse());
        panelSkryse.getFlowPaneSkryse().setVisible(false);
    }    

    /**Nastavuje label ke skrýši.
     *
     */    
    private void hBoxOvladani(){
        dolniFlowPane.getChildren().add(gridPaneOvladani);
        //gridPaneOvladani.setPadding(new Insets(10,10,10,10));
        gridPaneOvladani.setVgap(10);
        gridPaneOvladani.setHgap(5);
        gridPaneOvladani.setPrefWidth(600);
        //gridPaneOvladani.setStyle("-fx-border-color: #00ff00; -fx-border-width: 3px;");
    }   

    /**
     *
     * @return
     */
    public PanelVychodu getPanelVychodu() {
        return panelVychodu;
    }

    /**
     *
     * @return
     */
    public PanelSkryse getPanelSkryse() {
        return panelSkryse;
    }    
    
    /**
     *
     * @param hraAktivni
     */
    public void setHraAktivni(int hraAktivni) {
        this.hraAktivni = hraAktivni;
    }    

    @Override
    public void aktualizuj() {

        if(hraAktivni == 0){            
            prikazHra.setDisable(false);
            prikazPauza.setDisable(true);
            prikazJdi.setVisible(false);
            comboxPrikazJdi.setVisible(false);
            prikazVezmi.setVisible(false);
            comboxPrikazVezmi.setVisible(false);
            prikazPouzij.setVisible(false);            
            comboxPrikazPouzijCo.setVisible(false);
            comboxPrikazPouzijNa.setVisible(false);
            prikazPromluv.setVisible(false);
            comboxPrikazPromluv.setVisible(false);
            prikazProzkoumej.setVisible(false);
            comboxPrikazProzkoumej.setVisible(false);
            panelVychodu.getList().setVisible(false);
            panelSkryse.getFlowPaneSkryse().setVisible(false);
        }        

        if(hraAktivni == 1){
        
        hraAktivni = 2;
        
        prikazHra.setDisable(true);
        prikazPauza.setDisable(false);
        prikazPauza.setVisible(true);        
        prikazJdi.setVisible(true);
        comboxPrikazJdi.setVisible(true);        
        prikazVezmi.setVisible(true);
        comboxPrikazVezmi.setVisible(true);   
        prikazPouzij.setVisible(true);
        comboxPrikazPouzijCo.setVisible(true);
        comboxPrikazPouzijNa.setVisible(true); 
        prikazPromluv.setVisible(true);
        comboxPrikazPromluv.setVisible(true);
        prikazProzkoumej.setVisible(true);
        comboxPrikazProzkoumej.setVisible(true);  
        panelVychodu.getList().setVisible(true);
        panelSkryse.getFlowPaneSkryse().setVisible(true);        
        }

        if(hraAktivni == 2){
                    
        ObservableList<String> observableListProzkoumej = FXCollections.observableList(new ArrayList<>());

        for (Predmet predmet : hra.getHerniPlan().getAktualniLokace().getPredmety()) {            
            observableListProzkoumej.add(predmet.getNazev());
        }

        for (Postava postavy : hra.getHerniPlan().getAktualniLokace().getPostavy()) {            
            observableListProzkoumej.add(postavy.getNazev());
        }
        
        for (Predmet predmet : hra.getHerniPlan().getSkrys().vratSeznamPredmetu()) {            
            observableListProzkoumej.add(predmet.getNazev());
        }        
        
        comboxPrikazJdi.setItems(hra.getHerniPlan().getAktualniLokace().getVychody());
        comboxPrikazVezmi.setItems(hra.getHerniPlan().getAktualniLokace().getPredmety());
        comboxPrikazPouzijCo.setItems(hra.getHerniPlan().getSkrys().vratSeznamPredmetu());
        comboxPrikazPouzijNa.setItems(hra.getHerniPlan().getAktualniLokace().getPredmety());
        comboxPrikazPromluv.setItems(hra.getHerniPlan().getAktualniLokace().getPostavy());
        comboxPrikazProzkoumej.setItems(observableListProzkoumej);           
        }               
    }
    
    /**Metoda sloužící pro restart hry. Nastaví nový herní plán 
     *
     * @param hra
     */
    public void nastaveniHernihoPlanu (IHra hra){
        this.hra = hra;
        hra.getHerniPlan().zaregistrujPozorovatele(this);
        this.aktualizuj();
    }    
}