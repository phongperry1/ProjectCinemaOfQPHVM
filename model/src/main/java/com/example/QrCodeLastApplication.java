package com.example;




import com.example.CRUD.Repository.CinemaOwnerRepository;
import com.example.CRUD.service.CinemaOwnerService;
import com.example.Service.PurchaseHistoryService;
import com.example.mo.CinemaOwner;
import com.example.mo.PurchaseHistory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;



import java.util.List;

@SpringBootApplication
public class QrCodeLastApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(QrCodeLastApplication.class, args);
		// PurchaseHistoryService historyService = context.getBean(PurchaseHistoryService.class);
		CinemaOwnerService cinemaOwnerService = context.getBean(CinemaOwnerService.class);
		
		// List<CinemaOwner> cinemaOwners = cinemaOwnerService.getCinemaOwnerByAdmins();
		// for (CinemaOwner cinemaOwner : cinemaOwners) {
		// 	System.out.println("Id" + cinemaOwner.getEmployeeID());
		// 	System.out.println("Id" + cinemaOwner.getAddressCinema());
		// 	System.out.println("Id" + cinemaOwner.getCinemaName());
		// 	System.out.println("Id" + cinemaOwner.getEmail());
		// 	System.out.println("Id" + cinemaOwner.getCinemaOwnerID());
		// }
		// CinemaOwnerService cinemaOwnerService = context.getBean(CinemaOwnerService.class);
		// Integer UserId = 1;
		// List<PurchaseHistory> histories = historyService.getPurchaseHistoryById(UserId);
		// for (PurchaseHistory purchaseHistory : histories) {
        //     System.out.println(purchaseHistory);

		// System.out.println(adminRepository.findByUserNameContainingIgnoreCase("2"));
	// 	// Lấy instance của UserByAdminService
	// 	UserByAdminService userByAdminService = context.getBean(UserByAdminService.class);

	// 	// Gọi phương thức getTickets()
	// 	List<UserByAdmin> userByAdmins = userByAdminService.getTickets();
		// List<CinemaOwner> cinemaOwners = cinemaOwnerService.getCinemaOwnerByAdmins();
		// for (CinemaOwner CinemaOwner : cinemaOwners) {
		// 	System.out.println("ID" + CinemaOwner.getCinemaOwnerID());
		// 	System.out.println("Name" +CinemaOwner.getCinemaName());
		// 	System.out.println("Name" +CinemaOwner.getAddressCinema());
		// 	System.out.println("Name" +CinemaOwner.getEmail());
		// 	System.out.println("Name" +CinemaOwner.getEmployeeID());
		// }
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

		List<CinemaOwner> cinemaOwners2 = cinemaOwnerService.searchCinemasOwnerByCinemaName("C");
		System.out.println(cinemaOwners2);
	}
}
