package com.kpoma.film.model

import kotlin.reflect.KClass

class ExceptionResponse(status: Int, msg: String) {
    var httpStatus: Int = status
    var message: String = msg

}