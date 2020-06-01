package com.example.ecommerce.views.product

import android.graphics.Typeface
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerce.R
import com.example.ecommerce.adapters.product.CustomerReviewsAdapter
import com.example.ecommerce.databinding.ActivityCustomerReviewsBinding
import com.example.ecommerce.utils.BaseActivity

class CustomerAllReviewsActivity : BaseActivity() {
    private lateinit var binding: ActivityCustomerReviewsBinding

    override fun getLayoutId(): Int {
        return R.layout.activity_customer_reviews
    }

    override fun initViews() {
        binding = viewDataBinding as ActivityCustomerReviewsBinding

        initToolbar()
        setCustomerReviewsAdapter()
    }

    private fun initToolbar() {
        binding.commonToolBar.imgToolbarText.text =
            resources.getString(R.string.title_customer_review)
        val headingView = binding.commonToolBar.imgToolbarText

        headingView.setTypeface(headingView.getTypeface(), Typeface.BOLD)

        binding.commonToolBar.imgToolbarText.setTextColor(
            resources.getColor(R.color.headingColor)
        )
    }

    private fun setCustomerReviewsAdapter() {
//        if (notificationsList.size > 0) {
            val notificationAdapter = CustomerReviewsAdapter(this)
            val linearLayoutManager = LinearLayoutManager(this)
            linearLayoutManager.orientation = RecyclerView.VERTICAL
            binding.rvCustomerReviews.layoutManager = linearLayoutManager
            binding.rvCustomerReviews.adapter = notificationAdapter

            binding.rvCustomerReviews.visibility = View.VISIBLE
            binding.tvNoRecord.visibility = View.GONE
//        } else {
//            binding.rvJobHistory.visibility = View.GONE
//            binding.tvNoRecord.visibility = View.VISIBLE
//        }
    }
}