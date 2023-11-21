package  br.com.danielbrai.app.entrypoint.rest.model.request;

import br.com.danielbrai.core.entity.enums.Operations;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RouteRequestModel {
    private double xOrigin;
    private double yOrigin;
    private double xDestiny;
    private double yDestiny;
    private Operations operation;

}
