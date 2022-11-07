package com.kpoma.film.repository

import com.kpoma.film.model.Film
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.Optional

interface FilmRepository: JpaRepository<Film, Int> {
    fun findFilmByName(name:String):Optional<Film>

    @Query(name = "select f from film where annee:=annee", nativeQuery = true)
    fun getFilmByAnnee(annee:Int): Optional<List<Film>>

    fun findFilmByAnnee(annee:Int) :Optional<List<Film>>

}