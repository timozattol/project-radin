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
    private int mDebitorID;

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
    public TransactionModel(int transactionID, int parentRadinGroupID, int debitorID, 
            int creatorID, double amount, Currency currency, DateTime dateTime, String purpose,
            TransactionType type) {
        
        // Sanity checks
        checkArgumentPositive("Transaction ID", transactionID);
        checkArgumentPositive("RadinGroup ID", parentRadinGroupID);
        checkArgumentPositive("Transaction creator ID", creatorID);
        checkArgumentPositive("Transaction debitor ID", debitorID);
        
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
        mDebitorID = debitorID;
        mAmount = amount;
        mCurrency = currency;
        
        // DateTime is unmodifiable
        mDateTime = dateTime;
        
        mPurpose = purpose;
        mType = type;
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
     * @return the debitorID
     */
    public int getDebitorID() {
        return mDebitorID;
    }

    /**
     * @param debitorID the debitorID to set
     */
    public void setDebitorID(int debitorID) {
        checkArgumentPositive("Transaction debitor ID", debitorID);
        this.mDebitorID = debitorID;
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
    
}
