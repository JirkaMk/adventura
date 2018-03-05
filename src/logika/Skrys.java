package logika;

import java.util.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.swing.JOptionPane;

/**
 * Class Skladiste - třída představuje úložný prostor pro hráče. 
 * 
 * Může zde ukladát přenositelné věci. U sebe může mít maximálně 5 věcí najednou. 
 *
 *
 * @author     Jiří Mašek
 * @version    LS 2016/2017
 */
public class Skrys
{
    private static final int MAX_PREDMETU = 5;    // Maximální počet věcí ve skrýši
    private ObservableList<Predmet> seznamPredmetu;          // kolekce předmětů ve skrýši

    public Skrys()
    {
        seznamPredmetu = FXCollections.observableArrayList();  
    }                

    public boolean jePlna()
    {
        return seznamPredmetu.size() >= MAX_PREDMETU;
    }

    /**
     * Metoda vrací informaci zda je předmět obsažen ve Skrýši.
     * 
     * @param    nazev          název předmětu (jedno slovo)
     * @return   true pokud je předmět nalazen; jinak false
     */        
    public boolean jeVeSkrysi(String nazev)
    {
        for (Predmet p : seznamPredmetu)
        {
            if(p.getNazev().equals(nazev))
            {
                return true;
            }       
        }

        return false;
    }

    /**
     * Metoda vrací předmět ze Skrýše.
     * 
     * @param    nazev          název předmětu (jedno slovo)
     * @return   vrací předmět, pokud je nalezen; jinak null
     */        
    public Predmet oznacPredmet(String nazev)
    {
        Predmet predmet = null;

        for (Predmet p : seznamPredmetu)
        {
            if(p.getNazev().equals(nazev))
            {
                predmet = p;
                break;
            }       
        }        

        return predmet;
    } 

    /**
     * Metoda vloží předmět do Skrýše. V případě, že jde o klíč vypíše na panelu upozornění.
     * 
     * @param    predmet          objekt předmět, který chceme vložit 
     * @return   vrací true pokud bylo vloženi úspěšné; jinak false
     */            
    public boolean vlozPredmet(Predmet predmet)
    {
        if(predmet.getNazev().equals("klic"))
        {
            String infoMessage = "Pozor jakmile se vrátí dozorce a klíče nenajde,\n tak se spustí alarm a máš hodinu na to utéct"; 
            String titleBar = "Vezeňský info"; 
            
            JOptionPane.showMessageDialog(null, infoMessage, titleBar, JOptionPane.WARNING_MESSAGE);            
        }

        return seznamPredmetu.add(predmet);
    }


    /**
     * Metoda zahodí předmět ze Skrýše. 
     * 
     * @param    predmet          objekt předmět, který chceme zahodit 
     * @return   vrací true pokud bylo zahození úspěšné; jinak false
     */    
    public boolean zahodPredmet(String nazev)
    {
        Predmet predmet = null;

        for (Predmet p : seznamPredmetu)
        {
            if(p.getNazev().equals(nazev))
            {
                predmet = p;
                break;
            }       
        }            

        return seznamPredmetu.remove(predmet);
    }

    public ObservableList<Predmet> vratSeznamPredmetu()
    {
        return seznamPredmetu; 
    }     
}
