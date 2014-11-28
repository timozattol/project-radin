/**
 * 
 */
package ch.epfl.sweng.radin.test.storage;


import java.util.LinkedHashMap;
import java.util.Map;

import org.joda.time.DateTime;

import android.test.AndroidTestCase;
import ch.epfl.sweng.radin.storage.Currency;
import ch.epfl.sweng.radin.storage.TransactionModel;
import ch.epfl.sweng.radin.storage.TransactionType;
import ch.epfl.sweng.radin.storage.TransactionWithParticipantsModel;

/**
 * @author timozattol
 *
 */
public class TransactionWithParticipantsModelTest extends AndroidTestCase {
    private final static int DEFAULT_AMOUNT = 50;
    private DateTime rightNow;
    private DateTime tommorrow;
    private TransactionWithParticipantsModel transactionWithParticipants;
    private Map<Integer, Integer> coefficients;
    private TransactionModel transaction;
    /* (non-Javadoc)
     * @see android.test.AndroidTestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        rightNow = DateTime.now();
        tommorrow = rightNow.plusDays(1);
        coefficients = new LinkedHashMap<Integer, Integer>();
        Integer julieID = 0;
        Integer winnyID = 1;
        coefficients.put(julieID, 1);
        coefficients.put(winnyID, 2);

        transaction = new TransactionModel(0, 0, 0, 0, DEFAULT_AMOUNT, 
                Currency.CHF, rightNow, "Julie buys stuff for her and winny", TransactionType.PAYMENT);
        
        transactionWithParticipants = new TransactionWithParticipantsModel(transaction, coefficients);
    }
    
    /**
     * Tests all the getters and setters
     */
    public void testGettersSetters() {
        assertTrue(transactionWithParticipants.getTransactionID() == 0);
        transactionWithParticipants.setTransactionID(1);
        assertTrue(transactionWithParticipants.getTransactionID() == 1);
        
        assertTrue(transactionWithParticipants.getParentRadinGroupID() == 0);
        transactionWithParticipants.setParentRadinGroupID(1);
        assertTrue(transactionWithParticipants.getParentRadinGroupID() == 1);
        
        assertTrue(transactionWithParticipants.getCreatorID() == 0);
        transactionWithParticipants.setCreatorID(1);
        assertTrue(transactionWithParticipants.getCreatorID() == 1);
        
        assertTrue(transactionWithParticipants.getCreditorID() == 0);
        transactionWithParticipants.setCreditorID(1);
        assertTrue(transactionWithParticipants.getCreditorID() == 1);
        
        assertTrue(transactionWithParticipants.getAmount() == DEFAULT_AMOUNT);
        transactionWithParticipants.setAmount(DEFAULT_AMOUNT + 1);
        assertTrue(transactionWithParticipants.getAmount() == DEFAULT_AMOUNT + 1);
        
        assertTrue(transactionWithParticipants.getCurrency() == Currency.CHF);
        transactionWithParticipants.setCurrency(Currency.EUR);
        assertTrue(transactionWithParticipants.getCurrency() == Currency.EUR);
        
        assertTrue(transactionWithParticipants.getDateTime().equals(rightNow));
        transactionWithParticipants.setDateTime(tommorrow);
        assertTrue(transactionWithParticipants.getDateTime().equals(tommorrow));
        
        assertTrue(transactionWithParticipants.getPurpose().equals(
                "Julie buys stuff for her and winny"));
        transactionWithParticipants.setPurpose("Julie buys more things");
        assertTrue(transactionWithParticipants.getPurpose().equals(
                "Julie buys more things"));
        
        assertTrue(transactionWithParticipants.getJustificativePath() == null);
        transactionWithParticipants.setJustificativePath("/img/picture.jpeg");
        assertTrue(transactionWithParticipants.getJustificativePath().equals("/img/picture.jpeg"));
        
        assertTrue(transactionWithParticipants.getType() == TransactionType.PAYMENT);
        transactionWithParticipants.setType(TransactionType.REIMBURSEMENT);
        assertTrue(transactionWithParticipants.getType() == TransactionType.REIMBURSEMENT);
    }
    
    
    public void testConstructorNullFailure() {
        try {
            new TransactionWithParticipantsModel(null, coefficients);
            fail("Null TransactionModel should throw an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Throwable t) {
            fail("Null TransactionModel should throw an IllegalArgumentException");
        }
        
        try {
            new TransactionWithParticipantsModel(transaction, null);
            fail("Null coefficients should throw an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Throwable t) {
            fail("Null coefficients should throw an IllegalArgumentException");
        }
    }
    
    
}
