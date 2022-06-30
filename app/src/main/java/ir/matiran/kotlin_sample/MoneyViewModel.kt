package ir.matiran.kotlin_sample

import android.graphics.Color
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.ArrayList
import java.util.LinkedList
import cn.pedant.SweetAlert.SweetAlertDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoneyViewModel : ViewModel() {
   private var currency : MutableLiveData<LinkedList<String>?>? = null

    private var transactions : MutableLiveData<ProfileListInfo?>? = null

    private var currencyprofiles : MutableLiveData<ArrayList<Profile>>? = null

    private var moneyprofilelistinfo : ProfileListInfo? = null
    private var pDialog: SweetAlertDialog? = null


    fun getCurrency(): LiveData<LinkedList<String>?>? {
        return currency
    }

    fun getTransactions(): LiveData<ProfileListInfo?>? {
        return transactions
    }

    fun getProfiles(): LiveData<ArrayList<Profile>>? {
        return currencyprofiles
    }

    fun loadMoney() {
        var moneyList = currency?.value

        // Put initial data into the word list.(32)
        for (i in 0..32) {
            moneyList?.addLast(moneyprofilelistinfo?.GetCoins(i).toString())
        }
        currency?.value = moneyList
    }

    fun fetchTransactions(context: MainActivity?) {
        // Loading Dialog
        pDialog = SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE)
        pDialog!!.progressHelper.barColor = Color.parseColor("#dc86a5")
        pDialog!!.titleText = "Loading"
        pDialog!!.setCancelable(false)
        pDialog!!.show()

        val apiInterface = ApiInterface.create().getCurrencyApi()
        apiInterface?.enqueue( object : Callback<ProfileListInfo?>{
            override fun onResponse(call: Call<ProfileListInfo?>, response: Response<ProfileListInfo?>) {

                pDialog?.cancel()
                Log.wtf("all list", "Get all list: $response")
                moneyprofilelistinfo = response.body()!!

                transactions?.setValue(moneyprofilelistinfo)
            }

            override fun onFailure(call: Call<ProfileListInfo?>, t: Throwable) {
                Log.d("TAG", "Response = $t")
                pDialog!!.cancel()
            }
        })
    }

    @Throws(IllegalAccessException::class)
    fun fetchProfiles(coinName: String) {
        val fields = ProfileListInfo::class.java.fields
        for (i in fields.indices) {
            Log.wtf("Extract", "Field Value: " + fields[i].name)
            if (fields[i].name == coinName) {
                currencyprofiles!!.value = fields[i][moneyprofilelistinfo] as ArrayList<Profile>
            }
        }
    }

    init {
         currency = MutableLiveData()
        val list = LinkedList<String>()
         currency!!.setValue(list)

        transactions = MutableLiveData()
        val transList = ProfileListInfo()
        transactions!!.setValue(transList)

        currencyprofiles = MutableLiveData()
        val listprofile = ArrayList<Profile>()
        currencyprofiles!!.setValue(listprofile)

        moneyprofilelistinfo = ProfileListInfo()

    }




}
