package com.yma.banks.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.yma.banks.R
import com.yma.banks.databinding.ActivityBankDetailBinding
import com.yma.banks.TO_DETAIL
import com.yma.banks.model.BanksApiResponse

class BankDetailActivity : AppCompatActivity() {
    private var item: BanksApiResponse? = BanksApiResponse()
    private lateinit var binding: ActivityBankDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_bank_detail)

        if (intent != null) {
            item = intent.extras?.getParcelable(TO_DETAIL)
            binding.item = item
            // color white
            //binding.activityBankDetailName.setTextColor(Color.parseColor(item?.fontColor))
        }
    }
}