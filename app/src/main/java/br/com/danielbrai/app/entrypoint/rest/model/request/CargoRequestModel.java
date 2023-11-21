package  br.com.danielbrai.app.entrypoint.rest.model.request;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CargoRequestModel {

    private double xOrigin;
    private double yOrigin;
    private double xDestiny;
    private double yDestiny;
    private double capacity;
}
