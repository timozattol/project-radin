/**
 * 
 */
package ch.epfl.sweng.radin.storage;


import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author timozattol
 * A Decorator for TransactionModel, who also has the users concerned
 * by the Transaction, with the corresponding coefficients.
 * Uses the decorated transaction model getters and setters.
 */
public class TransactionWithParticipantsModel extends TransactionModel {
    private Map<Integer, Integer> mUsersWithCoefficients;

    /**
     * TransactionWithParticipantsModel Constructor.
     */
    public TransactionWithParticipantsModel(TransactionModel transactionModel, 
            Map<Integer, Integer> usersWithCoefficients) {
        super(transactionModel);
        
        if (usersWithCoefficients == null) {
            throw new IllegalArgumentException("usersWithCoefficients should not be null");
        }

        mUsersWithCoefficients = new LinkedHashMap<Integer, Integer>(usersWithCoefficients);
    }
    
    /**
     * @return the usersWithCoefficients
     */
    public Map<Integer, Integer> getUsersWithCoefficients() {
        return new LinkedHashMap<Integer, Integer>(mUsersWithCoefficients);
    }
    
    /**
     * @param usersWithCoefficients the mUsersWithCoefficients to set
     */
    public void setUsersWithCoefficients(
            Map<Integer, Integer> usersWithCoefficients) {
        this.mUsersWithCoefficients = new LinkedHashMap<Integer, Integer>(usersWithCoefficients);
    }

}
