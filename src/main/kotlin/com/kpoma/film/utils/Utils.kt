package com.kpoma.film.utils

import com.kpoma.film.model.ExceptionResponse
import com.kpoma.film.model.User
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.http.HttpStatus
import java.util.*
import javax.servlet.http.HttpServletRequest

const val SERCRET_KEY ="Technolyne.com"
const val ISSUER = "Technolyne"
const val EXPIRATION_DATE =  60 * 60 * 1000
const val FORBIDENMESSAGE = "Erreur de connexion, ou vous n'etes pas autorise"
const val NOTFOUNDMESSAGE = ""


fun generateAccessToken(user: User): String{

    return Jwts.builder().setSubject(user.username)
        .claim("roles", user.roles)
        .claim("userId",user.id)
        .signWith(SignatureAlgorithm.HS256, SERCRET_KEY)
        //.setIssuer(ISSUER)
        .setIssuedAt(Date(System.currentTimeMillis()))
        .setExpiration(Date(System.currentTimeMillis() + EXPIRATION_DATE))
        .compact()
}

fun checkValidAccesstoken(token:String): Boolean{
    try {
        Jwts.parser().setSigningKey(SERCRET_KEY).parseClaimsJws(token)
        return true
    }catch (ex:Exception){
    }
    return false
}


fun parseClaims(token: String): Claims {
    return Jwts.parser().setSigningKey(SERCRET_KEY).parseClaimsJws(token).body
}

fun getDataFromToken(token: String, key: String): Any? {
    val claims = parseClaims(token)
    return claims[key]
}

fun authenticationToken(request: HttpServletRequest): String{
    return request.getHeader("Authorization").split(" ")[1].trim()
}


fun responseBody(httpStatus: Int, message: String): ExceptionResponse{
    return ExceptionResponse(httpStatus,message)
}


