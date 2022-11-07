package com.kpoma.film.repository

import com.kpoma.film.model.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface UserRepository: JpaRepository<User, Int> {
   fun findUserByUsername(username: String): Optional<User>
}