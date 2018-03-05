package logika;


/**
 * Třída představuje příkaz pro sebrání předmětu z aktuální lokace
 * a jeho vložení do batohu (inventáře) postavy.
 * 
 * @author     Jan Riha, Jiri Masek
 * @version    LS 2016/2017
 */
public class PrikazVezmi implements IPrikaz
{
    private static final String NAZEV = "vezmi";
    
    private Hra hra;
    private HerniPlan hPlan;
    
    public PrikazVezmi(Hra hra)
    {
        this.hra = hra;
        hPlan = hra.getHerniPlan();
    }
    
    /**
     * Metoda představuje zpracování příkazu pro sebrání předmětu.
     * Nejprve zkontroluje, zda byl zadán právě jeden název jako
     * parametr, ověří, zda v aktuální lokaci je předmět s tímto
     * názvem, zda je přenositelný, jestli se nepokoušíme štípnout
     * klíč s dozorcem v místnosti, jestli už nemáme stejný předmět 
     * u sebe, zda je ve skrýši místo a následně předmět odebere z 
     * lokace a vloží ho do batohu.
     * 
     * @param parametry pole parametrů zadaných na příkazové řádce
     * @return výsledek zpracování, tj. text, který se vypíše na konzoli
     */
    public String proved(String... parametry)
    {        
        if (parametry.length < 1)
        {
            return "Musíš zadat co chceš vzít.";
        }
        
        if (parametry.length > 1)
        {
            return "Nemůžeš sebrat více věcí najednou.";
        }
        
        String nazevPredmetu = parametry[0];
        Lokace aktLokace = hPlan.getAktualniLokace();
        
        if (!aktLokace.obsahujePredmet(nazevPredmetu))
        {
            return "Předmět " + nazevPredmetu + " tady není.";
        }
  
        Predmet predmet = aktLokace.vezmiPredmet(nazevPredmetu);
        if (!predmet.isPrenositelny())
        {
            aktLokace.vlozPredmet(predmet);
            return "Předmět " + nazevPredmetu + " fakt neuneseš.";
        }
        
        Skrys skrys = hPlan.getSkrys();
        
        if(nazevPredmetu.equals("klic") && aktLokace.obsahujePostavu("dozorce"))
        {
            hra.setProhra(true);
            return "Vzal jsi klíč, když se koukal dozorce...";            
        }
        
        if(skrys.jeVeSkrysi(predmet.getNazev()))
        {
            aktLokace.vlozPredmet(predmet);
            return "Předmět " + nazevPredmetu + " už máš ve skrýši. Dvakrát ti je na dvě věci.";
        }
        
        if (skrys.jePlna())
        {
             aktLokace.vlozPredmet(predmet);
             return "Ve skrýši už nemáš volné misto, musíš něco zahodit.";
        }
        
        skrys.vlozPredmet(predmet);

        return "Sebral(a) jsi předmět " + nazevPredmetu + ".";
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
