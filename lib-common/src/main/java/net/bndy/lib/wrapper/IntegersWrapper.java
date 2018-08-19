package net.bndy.lib.wrapper;

import java.io.Serializable;
import java.util.List;

public class IntegersWrapper implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<Integer> values;

    public List<Integer> getValues() {
        return values;
    }

    public void setValues(List<Integer> values) {
        this.values = values;
    }
}
