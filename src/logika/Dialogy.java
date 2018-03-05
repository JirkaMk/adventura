package logika;

//import java.util.concurrent.TimeUnit;

/**
 * Třída Dialogy představuje scénařovou interakci mezi herními postavami a hráčem.
 * 
 * Každá postava má iterátor, který zaznamenává kolikrát s ní hráč mluvil právě 
 * v rámci této třídy. Podle počítače a jména postavy se pak volí konkrétní část
 * scénáře.
 *
 *
 * @author     Jiří Mašek
 * @version    LS 2016/2017
 */
public class Dialogy
{
    private Hra hra;
    private HerniPlan herniPlan;
    private Ukoly ukoly;
    private Postava postava;

    public Dialogy(Hra hra) 
    {
        this.hra = hra;
        herniPlan = hra.getHerniPlan();
        ukoly = hra.getUkoly();
    }  
    
    /**
     * Podle hodnoty iterátoru dialogu u postavy se zvolí úroveň dialogu.
     * 
     * @param    nazev          jméno postavy (jedno slovo)
     * @return   řetězec pokud platné jméno postavy; jinak null
     */        
    public String hlavniDialog(String jmenoPostavy)
    {
        String dialog = null; 
        postava = herniPlan.getAktualniLokace().najdiPostavu(jmenoPostavy);
        int dialogCounter = postava.getDialogCounter();

        switch (dialogCounter) {
            case 1:
            dialog = prvniDialog(jmenoPostavy); 
            break;
            case 2:
            dialog = druhyDialog(jmenoPostavy);
            default: 
            break;
        }

        return dialog;
    }

    /**
     * Podle hodnoty jména postavy se zvolí konkretné sekce dialogu.
     * 
     * @param    nazev          jméno postavy (jedno slovo)
     * @return   vrací defaultní dialog, pokud jde o neznámou postavu; jinak konkretní řetězec
     */            
    private String prvniDialog(String jmenoPostavy)  
    {
        String dialog = "\nJOHNY: Haloooo slyšíte mě.";        

        if(jmenoPostavy.equals("Velkej_Harry")) 
        {
            System.out.println("\nJOHNY: Servus Harry. Víš jak jsme se tuhle bavili. Potřeboval bych od tebe ten planek.");

            try {
                Thread.sleep(4000);               
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            
            System.out.println("VELKEJ HARRY: Tak to určitě víš co jsem za něj chtěl. Kde máš cíga.");

            try {
                Thread.sleep(4000);               
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }                  
                
            System.out.println("JOHNY: Však jo, hned ti je dám.\n");                
                
            try {
                Thread.sleep(3000);               
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }          

            dialog = ukoly.ziskejRozpis();
        }

        if(jmenoPostavy.equals("strazny"))
        {
            postava.setDialogCounter(2);

            dialog = "\nJOHNY: Čau Nicku, jak se dneska máš?\n"
            + "DOZORCE NICK: Nečum na mě a mlč.\n"
            + "JOHNY: Příjemnej jako vždycky.\n"; 
        }

        if(jmenoPostavy.equals("dozorce"))
        {
            dialog = "\nJOHNY: Pomátl(a) ses, s tím teď mluvit určitě nebudu.\n"; 
        }        

        return dialog;
    }

    /**
     * Podle hodnoty jména postavy se zvolí konkretné sekce dialogu.
     * 
     * @param    nazev          jméno postavy (jedno slovo)
     * @return   vrací defaultní dialog, pokud jde o neznámou postavu; jinak konkretní řetězec
     */              
    private String druhyDialog(String jmenoPostavy)  
    {
        String dialog = "\nJOHNY: Haloooo slyšíte mě.";

        if(jmenoPostavy.equals("Velkej_Harry")) 
        {
            dialog = "\nJOHNY: Servus Harry. Díky za ten plánek.\n"
            + "VELKEJ HARRY: Už teď toho lituju. Neřekl si mi, že to sou podělaný mentolky.\n"
            + "JOHNY: Aspoň je nevyhulíš za dnešek.";          
        }

        if(jmenoPostavy.equals("strazny"))
        {
            dialog = "\nJOHNY: Na něj už radši mluvit nebudu."; 
        }        

        return dialog;
    }    
}
