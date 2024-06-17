# Shopping Cart Application with Redis on Kubernetes ##############################################################

## Basic Rest api's for shopping cart operation using Reddis and springboot 
.yaml files are availabale in kubernetes folder. apply those files in kube cluster.
Make sure that Reddis instance is running in k8.

 kubectl apply -f redis-configmap.yaml
 kubectl apply -f redis-deployment.yaml
 kubectl apply -f redis-api-deployment.yaml
 docker image is available on dockerhub: sajid138/redis-api:latest

# End-points

  curl -X POST "http://localhost:8080/cart/add?userId=1&productId=101&quantity=2" (Add item to cart)
  curl "http://localhost:8080/cart/items?userId=1" (Get item from cart)
  curl -X DELETE "http://localhost:8080/cart/remove?userId=1&productId=101" (Remove item from cart:)
  curl -X DELETE "http://localhost:8080/cart/clear?userId=1" (Clear cart)

## Run application in local machine

  - install Reddis server on local machine
  - Run application




  

