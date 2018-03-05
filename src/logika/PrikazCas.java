package logika;


/**
 * Třída PrikazCas implementuje pro hru příkaz cas.
 * Tato třída je součástí jednoduché textové hry.
 *
 * @author     Jiri Masek
 * @version    LS 2016/2017
 */
public class PrikazCas implements IPrikaz
{
    private static final String NAZEV = "cas";
    
    private Hodiny hodiny;    
    
    public PrikazCas(Hodiny hodiny)
    {
        this.hodiny = hodiny;
    }         

    /**
     * Provádí příkaz "cas". Vrací aktuální herní čas. 
     *
     * @param     parametry jsou v tomto případě ignorovány 
     * @return    zpráva, kterou vypíše hra hráči
     */     
    public String proved(String... parametry)
    {
        int aktualniHodina = -1;
        
        if (parametry.length > 0)
        {
            return "Příkaz nemá parametry, pouze vypisuje aktuální hodinu.";
        }        
        
        
        if (parametry.length == 0)
        {
            aktualniHodina = hodiny.getAktualniHodina();
            
            if(aktualniHodina == -1)
            {
                return "Něco se pokazilo.";
            }
        }    
        
        return "Už odbila " + aktualniHodina + " hodina.";
    }    
    
    public String getNazev()
	{
	    return NAZEV;
	}  
}
