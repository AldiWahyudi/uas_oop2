package com.example.crudapp.Database

import androidx.room.*

@Dao
interface PaketDao {
    @Insert
    suspend fun addPaket(paket: Paket)

    @Update
    suspend fun updatePaket(paket: Paket)

    @Delete
    suspend fun deletePaket(paket: Paket)

    @Query("SELECT * FROM paket")
    suspend fun getAllPaket(): List<Paket>

    @Query("SELECT * FROM paket WHERE id=:paket_id")
    suspend fun getPaket(paket_id: Int) : List<Paket>

}