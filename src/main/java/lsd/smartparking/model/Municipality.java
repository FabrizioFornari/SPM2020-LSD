package lsd.smartparking.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.Assert;

import lsd.smartparking.enums.UserType;

@Document(collection = "municipalities")
public class Municipality extends Utils implements UserInfo {

	@Id
	@NotNull(message = "Id must be valid")
	private final ObjectId id;
	@Email(message = "Email must be valid")
	@NotBlank(message = "Email cannot be null")
	private String email;
	@NotBlank(message = "City cannot be empty")
	private String city;
	@NotBlank(message = "Province cannot be empty")
	private String province;
	@NotBlank(message = "Region cannot be empty")
	private String region;
	@NotNull(message = "Type cannot be empty")
	private UserType type;
	private boolean approved = false;
	private boolean disabled = false;

	
    public Municipality() { 
		this.id = new ObjectId();
	}

	public Municipality(ObjectId id, String email, String city, String province, String region) {
		Assert.notNull(id, "Id must be valid");
		Assert.hasText(email, "Email cannot be empty");
		Assert.hasText(city, "City cannot be empty");
		Assert.hasText(province, "Province cannot be empty");
		Assert.hasText(region, "Region cannot be empty");
		this.id = id;
		this.email = email;
		this.city = city;
		this.province = province;
		this.region = region;
		this.type = UserType.MUNICIPALITY;
	}

	@Override
	public String getId() {
		return id.toHexString();
	}

	@Override
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		checkFields(email);
		this.email = email.trim();
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		checkFields(city);
		checkFieldsLength(city, 30);
		this.city = city.trim();
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		checkFields(province);
		checkFieldsLength(province, 30);
		this.province = province.trim();
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		checkFields(region);
		checkFieldsLength(region, 30);
		this.region = region.trim();
	}

	@Override
	public UserType getType() {
		return type;
	}

	public void setType(UserType type) {
		this.type = type;
	}

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
	
}