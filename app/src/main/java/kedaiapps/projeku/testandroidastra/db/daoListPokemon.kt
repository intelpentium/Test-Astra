package kedaiapps.projeku.testandroidastra.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kedaiapps.projeku.testandroidastra.db.table.ListPokemonTable

@Dao
interface daoListPokemon {
    @Query("SELECT * FROM ListPokemonTable")
    fun getAll() : LiveData<List<ListPokemonTable>>

    @Query("SELECT * FROM ListPokemonTable WHERE id=:id")
    fun getById(id: String): LiveData<ListPokemonTable>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg data: ListPokemonTable)

//    @Query("UPDATE ListPokemonTable SET status=:status WHERE id=:id")
//    fun update(id: String, status: String)

    @Query("DELETE FROM ListPokemonTable")
    fun deleteAll()
}