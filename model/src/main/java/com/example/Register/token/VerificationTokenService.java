package com.example.Register.token;

import java.util.Calendar;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.Repository.UserRepository;
import com.example.mo.Users;

import lombok.RequiredArgsConstructor;

/**
 * @author Sampson Alfred
 */
@Service
@RequiredArgsConstructor
public class VerificationTokenService implements IVerificationTokenService {
    private final VerificationTokenRepository tokenRepository;
    private final UserRepository userRepository;

    @Override
    public String validateToken(String token) {
        Optional<VerificationToken> theToken = tokenRepository.findByToken(token);
        if (theToken.isEmpty()) {
            return "INVALID";
        }
        Users user = theToken.get().getUser();
        Calendar calendar = Calendar.getInstance();
        if ((theToken.get().getExpirationTime().getTime() - calendar.getTime().getTime()) <= 0) {
            return "EXPIRED";
        }
        user.setEnabled(true);
        userRepository.save(user);
        return "VALID";
    }

    @Override
    public void saveVerificationTokenForUser(Users user, String token) {
        var verificationToken = new VerificationToken(token, user);
        tokenRepository.save(verificationToken);
    }

    @Override
    public Optional<VerificationToken> findByToken(String token) {
        return tokenRepository.findByToken(token);
    }

    // @Override
    // public void deleteUserToken(Integer UserId) {
    //     // TODO Auto-generated method stub
    //     throw new UnsupportedOperationException("Unimplemented method 'deleteUserToken'");
    // }

 
    
}