/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package logika;

import javafx.scene.image.Image;

/**
 * Třída Predmet představuje jednotlivé předměty (věci), které je možné
 * ve hře najít.
 * 
 * Předmět může být přenositelný nebo nepřenositelný. Přenositelné předměty
 * je možné vložit do batohu postavy a přenášet mezi lokacemi. Nepřenositelné
 * předměty není možné z lokace odnést. Dále může být předmět použitelný nebo
 * nikoliv. 
 *
 *
 * @author     Jan Riha, Jiri Masek
 * @version    LS 2016/2017
 */
public class Predmet
{
    private String nazev;
    private String popis;
    private boolean prenositelny;
    private boolean pouzitelny;
    private Predmet pouzitelnostPr;
    private Image obrazekPredmetu;    

    
    /**
     * Vytvoří nový předmět se zadaným názvem, popisem, přenositelností, použitelností a přiřadí předmět kterým je daný objekt použitelný.
     * 
     * @param    nazev                     název předmětu (jedno slovo)
     * @param    popis                     popis předmětu (může se jednat o text libovolné délky)
     * @param    prenositelny              true, pokud má být předmět přenositelný; jinak false
     * @param    obrazekPredmetu
     * @param    pouzitelny                true, pokud má být předmět použitelný; jinak false
     * @param    pouzitelnostPredmetem     předmět kterým se jde daný objekt použít; jinak null
     */
    public Predmet(String nazev, String popis, boolean prenositelny, Image obrazekPredmetu, boolean pouzitelny, Predmet pouzitelnostPr)
    {
        this.nazev = nazev;
        this.popis = popis;
        this.prenositelny = prenositelny;
        this.obrazekPredmetu = obrazekPredmetu;
        this.pouzitelny = pouzitelny;
        this.pouzitelnostPr = pouzitelnostPr;
    }
    
    public Predmet(String nazev, String popis, boolean prenositelny, Image obrazekPredmetu)
    {
        this(nazev, popis, true, obrazekPredmetu, false, null);
    }  
    
    /**
     * Vytvoří nový předmět se zadaným názvem, popisem a přenositelností.
     * 
     * @param    nazev          název předmětu (jedno slovo)
     * @param    popis          popis předmětu (může se jednat o text libovolné délky)
     * @param    prenositelny   true, pokud má být předmět přenositelný; jinak false
     * @param    obrazekPredmetu
     */
    public Predmet(String nazev, String popis, boolean prenositelny)
    {
        this(nazev, popis, prenositelny, null, false, null);
    }    
    
    
    /**
     * Vytvoří nový přenositelný předmět se zadaným názvem a popisem.
     * 
     * @param    nazev    název předmětu (jedno slovo)
     * @param    popis    popis předmětu (může se jednat o text libovolné délky)
     */
    public Predmet(String nazev, String popis)
    {
        this(nazev, popis, true, null, false, null);
    }
     
    
    /**
     * Vrátí název předmětu.
     * 
     * @returns    název předmětu
     */
    public String getNazev()
    {
        return nazev;
    }
    
    /**
     * Vrátí popis předmětu.
     * 
     * @returns    popis předmětu
     */
    public String getPopis()
    {
        return popis + ".";
    }
    
    /**
     * Vrátí příznak, zda je předmět přenostilený, nebo ne.
     * 
     * @returns    true, pokud je předmět přenositelný; jinak false
     */
    public boolean isPrenositelny()
    {
        return prenositelny;
    }

     /**
     * Vrátí příznak, zda je předmět použitelný, nebo ne.
     * 
     * @returns    true, pokud je předmět použitelný; jinak false
     */
    public boolean isPouzitelny()
    {
        return pouzitelny;
    }
    
    /**
     * Nastaví nový popis předmětu.
     * 
     * @param    popis nový popispředmětu
     */
    public void setPopis(String popis)
    {
        this.popis = popis;
    }
    
    public Image getObrazekPredmetu() {
        return obrazekPredmetu;
    }    
    
    /**
     * Nastaví přenositelnost předmětu.
     * 
     * @param    prenositelny   true, pokud má být předmět přenositelný; jinak false
     */
    public void setPrenositelny(boolean prenositelny)
    {
        this.prenositelny = prenositelny;
    }
    
    /**
     * Nastaví použitelnost předmětu.
     * 
     * @param    použitelnost   true, pokud má být předmět použitelnost; jinak false
     */
    public void setPouzitelny(boolean pouzitelny)
    {
        this.pouzitelny = pouzitelny;
    }        

    @Override
    public String toString()
    {
        return nazev;
    }
    
    /**
     * Metoda equals pro porovnání dvou předmětů. Překrývá se metoda equals ze
     * třídy Object. Dva předměty jsou shodné, pokud mají stejný název. Tato
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
        if (!(o instanceof Predmet)) {
            return false;    // pokud parametr není typu Předmět, vrátíme false
        }
        // přetypujeme parametr na typ Předmět 
        Predmet druhy = (Predmet) o;

        //metoda equals třídy java.util.Objects porovná hodnoty obou názvů. 
        //Vrátí true pro stejné názvy a i v případě, že jsou oba názvy null,
        //jinak vrátí false.

       return (java.util.Objects.equals(this.nazev, druhy.nazev));       
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
    
    /**
     * Vrátí příznak, zda je předměte použitelný jiným předmětem.
     * 
     * @param      název předmětu, který bychom v souvislosti s tímto chtěli použít
     * @returns    true, pokud je předmět použitelný; jinak falsw
     */
    public boolean jePouzitelnyPredmetem(String nazevNastroje)
    {
        if(pouzitelnostPr != null) {
        return pouzitelnostPr.getNazev().equals(nazevNastroje);
        }
        else return false;
    }   

}
