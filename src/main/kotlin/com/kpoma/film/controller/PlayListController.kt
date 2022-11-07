package com.kpoma.film.controller

import com.kpoma.film.model.Film
import com.kpoma.film.service.PlayListService
import com.kpoma.film.model.Playlist
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.query.Param
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Suppress("SENSELESS_COMPARISON")
@RestController
@RequestMapping("v1/playlists")
class PlayListController(@Autowired val playListService: PlayListService) {

    @GetMapping("all")
    fun getPlaylists(): List<Playlist>{
       return playListService.getAllPlaylist()
    }

    @PostMapping("/add")
    fun addPlayLit(@RequestBody playlist: Playlist): Playlist?{
        if (playlist!= null){
            return playListService.addPlaylist(playlist)
        }
        return null
    }

    @GetMapping
    fun getPlaylistByName(@Param(value = "name") name:String): Playlist? {
       return playListService.getPlaylistByName(name)
    }

    @GetMapping("{id}")
    fun getPlaylistById(@PathVariable id:Int): Playlist? {
       return playListService.getPlaylistById(id)
    }

    @PostMapping("/film")
    fun addFilmToPlaylist(@Param(value = "idPlaylist") idPlaylist: Int, @Param(value = "idFilm") idFilm: Int): Playlist {
        return playListService.addFilmToPlaylist(idPlaylist, idFilm)
    }

    @DeleteMapping
    fun removeFilmFromPlaylist(@Param(value = "idPlaylist") idPlaylist: Int, @Param(value = "idFilm") idFilm: Int): Playlist {
       return playListService.removeFilmFromPlaylist(idPlaylist, idFilm)
    }

    @GetMapping("/{id}/films")
    fun listFilmPlaylist(@PathVariable id: Int): List<Film>{
       return  playListService.listFilmPlaylist(id)
    }


}
