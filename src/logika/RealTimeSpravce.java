package logika;
import java.util.concurrent.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.swing.JOptionPane;

/**
 * Class RealTimeSpravce - třída je správcem aktuálního stavu herního plánu.
 * 
 * Tato třída inicializuje herní hodiny, stará se o kuchyni a o místnost dozorců.
 *
 * @author     Jiří Mašek
 * @version    LS 2016/2017
 */
public class RealTimeSpravce
{
    private Hra hra;
    private HerniPlan herniPlan;
    private Hodiny hodiny;
    private ScheduledExecutorService executor;
    private Skladiste skladiste;
    private boolean podminkaZpravaKuchyne = true;
    private int pomocnyHodiny;
    private ObservableList<String> digiHodiny;

    public RealTimeSpravce(Hra hra)  
    {
        this.hra = hra;
        herniPlan = hra.getHerniPlan();
        this.hodiny = new Hodiny();
        this.skladiste = herniPlan.getSkladiste();
        digiHodiny = FXCollections.observableArrayList();
        spustSpravce();
    }

    /**
     * Hlavní správce všech aktivních funkčností a metod třídy. 
     */        
    public void spravceHlavni()
    {
        spravceHodin();
        spravceDigiHodin();
        spravceKuchyne();
        spravceStanovisteDozorcu();
    }       

    /**
     * Metoda, která se stará o 24 hodinový cyklus hry. 
     */      
    private void spravceHodin()
    {
        hodiny.hodiny();
    }
        

    /**
     * Metoda, která způsobí, že mezi 11 až 14 hodinou se v lokaci kuchyne objeví lzicka a polevka.
     * 
     * Dále metoda také informuje uživatele prostřednictvím vyskakujícího panelu. Panel zmizí 
     * pokud si hráč, alespoň jednou vzal lžíci do skrýše.
     */ 
    private void spravceKuchyne()
    {
        Lokace kuchyne = herniPlan.oznacLokaci("kuchyne");
        int aktualniHodina = hodiny.getAktualniHodina();
        String lzicka = "lzicka";
        String polevka = "polevka";
        String infoMessage = "Právě je jedenáct. Běž rychle do kuchyně, jinak přijdeš o žvanec."; 
        String titleBar = "Vezeňský info";

        if(aktualniHodina == 11 && podminkaZpravaKuchyne)
        {
            JOptionPane.showMessageDialog(null, infoMessage, titleBar, JOptionPane.INFORMATION_MESSAGE);
        }

        if(aktualniHodina == 11)
        {
            kuchyne.vlozPredmet(skladiste.oznacPredmet(lzicka));
            kuchyne.vlozPredmet(skladiste.oznacPredmet(polevka));
        }

        if (aktualniHodina == 14)  
        {
            if(kuchyne.vezmiPredmet(lzicka) == null)
            {
                podminkaZpravaKuchyne = false;
            }
            
            kuchyne.vezmiPredmet(polevka);
        }
    }
    

    /**
     * Metoda, která způsobí, že mezi 20 až 0 hodinou z lokace stanoviste_dozorcu zmizi dozorce
     * a ukončuje hru v případě, že hráč vezme klíč a nestihne do 1 hodiny klíč vrátit nebo vyhrát.
     * 
     * Dále metoda také informuje uživatele prostřednictvím vyskakujícího panelu o přítomnosti
     * dozorce, když hráč zůstane v lokaci po půlnoci. 
     */ 
    private void spravceStanovisteDozorcu()
    { 
        Lokace aktLokace = herniPlan.getAktualniLokace();
        Lokace stanovisteDozorcu = herniPlan.oznacLokaci("stanoviste_dozorcu");
        int aktualniHodina = hodiny.getAktualniHodina();
        String dozorce = "dozorce";
        String klic = "klic";
        String infoMessage = "Odbila půlnoc a dozorce se vrátil. Rychle se schovej, \n" +
                             "ustel si a počkej dokud zas dozorce neodejde. Pokud si \n" +
                             "už vzal klíč, tak jsi prohrál. Dozorci kvůli klíči \n" +
                             "prohledají místnost a najdou tě.";
        String titleBar = "Vezeňský info";
        
        if(aktLokace.equals(stanovisteDozorcu) && aktualniHodina == 0)
        {
            JOptionPane.showMessageDialog(null, infoMessage, titleBar, JOptionPane.INFORMATION_MESSAGE);
        }        
        
        if(aktualniHodina == 0)
        {
            stanovisteDozorcu.vlozPostavu(skladiste.oznacPostavu(dozorce));
        }

        if (aktualniHodina == 20)  
        {
            stanovisteDozorcu.vezmiPostavu(dozorce);
        }

        if(aktualniHodina == 1 && (!stanovisteDozorcu.obsahujePredmet(klic)))
        {
            hra.setProhra(true);
            System.out.println("Prohrál jsi. Dozorci tě lokalizovali při útěku s klíči.\nZadej kterýkoliv platný příkaz pro ukončení hry.");
        }
        
    }
    
    private void spravceDigiHodin(){
        int aktualniHodina = hodiny.getAktualniHodina();
        String digiFormat;
        
        if(aktualniHodina <= 9) {
            digiFormat = "0" + aktualniHodina + ":00";
        }
        else {
            digiFormat = "" + aktualniHodina + ":00";
        }        
        digiHodiny.add(digiFormat);
    }
    
    /**
     * Vytváří vlákno, ve kterém spouští s pravidelnou frekvencí metodu spravceHlavni()
     * 
     */     
    public void spustSpravce()
    {
        hodiny.restartHodin();
        
        executor = Executors.newScheduledThreadPool(1); // vytvorime exekutor s jednim vlaknem

        Runnable task = () -> spravceHlavni(); // pomoci lamda vyrazu lokalne implementujeme rozhrani Runnable (priradime spravce)

        executor.scheduleAtFixedRate(task, 0, 10, TimeUnit.SECONDS); // spustime task ve vlaknu s pravidelnou frekvenci, pro normalni hru 15 sekund 
    }   

    /**
     * Bezpodmínečně ukončuje správce jako aktivní proces
     * 
     */     
    public void zastavSpravce()
    {
        executor.shutdownNow();
    }
    
    public void pauzaSpravce()
    {
        pomocnyHodiny = hodiny.getAktualniHodina();
        executor.shutdownNow();
        hodiny.setHodina(-1);
    }

    public void obnovSpravce()
    {
        hodiny.setHodina(pomocnyHodiny);
        
        executor = Executors.newScheduledThreadPool(1); // vytvorime exekutor s jednim vlaknem

        Runnable task = () -> spravceHlavni(); // pomoci lamda vyrazu lokalne implementujeme rozhrani Runnable (priradime spravce)

        executor.scheduleAtFixedRate(task, 0, 10, TimeUnit.SECONDS); // spustime task ve vlaknu s pravidelnou frekvenci, pro normalni hru 15 sekund 
    }       
        
    
    // kvuli testum
    public Hodiny getHodiny()
    {
        return hodiny;
    }
    
    public ObservableList<String> getDigiHodiny() {
        return digiHodiny;
    }    

}
