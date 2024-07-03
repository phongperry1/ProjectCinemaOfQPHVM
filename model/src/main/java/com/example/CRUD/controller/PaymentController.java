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
import com.example.CRUD.service.IntermediaryWalletService;
import com.example.CRUD.service.TransactionService;
import com.example.CRUD.service.UserService;
import com.example.CRUD.service.CinemaService;
import com.example.CRUD.controller.InsufficientBalanceException;

import jakarta.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
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
    private IntermediaryWalletService intermediaryWalletService;

    @Autowired
    private CinemaService cinemaService;

    private String generatedOtp;

    @GetMapping({"/createOrder"})
    public String home() {
        return "createOrder";
    }

    @PostMapping("/submitOrder")
    public String submitOrder(@RequestParam("amount") int orderTotal,
                              @RequestParam("orderInfo") String orderInfo,
                              HttpServletRequest request,
                              Principal principal,
                              Model model) {
        String email = principal.getName();
        Users user = userService.getUsersByEmail(email);

        if (user == null) {
            logger.error("User not found with email: " + email);
            model.addAttribute("error", "User not found.");
            return "errorPage"; // Redirect to an error page
        }

        try {
            userService.withdraw(user.getUserId(), (double) orderTotal);
            intermediaryWalletService.addFunds((double) orderTotal);
        } catch (InsufficientBalanceException e) {
            model.addAttribute("error", e.getMessage());
            return "errorPage"; // Redirect to an error page
        }

        // Record the transaction as a debit
        Transaction transaction = new Transaction(
            user, "VNPay", new java.util.Date().toString(), UUID.randomUUID().toString(),
            "VNPAY_TMN_CODE", orderInfo, UUID.randomUUID().toString(),
            -orderTotal, "Wallet", "00", "12345", "00", "Debit"
        );
        transactionService.addTransaction(transaction);

        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        String vnpayUrl = vnPayService.createOrder(request, orderTotal, orderInfo, baseUrl + "/vnpay-payment-return");
        return "redirect:" + vnpayUrl;
    }

    @GetMapping("/vnpay-payment-return")
    public String paymentCompleted(HttpServletRequest request, Model model, Principal principal) {
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
                int points = (amount / 100000) * 10;
                userService.addMemberPoints(user.getUserId(), points);
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
                List<Transaction> transactions = transactionService.getTransactionsByUserId(user.getUserId());
                logger.info("User ID: " + user.getUserId() + " has " + transactions.size() + " transactions.");
                // Ensure there are no duplicate transactions
                model.addAttribute("transactions", transactions.stream().distinct().collect(Collectors.toList()));
            } else {
                logger.error("User not found with email: " + email);
                model.addAttribute("transactions", List.of());
            }
        } else {
            List<Transaction> transactions = transactionService.getAllTransactions();
            logger.info("Showing all transactions, total: " + transactions.size());
            // Ensure there are no duplicate transactions
            model.addAttribute("transactions", transactions.stream().distinct().collect(Collectors.toList()));
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
                user, "VNPAY", new java.util.Date().toString(), UUID.randomUUID().toString(),
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

    // Refund endpoint
    @PostMapping("/admin/refund")
    public String refundUser(@RequestParam("userId") int userId, Model model) {
        Users user = userService.getUsersById(userId);

        if (user == null) {
            logger.error("User not found with ID: " + userId);
            model.addAttribute("error", "User not found.");
            return "errorPage"; // Redirect to an error page
        }

        // Get the last transaction amount for the user
        Optional<Transaction> lastTransaction = transactionService.getTransactionsByUserId(userId).stream()
                .filter(t -> "Debit".equals(t.getTransactionType()))
                .findFirst();

        if (!lastTransaction.isPresent()) {
            model.addAttribute("error", "No transaction found to refund.");
            return "errorPageFunds";
        }

        double originalAmount = lastTransaction.get().getAmount() * -1; // Convert negative debit amount to positive
        double refundAmount = originalAmount * 0.7;

        try {
            intermediaryWalletService.withdrawFunds(refundAmount);
            userService.deposit(user.getUserId(), refundAmount);
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "errorPageFunds"; // Redirect to an error page
        }

        // Record the transaction as a credit
        Transaction transaction = new Transaction(
            user, "VNPAY", new java.util.Date().toString(), UUID.randomUUID().toString(),
            "VNPAY_TMN_CODE", "Refund", UUID.randomUUID().toString(),
            refundAmount, "Wallet", "00", "12345", "00", "Credit"
        );
        transactionService.addTransaction(transaction);

        model.addAttribute("message", "Refund processed successfully.");
        return "refundSuccess"; // Redirect to success page
    }

    // Transfer funds from intermediary wallet to cinema owner's wallet
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

        try {
            intermediaryWalletService.withdrawFunds(transferAmount);
            cinemaOwner.addFundsToWallet(transferAmount);
            cinemaService.saveCinemaOwner(cinemaOwner);
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "errorPageFunds"; // Redirect to an error page
        }

        model.addAttribute("message", "Funds transferred successfully.");
        return "transferSuccess"; // Redirect to success page
    }
}
