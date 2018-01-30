package net.bndy.lib.wrapper;

import java.io.Serializable;
import java.util.List;

public class IntegersWrapper implements Serializable {

    private List<Integer> values;

    public List<Integer> getValues() {
        return values;
    }

    public void setValues(List<Integer> values) {
        this.values = values;
    }
}
