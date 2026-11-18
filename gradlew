#!/bin/sh
# Small self-contained Gradle bootstrap for this source ZIP (not the canonical Gradle wrapper JAR).
set -eu
SCRIPT_DIR=$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)
VERSION=9.5.1
BASE="${GRADLE_USER_HOME:-$HOME/.gradle}/fishonautotip-bootstrap"
DIST="$BASE/gradle-$VERSION"
if [ ! -x "$DIST/bin/gradle" ]; then
  mkdir -p "$BASE"
  ZIP="$BASE/gradle-$VERSION-bin.zip"
  echo "Downloading Gradle $VERSION (first run only)..."
  if command -v curl >/dev/null 2>&1; then
    curl -fL --retry 3 -o "$ZIP" "https://services.gradle.org/distributions/gradle-$VERSION-bin.zip"
  elif command -v wget >/dev/null 2>&1; then
    wget -O "$ZIP" "https://services.gradle.org/distributions/gradle-$VERSION-bin.zip"
  else
    echo "ERROR: install curl or wget to download Gradle." >&2
    exit 1
  fi
  unzip -oq "$ZIP" -d "$BASE"
fi
cd "$SCRIPT_DIR"
exec "$DIST/bin/gradle" "$@"
