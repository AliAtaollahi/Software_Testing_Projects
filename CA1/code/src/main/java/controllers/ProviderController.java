package controllers;

import service.Baloot;
import model.Commodity;
import model.Provider;
import exceptions.NotExistentProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class ProviderController {
    @GetMapping(value = "/providers/{id}")
    public ResponseEntity<Provider> getProvider(@PathVariable String id) {
        try {
            Provider provider = Baloot.getInstance().getProviderById(id);
            return new ResponseEntity<>(provider, HttpStatus.OK);
        } catch (NotExistentProvider e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/providers/{id}/commodities")
    public ResponseEntity<ArrayList<Commodity>> getProvidedCommodities(@PathVariable String id) {
        ArrayList<Commodity> commodities = Baloot.getInstance().getCommoditiesProvidedByProvider(id);
        return new ResponseEntity<>(commodities, HttpStatus.OK);
    }
}
