package com.angelorobson.alternativescene.security.dto;

import com.angelorobson.alternativescene.dtos.UserAppDto;

public class TokenDto {

	private String token;
	private UserAppDto userAppDto;

	public TokenDto() {
	}

	public TokenDto(String token) {
		this.token = token;
	}

	public TokenDto(String token, UserAppDto userAppDto) {
		this.token = token;
		this.userAppDto = userAppDto;
	}

	public UserAppDto getUserAppDto() {
		return userAppDto;
	}

	public void setUserAppDto(UserAppDto userAppDto) {
		this.userAppDto = userAppDto;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
