package logika;
import java.util.*;
import javafx.collections.ObservableList;

/**
 * Třída představuje příkaz pro zahození předmětu ze skrýše 
 * do aktuální lokace.
 * 
 * @author     Jiří Mašek
 * @version    LS 2016/2017
 */
public class PrikazZahod implements IPrikaz
{
    private static final String NAZEV = "zahod";
    private static final String zahodVse = "vse";
    
    private HerniPlan hPlan;
    
    public PrikazZahod(HerniPlan hPlan)
    {
        this.hPlan = hPlan;
    } 
    
    /**
     * Metoda představuje zpracování příkazu pro zahození předmětu.
     * Nejprve zkontroluje, zda byl zadán právě jeden název jako
     * parametr, ověří, zda se v batahou nachází předmět s daným názvem 
     * nebo zdali nejde o zahození všech věcí, do aktuální lokace 
     * následně předmět(y) zahodí.
     * 
     * @param parametry pole parametrů zadaných na příkazové řádce
     * @return výsledek zpracování, tj. text, který se vypíše na konzoli
     */
    public String proved(String... parametry)
    {        
        Lokace aktLokace = hPlan.getAktualniLokace();
        Skrys skrys = hPlan.getSkrys();
        ObservableList<Predmet> seznamPredmetu = skrys.vratSeznamPredmetu();
        
        if (parametry.length < 1)
        {
            return "Nevim, co mam zahodit";
        }
        
        if (parametry.length > 1)
        {
            return "Tomu nerozumim, nedokazu zahodit vice veci najednou";
        }

        String nazevPredmetu = parametry[0];
        
        if (nazevPredmetu.equals(zahodVse))
        {
            for (Predmet p : seznamPredmetu) 
            {
                 aktLokace.vlozPredmet(p);
            }            
            
            seznamPredmetu.clear();
            
            return "Vsechno si zahodil. Skrys je prazdna";
        }        
        
        if (!skrys.jeVeSkrysi(nazevPredmetu))
        {
            return "Predmet " + nazevPredmetu + " nemas u sebe";
        }
        
        if (skrys.jeVeSkrysi(nazevPredmetu))
        {
            Predmet predmet = skrys.oznacPredmet(nazevPredmetu);
            aktLokace.vlozPredmet(predmet);
            skrys.zahodPredmet(nazevPredmetu);
        }

        return "Zahodil(a) jsi predmet " + nazevPredmetu + " do lokace " + aktLokace.getNazev();
    }    
    
	public String getNazev()
	{
	    return NAZEV;
	}    
}
