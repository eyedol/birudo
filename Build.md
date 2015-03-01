This is a Gradle based project. You can build it using Android Studio or from the command line. To
do so follow the steps below:

1. Install the following software:
       - Android SDK:
         http://developer.android.com/sdk/index.html
       - Gradle:
         http://www.gradle.org/downloads
       - Android Studio - Optional:
         http://developer.android.com/sdk/installing/studio.html

2. Run the Android SDK Manager by pressing the SDK Manager toolbar button
   in Android Studio or by running the 'android' command in a terminal
   window.

3. In the Android SDK Manager, ensure that the following are installed,
   and are updated to the latest available version:
       - Tools -> Android SDK Platform-tools (rev 20 or above)
       - Tools -> Android SDK Tools (rev 23.0.2 or above)
       - Tools -> Android SDK Build-tools version 21.1.2 or above
       - Android 4.4 -> SDK Platform (API 14)
       - Android LOllipop (API 21)
       - Extras -> Android Support Repository
       - Extras -> Android Support Library
       - Extras -> Google Play services
       - Extras -> Google Repository

4. Create a file in the root of the project called `local.properties`. Enter the path to your Android SDK.
    Eg. `sdk.dir=/opt/android-studio/sdk`

5. Import the project in Android Studio:

    1. Press File > Import Project
    2. Navigate to and choose the `settings.gradle` file in this project
    3. Press OK


6. Choose Build > Make Project in Android Studio or run the following
    command in the project root directory:

   `./gradlew clean build` Assemble the output(s) of the projects and runs all checks.

7. To install on your test device:

   ```
    ./gradlew installDebug
   ```