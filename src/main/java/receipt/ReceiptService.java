package receipt;


import receipt.models.IDResponse;
import receipt.models.Item;
import receipt.models.PointsResponse;
import receipt.models.Receipt;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.*;

public class ReceiptService {
    private static Map<String,Integer> receiptPoints = new HashMap<>();

    // Processes receipt, generates a random id and assign points to that id
    public static IDResponse processReceipt(Receipt receipt){
        String uuid = UUID.randomUUID().toString();
        int points = 0;
        String retailer = receipt.retailer;
        float total = Float.parseFloat(receipt.total);
        ArrayList<Item> items = receipt.items;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd" );
        Date purchaseDate = null;
        try {
            purchaseDate = formatter.parse( receipt.purchaseDate );
        } catch (ParseException e) {
            throw new RuntimeException("Purchase Date format is Incorrect");
        }
        LocalTime purchaseTime = LocalTime.parse( receipt.purchaseTime ) ;
        // Counts the number of alphanumeric characters in retailer name and adds one point for each
        for(int i =0; i<retailer.length(); i++)
        {
            if ((retailer.charAt(i) >= '0' & retailer.charAt(i) <= '9') || (retailer.charAt(i) >= 'a' && retailer.charAt(i) <= 'z') || (retailer.charAt(i) >= 'A' && retailer.charAt(i) <= 'Z')){
                points++;
            }
        }

        //Checks if the total is a round dollar amount with no cents and adds 50 points if is
        if(total == Math.round(total))
        {
            points += 50;
        }

        // Adds 25 points if the total value is a multiple of 0.25
        if(total%0.25 == 0)
        {
            points += 25;
        }

        //Adds 5 points for every two items on the receipt
        points += (items.size()/2)*5;

        /* Checks for each item if the trimmed length of description is a multiple of 3,
         if true multiply the corresponding price by 0.2 and round up to the nearest integer.
         Finally, adds the resulting value to the points.
         */
        for (Item item : items) {
            if (item.shortDescription.trim().length() % 3 == 0) {
                points += Math.round(Float.parseFloat(item.price) * 0.2);
            }
        }

        // Adds 6 points if the day in the purchase date is odd.
        if(purchaseDate.getDate()%2 != 0)
            points += 6;

        // Adds 10 points if the time of purchase is after 2:00pm and before 4:00pm.
        if(purchaseTime.isAfter(LocalTime.parse("14:00")) && purchaseTime.isBefore(LocalTime.parse("16:00")))
        {
            points += 10;
        }
        receiptPoints.put(uuid,points);
        return new IDResponse(uuid);
    }

    // Returns points corresponding to the receipt id
    public static PointsResponse getPointsById(String id) {
        return new PointsResponse(receiptPoints.get(id));
    }



}
