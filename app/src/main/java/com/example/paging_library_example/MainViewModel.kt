package com.example.paging_library_example

import androidx.paging.*
import com.example.paging_library_example.api.Contents
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map

class MainViewModel {

    private val _pagingData = Pager( PagingConfig(pageSize = 20) ){
        LocalDataSource() // pagingSource 연결
    }.flow
        .cachedIn(CoroutineScope(Dispatchers.Main))
        .map {
            it.insertHeaderItem(Contents(0, "header", "header", "header", "header"))
        }

    val pagingData = _pagingData.map { it }
}