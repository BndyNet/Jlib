package net.bndy.lib.wrapper;

import java.io.Serializable;
import java.util.List;

public class StringsWrapper implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<String> values;

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }
}
