#!/bin/bash

ECR_REPOSITORY=$AWS_ACCOUNT_ID.dkr.ecr.ap-northeast-2.amazonaws.com/tooda-test:latest
# ecr pull
docker pull "$ECR_REPOSITORY"
# image rename
docker tag "$ECR_REPOSITORY" tooda-app

if [ "$( docker container inspect -f '{{.State.Status}}' tooda_spring )" == "running" ]
then
  docker stop tooda_spring
  docker rm tooda_spring
fi

docker-compose up -d --build
docker image prune -f