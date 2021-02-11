package com.khouloud.gestion_prod.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Entity
@Table(name ="fournisseurs")
public class Fournisseur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email_id")
    private String emailId;
    /*@Column(name = "password")
    private String password;*/
    @Column(name = "adresse")
    private String adresse;
    @Column(name = "telephone")
    private int telephone;

    @OneToMany ( mappedBy = "fournisseur", cascade = CascadeType.ALL)
    private List<Category> categories ;

    public Fournisseur() {

    }
    /*public Fournisseur(String emailId,String password) {
        this.emailId = emailId;
        this.password = password;
    }*/
    public Fournisseur(long id, String firstName, String lastName, String emailId ,String password, String adresse, int telephone, List<Category> categories) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailId = emailId;
        //this.password = password;
        this.adresse = adresse;
        this.telephone = telephone;
        this.categories = categories;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public int getTelephone() {
        return telephone;
    }

    public void setTelephone(int telephone) {
        this.telephone = telephone;
    }

    @JsonManagedReference
    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

   /* public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }*/
}

