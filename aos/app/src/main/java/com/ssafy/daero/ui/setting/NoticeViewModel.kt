package com.ssafy.daero.ui.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ssafy.daero.base.BaseViewModel
import com.ssafy.daero.data.dto.service.NoticeResponseDto
import com.ssafy.daero.data.repository.ServiceRepository
import com.ssafy.daero.utils.constant.FAIL

class NoticeViewModel : BaseViewModel() {
    private val serviceRepository = ServiceRepository.get()

    private val _notices = MutableLiveData<List<NoticeResponseDto>>()
    val notices: LiveData<List<NoticeResponseDto>>
        get() = _notices

    val noticeState = MutableLiveData<Int>()

    fun getNotices() {
        addDisposable(
            serviceRepository.getNotices()
                .subscribe({ response ->
                    _notices.postValue(response.body()!!)
                }, { throwable ->
                    noticeState.postValue(FAIL)
                })
        )
    }
}