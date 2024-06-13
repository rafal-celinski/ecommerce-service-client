package pis24l.projekt.api_client.models;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Subcategory {

    @NotNull(message = "Name cannot be null")
    @Size(min = 1, max = 255, message = "Name must be between 1 and 255 characters")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
