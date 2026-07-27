# ADR-0003: Use GIN Index for JSONB Queries

## Status
Accepted

## Context
Queries filtering instruments by metadata attributes (e.g., sector, rating, tags) need to be fast. Standard B-tree indexes don't work well with JSONB containment queries.

## Decision
Use a GIN index with jsonb_path_ops operator class on the metadata column.

## Consequences
- Positive: Fast containment queries (@> operator)
- Positive: Supports partial matches and path queries
- Positive: jsonb_path_ops is more compact than default GIN
- Negative: Larger storage footprint than B-tree
- Negative: Index updates are slower on INSERT/UPDATE