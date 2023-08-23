package kedaiapps.projeku.testandroidastra.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kedaiapps.projeku.testandroidastra.db.table.ListPokemonTable

@Database(entities = [ListPokemonTable::class], version = 1)
abstract class AstraDB : RoomDatabase(){

    companion object {
        private var INSTANCE: AstraDB? = null

        fun getDatabase(context: Context): AstraDB {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.applicationContext, AstraDB::class.java, "AstraDB")
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE as AstraDB
        }
    }

    abstract fun daoListPokemon() : daoListPokemon
}