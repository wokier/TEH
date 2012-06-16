package teh.fields;

import teh.reflect.TEHFieldsReflectionExtractor;

/**
 * TEH fields Factory
 * 
 */
public class TEHFieldsFactory {

    /**
     * singleton
     * 
     * @return
     */
    public static synchronized TEHFields get() {
	return TEHFieldsReflectionExtractor.getInstance();
    }

}
