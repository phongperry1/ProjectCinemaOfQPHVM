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
import com.example.CRUD.service.TransactionService;
import com.example.CRUD.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.List;
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
    private UserService usersService;

    @GetMapping({"/createOrder"})
    public String home() {
        return "createOrder";
    }

    @PostMapping("/submitOrder")
    public String submitOrder(@RequestParam("amount") int orderTotal,
                              @RequestParam("orderInfo") String orderInfo,
                              HttpServletRequest request) {
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        String vnpayUrl = vnPayService.createOrder(request, orderTotal, orderInfo, baseUrl + "/vnpay-payment-return");
        return "redirect:" + vnpayUrl;
    }

    @GetMapping("/vnpay-payment-return")
    public String paymentCompleted(HttpServletRequest request, Model model, HttpSession session) {
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

        // Lấy userId từ session
        Integer userId = (Integer) session.getAttribute("userId");

        if (paymentStatus && userId != null) {
            Users user = usersService.getUserById(userId); // Fetch the user entity
            Transaction transaction = new Transaction(
                    user, bankCode, paymentTime, transactionId, tmnCode,
                    orderInfo, txnRef, Integer.parseInt(totalPrice) / 100, cardType,
                    transactionStatus, bankTranNo, responseCode
            );
            transactionService.addTransaction(transaction);
        }

        model.addAttribute("orderId", orderInfo);
        model.addAttribute("totalPrice", totalPrice != null ? Integer.parseInt(totalPrice) / 100 : 0);
        model.addAttribute("paymentTime", paymentTime);
        model.addAttribute("transactionId", transactionId);

        return paymentStatus ? "orderSuccess" : "orderFail";
    }

    @GetMapping("/transactionHistory")
    public String transactionHistory(HttpSession session, Model model) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId != null) {
            List<Transaction> transactions = transactionService.getTransactionsByUserId(userId);
            logger.info("User ID: " + userId + " has " + transactions.size() + " transactions.");
            model.addAttribute("transactions", transactions);
        } else {
            List<Transaction> transactions = transactionService.getAllTransactions();
            logger.info("Showing all transactions, total: " + transactions.size());
            model.addAttribute("transactions", transactions);
        }
        return "transactionHistory";
    }
}
