package com.jl.jlapp.mvp.base;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

import com.jl.jlapp.R;

/**
 * Created by fyf on 2017/12/14.
 */

public abstract class BasePermActivity extends BaseActivity {

    private final int permissionRequestCode = 110;
    private PermissionCall mPermissionCall;

    /* 处理权限问题*/

    /**
     * 对子类提供的申请权限方法
     *
     * @param permissions 申请的权限
     */
    public void requestRunTimePermissions(String[] permissions, PermissionCall call) {
        if (permissions == null || permissions.length == 0)
            return;
        mPermissionCall = call;
        if ((Build.VERSION.SDK_INT < Build.VERSION_CODES.M) || checkPermissionGranted(permissions)) {
            //提示已经拥有权限
            mPermissionCall.requestSuccess();
        } else {
            //申请权限
            requestPermission(permissions, permissionRequestCode);
        }
    }

    public boolean checkPermissionGranted(String... permissions) {
        boolean result = true;
        for (String p : permissions) {
            if (ActivityCompat.checkSelfPermission(BasePermActivity.this, p) != PackageManager.PERMISSION_GRANTED) {
                result = false;
                break;
            }
        }
        return result;
    }

    private boolean shouldShowRequestPermissionRationale(String[] permissions) {
        boolean flag = false;
        for (String p : permissions) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(BasePermActivity.this, p)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    private void requestPermission(final String[] permissions, final int requestCode) {
        if (shouldShowRequestPermissionRationale(permissions)) {
            new AlertDialog.Builder(BasePermActivity.this)
                    .setTitle(R.string.attention)
                    .setMessage(R.string.content_to_request_permission)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(BasePermActivity.this, permissions, requestCode);
                        }
                    }).show();
        } else {
            ActivityCompat.requestPermissions(BasePermActivity.this, permissions, permissionRequestCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == permissionRequestCode) {
            if (verifyPermissions(grantResults)) {
                mPermissionCall.requestSuccess();
                mPermissionCall = null;
            } else {
                mPermissionCall.refused();
                mPermissionCall = null;
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public boolean verifyPermissions(int[] grantResults) {
        // At least one result must be checked.
        if (grantResults.length < 1) {
            return false;
        }

        // Verify that each required permission has been granted, otherwise return false.
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    public interface PermissionCall {
        //申请成功
        void requestSuccess();

        //拒绝
        void refused();
    }


}
