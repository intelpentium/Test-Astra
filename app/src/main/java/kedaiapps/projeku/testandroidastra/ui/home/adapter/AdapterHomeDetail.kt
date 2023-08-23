package kedaiapps.projeku.testandroidastra.ui.home.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kedaiapps.projeku.testandroidastra.R
import kedaiapps.projeku.testandroidastra.databinding.ItemHomeBinding
import kedaiapps.projeku.testandroidastra.ext.inflate
import kedaiapps.projeku.testandroidastra.services.entity.ResponseAbilities

class AdapterHomeDetail : RecyclerView.Adapter<AdapterHomeDetail.ViewHolder>() {

    var items: MutableList<ResponseAbilities> = arrayListOf()

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

        fun bind(data: ResponseAbilities){
            with(binding){

                binding.judul.text = data.ability.name

//                line.setOnClickListener {
//                    onClick(data)
//                }
            }
        }
    }

    fun insertData(data : List<ResponseAbilities>){
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