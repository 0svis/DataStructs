package laborai.Lab3Zilinskas;

import laborai.studijosktu.BstSetKTU;
import laborai.studijosktu.SetADT;

import java.util.Set;

public class RiesutuApskaita {
    public static SetADT<String> riesutuPav(Riesutas[] ries){
        SetADT<Riesutas> uni = new BstSetKTU<>(Riesutas.pagalPav);
        SetADT<String> kart = new BstSetKTU<>();
        for(Riesutas r : ries){
            int sizeBefore = uni.size();
            uni.add(r);
            
            if(sizeBefore == uni.size()){
                kart.add(r.getPavadinimas());
            }
        }
        return kart;
    }
}
