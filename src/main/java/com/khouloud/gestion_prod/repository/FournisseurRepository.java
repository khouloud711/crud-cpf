package com.khouloud.gestion_prod.repository;

import com.khouloud.gestion_prod.model.Category;
import com.khouloud.gestion_prod.model.Fournisseur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FournisseurRepository extends JpaRepository<Fournisseur, Long> {

    Fournisseur findByEmailId (String emailId);




}
