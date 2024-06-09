package com.becoder.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.becoder.mo.Users;

public interface UserRepo extends JpaRepository<Users, Integer> {

	public Users findByEmail(String emaill);

	public Users findByVerificationCode(String code);

}
