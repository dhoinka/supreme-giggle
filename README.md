# supreme-giggle

## Firestore storage for shortened URLs

Shortened URLs can be persisted in Firestore by configuring these properties:

- `app.firestore.project-id`
- `app.firestore.credentials-path`
- `app.firestore.collection` (optional, defaults to `shortenUrls`)

If `project-id` and `credentials-path` are set, the app uses Firestore.
If they are not set, the app falls back to in-memory storage.

### Local setup

1. Create a GCP service account with Firestore permissions.
2. Download its JSON key file.
3. Set values in `src/main/resources/application.yaml` or via environment variables.

Example environment variables:

```bash
export APP_FIRESTORE_PROJECT_ID="your-gcp-project-id"
export APP_FIRESTORE_CREDENTIALS_PATH="/absolute/path/to/service-account.json"
export APP_FIRESTORE_COLLECTION="shortenUrls"
```

Run tests:

```bash
./gradlew test
```

