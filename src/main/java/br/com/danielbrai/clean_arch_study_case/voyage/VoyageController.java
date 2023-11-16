package br.com.danielbrai.clean_arch_study_case.voyage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/voyage")
public class VoyageController {

    @Autowired
    VoyageService voyageService;

    @Autowired
    VoyageRepository repository;


    @PostMapping()
    ResponseEntity<Voyage> createVoyage(@RequestBody VoyageRequestModel voyageRequestModel) {
        Voyage voyage = this.voyageService.createShipment(voyageRequestModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(voyage);
    }

    @GetMapping("/{id}")
    ResponseEntity<Voyage> getVoyage(@PathVariable Long id) {
        Voyage voyage = this.voyageService.retriveVoyage(id);
        return ResponseEntity.ok(voyage);
    }
}
