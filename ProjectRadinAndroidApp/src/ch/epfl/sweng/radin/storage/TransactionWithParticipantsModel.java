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
    private Map<UserModel, Integer> mUsersWithCoefficients;

    /**
     * TransactionWithParticipantsModel Constructor.
     */
    public TransactionWithParticipantsModel(TransactionModel transactionModel, 
            Map<UserModel, Integer> usersWithCoefficients) {
        super(transactionModel);
        
        if (usersWithCoefficients == null) {
            throw new IllegalArgumentException("usersWithCoefficients should not be null");
        }

        mUsersWithCoefficients = new LinkedHashMap<UserModel, Integer>(usersWithCoefficients);
    }
    
    /**
     * @return the usersWithCoefficients
     */
    public Map<UserModel, Integer> getUsersWithCoefficients() {
        return new LinkedHashMap<UserModel, Integer>(mUsersWithCoefficients);
    }
    
    /**
     * @param usersWithCoefficients the mUsersWithCoefficients to set
     */
    public void setUsersWithCoefficients(
            Map<UserModel, Integer> usersWithCoefficients) {
        this.mUsersWithCoefficients = new LinkedHashMap<UserModel, Integer>(usersWithCoefficients);
    }

}
