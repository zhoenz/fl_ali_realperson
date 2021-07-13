import 'dart:async';

import 'package:flutter/services.dart';

class FlAliRealperson {
  static const MethodChannel _channel =
      const MethodChannel('fl_ali_realperson');
  static Function _callBack;

  static Future<dynamic> _handler(MethodCall methodCall) {
    if ("onRealPersonResult" == methodCall.method) {
      print("onRealPersonResult: " + methodCall.arguments);
      _callBack(methodCall.arguments);
    }
    return Future.value(true);
  }

  static Future<Null> startRealPerson(token, callBack) async {
    _channel.setMethodCallHandler(_handler);
    _callBack = callBack;

    Map<String, dynamic> map = {
      "token": token,
    };
    await _channel.invokeMethod('startRealPerson', map);
  }

  static Future<Null> init(callBack) async {
    _channel.setMethodCallHandler(_handler);
    _callBack = callBack;

    Map<String, dynamic> map = {
    };
    await _channel.invokeMethod('init', map);
  }
}
