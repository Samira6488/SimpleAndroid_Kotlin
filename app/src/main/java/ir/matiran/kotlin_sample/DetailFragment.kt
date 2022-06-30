package ir.matiran.kotlin_sample

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import ir.matiran.kotlin_sample.databinding.FragmentDetailBinding
import java.io.Serializable
import java.util.ArrayList



class DetailFragment : Fragment() {
    var binding: FragmentDetailBinding? = null
    var detAdapter: TransactionListAdapter? = null
    private var nameCurrency: String? = null
    lateinit var navController: NavController
    var model: MoneyViewModel? = null


    fun DetailFragment() {
        // Required empty public constructor
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        binding?.backBtn?.setOnClickListener(View.OnClickListener {
            navController?.navigate(R.id.action_fragment_second_to_mainFragment)
        })
        if (arguments != null) {
            nameCurrency = arguments?.getString("CurrencyName")
            binding?.detcurrencyTv?.setText(nameCurrency)
        }

        // Give the RecyclerView a default layout manager.
        binding?.detailrecviewRv?.setLayoutManager(LinearLayoutManager(activity))
        model = ViewModelProvider(activity!!)[MoneyViewModel::class.java]


            var OneIfo = ArrayList<Profile>()
        try {
            model!!.getProfiles()!!.observe(this, { currencyprofiles ->
                if (currencyprofiles == null)
                    return@observe

                OneIfo = model!!.getProfiles()!!.value!!
                // Create an adapter and supply the data to be displayed.
                detAdapter = TransactionListAdapter(requireContext() as MainActivity, OneIfo)
                // Connect the adapter with the RecyclerView.
                binding!!.detailrecviewRv.adapter = detAdapter
                detAdapter!!.notifyDataSetChanged()
            })
            model!!.fetchProfiles(nameCurrency!!)


        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
        return binding?.getRoot()
    }

    override fun onViewCreated(v: View, savedInstanceState: Bundle?) {
        super.onViewCreated(v, savedInstanceState)
        navController = Navigation.findNavController(v)
    }

    companion object {
        fun newInstance(): DetailFragment {
            return DetailFragment()
        }
    }
}