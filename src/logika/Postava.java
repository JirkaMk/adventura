package logika;

/**
 * Třída Postava představuje jednotlivé postavy, které je možné
 * ve hře potkat.
 * 
 * Postava má název a popis. Vedle toho má také dialogový iterátor, který je klíčovým
 * prvkem v rozhovoru hráče s postavou. Každá postava může jeden předmět vlastnit a
 * může být ochotna ho za jiný předmět vyměnit. 
 *
 *
 * @author     Jiří Mašek
 * @version    LS 2016/2017
 */
public class Postava
{
    private String nazev;
    private String popis;
    private int dialogCounter;
    private Predmet vlastniPredmet;    
    private Predmet akceptujePredmet;
    
    /**
     * Vytvoří novou postavu se zadaným jménem, popisem, vlastněným předmětem a 
     * předmětem akceptovatelným jako výmenu. 
     * 
     * @param    nazev                     název postavy (jedno slovo)
     * @param    popis                     popis postavy (může se jednat o text libovolné délky)
     * @param    vlastniPredmet            přiřazený předmět; jinak null
     * @param    akceptujePredmet          přiřazený předmět; jinak null
     */
    public Postava(String nazev, String popis, Predmet vlastniPredmet, Predmet akceptujePredmet)
    {
        dialogCounter = 1;
        this.nazev = nazev;
        this.popis = popis;
        this.vlastniPredmet = vlastniPredmet;
        this.akceptujePredmet = akceptujePredmet;
    }


    public Postava(String nazev, String popis)
    {
        this(nazev, popis, null, null);
    }    
    
    
    public String getNazev()
    {
        return nazev;
    }
    
    /**
     * Vrátí popis postavy.
     * 
     * @returns    popis postavy
     */
    public String getPopis()
    {
        return popis + ".";
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
        if (!(o instanceof Postava)) {
            return false;    // pokud parametr není typu Předmět, vrátíme false
        }
        // přetypujeme parametr na typ Předmět 
        Postava druhy = (Postava) o;

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
    
    public boolean akceptujePredmet(String nazevPredmetu)
    {
        return akceptujePredmet.getNazev().equals(nazevPredmetu);
    }
    
    public Predmet getVlastniPredmet()
    {
        return vlastniPredmet;         
    }
    
    public int getDialogCounter()
    {
        return dialogCounter;    
    }
    
    public void setDialogCounter(int dialogCount)
    {
        this.dialogCounter = dialogCount;
    }      

}
