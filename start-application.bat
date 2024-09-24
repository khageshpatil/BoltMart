@echo off
start cmd /k "cd auth-service && mvn spring-boot:run"
start cmd /k "cd customer-service && mvn spring-boot:run"
start cmd /k "cd product-service && mvn spring-boot:run"
start cmd /k "cd vendor-service && mvn spring-boot:run"
