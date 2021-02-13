package com.example.paging_library_example

import androidx.paging.PagingSource
import com.example.paging_library_example.api.Contents
import com.example.paging_library_example.api.GitApi
import com.example.paging_library_example.api.Response
import java.lang.Exception

class LocalDataSource : PagingSource<Int, Contents>() {

    //replace dagger
    private val gitService = GitApi.create()


    private suspend fun searchRequestToGitApi(page: Int, perPage: Int): Response =
        gitService.searchRepos("android", page, perPage)

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Contents> {
        return try {
            val nextPageNumber = params.key ?: 1
            val perPage = params.loadSize

            val response = searchRequestToGitApi(nextPageNumber, perPage)
            LoadResult.Page(
                data = response.items,
                prevKey = null,
                nextKey = nextPageNumber + 1
            )
        } catch (e : Exception){
            LoadResult.Error(e)
        }
    }
}