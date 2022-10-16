package com.example.EnjoyEat.Repository;

import com.example.EnjoyEat.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
