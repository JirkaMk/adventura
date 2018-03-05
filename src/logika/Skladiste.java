package logika;
import java.util.*;

/**
 * Class Skladiste - třída představuje úložný prostor pro předměty a postavy, 
 * které zrovna nejsou za potřebí v herním plánu.
 * 
 *
 * @author     Jiří Mašek
 * @version    LS 2016/2017
 */
public class Skladiste
{
    private Set<Predmet> odkladistePredmetu;
    private Set<Postava> odkladistePostav;
    
    public Skladiste() {
        odkladistePredmetu = new HashSet<>();
        odkladistePostav = new HashSet<>();
    }
    
    /**
     * Metoda vrací informaci zda je předmět obsažen v Setu odkladistePredmetu.
     * 
     * @param    nazev          název předmětu (jedno slovo)
     * @return   true pokud je předmět nalazen; jinak false
     */    
    public boolean jePredmetVeSkladisti(String nazev)
    {
        for (Predmet p : odkladistePredmetu)
        {
            if(p.getNazev().equals(nazev))
            {
                return true;
            }       
        }
        
        return false;
    }

    /**
     * Metoda vrací informaci zda je postava obsažena v Setu odkladistePostav.
     * 
     * @param    nazev          jméno postavy (jedno slovo)
     * @return   true pokud je postava nalazena; jinak false
     */    
    public boolean jePostavaVeSkladisti(String nazev)
    {
        for (Postava p : odkladistePostav)
        {
            if(p.getNazev().equals(nazev))
            {
                return true;
            }       
        }
        
        return false;
    }    
    
    /**
     * Metoda vrací předmět ze Setu odkladistePredmetu.
     * 
     * @param    nazev          název předmětu (jedno slovo)
     * @return   vrací předmět, pokud je nalezen; jinak null
     */    
    public Predmet oznacPredmet(String nazev)
    {
        Predmet predmet = null;
        
        for (Predmet p : odkladistePredmetu)
        {
            if(p.getNazev().equals(nazev))
            {
                predmet = p;
                break;
            }       
        }        
        
        return predmet;
    }

    /**
     * Metoda vrací postavu ze Setu odkladistePostav.
     * 
     * @param    nazev          jméno postavy (jedno slovo)
     * @return   vrací postavu, pokud je nalezena; jinak null
     */        
    public Postava oznacPostavu(String nazev)
    {
        Postava postava = null;
        
        for (Postava p : odkladistePostav)
        {
            if(p.getNazev().equals(nazev))
            {
                postava = p;
                break;
            }       
        }        
        
        return postava;
    }    
    
    public boolean vlozPredmet(Predmet predmet)
    {
        return odkladistePredmetu.add(predmet);
    }

    public boolean vlozPostavu(Postava postava)
    {
        return odkladistePostav.add(postava);
    }    
    
    public boolean odstranPredmet(Predmet predmet)
    {
        return odkladistePredmetu.remove(predmet);
    } 
    
    public boolean odstranPostavu(Postava postava)
    {
        return odkladistePostav.remove(postava);
    }       
}
