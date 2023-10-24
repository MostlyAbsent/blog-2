---
title: 'Turso'
date: '2023-10-24'
tags: ['technology']
draft: false
summary: 'A first impressions of Turso.'
---
# Turso first look

An edge database for the 90%. The Turso offerings are well suited to handle workloads from the tiny to the very-large; basically until you're big enough to need a fully custom solution, Turso should do the trick. Based on SQLite and extended to edge computing the time to market for a dev team should be minimal.

## Edge 

Like all edge computing it pushes the data and compute closer to the end user to minimise latency.

Turso sends replicas to their configured regions to allow maximum access speed.

## libSQL

libSQL is a fully Open Source extension of SQLite targeting "modern applications." Adding replication to address web-scale usage, multitenancy for per-user data isolation.

## Multitenancy

Using the libSQL extensions optimised for per-user data isolation and auto-routing each user can experience a local optimised database solution - which can be easily geo-managed for compliance needs.

# Conclusion

There's not much more to say until I've actually used it, but it's interesting enough to be the front-runner for my next project.
