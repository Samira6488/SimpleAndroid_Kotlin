package ir.matiran.kotlin_sample.viewModel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.matiran.kotlin_sample.databinding.WordlistItemBinding
import ir.matiran.kotlin_sample.model.ProfileListInfo
import ir.matiran.kotlin_sample.view.MainActivity
import java.util.LinkedList

class CurrencyListAdapter(
    context: MainActivity?,
    wordList: LinkedList<String>, clicklistner: ItemClickListener
) : RecyclerView.Adapter<CurrencyListAdapter.WordViewHolder>() {

    private val CurrencyList: LinkedList<String>
    private val clicklistner: ItemClickListener
    private val mInflater: LayoutInflater
    //private var profileList: ProfileListInfo? = null

    /*
    fun setProfileList(profileList: ProfileListInfo?) {
        if (profileList != null) {
            //this.profileList = profileList
        }
    }
*/
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WordViewHolder {

        val binding: WordlistItemBinding = WordlistItemBinding.inflate(mInflater, parent, false)
        //return WordViewHolder(binding, this, profileList!!)
        return WordViewHolder(binding, this)

    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val mCurrent = CurrencyList[position]
        holder.bindView(mCurrent)
    }

    override fun getItemCount(): Int {
        return CurrencyList.size
    }

    //*****************************************************************************
   // , profileList: ProfileListInfo
    inner class WordViewHolder(
        wordlistItemBinding: WordlistItemBinding,
        adapter: CurrencyListAdapter
    ) :
        RecyclerView.ViewHolder(wordlistItemBinding.getRoot()) {
        val mAdapter: CurrencyListAdapter
       // var profileList: ProfileListInfo
        var wordlistItemBinding: WordlistItemBinding

        fun bindView(CurrencyList: String?) {
            wordlistItemBinding.currencylistTv.setText(CurrencyList)
        }

        init {
            this.wordlistItemBinding = wordlistItemBinding
            //this.profileList = profileList
            mAdapter = adapter
            wordlistItemBinding.currencylistTv.setOnClickListener(View.OnClickListener {
                val mPosition = layoutPosition
                clicklistner.onItemClick(CurrencyList[mPosition])
            })
        }
    }

    interface ItemClickListener {
        fun onItemClick(CurrencyList: String?)
    }

    init {
        mInflater = LayoutInflater.from(context)
        CurrencyList = wordList
        this.clicklistner = clicklistner
    }
}