# -----------------------------------------------------------------
# Enable the Firestore API
# -----------------------------------------------------------------
resource "google_project_service" "firestore" {
  project            = var.project_id
  service            = "firestore.googleapis.com"
  disable_on_destroy = false
}

# -----------------------------------------------------------------
# Firestore database
# Keep the default database in native mode for typical app usage.
# -----------------------------------------------------------------
resource "google_firestore_database" "default" {
  project     = var.project_id
  name        = var.firestore_database_name
  location_id = var.firestore_location
  type        = "FIRESTORE_NATIVE"

  depends_on = [google_project_service.firestore]
}

# -----------------------------------------------------------------
# Firestore TTL policy
# Documents with an `expireAt` timestamp field will be automatically
# deleted by Firestore once that timestamp is in the past.
# -----------------------------------------------------------------
resource "google_firestore_field" "ttl" {
  project    = var.project_id
  database   = google_firestore_database.default.name
  collection = var.firestore_collection
  field      = "expireAt"

  ttl_config {}

  depends_on = [google_firestore_database.default]
}
