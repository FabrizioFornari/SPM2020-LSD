package lsd.smartparking.model;

import javax.validation.constraints.NotBlank;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.Assert;

import lsd.smartparking.enums.UserType;

@Document(collection = "municipalities")
public class Municipality extends Account {

	@NotBlank(message = "City cannot be empty")
	private String city;
	@NotBlank(message = "Province cannot be empty")
	private String province;
	@NotBlank(message = "Region cannot be empty")
	private String region;
	private boolean approved = false;
	private boolean disabled = false;


	public Municipality() {
		this.setType(UserType.MUNICIPALITY);
	}

	@PersistenceConstructor
	public Municipality(ObjectId id, String email, String city, String province, String region) {
		super(id, email, UserType.MUNICIPALITY);
		Assert.hasText(city, "City cannot be empty");
		Assert.hasText(province, "Province cannot be empty");
		Assert.hasText(region, "Region cannot be empty");
		this.city = city;
		this.province = province;
		this.region = region;
	}

	public Municipality(String email, String city, String province, String region) {
		this(new ObjectId(), email, city, province, region);
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city.trim();
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province.trim();
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region.trim();
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