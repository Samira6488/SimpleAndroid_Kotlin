package ir.matiran.kotlin_sample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import ir.matiran.kotlin_sample.databinding.FragmentListBinding
import java.util.*
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager


class ListFragment : Fragment(), CurrencyListAdapter.ItemClickListener {
    var binding: FragmentListBinding? = null
    //private var CurrencyList = LinkedList<String>()
   // private var moneyprofilelistinfo = ProfileListInfo()
    private var mAdapter: CurrencyListAdapter? = null
    lateinit var navController: NavController
    var model: MoneyViewModel? = null

    fun ListFragment() {
        // Required empty public constructor
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

        model = ViewModelProvider(activity!!)[MoneyViewModel::class.java]
        FillRecycleView()

        return binding?.getRoot()

    }

    override fun onViewCreated(v: View, savedInstanceState: Bundle?) {
        super.onViewCreated(v, savedInstanceState)
        navController = Navigation.findNavController(v)
    }

    fun FillRecycleView() {

        model!!.getCurrency()!!.observe(this, { currency ->
            if (currency == null)
                return@observe

            var CurrencyList = LinkedList<String>()
            CurrencyList = model!!.getCurrency()!!.value!!

            mAdapter = CurrencyListAdapter(requireContext() as MainActivity, CurrencyList, this)
            binding?.currencyrecviewRv?.setAdapter(mAdapter)
            binding?.currencyrecviewRv?.setLayoutManager(LinearLayoutManager(activity))

        })

        model!!.loadMoney()
        FetchServer()
    }


    fun FetchServer() {

        model!!.getTransactions()!!.observe(this, { transactions ->
            if (transactions == null)
                return@observe

            var moneyprofilelistinfo = ProfileListInfo()
            moneyprofilelistinfo = model!!.getTransactions()!!.value!!
            mAdapter!!.setProfileList(moneyprofilelistinfo)
            mAdapter!!.notifyDataSetChanged()
        })
        model!!.fetchTransactions(requireContext() as MainActivity)
    }


    companion object {
        fun newInstance(): ListFragment {
            return ListFragment()
        }
    }

    override fun onItemClick(CurrencyList: String?) {
        val bundle = Bundle()
        bundle.putString("CurrencyName", CurrencyList)
        navController?.navigate(R.id.action_mainFragment_to_fragment_second, bundle)
    }
}