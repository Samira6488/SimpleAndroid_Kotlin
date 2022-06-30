package ir.matiran.kotlin_sample

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.matiran.kotlin_sample.databinding.RecyclerviewRowBinding
import java.util.ArrayList


class TransactionListAdapter(
    context: MainActivity?,
    detList: ArrayList<Profile>?
) : RecyclerView.Adapter<TransactionListAdapter.TransactionViewHolder>() {

    private val DetailList: ArrayList<Profile>?
    private val detInflater: LayoutInflater

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TransactionViewHolder {

        val binding: RecyclerviewRowBinding = RecyclerviewRowBinding.inflate(
            detInflater,
            parent, false
        )
        return TransactionViewHolder(binding, this)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val detCurrent = DetailList!![position]
        holder.bindView(detCurrent)
    }

    override fun getItemCount(): Int {
        return DetailList!!.size
    }


    //*****************************************************************************
    inner class TransactionViewHolder(
        recyclerviewrowBinding: RecyclerviewRowBinding,
        adapter: TransactionListAdapter
    ) :
        RecyclerView.ViewHolder(recyclerviewrowBinding.getRoot()), View.OnClickListener {

        var recBinding: RecyclerviewRowBinding
        val mAdapter: TransactionListAdapter


        fun bindView(DetailList: Profile?) {
            recBinding.selectedcurrencyTv.setText("  Size=" + DetailList?.size.toString() + "  Price=" + DetailList?.price.toString() + "  Side=" + DetailList?.side.toString() + "  Timestamp=" + DetailList?.timestamp)
        }

        init {
            this.recBinding = recyclerviewrowBinding
            mAdapter = adapter
            recyclerviewrowBinding.selectedcurrencyTv.setOnClickListener(this)
        }
        override fun onClick(v: View) {

            // Get the position of the item that was clicked.
            val mPosition = layoutPosition
            // Use that to access the affected item in CurrencyList.
            val element = DetailList?.get(mPosition)
            // Change the word in the mWordList.
            DetailList?.set(mPosition, element!!)
            val context = v.context
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(
                Intent.EXTRA_TEXT,
                "  Size=" + element?.size.toString() + "  Price=" + element?.price.toString() + "  Side=" + element?.side.toString() + "  Timestamp=" + element?.timestamp
            )
            sendIntent.type = "text/plain"
            val shareIntent = Intent.createChooser(sendIntent, null)
            context.startActivity(shareIntent)
        }
    }

    init {
        detInflater = LayoutInflater.from(context)
        DetailList = detList
    }
}