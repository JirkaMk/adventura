/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package logika;

import util.IHra;

/**
 * Třída Hra - třída představující logiku adventury.
 * 
 * Toto je hlavní třída logiky aplikace. Třída vytváří instanci třídy HerniPlan, která inicializuje
 * mistnosti hry a vytváří seznam platných příkazů a instance tříd provádějící jednotlivé příkazy.
 * Dále incializuje správce herního času a vývoje, pak také dialogy postav a úkoly, které plní hlavní 
 * postava. Vypisuje uvítací a ukončovací text hry. Také vyhodnocuje jednotlivé příkazy zadané uživatelem.
 *
 * @author     Michael Kolling, Lubos Pavlicek, Jarmila Pavlickova, Jan Riha, Jiri Masek
 * @version    LS 2016/2017
 */
public class Hra implements IHra {
    private RealTimeSpravce spravce;
    private SeznamPrikazu platnePrikazy;  
    private HerniPlan herniPlan;
    private boolean prohra = false;
    private boolean konecHry = false;
    private Dialogy dialogy;
    private Ukoly ukoly;
    
    private static final String vyhraText = "Gratuluji. Úspěšně si Johnyho vyvedl z lapáku.\n";
    private static final String prohraText = "Bohužel jsi byl chycen při pokusu o útěk.\n";    

    /**
     * Vytváří hru a inicializuje místnosti (prostřednictvím třídy HerniPlan),
     * inicializuje herního správce pomocí třídy RealTimeSpravce, dále také
     * dialogy, úkoly a seznam platných příkazů.
     */
    public Hra() {
        herniPlan = new HerniPlan();
        platnePrikazy = new SeznamPrikazu();
        spravce = new RealTimeSpravce(this);
        ukoly = new Ukoly(this);
        dialogy = new Dialogy(this);
        
        platnePrikazy.vlozPrikaz(new PrikazNapoveda(platnePrikazy));
        platnePrikazy.vlozPrikaz(new PrikazJdi(this));
        platnePrikazy.vlozPrikaz(new PrikazKonec(this));
        platnePrikazy.vlozPrikaz(new PrikazVezmi(this));
        platnePrikazy.vlozPrikaz(new PrikazZahod(herniPlan));        
        platnePrikazy.vlozPrikaz(new PrikazProzkoumej(herniPlan));
        platnePrikazy.vlozPrikaz(new PrikazSkrys(herniPlan));
        platnePrikazy.vlozPrikaz(new PrikazCas(spravce.getHodiny()));
        platnePrikazy.vlozPrikaz(new PrikazPouzij(herniPlan));  
        platnePrikazy.vlozPrikaz(new PrikazPromluv(this)); 
    }

    /**
     * Vrátí úvodní zprávu pro hráče.
     */
     public String vratUvitani() {
        return "Vítejte!\n" +
               "Na Johnyho Mlátičku ušili jeho bývalý spolupracovníci boudu a byl neprávem\n" + 
               "odsouzen za vraždu. Nyní se nachází ve vězení s maximální ostrahou za vraždu\n" +
               "prvního stupně a odpykává si doživotní trest. Johny se rozhodl, že to jen tak\n" +
               "nenechá a chce se svým bývalým kolegům pomstít.\n" +
               "Napište 'napoveda', pokud si nevíte rady, jak hrát dál.\n" +
               "\n" +
               herniPlan.getAktualniLokace().dlouhyPopis();
    }
    
    /**
     * Vrátí závěrečnou zprávu pro hráče.
     */
     public String vratEpilog() {
        return "Dík, že jste si zahráli.  Ahoj.";
    }
    
    /** 
     * Vrací true, pokud hra skončila.
     */
     public boolean konecHry() {
        return konecHry;
    }
    
    /**
     * Metoda zpracuje řetězec uvedený jako parametr, rozdělí ho na slovo příkazu a další parametry.
     * Pak otestuje zda příkaz je klíčovým slovem  např. jdi.
     * Pokud ano spustí samotné provádění příkazu.
     *
     * @param    radek  text, který zadal uživatel jako příkaz do hry.
     * @return   vrací se řetězec, který se má vypsat na obrazovku
     */
     public String zpracujPrikaz(String radek) {
        String [] slova = radek.split("[ \t]+");
        String slovoPrikazu = slova[0];
        String []parametry = new String[slova.length-1];
        for(int i=0 ;i<parametry.length;i++){
           	parametry[i]= slova[i+1];  	
        }
        String textKVypsani=" ... ";
        if (platnePrikazy.jePlatnyPrikaz(slovoPrikazu)) {
            IPrikaz prikaz = platnePrikazy.vratPrikaz(slovoPrikazu);
            textKVypsani = prikaz.proved(parametry);
            
            if (testujVyhru())
            {
                textKVypsani = vyhraText;
                setKonecHry(true);
            }
            
            if (testujProhru())
            {  
                textKVypsani = prohraText;
                setKonecHry(true);
            }
        }
        else {
            textKVypsani="Nevím co tím myslíš? Tento příkaz neznám. ";
        }
        return textKVypsani;
    }
    
    
    /**
     * Nastaví, že je konec hry, metodu využívá třída PrikazKonec,
     * mohou ji použít i další implementace rozhraní Prikaz.
     *  
     * @param    konecHry hodnota false = konec hry, true = hra pokračuje
     */
     public void setKonecHry(boolean konecHry) {
        spravce.zastavSpravce();
        this.konecHry = konecHry;
    }

    /**
     * Metoda vrátí odkaz na herní plán, je využita hlavně v testech,
     * kde se jejím prostřednictvím získává aktualní místnost hry.
     *  
     * @return    odkaz na herní plán
     */
     public HerniPlan getHerniPlan(){
        return herniPlan; 
     }
  
    /**
     * Metoda testuje zdali není současná lokace ta výherní.
     *  
     * @return    vrací true pokud je současná lokace výherní
     */     
    public boolean testujVyhru()
    {
        if(herniPlan.getAktualniLokace().getNazev().equals(herniPlan.getNazevVitezneLokace())) 
        {
            return true;
        }
        
        return false;
    }

    /**
     * Metoda testuje prohru. Ponecháno pro případné rozšíření. 
     *  
     * @return    vrací true pokud hráč prohrál
     */             
    public boolean testujProhru()
    {
        return prohra;
    }
    
    /**
     * Metoda nastavuje proměnnou prohra. 
     *  
     * @param    true pokud hráč prohrál; jinak false
     */  
    public void setProhra(boolean prohra)
    {
        this.prohra = prohra;
    }    

    // kvuli testum
    /**
     * Metoda vrací instanci třídy Dialogy. Využito především v testech. 
     *  
     * @return    vrací instanci třídy Dialogy
     */      
    public Dialogy getDialogy() 
    {        
        return dialogy;
    }    


    /**
     * Metoda vrací instanci třídy Ukoly. Využito především v testech. 
     *  
     * @return    vrací instanci třídy Ukoly
     */        
    public Ukoly getUkoly() 
    {        
        return ukoly;
    }        


    /**
     * Metoda vrací instanci třídy RealTimeSpravce. Využito především v testech. 
     *  
     * @return    vrací instanci třídy RealTimeSpravce
     */        
    public RealTimeSpravce getSpravce() 
    {        
        return spravce;
    }
  
}

