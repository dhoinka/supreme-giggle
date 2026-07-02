terraform {
  backend "gcs" {
    bucket = "supreme-giggle-tfstate"
    prefix = "terraform/project"
  }
}

