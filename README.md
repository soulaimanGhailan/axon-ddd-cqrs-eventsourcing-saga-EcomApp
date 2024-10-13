# E-Commerce Application Backend

This repository contains the backend implementation of an e-commerce application
The project incorporates advanced software design patterns, such as DDD , CQRS ,Event Sourcing and SAGA pattern
leveraging the Axon Framework to ensure a scalable, high-performance, and resilient system.



## Screenshots

1. **Architecture Overview (Axon Console)**
   
  ![Screenshot (34)](https://github.com/user-attachments/assets/dd780b48-c874-40ea-9f18-e022a7d1522e)
  

3. **Saga Pattern - Successful Workflow Logs**
   
   ![Screenshot (35)](https://github.com/user-attachments/assets/10157044-4f61-4bd6-a1b5-ce2828603199)


5. **Saga Pattern - Rollback Workflow Logs**
   
   ![Screenshot (36)](https://github.com/user-attachments/assets/46192ee6-d557-46a6-a717-0318f4e5e7ea)



7. **Event Store (Product Service)**
   
  ![Screenshot (37)](https://github.com/user-attachments/assets/92b06497-cff8-4c50-80b7-a284eb4f8e1c)


9. **Event Snapshots (Product Service)**
    
   ![Screenshot (38)](https://github.com/user-attachments/assets/cf0aac68-f831-4df0-8cae-0da4c6f87e80)



### testing the Application

1. **Start Axon Server**  
      Run Axon Server as a Docker container by executing the following command:
   ```bash
   docker-compose up

2. **Configure and exceute each Microservices**
   Update the application.properties file of each microservice to point to the Docker host IP address for Axon Server
   axon.axonserver.servers=<DOCKER_HOST_IP>

