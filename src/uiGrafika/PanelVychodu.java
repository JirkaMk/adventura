/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uiGrafika;

import javafx.scene.control.ListView;
import logika.Lokace;
import util.IHra;
import util.ObserverZmenyProstoru;

/**Třída přidává do hry panel s aktuálními východy ze hry.
 *
 * @author Jiří Mašek
 */
public class PanelVychodu implements ObserverZmenyProstoru
{
    private IHra hra;
    ListView<Lokace> list;
    //ObservableList<String> seznamAktualnichVychodu;

    /**Kontruktor třídy PanelVychodu...
     *
     * @param hra
     */
    public PanelVychodu(IHra hra) {
        this.hra = hra;
        hra.getHerniPlan().zaregistrujPozorovatele(this);
        nastavPanelVychodu();
    }

     /**
     * Metoda nastavuje grafickou sktrukturu panelu východů.
     * 
     */    
    private void nastavPanelVychodu() {
        list = new ListView<>();
        list.setMaxWidth(200);
        list.setMaxHeight(166);
        list.getStyleClass().add(".list-view");
    }
    
    /**
     *
     * @return
     */
    public ListView<Lokace> getList() {
        return list;
    }
     
     @Override
     public void aktualizuj() {
        list.setItems(hra.getHerniPlan().getAktualniLokace().getVychody());
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
