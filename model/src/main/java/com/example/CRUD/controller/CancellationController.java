package com.example.CRUD.controller;

import com.example.CRUD.Repository.CancelledRequestRepository;
import com.example.CRUD.controller.PaymentController;
import com.example.CRUD.service.TicketService;
import com.example.mo.CancelledRequest;
import com.example.mo.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Date;
import java.util.List;

@Controller
public class CancellationController {

    @Autowired
    private CancelledRequestRepository cancelledRequestRepository;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private PaymentController paymentService;

    @PostMapping("/tickets/request-cancellation")
    public String requestCancellation(@RequestParam int ticketId, Principal principal) {
        Ticket ticket = ticketService.getTicketById(ticketId);
        CancelledRequest request = new CancelledRequest();
        request.setTicket(ticket);
        request.setRequestDate(new Date());
        request.setStatus("Pending");
        cancelledRequestRepository.save(request);
        return "redirect:/user/mytickets";
    }

    @Transactional
    @PostMapping("/admin/approve-cancellation")
    public String approveCancellation(@RequestParam int requestId, Model model) {
        CancelledRequest request = cancelledRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));
        
        request.setStatus("Approved");
        cancelledRequestRepository.save(request);

        Ticket ticket = request.getTicket();
        paymentService.processRefundAndTransfer(ticket);

        // Delete related CancelledRequest entries before deleting the ticket
        cancelledRequestRepository.deleteAllByTicket(ticket);
        ticketService.deleteTicketById(ticket.getTicketId());

        // Refresh the list of cancellation requests
        List<CancelledRequest> requests = cancelledRequestRepository.findByTicketUserUserId(ticket.getUser().getUserId());
        model.addAttribute("requests", requests);

        return "cancelledRequests";
    }

    @Transactional
    @PostMapping("/admin/reject-cancellation")
    public String rejectCancellation(@RequestParam int requestId, Model model) {
        CancelledRequest request = cancelledRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));
        
        request.setStatus("Rejected");
        cancelledRequestRepository.save(request);

        // Refresh the list of cancellation requests
        List<CancelledRequest> requests = cancelledRequestRepository.findByTicketUserUserId(request.getTicket().getUser().getUserId());
        model.addAttribute("requests", requests);

        return "cancelledRequests";
    }

    @GetMapping("/admin/cancelled-requests/{userId}")
    public String viewCancelledRequests(@PathVariable int userId, Model model) {
        List<CancelledRequest> requests = cancelledRequestRepository.findByTicketUserUserId(userId);
        model.addAttribute("requests", requests);
        return "cancelledRequests";
    }
}
