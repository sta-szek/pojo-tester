# Reads SIGNING_KEY and SIGNING_PASSWORD into the Gradle properties the
# publisher expects. The key may be ASCII-armored or that block encoded as base64.

if [ -z "${SIGNING_KEY:-}" ] || [ -z "${SIGNING_PASSWORD:-}" ]; then
  echo "The release-ga environment needs secrets SIGNING_KEY and SIGNING_PASSWORD."
  exit 1
fi

case "$SIGNING_KEY" in
  *"BEGIN PGP PRIVATE KEY"*)
    export ORG_GRADLE_PROJECT_signingInMemoryKey="$SIGNING_KEY"
    ;;
  *)
    export ORG_GRADLE_PROJECT_signingInMemoryKey="$(printf '%s' "$SIGNING_KEY" | base64 -d)"
    ;;
esac
export ORG_GRADLE_PROJECT_signingInMemoryKeyPassword="$SIGNING_PASSWORD"
