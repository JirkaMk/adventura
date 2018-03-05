/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package main;

import util.IHra;
import java.io.IOException;
import logika.*;
import uiGrafika.GrafickeRozhrani;
import uiText.TextoveRozhrani;

/**
 * *****************************************************************************
 * Třída Start je hlavní třídou projektu, který představuje jednoduchou textovou
 * adventuru určenou k dalším úpravám a rozšiřování.
 *
 * @author Jarmila Pavlíčková, Jan Říha
 * @version LS 2016/2017
 */
public class Start //extends Application 
{
    /**
     * *************************************************************************
     * Metoda, prostřednictvím níž se spouští celá aplikace.
     *
     * @param args Parametry příkazového řádku
     */

    public static void main(String[] args) throws IOException {
        
        if (args.length == 0) {
            GrafickeRozhrani.main();
        } else if (args[0].equals("-text")) {
            IHra hra = new Hra();
            TextoveRozhrani textInterface = new TextoveRozhrani(hra);
            textInterface.hrajVTerminalu();
        } else if (args[0].equals("-file")) {
            IHra hra = new Hra();
            TextoveRozhrani textInterface = new TextoveRozhrani(hra);
            textInterface.hrajZeSouboru(args[0]);
        } else {
            System.out.println("Neplatny parametr");
        }
    }
}
