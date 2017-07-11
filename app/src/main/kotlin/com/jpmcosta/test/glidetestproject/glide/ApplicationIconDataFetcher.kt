package com.jpmcosta.test.glidetestproject.glide

import com.bumptech.glide.Priority
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.data.DataFetcher

class ApplicationIconDataFetcher(private val packageName: String) : DataFetcher<ApplicationIcon> {

    override fun loadData(priority: Priority?, callback: DataFetcher.DataCallback<in ApplicationIcon>) {
        callback.onDataReady(ApplicationIcon(packageName))
    }

    override fun cleanup() {
        // Do nothing.
    }

    override fun cancel() {
        // Do nothing.
    }

    override fun getDataClass(): Class<ApplicationIcon> = ApplicationIcon::class.java

    override fun getDataSource(): DataSource = DataSource.LOCAL
}