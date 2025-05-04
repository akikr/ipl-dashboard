# Deployment via Docker compose

Deployment steps:-

- Setup ENV variables:

```sh
IMAGE_REPO=<image-registry/<username>
# e.g., docker.io/<username> OR ghcr.io/<username>
SERVER_HOST=<server-vm-ip>
IMAGE_TAG=<app-version/latest>
```

- Run command:

```sh
docker compose -f compose-prod.yaml down --volumes
docker compose -f compose-prod.yaml up -d
```
