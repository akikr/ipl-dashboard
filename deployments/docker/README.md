# Deployment via Docker compose

Deployment setps:-

- Setup ENV variables:

```sh
SERVER_HOST=<server-host-ip/localhost>
IMAGE_TAG=<app-version/latest>
```

- Run command:

```sh
docker compose -f compose-prod.yaml down --volumes
docker compose -f compose-prod.yaml up -d
```
