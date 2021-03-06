#!/bin/bash

ECR_REPOSITORY=485616710973.dkr.ecr.ap-northeast-2.amazonaws.com/tooda-test:latest
aws ecr get-login-password --region ap-northeast-2 | docker login --username AWS --password-stdin 485616710973.dkr.ecr.ap-northeast-2.amazonaws.com
docker pull "$ECR_REPOSITORY"
docker tag "$ECR_REPOSITORY" tooda-app

if [ "$( docker container inspect -f '{{.State.Status}}' tooda_spring )" == "running" ];then
  docker stop tooda_spring
  docker rm tooda_spring
fi

PROJECT_PATH=/home/ec2-user/tooda
cd ${PROJECT_PATH}

docker-compose up -d --build
docker image prune -f
