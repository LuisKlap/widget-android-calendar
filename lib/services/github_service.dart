import 'dart:convert';
import 'package:http/http.dart' as http;
import '../models/commit.dart';

class GitHubService {
  static const String token = '';

  Future<List<Commit>> fetchContributions(String usernameToFetch) async {
    final year = DateTime.now().year;
    final since = '$year-01-01T00:00:00Z';
    final until = '$year-12-31T23:59:59Z';

    final reposResponse = await http.get(
      Uri.parse('https://api.github.com/users/$usernameToFetch/repos'),
      headers: {
        'Accept': 'application/vnd.github.v3+json',
        'Authorization': 'token $token',
      },
    );

    if (reposResponse.statusCode != 200) {
      throw Exception('Failed to load repos');
    }

    final repos = json.decode(reposResponse.body) as List;
    List<Commit> allCommits = [];

    for (final repo in repos) {
      int page = 1;
      while (true) {
        final commitsResponse = await http.get(
          Uri.parse('https://api.github.com/repos/$usernameToFetch/${repo['name']}/commits?since=$since&until=$until&per_page=100&page=$page'),
          headers: {
            'Accept': 'application/vnd.github.v3+json',
            'Authorization': 'token $token',
          },
        );

        if (commitsResponse.statusCode != 200) {
          break;
        }

        final newCommits = json.decode(commitsResponse.body) as List;
        if (newCommits.isEmpty) {
          break;
        }

        allCommits.addAll(newCommits.map((commit) => Commit.fromJson(commit)).toList());
        page++;
      }
    }

    return allCommits;
  }
}