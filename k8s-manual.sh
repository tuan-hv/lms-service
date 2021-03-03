docker build --no-cache -t lms/loan-service:v1.0.0 .
docker tag lms/loanmng-services:v1.0.0 registry.developer.smartosc.vn/lms/loan-service:v1.0.0
docker push registry.developer.smartosc.vn/lending/loan-service:v1.0.0