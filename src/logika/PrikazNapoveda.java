/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package logika;

/**
 * Třída PrikazNapoveda implementuje pro hru příkaz napoveda.
 * Tato třída je součástí jednoduché textové hry.
 *
 * @author     Jarmila Pavlickova, Luboš Pavlíček, Jan Říha, Jiří Mašek
 * @version    LS 2016/2017
 */
class PrikazNapoveda implements IPrikaz {

    private static final String NAZEV = "napoveda";
    private SeznamPrikazu platnePrikazy;

    /**
     * Konstruktor třídy
     *
     * @param    platnePrikazy seznam příkazů, které je možné ve hře použít, aby je nápověda mohla zobrazit uživateli.
     */    
    public PrikazNapoveda(SeznamPrikazu platnePrikazy) {
        this.platnePrikazy = platnePrikazy;
    }

    /**
     * Vrací základní nápovědu po zadání příkazu "napoveda". Nyní se vypisuje
     * vcelku primitivní zpráva a seznam dostupných příkazů.
     *
     * @return    napoveda ke hre
     */
    @Override
    public String proved(String... parametry) {
        return "Hra může skončit dvěma způsoby. Buď výhrou,\n"
        + "kdy se Johnymu podaří z vězení utéct a domoci se spravedlnosti\n"
        + "nebo se může stát, že Johny bude chycen během svého pokusu\n"
        + "o útěk a uvrhnut na samotku, kde je ostraha ještě přísnější\n"
        + "a útěk nemožný. V takovém případě hra končí prohrou.\n"
        + "\n"
        + "K úspěšnému splnění hry by ti mělo pomoct vězeňské info,\n"
        + "které se ti bude zobrazovat v průběhu hry.\n"
        + "\n"
        + "Můžeš zadat tyto příkazy:\n"
        + platnePrikazy.vratNazvyPrikazu();
    }

    /**
     * Metoda vrací název příkazu (slovo které používá hráč pro jeho vyvolání).
     *
     * @return    název příkazu
     */
    @Override
    public String getNazev() {
        return NAZEV;
    }

}
