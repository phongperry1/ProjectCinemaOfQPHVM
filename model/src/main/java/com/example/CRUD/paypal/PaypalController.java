package com.example.CRUD.paypal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

@Slf4j
@Controller
@RequiredArgsConstructor
public class PaypalController {

    private final PaypalService paypalService;

    @GetMapping("/paypal")
    public String Paypal(Model model) {    
        return "paypal"; 
    }

    @PostMapping("/payment/create")
    public RedirectView createPayment() {
        try {
            String cancelUrl = "http://localhost:8000/payment/cancel";
            String successUrl = "http://localhost:8000/payment/success?PayerId=";
            Payment payment = paypalService.createPayment(10.0, "USD", "paypal", "sale", "Payment Description", cancelUrl, successUrl);
            
            for (Links links : payment.getLinks()) {
                if(links.getRel().equals("approval_url")) {
                    return new RedirectView(links.getHref());
                }
            }
        } catch (PayPalRESTException e) {
           log.error("Error occurred:: ", e);
        }
        return new RedirectView("/payment/error");
    }
    

    @GetMapping("/payment/success")
    public String paymentSuccess(@RequestParam("paymentId") String paymentId, @RequestParam("PayerId") String payerId) {
       try {
            Payment payment = paypalService.executePayment(paymentId, payerId);
            if(payment.getState().equals("approved")) {
                return "paymentSuccess";
            }
       } catch (PayPalRESTException e) {
            log.error("Error occurred:: ", e);
       }

       return "paymentSuccess";
    }
    
    @GetMapping("/payment/cancel")
    public String paymentCancel() {
        return "paymentCancel";
    }

    @GetMapping("/payment/error")
    public String paymentError() {
        return "paymentError";
    }
}
