package com.example.crudapp.Database

import androidx.room.*

@Dao
interface KurirDao {
    @Insert
    suspend fun addKurir(kurir: Kurir)

    @Update
    suspend fun updateKurir(kurir: Kurir)

    @Delete
    suspend fun deleteKurir(kurir: Kurir)

    @Query("SELECT * FROM Kurir")
    suspend fun getAllKurir(): List<Kurir>

    @Query("SELECT * FROM Kurir WHERE id=:kurir_id")
    suspend fun getKurir(kurir_id: Int) : List<Kurir>
}