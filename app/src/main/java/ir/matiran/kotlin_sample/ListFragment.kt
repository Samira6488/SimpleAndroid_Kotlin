package ir.matiran.kotlin_sample

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import cn.pedant.SweetAlert.SweetAlertDialog
import ir.matiran.kotlin_sample.databinding.FragmentListBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager


class ListFragment : Fragment(), CurrencyListAdapter.ItemClickListener {
    var binding: FragmentListBinding? = null
    private val CurrencyList = LinkedList<String>()
    private var moneyprofilelistinfo: ProfileListInfo? = null
    private var pDialog: SweetAlertDialog? = null
    private var mAdapter: CurrencyListAdapter? = null
    lateinit var navController: NavController

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

        binding?.refreshBtn?.setOnClickListener(View.OnClickListener { startTask() })
        mAdapter = CurrencyListAdapter(requireContext() as MainActivity, CurrencyList, this)
        binding?.currencyrecviewRv?.setAdapter(mAdapter)
        binding?.currencyrecviewRv?.setLayoutManager(LinearLayoutManager(activity))
        startTask()

        return binding?.getRoot()

    }

    override fun onViewCreated(v: View, savedInstanceState: Bundle?) {
        super.onViewCreated(v, savedInstanceState)
        navController = Navigation.findNavController(v)
    }

    fun startTask() {
        // Loading Dialog
        pDialog = SweetAlertDialog(requireContext(), SweetAlertDialog.PROGRESS_TYPE)
        pDialog?.progressHelper?.barColor = Color.parseColor("#dc86a5")
        pDialog?.titleText = "Loading"
        pDialog?.setCancelable(false)
        pDialog?.show()

        val apiInterface = ApiInterface.create().getCurrencyApi()
        apiInterface?.enqueue( object : Callback<ProfileListInfo?>{
            override fun onResponse(call: Call<ProfileListInfo?>, response: Response<ProfileListInfo?>) {
                pDialog?.cancel()
                Log.wtf("all list", "Get all list: $response")

                moneyprofilelistinfo = response.body()
                mAdapter?.setProfileList(moneyprofilelistinfo)

                // Put initial data into the word list.
                for (i in 0..32) {
                    CurrencyList.addLast(moneyprofilelistinfo?.GetCoins(i).toString())
                }

                // for updating recycleview
                mAdapter?.notifyDataSetChanged()
                Log.wtf("again", "ok again call ")
            }

            override fun onFailure(call: Call<ProfileListInfo?>, t: Throwable) {
                Log.d("TAG", "Response = $t")
                pDialog?.cancel()
            }

        })

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