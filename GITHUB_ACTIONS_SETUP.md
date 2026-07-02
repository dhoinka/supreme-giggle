# GitHub Actions Setup for supreme-giggle

## Quick list (what to add in GitHub)

- **Secrets (required):** `WIF_PROVIDER`, `WIF_SERVICE_ACCOUNT`, `GCP_PROJECT_ID`
- **Variables (required):** none
- **Variables (optional):** `REGISTRY`, `IMAGE_NAME` (only if you want to make these configurable in the workflow)

This document explains how to set up the GitHub Actions "build" workflow to push artifacts to GCP Artifact Registry using Spring Boot's `bootBuildImage` task.

## Prerequisites

1. GCP project with Terraform infrastructure deployed (see `terraform/stateful`)
2. Workload Identity Federation configured in GCP
3. GitHub repository access

## GitHub Secrets Configuration

The build workflow requires the following secrets to be configured in GitHub:

### 1. `WIF_PROVIDER`
The Workload Identity Provider resource name for GitHub Actions OIDC.

To get this value, run:
```bash
cd terraform/stateful
terraform output workload_identity_provider
```

Then in GitHub:
1. Go to **Settings** → **Secrets and variables** → **Actions**
2. Click **New repository secret**
3. Name: `WIF_PROVIDER`
4. Value: (paste the output from above)

### 2. `WIF_SERVICE_ACCOUNT`
The service account email that GitHub Actions will impersonate.

To get this value, run:
```bash
cd terraform/stateful
terraform output github_actions_service_account
```

Then in GitHub:
1. Go to **Settings** → **Secrets and variables** → **Actions**
2. Click **New repository secret**
3. Name: `WIF_SERVICE_ACCOUNT`
4. Value: (paste the output from above)

### 3. `GCP_PROJECT_ID`
Your GCP project ID.

To get this value:
```bash
cd terraform/stateful
terraform output artifact_registry_repository_url
```

Extract the project ID from the URL format: `region-docker.pkg.dev/PROJECT_ID/repository`

Then in GitHub:
1. Go to **Settings** → **Secrets and variables** → **Actions**
2. Click **New repository secret**
3. Name: `GCP_PROJECT_ID`
4. Value: (your GCP project ID)

## Workflow Behavior

The `build` workflow:

1. **Triggers on:**
   - Push to `main` branch
   - Pull requests to `main` branch

2. **Steps:**
   - Checks out the code
   - Sets up Java 21 with Gradle cache
   - Authenticates to GCP using Workload Identity Federation
   - Configures Docker authentication with Artifact Registry
   - Builds the project with Gradle (including tests)
   - Builds OCI image using Spring Boot's `bootBuildImage` Gradle task
   - Publishes image to GCP Artifact Registry (only on pushes to main)

3. **Image Building:**
   - Uses Paketo Buildpacks with Jammy builder image
   - Automatically configures Java 21 runtime
   - No Dockerfile needed - image is built entirely by `bootBuildImage`

4. **Push Conditions:**
   - Image is tagged with the git commit SHA
   - Images are only published on successful pushes to the main branch
   - Pull requests build the image locally but don't push to registry

## Artifact Registry Location

Images will be pushed to:
```
us-central1-docker.pkg.dev/<PROJECT_ID>/supreme-giggle:<COMMIT_SHA>
```

## Troubleshooting

If the workflow fails:

1. **Auth errors:** Verify that the Workload Identity Provider and Service Account secrets are correct
2. **Build errors:** Check that Java 21 and Gradle are compatible with your code
3. **bootBuildImage errors:** Ensure Docker daemon is accessible and buildpacks builder image can be pulled
4. **Publish errors:** Verify the GCP_PROJECT_ID secret is correct and matches your GCP project

## Local Image Building

To build the image locally using the same method:

```bash
# For local testing (no publish)
./gradlew bootBuildImage

# For publishing to a registry
REGISTRY=us-central1-docker.pkg.dev \
GCP_PROJECT_ID=your-project-id \
IMAGE_NAME=supreme-giggle \
IMAGE_SHA=local \
PUBLISH_IMAGE=true \
./gradlew bootBuildImage
```

## Region Configuration

The workflow currently uses `us-central1` as the region for the Artifact Registry. If your repository is in a different region, update the `REGISTRY` environment variable in `.github/workflows/build.yaml`.
