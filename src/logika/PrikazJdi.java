/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package logika;

/**
 * Třída PrikazJdi implementuje pro hru příkaz jdi.
 * Tato třída je součástí jednoduché textové hry.
 *
 * @author     Jarmila Pavlickova, Lubos Pavlicek, Jan Riha, Jiri Masek
 * @version    LS 2016/2017
 */
class PrikazJdi implements IPrikaz {
    private static final String NAZEV = "jdi";
    private Hra hra;
    private HerniPlan plan;

    /**
     * Konstruktor třídy
     *
     * @param    plan herní plán, ve kterém se bude ve hře "chodit" 
     */    
    public PrikazJdi(Hra hra) {
        this.hra = hra;
        plan = hra.getHerniPlan();
    }

    /**
     * Provádí příkaz "jdi". Zkouší se vyjít do zadané lokace. Pokud lokace
     * existuje a není zamčená, vstoupí se do nového lokace. Pokud zadaná sousední lokace
     * (východ) není, vypíše se chybové hlášení. Zároveň se testuje úkol získej klíče.
     *
     * @param     parametry jako parametr obsahuje jméno lokace (východu), do kterého se má jít.
     * @return    zpráva, kterou vypíše hra hráči
     */ 
    @Override
    public String proved(String... parametry) {
        if (parametry.length == 0) {
            // pokud chybí druhé slovo (sousední lokace), tak ....
            return "Kam mám jít? Musíš zadat jméno východu";
        }

        String smer = parametry[0];
        String sachtaZaZdi = "sachta_za_zdi";
        String vychod = "vychod";

        // zkoušíme přejít do sousední lokace
        Lokace sousedniLokace = plan.getAktualniLokace().vratSousedniLokaci(smer);

        if (sousedniLokace == null) {
            return "Tam se odsud jít nedá!";
        }

        if (sousedniLokace.getZamcena())
        {
            if (sousedniLokace.odemkniLokaci())
            {
                plan.setAktualniLokace(sousedniLokace);
                return "Úspěšně se ti odemkla lokace " + sousedniLokace.getNazev() + ".\n" + sousedniLokace.dlouhyPopis();
            }

            if (sousedniLokace.getNazev().equals(sachtaZaZdi))
            {
                return "Šachta je za zdí. Musíš tu zeď nějak pořešit";                               
            }

            if (sousedniLokace.getNazev().equals(vychod))
            {
                return "Poklop je zamčený"; 
            }    
            
            return "Místnost je zamčená";
        }

        hra.getUkoly().ziskejKlice(sousedniLokace);

        plan.setAktualniLokace(sousedniLokace);
        plan.setAktualniSeznamPredmetu(plan.getAktualniLokace().getPredmety());
        return sousedniLokace.dlouhyPopis();
    }

    /**
     * Metoda vrací název příkazu (slovo které používá hráč pro jeho vyvolání).
     *
     * @return    název příkazu
     */
    @Override
    public String getNazev() {
        return NAZEV;
    }

}
