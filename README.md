# hexagonal-pet-clinic

The infamous Pet Clinic Spring Boot application implemented with Hexagonal Architecture

When running with the local-h2-tcp (default) profile, the application will start an H2 database in TCP mode. This allows you to connect to 
the database using a client like DBeaver - jdbc URL: jdbc:h2:tcp://localhost:9092/mem:petclinicdb