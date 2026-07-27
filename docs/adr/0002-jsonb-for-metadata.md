# ADR-0002: Use JSONB for Instrument Metadata

## Status
Accepted

## Context
Instrument attributes (sector, issuer, rating, tags) vary significantly across asset classes and change frequently. Adding dedicated columns would require constant schema migrations.

## Decision
Store flexible instrument attributes as a JSONB column with a GIN index for fast containment queries.

## Consequences
- Positive: Schema changes don't require migrations
- Positive: Query flexibility with JSON operators
- Positive: GIN index supports efficient filtering
- Negative: JSONB validation logic must move to application layer
- Negative: More storage overhead than dedicated columns