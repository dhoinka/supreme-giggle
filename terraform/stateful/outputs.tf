output "workload_identity_provider" {
  description = "Full resource name of the Workload Identity Provider — use this in GitHub Actions (id-token: write)"
  value       = google_iam_workload_identity_pool_provider.github.name
}

output "github_actions_service_account" {
  description = "Email of the service account impersonated by GitHub Actions"
  value       = google_service_account.github_actions.email
}

output "artifact_registry_repository_url" {
  description = "Full URL of the Artifact Registry repository"
  value       = "${var.region}-docker.pkg.dev/${var.project_id}/${var.artifact_registry_repository_id}"
}

output "firestore_database" {
  description = "Firestore database resource name"
  value       = google_firestore_database.default.name
}

output "firestore_location" {
  description = "Firestore location configured for the project"
  value       = google_firestore_database.default.location_id
}


