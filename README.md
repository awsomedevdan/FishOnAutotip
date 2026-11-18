# FishOnAutotip — Minecraft 26.1.2 community port (source)

Based on [Kimberlyow/FishOnAutotip](https://github.com/Kimberlyow/FishOnAutotip) (GPL-3.0).

Preserves the original client-side automatic tipping behavior and the Cloth Config / Mod Menu settings screen.
The six default tip amounts are unchanged. **This port has not been compiled or tested against the game** in the preparation environment; API changes may still require Java source adjustments.

## Build on macOS

1. Install/use **JDK 25** (`java -version` should show 25).
2. Open Terminal inside this directory.
3. Run:

   ```sh
   chmod +x gradlew
   ./gradlew clean build
   ```

4. If successful, use `build/libs/fishonautotip-1.0.1+26.1.2.jar` (not `-sources.jar`).

## Build launcher notes

`gradlew` is a self-contained **shell bootstrap script** that downloads the Gradle 9.5.1 distribution
if it is not already cached. This is a functional substitute for the official Gradle wrapper JAR,
which could not be transferred into this ZIP. The `gradle/wrapper/gradle-wrapper.properties`
file records the distribution URL. On Windows, use the included `gradlew.bat` bootstrap.

You will need internet access for the initial Gradle and Minecraft / Fabric dependency downloads.

## Dependencies

- Minecraft 26.1.2 / Fabric Loader 0.19.5
- Fabric API 0.155.3+26.1.2
- Cloth Config 26.1.154 (bundled via jar-in-jar)
- Mod Menu 18.0.2 (separate installation)
- JDK 25 to build

## Original source note

This reconstruction includes the original mod behavior and three Java classes, plus the original-style
translation resource and GPL license. The original PNG icon is omitted (it is not essential to build).
Preserves the original project's authorship attribution. Do not replace the author's official downloads with this untested build.

## 26.1.2 config screen fix

Uses `AutoConfigClient.getConfigScreen(...)` instead of the removed `AutoConfig.getConfigScreen(...)` API in Cloth Config 26.1. This port has not yet been compiled/tested in this environment.
