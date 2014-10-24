package ch.epfl.sweng.radin.storage;

import java.util.Date;
import java.util.List;

/**
 * @author timozattol
 * A model representing a transaction
 */
public final class TransactionModel {
    private int mTransactionID;

    // The id of the List containing this Transaction
    private int mParentListID;

    // The id of the User who paid for this Transaction
    private int mBuyerID;

    private double mAmount;
    private Currency mCurrency;
    private double mRate;
    private Date mDate;
    private String mPurpose;
    private TransactionType mType;
    private int creatorID;

    /**
     * Private constructor for TransactionModel
     */
    private TransactionModel(int transactionID, int parentListID, int buyerID, 
            double amount, Currency currency, Date date, String purpose, 
            TransactionType type) {
        // Sanity checks
        if (transactionID < 0) {
            throw new IllegalArgumentException("Transaction ID cannot be negative");
        }

        if (parentListID < 0) {
            throw new IllegalArgumentException("Transaction List ID cannot be negative");
        }

        if (buyerID < 0) {
            throw new IllegalArgumentException("Transaction ID cannot be negative");
        }

        if (amount <= 0) {
            throw new IllegalArgumentException("Transaction amount cannot be smaller " 
                    + "or equal to zero");
        }
        
        mCurrency = currency;
        mTransactionID = transactionID;
        mParentListID = parentListID;
        mBuyerID = buyerID;
        mAmount = amount;

        // Currencies are immutable
        mCurrency = currency;

        //TODO calculate rate with a CurrencyConverter
        mRate = 1;
        
        mDate = date;
        mPurpose = purpose;
        mType = type;
    }
    
    public static TransactionModel getTransactionWithID(int transactionID) {
        
        return null;
    }
    
    public static List<TransactionModel> getTransactionsForList(int listID) {
        
        return null;
    }
    
    public static List<TransactionModel> getTransactionsForBuyer(int buyerID) {
        
        return null;
    }
}
