import 'package:flutter/material.dart';

import 'package:flutter/services.dart';
import 'package:fl_ali_realperson/fl_ali_realperson.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _platformVersion = 'Unknown';

  @override
  void initState() {
    super.initState();
  }

  void initPlatformState() async {
    String platformVersion = "1";
    try {
      FlAliRealperson.startRealPerson("f96657d2205b4374a208312dad70f3f7",
          (result) {
        print("the realPerson result is :" + result);
      });
    } on PlatformException {
      platformVersion = 'Failed to get platform version.';
    }

    if (!mounted) return;

    setState(() {
      _platformVersion = platformVersion;
    });
  }

  void initReal() {
    FlAliRealperson.init((result) {
      print("REAL PERSON INIT SUCCESS");
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: Column(
            children: [
              TextButton(onPressed: () => initReal(), child: Text("点击初始化实人认证")),
              TextButton(
                  onPressed: () => initPlatformState(),
                  child: Text("点击测试实人认证")),
            ],
          ),
        ),
      ),
    );
  }
}
