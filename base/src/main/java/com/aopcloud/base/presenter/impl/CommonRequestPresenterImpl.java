package com.aopcloud.base.presenter.impl;


import android.content.Context;

import com.aopcloud.base.common.ICommonViewUi;
import com.aopcloud.base.common.IRequestListener;
import com.aopcloud.base.interactor.ICommonRequestInteractor;
import com.aopcloud.base.interactor.impl.CommonRequestInteractorImpl;
import com.aopcloud.base.presenter.ICommonRequestPresenter;

import java.util.Map;

/**
 * @PackageName : com.aopcloud.palmproject.presenter.impl
 * @File : CommonRequestPresenterImpl.java
 * @Date : 2020/4/27 21:30
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class CommonRequestPresenterImpl implements ICommonRequestPresenter, IRequestListener {

    public Context context;
    public ICommonViewUi iCommonViewUi;
    public ICommonRequestInteractor iCommonRequestInteractor;

    public CommonRequestPresenterImpl(Context context, ICommonViewUi iCommonViewUi) {
        this.context = context;
        this.iCommonViewUi = iCommonViewUi;
        iCommonRequestInteractor = new CommonRequestInteractorImpl(this);
    }

    @Override
    public void request(int eventTag, Context context, String url, Map<String, String> params) {
        iCommonRequestInteractor.request(eventTag, context, url, params);
    }


    @Override
    public void requestJson(int eventTag, Context context, String url, Map<String, String> params) {
        iCommonRequestInteractor.requestJson(eventTag, context, url, params);
    }

    @Override
    public void requestPost(int eventTag, Context context, String url, Map<String, String> params) {
        iCommonRequestInteractor.requestPost(eventTag, context, url, params);
    }

    @Override
    public void requestPostJson(int eventTag, Context context, String url, Map<String, String> params) {
        iCommonRequestInteractor.requestPostJson(eventTag, context, url, params);
    }

    @Override
    public void cancelRequest(Context context) {
        iCommonRequestInteractor.cancelRequest(context);
    }

    @Override
    public void cancelRequest(Object o) {
        iCommonRequestInteractor.cancelRequest(o);
    }


    @Override
    public void onSuccess(int eventTag, String data) {
        iCommonViewUi.getRequestData(eventTag, data);
//        if (HttpStatusUtil.getStatus(data) || eventTag < 0) {
//            iCommonViewUi.getRequestData(eventTag, data);
//        } else {
//            if (HttpStatusUtil.isRelogin(data)) {
//                try {
//                    JSONObject object = new JSONObject(data);
//                    String msg = object.getString("msg");
//                    CommonToast.makeText(context, msg,1);
//                } catch (Exception e) {
//
//                }
////                LoginMsgHelper.exitLogin(context);
////                LoginMsgHelper.reLogin(context); // 重启到登录页面
////                EventBus.getDefault().post(new PostResult(EventBusTags.LOGOUT));
//            }  else {
////                iCommonViewUi.onRequestSuccessException(eventTag, HttpStatusUtil.getStatusMsg(data));
//                iCommonViewUi.onRequestSuccessException(eventTag, HttpStatusUtil.isShowToastStr(eventTag, data));
//            }
//        }
    }

    @Override
    public void onError(int eventTag, String msg) {
        iCommonViewUi.onRequestFailureException(eventTag, msg);
    }

}
