package es.urjc.computadores;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonBackReference;


@Entity
@Inheritance(strategy= InheritanceType.JOINED)
public class User {

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	public enum paymentMethod {CREDITCARD,PAYPAL}
	
	public String nickname;
	private String password;
	public String email;
	public String name;
	public String surname;
	private paymentMethod myPaymentMethod;
	public boolean isDeveloper;
	private String accountID;
	
	@ElementCollection(fetch = FetchType.EAGER)
	protected List<String> roles;

	

	public String getAccountID() {
		return accountID;
	}

	public void setAccountID(String accountID) {
		this.accountID = accountID;
	}

	@LazyCollection(LazyCollectionOption.FALSE)
	@JsonBackReference
	@ManyToMany(mappedBy="contributors")
	private List<Project> financedProjects = new ArrayList<Project>();
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy="contributor", cascade = CascadeType.REMOVE)
	private List<Contract> myContracts = new ArrayList<Contract>();
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy="user", cascade = CascadeType.REMOVE)
	private List<Comment> myComments = new ArrayList<Comment>();
	
	
	
	

	/***
	 * Constructor sin parametros para la base de datos
	 */
	protected User() {
	}
	
	/***
	 * Constructor utilizado por la aplicación
	 * @param nickname
	 * @param password
	 * @param email
	 * @param name
	 * @param surname
	 */
	public User(String nickname, String password, String email, String name, String surname) {
		super();
		this.nickname = nickname;
		this.password = password;
		this.email = email;
		this.name = name;
		this.surname = surname;
		isDeveloper = false;
		roles= new ArrayList<>();
		roles.add("ROLE_USER");
	}
	


	
	public List<Project> getFinancedProjects() {
		return financedProjects;
	}

	public void setFinancedProjects(List<Project> financedProjects) {
		this.financedProjects = financedProjects;
	}


	public String getPassword() {
		return password;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setPassword(String password) {
		this.password = password;
	}



	public paymentMethod getMyPaymentMethod() {
		return myPaymentMethod;
	}



	public void setMyPaymentMethod(paymentMethod myPaymentMethod) {
		this.myPaymentMethod = myPaymentMethod;
	}

	public List<Contract> getMyContracts() {
		return myContracts;
	}

	public void setMyContracts(List<Contract> myContracts) {
		this.myContracts = myContracts;
	}

	public List<Comment> getMyComments() {
		return myComments;
	}

	public void setMyComments(List<Comment> myComments) {
		this.myComments = myComments;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nickname == null) ? 0 : nickname.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (nickname == null) {
			if (other.nickname != null)
				return false;
		} else if (!nickname.equals(other.nickname))
			return false;
		return true;
	}
}
