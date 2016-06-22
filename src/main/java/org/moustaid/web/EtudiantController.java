package org.moustaid.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.moustaid.dao.EtudiantRepository;
import org.moustaid.entities.Etudiant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(value="/etudiant")
public class EtudiantController {
	@Autowired //inject object
private EtudiantRepository etudiantRepository;
	@Value("${dir.images}")//inject property
	private String imageDir;
	@RequestMapping(value="/index")
	public String Index(Model model,@RequestParam(name="page",defaultValue="0")int p,@RequestParam(name="motCle",defaultValue="")String mc)
	{
		//Page<Etudiant> pageEtudiants=etudiantRepository.findAll(new PageRequest(p, 5));
		Page<Etudiant> pageEtudiants=etudiantRepository.rechercherEtudiantsByMC("%"+mc+"%",new PageRequest(p, 5));
		model.addAttribute("pageEtudiants", pageEtudiants);
		//pour afficher les pages dans la vue
		int pageCount=pageEtudiants.getTotalPages();
		int[]pages=new int[pageCount];
		for(int i=0;i<pageCount;i++)
		{
			pages[i]=i;
		}
		model.addAttribute("pages", pages);
		model.addAttribute("pageCourante", p);
		model.addAttribute("motCle",mc);
		
		return "etudiants";
	}
	
	@RequestMapping(value="/form",method=RequestMethod.GET)
	public String formEtudiant(Model model){
		model.addAttribute("etudiant",new Etudiant());
		return "formEtudiant";
	}
	
	@RequestMapping(value="/saveEtudiant",method=RequestMethod.POST)
	public String save(@Valid Etudiant e,BindingResult bindingResult,@RequestParam("picture")MultipartFile file) throws IllegalStateException, IOException{
		
		if(bindingResult.hasErrors())
			return "formEtudiant";
		if(!(file.isEmpty())) 
			e.setPhoto(file.getOriginalFilename());
		
		etudiantRepository.save(e);
		if(!(file.isEmpty()))
		{
			e.setPhoto(file.getOriginalFilename());
			file.transferTo(new File(imageDir+e.getId()));
		}
		
		return "redirect:index";
	}
	
	@RequestMapping(value="/getPhoto",produces=MediaType.IMAGE_JPEG_VALUE)
	@ResponseBody
	public byte[] getPhoto(Long id) throws FileNotFoundException, IOException
	{
		File f=new File(imageDir+id);
		return IOUtils.toByteArray(new FileInputStream(f));
	}
	
	@RequestMapping(value="/supprimer")
	public String remove(Long id)	{
		etudiantRepository.delete(id);
		return "redirect:index";	
		}
	
	@RequestMapping(value="/modifier")
	public String modifier(Long id,Model model)	{
		Etudiant e=etudiantRepository.getOne(id);
		model.addAttribute("etudiant", e);
		return "modifierEtudiant";	
		}
	
	@RequestMapping(value="/updateEtudiant",method=RequestMethod.POST)
	public String update(@Valid Etudiant e,BindingResult bindingResult,@RequestParam("picture")MultipartFile file) throws IllegalStateException, IOException{
		
		if(bindingResult.hasErrors())
			return "formEtudiant";
		if(!(file.isEmpty())) 
			e.setPhoto(file.getOriginalFilename());
		
		etudiantRepository.save(e);
		if(!(file.isEmpty()))
		{
			e.setPhoto(file.getOriginalFilename());
			file.transferTo(new File(imageDir+e.getId()));
		}
		
		return "redirect:index";
	}
	
}
