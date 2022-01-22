#!/bin/bash


docker stop tooda_spring
docker rm tooda_spring

ECR_REPOSITORY=$AWS_ACCOUNT_ID.dkr.ecr.ap-northeast-2.amazonaws.com/tooda-test:latest
aws ecr get-login-password --region ap-northeast-2 | docker login --username AWS --password-stdin "$AWS_ACCOUNT_ID".dkr.ecr.ap-northeast-2.amazonaws.com
docker pull "$ECR_REPOSITORY"
docker tag "$ECR_REPOSITORY" tooda-app

docker-compose up -d --build
docker image prune -f
echo "end deploy"