package ch.epfl.sweng.radin.storage;

import org.joda.time.DateTime;

/**
 * @author timozattol
 * A model representing a transaction
 */
public final class TransactionModel extends Model {
    private int mTransactionID;

    // The id of the RadinGroup containing this Transaction
    private int mParentRadinGroupID;
    
    // The id of the creator of the Transaction
    private int mCreatorID;

    // The id of the User who paid for this Transaction
    private int mCreditorID;

    private double mAmount;
    private Currency mCurrency;
    private DateTime mDateTime;
    private String mPurpose;
    
    // The path of the image of the receipt. Optional.
    private String mJustificativePath;
    
    private TransactionType mType;

    /**
     * Public constructor for TransactionModel
     */
    public TransactionModel(int transactionID, int parentRadinGroupID, int creditorID, 
            int creatorID, double amount, Currency currency, DateTime dateTime, String purpose,
            TransactionType type) {
        
        // Sanity checks
        checkArgumentPositive("Transaction ID", transactionID);
        checkArgumentPositive("RadinGroup ID", parentRadinGroupID);
        checkArgumentPositive("Transaction creator ID", creatorID);
        checkArgumentPositive("Transaction creditor ID", creditorID);
        
        checkArgumentPositive("Transaction amount", amount);
        if (amount == 0) {
            throw new IllegalArgumentException("Transaction amount cannot be non-zero");
        }
        
        checkArgumentNull("DateTime ", dateTime);
        
        checkArgumentNull("Purpose ", purpose);
        checkEmptyStringArgument("Purpose ", purpose);
        
        mTransactionID = transactionID;
        mParentRadinGroupID = parentRadinGroupID;
        mCreatorID = creatorID;
        mCreditorID = creditorID;
        mAmount = amount;
        mCurrency = currency;
        
        // DateTime is unmodifiable
        mDateTime = dateTime;
        
        mPurpose = purpose;
        mType = type;
    }
    
    /**
     * Copy constructor for TransactionModel
     * @param original the original TransactionModel to be copied
     */
    public TransactionModel(TransactionModel original) {
        mTransactionID = original.getTransactionID();
        mParentRadinGroupID = original.getParentRadinGroupID();
        mCreditorID = original.getCreditorID();
        mCreatorID = original.getCreatorID();
        mAmount = original.getAmount();
        mCurrency = original.getCurrency(); 
        mDateTime = original.getDateTime(); 
        mPurpose = original.getPurpose();
        mType = original.getType();
    }

    /**
     * @return the transactionID
     */
    public int getTransactionID() {
        return mTransactionID;
    }

    /**
     * @param transactionID the transactionID to set
     */
    public void setTransactionID(int transactionID) {
        checkArgumentPositive("Transaction ID", transactionID);
        this.mTransactionID = transactionID;
    }

    /**
     * @return the parentRadinGroupID
     */
    public int getParentRadinGroupID() {
        return mParentRadinGroupID;
    }

    /**
     * @param parentRadinGroupID the parentRadinGroupID to set
     */
    public void setParentRadinGroupID(int parentRadinGroupID) {
        checkArgumentPositive("RadinGroup ID", parentRadinGroupID);
        this.mParentRadinGroupID = parentRadinGroupID;
    }

    /**
     * @return the creatorID
     */
    public int getCreatorID() {
        return mCreatorID;
    }

    /**
     * @param creatorID the creatorID to set
     */
    public void setCreatorID(int creatorID) {
        checkArgumentPositive("Transaction creator ID", creatorID);
        this.mCreatorID = creatorID;
    }

    /**
     * @return the creditorID
     */
    public int getCreditorID() {
        return mCreditorID;
    }

    /**
     * @param creditorID the creditorID to set
     */
    public void setCreditorID(int creditorID) {
        checkArgumentPositive("Transaction creditor ID", creditorID);
        this.mCreditorID = creditorID;
    }

    /**
     * @return the amount
     */
    public double getAmount() {
        return mAmount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(double amount) {
        checkArgumentPositive("Transaction amount", amount);
        if (amount == 0) {
            throw new IllegalArgumentException("Transaction amount cannot be non-zero");
        }
        
        this.mAmount = amount;
    }

    /**
     * @return the currency
     */
    public Currency getCurrency() {
        return mCurrency;
    }

    /**
     * @param currency the currency to set
     */
    public void setCurrency(Currency currency) {
        this.mCurrency = currency;
    }

    /**
     * @return the dateTime
     */
    public DateTime getDateTime() {
        return mDateTime;
    }

    /**
     * @param dateTime the dateTime to set
     */
    public void setDateTime(DateTime dateTime) {
        checkArgumentNull("DateTime", dateTime);
        this.mDateTime = dateTime;
    }

    /**
     * @return the purpose
     */
    public String getPurpose() {
        return mPurpose;
    }

    /**
     * @param purpose the purpose to set
     */
    public void setPurpose(String purpose) {
        checkArgumentNull("Purpose", purpose);
        checkEmptyStringArgument("Purpose", purpose);
        this.mPurpose = purpose;
    }

    /**
     * @return the justificativePath
     */
    public String getJustificativePath() {
        return mJustificativePath;
    }

    /**
     * @param justificativePath the justificativePath to set
     */
    public void setJustificativePath(String justificativePath) {
        // No checks here, because the justificative will probably be optional
        
        this.mJustificativePath = justificativePath;
    }

    /**
     * @return the type
     */
    public TransactionType getType() {
        return mType;
    }

    /**
     * @param type the type to set
     */
    public void setType(TransactionType type) {
        checkArgumentNull("Transaction type", type);
        this.mType = type;
    }
    
    private void checkArgumentPositive(String argName, double arg) {
        if (arg < 0) {
            throw new IllegalArgumentException(argName + " cannot be negative");
        }
    }
    
    private void checkArgumentNull(String argName, Object arg) {
        if (arg == null) {
            throw new IllegalArgumentException(argName + " cannot be null");
        }
    }
    
    private void checkEmptyStringArgument(String argName, String string) {
        if (string.equals("")) {
            throw new IllegalArgumentException(argName + " cannot be the empty String");
        }
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return mTransactionID;
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
		TransactionModel other = (TransactionModel) obj;
		if (Double.doubleToLongBits(mAmount) != Double.doubleToLongBits(other.mAmount)) {
			
			return false;
		}
		if (mCreatorID != other.mCreatorID) {
			
			return false;
		}
		if (mCurrency != other.mCurrency) {
			
			return false;
		}
		if (mDateTime == null) {
			if (other.mDateTime != null) {
				
				return false;
			}
		} else if (!mDateTime.equals(other.mDateTime)) {
			
			return false;
		}
		if (mCreditorID != other.mCreditorID) {
			
			return false;
		}
		if (mJustificativePath == null) {
			if (other.mJustificativePath != null) {
				
				return false;
			}
		} else if (!mJustificativePath.equals(other.mJustificativePath)) {
			
			return false;
		}
		if (mParentRadinGroupID != other.mParentRadinGroupID) {
			
			return false;
		}
		if (mPurpose == null) {
			if (other.mPurpose != null) {
				
				return false;
			}
		} else if (!mPurpose.equals(other.mPurpose)) {
			
			return false;
		}
		if (mTransactionID != other.mTransactionID) {
			
			return false;
		}
		if (mType != other.mType) {
			
			return false;
		}
		return true;
	}
    
}
