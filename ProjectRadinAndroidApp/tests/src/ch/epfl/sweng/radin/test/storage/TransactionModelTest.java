/**
 * 
 */
package ch.epfl.sweng.radin.test.storage;

import org.joda.time.DateTime;

import ch.epfl.sweng.radin.storage.Currency;
import ch.epfl.sweng.radin.storage.TransactionModel;
import ch.epfl.sweng.radin.storage.TransactionType;
import android.test.AndroidTestCase;

/**
 * @author timozattol
 *
 */
public class TransactionModelTest extends AndroidTestCase{
    private final static int DEFAULT_AMOUNT = 50;
    private DateTime rightNow;
    private DateTime tommorrow;
    private TransactionModel transaction;
    /* (non-Javadoc)
     * @see android.test.AndroidTestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        rightNow = DateTime.now();
        tommorrow = rightNow.plusDays(1);
        transaction = new TransactionModel(0, 0, 0, 0, DEFAULT_AMOUNT, 
                Currency.CHF, rightNow, "Buying stuff", TransactionType.PAYMENT);
    }
    
    /**
     * Tests all the getters and setters
     */
    public void testGettersSetters() {
        assertTrue(transaction.getTransactionID() == 0);
        transaction.setTransactionID(1);
        assertTrue(transaction.getTransactionID() == 1);
        
        assertTrue(transaction.getParentRadinGroupID() == 0);
        transaction.setParentRadinGroupID(1);
        assertTrue(transaction.getParentRadinGroupID() == 1);
        
        assertTrue(transaction.getCreatorID() == 0);
        transaction.setCreatorID(1);
        assertTrue(transaction.getCreatorID() == 1);
        
        assertTrue(transaction.getCreditorID() == 0);
        transaction.setCreditorID(1);
        assertTrue(transaction.getCreditorID() == 1);
        
        assertTrue(transaction.getAmount() == DEFAULT_AMOUNT);
        transaction.setAmount(DEFAULT_AMOUNT + 1);
        assertTrue(transaction.getAmount() == DEFAULT_AMOUNT + 1);
        
        assertTrue(transaction.getCurrency() == Currency.CHF);
        transaction.setCurrency(Currency.EUR);
        assertTrue(transaction.getCurrency() == Currency.EUR);
        
        assertTrue(transaction.getDateTime().equals(rightNow));
        transaction.setDateTime(tommorrow);
        assertTrue(transaction.getDateTime().equals(tommorrow));
        
        assertTrue(transaction.getPurpose().equals("Buying stuff"));
        transaction.setPurpose("Buying more things");
        assertTrue(transaction.getPurpose().equals("Buying more things"));
        
        assertTrue(transaction.getJustificativePath() == null);
        transaction.setJustificativePath("/img/picture.jpeg");
        assertTrue(transaction.getJustificativePath().equals("/img/picture.jpeg"));
        
        assertTrue(transaction.getType() == TransactionType.PAYMENT);
        transaction.setType(TransactionType.REIMBURSEMENT);
        assertTrue(transaction.getType() == TransactionType.REIMBURSEMENT);
    }
    
    /**
     * Tries to construct successfully a new TransactionModel
     */
    public void testConstructorSuccess() {
        new TransactionModel(0, 0, 0, 0, DEFAULT_AMOUNT, Currency.CHF, rightNow, 
                "Buying stuff", TransactionType.PAYMENT);
    }
    
    /**
     * Tries to make constructor fail with negative ids
     */
    public void testConstructorIDFailure() {
        try {
            new TransactionModel(-1, 0, 0, 0, DEFAULT_AMOUNT, Currency.CHF, rightNow, 
                    "Buying stuff", TransactionType.PAYMENT);
            fail("Negative id should throw an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // ok
        }
        
        try {
            new TransactionModel(0, -1, 0, 0, DEFAULT_AMOUNT, Currency.CHF, rightNow, 
                    "Buying stuff", TransactionType.PAYMENT);
            fail("Negative id should throw an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // ok
        }
        
        try {
            new TransactionModel(0, 0, -1, 0, DEFAULT_AMOUNT, Currency.CHF, rightNow, 
                    "Buying stuff", TransactionType.PAYMENT);
            fail("Negative id should throw an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // ok
        }
        
        try {
            new TransactionModel(0, 0, 0, -1, DEFAULT_AMOUNT, Currency.CHF, rightNow, 
                    "Buying stuff", TransactionType.PAYMENT);
            fail("Negative id should throw an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }
    
    /**
     * Checks if constructor fails with false amounts
     */
    public void testConstructorAmountFailure() {
        try {
            new TransactionModel(0, 0, 0, 0, 0, Currency.CHF, rightNow, 
                    "Buying stuff", TransactionType.PAYMENT);
            fail("Zero-amount throw an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // ok
        }
        
        try {
            new TransactionModel(0, 0, 0, 0, -1, Currency.CHF, rightNow, 
                    "Buying stuff", TransactionType.PAYMENT);
            fail("Negative amount should throw an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }
    
    public void testConstructorNullFailure() {
        try {
            new TransactionModel(0, 0, 0, 0, DEFAULT_AMOUNT, Currency.CHF, null, 
                    "Buying stuff", TransactionType.PAYMENT);
            fail("Null DateTime should throw an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // ok
        }
        
        try {
            new TransactionModel(0, 0, 0, 0, DEFAULT_AMOUNT, Currency.CHF, rightNow, 
                    null, TransactionType.PAYMENT);
            fail("Null purpose should throw an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }
    
    
}
