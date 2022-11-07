package com.kpoma.film.repository

import com.kpoma.film.model.Playlist
import com.kpoma.film.model.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface PlaylistRepository: JpaRepository<Playlist, Int> {
    fun findPlaylistByName(name:String): Optional<Playlist>

    fun findPlaylistByUserByUserId(user: User):Optional<List<Playlist>>
}