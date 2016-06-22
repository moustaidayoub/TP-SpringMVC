package org.moustaid.dao;

import java.util.Date;
import java.util.List;

import org.moustaid.entities.Etudiant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EtudiantRepository extends JpaRepository<Etudiant, Long>{
	public List<Etudiant> findByNom(String name);
	public Page<Etudiant> findByNom(String name,Pageable pageable);
	
	@Query("select e from Etudiant e where e.nom like :x")
	public Page<Etudiant> rechercherEtudiantsByMC(@Param("x")String mc,Pageable pageable);
	
	@Query("select e from Etudiant e where e.dateNaissance between :x and :y")
	public List<Etudiant> rechercherEtudiantsByDate(@Param("x")Date d1,@Param("y")Date d2);

}
