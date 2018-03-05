package logika;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/*******************************************************************************
 * Testovací třída LokaceTest slouží ke komplexnímu otestování
 * třídy Lokace
 *
 * @author     Jarmila Pavlickova, Jan Riha, Jiri Masek
 * @version    LS 2016/2017
 */
public class LokaceTest
{
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

    /**
     * Testuje, zda jsou správně nastaveny průchody mezi prostory hry. Prostory
     * nemusí odpovídat vlastní hře, 
     */
    @Test
    public  void testLzeProjit() {		
        Predmet lzicka = new Predmet("lzicka", "Kovová lžička. Ta by se mohla hodit");
        Predmet zed = new Predmet("zed", "stará vězeňská zeď", false,null,true,lzicka);
        Lokace lokace1 = new Lokace("hala", "vstupní hala budovy VŠE na Jižním městě");
        Lokace lokace2 = new Lokace("sachta_za_zdi","Dlouhá větrací šachta. Když dlouho polezeš, tak se možná někam dostaneš", true, zed);        
        lokace1.setVychod(lokace2);
        lokace2.setVychod(lokace1);        
        
        assertEquals(lokace2, lokace1.vratSousedniLokaci("sachta_za_zdi"));
        assertEquals(null, lokace2.vratSousedniLokaci("pokoj"));
    }

    /**
     * Testuje mapu věcí vázanou k lokaci. 
     */
    @Test
    public void testMapaVeci()
    {
        Lokace lokace1 = new Lokace(null, null);
        Predmet predmet1 = new Predmet("a", "popis a", true);
        Predmet predmet2 = new Predmet("b", "popis b", false, null, true, predmet1);
        lokace1.vlozPredmet(predmet1);
        lokace1.vlozPredmet(predmet2);
        
        assertTrue(lokace1.obsahujePredmet("a"));
        assertSame(predmet1, lokace1.najdiPredmet("a"));
        assertSame(predmet1, lokace1.vezmiPredmet("a"));
        assertTrue(lokace1.obsahujePredmet("b"));
        assertSame(predmet2, lokace1.najdiPredmet("b"));
        assertSame(predmet2, lokace1.vezmiPredmet("b"));
        assertFalse(lokace1.obsahujePredmet("c"));
        assertNull(lokace1.najdiPredmet("c"));
        assertNull(lokace1.vezmiPredmet("c"));
    }
    
    /**
     * Testuje mapu postav vázanou k lokaci. 
     */
    @Test
    public void testMapaPostav()
    {
        Lokace lokace1 = new Lokace(null, null);
        Postava postava1 = new Postava("a", "test");
        Postava postava2 = new Postava("b","test");
        lokace1.vlozPostavu(postava1);
        lokace1.vlozPostavu(postava2);
        
        assertTrue(lokace1.obsahujePostavu("a"));
        assertSame(postava1, lokace1.najdiPostavu("a"));
        assertSame(postava1, lokace1.vezmiPostavu("a"));
        assertTrue(lokace1.obsahujePostavu("b"));
        assertSame(postava2, lokace1.najdiPostavu("b"));
        assertSame(postava2, lokace1.vezmiPostavu("b"));
        assertFalse(lokace1.obsahujePostavu("c"));
        assertNull(lokace1.najdiPostavu("c"));
        assertNull(lokace1.vezmiPostavu("c"));
    } 
    
    /**
     * Testuje zdali zamčení odemčení místnosti. 
     */
    @Test
    public void testJeZamcena()
    {
        Predmet lzicka = new Predmet("lzicka", "Kovová lžička. Ta by se mohla hodit");
        Predmet zed = new Predmet("zed", "stará vězeňská zeď", false, null, true,lzicka);
        Lokace lokace1 = new Lokace("hala", "vstupní hala budovy VŠE na Jižním městě");
        Lokace lokace2 = new Lokace("sachta_za_zdi","Dlouhá větrací šachta. Když dlouho polezeš, tak se možná někam dostaneš", true, zed);  
        
        assertFalse(lokace1.getZamcena());
        assertTrue(lokace2.getZamcena());
        
        assertFalse(lokace2.odemkniLokaci());
        zed.setPouzitelny(false);
        assertTrue(lokace2.odemkniLokaci());
        assertFalse(lokace2.getZamcena());
    }        

}
