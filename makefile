

default: debug
release: build-release move-artifact

setup:
	mkdir -p artifacts
	mkdir -p artifacts/android-root/system/priv-app/Beacon
	mkdir -p artifacts/android-root/META-INF/com/google/android/

clean:
	./gradlew clean

build-debug: app
	./gradlew assembleDebug

build-release: app
	./gradlew assembleRelease

move-artifact: app/build/outputs/apk/debug/app-debug.apk
	cp app/build/outputs/apk/debug/app-debug.apk artifacts/android-root/system/priv-app/Beacon/app.beacon.apk

priv-debug: build-debug
	adb root
	adb shell remount || adb shell mount -o remount,rw /
	adb shell mkdir -p /system/priv-app/Beacon/
	adb push artifacts/android-root/system/priv-app/Beacon/app.beacon.apk /system/priv-app/Beacon/app.beacon.apk
	adb shell chmod a+rwx,g-w,o-w /system/priv-app/Beacon
	adb reboot

debug: build-debug move-artifact
	adb install -r artifacts/android-root/system/priv-app/Beacon/app.beacon.apk

debug-zip:
	cd artifacts/android-root && zip artifacts/app.beacon.debug-build.zip -r9 .

generate-kls:
	@echo "Generating kls-classpath"
	@./gradlew :app:printCpath --quiet 2>/dev/null | sed 's/:$$//' > kls-classpath
	@echo "Done."
