package logika;

import java.util.*;
import java.util.stream.*;

/**
 * Třída představuje příkaz pro výpis předmětů ve skrýši.
 * 
 * @author     Jiří Mašek
 * @version    LS 2016/2017
 */
public class PrikazSkrys implements IPrikaz
{
    private static final String NAZEV = "skrys";
    
    private HerniPlan hPlan;
    
    public PrikazSkrys(HerniPlan hPlan)
    {
        this.hPlan = hPlan;
    }     

    /**
     * Provádí příkaz "skrys". Vrací aktuální obsah skrýše. 
     *
     * @param     parametry jsou v tomto případě ignorovány 
     * @return    zpráva, kterou vypíše hra hráči
     */
    public String proved(String... parametry)
    {
        Skrys skrys = hPlan.getSkrys();
        
        if (parametry.length > 0)
        {
            return "Prikaz nema parametry, pouze vypisuje co je ve skrysi.";
        }        
        
        if (skrys.vratSeznamPredmetu().size() == 0)
        {
            return "Skrys je zatim prazdna.";
        }     
        
        return "Ve skrysi je: " + 
               skrys.vratSeznamPredmetu().stream()
               .sorted( (p1, p2) -> p1.getNazev()
               .compareTo(p2.getNazev()) ).map( p -> p.getNazev() )
               .collect( Collectors.joining(", ") );         
    }
    
	public String getNazev()
	{
	    return NAZEV;
	}       
}
