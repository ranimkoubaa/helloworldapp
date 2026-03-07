package iset.master.spring.repository;

import java.util.Date;
import java.util.List;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import iset.master.spring.model.Produit;

public interface ProduitRepository extends JpaRepository<Produit, Long> {

    @Query("select p from Produit p where p.designation like %:x%")
    public List<Produit> findByDesignation(@Param("x") String mc);

    @Query("select p from Produit p where p.designation like %:mc% and p.prix > :prix")
    public List<Produit> findByDesignationAndPrix(
            @Param("mc") String mc,
            @Param("prix") double prix);
    @Query("update Produit p set p.designation = :designation where p.id = :id")
    @Modifying
    @Transactional
    public int mettreAJourDesignation(
            @Param("designation") String designation,
            @Param("id") Long idProduit);

    List<Produit> findByPrixGreaterThan(double prixMin);
    List<Produit> findAllByOrderByPrixAsc();
    List<Produit> findByDateAchatAfter(Date date);
}