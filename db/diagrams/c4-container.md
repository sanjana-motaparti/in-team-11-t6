```markdown
# C4 Container Diagram - ReconX

```mermaid
C4Container
  title C4 Container — ReconX (Level 2)

  System_Boundary(reconxBoundary, "ReconX") {
    Container(spa, "React SPA", "React + Vite", "User interface")
    Container(api, "API Service", "Spring Boot", "REST API with SSE")
    Container(engine, "Recon Engine", "Java", "Reconciliation processing")
    ContainerDb(db, "Postgres", "PostgreSQL 15", "Main database")
    ContainerQueue(kafka, "Kafka", "Apache Kafka", "Event streaming")
    Container(prometheus, "Prometheus", "Prometheus", "Metrics collection")
    Container(grafana, "Grafana", "Grafana", "Dashboard and monitoring")
  }

  System_Ext(user, "User", "Human actor")
  System_Ext(oms, "OMS", "Order Management")
  System_Ext(sso, "SSO", "Authentication")

  Rel(user, spa, "Uses", "HTTPS")
  Rel(spa, api, "REST + SSE", "HTTPS/JSON")
  Rel(api, engine, "Calls", "gRPC")
  Rel(api, db, "Reads/Writes", "JDBC")
  Rel(api, kafka, "Publishes", "Kafka")
  Rel(engine, kafka, "Consumes", "Kafka")
  Rel(api, oms, "Fetches trades", "HTTPS")
  Rel(api, sso, "Validates", "OIDC")
  Rel(engine, db, "Updates", "JDBC")
  Rel(prometheus, db, "Scrapes", "PostgreSQL exporter")
  Rel(grafana, prometheus, "Queries", "PromQL")