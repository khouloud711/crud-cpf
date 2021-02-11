package com.khouloud.gestion_prod.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "description")
    private String description;

    @Column(name = "prix")
    private double prix;

    @Column(name = "quantite")
    private int quantite;

    @Column(name="dateP")
    @CreationTimestamp
    private LocalDateTime dateP;

    @Column(name="dateUpdate")
    @UpdateTimestamp
    private LocalDateTime dateUpdate;

    /*@JsonIgnore*/
    @ManyToOne/*(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)*/
    @JoinColumn(name = "cId",insertable = false, updatable = false)
    private Category category;

    private Long cId;

    public Product(){

    }

    public Product(long id, String nom, String description, double prix, int quantite, LocalDateTime dateP, LocalDateTime dateUpdate, Category category) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.prix = prix;
        this.quantite = quantite;
        this.dateP = dateP;
        this.dateUpdate = dateUpdate;
        this.category = category;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    @JsonBackReference
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Long getcId() {
        return cId;
    }

    public void setcId(Long cId) {
        this.cId = cId;
    }

    public LocalDateTime getDateP() {
        return dateP;
    }

    public void setDateP(LocalDateTime dateP) {
        this.dateP = dateP;
    }

    public LocalDateTime getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(LocalDateTime dateUpdate) {
        this.dateUpdate = dateUpdate;
    }
}
