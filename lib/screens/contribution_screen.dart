import 'package:flutter/material.dart';
import 'package:flutter_application/models/commit.dart';
import 'package:flutter_application/utils/date_time_extension.dart';
import 'package:provider/provider.dart';
import '../providers/github_provider.dart';
import '../widgets/commit_list_tile.dart';
import '../widgets/contribution_heatmap.dart';

class ContributionScreen extends StatelessWidget {
  final TextEditingController _controller = TextEditingController();

  @override
  Widget build(BuildContext context) {
    final provider = Provider.of<GitHubProvider>(context);

    return Padding(
      padding: const EdgeInsets.all(16.0),
      child: Column(
        children: [
          TextField(
            controller: _controller,
            decoration: InputDecoration(labelText: 'GitHub Username'),
          ),
          SizedBox(height: 16),
          ElevatedButton(
            onPressed: () {
              provider.fetchContributions(_controller.text);
            },
            child: Text('Fetch Contributions'),
          ),
          if (provider.loading) CircularProgressIndicator(),
          if (provider.dataFound != null)
            provider.dataFound!
                ? Expanded(
                    child: Column(
                      children: [
                        ContributionHeatmap(
                          contributions: _generateHeatmapData(provider.commits),
                          maxContributions: _getMaxContributions(provider.commits),
                        ),
                        Expanded(
                          child: ListView.builder(
                            itemCount: provider.commits.length,
                            itemBuilder: (context, index) {
                              return CommitListTile(commit: provider.commits[index]);
                            },
                          ),
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
    // Implementar lógica para gerar dados do heatmap a partir dos commits
    // Exemplo simplificado:
    List<List<int>> data = List.generate(7, (_) => List.generate(52, (_) => 0));
    for (var commit in commits) {
      DateTime date = DateTime.parse(commit.date);
      int week = date.weekOfYear - 1;
      int day = date.weekday - 1;
      data[day][week]++;
    }
    return data;
  }

  int _getMaxContributions(List<Commit> commits) {
    // Implementar lógica para obter o máximo de contribuições em um dia
    // Exemplo simplificado:
    return commits.length;
  }
}