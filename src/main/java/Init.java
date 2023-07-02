import io.javalin.Javalin;
import receipt.ReceiptController;
import static io.javalin.apibuilder.ApiBuilder.*;


public class Init {
    public static void main(String[] args) {
        Javalin.create().routes(() -> {
            path("receipts", () -> {
                path("process", () -> post(ReceiptController::process));
                path("{id}/points", () -> get(ReceiptController::getPoints));
            });
        }).start(7002);

    }
}