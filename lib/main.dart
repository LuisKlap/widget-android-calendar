import 'package:flutter/material.dart';
import 'widgets/contribution_heatmap_widget.dart';

void main() {
  runApp(MyApp());
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
