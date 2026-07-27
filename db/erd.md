# Entity Relationship Diagram - ReconX

```mermaid
erDiagram
    COUNTERPARTIES ||--o{ TRADES : has
    INSTRUMENTS ||--o{ TRADES : has
    USERS ||--o{ RECON_JOBS : runs
    TRADES ||--o{ SETTLEMENTS : has
    TRADES ||--o{ RECON_BREAKS : has
    RECON_JOBS ||--o{ RECON_BREAKS : identifies
    USERS ||--o{ AUDIT_LOG : creates

    COUNTERPARTIES {
        bigint id PK
        string name
        string code UK
        string region
        string contact_email
        timestamp created_at
    }

    INSTRUMENTS {
        bigint id PK
        string symbol UK
        string name
        string asset_class
        jsonb metadata "TICKET-ADV009"
        timestamp created_at
    }

    USERS {
        bigint id PK
        string email UK
        string password_hash
        string role
        boolean active
        timestamp created_at
    }

    TRADES {
        bigint id PK
        string trade_ref UK
        date trade_date "PARTITION KEY (ADV007)"
        bigint counterparty_id FK
        bigint instrument_id FK
        decimal quantity
        decimal price
        decimal notional
        string status
        timestamp executed_at
    }

    SETTLEMENTS {
        bigint id PK
        bigint trade_id FK
        date settlement_date
        decimal settlement_amount
        string status
        timestamp created_at
    }

    RECON_JOBS {
        bigint id PK
        bigint initiated_by FK
        date recon_date
        string status
        int total_trades
        int matched_count
        int break_count
        timestamp started_at
        timestamp completed_at
    }

    RECON_BREAKS {
        bigint id PK
        bigint trade_id FK
        bigint recon_job_id FK
        string break_type
        string description
        decimal difference_amount
        string status
        timestamp resolved_at
    }

    AUDIT_LOG {
        bigint id PK
        bigint user_id "NO FK - audit outlives users"
        string action
        string entity_type
        bigint entity_id
        jsonb details
        timestamp changed_at
        string changed_by
    }