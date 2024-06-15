package com.example.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mo.Users;
import com.example.Repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Users updateUser(Users users) {

            Users existingUser = userRepository.findById(users.getUserId()).orElse(null);
            existingUser.setUserName(users.getUserName());
            existingUser.setEmail(users.getEmail());
            existingUser.setLocation(users.getLocation());
            existingUser.setPhone(users.getPhone());
            existingUser.setBirthdate(users.getBirthdate());
            existingUser.setProfileImageURL(users.getProfileImageURL());
            return userRepository.save(existingUser);
    }


    public boolean updatePassword(int userId, String currentPassword, String newPassword) {
        Users existingUser = userRepository.findById(userId).orElse(null);
        if (existingUser != null) {
            if (currentPassword.equals(existingUser.getUserPassword())) { // Kiểm tra mật khẩu hiện tại có khớp không
                existingUser.setUserPassword(newPassword);
                userRepository.save(existingUser);
                return true; // Mật khẩu đã được cập nhật thành công
            }
        }
        return false; // Cập nhật mật khẩu thất bại
    }

    public List<Users> getUsers() {
        return userRepository.findAll();
    }

    public Users getUsersById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    public Users getUserByUserName(String username) {
        // Gọi phương thức findByUserName của UserRepository để lấy đối tượng User từ cơ sở dữ liệu
        Users user = userRepository.findByUserName(username);
        return user;
    }

    
}