package com.sym.myboot.dao;

import com.sym.myboot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;


public interface UserRepository extends JpaRepository<User,Long> {
    User findAllById(int id);

}
