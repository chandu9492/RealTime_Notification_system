#!/bin/bash

CLUSTER_ID=$(docker run --rm confluentinc/cp-kafka:7.4.0 kafka-storage random-uuid)
echo "Generated Cluster ID: $CLUSTER_ID"
echo "KAFKA_CLUSTER_ID=$CLUSTER_ID" > .env