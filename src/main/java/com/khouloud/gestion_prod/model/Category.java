package com.khouloud.gestion_prod.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name ="categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "designation")
    private String designation;

    @OneToMany (mappedBy = "category", cascade = CascadeType.ALL)
    private List<Product> products;

    /*@JsonIgnore*/
    @ManyToOne /*(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)*/
    @JoinColumn(name = "fId", insertable = false, updatable = false)
    private Fournisseur fournisseur;

    private Long fId;



    public Category() {

    }

    public Category(long id, String designation, List<Product> products, Fournisseur fournisseur) {
        this.id = id;
        this.designation = designation;
        this.products = products;
        this.fournisseur = fournisseur;
    }

    @JsonBackReference
    public Fournisseur getFournisseur() {
        return fournisseur;
    }

    public void setFournisseur(Fournisseur fournisseur) {
        this.fournisseur = fournisseur;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    @JsonManagedReference
    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Long getFId() {
        return fId;
    }

    public void setFId(Long fournisseurId) {
        this.fId = fournisseurId;
    }


}
