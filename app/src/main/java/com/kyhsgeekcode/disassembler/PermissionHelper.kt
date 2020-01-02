package com.kyhsgeekcode.disassembler

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import com.kyhsgeekcode.disassembler.util.UIUtil

val TAG = "PermissionHelper"
/////////////////////////////////////End Show **** dialog///////////////////////////////////////////
fun requestAppPermissions(a: Activity) {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
        a.onRequestPermissionsResult(MainActivity.REQUEST_WRITE_STORAGE_REQUEST_CODE,
                null, intArrayOf(PackageManager.PERMISSION_GRANTED))
        return
    }
    if (hasReadPermissions(a) && hasWritePermissions(a) /*&&hasGetAccountPermissions(a)*/) {
        Log.i(TAG, "Has permissions")
        a.onRequestPermissionsResult(MainActivity.REQUEST_WRITE_STORAGE_REQUEST_CODE,
                null, intArrayOf(PackageManager.PERMISSION_GRANTED))
        return
    }
    showPermissionRationales(a, Runnable {
        a.requestPermissions(arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE //,Mani fest.permission.GET_ACCOUNTS
        ), MainActivity.REQUEST_WRITE_STORAGE_REQUEST_CODE) // your request code
    })
}

fun hasGetAccountPermissions(c: Context): Boolean {
    return c.checkSelfPermission(Manifest.permission.GET_ACCOUNTS) == PackageManager.PERMISSION_GRANTED
}

fun hasReadPermissions(c: Context): Boolean {
    return c.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
}

fun hasWritePermissions(c: Context): Boolean {
    return c.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
}

fun showPermissionRationales(a: Activity, run: Runnable?) {
    UIUtil.ShowAlertDialog(a, a.getString(R.string.permissions),
            a.getString(R.string.permissionMsg)
    ) { p1, p2 ->
        run?.run()
        //requestAppPermissions(a);
    }
}