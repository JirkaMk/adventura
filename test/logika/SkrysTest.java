/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package logika;



import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/*******************************************************************************
 * Testovací třída SkrysTest slouží ke komplexnímu otestování třídy Skrys 
 *
 * @author    Jiří Mašek
 * @version   LS 2016/2017
 */
public class SkrysTest
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
    
    @Test
    public void testSetSkrys()
    {
        Skrys skrys = new Skrys();
        Predmet lzicka = new Predmet("lzicka", "Kovová lžička. Ta by se mohla hodit");
        Predmet balicekCigaret = new Predmet("cigarety", "Balíček cigaret, vězeňská měna");
        Predmet balicekCigaret2 = new Predmet("cigarety", "Balíček cigaret, vězeňská měna");

        
        assertTrue(skrys.vlozPredmet(lzicka));
        assertTrue(skrys.vlozPredmet(balicekCigaret));
        assertFalse(skrys.vlozPredmet(balicekCigaret));     
        assertFalse(skrys.vlozPredmet(balicekCigaret2));  
        
        assertTrue(skrys.jeVeSkrysi("lzicka"));
        assertTrue(skrys.jeVeSkrysi("cigarety"));
        
        assertSame(lzicka, skrys.oznacPredmet("lzicka"));
        assertSame(balicekCigaret, skrys.oznacPredmet("cigarety"));
        
        assertTrue(skrys.zahodPredmet("cigarety"));        
        assertFalse(skrys.zahodPredmet("cigarety")); 
        
    }         
}
