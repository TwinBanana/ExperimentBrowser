# ExperimentBrowser
Android Browser

1. Prerequisites

A Linux build machine capable of building Chrome for Linux. Other (Mac/Windows) platforms are not supported for Android.

Getting the code

First, check out and install the depot_tools package.

Then, if you have no existing checkout, create your source directory and get the code:

Checking out the source-
$ git clone git@github.com:TwinBanana/ExperimentBrowser.git

Run the below command to generate the project files
$ gclient runhooks

2. Install build dependencies

Update the system packages required to build by running:

./build/install-build-deps-android.sh

3. Create a build directory and set the build flags with:

$ gn args out/Default

You can replace out/Default with another name you choose inside the out directory.
This command will bring up your editor with the GN build args. In this file add:
#############################################################################
target_os = "android"
target_cpu = "arm"  # (default)
is_debug = false
# Other args you may want to set:
is_component_build = true
is_clang = true
symbol_level = 1  # Faster build with fewer symbols. -g1 rather than -g2
#enable_incremental_javac = true  # Much faster; experimental
enable_nacl = false
remove_webcore_debug_symbols = true
enable_google_now = false
enable_remoting = false
safe_browsing_mode = 2
enable_hangout_services_extension = false
fieldtrial_testing_like_official_build = true
#############################################################################

4. Build the full browser

ninja -C out/Release chrome_public_apk

And deploy it to your Android device:

CHROMIUM_OUTPUT_DIR=$gndir build/android/adb_install_apk.py $gndir/apks/ChromePublic.apk # for gn.

