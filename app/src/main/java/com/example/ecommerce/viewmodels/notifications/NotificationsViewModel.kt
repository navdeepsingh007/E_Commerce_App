package com.example.ecommerce.viewmodels.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import android.view.View
import com.example.ecommerce.common.UtilsFunctions
import com.example.ecommerce.model.CommonModel
import com.example.ecommerce.model.notificaitons.NotificationsListResponse
import com.example.ecommerce.repositories.notifications.NotificationsRepository
import com.example.ecommerce.viewmodels.BaseViewModel

class NotificationsViewModel : BaseViewModel() {
    private var clearAllNotifications = MutableLiveData<CommonModel>()
    private var notificationList = MutableLiveData<NotificationsListResponse>()

    private var notificationRepository = NotificationsRepository()
    private val mIsUpdating = MutableLiveData<Boolean>()
    private val btnClick = MutableLiveData<String>()

    init {
        if (UtilsFunctions.isNetworkConnectedWithoutToast()) {
            notificationList = notificationRepository.getNotificationsList("")
            clearAllNotifications = notificationRepository.clearAllNotifications("")
        }

    }

    fun clearAll() : LiveData<CommonModel> {
        return clearAllNotifications
    }

    fun getNotificationList() : LiveData<NotificationsListResponse> {
        return notificationList
    }

    override fun isLoading() : LiveData<Boolean> {
        return mIsUpdating
    }

    override fun isClick() : LiveData<String> {
        return btnClick
    }

    override fun clickListener(v : View) {
        btnClick.value = v.resources.getResourceName(v.id).split("/")[1]
    }

    fun clearAllNotification(id:String) {
        if (UtilsFunctions.isNetworkConnected()) {
            clearAllNotifications = notificationRepository.clearAllNotifications(id)
            mIsUpdating.postValue(true)
        }
    }

    fun getNotificationsList(userId : String) {
        if (UtilsFunctions.isNetworkConnected()) {
            notificationList = notificationRepository.getNotificationsList(userId)
            mIsUpdating.postValue(true)
        }
    }

}