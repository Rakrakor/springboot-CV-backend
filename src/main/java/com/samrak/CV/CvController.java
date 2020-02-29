package com.samrak.CV;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.samrak.CV.entities.AuthenticationResponse;
import com.samrak.CV.entities.DTOOffers;
import com.samrak.CV.entities.DTOUsers;
import com.samrak.CV.entities.File;
import com.samrak.CV.entities.Me;
import com.samrak.CV.entities.Offer;
import com.samrak.CV.entities.Response;
import com.samrak.CV.entities.Skills;
import com.samrak.CV.entities.UploadFileResponse;
import com.samrak.CV.entities.Users;
import com.samrak.CV.exceptions.FilePathException;
import com.samrak.CV.security.JwtUtil;
import com.samrak.CV.service.ServiceFile;
import com.samrak.CV.service.ServiceMe;
import com.samrak.CV.service.ServiceOffer;
import com.samrak.CV.service.ServiceResponse;
import com.samrak.CV.service.ServiceRole;
//import com.samrak.CV.service.ServiceRecruiter;
//import com.samrak.CV.service.ServiceResponse;
import com.samrak.CV.service.ServiceSkills;
import com.samrak.CV.service.UserDetailsServices;

//@Controller
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
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
		
	@Autowired
	private ServiceResponse serveResp;
	
	@Autowired
	private UserDetailsServices userDetailsServices;
	
	@Autowired
	private ServiceFile serveFile;
	
	@Autowired
	private ServiceRole serveRole;
	
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUtil jwtTokenUtil;
	
	
	
	
	
	//@CrossOrigin(origins = "http://localhost:3000") 
	//@CrossOrigin(origins = "https://www.samrak.info")
	//@CrossOrigin(origins = "cvfronta-env.he6mbc62qv.us-east-2.elasticbeanstalk.com")
	@CrossOrigin(origins = "https://guarded-castle-37898.herokuapp.com")
	@RequestMapping("/skills")
	public List skills(Model model) {
		List<Skills>skillsList= serveSkills.listAll();
		return skillsList;
	}
	
	//@CrossOrigin(origins = "http://localhost:3000")
	//@CrossOrigin(origins = "https://www.samrak.info")
	//@CrossOrigin(origins = "cvfronta-env.he6mbc62qv.us-east-2.elasticbeanstalk.com")
	@CrossOrigin(origins = "https://guarded-castle-37898.herokuapp.com")
	@GetMapping("/downloadCV/{fileId}")
    public ResponseEntity<Resource> downloadCV(@PathVariable Long fileId) throws FileNotFoundException {
        // Load file from database
        File dbFile = serveFile.getFile(fileId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(dbFile.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getFileName() + "\"")
                .body(new ByteArrayResource(dbFile.getData()));
    }
/*
 * Register - Login
 */
	
	//@CrossOrigin(origins = "http://localhost:3000")
	//@CrossOrigin(origins = "https://www.samrak.info")
	//@CrossOrigin(origins = "cvfronta-env.he6mbc62qv.us-east-2.elasticbeanstalk.com")
	@CrossOrigin(origins = "https://guarded-castle-37898.herokuapp.com")
	@PostMapping("/register")
//	public String register(@RequestBody Users user) {
	@JsonProperty("data")
	public String register(@RequestBody Users user) {
		
		
		//TODO: improvement to delete any other role other than "USER"
	

		userDetailsServices.registerUser(user);
		
		return "redirect:/authenticate";
	}
	
	
	//@CrossOrigin(origins = "http://localhost:3000")
	//@CrossOrigin(origins = "https://www.samrak.info")
	//@CrossOrigin(origins = "cvfronta-env.he6mbc62qv.us-east-2.elasticbeanstalk.com")
	@CrossOrigin(origins = "https://guarded-castle-37898.herokuapp.com")
	@GetMapping("/usercredentials")
//	public String register(@RequestBody Users user) {
	@JsonProperty("data")
	public List credentials() {
		
		
		Authentication auth= SecurityContextHolder.getContext().getAuthentication();
		return auth.getAuthorities().stream().collect(Collectors.toList());
		
	}
	
	//@CrossOrigin(origins = "http://localhost:3000")
	//@CrossOrigin(origins = "https://www.samrak.info")
	//@CrossOrigin(origins = "cvfronta-env.he6mbc62qv.us-east-2.elasticbeanstalk.com")
	@CrossOrigin(origins = "https://guarded-castle-37898.herokuapp.com")
	@ResponseBody
	@RequestMapping(value = "/authenticate",method= {RequestMethod.POST,RequestMethod.GET})
    public ResponseEntity<?> createAuthenticationToken(@RequestBody Users users) throws Exception{
		
		try {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(users.getUsername(), users.getUserpassword())
				);
		}catch(BadCredentialsException e) {
			 throw new Exception("Incorrect username or password", e);
		}
		final UserDetails userDetails = userDetailsServices.loadUserByUsername(users.getUsername());
		final String jwt=jwtTokenUtil.generateToken(userDetails);
		
		return ResponseEntity.ok(new AuthenticationResponse(jwt));
		
		
	}
	
	
	
/*
 * Recruiter: submit New Offer - List recruiter specific offer - Modify specific offer - Delete a specific offer
 */
	//@CrossOrigin(origins = "http://localhost:3000")
	//@CrossOrigin(origins = "https://www.samrak.info")
	//@CrossOrigin(origins = "cvfronta-env.he6mbc62qv.us-east-2.elasticbeanstalk.com")
	@CrossOrigin(origins = "https://guarded-castle-37898.herokuapp.com")
	@PostMapping("/recruiter/saveNewOffer")
	public String newOffer(@RequestBody Offer offer) throws IOException {
	//public String newOffer(@ModelAttribute("offer") Offer offer) {
		
		offer.setMe(serveMe.get(1L).get());
		
		Authentication auth= SecurityContextHolder.getContext().getAuthentication();
		offer.setUsers(userDetailsServices.findByUsername(auth.getName()));
		serveOffer.save(offer);
		return "redirect:/recruiter/submittedOffers";
		
	}

	//@CrossOrigin(origins = "http://localhost:3000")
	//@CrossOrigin(origins = "https://www.samrak.info")
	//@CrossOrigin(origins = "cvfronta-env.he6mbc62qv.us-east-2.elasticbeanstalk.com")
	@CrossOrigin(origins = "https://guarded-castle-37898.herokuapp.com")
	@RequestMapping("/recruiter/submittedOffers")
	public List userOfferList() {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		List<Offer>userOfferList= serveOffer.listUserOffers(userDetailsServices.findByUsername(auth.getName()));
		
		List <DTOOffers>userOfferDTOList= new ArrayList<>();
		
		for(Offer offer:userOfferList) {
			DTOUsers userDTO=new DTOUsers();
			userDTO.setId(offer.getuserPersonalOffers().getId());
			userDTO.setEmail(offer.getuserPersonalOffers().getEmail());
			userDTO.setUsername(offer.getuserPersonalOffers().getUsername());
			userDTO.setUsercompany(offer.getuserPersonalOffers().getUsercompany());
			userDTO.setPhonenumber(offer.getuserPersonalOffers().getPhonenumber() );
			userDTO.setStatus(offer.getuserPersonalOffers().getStatus());
			
			DTOOffers offerDTO=new DTOOffers();
			
			offerDTO.setId(offer.getId());
			offerDTO.setTitle(offer.getTitle());
			offerDTO.setDescription(offer.getDescription());
			offerDTO.setContractType(offer.getContractType());
			offerDTO.setStartDate(offer.getStartDate());
			offerDTO.setWages(offer.getWages());
			//offerDTO.setUserPersonalOffers(userDTO);
			offerDTO.setResponse(offer.getResponse());
			
			userOfferDTOList.add(offerDTO);
		}
		
		return userOfferDTOList;
	
	}
	
	
	//@CrossOrigin(origins = "http://localhost:3000")
	//@CrossOrigin(origins = "https://www.samrak.info")
	//@CrossOrigin(origins = "cvfronta-env.he6mbc62qv.us-east-2.elasticbeanstalk.com")
	@CrossOrigin(origins = "https://guarded-castle-37898.herokuapp.com")
	@GetMapping("/recruiter/findOffer/{id}")
	public DTOOffers findOffer(@PathVariable(name="id") Long id ) {
		
		
		//on ne peux pas caster Optional vers une entity comme Offer directement
		//il faut refaire un get d'abord
		Offer offerInDB=serveOffer.get(id).get();
		if(offerInDB!=null) {
			DTOUsers userDTO=new DTOUsers();
			userDTO.setId(offerInDB.getuserPersonalOffers().getId());
			userDTO.setEmail(offerInDB.getuserPersonalOffers().getEmail());
			userDTO.setUsername(offerInDB.getuserPersonalOffers().getUsername());
			userDTO.setUsercompany(offerInDB.getuserPersonalOffers().getUsercompany());
			userDTO.setPhonenumber(offerInDB.getuserPersonalOffers().getPhonenumber() );
			userDTO.setStatus(offerInDB.getuserPersonalOffers().getStatus());
			
			DTOOffers offerDTO=new DTOOffers();
			
			offerDTO.setId(offerInDB.getId());
			offerDTO.setTitle(offerInDB.getTitle());
			offerDTO.setDescription(offerInDB.getDescription());
			offerDTO.setContractType(offerInDB.getContractType());
			offerDTO.setStartDate(offerInDB.getStartDate());
			offerDTO.setWages(offerInDB.getWages());
			//offerDTO.setUserPersonalOffers(userDTO);
			offerDTO.setResponse(offerInDB.getResponse());
			
			
			return offerDTO;
		}else {
			return null;
		}
	}
		
		
	
	//@CrossOrigin(origins = "http://localhost:3000")
	//@CrossOrigin(origins = "https://www.samrak.info")
	//@CrossOrigin(origins = "cvfronta-env.he6mbc62qv.us-east-2.elasticbeanstalk.com")
	@CrossOrigin(origins = "https://guarded-castle-37898.herokuapp.com")
	@PutMapping("/recruiter/update/{id}")
	public String editOffer(@PathVariable(name="id") Long id,@RequestBody Offer offer ) {
		
		offer.setMe(serveMe.get(1L).get());
		Authentication auth= SecurityContextHolder.getContext().getAuthentication();
		offer.setUsers(userDetailsServices.findByUsername(auth.getName()));
		
		
		//on ne peux pas caster Optional vers une entity comme Offer directement
		//il faut refaire un get d'abord
		Offer offerInDB=serveOffer.get(id).get();
		if(offerInDB!=null) {	
			offer.setId(id);
			serveOffer.save(offer);
		}
		
		return "redirect:/recruiter/submittedOffers";
		
		
		//ModelAndView:  https://stackoverflow.com/questions/18486660/what-are-the-differences-between-model-modelmap-and-modelandview
	}
	
	
	//@CrossOrigin(origins = "http://localhost:3000")
	//@CrossOrigin(origins = "https://www.samrak.info")
	//@CrossOrigin(origins = "cvfronta-env.he6mbc62qv.us-east-2.elasticbeanstalk.com")
	@CrossOrigin(origins = "https://guarded-castle-37898.herokuapp.com")
	@DeleteMapping(value="/recruiter/delete/{id}")  /* Works with axios.delete(id)*/
	public String deleteProduct(@PathVariable(name="id") Long id) {
		
		Offer offerInDB=serveOffer.get(id).get();
		if(offerInDB!=null) {
		serveOffer.delete(id);
		}
		return "redirect:/recruiter/submittedOffers";
	}
	

/*
 * ADMIN: GET Full Profil -
 */
	
	/*TODO: SKILLS : add code to add - edit - delete skills*/	

	
	//avec l'annotation @RestController en tete de classe
	//@CrossOrigin(origins = "https://www.samrak.info")
	//@CrossOrigin(origins = "cvfronta-env.he6mbc62qv.us-east-2.elasticbeanstalk.com")
	@CrossOrigin(origins = "https://guarded-castle-37898.herokuapp.com")
	@GetMapping("admin/profil")
	public Me getMe(){
		
		//TODO: retrieve the admin role
		return serveMe.get(1L).get();
		
	}
	
	//Me
	//https://stackoverflow.com/questions/49049312/cors-error-when-connecting-local-react-frontend-to-local-spring-boot-middleware	
	/*SECURITY: Cross-Origin Resource Sharing (CORS). Allows REACT.JS on port 3000 to consumes REST service*/  
	
	
	//@CrossOrigin(origins = "http://localhost:3000")
	//@CrossOrigin(origins = "https://www.samrak.info")
	//@CrossOrigin(origins = "cvfronta-env.he6mbc62qv.us-east-2.elasticbeanstalk.com")
	@CrossOrigin(origins = "https://guarded-castle-37898.herokuapp.com")
	@RequestMapping("/admin/offers")
	public List AllJobOffer() {

		List <DTOOffers>adminOfferDTOList= new ArrayList<>();
		
		//Solution if no null pointer in table offer:
		//Authentication auth= SecurityContextHolder.getContext().getAuthentication();
		//List<Offer>adminOfferList= serveOffer.listUserOffers(userDetailsServices.findByUsername(auth.getName()));
	
		//wiht possibilities of nullpointer:
		List<Offer> adminOfferList=serveOffer.retrieveAll();
		
		for(Offer offer:adminOfferList) {
			DTOUsers userDTO=new DTOUsers();
			userDTO.setId(offer.getuserPersonalOffers().getId());
			userDTO.setEmail(offer.getuserPersonalOffers().getEmail());
			userDTO.setUsername(offer.getuserPersonalOffers().getUsername());
			userDTO.setUsercompany(offer.getuserPersonalOffers().getUsercompany());
			userDTO.setPhonenumber(offer.getuserPersonalOffers().getPhonenumber() );
			userDTO.setStatus(offer.getuserPersonalOffers().getStatus());
			
			DTOOffers offerDTO=new DTOOffers();
			
			offerDTO.setId(offer.getId());
			offerDTO.setTitle(offer.getTitle());
			offerDTO.setDescription(offer.getDescription());
			offerDTO.setContractType(offer.getContractType());
			offerDTO.setStartDate(offer.getStartDate());
			offerDTO.setWages(offer.getWages());
			offerDTO.setUserPersonalOffers(userDTO);
			offerDTO.setResponse(offer.getResponse());
			
			adminOfferDTOList.add(offerDTO);
		}
		
		return adminOfferDTOList;
	}
	
	//TODO: Implement Response correctly
	//@CrossOrigin(origins = "http://localhost:3000")
	//@CrossOrigin(origins = "https://www.samrak.info")
	//@CrossOrigin(origins = "cvfronta-env.he6mbc62qv.us-east-2.elasticbeanstalk.com")
	@CrossOrigin(origins = "https://guarded-castle-37898.herokuapp.com")
	@PostMapping("/offer/addacomment/{id}")
	public String respondToOffer(@PathVariable(name="id") Long id,@RequestBody String answer){
		
		Offer offerInDB=serveOffer.get(id).get();
		if(offerInDB!=null) {	
			
			Response aResp=new Response();
			aResp.setOffer(offerInDB);
			aResp.setAnswer(answer);
			serveResp.save(aResp);
		}
		
	return "redirect:/offers";
			
	}
	
	//@CrossOrigin(origins = "http://localhost:3000")
	//@CrossOrigin(origins = "https://www.samrak.info")
	//@CrossOrigin(origins = "cvfronta-env.he6mbc62qv.us-east-2.elasticbeanstalk.com")
	@CrossOrigin(origins = "https://guarded-castle-37898.herokuapp.com")
	@GetMapping("offer/viewconversation/{id}")
	public List respondResponses(@PathVariable(name="id") Long id){
		
		Offer offerInDB=serveOffer.get(id).get();
		List <Response> listResponses=new ArrayList<>();
		if(offerInDB!=null) {	
			
			listResponses=serveResp.findResponseByOffer(id);
		}
	return listResponses;
			
	}


	

	
	
	
	
	
	/****************************************************************
	 						FILE Management 
	****************************************************************/
	//https://www.callicoder.com/spring-boot-file-upload-download-jpa-hibernate-mysql-database-example/
	//or
	//https://www.devglan.com/spring-boot/spring-boot-file-upload-download
	
	
	//@PostMapping("uploadFile") <=> @RequestMapping(value="/uploadFile", method=RequestMethod.POST)
	
	//@CrossOrigin(origins = "http://localhost:3000")
	//@CrossOrigin(origins = "https://www.samrak.info")
	//@CrossOrigin(origins = "cvfronta-env.he6mbc62qv.us-east-2.elasticbeanstalk.com")
	@CrossOrigin(origins = "https://guarded-castle-37898.herokuapp.com")
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

	
	//@CrossOrigin(origins = "http://localhost:3000")
	//@CrossOrigin(origins = "https://www.samrak.info")
	//@CrossOrigin(origins = "cvfronta-env.he6mbc62qv.us-east-2.elasticbeanstalk.com")
	@CrossOrigin(origins = "https://guarded-castle-37898.herokuapp.com")
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

    
	//@CrossOrigin(origins = "http://localhost:3000")
	//@CrossOrigin(origins = "https://www.samrak.info")
	//@CrossOrigin(origins = "cvfronta-env.he6mbc62qv.us-east-2.elasticbeanstalk.com")
	@CrossOrigin(origins = "https://guarded-castle-37898.herokuapp.com")
	@GetMapping("/user/downloadFile/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long fileId) throws FileNotFoundException {
        // Load file from database
        File dbFile = serveFile.getFile(fileId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(dbFile.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getFileName() + "\"")
                .body(new ByteArrayResource(dbFile.getData()));
    }
    
    
	//@CrossOrigin(origins = "http://localhost:3000")
	//@CrossOrigin(origins = "https://www.samrak.info")
	//@CrossOrigin(origins = "cvfronta-env.he6mbc62qv.us-east-2.elasticbeanstalk.com")
	@CrossOrigin(origins = "https://guarded-castle-37898.herokuapp.com")
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
