# conductor-workers
This is a base for any Netflix Conductor Orchestrator installation, no matter if it's a Docker or a traditional installation.

# Pre-requisites
* A Netflix Conductor Instance running (2.9.1 or higher)
* Maven 4.0
* Netflix Conductor 1.11.4 (Common and Client Libraries)
* jUnit 4.10
* Mockito 1.9.5

* Any other Libraries that may be used in each worker (JDBC, REST API, ISO-8583 parser, etc)

# Project Architecture
This project consist of 3 packages:
* Model: This consist of any models needed for any Database/REST calls in the workers
* Utils: This contains every utilities used in the workers, such as logging utilities, casting, async tasks, etc.
* Workers: This contains all the workers used in the Workflow. Here we can have any number of workers, for any number of workflows (Meaning, we don't need a Worker Driver/Repository for each Workflow, but it's recomended to separate workers by usage or actions).
* Driver: The main driving class, which will recieve and route every call from Conductor to their corresponding worker. If the call contains a worker that doesn't exist in the Worker's repository, it logs the call. 

In this example, this worker Driver is a simple Main() Class, but it can be easily modified to work as a SpringBoot Application (If you're planning to run it in a virtual server, such as AWS EC2 or Google Compute Engine), a Docker container (If you're planning to run it in a container HUB such as AWS ECS) or in a Serverless Architecture (Such as AWS Lambda).
