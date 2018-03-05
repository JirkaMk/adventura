/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package logika;

/**
 * Třída PrikazProzkoumej implementuje pro hru příkaz prozkoumej.
 * Tato třída je součástí jednoduché textové hry.
 *
 * @author     Jan Riha, Jiri Masek
 * @version    LS 2016/2017
 */
public class PrikazProzkoumej implements IPrikaz
{
    private static final String NAZEV = "prozkoumej";
    
    private HerniPlan hPlan;
    
   /**
    * Konstruktor třídy
    *
    * @param    plan herní plán, ve kterém se bude ve hře "chodit" 
    */  
    public PrikazProzkoumej(HerniPlan hPlan)
    {
        this.hPlan = hPlan;
    }

    /**
     * Provádí příkaz "prozkoumej". Pokud nebyl zadán žádný parametr, vrátí kompletní
     * popis aktuální lokace. Pokud byl zadán jeden parametr, pokusí se v aktuální lokaci
     * a následně ve skrýši najít předmět nebo postavu s daným názvem a vrátit popis. Pokud bylo
     * zadáno více parametrů, vrátí chybové hlášení.
     *
     * @param     parametry může jako parametr obsahovat název předmětu nebo postavy, který chce 
     *            hráč prozkoumat
     * @return    zpráva, kterou vypíše hra hráči
     */ 
    @Override
    public String proved(String... parametry)
    {
        if (parametry.length > 1)
        {
            return "Tomu nerozumím, nemůžeš prozkoumat více předmětů najednou";
        }
        
        Lokace aktLokace = hPlan.getAktualniLokace();
        
        if (parametry.length > 0)
        {
            // Vypis popis predmetu
            String nazevObjektu = parametry[0];
            
            if (aktLokace.obsahujePostavu(nazevObjektu))
            {
                return aktLokace.najdiPostavu(nazevObjektu).getPopis();
            }

            if (aktLokace.obsahujePredmet(nazevObjektu))
            {
                return aktLokace.najdiPredmet(nazevObjektu).getPopis();
            }
            
            if (hPlan.getSkrys().jeVeSkrysi(nazevObjektu))
            { 
                 return hPlan.getSkrys().oznacPredmet(nazevObjektu).getPopis(); 
            }
            
            
            
            

            return "Postava nebo předmět " + nazevObjektu + " není v aktuální lokaci ani ve skrýši";
        }
        else
        {
            // Zobraz popis aktualni lokace
            return aktLokace.dlouhyPopis();
        }
    }

    /**
     * Metoda vrací název příkazu (slovo které používá hráč pro jeho vyvolání).
     *
     * @return    název příkazu
     */
    @Override
	public String getNazev()
	{
	    return NAZEV;
	}
}
