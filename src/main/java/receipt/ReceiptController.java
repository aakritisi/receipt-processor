package receipt;


import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import io.javalin.plugin.openapi.annotations.*;
import receipt.models.ErrorResponse;
import receipt.models.IDResponse;
import receipt.models.PointsResponse;
import receipt.models.Receipt;


// This is a controller, it contains the logic related to client/server
public class ReceiptController {

    @OpenApi(
            summary = "Process Receipt",
            operationId = "process",
            path = "/receipts/process",
            method = HttpMethod.POST,
            tags = {"receipt"},
            requestBody = @OpenApiRequestBody(content = {@OpenApiContent(from = Receipt.class)}),
            responses = {
                    @OpenApiResponse(status = "201", content = {@OpenApiContent(from = String.class)}),
                    @OpenApiResponse(status = "400", content = {@OpenApiContent(from = ErrorResponse.class)})
            }
    )

    public static void process(Context ctx) {
        Receipt receipt = ctx.bodyAsClass(Receipt.class);
        System.out.println(receipt.retailer);
        validateReceiptJson(receipt);
        IDResponse id = ReceiptService.processReceipt(receipt);
        ctx.json(id);

    }

    @OpenApi(
            summary = "Get points by receipt ID",
            operationId = "getPoints",
            path = "/receipts/{id}/points",
            method = HttpMethod.GET,
            pathParams = {@OpenApiParam(name = "id", description = "Receipt ID")},
            tags = {"receipt"},
            responses = {
                    @OpenApiResponse(status = "200", content = {@OpenApiContent(from = Integer.class)}),
                    @OpenApiResponse(status = "400", content = {@OpenApiContent(from = ErrorResponse.class)}),
                    @OpenApiResponse(status = "404", content = {@OpenApiContent(from = ErrorResponse.class)})
            }
    )

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

