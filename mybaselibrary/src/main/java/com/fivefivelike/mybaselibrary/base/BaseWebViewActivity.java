package com.fivefivelike.mybaselibrary.base;

/**
 * Created by liugongce on 2017/10/13.
 */

public class BaseWebViewActivity extends BaseDataBindActivity<BaseWebViewDelegate, BaseWebViewDataBinder> {
    @Override
    public BaseWebViewDataBinder getDataBinder(BaseWebViewDelegate viewDelegate) {
        return new BaseWebViewDataBinder(viewDelegate);
    }

    @Override
    protected void onServiceSuccess(String data, String info, int status, int requestCode) {

    }

    @Override
    protected Class<BaseWebViewDelegate> getDelegateClass() {
        return BaseWebViewDelegate.class;
    }
}
