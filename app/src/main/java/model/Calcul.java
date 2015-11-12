package model;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by dionys on 16/10/15.
 */
public class Calcul {

    /**
     *
     * @param items liste d'item
     * @return les frais totaux
     */
    public int fraisTotaux (ArrayList<Item> items) {
        int total = 0;

        for (int i=0; i<items.size(); i++) {
            if (items.get(i).isContent()){

                Content item = (Content) items.get(i);

                if (item.operate == false) {
                    int sortie = item.montant;
                    total = sortie + total;
                }
            }

        }

        return total;
    }

    /**
     *
     * @param items liste d'item
     * @return ce qui reste a la fin du mois
     */
    public int restant(ArrayList<Item> items) {
        int reste = 0;
        int rentrerTotal = 0;

        for (int i=0; i < items.size(); i++) {
            if (items.get(i).isContent()) {
                Content item = (Content) items.get(i);
                if (item.operate == true) {
                    int rentrer = item.montant;
                    rentrerTotal = rentrer + rentrerTotal;
                }
            }
        }
        reste = rentrerTotal - fraisTotaux(items);

        return reste;
    }
}
