

import receipt.ReceiptController;
import receipt.ReceiptService;
import receipt.models.Item;
import receipt.models.Receipt;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class UnitTest {

    @Test
    public void testProcessReceiptOne() {

        ArrayList<Item> requestItems = new ArrayList<Item>();
        requestItems.add(new Item("Mountain Dew 12PK","6.49"));
        requestItems.add(new Item("Emils Cheese Pizza","12.25"));
        requestItems.add(new Item("Knorr Creamy Chicken","1.26"));
        requestItems.add(new Item("Doritos Nacho Cheese","3.35"));
        requestItems.add(new Item("   Klarbrunn 12-PK 12 FL OZ  ","12.00"));
        Receipt requestReceipt = new Receipt("Target", "2022-01-01", "13:01", requestItems, "35.35");
        String id = ReceiptService.processReceipt(requestReceipt).id;

        int result = ReceiptService.getPointsById(id).points;
        int expectedOutput = 0;
        //retailer name has 6 characters
        expectedOutput += 6;
        //4 items (2 pairs @ 5 points each)
        expectedOutput += 10;
        /* "Emils Cheese Pizza" is 18 characters (a multiple of 3)
        item price of 12.25 * 0.2 = 2.45, rounded up is 2 points */
        expectedOutput += 2;
        /* "Klarbrunn 12-PK 12 FL OZ" is 24 characters (a multiple of 3)
            item price of 12.00 * 0.2 = 2.4, rounded up is 2 points */
        expectedOutput += 2;
        // purchase day is odd
        expectedOutput += 6;

        assertEquals(expectedOutput, result);
    }

    @Test
    public void testProcessReceiptTwo() {

        ArrayList<Item> requestItems = new ArrayList<Item>();
        requestItems.add(new Item("Gatorade","2.25"));
        requestItems.add(new Item("Gatorade","2.25"));
        requestItems.add(new Item("Gatorade","2.25"));
        requestItems.add(new Item("Gatorade","2.25"));
        Receipt requestReceipt = new Receipt("M&M Corner Market", "2022-03-20", "14:33", requestItems, "9.00");
        String id = ReceiptService.processReceipt(requestReceipt).id;

        int result = ReceiptService.getPointsById(id).points;
        int expectedOutput = 0;
        //total is a round dollar amount
        expectedOutput += 50;
        //total is a multiple of 0.25
        expectedOutput += 25;
        //retailer name (M&M Corner Market) has 14 alphanumeric characters
        expectedOutput += 14;
        //time 2:33pm is between 2:00pm and 4:00pm
        expectedOutput += 10;
        //4 items (2 pairs @ 5 points each)
        expectedOutput += 10;
        assertEquals(expectedOutput, result);
    }

    @Test
    public void testValidateReceiptJsonMissingRetailer() {
        ArrayList<Item> requestItems = new ArrayList<Item>();
        Receipt requestReceipt = new Receipt("", "2023-07-02", "10:00", requestItems, "100.0");
        RuntimeException exception = assertThrows(RuntimeException.class, () -> ReceiptController.validateReceiptJson(requestReceipt));
        assertEquals("Retailer not provided", exception.getMessage());
    }

    @Test
    public void testValidateReceiptJsonMissingPurchaseDate() {
        ArrayList<Item> requestItems = new ArrayList<Item>();
        Receipt requestReceipt = new Receipt("Target","", "10:00", requestItems, null);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> ReceiptController.validateReceiptJson(requestReceipt));
        assertEquals("Purchase date not provided", exception.getMessage());
    }

    @Test
    public void testValidateReceiptJsonMissingPurchaseTime() {
        ArrayList<Item> requestItems = new ArrayList<Item>();
        Receipt requestReceipt = new Receipt("Target","2023-07-02", "", requestItems, null);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> ReceiptController.validateReceiptJson(requestReceipt));
        assertEquals("Purchase time not provided", exception.getMessage());
    }


    @Test
    public void testValidateReceiptJsonMissingTotal() {
        ArrayList<Item> requestItems = new ArrayList<Item>();
        Receipt requestReceipt = new Receipt("Target", "2023-07-02", "10:00", requestItems, null);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> ReceiptController.validateReceiptJson(requestReceipt));
        assertEquals("Total price not provided", exception.getMessage());
    }
}