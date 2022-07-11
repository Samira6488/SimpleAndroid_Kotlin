package ir.matiran.kotlin_sample.viewModel

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Color
import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.*
import cn.pedant.SweetAlert.SweetAlertDialog
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.matiran.kotlin_sample.model.ProfileListInfo
import ir.matiran.kotlin_sample.view.MainActivity
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoneyViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private var transactions = MutableLiveData<ProfileListInfo> ()
    val transactionsList : LiveData<ProfileListInfo?> = transactions

    private var moneyprofilelistinfo : ProfileListInfo? = null
    private var pDialog: SweetAlertDialog? = null

    fun getTransactions(): LiveData<ProfileListInfo?>? {
        return transactions
    }


    fun fetchTransactions(context: MainActivity?) {

        // Loading Dialog
        pDialog = SweetAlertDialog(context , SweetAlertDialog.PROGRESS_TYPE)
        pDialog!!.progressHelper.barColor = Color.parseColor("#dc86a5")
        pDialog!!.titleText = "Loading"
        pDialog!!.setCancelable(false)
        pDialog!!.show()

        viewModelScope.launch {
            try {
                val response = repository.getCurrency()
                with(response) {
                    if(this?.isSuccessful == true) {
                        body()?.let {
                            transactions.postValue(it)
                            //moneyprofilelistinfo = response?.body()
                            pDialog?.cancel()

                        }
                    } else {
                        Log.d("TAG", "fetch items: ${this?.message()}")
                    }
                }
            } catch (e: Exception) {
                Log.d("TAG", "fetch items 2: ${e.message}")
                pDialog!!.cancel()
            }
        }

    }



    init {
        moneyprofilelistinfo = ProfileListInfo()

    }




}
