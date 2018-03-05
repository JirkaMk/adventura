/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uiGrafika;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import logika.Predmet;
import util.IHra;
import util.ObserverZmenyProstoru;

/**Třída, která se stará o grafickou podobu Skrýše.
 *
 * @author Jiří Mašek
 */
public class PanelSkryse implements ObserverZmenyProstoru{

    private IHra hra;
    private FlowPane flowPaneObrazku;
    private FlowPane flowPaneSkryse;
    private HBox hBoxNadpis;
    private Label labelSkrys;
    private String zahod = "zahod ";
    private TextArea centerTextArea;    
    
    private static final String IDLE_IMAGE_STYLE = "-fx-border-color: transparent;";
    private static final String HOVERED_IMAGE_STYLE = "-fx-cursor: hand;";    

    public PanelSkryse(IHra hra, TextArea centerTextArea){
        flowPaneSkryse = new FlowPane();
        flowPaneObrazku = new FlowPane();
        hBoxNadpis = new HBox();
        labelSkrys = new Label("Skrýš");
        this.hra = hra;
        this.centerTextArea = centerTextArea;
        nastavFlowPaneSkryse();
        hra.getHerniPlan().zaregistrujPozorovatele(this);
    }

    /**Metoda davá do jednoho grafického celku label pro skrýš a okno s předměty.
    *
    *  
    */ 
    private void nastavFlowPaneSkryse(){
        hBoxNadpis.setMinWidth(450);
        hBoxNadpis.getChildren().add(labelSkrys);
        hBoxNadpis.setAlignment(Pos.CENTER);
        
        flowPaneObrazku.setMinWidth(450);
        flowPaneObrazku.setMaxWidth(450);
        flowPaneObrazku.setMaxHeight(138);
        flowPaneObrazku.setMinHeight(138);
        flowPaneObrazku.setPadding(new Insets(4, 4, 4, 4));
        flowPaneObrazku.setVgap(4);
        flowPaneObrazku.setHgap(4);
        
        flowPaneObrazku.setStyle("-fx-border-color: #00ff00;-fx-border-width: 3px;");        
        
        flowPaneSkryse.getChildren().addAll(hBoxNadpis, flowPaneObrazku);
    }   
    
    public FlowPane getFlowPaneSkryse() {
        return flowPaneSkryse;
    }    
    
    @Override
    public void aktualizuj() {
        flowPaneObrazku.getChildren().clear();
        
        for (Predmet predmet : hra.getHerniPlan().getSkrys().vratSeznamPredmetu()) {
            Tooltip tooltip = new Tooltip();
                        
            ImageView obrazekImageView = new ImageView(predmet.getObrazekPredmetu());           
            obrazekImageView.setPreserveRatio(true);
            obrazekImageView.setFitHeight(60);
            
            tooltip.setText(predmet.getNazev());
            tooltip.install(obrazekImageView,tooltip);
            
            obrazekImageView.setOnMouseClicked((MouseEvent e) -> {
                    zahod = zahod + predmet.getNazev();
                    String text = hra.zpracujPrikaz(zahod);
                    centerTextArea.appendText("\n" + zahod + "\n");
                    centerTextArea.appendText("\n" + text + "\n");
                    zahod = "zahod ";
                });            
            
            obrazekImageView.setOnMouseEntered(e -> obrazekImageView.setStyle(HOVERED_IMAGE_STYLE));
            obrazekImageView.setOnMouseExited(e -> obrazekImageView.setStyle(IDLE_IMAGE_STYLE));           
            
            flowPaneObrazku.getChildren().add(obrazekImageView);
        }
    }

     /**
     * Metoda zaregistruje pozorovatele k hernímu plánu při spuštění nové hry.
     * @param hra
     */    
    public void nastaveniHernihoPlanu (IHra hra){
        this.hra = hra;
        hra.getHerniPlan().zaregistrujPozorovatele(this);
        this.aktualizuj();
    }        
}
