/**
 * 
 */
package ch.epfl.sweng.radin.storage;

/**
 * @author timozattol
 * An enum to represent all the Currencies supported by the app.
 */
public enum Currency {
    CHF("756"), USD("840"), EUR("978"), GBP("826");
    
    private final String mIsoCode;
    
    private Currency(String isoCode) {
        mIsoCode = isoCode;
    }
    
    public String getIsoCode() {
        return mIsoCode;
    }
}
