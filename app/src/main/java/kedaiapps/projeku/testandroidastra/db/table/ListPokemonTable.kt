package kedaiapps.projeku.testandroidastra.db.table

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ListPokemonTable(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val url: String,
)