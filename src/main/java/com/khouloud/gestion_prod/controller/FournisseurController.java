package com.khouloud.gestion_prod.controller;


import com.khouloud.gestion_prod.exception.ResourceNotFoundException;
import com.khouloud.gestion_prod.model.Category;
import com.khouloud.gestion_prod.model.Fournisseur;
import com.khouloud.gestion_prod.repository.FournisseurRepository;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/")
public class FournisseurController {

    @Autowired
    private FournisseurRepository fournisseurRepository;


    //get all fourniss
    @GetMapping("/fournisseurs")
    public List<Fournisseur> getAllFournisseurs() {
        return fournisseurRepository.findAll();
    }

    //create fourniss
    @PostMapping("/fournisseurs")
    public Fournisseur createFournisseur(@RequestBody Fournisseur fournisseur) {
        return fournisseurRepository.save(fournisseur);
    }

    //get fourn by id
    @GetMapping("/fournisseur/{id}")
    public Optional<Fournisseur> getFournisseurById(@PathVariable Long id) {

        return fournisseurRepository.findById(id);
    }

    //update fourn
    @PutMapping("/fournisseur/{id}")
    public ResponseEntity<Fournisseur> updateFournisseur(@PathVariable Long id, @RequestBody Fournisseur fournisseurDetails) {

        Fournisseur fournisseur = fournisseurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("fournisseur not found with id:" + id));

        fournisseur.setAdresse(fournisseurDetails.getAdresse());
        fournisseur.setEmailId(fournisseurDetails.getEmailId());
        fournisseur.setFirstName(fournisseurDetails.getFirstName());
        fournisseur.setLastName(fournisseurDetails.getLastName());
        fournisseur.setTelephone(fournisseurDetails.getTelephone());

        Fournisseur updatedFournisseur = fournisseurRepository.save(fournisseur);
        return ResponseEntity.ok(updatedFournisseur);
    }

    //delete fourn
   /* @DeleteMapping("/fournisseur/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteFournisseur(@PathVariable Long id) {

        Fournisseur fournisseur = fournisseurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("fournisseur not found with id:" + id));

        fournisseurRepository.delete(fournisseur);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }*/

    @DeleteMapping("/fournisseur/{id}")
    public void deleteFournisseur(@PathVariable Long id){
        fournisseurRepository.deleteById(id);
    }

    // get all categ of a specefic furn
    @GetMapping("/fournisseur/{id}/categories")
    public List<Category> getCategoriesByFurn(@PathVariable Long id) {
        Optional<Fournisseur> fournisseur = fournisseurRepository.findById(id);

        if (fournisseur.isPresent()) {
            Fournisseur newFournisseur = fournisseur.get();
            return newFournisseur.getCategories();
        }
        return null;
    }

}
