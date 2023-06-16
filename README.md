# Temporal Patterns Workshop

[![Gitmoji](https://img.shields.io/badge/gitmoji-%20%F0%9F%98%9C%20%F0%9F%98%8D-FFDD67.svg)](https://gitmoji.dev)

This is my implementation for the
[Temporal Patterns hands-on lab](https://github.com/H-Ahmadi/DDDEU_2023_Temporal_Patterns) that was animated by Hadi
Ahmadi at [DDD Europe 2023](https://2023.dddeurope.com/program/temporal-patterns/).

## Steps

### Temporal property

The address of a customer can change over time, and we want to keep track of these changes.

### Temporal object (preparation)

A supply plan defines demands for days (e.g. every mondays and fridays) within an active period. We need to be able to
* resolve total demands for a given time period
* resolve the list of daily demands for a given time period

### Temporal object

The supply plan can be modified, and we want the changes to be taken into account for the operations defined in the previous step.