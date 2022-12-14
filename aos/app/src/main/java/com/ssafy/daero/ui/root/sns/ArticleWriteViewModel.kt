package com.ssafy.daero.ui.root.sns

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.ssafy.daero.base.BaseViewModel
import com.ssafy.daero.data.dto.article.ArticleWriteDayItem
import com.ssafy.daero.data.dto.article.ArticleWriteRequestDto
import com.ssafy.daero.data.dto.article.ArticleWriteTripStampItem
import com.ssafy.daero.data.dto.article.Expense
import com.ssafy.daero.data.repository.TripRepository
import com.ssafy.daero.utils.constant.DEFAULT
import com.ssafy.daero.utils.constant.FAIL
import com.ssafy.daero.utils.constant.SUCCESS
import com.ssafy.daero.utils.time.toServerDate
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class ArticleWriteViewModel : BaseViewModel() {
    private val tripRepository = TripRepository.get()

    var articleWriteRequest: ArticleWriteRequestDto? = null
    var postState = MutableLiveData<Int>()

    val tripStampState = MutableLiveData<Int>()
    var tripStamps: List<List<TripStampDto>> = listOf()
    var linearTripStamps: List<TripStampDto> = listOf()
    var expenses = mutableListOf<Expense>()
    var dayIndex = 0

    var showProgress = MutableLiveData<Boolean>()

    fun postArticle() {
        showProgress.postValue(true)

        articleWriteRequest?.tripExpenses = makeExpenseString()

        val files = linearTripStamps.map {
            MultipartBody.Part.createFormData(
                "files",
                it.imageUrl,
                File(it.imageUrl).asRequestBody("image/jpeg".toMediaTypeOrNull())
            )
        }

        val json = Json.encodeToString(articleWriteRequest!!)
        val requestBody = json.toRequestBody("text/plain".toMediaTypeOrNull())

        addDisposable(
            tripRepository.postArticle(
                files = files,
                //json = json
                json = requestBody
            ).subscribe({
                postState.postValue(SUCCESS)
                showProgress.postValue(false)
            }, { throwable ->
                postState.postValue(FAIL)
                showProgress.postValue(false)
            })
        )
    }

    fun getTripStamps() {
        addDisposable(
            tripRepository.getTripStamps()
                .map { tripStamps ->
                    tripStamps
                        .map { it.toTripStampDto() }
                        .groupBy { it.day }
                        .map { it.value }
                }
                .subscribe({
                    initArticleRequest(it)
                    tripStamps = it
                    linearTripStamps = it.flatten()
                    tripStampState.postValue(SUCCESS)
                }, { throwable ->
                    tripStampState.postValue(FAIL)
                })
        )
    }

    private fun initArticleRequest(tripStamps: List<List<TripStampDto>>) {
        articleWriteRequest = ArticleWriteRequestDto(
            title = "",
            tripComment = "",
            tripExpenses = "",
            rating = 5,
            expose = 'y',
            thumbnailIndex = 0,
            records = tripStamps.map { tripStampDtos ->
                ArticleWriteDayItem(
                    datetime = tripStampDtos[0].dateTime.toServerDate(),
                    dayComment = "",
                    tripStamps = tripStampDtos.map {
                        ArticleWriteTripStampItem(
                            satisfaction = it.satisfaction,
                            tripPlaceSeq = it.tripPlaceSeq
                        )
                    }
                )
            }
        )
    }

    fun finishWriteArticle() {
        articleWriteRequest = null
        tripStamps = listOf()
        linearTripStamps = listOf()
        expenses = mutableListOf()
        dayIndex = 0
    }

    fun getThumbnailIndex(): Int {
        var index = -1
        linearTripStamps.forEachIndexed { _index, tripStampDto ->
            if (tripStampDto.isThumbnail) {
                index = _index
                return@forEachIndexed
            }
        }
        return index
    }

    private fun makeExpenseString(): String {
        if (expenses.isEmpty()) return ""

        var res = "["
        expenses.forEach {
            res += "$it,"
        }
        res = res.substring(0, res.length - 1)
        res += "]"
        return res
    }

    fun deleteAllTripRecord() {
        addDisposable(
            tripRepository.deleteAllTripFollow().subscribe()
        )
        addDisposable(
            tripRepository.deleteAllTripStamps().subscribe()
        )
    }
}

data class TripStampDto(
    var tripPlaceSeq: Int,
    var placeName: String,
    var dateTime: Long,
    var imageUrl: String,
    var satisfaction: Char,
    var day: String,
    var id: Int,
    var isThumbnail: Boolean
)