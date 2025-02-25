import 'package:flutter/material.dart';
import '../services/github_service.dart';
import '../models/commit.dart';

class GitHubProvider with ChangeNotifier {
  final GitHubService _gitHubService = GitHubService();
  bool _loading = false;
  bool? _dataFound;
  List<Commit> _commits = [];

  bool get loading => _loading;
  bool? get dataFound => _dataFound;
  List<Commit> get commits => _commits;

  Future<void> fetchContributions(String usernameToFetch) async {
    _loading = true;
    _dataFound = null;
    notifyListeners();

    try {
      _commits = await _gitHubService.fetchContributions(usernameToFetch);
      _dataFound = _commits.isNotEmpty;
    } catch (error) {
      _dataFound = false;
    } finally {
      _loading = false;
      notifyListeners();
    }
  }
}