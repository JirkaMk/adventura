/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package logika;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/*******************************************************************************
 * Testovací třída RealTimeSpravceTest slouží ke komplexnímu otestování 
 * třídy RealTimeSpravce
 *
 * @author    Jiří Mašek
 * @version   LS 2016/2017
 */
public class RealTimeSpravceTest
{
    private Hra hra1; 
    private Hodiny hodiny;

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
     * Testuje aktivni průbeh metody SpravceKuchyne(). 
     */        
    @Test
    public void testSpravceKuchyne() {
        hra1 = new Hra();
        hodiny = hra1.getSpravce().getHodiny();        
        Lokace kuchyne = hra1.getHerniPlan().oznacLokaci("kuchyne");
        String lzicka = "lzicka";
        String polevka = "polevka";

        assertFalse(kuchyne.obsahujePredmet(lzicka));
        assertFalse(kuchyne.obsahujePredmet(polevka));

        while(hodiny.getAktualniHodina() != 12)
        {
            System.out.println(hodiny.getAktualniHodina());
        }

        assertTrue(kuchyne.obsahujePredmet(lzicka));
        assertTrue(kuchyne.obsahujePredmet(polevka));

        while(hodiny.getAktualniHodina() != 15)
        {
            System.out.println(hodiny.getAktualniHodina());
        }

        assertFalse(kuchyne.obsahujePredmet(lzicka));
        assertFalse(kuchyne.obsahujePredmet(polevka));

        hra1.zpracujPrikaz("konec");
    }

    /**
     * Testuje aktivni průbeh metody SpravceStanovisteDozorcu(). 
     */        
    @Test
    public void testSpravceStanovisteDozorcu() {
        hra1 = new Hra();
        hodiny = hra1.getSpravce().getHodiny();        
        Lokace stanovisteDozorcu =  hra1.getHerniPlan().oznacLokaci("stanoviste_dozorcu");
        String dozorce = "dozorce";

        assertFalse(stanovisteDozorcu.obsahujePostavu(dozorce));

        while(hodiny.getAktualniHodina() != 21)
        {
            System.out.println(hodiny.getAktualniHodina());
        }

        assertFalse(stanovisteDozorcu.obsahujePostavu(dozorce));

        while(hodiny.getAktualniHodina() != 1)
        {
            System.out.println(hodiny.getAktualniHodina());
        }

        assertTrue(stanovisteDozorcu.obsahujePostavu(dozorce));     

        hra1.zpracujPrikaz("konec");
    }  

}
