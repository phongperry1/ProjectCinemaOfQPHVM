package com.example;



import com.example.CRUD.service.PurchaseHistoryService;
import com.example.mo.PurchaseHistory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;



import java.util.List;

@SpringBootApplication
public class QrCodeLastApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(QrCodeLastApplication.class, args);
		PurchaseHistoryService historyService = context.getBean(PurchaseHistoryService.class);
		Integer UserId = 1;
		List<PurchaseHistory> histories = historyService.getPurchaseHistoryById(UserId);
		for (PurchaseHistory purchaseHistory : histories) {
            System.out.println(purchaseHistory);
		// System.out.println(adminRepository.findByUserNameContainingIgnoreCase("2"));
	// 	// Lấy instance của UserByAdminService
	// 	UserByAdminService userByAdminService = context.getBean(UserByAdminService.class);

	// 	// Gọi phương thức getTickets()
	// 	List<UserByAdmin> userByAdmins = userByAdminService.getTickets();

	// 	// Hiển thị thông tin các UserByAdmin
	// 	for (UserByAdmin userByAdmin : userByAdmins) {
	// 		System.out.println("ID: " + userByAdmin.getId());
	// 		System.out.println("Username: " + userByAdmin.getUserName());
	// 		System.out.println("Phone: " + userByAdmin.getPhone());
	// 		System.out.println("Email: " + userByAdmin.getEmail());
	// 		System.out.println("Password: " + userByAdmin.getUserPassword());
	// 		System.out.println("Birthdate: " + userByAdmin.getBirthdate());
	// 		System.out.println("Location: " + userByAdmin.getLocation());
	// 		System.out.println("Gender: " + userByAdmin.getGender());
	// 		System.out.println("Captcha: " + userByAdmin.getCaptcha());
	// 		System.out.println("Rank: " + userByAdmin.getUserRank());
	// 		System.out.println("Points: " + userByAdmin.getMemberPoints());
	// 		System.out.println("ID Card: " + userByAdmin.getIdCard());
	// 		System.out.println("Payment Method: " + userByAdmin.getPaymentMethod());
	// 		System.out.println("User Type: " + userByAdmin.getUserType());
	// 		System.out.println("Profile Image URL: " + userByAdmin.getProfileImageURL());
	// 		System.out.println("----------------------");
	// 	}

	// 	int userid = 2;

	// 	UserByAdmin userbyid = userByAdminService.findById((String.valueOf(userid)));
	// 	System.out.println(userbyid);

	}
}
}