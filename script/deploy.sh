
ECR_REPOSITORY=$AWS_ACCOUNT_ID.dkr.ecr.ap-northeast-2.amazonaws.com/tooda-test:latest
# ecr pull
docker pull "$ECR_REPOSITORY"
# image rename
docker tag "$ECR_REPOSITORY" tooda-app

if [ "$( docker container inspect -f '{{.State.Status}}' tooda_spring )" == "running" ]
then
  docker stop app
  docker rm app
fi

docker-compose up --build -d
docker image prune -f