package laborai.Lab3Zilinskas;

import laborai.demo.Automobilis;
import laborai.gui.MyException;

import java.util.Arrays;
import java.util.stream.Stream;

public class RiesutGamyba {

    private static Riesutas[] riesutai;
    private static int prInd = 0, galInd = 0;
    private static boolean arPradzia = true;

    public static Riesutas[] generuoti(int kiekis){
        riesutai = new Riesutas[kiekis];
        for(int i = 0; i< kiekis; i++){
            riesutai[i] = new Riesutas.Builder().buildRandom();
        }
        return riesutai;
    }
    public static Riesutas[] generuotiIrIsmaisyti(int aibesDydis,
                                                     double isbarstymoKoeficientas) throws MyException {
        return generuotiIrIsmaisyti(aibesDydis, aibesDydis, isbarstymoKoeficientas);
    }
    public static Riesutas[] generuotiIrIsmaisyti(int aibesDydis,
                                                     int aibesImtis, double isbarstymoKoeficientas) throws MyException {
        riesutai = generuoti(aibesDydis);
        return ismaisyti(riesutai, aibesImtis, isbarstymoKoeficientas);
    }
    public static Riesutas[] ismaisyti(Riesutas[] riesBaze, int kiekis, double isbarstKoef)
        throws MyException{

        if(riesBaze == null){
            throw new IllegalArgumentException("riesBaze yra 'null' ");
        }
        if(kiekis <= 0){
            throw new MyException(String.valueOf(kiekis),1);
        }
        if(riesBaze.length < kiekis){
            throw new MyException(riesBaze.length + " >= " + kiekis, 2);
        }
        if((isbarstKoef<0) || (isbarstKoef > 1)){
            throw new MyException(String.valueOf(isbarstKoef),3);
        }
        int likesKiekis = riesBaze.length - kiekis;
        int prIndeksas = (int) (likesKiekis * isbarstKoef/2);

        Riesutas[] pradineRiesutuImtis = Arrays.copyOfRange(riesBaze, prIndeksas, prIndeksas+kiekis);
        Riesutas[] likusiRiesutuImtis = Stream.concat(
                Arrays.stream(Arrays.copyOfRange(riesBaze, 0, prIndeksas)),
                Arrays.stream(Arrays.copyOfRange(riesBaze, prIndeksas+kiekis, riesBaze.length)))
                .toArray(Riesutas[]::new);

        RiesutGamyba.prInd = 0;
        galInd = likusiRiesutuImtis.length-1;
        RiesutGamyba.riesutai = likusiRiesutuImtis;
        return likusiRiesutuImtis;

    }
    public static Riesutas gautiIsBazes() throws MyException {
        if((galInd - prInd) < 0){
            throw new MyException(String.valueOf(galInd-prInd),4);
        }
        arPradzia = !arPradzia;
        return riesutai[arPradzia ? prInd++ : galInd --];
    }
}
