##### Broadcast Receiver

`registerReceiver()` 与 `unregisterReceiver()` 要出现在 `Activity` 互补的生命周期中。如在 `onCreate()` 中调用 `registerReceiver()`，在 `onDestroy()` 中调用 `unregisterReceiver()` 。

