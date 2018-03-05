/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package logika;



import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


/*******************************************************************************
 * Testovací třída SkladisteTest slouží ke komplexnímu otestování třídy Skladiste 
 *
 * @author    Jiří Mašek
 * @version   LS 2016/2017
 */
public class SkladisteTest
{
    //== KONSTRUKTORY A TOVÁRNÍ METODY =========================================
    //-- Testovací třída vystačí s prázdným implicitním konstruktorem ----------

    /***************************************************************************
     * Inicializace předcházející spuštění každého testu a připravující tzv.
     * přípravek (fixture), což je sada objektů, s nimiž budou testy pracovat.
     */
    @Before
    public void setUp()
    {
    }

    /***************************************************************************
     * Úklid po testu - tato metoda se spustí po vykonání každého testu.
     */
    @After
    public void tearDown()
    {
    }

    //== VLASTNÍ TESTY =========================================================
    //
    //     /********************************************************************
    //      *
    //      */
    //     @Test
    //     public void testXxx()
    //     {
    //     }

    /**
     * Testuje základní funkčnosti Setu OdkladistePredmetu. 
     */        
    @Test
    public void testOdkladistePredmetu()
    {
        Skladiste skladiste = new Skladiste();
        Predmet lzicka = new Predmet("lzicka", "Kovová lžička. Ta by se mohla hodit");
        Predmet balicekCigaret = new Predmet("cigarety", "Balíček cigaret, vězeňská měna");
        Predmet balicekCigaret2 = new Predmet("cigarety", "Balíček cigaret, vězeňská měna");

        assertTrue(skladiste.vlozPredmet(lzicka));
        assertTrue(skladiste.vlozPredmet(balicekCigaret));
        assertFalse(skladiste.vlozPredmet(balicekCigaret));     
        assertFalse(skladiste.vlozPredmet(balicekCigaret2));  

        assertTrue(skladiste.jePredmetVeSkladisti("lzicka"));
        assertTrue(skladiste.jePredmetVeSkladisti("cigarety"));

        assertSame(lzicka, skladiste.oznacPredmet("lzicka"));
        assertSame(balicekCigaret, skladiste.oznacPredmet("cigarety"));

        assertTrue(skladiste.odstranPredmet(balicekCigaret));        
        assertFalse(skladiste.odstranPredmet(balicekCigaret)); 

    }   

    /**
     * Testuje základní funkčnosti Setu OdkladistePostav. 
     */    
    @Test
    public void testOdkladistePostav()
    {
        Skladiste skladiste = new Skladiste();
        Postava dozorce = new Postava("dozorce","Obéznější vězeňský dozorce, který rád chodí na koblihy");
        Postava strazny = new Postava("strazny","Nepřilíš vlídný stražný Nick, který mívá hlídku u tvé cely");
        Postava strazny2 = new Postava("strazny","Nepřilíš vlídný stražný Nick, který mívá hlídku u tvé cely");
        
        assertTrue(skladiste.vlozPostavu(dozorce));
        assertTrue(skladiste.vlozPostavu(strazny));
        assertFalse(skladiste.vlozPostavu(strazny));     
        assertFalse(skladiste.vlozPostavu(strazny2));  

        assertTrue(skladiste.jePostavaVeSkladisti("dozorce"));
        assertTrue(skladiste.jePostavaVeSkladisti("strazny"));

        assertSame(dozorce, skladiste.oznacPostavu("dozorce"));
        assertSame(strazny, skladiste.oznacPostavu("strazny"));

        assertTrue(skladiste.odstranPostavu(strazny));        
        assertFalse(skladiste.odstranPostavu(strazny)); 

    }         
}

