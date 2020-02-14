package com.samrak.CV;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.samrak.CV.entities.File;
import com.samrak.CV.entities.Me;
import com.samrak.CV.entities.Offer;
import com.samrak.CV.entities.Response;
import com.samrak.CV.entities.Role;
import com.samrak.CV.entities.Skills;
import com.samrak.CV.entities.UploadFileResponse;
import com.samrak.CV.entities.Users;
import com.samrak.CV.exceptions.FilePathException;
import com.samrak.CV.service.ServiceFile;
import com.samrak.CV.service.ServiceMe;
import com.samrak.CV.service.ServiceNewUserRegistration;
import com.samrak.CV.service.ServiceOffer;
import com.samrak.CV.service.ServiceRole;
//import com.samrak.CV.service.ServiceRecruiter;
//import com.samrak.CV.service.ServiceResponse;
import com.samrak.CV.service.ServiceSkills;
import com.samrak.CV.service.UserDetailsServices;

//@Controller
@RestController
public class CvController {
	
	@Autowired
	private ServiceMe serveMe;
//	
//	@Autowired 
//	private ServiceRecruiter serveRecruiter;

	@Autowired
	private ServiceSkills serveSkills;
	
	@Autowired
	private ServiceOffer serveOffer;
//	
//	@Autowired
//	private ServiceResponse serveResp;
	
	@Autowired
	private UserDetailsServices userDetailsServices;
	
	@Autowired
	private ServiceFile serveFile;
	
	@Autowired
	private ServiceRole serveRole;
	
	
	
	


	
	@CrossOrigin(origins = "http://localhost:3000") 
	@PostMapping("/signin")
	public String signin(@RequestBody Users user) {
		
		
		//TODO: improvement to delete any other role other than "USER"
	
		Optional<Role> roleUser=serveRole.get(2L);
		Set<Role>userRole=new HashSet<Role>();
		userRole.add(roleUser.get());
		user.setRoles(userRole);

		userDetailsServices.registerUser(user);
		
		return "redirect:/login";
	}
	
	@CrossOrigin(origins = "http://localhost:3000")
	@RequestMapping(value = {"/","/login","/register","/forgot"})
    public String index() {
        return "index";
	}
/*
	@CrossOrigin(origins = "http://localhost:3000") 
	@PostMapping("/login")
	public Users login(@RequestBody Users user) {
		
		
		//TODO: improvement to delete any other role other than "USER"
	
		String usernameReact=user.getUsername();
		Users foundUser=userDetailsServices.findByUsername(usernameReact);
		
		
		return foundUser;
	}
*/
	
	
	
	
	//avec l'annotation @RestController en tete de classe
	@GetMapping("admin/profil")
	public Me getMe(){
		
		//TODO: retrieve the admin role
		return serveMe.get(1L).get();
		
	}
	
	//Me
	//https://stackoverflow.com/questions/49049312/cors-error-when-connecting-local-react-frontend-to-local-spring-boot-middleware	
	@CrossOrigin(origins = "http://localhost:3000") /*SECURITY: Cross-Origin Resource Sharing (CORS). Allows REACT.JS on port 3000 to consumes REST service*/  
	@RequestMapping("/admin")
	public List homePage(Model model) {
		
		List<Me> me = serveMe.listAll();
		model.addAttribute("me",me);
		//return "index";
		return me;
		
	}
	
	@CrossOrigin(origins = "http://localhost:3000") 
	@RequestMapping("/admin/offers")
	public List jobOffer(Model model) {


		List<Offer>offersList= serveOffer.listAll();
		model.addAttribute("offersList",offersList);
		//return "skills";
		return offersList;
	}
	
	@CrossOrigin(origins = "http://localhost:3000") 
	@RequestMapping("/admin/respondToOffer/{id}")
	public void respondToOffer(@PathVariable(name="id") Long id,@RequestBody Offer offer, @RequestBody Response response){
		
		Offer offerInDB=serveOffer.get(id).get();
		
		List<Response> responseList=new ArrayList<Response>();
		
		if(offerInDB.getResponse()!=null) {
		responseList.addAll(offerInDB.getResponse());
		}
		responseList.add(response);
		
		offerInDB.setResponse(responseList);
		
		
	}

	/*TODO: SKILLS : add code to add - edit - delete skills*/	

	@CrossOrigin(origins = "http://localhost:3000") 
	@RequestMapping("/skills")
	public List skills(Model model) {
		List<Skills>skillsList= serveSkills.listAll();
		model.addAttribute("skillsList",skillsList);
		//return "skills";
		return skillsList;
	}
	

	
	
	
	@CrossOrigin(origins = "http://localhost:3000") 
	@RequestMapping("/user/submittedOffers")
	public List userOfferList(Model model) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		List<Offer>userOfferList= serveOffer.listUserOffers(userDetailsServices.userCurrentID(auth.getName()));
		//model.addAttribute("userOfferList",userOfferList);
		
		return userOfferList;
	}
	
	@CrossOrigin(origins = "http://localhost:3000") 
	@RequestMapping("/showNewOfferForm")
	public void makeNewOffer(Model model) {
		Offer offer = new Offer();
		model.addAttribute("offer",offer);
		
		//on retourne une page Thymeleaf ou autre desiree pour editer le contenu de l'offre
		//return "new_offer";
		
		
	}
	
	@CrossOrigin(origins = "http://localhost:3000") 
	@PostMapping("/user/saveNewOffer")
	public String newOffer(@RequestBody Offer offer) {
	//public String newOffer(@ModelAttribute("offer") Offer offer) {
		
		offer.setMe(serveMe.get(1L).get());
		serveOffer.save(offer);
		
		return "redirect:/offers";
	}
	
	@CrossOrigin(origins = "http://localhost:3000") 
	@PutMapping("/user/update/{id}")
	public String editOffer(@PathVariable(name="id") Long id,@RequestBody Offer offer ) {
		
		//la page edit_offer doit s'afficher dans la vue
		//ModelAndView mav=new ModelAndView("edit_offer");
		
		
		//on ne peux pas caster Optional vers une entity comme Offer directement
		//il faut refaire un get d'abord
		Offer offerInDB=serveOffer.get(id).get();
		if(offerInDB!=null) {
		
			//mav.addObject("offer",offer);
			
			serveOffer.save(offer);
		}
		
		return "redirect:/offers";
		//return mav;
		
		//ModelAndView:  https://stackoverflow.com/questions/18486660/what-are-the-differences-between-model-modelmap-and-modelandview
	}
	
	
	@CrossOrigin(origins = "http://localhost:3000") 
	@DeleteMapping(value="/user/delete/{id}")  /* Works with axios.delete(id)*/
	public String deleteProduct(@PathVariable(name="id") Long id) {
		
		serveOffer.delete(id);
		return "redirect:/offers";
	}
	
	
	/****************************************************************
	 						FILE Management 
	****************************************************************/
	//https://www.callicoder.com/spring-boot-file-upload-download-jpa-hibernate-mysql-database-example/
	//or
	//https://www.devglan.com/spring-boot/spring-boot-file-upload-download
	
	
	//@PostMapping("uploadFile") <=> @RequestMapping(value="/uploadFile", method=RequestMethod.POST)
	
	@CrossOrigin(origins = "http://localhost:3000") 
	@PostMapping("/user/uploadFile")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) throws FilePathException {
      
		//File storage into DB
		File dbFile = serveFile.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
        		.path("/downloadFile/")
        		.path(dbFile.getId().toString())
                .toUriString();

        return new UploadFileResponse(dbFile.getFileName(), fileDownloadUri,
                file.getContentType(), file.getSize());
    }

	
	@CrossOrigin(origins = "http://localhost:3000") 
	@PostMapping("/user/uploadMultipleFiles")
    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        return Arrays.asList(files)
                .stream()
                .map(file -> {
					try {
						return uploadFile(file);
					} catch (FilePathException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return null;
				})
                .collect(Collectors.toList());
    }

    
	@CrossOrigin(origins = "http://localhost:3000") 
	@GetMapping("/user/downloadFile/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long fileId) throws FileNotFoundException {
        // Load file from database
        File dbFile = serveFile.getFile(fileId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(dbFile.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getFileName() + "\"")
                .body(new ByteArrayResource(dbFile.getData()));
    }
    
    
	@CrossOrigin(origins = "http://localhost:3000") 
	@GetMapping("/user/deleteFile/{fileId}")
    public boolean deleteFile(@PathVariable Long fileId) throws FileNotFoundException {
        // Load file from database
    	
    	
    	boolean filedeleted= serveFile.deleteFile(fileId);
    	
    	
        return filedeleted;
        
    }
    
	
//	@RequestMapping("/contacts")
//	public String contacts(Model model) {
//		Optional<Me> me = serveMe.get(1L);
//		model.addAttribute("me",me);
//		return "contacts";
//	}

	
//	@RequestMapping("/recruiterSignIn")
//	public String showNewProductForm(Model model) {
//		Recruiter recruiter = new Recruiter();
//		model.addAttribute("recruiter",recruiter);
//		
//		return "new_recruiter"; 
//		// TODO: Creer la page SIGN-IN "NewRecruiter"
//	}

	//Important to have save Name of OBJECT "product" in the MODEL
	//OBJECT "product is instantiate => Passed to Model => Passed to View 
	// => Saved from Model by controller"
//	@RequestMapping(value="/saveNewRecruiter", method=RequestMethod.POST)
//	public String saveRecruiter(@ModelAttribute("recruiter") Recruiter recruiter) {
//		
//		serveRecruiter.save(recruiter);
//		return "redirect:/recruiterProfil";
//		//TODO: Creer la page RecruiterProfil
//	}
	

		
	//RecruiterLogIN
//	@RequestMapping(value="/login", method=RequestMethod.POST)
//	public ModelAndView logInPage(@ModelAttribute("recruiter") Recruiter recruiter) throws LoginNotFoundException {
//		
//		ModelAndView mav=new ModelAndView("recruiterAccessPage");
//		
//		Optional<Recruiter> recruiterFoundInDb=serveRecruiter.get(serveRecruiter.searchLogin(recruiter.getUserName(),
//				recruiter.getCompanyName(), recruiter.getEmail()));	
//		if(recruiterFoundInDb.isPresent()) {
//			mav.addObject("recruiter",recruiterFoundInDb);
//		}
//		
//		
//		return mav;
//		//TODO: creer la page recruiterAccessPage
//	}
	
//	@RequestMapping("/editRecruiter/{id}")
//	public ModelAndView showEditRecruiterForm(@PathVariable(name="id") Long id ) {
//		ModelAndView mav=new ModelAndView("edit_recruiter");
//		Optional<Recruiter> recruiter=serveRecruiter.get(id);
//		mav.addObject("recruiter",recruiter);
//		
//		return mav;
//	}
//	
//	
//	
	
	
}
