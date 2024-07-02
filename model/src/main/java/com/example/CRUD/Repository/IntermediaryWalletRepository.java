package com.example.CRUD.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.mo.IntermediaryWallet;

public interface IntermediaryWalletRepository extends JpaRepository<IntermediaryWallet, Long> {
}
