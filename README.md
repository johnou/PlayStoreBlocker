# PlayStoreBlocker

Minimal Android Accessibility Service that silently blocks access to the Google Play Store.

---

### What it does

When the Play Store (`com.android.vending`) is opened, this service intercepts the window event and immediately triggers a global back action — effectively closing it before the user can interact.

This is intended for use on child-managed devices (e.g. Family Link) where direct Play Store blocking isn't otherwise available.

---

### How it works

- No launcher activity
- No icon in app drawer
- No visible UI
- Launches automatically once the accessibility service is enabled manually via system settings
- Labeled inconspicuously as `System Framework Monitor` to avoid tampering

---

### Build Instructions

This project supports custom signing for both `debug` and `release` builds.

You'll need to define signing credentials in `gradle.properties`:

```properties
# Release signing
RELEASE_STORE_FILE=/home/youruser/.keystore/release.jks
RELEASE_KEY_ALIAS=releaseAlias
RELEASE_STORE_PASSWORD=yourPassword
RELEASE_KEY_PASSWORD=yourPassword

# Debug signing
DEBUG_STORE_FILE=/home/youruser/.keystore/debug.jks
DEBUG_KEY_ALIAS=debugAlias
DEBUG_STORE_PASSWORD=yourPassword
DEBUG_KEY_PASSWORD=yourPassword
```

Then run:

```bash
./gradlew assembleRelease
# or
./gradlew assembleDebug
```

APK outputs will land in:
```
app/build/outputs/apk/release/
app/build/outputs/apk/debug/
```

---

### Install Instructions

1. Host the APK locally (e.g., with Python HTTP server):
   ```bash
   python3 -m http.server 8080
   ```

2. Open the URL on the target device:
   ```
   http://<your-local-ip>:8080/app-release.apk
   ```

3. Enable "Install from Unknown Sources" if prompted (disable it afterwards!).

4. After install, go to:
   ```
   Settings > Accessibility > Installed Services > System Framework Monitor
   ```

5. Enable the service. That's it.

---

### Known Limitations

- Service must be manually enabled in Accessibility settings.
- Can be disabled by the user unless the device is locked down (e.g., via Device Owner / Kiosk Mode).

---

### License

MIT. Use it, modify it, don't upload it to the Play Store.
