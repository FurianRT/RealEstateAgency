package com.furianrt.realestateagency.houselist

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.furianrt.realestateagency.R
import com.furianrt.realestateagency.data.model.House
import com.furianrt.realestateagency.utils.networkAvailability
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_house_list.*
import javax.inject.Inject
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AlertDialog
import com.furianrt.realestateagency.apartmentlist.ApartmentListActivity
import com.furianrt.realestateagency.house.HouseActivity
import com.furianrt.realestateagency.houselist.adapterlist.HouseListAdapter

private const val BUNDLE_RECYCLER_VIEW_STATE = "recyclerState"
private const val EXTRA_HOUSE_ID = "houseId"

class HouseListActivity
    : AppCompatActivity(), HouseListContract.View, HouseListAdapter.OnHouseListInteractionListener {

    @Inject
    lateinit var mPresenter: HouseListContract.Presenter

    private lateinit var mLoadingDialog: Dialog
    private val mListAdapter = HouseListAdapter(this)
    private var mRecyclerViewState: Parcelable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getPresenterComponent(this).inject(this)
        setContentView(R.layout.activity_house_list)

        mRecyclerViewState = savedInstanceState?.getParcelable(BUNDLE_RECYCLER_VIEW_STATE)

        setupUi()
    }

    private fun setupUi() {
        supportActionBar?.setTitle(R.string.activity_house_list_title)

        mLoadingDialog = AlertDialog.Builder(this)
                .setView(R.layout.dialog_loading)
                .setCancelable(false)
                .create()
        mLoadingDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        list_houses.apply {
            layoutManager = LinearLayoutManager(this@HouseListActivity)
            adapter = mListAdapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_house_list_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean =
            when (item?.itemId) {
                R.id.menu_sync -> {
                    mPresenter.onButtonSyncClick()
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }

    override fun onListItemClick(house: House) {
        mPresenter.onHouseClick(house)
    }

    override fun onButtonApartmentListClick(house: House) {
        mPresenter.onButtonApartmentListClick(house)
    }

    override fun showHouses(houses: List<House>) {
        mListAdapter.submitList(houses)
        mRecyclerViewState?.let {
            list_houses.layoutManager?.onRestoreInstanceState(it)
            mRecyclerViewState = null
        }
    }

    override fun showViewHouse(houseId: Long) {
        val intent = Intent(this, HouseActivity::class.java)
        intent.putExtra(EXTRA_HOUSE_ID, houseId)
        startActivity(intent)
    }

    override fun showViewApartmentList(houseId: Long) {
        val intent = Intent(this, ApartmentListActivity::class.java)
        intent.putExtra(EXTRA_HOUSE_ID, houseId)
        startActivity(intent)
    }

    override fun isNetworkAvailable(): Boolean = networkAvailability(this)

    override fun showNetworkErrorMessage() {
        Snackbar.make(layout_root_house_list, R.string.network_error, Snackbar.LENGTH_SHORT).show()
    }

    override fun showLoadingIndicator() {
        mLoadingDialog.show()
    }

    override fun hideLoadingIndicator() {
        mLoadingDialog.dismiss()
    }

    override fun onStart() {
        super.onStart()
        mPresenter.attachView(this)
        mPresenter.loadHouses()
    }

    override fun onStop() {
        super.onStop()
        mPresenter.detachView()
    }

    override fun onDestroy() {
        super.onDestroy()
        mListAdapter.listener = null
    }
}
