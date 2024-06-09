package com.becoder.service;

import com.becoder.mo.Users;

public interface UserService {

	public Users saveUser(Users user, String url);

	public void removeSessionMessage();

	public void sendEmail(Users user, String path);

	public boolean verifyAccount(String verificationCode);

}
