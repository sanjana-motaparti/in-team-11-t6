# ADR-0001: Partition Trades Table by Trade Date

## Status
Accepted

## Context
ReconX will process approximately 50,000 trades per day, accumulating to 91 million rows over 5 years. EOD reconciliation queries and materialized view refreshes must scan only relevant date ranges.

## Decision
Partition the `trades` table by `trade_date` using PostgreSQL RANGE partitioning, with monthly child partitions.

## Consequences
- Positive: Query performance improves through partition pruning
- Positive: Easier archival of old data via partition detachment
- Positive: Concurrent refresh of materialized views is feasible
- Negative: Requires careful maintenance of partition creation
- Negative: Cross-partition queries become slightly more complex