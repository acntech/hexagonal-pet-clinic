#!/bin/bash

# ===========================
# Script: docker-deploy-mongodb.sh
#
# Description:
# Deploys a MongoDB container using Docker **without SSL/TLS**.
# Does NOT persist data (no volumes).
#
# Requirements:
# - Docker must be installed and running.
# ===========================

# Import logger.sh (Ensure logger.sh exists in the same directory)
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
source "$SCRIPT_DIR/logger.sh" "$@"

# Variables
IMAGE_NAME="mongo:6.0"  # Use a specific version if needed
CONTAINER_NAME="mongodb"
HOST_PORT=27017
CONTAINER_PORT=27017

log_debug "Image Name: $IMAGE_NAME"
log_debug "Container Name: $CONTAINER_NAME"
log_debug "Host Port: $HOST_PORT"
log_debug "Container Port: $CONTAINER_PORT"

# Stop and remove existing container if running
if [ "$(docker ps -q -f name=^${CONTAINER_NAME}$)" ]; then
    log_warn "Stopping running MongoDB container: $CONTAINER_NAME..."
    docker stop "$CONTAINER_NAME"
    docker rm "$CONTAINER_NAME"
fi

# Run MongoDB **without SSL**
log_info "Starting MongoDB container in non-SSL mode..."
docker run -d --name "$CONTAINER_NAME" \
  -p $HOST_PORT:$CONTAINER_PORT \
  "$IMAGE_NAME" --tlsMode disabled

if [ $? -ne 0 ]; then
    log_error "Failed to start MongoDB container: $CONTAINER_NAME"
    exit 1
fi

# Check if the container is running
if [ -z "$(docker ps -q -f name=$CONTAINER_NAME)" ]; then
    log_error "MongoDB container failed to start: $CONTAINER_NAME"
    exit 1
else
    log_info "MongoDB container '$CONTAINER_NAME' is running on port '$HOST_PORT' (non-SSL mode)."
fi

# Tail the logs for debugging
log_info "Tailing logs for MongoDB container: $CONTAINER_NAME..."
docker logs -f "$CONTAINER_NAME"
