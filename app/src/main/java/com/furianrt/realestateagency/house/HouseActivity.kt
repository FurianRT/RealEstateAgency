package com.furianrt.realestateagency.house

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.furianrt.realestateagency.R
import com.furianrt.realestateagency.data.model.House
import kotlinx.android.synthetic.main.activity_house.*
import javax.inject.Inject

private const val EXTRA_HOUSE_ID = "houseId"

class HouseActivity : AppCompatActivity(), HouseContract.View {

    @Inject
    lateinit var mPresenter: HouseContract.Presenter

    private var mHouseId = -1L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getPresenterComponent(this).inject(this)
        setContentView(R.layout.activity_house)

        mHouseId = intent.getLongExtra(EXTRA_HOUSE_ID, -1L)
        if (mHouseId == -1L) throw IllegalArgumentException()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setTitle(R.string.activity_house_title)
    }

    override fun showHouseInfo(house: House) {
        text_house_name.text = house.name
        text_company.text = house.company
        text_floors.text = house.floors.toString()
        text_house_id.text = house.id.toString()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onStart() {
        super.onStart()
        mPresenter.attachView(this)
        mPresenter.loadHouse(mHouseId)
    }

    override fun onStop() {
        super.onStop()
        mPresenter.detachView()
    }
}
