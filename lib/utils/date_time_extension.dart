import 'package:intl/intl.dart';

extension DateTimeExtension on DateTime {
  int get weekOfYear {
    int dayOfYear = int.parse(DateFormat("D").format(this));
    return ((dayOfYear - weekday + 10) / 7).floor();
  }
}