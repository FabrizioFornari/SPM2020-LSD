package lsd.smartparking.model;

import lsd.smartparking.enums.UserType;

public interface UserInfo {

	public String getId();

	public String getEmail();

	public UserType getType();
	
}