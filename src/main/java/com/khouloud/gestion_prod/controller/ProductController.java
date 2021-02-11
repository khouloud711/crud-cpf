package com.khouloud.gestion_prod.controller;


import com.khouloud.gestion_prod.exception.ResourceNotFoundException;
import com.khouloud.gestion_prod.model.Category;
import com.khouloud.gestion_prod.model.Fournisseur;
import com.khouloud.gestion_prod.model.Product;
import com.khouloud.gestion_prod.repository.CategoryRepository;
import com.khouloud.gestion_prod.repository.ProductRepository;
import com.khouloud.gestion_prod.service.ExportProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
@RequestMapping("/api/v1/")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    @Autowired
    private ExportProductService exportProductService;

    private List<Product> productsList = new CopyOnWriteArrayList<>();

    //get products
    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    //get all products by categ
    /*@GetMapping("/products/{id}")
    public List<Product> getAllProductsByCateg(@PathVariable Long id){
        List<Product>Li=new ArrayList<>();
        productsList=getAllProducts();
        productsList.stream().forEach(c->{
            if(c.getCategory().getId()==id)
                Li.add(c);
        });
        return Li;
    }*/

    //post prod
   @PostMapping("/products")
    public Product createProduct(@RequestBody Product product) {
        return productRepository.save(product);
    }

    //get prod by id
    @GetMapping("/product/{id}")
    public Optional<Product> getProductById(@PathVariable Long id) {
        return productRepository.findById(id);
    }

    //update prod
    @PutMapping("/product/{id}")
    public ResponseEntity <Product> updateProduct(@PathVariable Long id, @RequestBody Product productDetails) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("product not found with id:"+ id));

        product.setCategory(productDetails.getCategory());
        product.setDescription(productDetails.getDescription());
        product.setPrix(productDetails.getPrix());
        product.setNom(productDetails.getNom());
        product.setQuantite(productDetails.getQuantite());

        product.setDateUpdate(productDetails.getDateUpdate());
        Product updatedProduct = productRepository.save(product);
        return  ResponseEntity.ok(updatedProduct);
    }

    //delete prod
   /* @DeleteMapping("/product/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteProduct(@PathVariable Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id:"+ id));

        productRepository.delete(product);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted",Boolean.TRUE);
    });
        return  ResponseEntity.ok(response);
    }*/

    @DeleteMapping("/product/{id}")
    public void deleteProduct(@PathVariable Long id){
        productRepository.deleteById(id);
    }

    @GetMapping("/products/category/{id}/products")
    public List<Product> getProductsByCategory(@PathVariable Long id){
       return productRepository.findByCategoryId(id);
    }


    @GetMapping("products/{id}/export/pdf")
    public ResponseEntity<InputStreamResource> exportTermsPdf(@PathVariable Long id){
        List<Product>products=productRepository.findByCategoryId(id);
        ByteArrayInputStream bais=exportProductService.productsPDF(products);
        HttpHeaders headers=new HttpHeaders();
        headers.add("Content-Disposition","inline; filename=product.pdf");
        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body((new InputStreamResource(bais)));
    }

    @GetMapping("products/{id}/export/excel")
    public ResponseEntity<InputStreamResource> exportTermsExcel(@PathVariable Long id) throws IOException {
        List<Product>products=productRepository.findByCategoryId(id);
        ByteArrayInputStream bais=exportProductService.productsExcel(products);
        HttpHeaders headers=new HttpHeaders();
        headers.add("Content-Disposition","inline; filename=product.xlsx");
        return ResponseEntity.ok().headers(headers).body((new InputStreamResource(bais)));
    }
}
