package receipt.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Item {
    public String shortDescription;

    public String price;

    public Item(@JsonProperty("shortDescription") String shortDescription, @JsonProperty("price") String price){
        this.shortDescription = shortDescription;
        this.price = price;
    }
}
