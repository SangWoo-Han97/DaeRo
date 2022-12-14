package com.ssafy.daero.data.repository.paging

import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import com.ssafy.daero.application.App
import com.ssafy.daero.data.dto.trip.TripAlbumItem
import com.ssafy.daero.data.remote.TripApi
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class TripMyAlbumDataSource(private val tripApi: TripApi) : RxPagingSource<Int, TripAlbumItem>() {
    override fun getRefreshKey(state: PagingState<Int, TripAlbumItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, TripAlbumItem>> {
        val page = params.key ?: 1
        return tripApi.getMyAlbum(App.prefs.userSeq, page)
            .subscribeOn(Schedulers.io())
            .map {
                LoadResult.Page(
                    data = it.results,
                    prevKey = null,
                    nextKey = if (page >= it.total_page) null else page + 1
                ) as LoadResult<Int, TripAlbumItem>
            }
            .onErrorReturn {
                LoadResult.Error(it)
            }
    }
}