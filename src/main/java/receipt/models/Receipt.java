package receipt.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import receipt.models.Item;

import java.util.ArrayList;

public class Receipt {

    public String retailer;
    public String purchaseDate;
    public String purchaseTime;

    public ArrayList<Item> items;

    public String total;
    @JsonCreator
    public Receipt(@JsonProperty("retailer") String retailer, @JsonProperty("purchaseDate") String purchaseDate, @JsonProperty("purchaseTime") String purchaseTime, @JsonProperty("items") ArrayList<Item> items, @JsonProperty("total") String total) {
        this.retailer = retailer;
        this.purchaseDate = purchaseDate;
        this.purchaseTime = purchaseTime;
        this.items = items;
        this.total = total;
    }
}

