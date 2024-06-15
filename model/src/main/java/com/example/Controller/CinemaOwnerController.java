package com.example.Controller;

import java.util.List;

import org.apache.catalina.authenticator.SpnegoAuthenticator.AuthenticateAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.CRUD.service.CinemaOwnerService;

import com.example.Service.UserService;
import com.example.mo.CinemaOwner;
import com.example.mo.CinemaOwnerRequest;
import com.example.mo.Users;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class CinemaOwnerController {
    @Autowired
    private CinemaOwnerService cinemaOwnerService;
    private UserService userService; 
    //  @PostMapping
    // public CinemaOwnerRequest createCinemaOwnerRequest(@RequestBody CinemaOwnerRequest cinemaOwnerRequest) {
    //     Users currentUser = userService.getCurrentUser();
    //     cinemaOwnerRequest.setUsers(currentUser);
    //     return cinemaOwnerService.saveCinemaOwnerRequest(cinemaOwnerRequest);
    // }


}