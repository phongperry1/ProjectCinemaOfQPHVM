package com.example.CRUD.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.CRUD.config.VNPAYService;
import com.example.mo.Transaction;
import com.example.mo.Users;
import com.example.mo.CinemaOwner;
import com.example.mo.CinemaOwnerTransaction;
import com.example.mo.TicketDTO;
import com.example.mo.Ticket;
import com.example.CRUD.service.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class PaymentController {

    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

    @Autowired
    private VNPAYService vnPayService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UserService userService;

    @Autowired
    private CinemaService cinemaService;

    @Autowired
    private IntermediaryWalletService intermediaryWalletService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private TheaterService theaterService;

    @Autowired
    private CinemaOwnerTransactionService cinemaOwnerTransactionService;

    private String generatedOtp;

    @GetMapping("/createOrder")
    public String createOrder(@RequestParam("amount") int amount, Model model) {
        model.addAttribute("amount", amount);
        model.addAttribute("orderInfo", "Ve xem phim");
        return "createOrder";
    }

    @PostMapping("/submitOrder")
    public String submitOrder(@RequestParam("amount") int orderTotal,
                              @RequestParam("orderInfo") String orderInfo,
                              HttpServletRequest request,
                              Principal principal,
                              HttpSession session,
                              Model model) {
        String email = principal.getName();
        Users user = userService.getUsersByEmail(email);

        if (user == null) {
            logger.error("User not found with email: " + email);
            model.addAttribute("error", "User not found.");
            return "errorPage"; // Redirect to an error page
        }

        // Lưu ticketDTO tạm thời vào session
        TicketDTO ticketDTO = (TicketDTO) session.getAttribute("ticketDTO");
        if (ticketDTO == null) {
            model.addAttribute("error", "No ticket details found in session.");
            return "errorPage"; // Redirect to an error page
        }

        ticketDTO.setUserId(String.valueOf(user.getUserId()));
        ticketDTO.setTotalPrice3(orderTotal);
        ticketDTO.setOrderInfo(orderInfo);
        session.setAttribute("ticketDTO", ticketDTO);

        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        String vnpayUrl = vnPayService.createOrder(request, orderTotal, orderInfo, baseUrl + "/vnpay-payment-return");
        return "redirect:" + vnpayUrl;
    }

    @GetMapping("/vnpay-payment-return")
    public String paymentCompleted(HttpServletRequest request, Model model, Principal principal, HttpSession session) {
        boolean paymentStatus = vnPayService.validatePayment(request, model);

        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount");
        String bankCode = request.getParameter("vnp_BankCode");
        String tmnCode = request.getParameter("vnp_TmnCode");
        String txnRef = request.getParameter("vnp_TxnRef");
        String cardType = request.getParameter("vnp_CardType");
        String transactionStatus = request.getParameter("vnp_TransactionStatus");
        String bankTranNo = request.getParameter("vnp_BankTranNo");
        String responseCode = request.getParameter("vnp_ResponseCode");

        if (paymentStatus && principal != null) {
            String email = principal.getName();
            Users user = userService.getUsersByEmail(email);
            if (user != null) {
                Transaction transaction = new Transaction(
                    user, bankCode, paymentTime, transactionId, tmnCode,
                    orderInfo, txnRef, Integer.parseInt(totalPrice) / 100, cardType,
                    transactionStatus, bankTranNo, responseCode, "Debit"
                );
                transactionService.addTransaction(transaction);
                logger.info("Transaction added: " + transaction.toString());

                // Tính điểm thành viên
                int amount = Integer.parseInt(totalPrice) / 100;
            int points = amount / 10000; 
            userService.addMemberPoints(user.getUserId(), points);
                // Lưu vé vào cơ sở dữ liệu
                TicketDTO ticketDTO = (TicketDTO) session.getAttribute("ticketDTO");
                ticketService.savePendingTicket(ticketDTO, user);
                session.removeAttribute("ticketDTO");
            } else {
                logger.error("User not found with email: " + email);
            }
        } else {
            logger.error("Payment failed or principal is null. Payment status: " + paymentStatus);
        }

        model.addAttribute("orderId", orderInfo);
        model.addAttribute("totalPrice", totalPrice != null ? Integer.parseInt(totalPrice) / 100 : 0);
        model.addAttribute("paymentTime", paymentTime);
        model.addAttribute("transactionId", transactionId);

        return paymentStatus ? "orderSuccess" : "orderFail";
    }

    @GetMapping("/transactionHistory")
    public String transactionHistory(Principal principal, Model model) {
        if (principal != null) {
            String email = principal.getName();
            Users user = userService.getUsersByEmail(email);
            if (user != null) {
                List<Transaction> transactions = transactionService.getTransactionsByUserId(user.getUserId())
                        .stream()
                        .filter(t -> !t.getTransactionType().equals("Transfer") && !t.getTransactionType().equals("Intermediary"))
                        .collect(Collectors.toList());
                logger.info("User ID: " + user.getUserId() + " has " + transactions.size() + " transactions.");
                model.addAttribute("transactions", transactions);
            } else {
                logger.error("User not found with email: " + email);
                model.addAttribute("transactions", List.of());
            }
        }
        return "transactionHistory";
    }

    @GetMapping("/addFunds")
    public String showAddFundsPage() {
        return "addFunds";
    }

    @PostMapping("/api/payments/verifyOTP")
    public String verifyOTP(@RequestParam("cardNumber") String cardNumber,
                            @RequestParam("expiryDate") String expiryDate,
                            @RequestParam("amount") double amount,
                            Principal principal,
                            Model model) {
        // Simulate sending OTP
        generatedOtp = "123456"; // In real-world applications, generate and send OTP to user's phone/email
        model.addAttribute("amount", amount);
        return "verifyOTP";
    }

    @PostMapping("/api/payments/confirmOTP")
    public String confirmOTP(@RequestParam("otp") String otp,
                             @RequestParam("amount") double amount,
                             Principal principal,
                             Model model) {
        if (otp.equals(generatedOtp)) {
            String email = principal.getName();
            Users user = userService.getUsersByEmail(email);

            if (user == null) {
                logger.error("User not found with email: " + email);
                model.addAttribute("error", "User not found.");
                return "errorPage"; // Redirect to an error page
            }

            try {
                userService.deposit(user.getUserId(), amount);
            } catch (IllegalArgumentException e) {
                model.addAttribute("error", e.getMessage());
                return "errorPage"; // Redirect to an error page
            }

            // Add a transaction for the fund addition
            Transaction transaction = new Transaction(
                user, "VNPAY", new Date().toString(), UUID.randomUUID().toString(),
                "VNPAY_TMN_CODE", "Funds added", UUID.randomUUID().toString(),
                amount, "Wallet", "00", "12345", "00", "Credit"
            );
            transactionService.addTransaction(transaction);

            model.addAttribute("message", "Funds added successfully.");
            return "addFundsSuccess"; // Redirect to success page
        } else {
            model.addAttribute("error", "Invalid OTP.");
            model.addAttribute("amount", amount);
            return "verifyOTP";
        }
    }

    // Custom error page
    @GetMapping("/error")
    public String handleError() {
        return "errorPage";
    }

    // Refund and transfer logic during cancellation approval
    @Transactional
    public void processRefundAndTransfer(Ticket ticket) {
        Users user = ticket.getUser();
        Integer cinemaOwnerId = theaterService.findCinemaOwnerIdByTheaterId(ticket.getTheater().getTheaterID());
        CinemaOwner cinemaOwner = cinemaService.getCinemaOwnerById(cinemaOwnerId);

        double totalPrice = ticket.getPrice();
        double refundAmount = totalPrice * 0.7;
        double transferAmount = totalPrice * 0.25;
        double intermediaryAmount = totalPrice * 0.05;

        // Refund 70% to user
        userService.deposit(user.getUserId(), refundAmount);

        // Transfer 25% to cinema owner's wallet
        cinemaOwner.addFundsToWallet(transferAmount);
        cinemaService.saveCinemaOwner(cinemaOwner);

        // Add 5% to intermediary wallet
        intermediaryWalletService.addFunds(intermediaryAmount);

        // Add refund transaction
        Transaction refundTransaction = new Transaction(
                user, "Refund", new Date().toString(), UUID.randomUUID().toString(),
                "Refund", "Refund for ticket " + ticket.getTicketId(), UUID.randomUUID().toString(),
                refundAmount, "Wallet", "00", "12345", "00", "Credit"
        );
        transactionService.addTransaction(refundTransaction);

        // Deduct member points
        int pointsToDeduct = (int) ((totalPrice / 100000) * 10);
        userService.deductMemberPoints(user.getUserId(), pointsToDeduct);

        // Add transfer transaction for cinema owner
        CinemaOwnerTransaction cinemaOwnerTransaction = new CinemaOwnerTransaction();
        cinemaOwnerTransaction.setCinemaOwner(cinemaOwner);
        cinemaOwnerTransaction.setTransactionType("Transfer");
        cinemaOwnerTransaction.setAmount(transferAmount);
        cinemaOwnerTransaction.setTransactionDate(new Date());
        cinemaOwnerTransactionService.saveTransaction(cinemaOwnerTransaction);

        // Add intermediary wallet transaction
        // Transaction intermediaryTransaction = new Transaction(
        //         user, "Intermediary", new Date().toString(), UUID.randomUUID().toString(),
        //         "Intermediary", "Intermediary funds for ticket " + ticket.getTicketId(), UUID.randomUUID().toString(),
        //         intermediaryAmount, "Wallet", "00", "12345", "00", "Credit"
        // );
        // transactionService.addTransaction(intermediaryTransaction);
    }

    // Approve cancellation and refund
    

    // Transfer remaining funds from intermediary wallet to cinema owner
    @PostMapping("/admin/transferToCinemaOwner")
    public String transferToCinemaOwner(@RequestParam("cinemaOwnerId") int cinemaOwnerId, Model model) {
        CinemaOwner cinemaOwner = cinemaService.getCinemaOwnerById(cinemaOwnerId);

        if (cinemaOwner == null) {
            logger.error("Cinema Owner not found with ID: " + cinemaOwnerId);
            model.addAttribute("error", "Cinema Owner not found.");
            return "errorPageFunds"; // Redirect to an error page
        }

        Double intermediaryBalance = intermediaryWalletService.getBalance();
        Double transferAmount = intermediaryBalance * 0.9; // Transfer 90% of the intermediary wallet balance
        Double intermediaryAmount = intermediaryBalance * 0.1; // 10% for intermediary wallet

        try {
            intermediaryWalletService.withdrawFunds(intermediaryBalance); // Withdraw the full intermediary balance
            cinemaOwner.addFundsToWallet(transferAmount);
            intermediaryWalletService.addFunds(intermediaryAmount); // Add 10% back to intermediary wallet
            cinemaService.saveCinemaOwner(cinemaOwner);
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "errorPageFunds"; // Redirect to an error page
        }

        model.addAttribute("message", "Funds transferred successfully.");
        return "transferSuccess"; // Redirect to success page
    }
}
