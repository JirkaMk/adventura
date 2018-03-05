package logika;
import java.util.concurrent.*;


/**
 * Class Hodiny - třída je základním stavebním prvkem správce, 
 * pasivně implementuje 24 hodinový cyklus.
 *
 * @author     Jiří Mašek
 * @version    LS 2016/2017
 */
public class Hodiny
{
    
    private int[] poleHodin = new int[24]; // zde by se nicmene mozna hodil final, ale prislo mi hezci to takle nevypisovat
    private int hodina = 0;

    public void setHodina(int hodina) {
        this.hodina = hodina;
    }
    
    public Hodiny()  
    {
         incializacePoleHodin();
    }

    private void incializacePoleHodin()
    {
        for(int i = 0; i<24; i++)
        {
            poleHodin[i] = i;
        }
    }
    
    /**
     * Iteruje pole hodin o jeden prvek dopředu, pokud je obsah i prvku 23,
     * tak se index vrátí na začátek pole.
     * 
     */
    public void hodiny()
    {
        if(poleHodin[hodina] == poleHodin[poleHodin.length-1])
        {
            hodina = 0;
        }
        else
        {
            hodina++;
        }
        //System.out.println(hodina);
    }            
    
    /**
     * Vrací aktuální hodinu v cyklu.
     * 
     * @return   aktualniHodina
     */
    public int getAktualniHodina()
    {
        int aktualniHodina = poleHodin[hodina];
        return aktualniHodina;
    }

    /**
     * Resetuje pole hodin.
     * 
     */    
    public void restartHodin()
    {
        hodina = 23;
    }

}

