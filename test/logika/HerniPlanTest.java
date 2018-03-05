/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package logika;



import org.junit.After;
import org.junit.Before; 
import org.junit.Test;
import static org.junit.Assert.*;

/*******************************************************************************
 * Testovací třída HerniPlanTest slouží ke komplexnímu otestování třídy HerniPlan 
 *
 * @author    Jiří Mašek
 * @version   LS 2016/2017
 */
public class HerniPlanTest
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
     * Testuje základní funkčnosti Setu všech lokací v herním plánu. 
     */    
    @Test
    public void testSetLokaci()
    {
        HerniPlan herniPlan = new HerniPlan();
        Predmet lzicka = new Predmet("lzicka", "Kovová lžička. Ta by se mohla hodit");
        Predmet zed = new Predmet("zed", "stará vězeňská zeď", false,null,true,lzicka);
        Lokace lokace1 = new Lokace("hala", "vstupní hala budovy VŠE na Jižním městě");
        Lokace lokace2 = new Lokace("sachta_za_zdi2","Dlouhá větrací šachta. Když dlouho polezeš, tak se možná někam dostaneš", true, zed);
        Lokace lokace3 = new Lokace("sachta_za_zdi2","Dlouhá větrací šachta. Když dlouho polezeš, tak se možná někam dostaneš", true, zed);
        
        assertTrue(herniPlan.vlozLokaci(lokace1));
        assertTrue(herniPlan.vlozLokaci(lokace2));
        assertFalse(herniPlan.vlozLokaci(lokace2));     
        assertFalse(herniPlan.vlozLokaci(lokace3));  
        
        assertTrue(herniPlan.jeVLokacich("hala"));
        assertTrue(herniPlan.odstranLokaci(lokace1));
        assertFalse(herniPlan.jeVLokacich("hala"));   

    }     
}
