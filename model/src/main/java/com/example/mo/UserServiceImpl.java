package com.example.mo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Register.RegistrationRequest;
import com.example.Repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    // @Override
    // public Users registerUser(RegistrationRequest registrationRequest) {
    //     Users user = new Users();
    //     user.setEmail(registrationRequest.getEmail());
    //     user.setUserPassword(registrationRequest.getPassword());
    //     // Thiết lập các thuộc tính khác
    //     return userRepository.save(user);
    // }

    @Override
    public Optional<Users> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<Users> findById(Integer userId) {
        return userRepository.findById(userId);
    }

    @Override
    public Users registerUser(RegistrationRequest registrationRequest) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'registerUser'");
    }

    // @Override
    // public Users registerUser(RegistrationRequest registrationRequest) {
    //     // TODO Auto-generated method stub
    //     throw new UnsupportedOperationException("Unimplemented method 'registerUser'");
    // }
}
