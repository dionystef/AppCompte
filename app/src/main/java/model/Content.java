package model;

import java.io.Serializable;

/**
 * Created by dionys on 26/09/15.
 */
public class Content implements Item, Serializable{

    public String label;
    public int montant;
    public boolean operate;

    public Content(String _label, int _montant, boolean _operate) {
        this.label = _label;
        this.montant = _montant;
        this.operate = _operate;
    }

    @Override
    public boolean isHeader() {
        return false;
    }

    @Override
    public boolean isContent() {
        return true;
    }
}
