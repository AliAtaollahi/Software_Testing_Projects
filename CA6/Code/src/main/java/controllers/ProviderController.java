package controllers;

import exceptions.NotExistentProvider;
import model.Commodity;
import model.Provider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import service.Baloot;

import java.util.ArrayList;

@RestController
public class ProviderController {
    private Baloot baloot = Baloot.getInstance();

    public void setBaloot(Baloot baloot) {
        this.baloot = baloot;
    }
    @GetMapping(value = "/providers/{id}")
    public ResponseEntity<Provider> getProvider(@PathVariable String id) {
        try {
            Provider provider = baloot.getProviderById(id);
            return new ResponseEntity<>(provider, HttpStatus.OK);
        } catch (NotExistentProvider e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/providers/{id}/commodities")
    public ResponseEntity<ArrayList<Commodity>> getProvidedCommodities(@PathVariable String id) {
        ArrayList<Commodity> commodities = baloot.getCommoditiesProvidedByProvider(id);
        return new ResponseEntity<>(commodities, HttpStatus.OK);
    }
}
