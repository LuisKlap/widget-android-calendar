import 'package:flutter/material.dart';
import 'package:flutter_app_widgets/flutter_app_widgets.dart';
import 'widgets/contribution_heatmap_widget.dart';

void main() {
  runApp(MyApp());
  AppWidgets.registerAppWidget(ContributionHeatmapWidget());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(title: Text('GitHub Contributions')),
        body: ContributionHeatmapWidget(),
      ),
    );
  }
}
