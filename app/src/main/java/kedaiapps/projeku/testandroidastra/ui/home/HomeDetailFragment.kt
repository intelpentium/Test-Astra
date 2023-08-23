package kedaiapps.projeku.testandroidastra.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import kedaiapps.projeku.testandroidastra.common.UiState
import kedaiapps.projeku.testandroidastra.databinding.FragmentHomeDetailBinding
import kedaiapps.projeku.testandroidastra.ext.observe
import kedaiapps.projeku.testandroidastra.ext.toast
import kedaiapps.projeku.testandroidastra.modules.base.BaseFragment
import kedaiapps.projeku.testandroidastra.services.entity.ResponseHomeDetail
import kedaiapps.projeku.testandroidastra.ui.home.adapter.AdapterHome
import kedaiapps.projeku.testandroidastra.ui.home.adapter.AdapterHomeDetail
import kedaiapps.projeku.testandroidastra.viewmodel.MainViewModel
import kedaiapps.projeku.testandroidastra.viewmodel.RepoViewModel

class HomeDetailFragment : BaseFragment() {
    lateinit var mBinding: FragmentHomeDetailBinding
    private val viewModel by viewModels<MainViewModel>()
    private val args by navArgs<HomeDetailFragmentArgs>()

    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        AdapterHomeDetail()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        mBinding = FragmentHomeDetailBinding.inflate(inflater, container, false)
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
            ivBack.setOnClickListener {
                findNavController().popBackStack()
            }
            tvTitle.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun initView() {
        viewModel.homeDetail(args.name)

        mBinding.rv.adapter = adapter
    }

    private fun handleState() {
        observe(viewModel.responseHomeDetail) {
            if (it != null) {
                setData(it)
            }
        }

        // loading
        observe(viewModel.loadState) {
            when (it) {
                UiState.Loading -> mBinding.progress.progress.isVisible = true
                UiState.Success -> {
                    mBinding.progress.progress.isVisible = false
                }
                is UiState.Error -> {
                    mBinding.progress.progress.isVisible = false
                    requireActivity().toast(it.message)
                }
            }
        }

    }

    private fun setData(data: ResponseHomeDetail) {

        mBinding.tlbr.tvTitle.text = data.name

        Glide.with(this).load(data.sprites.other.home.front_default)
            .apply(
                RequestOptions()
                    .transform(RoundedCorners(16))
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .dontAnimate()
            ).into(mBinding.image)

        mBinding.judul.text = data.name
        mBinding.weight.text = "Berat : "+data.weight

        adapter.clearData()
        adapter.insertData(data.abilities)
    }
}