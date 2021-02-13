package com.example.paging_library_example

import android.util.Log
import androidx.paging.PositionalDataSource
import com.example.paging_library_example.api.Contents
import com.example.paging_library_example.api.GitApi
import com.example.paging_library_example.api.Response
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class LocalDataSource : PositionalDataSource<Contents>() {

    //replace dagger
    private val gitService = GitApi.create()

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<Contents>) {
        try {
            CoroutineScope(Dispatchers.Main).launch {
                val response = searchRequestToGitApi(params.requestedStartPosition, params.requestedLoadSize)
                Log.d("godgod", "${response}")
                callback.onResult(response.items, 0)
            }
        }
        catch (e : Exception){
            Log.d("godgod", "${e.toString()}")
        }
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<Contents>) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = searchRequestToGitApi(params.startPosition, params.loadSize)
                Log.d("godgod", "${response}")
                callback.onResult(response.items)
            }
            catch (e : Exception){
                Log.d("godgod", "${e.toString()}")
            }
        }
    }

    private suspend fun searchRequestToGitApi(page : Int, perPage : Int) : Response =
        gitService.searchRepos("android", page, perPage)
}