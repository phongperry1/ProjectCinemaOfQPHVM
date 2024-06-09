package com.becoder.service;

import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.becoder.mo.Users;
import com.becoder.repository.UserRepo;

import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpSession;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private JavaMailSender mailSender;

	@Override
	public Users saveUser(Users user, String url) {

		String password = passwordEncoder.encode(user.getUserPassword());
		user.setUserPassword(password);
		user.setRole("ROLE_USER");

		user.setstatus(false);
		user.setVerificationCode(UUID.randomUUID().toString());

		Users newuser = userRepo.save(user);

		if (newuser != null) {
			sendEmail(newuser, url);
		}

		return newuser;
	}

	@Override
	public void sendEmail(Users user, String url) {

		String from = "dhquan235@gmail.com";
		String to = user.getEmail();
		String subject = "Account Verfication";
		String content = "Dear [[name]],<br>" + "Please click the link below to verify your registration:<br>"
				+ "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>" + "Thank you,<br>" + "Becoder";

		try {

			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message);

			helper.setFrom(from, "Becoder");
			helper.setTo(to);
			helper.setSubject(subject);

			content = content.replace("[[name]]", user.getUserName());
			String siteUrl = url + "/verify?code=" + user.getVerificationCode();

			System.out.println(siteUrl);

			content = content.replace("[[URL]]", siteUrl);

			helper.setText(content, true);

			mailSender.send(message);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public boolean verifyAccount(String verificationCode) {

		Users user = userRepo.findByVerificationCode(verificationCode);

		if (user == null) {
			return false;
		} else {

			user.setstatus(true);
			user.setVerificationCode(null);

			userRepo.save(user);

			return true;
		}

	}

	@Override
	public void removeSessionMessage() {

		HttpSession session = ((ServletRequestAttributes) (RequestContextHolder.getRequestAttributes())).getRequest()
				.getSession();

		session.removeAttribute("msg");
	}

}
