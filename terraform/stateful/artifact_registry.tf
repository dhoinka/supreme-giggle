# -----------------------------------------------------------------
# Enable Artifact Registry API
# -----------------------------------------------------------------
resource "google_project_service" "artifact_registry" {
  project            = var.project_id
  service            = "artifactregistry.googleapis.com"
  disable_on_destroy = false
}

# -----------------------------------------------------------------
# Artifact Registry repository
# -----------------------------------------------------------------
resource "google_artifact_registry_repository" "main" {
  project       = var.project_id
  location      = var.region
  repository_id = var.artifact_registry_repository_id
  description   = "supreme-giggle application artifacts"
  format        = var.artifact_registry_format

  depends_on = [google_project_service.artifact_registry]
}

