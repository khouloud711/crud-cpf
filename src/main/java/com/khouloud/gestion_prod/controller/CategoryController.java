package com.khouloud.gestion_prod.controller;


import com.khouloud.gestion_prod.exception.ResourceNotFoundException;
import com.khouloud.gestion_prod.model.Category;
import com.khouloud.gestion_prod.model.Fournisseur;
import com.khouloud.gestion_prod.model.Product;
import com.khouloud.gestion_prod.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
@RequestMapping("/api/v1/")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    private List<Category> categoriesList = new CopyOnWriteArrayList<>();


    //get Categories
    @GetMapping("/categories")
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    //create categ
    @PostMapping("/categories")
    public Category createCategory(@RequestBody Category category) {
        return categoryRepository.save(category);
    }


    //get categ by id
    @GetMapping("/category/{id}")
    public Optional<Category> getCategoryById(@PathVariable Long id) {

        return categoryRepository.findById(id);

    }

    //get all categories by fournisseur
    /*@GetMapping("/categories/{id}")
    public List<Category> getAllCategoriesByFurn(@PathVariable Long id){
        List<Category>Li=new ArrayList<>();
        categoriesList=getAllCategories();
        categoriesList.stream().forEach(c->{
            if(c.getFournisseur().getId()==id)
                Li.add(c);
        });
        return Li;
    }*/

    //update categ
    @PutMapping("/category/{id}")
    public ResponseEntity <Category> updateCategory(@PathVariable Long id, @RequestBody Category categoryDetails) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id:"+ id));

        category.setDesignation(categoryDetails.getDesignation());
        category.setFournisseur(categoryDetails.getFournisseur());
        category.setProducts(categoryDetails.getProducts());

        Category updatedCategory = categoryRepository.save(category);
        return  ResponseEntity.ok(updatedCategory);
    }


    //delete categ
    /*@DeleteMapping("/category/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteCategory(@PathVariable Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id:"+ id));

        categoryRepository.delete(category);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted",Boolean.TRUE);
        return  ResponseEntity.ok(response);
    }*/
    @DeleteMapping("/category/{id}")
    public void deleteCategory(@PathVariable Long id){
        categoryRepository.deleteById(id);
    }


    //get all products of a specefic categ
    @GetMapping("/category/{id}/products")
    public List<Product> getProductsByCateg(@PathVariable Long id){
        Optional<Category> category = categoryRepository.findById(id);

        if (category.isPresent()) {
            Category newCategory = category.get();
            return newCategory.getProducts();
        }
        return null;
    }

    @GetMapping("/categories/fournisseur/{id}/categories")
    public List<Category> getCategoriesByFurn(@PathVariable Long id){
        return categoryRepository.findByFournisseurId(id);
    }




}
