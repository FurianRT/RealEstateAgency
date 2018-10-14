package com.furianrt.realestateagency.apartmentlist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.furianrt.realestateagency.R
import com.furianrt.realestateagency.apartment.ApartmentActivity
import com.furianrt.realestateagency.apartmentlist.adapterlist.ApartmentListAdapter
import com.furianrt.realestateagency.data.model.Apartment
import com.github.brnunes.swipeablerecyclerview.SwipeableRecyclerViewTouchListener
import kotlinx.android.synthetic.main.activity_apartment_list.*
import javax.inject.Inject

private const val EXTRA_HOUSE_ID = "houseId"
private const val EXTRA_APARTMENT_ID = "apartmentId"

class ApartmentListActivity : AppCompatActivity(), ApartmentListContract.View,
        ApartmentListAdapter.OnApartmentListInteractionListener {

    @Inject
    lateinit var mPresenter: ApartmentListContract.Presenter

    private var mHouseId = -1L
    private val mListAdapter = ApartmentListAdapter(this)

    private lateinit var mListTouchListener: SwipeableRecyclerViewTouchListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getPresenterComponent(this).inject(this)
        setContentView(R.layout.activity_apartment_list)

        mHouseId = intent.getLongExtra(EXTRA_HOUSE_ID, -1L)
        if (mHouseId == -1L) throw IllegalArgumentException()

        setupUi()
    }

    private fun setupUi() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setTitle(R.string.activity_apartment_list_title)

        mListTouchListener = SwipeableRecyclerViewTouchListener(list_apartments,
                object : SwipeableRecyclerViewTouchListener.SwipeListener {
                    override fun canSwipeLeft(position: Int) = true

                    override fun canSwipeRight(position: Int) = true

                    override fun onDismissedBySwipeLeft(rv: RecyclerView?, positions: IntArray?) {
                        onItemDismissedBySwipe(positions)
                    }

                    override fun onDismissedBySwipeRight(rv: RecyclerView?, positions: IntArray?) {
                        onItemDismissedBySwipe(positions)
                    }
                })

        list_apartments.apply {
            layoutManager = LinearLayoutManager(this@ApartmentListActivity)
            adapter = mListAdapter
            addOnItemTouchListener(mListTouchListener)
        }
    }

    private fun onItemDismissedBySwipe(positions: IntArray?) {
        val items = mutableListOf<Apartment>()
        positions?.forEach {
            val apartment = mListAdapter.list.removeAt(it)
            mListAdapter.notifyItemRemoved(it)
            items.add(apartment)
        }
        mListAdapter.notifyDataSetChanged()
        mPresenter.onApartmentsRemoved(items)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onListItemClick(apartment: Apartment) {
        mPresenter.onApartmentClick(apartment)
    }

    override fun showApartments(apartmentList: List<Apartment>) {
        mListAdapter.list = apartmentList.toMutableList()
    }

    override fun showViewApartment(apartmentId: Long) {
        val intent = Intent(this, ApartmentActivity::class.java)
        intent.putExtra(EXTRA_APARTMENT_ID, apartmentId)
        startActivity(intent)
    }

    override fun onStart() {
        super.onStart()
        mPresenter.attachView(this)
        mPresenter.loadApartments(mHouseId)
    }

    override fun onStop() {
        super.onStop()
        mPresenter.detachView()
    }

    override fun onDestroy() {
        super.onDestroy()
        mListAdapter.listener = null
        list_apartments.removeOnItemTouchListener(mListTouchListener)
    }
}
