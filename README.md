# Pizza Order Receiver

**Default Application Port:** 8082  
**API Doc:** https://127.0.0.1:8082/pizza-order-receiver/swagger-ui.html

## Assumption

- Storing keystore on GIT is just for demo purpose.
- Storing password on GIT is just for demo purpose. They are expected to be stored in Secret on k8s/Openshift.

## Error Code

Error code is formed by `E(Service Number)_(Status Code)_(Sequence Number)` and is used for developers to identify the exact issue.  
Service Number 1 refers to Pizza Order Receiver  
Service Number 2 refers to Pizza Order Generator

## Possible Enhancement

- Currently, HTTPs is used to secure the connection between services. In order to increase the security level, basic auth or client credentials grant flow can be added.
- Concurrency issue for concurrent pizza order creation.
