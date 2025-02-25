import 'package:flutter/material.dart';
import 'package:flutter_app_widgets/flutter_app_widgets.dart';
import 'package:provider/provider.dart';
import '../providers/github_provider.dart';
import '../utils/date_time_extension.dart';
import '../models/commit.dart';

class ContributionHeatmapWidget extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return AppWidget(
      id: 'contribution_heatmap_widget',
      name: 'Contribution Heatmap',
      description: 'Displays a GitHub-like contribution heatmap',
      provider: ChangeNotifierProvider(
        create: (context) => GitHubProvider(),
        child: ContributionHeatmap(),
      ),
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
          SizedBox(height: 16),
          if (provider.loading) CircularProgressIndicator(),
          if (provider.dataFound != null)
            provider.dataFound!
                ? Expanded(
                    child: Column(
                      children: [
                        ContributionHeatmapGrid(
                          contributions: _generateHeatmapData(provider.commits),
                          maxContributions: _getMaxContributions(provider.commits),
                        ),
                      ],
                    ),
                  )
                : Text('No data found'),
        ],
      ),
    );
  }

  List<List<int>> _generateHeatmapData(List<Commit> commits) {
    List<List<int>> data = List.generate(7, (_) => List.generate(52, (_) => 0));
    for (var commit in commits) {
      DateTime date = commit.date;
      int week = date.weekOfYear - 1;
      int day = date.weekday - 1;
      data[day][week]++;
    }
    return data;
  }

  int _getMaxContributions(List<Commit> commits) {
    return commits.length;
  }
}

class ContributionHeatmapGrid extends StatelessWidget {
  final List<List<int>> contributions;
  final int maxContributions;

  ContributionHeatmapGrid({
    required this.contributions,
    required this.maxContributions,
  });

  @override
  Widget build(BuildContext context) {
    return Column(
      children: contributions.map((week) {
        return Row(
          children: week.map((day) {
            return Container(
              margin: EdgeInsets.all(2.0),
              width: 16.0,
              height: 16.0,
              color: _getColorForContributions(day),
            );
          }).toList(),
        );
      }).toList(),
    );
  }

  Color _getColorForContributions(int count) {
    if (count == 0) {
      return Colors.grey[200]!;
    } else if (count <= maxContributions * 0.25) {
      return Colors.green[100]!;
    } else if (count <= maxContributions * 0.5) {
      return Colors.green[300]!;
    } else if (count <= maxContributions * 0.75) {
      return Colors.green[500]!;
    } else {
      return Colors.green[700]!;
    }
  }
}