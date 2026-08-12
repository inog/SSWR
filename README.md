# SSWR - Pregnancy Week Calculator

**SSWR** (SchwangerschaftsWochenRechner) is an Android application that helps track pregnancy progress by calculating the current pregnancy week based on the expected due date (E.T. - Errechneter Termin).

## Features

- **Pregnancy Week Calculation**: Enter your due date and instantly see your current pregnancy week
- **Multiple Display Formats**: 
  - Days until due date
  - Days completed in pregnancy
  - "Mutterpass" format (week + days notation)
  - Current pregnancy month
- **Weekly Information**: Get detailed information and individual fetal development illustrations for every pregnancy week (weeks 1–42)
- **Time Machine Mode**: Travel forward or backward in time to see your pregnancy status on specific dates (e.g., holidays)
- **Home Screen Widget**: Quick access to current pregnancy week directly from your home screen
- **Share Progress**: Share your current pregnancy week with friends and family
- **Multilingual**: Available in German and English

## Technical Details

### Build Configuration
- **Min SDK**: Android 8.0 (API 26)
- **Target SDK**: Android 16 (API 36)
- **Language**: Kotlin 2.2.10
- **Java Version**: JDK 17

### Monetization
- Single app distribution (`de.ingoreschke.sswr.lite`)
- Ad-supported with optional **In-App Purchase** (Google Play Billing) to remove ads

### Key Dependencies
- AndroidX AppCompat
- ConstraintLayout
- Google Play Services (Ads)
- Google Play Billing Library (`billing-ktx`)
- Kotlin Standard Library

### Version
- **Current Version**: 4.3.0 (versionCode: 2600430)

## Project Structure

```
app/
├── src/
│   ├── main/
│   │   ├── java/de/ingoreschke/sswr/
│   │   │   ├── About.java
│   │   │   ├── ActivityIr.java
│   │   │   ├── BillingManager.kt
│   │   │   ├── PregnancyDate.java
│   │   │   ├── SswrMainActivity.kt
│   │   │   ├── WeekInfo.java
│   │   │   ├── Widget.java
│   │   │   └── utils/
│   │   ├── res/
│   │   │   ├── layout/           # UI layouts
│   │   │   ├── values/           # German strings & resources
│   │   │   └── values-en/        # English translations
│   │   └── AndroidManifest.xml
│   ├── test/                     # JVM unit tests
│   └── androidTest/              # Android instrumentation tests
├── build.gradle
└── proguard-rules.pro
```

## Building the Project

```bash
# Build debug version
./gradlew assembleDebug

# Build release version
./gradlew assembleRelease

# Run tests
./gradlew test

# Install on device
./gradlew installDebug
```

## Testing

The project includes:
- Unit tests with JUnit and Mockito
- UI tests with Espresso and UIAutomator

## Contact

For bugs, feature requests, or feedback:
- Email: coffee2code@sswr.ingo-reschke.de
- Developer: Ingo Reschke

## Credits

- Icon design by Robert Hahmann

## License

Copyright © Ingo Reschke
