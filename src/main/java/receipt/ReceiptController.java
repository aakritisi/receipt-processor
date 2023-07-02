package receipt;

import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import receipt.models.IDResponse;
import receipt.models.PointsResponse;
import receipt.models.Receipt;


// This is a controller, it contains the logic related to client/server
public class ReceiptController {

    public static void process(Context ctx) {
        Receipt receipt = ctx.bodyAsClass(Receipt.class);
        validateReceiptJson(receipt);
        IDResponse id = ReceiptService.processReceipt(receipt);
        ctx.json(id);

    }

    public static void getPoints(Context ctx) {
        PointsResponse pointsResponse = ReceiptService.getPointsById(ctx.pathParamAsClass("id", String.class).get());
        if (pointsResponse.points == null) {
            throw new NotFoundResponse("Receipt not found");
        } else {
            ctx.json(pointsResponse);
        }
    }

    // Validates the JSON payload for a missing value in receipt
    public static void validateReceiptJson(Receipt receipt) {
        if(receipt.retailer == null || receipt.retailer.equals(""))
            throw new RuntimeException("Retailer not provided");
        if(receipt.purchaseTime == null || receipt.purchaseTime.equals(""))
            throw new RuntimeException("Purchase time not provided");
        if(receipt.purchaseDate == null || receipt.purchaseDate.equals(""))
            throw new RuntimeException("Purchase date not provided");
        if(receipt.total == null || receipt.total.equals(""))
            throw new RuntimeException("Total price not provided");
    }


}

