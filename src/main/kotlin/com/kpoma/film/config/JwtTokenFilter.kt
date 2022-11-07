package com.kpoma.film.config

import com.kpoma.film.model.User
import com.kpoma.film.utils.authenticationToken
import com.kpoma.film.utils.checkValidAccesstoken
import com.kpoma.film.utils.getDataFromToken
import com.kpoma.film.utils.parseClaims
import io.jsonwebtoken.Claims
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Service
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Service
class JwtTokenFilter: OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {

        if(!hasAuthorizationBearer(request)){
            filterChain.doFilter(request, response)
            return
        }

        val token = authenticationToken(request)

        if(!checkValidAccesstoken(token)){
           filterChain.doFilter(request, response)
           return
        }

        setSecurtityContext(token, request)
        filterChain.doFilter(request,response)

    }

    fun hasAuthorizationBearer(request: HttpServletRequest): Boolean{
        val header = request.getHeader("Authorization")
        if(header!=null){
            return true
        }
        return false
    }

    fun setSecurtityContext(token: String, request: HttpServletRequest){
        val userDetails = getDetailUser(token)
        userDetails.authorities.forEach { authorities-> println(authorities) }
        val authenticated = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
        authenticated.details = WebAuthenticationDetailsSource().buildDetails(request)
        SecurityContextHolder.getContext().authentication = authenticated
    }

    fun getDetailUser(token: String): UserDetails{
        val user = User()
        val subject= getDataFromToken(token,Claims.SUBJECT)
        val roles =  getDataFromToken(token,"roles")
        user.username =subject.toString().split(",")[0]
        user.roles =roles.toString().replace("[", "").replace("]", "")
        return user
    }






}