package org.moustaid;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.moustaid.dao.EtudiantRepository;
import org.moustaid.entities.Etudiant;
import org.neo4j.cypher.internal.compiler.v2_2.perty.recipe.formatErrors;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@SpringBootApplication
public class TpSpringMvcApplication {

	public static void main(String[] args) throws ParseException {
		ApplicationContext ctx=SpringApplication.run(TpSpringMvcApplication.class, args);
		EtudiantRepository etdRepo=ctx.getBean(EtudiantRepository.class);
		//insertion
		/*DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		etdRepo.save(new Etudiant("Ayoub",df.parse("1993-02-04"),"ayoub@gmail.com","ayoub.jpg"));
		etdRepo.save(new Etudiant("Moustaid",df.parse("1994-12-14"),"moustaid@gmail.com","moustaid.jpg"));
		etdRepo.save(new Etudiant("Ahmed",df.parse("1984-01-10"),"ahmed@gmail.com","ahmed.jpg"));
		
	    //Page<Etudiant> etds=etdRepo.findAll(new PageRequest(0, 2));
		Page<Etudiant> etds=etdRepo.rechercherEtudiantsByMC("%b%",new PageRequest(0, 2));
	    etds.forEach(e->System.out.println(e.getNom()));*/
		

	}
}
