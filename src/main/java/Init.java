import io.javalin.Javalin;
import receipt.ReceiptController;
import io.javalin.openapi.plugin.OpenApiConfiguration;
import io.javalin.openapi.plugin.OpenApiPlugin;
import io.javalin.openapi.plugin.redoc.ReDocConfiguration;
import io.javalin.openapi.plugin.redoc.ReDocPlugin;
import io.javalin.openapi.plugin.swagger.SwaggerConfiguration;
import io.javalin.openapi.plugin.swagger.SwaggerPlugin;
import static io.javalin.apibuilder.ApiBuilder.*;


public class Init {
    public static void main(String[] args) {
        Javalin.create(config -> {
            OpenApiConfiguration openApiConfiguration = new OpenApiConfiguration();
            openApiConfiguration.getInfo().setTitle("Javalin OpenAPI example");
            config.plugins.register(new OpenApiPlugin(openApiConfiguration));
            config.plugins.register(new SwaggerPlugin(new SwaggerConfiguration()));
            config.plugins.register(new ReDocPlugin(new ReDocConfiguration()));
        }).routes(() -> {
            path("receipts", () -> {
                path("process", () -> post(ReceiptController::process));
                path("{id}/points", () -> get(ReceiptController::getPoints));
            });
        }).start(7002);

        System.out.println("Check out ReDoc docs at http://localhost:7002/redoc");
        System.out.println("Check out Swagger UI docs at http://localhost:7002/swagger-ui");
    }
}