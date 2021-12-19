package com.cherish.homeprojectapp.view.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.cherish.homeprojectapp.data.remote.ResponseManager
import com.cherish.homeprojectapp.databinding.FragmentItemListBinding
import com.cherish.homeprojectapp.view.adapters.ListItemAdapter
import com.cherish.homeprojectapp.view.adapters.ListItemData
import com.cherish.homeprojectapp.viewmodel.MainViewModel


import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class ListItemFragment : Fragment() {
    private var _binding: FragmentItemListBinding? = null
    private val binding get() = _binding!!
    private  val viewModel : MainViewModel by viewModels()
    private var listAdapter : ListItemAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentItemListBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpAdapter()
        getOrganizationList()

    }


    private fun stopShimmer(){
        binding.loading.wrapper.apply {
           binding.loading.shimmerLayout.stopShimmer()
            visibility = View.GONE
        }
    }

    private fun getOrganizationList(){
        viewModel.getOrganization().observe(viewLifecycleOwner){ response ->
            when(response){
                is ResponseManager.Failure -> {
                    stopShimmer()
                    binding.emptyOrError.wrapper.visibility = View.VISIBLE
                }
                is ResponseManager.Loading -> {
                    when(response.state){
                        true -> binding.loading.shimmerLayout.startShimmer()
                        false -> stopShimmer()
                    }
                }
                is ResponseManager.Success -> {
                    binding.recyclerView.visibility = View.VISIBLE
                    val item = response.data.map { ListItemData(it.login, it.url, it.description,it.avatar_url, it.node_id, it.id) }
                    listAdapter!!.submitList(item)
                }
            }
        }
    }

    private fun setUpAdapter(){
        listAdapter = ListItemAdapter{listItem ->
            val action = listItem.id?.let {
                ListItemFragmentDirections.actionFirstFragmentToSecondFragment(
                    it, listItem.login,listItem.description,listItem.node_id)
            }
            findNavController().navigate(action!!)

        }
        binding.recyclerView.adapter = listAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}