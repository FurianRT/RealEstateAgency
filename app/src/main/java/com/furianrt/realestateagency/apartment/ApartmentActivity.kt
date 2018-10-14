package com.furianrt.realestateagency.apartment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.furianrt.realestateagency.R
import com.furianrt.realestateagency.data.model.Apartment
import kotlinx.android.synthetic.main.activity_apartment.*
import javax.inject.Inject

private const val EXTRA_APARTMENT_ID = "apartmentId"

class ApartmentActivity : AppCompatActivity(), ApartmentContract.View {

    @Inject
    lateinit var mPresenter: ApartmentContract.Presenter

    private var mApartmentId = -1L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getPresenterComponent(this).inject(this)
        setContentView(R.layout.activity_apartment)

        mApartmentId = intent.getLongExtra(EXTRA_APARTMENT_ID, -1L)
        if (mApartmentId == -1L) throw IllegalArgumentException()

        setupUi()
    }

    private fun setupUi() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setTitle(R.string.activity_apartment_title)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun showApartmentInfo(apartment: Apartment) {
        text_apartment_id.text = apartment.id.toString()
        text_apartment_floor.text = apartment.floor.toString()
        text_apartment_square.text = apartment.square.toString()
        text_house_id.text = apartment.houseId.toString()
    }

    override fun onStart() {
        super.onStart()
        mPresenter.attachView(this)
        mPresenter.loadApartment(mApartmentId)
    }

    override fun onStop() {
        super.onStop()
        mPresenter.detachView()
    }
}
