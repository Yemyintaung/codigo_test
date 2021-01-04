package com.yma.banks

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.yma.banks.adapter.PersonItemAdapter
import com.yma.banks.detail.PersonDetailActivity
import com.yma.banks.di.kodeinViewModel
import com.yma.banks.viewmodel.PersonListViewModel
import com.yma.banks.model.PersonApiResponse
import com.yma.banks.model.PersonApiResponseContainer
import com.yma.banks.utils.VerticalSpaceItemDecoration
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein


const val TO_DETAIL = "com.yma.bank.detail"
const val SAVE_LIST = "com.yma.bank.person.list"

class MainActivity : AppCompatActivity(), KodeinAware {
    override val kodein: Kodein by closestKodein()
    private val viewModel: PersonListViewModel by kodeinViewModel()
    private val personList: ArrayList<PersonApiResponse> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.personLiveData.observe(
            this,
            Observer<PersonApiResponseContainer>(::onRetrievepersonList)
        )
        viewModel.errorLiveData.observe(
            this,
            Observer<String>(::onRetrievepersonList)
        )
        viewModel.getPersonListFromApi()
    }

    private fun initializeRecycleViewLinear() {
        activity_main_recycler.apply {
            setHasFixedSize(true)
            isNestedScrollingEnabled = false
            layoutManager = LinearLayoutManager(context)
            itemAnimator = DefaultItemAnimator()
            addItemDecoration(VerticalSpaceItemDecoration(20))
        }

        val adapter = PersonItemAdapter(personList) { item: PersonApiResponse ->
            personItemClicked(item)
        }
        activity_main_recycler.adapter = adapter
    }

    private fun personItemClicked(item: PersonApiResponse) {
        val intent = Intent(this, PersonDetailActivity::class.java).apply {
            putExtra(TO_DETAIL, item)
        }
        startActivity(intent)
    }

    private fun onRetrievepersonList(data: Any?) {
        when (data) {
            is PersonApiResponseContainer -> {
                personList.clear()
                data.data.forEach { personList.add(it) }
                initializeRecycleViewLinear()
                activity_main_recycler.adapter!!.notifyDataSetChanged()

                if(personList.isEmpty()){
                    Toast.makeText(this, "Empty", Toast.LENGTH_SHORT).show()
                }
            }
            is String -> {
                Toast.makeText(this, "$data", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        // Save the state of item position
        outState.putParcelableArrayList(SAVE_LIST, personList)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        // Read the state of item position
        personList.clear()
        personList.addAll(savedInstanceState.getParcelableArrayList(SAVE_LIST)!!)
    }
}