package com.kpoma.film.exception

import org.springframework.security.core.userdetails.UsernameNotFoundException

class UnAuthorizedException(message: String): Exception(message)