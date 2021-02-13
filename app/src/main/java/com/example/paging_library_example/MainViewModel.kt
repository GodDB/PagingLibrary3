package com.example.paging_library_example

import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.paging_library_example.api.Contents

class MainViewModel {

    private val config = PagedList.Config.Builder()
        .setInitialLoadSizeHint(20)
        .setPageSize(10)
        .setPrefetchDistance(5)
        .setEnablePlaceholders(false)
        .build()

    private val _contentsLiveData: LiveData<PagedList<Contents>> =
        LivePagedListBuilder(LocalDataSourceFactory(), config).build()

    val contentsList : LiveData<PagedList<Contents>> = _contentsLiveData.map { it }

}