package logika;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/*******************************************************************************
 * Testovací třída HraTest slouží ke komplexnímu otestování
 * třídy Hra
 *
 * @author     Jarmila Pavlíčková, Jan Říha, Jiří Mašek
 * @version    LS 2016/2017
 */
public class HraTest {
    private Hra hra1; 
    private Hodiny hodiny;
    private Skrys skrys;
    private HerniPlan herniPlan;

    //== Datové atributy (statické i instancí)======================================

    //== Konstruktory a tovární metody =============================================
    //-- Testovací třída vystačí s prázdným implicitním konstruktorem ----------

    //== Příprava a úklid přípravku ================================================

    /***************************************************************************
     * Metoda se provede před spuštěním každé testovací metody. Používá se
     * k vytvoření tzv. přípravku (fixture), což jsou datové atributy (objekty),
     * s nimiž budou testovací metody pracovat.
     */
    @Before
    public void setUp() {
    }

    /***************************************************************************
     * Úklid po testu - tato metoda se spustí po vykonání každé testovací metody.
     */
    @After
    public void tearDown() {
    }

    //== Soukromé metody používané v testovacích metodách ==========================

    //== Vlastní testovací metody ==================================================

    /***************************************************************************
     * Testuje průběh hry, testuje, zda hra končí po zavolání klíčových příkazů
     * a v jaké aktuální místnosti se hráč nachází.
     * Při dalším rozšiřování hry doporučujeme testovat i jaké věci nebo osoby
     * jsou v místnosti a jaké věci jsou v batohu hráče. 
     * Tato testovací sada pracuje s herním časem.
     * 
     */
    @Test
    public void testVyhry() {
        hra1 = new Hra();
        hodiny = hra1.getSpravce().getHodiny();

        assertEquals(false, hra1.konecHry());
        assertEquals("Johnyho_cela", hra1.getHerniPlan().getAktualniLokace().getNazev());
        hra1.zpracujPrikaz("jdi chodba"); 
        assertEquals(false, hra1.konecHry());
        hra1.zpracujPrikaz("jdi kuchyne");      

        while(hodiny.getAktualniHodina() != 12)
        {
            System.out.println(hodiny.getAktualniHodina());
        }

        hra1.zpracujPrikaz("vezmi lzicka");
        assertTrue(hra1.getHerniPlan().getSkrys().jeVeSkrysi("lzicka"));

        while(hodiny.getAktualniHodina() != 21)
        {
            System.out.println(hodiny.getAktualniHodina());
        }

        hra1.zpracujPrikaz("jdi stanoviste_dozorcu");
        assertEquals(false, hra1.konecHry());
        hra1.zpracujPrikaz("vezmi klic");
        assertTrue(hra1.getHerniPlan().getSkrys().jeVeSkrysi("klic"));
        hra1.zpracujPrikaz("jdi dvur");
        hra1.zpracujPrikaz("jdi chodba");        
        hra1.zpracujPrikaz("jdi Johnyho_cela");
        hra1.zpracujPrikaz("pouzij lzicka zed");
        hra1.zpracujPrikaz("jdi sachta_za_zdi");
        hra1.zpracujPrikaz("jdi mistnost_s_poklopem");
        hra1.zpracujPrikaz("pouzij klic poklop");
        hra1.zpracujPrikaz("jdi vychod");
        assertEquals(true, hra1.konecHry());  
        
        hra1.zpracujPrikaz("konec");        
    }


    /***************************************************************************
     * Testuje průběh hry, testuje, zda hra končí po zavolání klíčových příkazů.
     * Tato testovací sada pracuje s herním časem.
     * 
     */    
    @Test
    public void testProhry1() {
        hra1 = new Hra();
        hodiny = hra1.getSpravce().getHodiny();

        hra1.zpracujPrikaz("jdi chodba");
        hra1.zpracujPrikaz("jdi dvur");    

        while(hodiny.getAktualniHodina() != 1)
        {
            System.out.println(hodiny.getAktualniHodina());
        }

        hra1.zpracujPrikaz("jdi stanoviste_dozorcu");
        assertTrue(hra1.testujProhru());
        assertEquals(true, hra1.konecHry());
        
        hra1.zpracujPrikaz("konec");        
    }

    /***************************************************************************
     * Testuje průběh hry, testuje, zda hra končí po zavolání klíčových příkazů.
     * Tato testovací sada pracuje s herním časem.
     * 
     */  
    @Test
    public void testProhry2() {
        hra1 = new Hra();
        hodiny = hra1.getSpravce().getHodiny();

        hra1.zpracujPrikaz("jdi chodba");
        hra1.zpracujPrikaz("jdi dvur");    

        while(hodiny.getAktualniHodina() != 21)
        {
            System.out.println(hodiny.getAktualniHodina());
        }

        hra1.zpracujPrikaz("jdi stanoviste_dozorcu");

        while(hodiny.getAktualniHodina() != 1)
        {
            System.out.println(hodiny.getAktualniHodina());
        }

        hra1.zpracujPrikaz("jdi kuchyne");
        assertTrue(hra1.testujProhru());
        assertEquals(true, hra1.konecHry());

        hra1.zpracujPrikaz("konec");
    }   

    /***************************************************************************
     * Testuje průběh hry, testuje, zda hra končí po zavolání klíčových příkazů.
     * Tato testovací sada pracuje s herním časem.
     * 
     */      
    @Test
    public void testProhry3() {
        hra1 = new Hra();
        hodiny = hra1.getSpravce().getHodiny();

        hra1.zpracujPrikaz("jdi chodba");
        hra1.zpracujPrikaz("jdi dvur");    

        while(hodiny.getAktualniHodina() != 21)
        {
            System.out.println(hodiny.getAktualniHodina());
        }

        hra1.zpracujPrikaz("jdi stanoviste_dozorcu");
        hra1.zpracujPrikaz("vezmi klic");
        System.out.println(hra1.getHerniPlan().getAktualniLokace().dlouhyPopis());        
        System.out.println(hra1.getHerniPlan().getAktualniLokace().getNazev());

        while(hodiny.getAktualniHodina() != 2)
        {
            System.out.println(hodiny.getAktualniHodina());
        }

        assertTrue(hra1.testujProhru());
        hra1.zpracujPrikaz("konec");
        assertTrue(hra1.konecHry());
    }          

    /***************************************************************************
     * Testuje skrýš, její maximalní kapacitu a příkazy vezmi a zahod. 
     * 
     */      
    @Test    
    public void testSkrys() {
        hra1 = new Hra();
        herniPlan = hra1.getHerniPlan();
        skrys = herniPlan.getSkrys();           
        Lokace lokace = herniPlan.getAktualniLokace();

        Predmet predmet1 = new Predmet("1", "test");
        Predmet predmet2 = new Predmet("2", "test");
        Predmet predmet3 = new Predmet("3", "test");
        Predmet predmet4 = new Predmet("4", "test");
        Predmet predmet5 = new Predmet("5", "test");
        Predmet predmet6 = new Predmet("6", "test");

        lokace.vlozPredmet(predmet1);
        lokace.vlozPredmet(predmet2);
        lokace.vlozPredmet(predmet3);
        lokace.vlozPredmet(predmet4);
        lokace.vlozPredmet(predmet5);
        lokace.vlozPredmet(predmet6);

        System.out.println(lokace.dlouhyPopis());
        hra1.zpracujPrikaz("vezmi 1");
        assertTrue(skrys.jeVeSkrysi("1"));        
        assertFalse(lokace.obsahujePredmet("1"));  
        
        
        hra1.zpracujPrikaz("vezmi 2");
        hra1.zpracujPrikaz("vezmi 3");
        hra1.zpracujPrikaz("vezmi 4");
        hra1.zpracujPrikaz("vezmi 5");
        hra1.zpracujPrikaz("vezmi 6");
        
        assertFalse(skrys.jeVeSkrysi("6"));        
        assertTrue(lokace.obsahujePredmet("6"));
        
        hra1.zpracujPrikaz("zahod 5");
        assertFalse(skrys.jeVeSkrysi("5"));        
        assertTrue(lokace.obsahujePredmet("5"));
        
        hra1.zpracujPrikaz("zahod vse");
        assertTrue(skrys.vratSeznamPredmetu().isEmpty());
        
        hra1.zpracujPrikaz("konec");
    }              
}
