package logika;

import javax.swing.*;
import java.util.Random;

/**
 * Třída Ukoly představuje seznam úkolů, které hráč plní, aby vyhrál.
 *
 *
 * @author     Jiří Mašek
 * @version    LS 2016/2017
 */
public class Ukoly
{
    Hra hra;
    HerniPlan herniPlan;
    Skrys skrys;

    public Ukoly(Hra hra) 
    {
        this.hra = hra;
        herniPlan = hra.getHerniPlan();
        skrys = herniPlan.getSkrys();
    }

    /**
     * Představuje logickou jednotku rozmluvy s kápem věznice.
     * 
     * Johnymu se otevře panel. Potřebuje od Harryho získat rozpis služeb a má tři možnosti.
     * Může plánek vyměnit za cíga, což je jistota. Pak se může pokusit ho okrást.
     * V tomhle případě může náhodně Johny získat plánek a cíga mu zustanou nebo může přijít
     * o plánek i cíga (každopádně Harry zmizí jako postava z mapy). Třetí možnost je 
     * promluvit si později. Metoda počítá s plnou skrýší.  
     * 
     * @return   výsledek rozhovoru s Harrym
     */        
    public String ziskejRozpis()
    {        
        Postava postava = herniPlan.getAktualniLokace().najdiPostavu("Velkej_Harry");
        String prvniMoznost = "Dej Harrymu cíga.";
        String druhaMoznost = "Zkus Harryho okrást.";
        String tretiMOznost = "Rozhodni se pozdějc.";
        Random randomGenerator = new Random();
        int randomInt = randomGenerator.nextInt(100);

        String[] seznamMoznosti = {prvniMoznost, druhaMoznost, tretiMOznost};

        JComboBox jcb = new JComboBox(seznamMoznosti);
        jcb.setEditable(true);    

        JOptionPane.showMessageDialog( null, jcb, "Vyber možnost", JOptionPane.QUESTION_MESSAGE);

        if(jcb.getSelectedItem().equals(prvniMoznost))
        {
            if (skrys.jePlna())
            {
                return "Ve skrýši už nemáš volné misto, musíš něco zahodit a pak přijď za Harrym znovu.";
            }            

            if(skrys.jeVeSkrysi("cigarety"))
            {
                postava.setDialogCounter(2);
                skrys.zahodPredmet("cigarety");
                skrys.vlozPredmet(postava.getVlastniPredmet());
                return "V skrýši máš nyní rozpis služeb dozorců.";
            }

            return "V skrýši nemáš cigarety.";
        }  

        if(jcb.getSelectedItem().equals(druhaMoznost))
        {   
            if (skrys.jePlna())
            {
                return "V skrýši už nemáš volné misto, musíš něco zahodit a pak přijď za Harrym znovu.";
            }            

            if(randomInt % 2 == 0)
            {
                skrys.zahodPredmet("cigarety");
                herniPlan.getAktualniLokace().vezmiPostavu("Velkej_Harry");                
                return "Harry ti dal nakladáčku a nyní je na samotce i s plánkem. \n" +
                "Jestli sis vzal(a) cigarety se sebou, tak už nemáš co kouřit. \n" + 
                "Teď už se do místnosti dozorců jen tak nedostaneš. \n" +                       
                "Stihl(a) sis jen všimnout, že stráž tam není někdy pozdě večer.";
            }

            skrys.vlozPredmet(postava.getVlastniPredmet());
            herniPlan.getAktualniLokace().vezmiPostavu("Velkej_Harry");

            return "Johny dal Harrymu na ciferník a nyní je v nemocnici. Máš jeho rozpis.";                      
        }

        return "Stavíš se za Harrym pozdějc.";
    }

    /**
     * Představuje logickou jednotku získání klíče.
     * 
     * Johnymu potřebuje získat klíč, aby otevřel poklop. Na plánku od Harryho si přečtě, kdy
     * je stanoviště dozorců nehlídané. Vezme klíč a pak musí co nejrychleji otevřít poklop.
     * Pokud na stanoviště s přítomným dozorcem, tak prohrál. Pokud odejde ze stanoviště, když
     * je schovaný před dozorcem v místnosti, tak opět prohrál. 
     * 
     */        
    public void ziskejKlice(Lokace sousedniLokace)
    {
        String stanoviste = "stanoviste_dozorcu";
        String dozorce = "dozorce";
        Lokace aktLokace = herniPlan.getAktualniLokace();

        if(sousedniLokace.getNazev().equals(stanoviste) && sousedniLokace.obsahujePostavu(dozorce))
        {
            hra.setProhra(true);
            System.out.println("Vešel jsi na stanoviště ve špatný čas. Prohrál jsi");
        }

        if(aktLokace.getNazev().equals(stanoviste) && aktLokace.obsahujePostavu(dozorce))
        {
            hra.setProhra(true);
            System.out.println("Opustil jsi svůj ukryt a dozorce tě viděl odejít.");            
        }
    }
}
