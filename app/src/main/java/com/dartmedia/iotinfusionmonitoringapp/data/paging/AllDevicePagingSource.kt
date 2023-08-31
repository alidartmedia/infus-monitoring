package com.dartmedia.iotinfusionmonitoringapp.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dartmedia.iotinfusionmonitoringapp.data.remote.api.MainApiService
import com.dartmedia.iotinfusionmonitoringapp.data.remote.dto.main.ProductsItemAllDeviceResponse
import javax.inject.Inject

class AllDevicePagingSource @Inject constructor(private val mainApiService: MainApiService): PagingSource<Int, ProductsItemAllDeviceResponse>() {

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override fun getRefreshKey(state: PagingState<Int, ProductsItemAllDeviceResponse>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ProductsItemAllDeviceResponse> {
        return try {
            val page = params.key ?: INITIAL_PAGE_INDEX
            val response = mainApiService.getAllDevicePagination(page)

            LoadResult.Page(
                data = response.data!!.products,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.nextPage == null) null else page + 1
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }
}