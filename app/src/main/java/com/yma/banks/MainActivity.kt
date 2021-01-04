package com.yma.banks

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.yma.banks.adapter.BankItemAdapter
import com.yma.banks.detail.BankDetailActivity
import com.yma.banks.di.kodeinViewModel
import com.yma.banks.list.BankListViewModel
import com.yma.banks.model.BanksApiResponse
import com.yma.banks.model.BanksApiResponseContainer
import com.yma.banks.utils.VerticalSpaceItemDecoration
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein


const val TO_DETAIL = "com.yma.bank.detail"
const val SAVE_LIST = "com.yma.bank.bank.list"

class MainActivity : AppCompatActivity(), KodeinAware {


    override val kodein : Kodein by closestKodein()
    private val viewModel: BankListViewModel by kodeinViewModel()
    private val bankList: ArrayList<BanksApiResponse> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.banksLiveData.observe(
            this,
            Observer<BanksApiResponseContainer>(::onRetrieveBankList)
        )
        viewModel.errorLiveData.observe(
            this,
            Observer<String>(::onRetrieveBankList)
        )

        viewModel.getBankListFromApi()
    }

    private fun initializeRecycleViewLinear() {
        activity_main_recycler.apply {
            setHasFixedSize(true)
            isNestedScrollingEnabled = false
            layoutManager = LinearLayoutManager(context)
            itemAnimator = DefaultItemAnimator()
            addItemDecoration(VerticalSpaceItemDecoration(20))
        }

        val adapter = BankItemAdapter(bankList) { item: BanksApiResponse ->
            bankItemClicked(item)
        }
        activity_main_recycler.adapter = adapter
    }

    private fun bankItemClicked(item: BanksApiResponse) {
        val intent = Intent(this, BankDetailActivity::class.java).apply {
            putExtra(TO_DETAIL, item)
        }
        startActivity(intent)
    }

    private fun onRetrieveBankList(data: Any?) {
        when (data) {
            is BanksApiResponseContainer -> {
                bankList.clear()
                data.data.forEach { bankList.add(it) }
                initializeRecycleViewLinear()
                activity_main_recycler.adapter!!.notifyDataSetChanged()
            }
            is String -> {
                Toast.makeText(this, "$data", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        // Save the state of item position
        outState.putParcelableArrayList(SAVE_LIST, bankList)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        // Read the state of item position
        bankList.clear()
        bankList.addAll(savedInstanceState.getParcelableArrayList(SAVE_LIST)!!)
    }
}