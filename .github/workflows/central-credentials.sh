# Reads CENTRAL_TOKEN into the Gradle properties the publisher expects.
# Accepted forms:
#   username:password
#   base64(username:password)
#   password, when CENTRAL_USERNAME is also set
# The password may itself contain colons.

if [ -z "${CENTRAL_TOKEN:-}" ]; then
  echo "Missing repository secret CENTRAL_TOKEN."
  exit 1
fi

token=$(printf '%s' "$CENTRAL_TOKEN" | tr -d '\r\n')
username="${CENTRAL_USERNAME:-}"
password=""

if [ -n "$username" ] && ! printf '%s' "$token" | grep -q ':'; then
  password=$token
else
  pair=$token
  if ! printf '%s' "$pair" | grep -q ':'; then
    pair=$(printf '%s' "$token" | base64 -d 2>/dev/null || true)
  fi
  if ! printf '%s' "$pair" | grep -q ':'; then
    echo "CENTRAL_TOKEN must be the Central user token as username:password, or the base64 of that pair."
    echo "The portal shows the two values together when the token is generated."
    exit 1
  fi
  username=${pair%%:*}
  password=${pair#*:}
fi

if [ -z "$username" ] || [ -z "$password" ]; then
  echo "CENTRAL_TOKEN did not contain both a username and a password."
  exit 1
fi

export ORG_GRADLE_PROJECT_mavenCentralUsername="$username"
export ORG_GRADLE_PROJECT_mavenCentralPassword="$password"
