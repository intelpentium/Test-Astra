package kedaiapps.projeku.testandroidastra.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import kedaiapps.projeku.testandroidastra.R
import kedaiapps.projeku.testandroidastra.databinding.FragmentHomeBinding
import kedaiapps.projeku.testandroidastra.db.table.ListPokemonTable
import kedaiapps.projeku.testandroidastra.ext.hideKeyboard
import kedaiapps.projeku.testandroidastra.ext.observe
import kedaiapps.projeku.testandroidastra.modules.base.BaseFragment
import kedaiapps.projeku.testandroidastra.ui.home.adapter.AdapterHome
import kedaiapps.projeku.testandroidastra.viewmodel.RepoViewModel
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment : BaseFragment() {
    lateinit var mBinding: FragmentHomeBinding
    private val viewModelRepo by viewModels<RepoViewModel>()

    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        AdapterHome(::onClick)
    }

    private var list: List<ListPokemonTable> = ArrayList()
    private var sort: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        mBinding = FragmentHomeBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar()
        initView()
        handleState()
    }

    private fun initToolbar() {
        mBinding.tlbr.apply {
            ivBack.isVisible = false
            tvTitle.text = "Home"
        }
    }

    private fun initView() {

        mBinding.search.addTextChangedListener {
            val filteredModelList: List<ListPokemonTable> = filter(list, it.toString())
            adapter.clearData()
            adapter.insertData(filteredModelList)
        }

        mBinding.search.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                hideKeyboard()
                return@setOnEditorActionListener true
            }
            false
        }

        mBinding.ivSort.setOnClickListener {
            mBinding.search.setText("")
            if(sort){
                sortAsc()
            }else{
                sortDesc()
            }
        }

        mBinding.rv.adapter = adapter
    }

    private fun filter(
        models: List<ListPokemonTable>,
        query: String
    ): List<ListPokemonTable> {

        val filteredModelList: MutableList<ListPokemonTable> = ArrayList()
        for (model in models) {
            val name: String = model.name.toLowerCase()
            if (name.contains(query.toLowerCase())) {
                filteredModelList.add(model)
            }
        }
        return filteredModelList
    }

    private fun sortAsc() {
        sort = false
        val sortAZ =
            Comparator<ListPokemonTable> { data1, data2 -> data1.name.compareTo(data2.name) }
        Collections.sort(list, sortAZ)
        adapter.clearData()
        adapter.insertData(list)

        mBinding.ivSort.setImageResource(R.drawable.ic_sort_down)
    }

    private fun sortDesc() {
        sort = true
        val sortZA =
            Comparator<ListPokemonTable> { data1, data2 -> data2.name.compareTo(data1.name) }
        Collections.sort(list, sortZA)
        adapter.clearData()
        adapter.insertData(list)

        mBinding.ivSort.setImageResource(R.drawable.ic_sort_up)
    }

    private fun handleState() {

        observe(viewModelRepo.getListPokemon()) {
            if (it != null) {
                adapter.clearData()
                adapter.insertData(it)

                list = it
            }
        }
    }

    private fun onClick(data: ListPokemonTable) {
        findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToHomeDetailFragment(
                data.name
            )
        )
    }
}