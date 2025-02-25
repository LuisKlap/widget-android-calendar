import 'package:flutter/material.dart';

class ContributionHeatmap extends StatelessWidget {
  final List<List<int>> contributions; // Matriz de contribuições diárias
  final int maxContributions; // Máximo de contribuições em um dia

  ContributionHeatmap({
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