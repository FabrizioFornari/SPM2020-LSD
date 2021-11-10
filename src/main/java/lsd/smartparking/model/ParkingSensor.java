package lsd.smartparking.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.util.Assert;

import lsd.smartparking.enums.SensorState;

public class ParkingSensor {

    @Id
	@NotNull(message = "Id cannot be null")
    private final ObjectId id;
	@NotBlank(message = "Slot cannot be null")
    private String slot;
	@NotBlank(message = "Owner cannot be null")
    private String owner;
	@NotNull(message = "State cannot be null")
    private SensorState state;


	public ParkingSensor() {
		this.id = new ObjectId();
	}

    @PersistenceConstructor
	public ParkingSensor(ObjectId id, String slot, String owner, SensorState state) {
		Assert.isTrue(id.getClass() == ObjectId.class, "Id must be valid");
		Assert.hasText(slot, "Slot cannot be empty");
		Assert.hasText(owner, "Owner cannot be empty");
		Assert.notNull(state, "State cannot be empty");
		this.id = id;
		this.slot = slot;
		this.owner = owner;
		this.state = state;
	}

    public ObjectId getId() {
        return this.id;
    }

    public String getSlot() {
        return this.slot;
    }

    public void setSlot(String slot) {
		Assert.hasText(slot, "Slot cannot be empty");
        this.slot = slot;
    }

    public String getOwner() {
        return this.owner;
    }

    public void setOwner(String owner) {
		Assert.hasText(owner, "Owner cannot be empty");
        this.owner = owner;
    }

    public SensorState getState() {
        return this.state;
    }

    public void setState(SensorState state) {
		Assert.notNull(state, "State cannot be empty");
        this.state = state;
    }

}