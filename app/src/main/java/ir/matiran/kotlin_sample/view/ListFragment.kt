package ir.matiran.kotlin_sample.view

import android.content.ContextWrapper
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import ir.matiran.kotlin_sample.databinding.FragmentListBinding
import java.util.*
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ir.matiran.kotlin_sample.viewModel.MoneyViewModel
import ir.matiran.kotlin_sample.R
import ir.matiran.kotlin_sample.model.ProfileListInfo
import ir.matiran.kotlin_sample.viewModel.CurrencyListAdapter

@AndroidEntryPoint
class ListFragment : Fragment(), CurrencyListAdapter.ItemClickListener {

    var binding: FragmentListBinding? = null
    var mAdapter: CurrencyListAdapter? = null
    lateinit var navController: NavController
    var moneyprofilelistinfo = ProfileListInfo()
    private val model:MoneyViewModel by viewModels()

    fun ListFragment() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentListBinding.inflate(inflater, container, false)
        binding?.refreshBtn?.setOnClickListener(View.OnClickListener {
            FetchServer()
        })

       FillRecycleView()
        return binding?.getRoot()
    }

    override fun onViewCreated(v: View, savedInstanceState: Bundle?) {
        super.onViewCreated(v, savedInstanceState)
        navController = Navigation.findNavController(v)
    }

    fun FillRecycleView() {

        var CurrencyList = LinkedList<String>()
        var money = ProfileListInfo()

        for (i in 0..32) {
            CurrencyList?.addLast(money?.GetCoins(i).toString())
        }
        mAdapter = CurrencyListAdapter((context as ContextWrapper).baseContext as MainActivity?, CurrencyList, this)
        binding?.currencyrecviewRv?.setAdapter(mAdapter)
        binding?.currencyrecviewRv?.setLayoutManager(LinearLayoutManager(activity))
        mAdapter!!.notifyDataSetChanged()

        FetchServer()
    }


    fun FetchServer() {

        model.transactionsList.observe(viewLifecycleOwner) {
            moneyprofilelistinfo = model!!.getTransactions()!!.value!!
        }
        model.fetchTransactions((context as ContextWrapper).baseContext as MainActivity?)

    }

    companion object {
        fun newInstance(): ListFragment {
            return ListFragment()
        }
    }

    override fun onItemClick(CurrencyList: String?) {
        val bundle = Bundle()
        bundle.putString("CurrencyName", CurrencyList)
        bundle.putSerializable("CurrencyInfo", moneyprofilelistinfo)
        navController?.navigate(R.id.action_mainFragment_to_fragment_second, bundle)
    }
}


