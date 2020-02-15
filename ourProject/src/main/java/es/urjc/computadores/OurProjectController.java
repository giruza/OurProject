package es.urjc.computadores;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.tomcat.jni.Time;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.urjc.computadores.User.paymentMethod;

@Controller
public class OurProjectController {
	
	@GetMapping("/ourProject")
	public String ourProject(Model model, /*@RequestParam*/ String usern) {
		if (usern != null) {
			model.addAttribute("usern", usern);
		}
		
		List<Project> listProject = projectRepo.findAll();
		Project[] arrayProjects = listProject.toArray(new Project[listProject.size()]);
		model.addAttribute("arrayProjects", arrayProjects);
		/*
		for (int i = 0; i< arrayProjects.length; i++) {
			String name = "name_p" + i;
			String desc = "desc_p" + i;
			String dev = "devName_p" + i;
			String goal = "goal_p" + i;
			String money = "moneyCollected_p" + i;
			model.addAttribute(name, arrayProjects[i].projectName);
			model.addAttribute(desc, arrayProjects[i].description);
			model.addAttribute(dev, arrayProjects[i].developer.nickname);
			//model.addAttribute(goal, arrayProjects[i].goals.get(0));
			model.addAttribute(money, arrayProjects[i].moneyCollected);
		}
		*/
		return "index - copia";
	}
	
	// me pones aqui una isntancia de este repo
	@Autowired
	private UserRepository userRepo;
	
	@Autowired 
	private ProjectRepository projectRepo;
	
	@Autowired
	private ContractRepository contractRepo;
	
	@Autowired
	private CommentRepository commentRepo;
	
	@Autowired
	private RewardRepository rewardRepo;
	
	@Autowired
	private GoalRepository goalRepo;
	/*
	@Autowired
	private DeveloperRepository devRepo;
	*/
	@PostConstruct
	public void init() {
		
		
		
		User us = new User("sergjio","1234","soyfeo@sergio.com","sergio","plaza");
		User us1 = new User("dani","1234","soyfeo@dani.com","daniel","jimenez");
		
		Developer dev = new Developer("axwel","3565","soyfeo@axwel.com","alejandro", "garcia");
		
		userRepo.save(dev);
		userRepo.save(us);
		userRepo.save(us1);
		
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	    Date date = new Date();
	    System.out.println(dateFormat.format(date)); //2016/11/16 12:08:43
		
		Project p1 = new Project("takeMe","Jueguito rico",paymentMethod.CREDITCARD,"ES-51561321518",dev, dateFormat.format(date));
		Project p2 = new Project("BiruBiru","A Beber",paymentMethod.PAYPAL,"PAY-54215",dev, dateFormat.format(date));
		
		
		p1.getContributors().add(us);
		p1.getContributors().add(us1);
		p2.getContributors().add(us1);
		p2.getContributors().add(dev);
		
		
		projectRepo.save(p1);
		projectRepo.save(p2);
		
		Goal g1 = new Goal(p1,100.0,"video exclusivo");
		Goal g2 = new Goal(p1,2000.0,"Claves juego");
		
		goalRepo.save(g1);
		goalRepo.save(g2);
		
		
		/*
		us.getFinancedProjects().add(p1);
		us1.getFinancedProjects().add(p1);
		us1.getFinancedProjects().add(p2);
		*/
		
		Contract c1 = new Contract(us1,p1,"500€", 500.00);
		Contract c2 = new Contract(us,p1,"100€", 100.00);
		Contract c3 = new Contract(us1,p2,"1000€", 1000.00);
		Contract c4 = new Contract(dev,p2,"999€", 999.99);

		
		contractRepo.save(c1);
		contractRepo.save(c2);
		contractRepo.save(c3);
		contractRepo.save(c4);
		
		Comment com1 = new Comment(us1,p1,"Vaya proyecto guapo",new Date());
		Comment com2 = new Comment(dev,p1,"Yo doy pasta aqui",new Date());
		
		commentRepo.save(com1);
		commentRepo.save(com2);
		
		Reward r1 = new Reward(10,"Peluche de Axwel",p1);
		Reward r2 = new Reward(2,"acceso a la beta",p2);
		Reward r3 = new Reward(5,"Clave Juego",p1);
		
		rewardRepo.save(r1);
		rewardRepo.save(r2);
		rewardRepo.save(r3);
		
		/*
		
		us.getMyContracts().add(c2);
		us1.getMyContracts().add(c1);
		us1.getMyContracts().add(c3);
		*/
		
		/*
		userRepo.save(dev);
		userRepo.save(us);
		userRepo.save(us1);
		
		projectRepo.save(p1);
		projectRepo.save(p2);

		contractRepo.save(c1);
		contractRepo.save(c2);
		contractRepo.save(c3);
		*/

		
		/*
		us1.getMyContracts().add(c1);
		
		userRepo.save(us);
		*/
	}
	
}
