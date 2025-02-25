class Commit {
  final String date;

  Commit({required this.date});

  factory Commit.fromJson(Map<String, dynamic> json) {
    return Commit(date: json['commit']['author']['date']);
  }
}