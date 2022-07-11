package ir.matiran.kotlin_sample.viewModel

import javax.inject.Inject

class Repository @Inject constructor(private val apiInterface: ApiInterface) {
    suspend fun getCurrency() = apiInterface.getCurrencyApi()
}