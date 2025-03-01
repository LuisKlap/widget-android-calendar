import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import '../providers/github_provider.dart';

class ContributionHeatmapWidget extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return ChangeNotifierProvider(
      create: (context) => GitHubProvider(),
      child: ContributionHeatmap(),
    );
  }
}

class ContributionHeatmap extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final provider = Provider.of<GitHubProvider>(context);

    return Container(
      padding: EdgeInsets.all(16.0),
      child: Column(
        children: [
          TextField(
            decoration: InputDecoration(labelText: 'GitHub Username'),
            onSubmitted: (username) {
              provider.fetchContributions(username);
            },
          ),
          // Resto do código...
        ],
      ),
    );
  }
}