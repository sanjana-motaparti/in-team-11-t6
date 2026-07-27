# C4 Context Diagram - ReconX

```mermaid
C4Context
  title C4 Context — ReconX (Level 1)

  Person(trader, "Trader", "Executes trades and monitors positions")
  Person(analyst, "Recon Analyst", "Investigates breaks and runs reconciliations")
  Person(ops, "Ops Admin", "Configures jobs and monitors system health")
  Person(compliance, "Compliance", "Reviews audit trails and reports")

  System(reconx, "ReconX", "Automated trade reconciliation system")

  System_Ext(oms, "OMS", "Order Management System")
  System_Ext(sftp, "SFTP", "File transfer for trade files")
  System_Ext(bloomberg, "Bloomberg", "Market data provider")
  System_Ext(email, "Email", "Notification service")
  System_Ext(sso, "SSO", "Authentication provider")
  System_Ext(grafana, "Grafana", "Monitoring and dashboards")

  Rel(trader, reconx, "Submits trades", "HTTPS")
  Rel(analyst, reconx, "Runs reports and investigates", "HTTPS")
  Rel(ops, reconx, "Monitors and configures", "HTTPS")
  Rel(compliance, reconx, "Reviews audit logs", "HTTPS")

  Rel(reconx, oms, "Fetches trades", "REST/HTTPS")
  Rel(reconx, sftp, "Exports/Imports files", "SFTP")
  Rel(reconx, bloomberg, "Gets market prices", "API/HTTPS")
  Rel(reconx, email, "Sends notifications", "SMTP")
  Rel(reconx, sso, "Validates identities", "OIDC")
  Rel(reconx, grafana, "Exports metrics", "Prometheus")