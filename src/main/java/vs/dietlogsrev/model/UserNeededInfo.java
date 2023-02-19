package vs.dietlogsrev.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import vs.dietlogsrev.entity.UserInfo;

public class UserNeededInfo {
    
    private String name;
    private String surname;
    private Gender gender;
    private LocalDate dateOfBirth;
    private BigDecimal height;
    private BigDecimal weight;
    
    public UserNeededInfo() {
	}

	public UserNeededInfo(UserInfo user) {
        this.name = user.getName();
        this.surname = user.getSurname();
        this.gender = user.getGender();
        this.dateOfBirth = user.getDateOfBirth();
        this.height = user.getHeight();
        this.weight = user.getWeight();
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

	@Override
	public String toString() {
		return "UserNeededInfo [name=" + name + ", surname=" + surname + ", gender=" + gender + ", dateOfBirth="
				+ dateOfBirth + ", height=" + height + ", weight=" + weight + "]";
	}

}
