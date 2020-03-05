package com.lib.update;

import android.app.Activity;
import android.content.Context;

import com.lib.network.CommonOkHttpClient;
import com.lib.network.listener.DisposeDataHandle;
import com.lib.network.listener.DisposeDataListener;
import com.lib.network.request.CommonRequest;
import com.lib.network.utils.ResponseEntityToModule;
import com.lib.ui.views.CommonDialog;
import com.lib.update.constant.Constants;
import com.lib.update.model.UpdateModel;
import com.lib.update.utils.Utils;

public final class UpdateHelper {

  public static final String UPDATE_FILE_KEY = "apk";

  public static String UPDATE_ACTION;
  //SDK全局Context, 供子模块用
  private static Context mContext;

  public static void init(Context context) {
    mContext = context;
    UPDATE_ACTION = mContext.getPackageName() + ".INSTALL";
  }

  public static Context getContext() {
    return mContext;
  }

  //外部检查更新方法
  public static void checkUpdate(final Activity activity) {
    CommonOkHttpClient.get(CommonRequest.
            createGetRequest(Constants.CHECK_UPDATE, null),
        new DisposeDataHandle(new DisposeDataListener() {
          @Override public void onSuccess(Object responseObj) {
            final UpdateModel updateModel = (UpdateModel) responseObj;
            if (Utils.getVersionCode(mContext) < updateModel.data.currentVersion) {
              //说明有新版本,开始下载
              CommonDialog dialog =
                  new CommonDialog(activity, mContext.getString(R.string.update_new_version),
                      mContext.getString(R.string.update_title),
                      mContext.getString(R.string.update_install),
                      mContext.getString(R.string.cancel), new CommonDialog.DialogClickListener() {
                    @Override public void onDialogClick() {
                      UpdateService.startService(mContext);
                    }
                  });
              dialog.show();
            } else {
              //弹出一个toast提示当前已经是最新版本等处理
            }
          }

          @Override public void onFailure(Object reasonObj) {
            onSuccess(
                ResponseEntityToModule.parseJsonToModule(MockData.UPDATE_DATA, UpdateModel.class));
          }
        }, UpdateModel.class));
  }
}
