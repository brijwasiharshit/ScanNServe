# ScanNServe
This is an application for Hotel and Homestay owners to provide dynamic menu to users

Project Structure
Annotations -> This is the annotation directory where all the custom annotations 
required in the project will be stored.
Controller -> This is the controller to handle request and response.
Request and response are provided to the user in the form of DTO to support abstraction.
Domain -> This is the package that will contain two different packages. First one is repository which will contain the functions that will be required to interact with the Database.
            Second is the Entity package which will be required to define basic entities that will be needed to provide structure of content of tables.
Dto -> This is the DTO Layer where the format of request and response will be defined.
Service -> This is the service layer where all the business Logic will be written.
Transformer -> This is the transformer layer where convertion of Dto to entity and entity to Dto will take place because all after the service layer, resository will work only on entities not in Dto's.
