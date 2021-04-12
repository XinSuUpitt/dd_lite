package com.suxin.ddlite.view

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class EndlessScrollListener(private val mLayoutManager: RecyclerView.LayoutManager) : RecyclerView.OnScrollListener() {

    // The minimum amount of items to have below your current scroll position
    // before loading more.
    private val visibleThreshold = 5
    private val invalidViewIndex = -1
    private var currentPage = 0
    private var previousTotalItemCount = 0
    private var loading = true
    private val startingPageIndex = 0

    override fun onScrolled(view: RecyclerView, dx: Int, dy: Int) {
        val firstVisibleItem = when (mLayoutManager) {
            is LinearLayoutManager -> mLayoutManager.findFirstVisibleItemPosition()
            else -> invalidViewIndex
        }
        val visibleItemCount = view.childCount
        val totalItemCount = mLayoutManager.itemCount

        if (totalItemCount < previousTotalItemCount) {
            this.currentPage = this.startingPageIndex
            this.previousTotalItemCount = totalItemCount
            if (totalItemCount == 0) {
                this.loading = true
            }
        }

        if (loading && totalItemCount > previousTotalItemCount) {
            loading = false
            previousTotalItemCount = totalItemCount
        }

        if (!loading && totalItemCount - visibleItemCount <= firstVisibleItem + visibleThreshold) {
            currentPage++
            onLoadMore(currentPage, totalItemCount)
            loading = true
        }
    }

    abstract fun onLoadMore(page: Int, totalItemsCount: Int)
}