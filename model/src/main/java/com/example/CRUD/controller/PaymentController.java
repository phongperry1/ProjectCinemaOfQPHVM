


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
import java.security.Principal;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class PaymentController {

    // @Autowired
    // private WebClient.Builder webClientBuilder;
    // @Autowired
    // private RestTemplate restTemplate;

    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

    @Autowired
    private VNPAYService vnPayService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UserService usersService;


    @GetMapping("/createOrder.html")
    public String showCreateOrderPage(@RequestParam(value = "amount", required = false) String amount, Model model) {

        model.addAttribute("amount", amount);
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
            Users user = usersService.getUsersByEmail(email);
            if (user != null) {
                Transaction transaction = new Transaction(
                    user, bankCode, paymentTime, transactionId, tmnCode,
                    orderInfo, txnRef, Integer.parseInt(totalPrice) / 100, cardType,
                    transactionStatus, bankTranNo, responseCode
                );
                transactionService.addTransaction(transaction);
                logger.info("Transaction added: " + transaction.toString());

                // HttpSession session = request.getSession();
                // TicketDTO ticketDTO = (TicketDTO) session.getAttribute("ticketData");
                // if (ticketDTO != null) {
                //     Mono<TicketDTO> response = webClientBuilder.build()
                //             .post()
                //             .uri("http://localhost:8080/tickets/save")
                //             .body(Mono.just(ticketDTO), TicketDTO.class)
                //             .retrieve()
                //             .bodyToMono(TicketDTO.class);

                //     response.subscribe(savedTicket -> {
                //         logger.info("Ticket saved: " + savedTicket.toString());
                //     }, error -> {
                //         logger.error("Failed to save ticket: " + error.getMessage());
                //     });
                // } else {
                //     logger.error("TicketDTO is null.");
                // }
                
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
            Users user = usersService.getUsersByEmail(email);
            if (user != null) {
                List<Transaction> transactions = transactionService.getTransactionsByUserId(user.getUserId());
                logger.info("User ID: " + user.getUserId() + " has " + transactions.size() + " transactions.");
                model.addAttribute("transactions", transactions);
            } else {
                logger.error("User not found with email: " + email);
            }
        } else {
            List<Transaction> transactions = transactionService.getAllTransactions();
            logger.info("Showing all transactions, total: " + transactions.size());
            model.addAttribute("transactions", transactions);
        }
        return "transactionHistory";
    }
}
