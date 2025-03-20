# Group 3 - waa-final-backend

[![Unit Tests](https://github.com/devsteppe9/waa-final-backend/actions/workflows/unit-test.yaml/badge.svg)](https://github.com/devsteppe9/waa-final-backend/actions/workflows/unit-test.yaml) 
[![Deploy to kubernetes cluster](https://github.com/devsteppe9/waa-final-backend/actions/workflows/main.yaml/badge.svg)](https://github.com/devsteppe9/waa-final-backend/actions/workflows/main.yaml)

## Running the Application with Docker Compose

### Building and Running the Database and Backend

To build and run both the PostgreSQL database and the Spring Boot backend, use the following command:

```sh
docker-compose up -d
```

This command will:
- Build the Docker images for the services defined in the `docker-compose.yml` file.
- Start the `postgres` and `backend` services in detached mode.

### Running Only the Database

If you only want to run the PostgreSQL database, use the following command:

```sh
docker-compose up postgres -d
```

This command will:
- Start only the `postgres` service in detached mode.
