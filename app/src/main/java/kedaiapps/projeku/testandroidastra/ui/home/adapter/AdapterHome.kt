package kedaiapps.projeku.testandroidastra.ui.home.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kedaiapps.projeku.testandroidastra.R
import kedaiapps.projeku.testandroidastra.databinding.ItemHomeBinding
import kedaiapps.projeku.testandroidastra.db.table.ListPokemonTable
import kedaiapps.projeku.testandroidastra.ext.inflate

class AdapterHome (
    private val onClick: (ListPokemonTable) -> Unit
) : RecyclerView.Adapter<AdapterHome.ViewHolder>() {

    var items: MutableList<ListPokemonTable> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.item_home))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        (holder as? ViewHolder)?.bind(items.getOrNull(position)!!)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(containerView: View) : RecyclerView.ViewHolder(containerView){
        private var binding = ItemHomeBinding.bind(containerView)

        fun bind(data: ListPokemonTable){
            with(binding){

                binding.judul.text = data.name

                line.setOnClickListener {
                    onClick(data)
                }
            }
        }
    }

    fun insertData(data : List<ListPokemonTable>){
        data.forEach {
            items.add(it)
            notifyDataSetChanged()
        }
    }

    fun clearData() {
        if (items.isNotEmpty()) {
            items.clear()
            notifyDataSetChanged()
        }
    }
}