package ch.epfl.sweng.radin.storage;

import java.util.Arrays;
import java.util.Currency;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author timozattol
 * A model representing a transaction
 */
public class TransactionModel {
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

    /**
     * Constructor for TransactionModel
     */
    public TransactionModel(int transactionID, int parentListID, int buyerID, 
            double amount, Currency currency, Date date, String purpose, 
            TransactionType type) {
        // Sanity checks
        if(transactionID < 0) {
            throw new IllegalArgumentException("Transaction ID cannot be negative");
        }

        if(parentListID < 0) {
            throw new IllegalArgumentException("Transaction List ID cannot be negative");
        }

        if(buyerID < 0) {
            throw new IllegalArgumentException("Transaction ID cannot be negative");
        }

        if(amount <= 0) {
            throw new IllegalArgumentException("Transaction amount cannot be smaller " +
            		"or equal to zero");
        }

        if(currency == null || !TransactionModel.mAvailableCurrencies.contains(currency)) {
            throw new IllegalArgumentException("Currency must be one of the supported currencies");
        }

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
}
