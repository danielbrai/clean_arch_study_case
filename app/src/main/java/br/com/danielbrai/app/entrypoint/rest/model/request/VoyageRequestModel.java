package  br.com.danielbrai.app.entrypoint.rest.model.request;

import lombok.Builder;
import lombok.Data;

import java.util.LinkedHashSet;
import java.util.LinkedList;
@Builder
@Data
public class VoyageRequestModel {

    private LinkedHashSet<RouteRequestModel> schedule;
    private LinkedList<CargoRequestModel> cargo;
    private double capacity;
}
