/** @author Eimutis Karčiauskas, KTU IF Programų inžinerijos katedra, 2014 09 23
 * Koreguota: Aleksas Riškus, MIK, 2016 
 *
 * Tai interfeisas, kurį turi įdiegti KTU studentų kuriamos duomenų klasės.
 * Metodai užtikrina patogų duomenų suformavimą iš String tipo eilučių.
 ******************************************************************************/
package studijosKTU;

public interface KTUable<T> extends Comparable<T> {
    KTUable create(String dataString); // sukuria naują objektą iš eilutės
    String validate();                 // patikrina objekto reikšmes
    void parse(String dataString);     // suformuoja objektą iš eilutės
}
