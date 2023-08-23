package kedaiapps.projeku.testandroidastra.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kedaiapps.projeku.testandroidastra.db.AstraDB
import kedaiapps.projeku.testandroidastra.db.table.ListPokemonTable
import kedaiapps.projeku.testandroidastra.ext.ioThread
import javax.inject.Inject

@HiltViewModel
class RepoViewModel @Inject constructor(
    val application: Application
) : ViewModel() {

    val db = AstraDB.getDatabase(this.application)

    //======================= Local Database ListPokemon ===================
    fun setListPokemon(name: String, url: String) {
        ioThread {
            db.daoListPokemon().insert(ListPokemonTable(0, name, url))
        }
    }

    fun getListPokemon(): LiveData<List<ListPokemonTable>> {
        return db.daoListPokemon().getAll()
    }

    fun getListPokemonId(fav_id: String): LiveData<ListPokemonTable> {
        return db.daoListPokemon().getById(fav_id)
    }

//    fun updateListPokemon(fav_id: String, status: String){
//        ioThread {
//            db.daoListPokemon().update(fav_id, status)
//        }
//    }

    fun deleteListPokemon() {
        ioThread {
            db.daoListPokemon().deleteAll()
        }
    }
}