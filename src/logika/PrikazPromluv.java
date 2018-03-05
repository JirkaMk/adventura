package logika;



/**
 * Třída představuje implementaci příkazu pro interakci s postavu.
 * 
 * @author     Jiří Mašek
 * @version    LS 2016/2017
 */
public class PrikazPromluv implements IPrikaz
{
    private static final String NAZEV = "promluv";
    
    private Hra hra;
    
    public PrikazPromluv(Hra hra)
    {
        this.hra = hra;
    }
    
    /**
     * Metoda představuje zpracování příkazu pro interakci s postavou.
     * Nejprve zkontroluje, zda byl zadán právě jeden název postavy jako
     * parametr, ověří, zda v aktuální lokaci je postava s tímto
     * jménem a poté zavolá instanci třídy Dialogy se jménem postavy
     * jako parametr.  
     * 
     * @param parametry pole parametrů zadaných na příkazové řádce
     * @return výsledek zpracování, tj. text, který se vypíše na konzoli
     */
    public String proved(String... parametry)
    {        
        String nazevPostavy;
        Dialogy dialogy = hra.getDialogy();
        HerniPlan herniPlan = hra.getHerniPlan();
        
        if (parametry.length == 0)
        {
            return "Musíš zadat osobu se kterou chceš mluvit";
        }
        
        if (parametry.length > 1)
        {
            return "Nemůžeš zahájit rozhovor s více osobami najednou";
        }
        
        nazevPostavy = parametry[0];
        
        if (!herniPlan.getAktualniLokace().obsahujePostavu(nazevPostavy))
        {
            return "Taková postava není v místnosti";
        }

        return dialogy.hlavniDialog(nazevPostavy);
    }
    
    /**
     * Metoda vrací název příkazu (slovo které používá hráč pro jeho vyvolání).
     *
     * @return    název příkazu
     */
	public String getNazev()
	{
	    return NAZEV;
	}
    
}    
