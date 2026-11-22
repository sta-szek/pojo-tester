# Reads CENTRAL_TOKEN into the Gradle properties the publisher expects.
# Accepted forms, in this order:
#   username:password          (the password may itself contain colons)
#   base64(username:password)  (what "Copy user token" puts on the clipboard)
#   the password alone, when CENTRAL_USERNAME is also set
# A token that decodes as a pair wins over CENTRAL_USERNAME, so storing both
# the portal's base64 token and the username does not send the base64 blob
# as the password.

if [ -z "${CENTRAL_TOKEN:-}" ]; then
  echo "Missing repository secret CENTRAL_TOKEN."
  exit 1
fi

token=$(printf '%s' "$CENTRAL_TOKEN" | tr -d '\r\n')
username="${CENTRAL_USERNAME:-}"
password=""
pair=""

if printf '%s' "$token" | grep -q ':'; then
  pair=$token
else
  decoded=$(printf '%s' "$token" | base64 -d 2>/dev/null || true)
  if printf '%s' "$decoded" | grep -q ':'; then
    pair=$decoded
  fi
fi

if [ -n "$pair" ]; then
  username=${pair%%:*}
  password=${pair#*:}
elif [ -n "$username" ]; then
  password=$token
fi

if [ -z "$username" ] || [ -z "$password" ]; then
  echo "CENTRAL_TOKEN must be the Central user token from Account, Generate User Token."
  echo "Store it as username:password, or as the base64 of that pair."
  echo "CENTRAL_USERNAME is only needed when CENTRAL_TOKEN is the password by itself."
  exit 1
fi

echo "Central user token parsed (username length ${#username}, password length ${#password})."

export ORG_GRADLE_PROJECT_mavenCentralUsername="$username"
export ORG_GRADLE_PROJECT_mavenCentralPassword="$password"
