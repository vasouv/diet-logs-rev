package vs.dietlogsrev.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import vs.dietlogsrev.model.CreateUserInfoRequest;
import vs.dietlogsrev.model.Gender;

@Entity
@Table(name = "user_info")
public class UserInfo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name = "name", nullable = false)
    @NotNull
    private String name;
    
    @Column(name = "surname", nullable = false)
    @NotNull
    private String surname;
    
    @Column(name = "gender", nullable = false)
    @NotNull
    private Gender gender;
    
    @Column(name = "date_of_birth", nullable = false)
    @NotNull
    private LocalDate dateOfBirth;
    
    @Column(name = "height", nullable = false)
    @NotNull
    private BigDecimal height;
    
    @Column(name = "weight", nullable = false)
    @NotNull
    private BigDecimal weight;
    
    @OneToOne
    private User user;
    
    public UserInfo() {
	}

	public UserInfo(CreateUserInfoRequest request) {
        this.name = request.name();
        this.surname = request.surname();
        this.gender = request.gender();
        this.dateOfBirth = request.dateOfBirth();
        this.height = request.height();
        this.weight = request.weight();
    }

    public UserInfo(int id, String name, String surname, Gender gender,
            LocalDate dateOfBirth, BigDecimal height, BigDecimal weight) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.height = height;
        this.weight = weight;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public BigDecimal getHeight() {
		return height;
	}

	public void setHeight(BigDecimal height) {
		this.height = height;
	}

	public BigDecimal getWeight() {
		return weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return "UserInfo [id=" + id + ", name=" + name + ", surname=" + surname + ", gender=" + gender
				+ ", dateOfBirth=" + dateOfBirth + ", height=" + height + ", weight=" + weight + "]";
	}

}
