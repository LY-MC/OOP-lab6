Changes added to the lab:
* Project moved to maven for dependency management and ease of building/packaging/deployment
* Simulation class now has dependency injection
* Simulation logic has been divided into multiple methods for ease of debugging and readability, and also respect the 
single responsibility principle
* Some fields in the Simulation class now accept abstractions, not concrete implementations (Liskov, Dependency Inversion)
* Hardcoded values have been removed and replaced with constants or using random generation libraries (JavaFaker, UUID)
* Getters and Setters have been replaced with annotation generated ones (Lombok)