# SSWR Agent Instructions

Android pregnancy week calculator (Kotlin/Java, single `app/` module, unified single-app architecture with Google Play In-App Purchase to remove ads).

## Commands

```bash
./gradlew assembleDebug
./gradlew test                                                    # JVM unit tests, no device needed
./gradlew test --tests "de.ingoreschke.sswr.PregnancyDateTest"   # single class
./gradlew connectedAndroidTest                                    # requires device/emulator
./gradlew installDebug
```

## Architecture

- **`PregnancyDate.java`** — sole domain/logic class; takes `(LocalDate today, LocalDate dueDate)`, computes all derived values. Only unit-tested class.
- **`ActivityIr`** (abstract) — base activity; holds ad unit IDs and `isLiteVersion()`.
- **`BillingManager.kt`** — Google Play Billing 7.x manager for handling the "remove_ads" in-app purchase.
- **`SswrMainActivity.kt`** — main screen; date pickers, persists due date to `SharedPreferences`, drives `PregnancyDate`, handles fetus illustration display, updates and pins widget, controls banner ads.
- **`WeekInfo.java`** — receives week number via Intent extra `"week"`, displays content from `strings_weekinfo.xml` (weeks 1–42, keys `info_title_week_N` / `info_text_week_N`) and week illustration.
- **`Widget.java`** — `AppWidgetProvider` reading `SharedPreferences` and calling `PregnancyDate` independently of the activity.

## Conventions

- **Monetization & Ads:** Guard banner ad requests with `if (isLiteVersion())`. `BillingManager` manages the `remove_ads` SKU.
- **Month offset:** `Calendar`/`DatePicker` months are 0-based. Always add `+1` when constructing `LocalDate` (e.g. `LocalDate.of(year, month + 1, day)`).
- **SharedPreferences schema:** file `"et_date"`, keys `etYear` / `etMonth` (0-based) / `etDAY`. Duplicated in `SswrMainActivity` and `Widget` — no shared layer.
- **`versionCode` format:** `{minSdk}{00}{epic}{major}{minor}` — e.g. `2600431` = minSdk 26, v4.3.1.
- **`PregnancyDate` errors:** throws `IllegalArgumentException` with constants `DATE1_TOO_SMALL` or `DATE2_TOO_BIG`; callers match by string equality.
