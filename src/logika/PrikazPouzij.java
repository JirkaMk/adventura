package logika;

import java.util.*;

/**
 * Třída představuje implementaci příkazu pouzij.
 * 
 * @author     Jiří Mašek
 * @version    LS 2016/2017
 */
public class PrikazPouzij implements IPrikaz
{
    private static final String NAZEV = "pouzij";
    
    private HerniPlan hPlan;
    
    public PrikazPouzij(HerniPlan hPlan)
    {
        this.hPlan = hPlan;
    }        
    
    /**
     * Metoda představuje zpracování příkazu pouzij.
     * Nejprve zkontroluje, zda byly zadány právě dva názvy předmětů jako
     * parametr, ověří, zda v aktuální lokaci nebo ve skrýši je předmět
     * s kterým chceme pracovat a dále se podívá do skrýše, jestli v něm
     * je potřebný nástroj. Poté zkontroluje vazbu mezi předměty, jestli
     * jsou vzájemně použitelné a nakonec nastaví předmět jako použitý. 
     * 
     * @param parametry pole parametrů zadaných na příkazové řádce
     * @return výsledek zpracování, tj. text, který se vypíše na konzoli
     */    
    public String proved(String... parametry)
    {
        Skrys skrys = hPlan.getSkrys();
        Lokace aktLokace = hPlan.getAktualniLokace();
        
        if (parametry.length < 2)
        {
            return "Musíš zadat jméno nástroje ze skrýše, který chceš vybrat a na jaký předmět ho chceš použít.";
        }        

        if (parametry.length > 2)
        {
            return "Tomu nerozumím, zadej dva parametry, jméno nástroje ze skrýše, který chceš vybrat a na jaký předmět ho chceš použít.";
        }
        
        String nazevNastroje = parametry[0]; 
        String nazevPredmetu = parametry[1];   

        if ((!skrys.jeVeSkrysi(nazevNastroje)) || ((!skrys.jeVeSkrysi(nazevPredmetu)) && (!aktLokace.obsahujePredmet(nazevPredmetu))))
        {
            return "Takové předměty buď nemáš ve skrýši nebo nejsou v lokaci.";
        }
        
        Predmet predmet;
        if (skrys.jeVeSkrysi(nazevPredmetu)) 
        {
            predmet = skrys.oznacPredmet(nazevPredmetu);
        }
        else
        {
            predmet = aktLokace.najdiPredmet(nazevPredmetu);
        }       
        
        if (!predmet.jePouzitelnyPredmetem(nazevNastroje))
        {
            return  Character.toUpperCase(nazevNastroje.charAt(0)) + nazevNastroje.substring(1) + " nemůžeš použít na " + nazevPredmetu + 
            ". Nezapomeň, že nejdřív musíš zadat co chceš použít a pak na jaký předmět.";
        }
        
        if (!predmet.isPouzitelny())
        {
            return "S tímhle(" + nazevPredmetu + ") už jsi něco dělal.";
        }
        
        predmet.setPouzitelny(false); 
        
        return "Úspěšně si použil " + nazevNastroje + ".";
    }    
    
    
    public String getNazev()
	{
	    return NAZEV;
	}      
}
