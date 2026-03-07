package com.example.demo.repository;

import com.example.demo.model.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserDataRepository extends JpaRepository<UserData, Long> {

	@Transactional
    @Modifying
    @Query(value = "TRUNCATE TABLE user_data RESTART IDENTITY CASCADE", nativeQuery = true)
    void truncateTable();
}