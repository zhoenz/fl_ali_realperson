package com.wxhg.fl_ali_realperson;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.alibaba.security.cloud.CloudRealIdentityTrigger;
import com.alibaba.security.realidentity.RPEventListener;
import com.alibaba.security.realidentity.RPResult;
import com.alibaba.security.realidentity.RPVerify;
import com.alibaba.security.rp.RPSDK;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/**
 * FlAliRealpersonPlugin
 */
public class FlAliRealpersonPlugin implements FlutterPlugin, MethodCallHandler {
    /// The MethodChannel that will the communication between Flutter and native Android
    ///
    /// This local reference serves to register the plugin with the Flutter Engine and unregister it
    /// when the Flutter Engine is detached from the Activity
    private MethodChannel channel;
    private Context context;
    @Override
    public void onAttachedToEngine(@NonNull FlutterPlugin.FlutterPluginBinding binding) {
        this.context = binding.getApplicationContext();
        onAttachedToEngine(binding.getApplicationContext(), binding.getBinaryMessenger());
    }

    private void onAttachedToEngine(Context applicationContext, BinaryMessenger messenger) {
        this.context = applicationContext;
        channel = new MethodChannel(messenger, "fl_ali_realperson");
        channel.setMethodCallHandler(this);
    }


    @Override
    public void onDetachedFromEngine(@NonNull FlutterPlugin.FlutterPluginBinding binding) {
        context = binding.getApplicationContext();
        channel.setMethodCallHandler(null);
        channel = null;
    }



//    @Override
//    public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
//        channel = new MethodChannel(flutterPluginBinding.getFlutterEngine().getDartExecutor(), "fl_ali_realperson");
//        channel.setMethodCallHandler(this);
//        this.context = flutterPluginBinding.getApplicationContext();
////        RPVerify.init(context);
//    }

    // This static function is optional and equivalent to onAttachedToEngine. It supports the old
    // pre-Flutter-1.12 Android projects. You are encouraged to continue supporting
    // plugin registration via this function while apps migrate to use the new Android APIs
    // post-flutter-1.12 via https://flutter.dev/go/android-project-migration.
    //
    // It is encouraged to share logic between onAttachedToEngine and registerWith to keep
    // them functionally equivalent. Only one of onAttachedToEngine or registerWith will be called
    // depending on the user's project. onAttachedToEngine or registerWith must both be defined
    // in the same class.
    public static void registerWith(Registrar registrar) {
//        final MethodChannel channel = new MethodChannel(registrar.messenger(), "fl_ali_realperson");
//        channel.setMethodCallHandler(new FlAliRealpersonPlugin());

        final FlAliRealpersonPlugin instance = new FlAliRealpersonPlugin();
        instance.onAttachedToEngine(registrar.context(), registrar.messenger());


//        final FlAliRealpersonPlugin instance = new FlAliRealpersonPlugin(registrar.activity());
//        registrar.addActivityResultListener(instance);
//        instance.onAttachedToEngine(registrar.context(), registrar.messenger());

    }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
        if (call.method.equals("startRealPerson")) {
            String token = call.argument("token");
            RPVerify.start(context, token, new RPEventListener() {
                @Override
                public void onFinish(RPResult auditResult, String code, String msg) {
                    if (auditResult == RPResult.AUDIT_PASS) {
                        // 认证通过。建议接入方调用实人认证服务端接口DescribeVerifyResult来获取最终的认证状态，并以此为准进行业务上的判断和处理。
                        // do something
                        channel.invokeMethod("onRealPersonResult", "SUCCESS");
                    } else if (auditResult == RPResult.AUDIT_FAIL) {
                        // 认证不通过。建议接入方调用实人认证服务端接口DescribeVerifyResult来获取最终的认证状态，并以此为准进行业务上的判断和处理。
                        // do something
                        channel.invokeMethod("onRealPersonResult", "FAIL");
                    } else if (auditResult == RPResult.AUDIT_NOT) {
                        // 未认证，具体原因可通过code来区分（code取值参见错误码说明），通常是用户主动退出或者姓名身份证号实名校验不匹配等原因，导致未完成认证流程。
                        // do something
                        channel.invokeMethod("onRealPersonResult", "CANCLE");
                    }
                }
            });
        }else if(call.method.equals("init")){
            RPVerify.init(context);
            channel.invokeMethod("onRealPersonResult", "SUCCESS");
        } else {
            result.notImplemented();
        }
    }
}
