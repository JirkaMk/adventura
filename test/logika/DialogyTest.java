/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package logika;



import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/*******************************************************************************
 * Testovací třída DialogyTest slouží ke komplexnímu otestování třídy Dialogy 
 *
 * @author    Jiří Mašek
 * @version   LS 2016/2017
 */
public class DialogyTest
{
    //private Hra hra1;  
    //private Dialogy dialogy; 
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
     * Testuje průchodnost třídy Dialogy. 
     */        
    @Test
    public void testHlavniDialog() {
        Hra hra1 = new Hra();
        Dialogy dialogy = hra1.getDialogy(); 
        Lokace chodba = hra1.getHerniPlan().oznacLokaci("chodba");
        hra1.getHerniPlan().setAktualniLokace(chodba);
        
        assertSame("\nJOHNY: Čau Nicku, jak se dneska máš?\n"
            + "DOZORCE NICK: Nečum na mě a mlč.\n"
            + "JOHNY: Příjemnej jako vždycky.\n",dialogy.hlavniDialog("strazny"));
            
        assertSame("\nJOHNY: Na něj už radši mluvit nebudu.",dialogy.hlavniDialog("strazny"));            
        
        hra1.zpracujPrikaz("konec");
    }      
}
