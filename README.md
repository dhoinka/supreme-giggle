# supreme-giggle

Spring Boot service for storing and serving **features / feature flags** from **Firestore**.

## Configuration

Application settings live under `app`:

```yaml
app:
  base-url: http://localhost:8080
  feature-collection: feature-flag-collection
```

Firestore is created with `FirestoreOptions.getDefaultInstance()`, so authentication uses **Google Application Default
Credentials**.

For local development, authenticate with one of these approaches:

```bash
gcloud auth application-default login
```

## API

### Create feature

`POST /features`

```json
{
  "name": "checkout-banner",
  "displayName": "Checkout banner",
  "description": "Controls the checkout banner",
  "payload": {
    "body": "{\"color\":\"green\"}",
    "format": "json"
  }
}
```

### List features

`GET /features`

Returns full feature documents.

### Get feature by id

`GET /features/{id}`

Returns the full feature document.

### Get feature state

`GET /features/{id}/state`

Returns the lightweight flag state payload:

```json
{
  "enabled": true,
  "payload": {
    "body": "{\"color\":\"green\"}",
    "format": "json"
  }
}
```

## Payload formats

Supported payload format values:

- `text`
- `json`
- `yaml`
- `xml`
- `markdown`

Legacy stored enum names such as `PLAIN_TEXT` are still accepted when reading from Firestore.

## Running tests

```bash
./gradlew test
```
