package kedaiapps.projeku.testandroidastra.ui.onboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import kedaiapps.projeku.testandroidastra.common.UiState
import kedaiapps.projeku.testandroidastra.databinding.FragmentOnboardBinding
import kedaiapps.projeku.testandroidastra.ext.observe
import kedaiapps.projeku.testandroidastra.ext.toast
import kedaiapps.projeku.testandroidastra.modules.base.BaseFragment
import kedaiapps.projeku.testandroidastra.viewmodel.MainViewModel
import kedaiapps.projeku.testandroidastra.viewmodel.RepoViewModel

class OnboardFragment : BaseFragment() {
    lateinit var mBinding: FragmentOnboardBinding
    private val viewModel by viewModels<MainViewModel>()
    private val viewModelRepo by viewModels<RepoViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        mBinding = FragmentOnboardBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        handleState()
    }

    private fun initView() {
        mBinding.btnSubmit.setOnClickListener {
            viewModelRepo.deleteListPokemon()
            viewModel.home()
        }
    }

    private fun handleState() {
        observe(viewModel.responseHome) {
            it?.forEach { data ->
                viewModelRepo.setListPokemon(data.name, data.url)
            }
        }

        // loading
        observe(viewModel.loadState) {
            when (it) {
                UiState.Loading -> mBinding.progress.progress.isVisible = true
                UiState.Success -> {
                    mBinding.progress.progress.isVisible = false
                    findNavController().navigate(OnboardFragmentDirections.actionOnboardFragmentToHomeFragment())
                }
                is UiState.Error -> {
                    mBinding.progress.progress.isVisible = false
                    requireActivity().toast(it.message)
                }
            }
        }

    }
}