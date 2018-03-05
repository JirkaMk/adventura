/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package logika;
import java.util.*;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import uiGrafika.GrafickeRozhrani;
import util.ObserverZmenyProstoru;
import util.SubjektZmenyProstoru;

/**
 * Class HerniPlan - třída představující mapu a stav adventury.
 * 
 * Tato třída inicializuje prvky ze kterých se hra skládá:
 * vytváří všechny lokace, propojuje je vzájemně pomocí východů 
 * a pamatuje si aktuální lokaci, ve které se hráč právě nachází.
 * Třída dále incializuje skladiště, kam se odkládají věci, co
 * zrovna nejsou potřeba v herním plánu a také skrýš pro hráče na
 * sbírání věcí. Vytvořené lokace se umisťují do Setu. 
 *
 * @author     Michael Kolling, Lubos Pavlicek, Jarmila Pavlickova, Jan Riha, Jiri Masek
 * @version    LS 2016/2017
 */
public class HerniPlan implements SubjektZmenyProstoru{

    private static final String NAZEV_VITEZNE_LOKACE = "vychod";
    
    private Lokace aktualniLokace;
    private Skrys skrys; 
    public Skladiste skladiste;
    private Set<Lokace> seznamLokaci;
    private List<ObserverZmenyProstoru> seznamPozorovatelu;
    private ObservableList<Predmet> aktualniSeznamPredmetu;
    //private ObservableNumberValue zmenaPredmetu = new SimpleIntegerProperty(0);

    
    /**
     * Konstruktor který vytváří jednotlivé lokace, propojuje je pomocí východů a 
     * incializuje skrýš a skladiště.
     */
    public HerniPlan() {
        skrys = new Skrys();
        skladiste = new Skladiste();        
        seznamLokaci = new HashSet<>();
        zalozLokaceHry();
        seznamPozorovatelu = new ArrayList<>();
    }

    /**
     * Vytváří jednotlivé lokace a propojuje je pomocí východů, vkládá do lokací předměty a postavy.
     * Jako výchozí aktuální lokaci nastaví východ.
     */
    private void zalozLokaceHry() {
        // nahrajem obrázky
        Image obrazekCigaret = new Image(GrafickeRozhrani.class.getResourceAsStream("/zdroje/balicekCigaret.PNG"));
        Image obrazekFotky = new Image(GrafickeRozhrani.class.getResourceAsStream("/zdroje/fotografie.PNG"));
        Image obrazekLzicky = new Image(GrafickeRozhrani.class.getResourceAsStream("/zdroje/lzicka.PNG"));
        Image obrazekPolevky = new Image(GrafickeRozhrani.class.getResourceAsStream("/zdroje/polevka.PNG"));
        Image obrazekKlice = new Image(GrafickeRozhrani.class.getResourceAsStream("/zdroje/klic.PNG"));
        Image obrazekRozpisu = new Image(GrafickeRozhrani.class.getResourceAsStream("/zdroje/rozpis.PNG"));
        
        
        // vytvoříme několik věcí
        Predmet balicekCigaret = new Predmet("cigarety", "Balíček cigaret, vězeňská měna", true, obrazekCigaret);
        Predmet fotografie = new Predmet("fotografie", "Fotka Johnyho starý, aby nezapomněl na co se má těšit", true, obrazekFotky);
        Predmet lzicka = new Predmet("lzicka", "Kovová lžička. Ta by se mohla hodit", true, obrazekLzicky);
        Predmet polevka = new Predmet("polevka", "Tekutá směs čehosi, co někdy bývalo polévkou", true, obrazekPolevky);
        Predmet klic = new Predmet("klic", "Klíče které jsou nejspíš k poklopu", true, obrazekKlice);
        Predmet rozpis = new Predmet("rozpis_sluzeb", "Rozpis vězeňských služeb na stanovišti dozorců. \nPodle ně by na stanovišti neměl nikdo být v noci mezi 8 a půlnocí", true, obrazekRozpisu);
        Predmet stul = new Predmet("stul", "dřevěný starý stůl", false);
        Predmet postel = new Predmet("postel", "Bytelná kovová postel", false);
        Predmet zed = new Predmet("zed", "stará vězeňská zeď", false, null, true,lzicka);
        Predmet poklop = new Predmet("poklop", "zamčený bytelný poklop", false, null, true, klic);
     
         
        // vytvoříme několik postav
        Postava harry = new Postava("Velkej_Harry","Velkej Harry je místní vězenský kápo. \nStačí aby si někdo ukýchl a Harry o tom ví", rozpis, balicekCigaret);
        Postava dozorce = new Postava("dozorce","Obéznější vězeňský dozorce, který rád chodí na koblihy");
        Postava strazny = new Postava("strazny","Nepřilíš vlídný stražný Nick, který mívá hlídku u tvé cely");
  
        // vytvářejí se jednotlivé lokace
        Lokace johnyhoCela = new Lokace("Johnyho_cela","Cela kam zavřeli Johnyho. Tady má Johny svou skrýš", false, null, 277, 32);
        Lokace chodba = new Lokace("chodba","Chodba plná dalších cel, kde jsou spoluvězni", false, null, 277, 170);
        Lokace dvur = new Lokace("dvur","Dvůr, kde se vězni setkávájí. Můžeš si tu najít přátele i nepřátele", false, null, 22, 171);
        Lokace stanovisteDozorcu = new Lokace("stanoviste_dozorcu","Tahle místnost patří dozorcům. Vězni sem nesmí", false, null, 12, 309);
        Lokace kuchyne = new Lokace("kuchyne","Celkem obyčejná smradlává vězeňská chemička", false, null, 271, 309);
        Lokace sachtaZaZdi = new Lokace("sachta_za_zdi","Dlouhá větrací šachta. Když dlouho polezeš, tak se možná někam dostaneš", true, zed, 533, 29);
        Lokace mistnostSPoklopem = new Lokace("mistnost_s_poklopem","Je to tu černější než černá. Povedlo se ti nahmatat poklop, zamčený poklop", false, null, 533, 167);
        Lokace vychod = new Lokace(NAZEV_VITEZNE_LOKACE, "Na poklopu leží tvoje šance uniknout z vězení", true, poklop, 533, 309);
        
        // přiřazují se průchody mezi lokacemi (sousedící lokace)
        johnyhoCela.setVychod(chodba);
        johnyhoCela.setVychod(sachtaZaZdi);
        chodba.setVychod(johnyhoCela);
        chodba.setVychod(dvur);
        chodba.setVychod(kuchyne);
        dvur.setVychod(chodba);
        dvur.setVychod(stanovisteDozorcu);
        stanovisteDozorcu.setVychod(dvur);
        stanovisteDozorcu.setVychod(kuchyne);
        kuchyne.setVychod(stanovisteDozorcu);
        kuchyne.setVychod(chodba);
        sachtaZaZdi.setVychod(johnyhoCela);
        sachtaZaZdi.setVychod(mistnostSPoklopem);
        mistnostSPoklopem.setVychod(sachtaZaZdi);
        mistnostSPoklopem.setVychod(vychod);        
        
        //vlož lokace do seznamu
        seznamLokaci.add(johnyhoCela);
        seznamLokaci.add(chodba);
        seznamLokaci.add(dvur);
        seznamLokaci.add(stanovisteDozorcu);
        seznamLokaci.add(kuchyne);
        seznamLokaci.add(sachtaZaZdi);
        seznamLokaci.add(mistnostSPoklopem);
        seznamLokaci.add(vychod);
        
        // umístíme věci do prostorů
        johnyhoCela.vlozPredmet(balicekCigaret); 
        johnyhoCela.vlozPredmet(fotografie);
        johnyhoCela.vlozPredmet(stul);
        johnyhoCela.vlozPredmet(postel);
        johnyhoCela.vlozPredmet(zed);
        stanovisteDozorcu.vlozPredmet(klic);        
        mistnostSPoklopem.vlozPredmet(poklop);
        
        // umístíme postavy do prostorů
        dvur.vlozPostavu(harry);
        chodba.vlozPostavu(strazny);
        
        // umístíme věcí do skladiště
        skladiste.vlozPredmet(lzicka);
        skladiste.vlozPredmet(polevka);
        
        // umístíme postavy do skladiště
        skladiste.vlozPostavu(dozorce);
        
        // hra začíná v Johnyho cele  
        aktualniLokace = johnyhoCela;       
    }

    /**
     * Metoda vrací odkaz na aktuální lokaci, ve které se hráč právě nachází.
     *
     * @return    aktuální lokace
     */
    
    public Lokace getAktualniLokace() {
        return aktualniLokace;
    }

    /** Metoda vrací skrýš. Především pro testovací účely.
     *
     * @return    skrys
     */    
    public Skrys getSkrys() {
        return skrys;
    }

    /** Metoda vrací skrýš. Především pro testovací účely.
     *
     * @return    skladiste
     */        
    public Skladiste getSkladiste() {
        return skladiste;
    }  

    /**
     * Metoda nastaví aktuální lokaci, používá se nejčastěji při přechodu mezi lokacemi
     *
     * @param    lokace nová aktuální lokace
     */
    public void setAktualniLokace(Lokace lokace) {
       aktualniLokace = lokace;
    }        
    
    /**
     * Metoda vrací informaci název vítězné lokace.
     * 
     * @return   název vítězné lokace
     */
    public String getNazevVitezneLokace() {        
        return NAZEV_VITEZNE_LOKACE;
    }
    
    public ObservableList<Predmet> getAktualniSeznamPredmetu() {
        return aktualniSeznamPredmetu;
    }

    public void setAktualniSeznamPredmetu(ObservableList<Predmet> aktualniSeznamPredmetu) {
        this.aktualniSeznamPredmetu = aktualniSeznamPredmetu;
    }    

    /**
     * Metoda vrací informaci zda je lokace obsažena v Setu všech herních lokací.
     * 
     * @param    nazev          název lokace (jedno slovo)
     * @return   true pokud je lokace nalazena; jinak false
     */    
    public boolean jeVLokacich(String nazev)
    {
        for (Lokace lokace : seznamLokaci)
        {
            if(lokace.getNazev().equals(nazev))
            {
                return true;
            }       
        }
        
        return false;
    }

    /**
     * Metoda vrací lokaci ze Setu všech herních lokací.
     * 
     * @param    nazev          název lokace (jedno slovo)
     * @return   vrací lokaci, pokud je nalezena; jinak null
     */    
    public Lokace oznacLokaci(String nazev)
    {
        Lokace lokace = null;
        
        for (Lokace l : seznamLokaci)
        {
            if(l.getNazev().equals(nazev))
            {
                lokace = l;
                break;
            }       
        }        
        
        return lokace;
    }


    public boolean vlozLokaci(Lokace lokace)
    {
        return seznamLokaci.add(lokace);
    }


    public boolean odstranLokaci(Lokace lokace)
    {
        return seznamLokaci.remove(lokace);
    }         

    @Override
    public void zaregistrujPozorovatele(ObserverZmenyProstoru pozorovatel) {
        seznamPozorovatelu.add(pozorovatel);
    }

    @Override
    public void odregistrujPozorovatele(ObserverZmenyProstoru pozorovatel) {
        seznamPozorovatelu.remove(pozorovatel);
    }

    @Override
    public void upozorniPozorovatele() {
        for (ObserverZmenyProstoru pozorovatel : seznamPozorovatelu) {
            pozorovatel.aktualizuj();
        }
    }
}
