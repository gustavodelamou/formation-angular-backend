package com.kpoma.film.service

import com.kpoma.film.model.Film
import com.kpoma.film.model.Playlist
import com.kpoma.film.model.User
import com.kpoma.film.repository.PlaylistRepository
import com.kpoma.film.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@Service
class UserService(
    @Autowired val  userRepository: UserRepository,
    @Autowired private val  playlistRepository: PlaylistRepository,
    @Autowired private val passwordEncoder: PasswordEncoder
    ) {

    fun addUser(user:User):User{
        val encodedPasswordEncoder = passwordEncoder.encode(user.password)
        user.password=encodedPasswordEncoder
        user.roles = user.roles.trimIndent().trimEnd().trimStart()
        return userRepository.save(user)
    }

    fun getUserByUserName(username:String): User? {
        val user = userRepository.findUserByUsername(username)
        if(user.isPresent){
            return user.get()
        }
        return null
    }

    fun getUserById(id:Int): User? {
        val user = userRepository.findById(id)
        if(user.isPresent){
            return user.get()
        }
        return null
    }

    fun getAllUser(): List<User>{
        return userRepository.findAll()
    }

    fun getPlayListByUser(id: Int): List<Playlist>{
        val user = User()
        user.id=id
        return  playlistRepository.findPlaylistByUserByUserId(user).get()
    }
}