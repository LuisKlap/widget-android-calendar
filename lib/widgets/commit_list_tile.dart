import 'package:flutter/material.dart';
import '../models/commit.dart';

class CommitListTile extends StatelessWidget {
  final Commit commit;

  CommitListTile({required this.commit});

  @override
  Widget build(BuildContext context) {
    return ListTile(title: Text(commit.date));
  }
}