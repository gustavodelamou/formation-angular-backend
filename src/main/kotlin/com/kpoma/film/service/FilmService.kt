package com.kpoma.film.service

import com.kpoma.film.model.Film
import com.kpoma.film.model.Playlist
import com.kpoma.film.repository.FilmRepository
import com.kpoma.film.repository.PlaylistRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class FilmService(
    @Autowired
    val filmRepository: FilmRepository,
    @Autowired
    val playlistRepository: PlaylistRepository
) {

    fun addFilm(film: Film):Film? {
        if(film!=null){
            return filmRepository.save(film)
        }
        return  null
    }

    fun getFilmByName(name:String): Film? {
        val film = filmRepository.findFilmByName(name)
        if(film.isPresent){
            return film.get()
        }
        return null
    }

    fun getFilmById(id:Int): Film? {
      val film = filmRepository.findById(id)
        if(film.isPresent){
            return film.get()
        }
        return null
    }

    fun getFilmsByYear(annee: Int): List<Film>? {
        val films = filmRepository.getFilmByAnnee(annee)
        if(films.isPresent){
            return films.get()
        }
        return null
    }

    fun getAllFilm(): List<Film>{
        return filmRepository.findAll()
    }


}