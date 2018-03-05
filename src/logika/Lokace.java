/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package logika;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Trida Lokace - popisuje jednotlivé lokace (místnosti) hry. Tato třída je
 * součástí jednoduché textové hry.
 *
 * "Lokace" reprezentuje jedno místo (místnost, prostor, ...) ve scénáři hry.
 * Lokace může mít sousední lokace připojené přes východy. Pro každý východ
 * si lokace ukládá odkaz na sousedící lokace.
 *
 * @author     Michael Kolling, Lubos Pavlicek, Jarmila Pavlickova, Jan Riha, Jiri Masek
 * @version    LS 2016/2017
 */
public class Lokace {

    private String nazev;
    private String popis;
    private boolean zamcena;
    private Predmet zamek;
    private ObservableList<Lokace> vychody; // obsahuje sousední lokace
    //private Map<String, Predmet> predmety;
    private ObservableList<Predmet> predmety;
    private ObservableList<Postava> postavy;
    private double posLeft = 0.0;
    private double posTop = 0.0;    
    
    /**
     * Vytvoření lokace se zadaným popisem, např. "kuchyň", "hala", "trávník
     * před domem"
     *
     * @param    nazev nazev lokace, jednoznačný identifikátor, jedno slovo nebo víceslovný název bez mezer
     * @param    popis Popis lokace
     * @param    zamcena lokace může být zamčená
     * @param    zamek jeden Predmet, kerý lokaci může otevřít
     * @param    posLeft
     * @param    posTop
     */
    public Lokace(String nazev, String popis, boolean zamcena, Predmet zamek, double posLeft, double posTop) {
        this.nazev = nazev;
        this.popis = popis;
        this.zamcena = zamcena;
        this.zamek = zamek;
        this.posLeft = posLeft;
        this.posTop = posTop;
        vychody = FXCollections.observableArrayList();
        //predmety = new HashMap<>();
        predmety = FXCollections.observableArrayList();
        postavy = FXCollections.observableArrayList();
    }

    public Lokace(String nazev, String popis, boolean zamcena, Predmet zamek)
    {
        this(nazev, popis, zamcena, zamek, 0.0, 0.0);
    }    
    
    public Lokace(String nazev, String popis)
    {
        this(nazev, popis, false, null, 0.0, 0.0);
    }
    
    
    /**
     * Definuje východ z lokace (sousední/vedlejsi lokace). Vzhledem k tomu,
     * že je použit Set pro uložení východů, může být sousední lokace uvedena
     * pouze jednou (tj. nelze mít dvoje dveře do stejné sousední lokace).
     * Druhé zadání stejné lokace tiše přepíše předchozí zadání (neobjeví
     * se žádné chybové hlášení). Lze zadat též cestu ze do sebe sama.
     *
     * @param    vedlejsi lokace, která sousedi s aktualní lokací.
     *
     */
    public void setVychod(Lokace vedlejsi) {
        vychody.add(vedlejsi);
    }      

    /**
     * Vrací název lokace (byl zadán při vytváření lokace jako parametr
     * konstruktoru)
     *
     * @return    název lokace
     */
    public String getNazev() {
        return nazev;       
    }

    /**
     * Vrací "dlouhý" popis lokace, který může vypadat následovně: Jsi v
     * mistnosti/lokaci vstupni hala budovy VSE na Jiznim meste. vychody:
     * chodba bufet ucebna
     *
     * @return    dlouhý popis lokace
     */
    public String dlouhyPopis() {        
        if (nazev.equals("východ"))
        {
            return "Jsi venku.\n";
        }
        
        return "Jsi v mistnosti/lokaci " + nazev + ". " + popis + ".\n"
                + popisVychodu() + "\n"
                + seznamPredmetu() + "\n"
                + seznamPostav();
    }

    /**
     * Vrací textový řetězec, který popisuje všechny předměty v lokaci, například:
     * "predmety: zidle, stul, rum ".
     *
     * @return    popis předmětů - názvů všech předmětů v lokaci
     */
    private String seznamPredmetu()
    {
        String seznam = "predmety:";
        
        for (Predmet predmet : predmety)
        {
            String nazevPredmetu = predmet.getNazev();
            seznam += " " + nazevPredmetu;
        }
        return seznam;
    }
    
    /**
     * Vrací textový řetězec, který popisuje všechny postavy v lokaci, například:
     * "Postavy: dozorce, Johny".
     *
     * @return    popis postav - názvů všech postav v lokaci
     */
    private String seznamPostav()
    {
        String seznam = "postavy:";
        
        for (Postava postava : postavy)
        {
            seznam += " " + postava.getNazev();
        }
        
        return seznam;
    }    

    /**
     * Vrací textový řetězec, který popisuje sousední východy, například:
     * "vychody: hala ".
     *
     * @return    popis východů - názvů sousedních lokací
     */
    private String popisVychodu() {
        String vracenyText = "vychody:";
        for (Lokace sousedni : vychody) {
            vracenyText += " " + sousedni.getNazev();
        }
        return vracenyText;
    }

    /**
     * Vrací lokaci, která sousedí s aktuální lokací a jejíž název je zadán
     * jako parametr. Pokud lokace s udaným jménem nesousedí s aktuální
     * lokací, vrací se hodnota null.
     *
     * @param     nazevSouseda Jméno sousední lokace (východu)
     * @return    lokace, která se nachází za příslušným východem, nebo hodnota null, pokud lokace zadaného jména není sousedem.
     */
    public Lokace vratSousedniLokaci(String nazevSouseda) {
        List<Lokace>hledaneLokace = 
            vychody.stream()
                   .filter(sousedni -> sousedni.getNazev().equals(nazevSouseda))
                   .collect(Collectors.toList());
        if(hledaneLokace.isEmpty()){
            return null;
        }
        else {
            return hledaneLokace.get(0);
        }
    }

    /**
     * Vrací kolekci obsahující lokace, se kterými ta lokace sousedí.
     * Takto získaný seznam sousedních lokací nelze upravovat (přidávat,
     * odebírat východy) protože z hlediska správného návrhu je to plně
     * záležitostí třídy Lokace.
     *
     * @return    nemodifikovatelná kolekce lokací (východů), se kterými tato lokace sousedí.
     */   
    public ObservableList<Lokace> getVychody() {
        return vychody;
    }
    
    /**
     * Přidá předmět do lokace.
     * 
     * @param    predmet předmět, který má být přidán do lokace
     */
    public void vlozPredmet(Predmet predmet)
    {
        //predmety.put(predmet.getNazev(), predmet);
        predmety.add(predmet);
    }

    /**
     * Přidá předmět do lokace.
     * 
     * @param    predmet předmět, který má být přidán do lokace
     */
    public void vlozPostavu(Postava postava)
    {
        postavy.add(postava);
    }    

    /**
     * Odebere předmět s daným názvem z lokace a vrátí ho. Pokud v lokaci předmět s daným
     * názvem není, vrátí null.
     * 
     * @param      nazevPredmetu název předmětu
     * @returns    předmět, který byl odebrán z lokace
     */
    /*public Predmet vezmiPredmet(String nazevPredmetu)
    {
        return predmety.remove(nazevPredmetu);
    }*/

    public Predmet vezmiPredmet(String nazevPredmetu)
    {
        for (Predmet predmet : predmety) {
            if(predmet.getNazev().equals(nazevPredmetu)){
                predmety.remove(predmet);
                return predmet;
            }
        }
        return null;
    }    

    /**
     * Odebere předmět s daným názvem z lokace a vrátí ho. Pokud v lokaci předmět s daným
     * názvem není, vrátí null.
     * 
     * @param      nazevPredmetu název předmětu
     * @returns    předmět, který byl odebrán z lokace
     */
    public Postava vezmiPostavu(String nazevPostavy)
    {
        for (Postava postava : postavy) {
            if(postava.getNazev().equals(nazevPostavy)){
                postavy.remove(postava);
                return postava;
            }
        }
        return null;        
    }    
    
    /**
     * Najde v lokaci předmět s daným názvem a vrátí ho. Pokud v lokaci předmět s daným
     * názvem není, vrátí null.
     * 
     * @param      nazevPredmetu název předmětu
     * @returns    předmět z lokace
     */
    public Predmet najdiPredmet(String nazevPredmetu)
    {
        for (Predmet predmet : predmety) {
            if(predmet.getNazev().equals(nazevPredmetu)){
                return predmet;
            }
        }
        return null;
    }

    /**
     * Najde v lokaci předmět s daným názvem a vrátí ho. Pokud v lokaci předmět s daným
     * názvem není, vrátí null.
     * 
     * @param      nazevPredmetu název předmětu
     * @returns    předmět z lokace
     */
    public Postava najdiPostavu(String nazevPostavy)
    {
        for (Postava postava : postavy) {
            if(postava.getNazev().equals(nazevPostavy)){
                return postava;
            }
        }
        return null; 
    }    
    
    /**
     * Zjistí, jestli je v lokaci předmět s daným názvem.
     * 
     * @param      nazevPredmetu název předmětu
     * @returns    true, pokud lokace obsahuje předmět s daným názvem; jinak false
     */
    public boolean obsahujePredmet(String nazevPredmetu)
    {
        for (Predmet predmet : predmety) {
            if(predmet.getNazev().equals(nazevPredmetu)){
                return true;
            }
        }
        return false;
    }

    /**
     * Zjistí, jestli je v lokaci předmět s daným názvem.
     * 
     * @param      nazevPredmetu název předmětu
     * @returns    true, pokud lokace obsahuje předmět s daným názvem; jinak false
     */
    public boolean obsahujePostavu(String nazevPostavy)
    {
        for (Postava postava : postavy) {
            if(postava.getNazev().equals(nazevPostavy)){
                return true;
            }
        }
        return false; 
    }

    public double getPosLeft() {
        return posLeft;
    }

    public double getPosTop() {
        return posTop;
    }    


    /**
     * Zjistí, jestli je lokace zamčená.
     * 
     * @returns    true, pokud je lokace zamčená; jinak false
     */    
    public boolean getZamcena()
    {
        return zamcena;
    }
    
    /*public Map<String, Predmet> getPredmety() {
        return predmety;
    }*/
    
    public ObservableList<Predmet> getPredmety() {
        return predmety;
    }    

    public ObservableList<Postava> getPostavy() {
        return postavy;
    }    
    
    /**
     * Metoda se pokusí odemknout lokaci, pokud to je možné.
     *
     * @return    true pokud se odemčení povedlo nebo pokud je lokace již odemčená; jinak false
     */
    public boolean odemkniLokaci() {
       if(zamcena)
       {
           if(!zamek.isPouzitelny())
           {
               zamcena = false;
               return true;
           }
           else return false;
       }       
       else return true; 
    }  
    
    @Override
    public String toString() {
       return nazev;
    }

    /**
     * Metoda equals pro porovnání dvou lokací. Překrývá se metoda equals ze
     * třídy Object. Dvě lokace jsou shodné, pokud mají stejný název. Tato
     * metoda je důležitá z hlediska správného fungování seznamu východů (Set).
     *
     * Bližší popis metody equals je u třídy Object.
     *
     * @param     o object, který se má porovnávat s aktuálním
     * @return    hodnotu true, pokud má zadaná lokace stejný název, jinak false
     */  
    @Override
    public boolean equals(Object o) {
        // porovnáváme zda se nejedná o dva odkazy na stejnou instanci
        if (this == o) {
            return true;
        }
        // porovnáváme jakého typu je parametr 
        if (!(o instanceof Lokace)) {
            return false;    // pokud parametr není typu Lokace, vrátíme false
        }
        // přetypujeme parametr na typ Lokace 
        Lokace druha = (Lokace) o;

        //metoda equals třídy java.util.Objects porovná hodnoty obou názvů. 
        //Vrátí true pro stejné názvy a i v případě, že jsou oba názvy null,
        //jinak vrátí false.

       return (java.util.Objects.equals(this.nazev, druha.nazev));       
    }

    /**
     * metoda hashCode vraci ciselny identifikator instance, ktery se pouziva
     * pro optimalizaci ukladani v dynamickych datovych strukturach. Pri
     * prekryti metody equals je potreba prekryt i metodu hashCode. Podrobny
     * popis pravidel pro vytvareni metody hashCode je u metody hashCode ve
     * tride Object.
     */
    @Override
    public int hashCode() {
        int vysledek = 3;
        int hashNazvu = java.util.Objects.hashCode(this.nazev);
        vysledek = 37 * vysledek + hashNazvu;
        return vysledek;
    }    
}
