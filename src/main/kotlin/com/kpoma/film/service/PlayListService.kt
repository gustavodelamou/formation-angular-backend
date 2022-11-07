package com.kpoma.film.service

import com.kpoma.film.model.Film
import com.kpoma.film.model.Playlist
import com.kpoma.film.model.User
import com.kpoma.film.repository.FilmRepository
import com.kpoma.film.repository.PlaylistRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PlayListService (
    @Autowired private val playlistRepository: PlaylistRepository,
    @Autowired private val filmRepository: FilmRepository
){

    fun addPlaylist(playlist: Playlist): Playlist? {
        if(playlist!=null){
            return playlistRepository.save(playlist)
        }
        return  null
    }

    fun getPlaylistByName(name:String): Playlist? {
        val playlist = playlistRepository.findPlaylistByName(name)
        if(playlist.isPresent){
            return playlist.get()
        }
        return null
    }

    fun getPlaylistById(id:Int): Playlist? {
        val playlist = playlistRepository.findById(id)
        if(playlist.isPresent){
            return playlist.get()
        }
        return null
    }


    fun getAllPlaylist(): List<Playlist>{
        return playlistRepository.findAll()
    }

    fun addFilmToPlaylist(idPlaylist: Int, idFilm: Int): Playlist {
        val playlist = playlistRepository.findById(idPlaylist)
        if(playlist.isPresent){
            val film= filmRepository.findById(idFilm)
            if(film.isPresent){
                playlist.get().idFilms = "${playlist.get().idFilms},${film}"
                playlistRepository.save(playlist.get())
            }
        }

        return playlist.get()
    }


    fun removeFilmFromPlaylist(idPlaylist: Int, idFilm: Int): Playlist {
        val playlist = playlistRepository.findById(idPlaylist)
        if(playlist.isPresent){
            val film= filmRepository.findById(idFilm)
            if(film.isPresent){
                val idFilms = playlist.get().idFilms.split(",").filter { i -> i.toInt()!=film.get().id }
                playlist.get().idFilms = idFilms.toString()
                playlistRepository.save(playlist.get())
            }
        }
        return playlist.get()
    }

    fun listFilmPlaylist(id: Int): List<Film>{
        val playlist = playlistRepository.findById(id)
        var  listFilm = ArrayList<Film>()
        if(playlist.isPresent){
            val idFilm = playlist.get().idFilms.split(",")
            if(idFilm.isNotEmpty()){
                idFilm.forEach { idFilm ->
                    listFilm.add(filmRepository.findById(idFilm.toInt()).get())
                }
            }
        }
        return listFilm
    }


}