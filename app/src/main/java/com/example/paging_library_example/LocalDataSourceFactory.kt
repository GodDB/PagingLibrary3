package com.example.paging_library_example

import androidx.paging.DataSource
import com.example.paging_library_example.api.Contents


class LocalDataSourceFactory : DataSource.Factory<Int, Contents>() {
    override fun create(): DataSource<Int, Contents> {
        return LocalDataSource()
    }
}