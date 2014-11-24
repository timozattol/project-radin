/**
 * 
 */
package ch.epfl.sweng.radin.storage;

import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;

/**
 * @author timozattol
 * A Decorator for TransactionModel, who also has the users concerned
 * by the Transaction, with the corresponding coefficients.
 * Uses the decorated transaction model getters and setters.
 */
public class TransactionWithParticipantsModel extends Model {
    private TransactionModel mDecoratedTransactionModel;
    private Map<UserModel, Integer> mUsersWithCoefficients;

    /**
     * TransactionWithParticipantsModel Constructor.
     */
    public TransactionWithParticipantsModel(TransactionModel transactionModel, 
            Map<UserModel, Integer> usersWithCoefficients) {
        if (transactionModel == null) {
            throw new IllegalArgumentException("transactionModel should not be null");
        }
        
        if (usersWithCoefficients == null) {
            throw new IllegalArgumentException("usersWithCoefficients should not be null");
        }

        mDecoratedTransactionModel = new TransactionModel(transactionModel);
        mUsersWithCoefficients = new HashMap<UserModel, Integer>(usersWithCoefficients);
    }
    
    /**
     * @return the usersWithCoefficients
     */
    public Map<UserModel, Integer> getUsersWithCoefficients() {
        return new HashMap<UserModel, Integer>(mUsersWithCoefficients);
    }
    
    /**
     * @param usersWithCoefficients the mUsersWithCoefficients to set
     */
    public void setUsersWithCoefficients(
            Map<UserModel, Integer> usersWithCoefficients) {
        this.mUsersWithCoefficients = new HashMap<UserModel, Integer>(usersWithCoefficients);
    }

    /**
     * @return the transactionModel
     */
    public TransactionModel getTransaction() {
        return mDecoratedTransactionModel;
    }
    
    /**
     * @return the Map of users
     */
    public Map<UserModel, Integer> getMap() {
        return mUsersWithCoefficients;
    }
    
    /**
     * @return the transactionID
     */
    public int getTransactionID() {
        return mDecoratedTransactionModel.getTransactionID();
    }

    /**
     * @param transactionID the transactionID to set
     */
    public void setTransactionID(int transactionID) {
        mDecoratedTransactionModel.setTransactionID(transactionID);
    }

    /**
     * @return the parentRadinGroupID
     */
    public int getParentRadinGroupID() {
        return mDecoratedTransactionModel.getParentRadinGroupID();
    }

    /**
     * @param parentRadinGroupID the parentRadinGroupID to set
     */
    public void setParentRadinGroupID(int parentRadinGroupID) {
        mDecoratedTransactionModel.setParentRadinGroupID(parentRadinGroupID);
    }

    /**
     * @return the creatorID
     */
    public int getCreatorID() {
        return mDecoratedTransactionModel.getCreatorID();
    }

    /**
     * @param creatorID the creatorID to set
     */
    public void setCreatorID(int creatorID) {
        mDecoratedTransactionModel.setCreatorID(creatorID);
    }

    /**
     * @return the creditorID
     */
    public int getCreditorID() {
        return mDecoratedTransactionModel.getCreditorID();
    }

    /**
     * @param creditorID the creditorID to set
     */
    public void setCreditorID(int creditorID) {
        mDecoratedTransactionModel.setCreditorID(creditorID);
    }

    /**
     * @return the amount
     */
    public double getAmount() {
        return mDecoratedTransactionModel.getAmount();
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(double amount) {        
        mDecoratedTransactionModel.setAmount(amount);
    }

    /**
     * @return the currency
     */
    public Currency getCurrency() {
        return mDecoratedTransactionModel.getCurrency();
    }

    /**
     * @param currency the currency to set
     */
    public void setCurrency(Currency currency) {
        mDecoratedTransactionModel.setCurrency(currency);
    }

    /**
     * @return the dateTime
     */
    public DateTime getDateTime() {
        return mDecoratedTransactionModel.getDateTime();
    }

    /**
     * @param dateTime the dateTime to set
     */
    public void setDateTime(DateTime dateTime) {
        mDecoratedTransactionModel.setDateTime(dateTime);
    }

    /**
     * @return the purpose
     */
    public String getPurpose() {
        return mDecoratedTransactionModel.getPurpose();
    }

    /**
     * @param purpose the purpose to set
     */
    public void setPurpose(String purpose) {
        mDecoratedTransactionModel.setPurpose(purpose);
    }

    /**
     * @return the justificativePath
     */
    public String getJustificativePath() {
        return mDecoratedTransactionModel.getJustificativePath();
    }

    /**
     * @param justificativePath the justificativePath to set
     */
    public void setJustificativePath(String justificativePath) {
        mDecoratedTransactionModel.setJustificativePath(justificativePath);
    }

    /**
     * @return the type
     */
    public TransactionType getType() {
        return mDecoratedTransactionModel.getType();
    }

    /**
     * @param type the type to set
     */
    public void setType(TransactionType type) {
        mDecoratedTransactionModel.setType(type);
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {

		return getTransactionID();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			
			return true;
		}
		if (obj == null) {
			
			return false;
		}
		if (getClass() != obj.getClass()) {
			
			return false;
		}
		TransactionWithParticipantsModel other = (TransactionWithParticipantsModel) obj;
		if (mDecoratedTransactionModel == null) {
			if (other.mDecoratedTransactionModel != null) {
				
				return false;
			}
		} else if (!mDecoratedTransactionModel.equals(other.mDecoratedTransactionModel)) {
			
			return false;
		}
		if (mUsersWithCoefficients == null) {
			if (other.mUsersWithCoefficients != null) {
				
				return false;
			}
		} else if (!mUsersWithCoefficients.equals(other.mUsersWithCoefficients)) {
			
			return false;
		}
		return true;
	}
}
