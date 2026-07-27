```markdown
# C4 Component Diagram - ReconX API Service

```mermaid
C4Component
  title C4 Component — recon-service API (Level 3)

  Container_Ext(ui, "React SPA", "User Interface")
  ContainerDb_Ext(db, "Postgres", "Main database")
  ContainerQueue_Ext(kafka, "Kafka", "Event streaming")

  System_Boundary(apiBoundary, "recon-service API") {
    Component(tradeController, "TradeController", "REST Controller", "Handles trade endpoints")
    Component(reconController, "ReconController", "REST Controller", "Handles reconciliation endpoints")
    Component(jobController, "JobController", "REST Controller", "Handles job management")
    Component(auditController, "AuditController", "REST Controller", "Handles audit endpoints")

    Component(jwtFilter, "JwtAuthFilter", "Security Filter", "Validates JWT tokens")
    Component(methodSecurity, "MethodSecurity", "Security", "Method-level authorization")

    Component(tradeService, "TradeService", "Service", "Business logic for trades")
    Component(reconService, "ReconService", "Service", "Reconciliation logic")
    Component(jobService, "JobService", "Service", "Job scheduling and management")

    Component(tradeRepo, "TradeRepository", "Repository", "Trade data access")
    Component(reconRepo, "ReconRepository", "Repository", "Reconciliation data access")
    Component(jobRepo, "JobRepository", "Repository", "Job data access")

    Component(kafkaProducer, "KafkaProducer", "Producer", "Publishes trade events")
    Component(kafkaConsumer, "KafkaConsumer", "Consumer", "Consumes reconciliation events")
  }

  Rel(ui, tradeController, "Submits trades", "REST/JSON")
  Rel(ui, reconController, "Runs reports", "REST/JSON")
  Rel(ui, jobController, "Manages jobs", "REST/JSON")
  Rel(ui, auditController, "Views audits", "REST/JSON")

  Rel(tradeController, jwtFilter, "Passes through", "Request chain")
  Rel(tradeController, methodSecurity, "Checks", "Method invocation")

  Rel(tradeController, tradeService, "Calls", "Method call")
  Rel(reconController, reconService, "Calls", "Method call")
  Rel(jobController, jobService, "Calls", "Method call")

  Rel(tradeService, tradeRepo, "Uses", "Method call")
  Rel(reconService, reconRepo, "Uses", "Method call")
  Rel(jobService, jobRepo, "Uses", "Method call")

  Rel(tradeService, kafkaProducer, "Publishes", "Kafka")
  Rel(kafkaConsumer, reconService, "Triggers", "Kafka")

  Rel(tradeRepo, db, "Queries", "JDBC")
  Rel(reconRepo, db, "Queries", "JDBC")
  Rel(jobRepo, db, "Queries", "JDBC")