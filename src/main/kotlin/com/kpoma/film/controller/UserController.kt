package com.kpoma.film.controller

import com.kpoma.film.exception.CustomException
import com.kpoma.film.model.AuthResponse
import com.kpoma.film.model.ExceptionResponse
import com.kpoma.film.model.Playlist
import com.kpoma.film.model.User
import com.kpoma.film.service.UserService
import com.kpoma.film.utils.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import java.util.Objects
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/users")
class UserController(
    @Autowired val userService: UserService,
    @Autowired val authManager: AuthenticationManager
) {

    @PostMapping("signup")
    fun addUser(@RequestBody user:User):User{
        return userService.addUser(user)
    }

    @PostMapping("login")
    fun login(@RequestBody user:User): ResponseEntity<Any> {
        try {
            val authenticated = authManager.authenticate(UsernamePasswordAuthenticationToken(user.username, user.password))
            val authUser = authenticated.principal as User
            val authResponse = AuthResponse()
            authResponse.token = generateAccessToken(authUser)
            authResponse.username = authUser.username
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(authResponse)
        }catch (ex: Exception){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                responseBody(
                    message = FORBIDENMESSAGE,
                    httpStatus = HttpStatus.FORBIDDEN.value()
                )
            )
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/all")
    fun getUser():List<User>{
        return userService.getAllUser()
    }

    @GetMapping
    fun getUserByUsername(@Param(value = "username") username:String):User? {
        return userService.getUserByUserName(username)
    }


    @GetMapping("{id}")
    fun getUserById(@PathVariable id:Int, request: HttpServletRequest):ResponseEntity<Any>? {
        val token = authenticationToken(request)
        val userId= getDataFromToken(token, "userId")
        val roles = getDataFromToken(token, "roles")

        if((userId.toString().toInt() == id) || roles.toString().contains("ROLE_ADMIN")){
            return ResponseEntity.ok().body(userService.getUserById(id))
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
            responseBody(
                message = FORBIDENMESSAGE,
                httpStatus = HttpStatus.FORBIDDEN.value()
            )
        )
    }

    @GetMapping("{id}/playslists")
    fun getPlayListByUser(@PathVariable id:Int):List<Playlist>? {
        return userService.getPlayListByUser(id)
    }

}