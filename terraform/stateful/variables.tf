variable "project_id" {
  description = "GCP project ID"
  type        = string
}

variable "region" {
  description = "GCP region"
  type        = string
  default     = "us-central1"
}

variable "github_org" {
  description = "GitHub organisation or user name"
  type        = string
}

variable "github_repo" {
  description = "GitHub repository name (without the org prefix)"
  type        = string
}

variable "artifact_registry_repository_id" {
  description = "ID for the Artifact Registry repository"
  type        = string
}

variable "artifact_registry_format" {
  description = "Format of the Artifact Registry repository (e.g. DOCKER, MAVEN)"
  type        = string
  default     = "DOCKER"
}

variable "firestore_database_name" {
  description = "Firestore database name. Keep '(default)' to stay aligned with free-tier quota rules."
  type        = string
  default     = "(default)"
}

variable "firestore_location" {
  description = "Firestore location ID. Multi-region US (nam5) is recommended for free tier availability."
  type        = string
  default     = "us-central1"
}

variable "firestore_collection" {
  description = "Firestore collection name for shortened URLs. Must match the collection used by the application."
  type        = string
  default     = "feature-flag-collection"
}
