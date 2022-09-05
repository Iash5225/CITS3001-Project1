# Informed Search Algorithms

## Uninformed vs Informed

- Uninformed
  - Selects nodes for expansion on the basis of distance/cost from the start state
  - Uses only information contained in the graph
  - No indication of distance to go
- Informed
  - Selects nodes for expansion on the basis of some estimate of distance to the goal state
  - Requires addition information
    - heuristic rules
    - evaluation function
  - Selects "best" node i.e. most promising

## Greedy Search

- Always selects the unisited node with the smallest estimated distance to goal.
- Heuristic h(n) (evaluation function) is the estimate of the cost of getting from (n) to the goal.

## A* Search

- Uses estimate of total path-cost as heuristic:
  - f(n) = g(n)+h(n)
    - g(n) = actual cost from start to (n)
    - h(n) = estimated cost from (n) to goal
    - f(n) = estimated total cost from start to goal via (n).

## A* Optimality

- A* Search is complete + optimal under 2 conditions:
  - Heuristic must be admissible
  - The costs along a given path must be monotonic
- A heuristic (h) is admissible: iff h(n) <= h<sup>*</sup>(n) for all (n)
  - h<sup>*</sup>(n) is the actual path-cost from (n) to the goal
  - i.e. (h) must never over-estimate the cost
- A heuristic (h) is monotonic : iff h(n) <= c(n,a,n')+h(n') for all (n,a,n')
  - n' = successor to (n) by action (a)
  - n to the goal 'directly' should be no more than (n) to the goal via any successor (n')
- Pathmax modification: f(n')= max(g(n'))+h(n'),f(n))

## Assessing Heuristics

- Straight-line distance for Travel + is admissible

## Heuristic Quality

- The quality of a heuristic is expressed as its effective branching factor (b*)
- effective branching factor (b*) = branching factor of a "perfect tree"
- A good heuristic would have (b*) close to 1

## Heuristic Dominance

- h<sub>2</sub> dominates h<sub>1</sub>, iff they are both addmisible, and h<sub>2</sub>(n) >= h<sub>1</sub>(n), for all nodes (n)
- The dominating heuristic will usual visit, fewer nodes.
- Always favour dominmating heuristic
- If neither dominates, use both:
  - h(n) = max(h<sub>1</sub>(n),h<sub>2</sub>(n))

## Deriving Heuristics

- Given a problem (p), a relaxed version (p') of (p) is derived by reducing restrictions on operators.
- The cost of an exact solution to (p') is often a good heurstic to use for (p) (never over-estimates)
- Relax problems by eliminating one or more restrictions on operations

## Memory Bounded A*

- Limiting factor of A* is space availibility
- Iterative Deepening A* (IDA*) imposes an f-cost cut-off
- IDA performs depth-limited search on all nodes (n) such that f(n) <= k, increasing (k) as search continues
- IDA* Problems:
  - How much to increase (k)
  - Doesn't use all of the space available
  - The only information communicated between iterations is the f-cost limit

## Simplified Memory-Bounded A*

- Expands the most promising node until memory is full
- Then drops the least promising node to continue search
- When a node (x) is dropped, the f-cost of (x) is backed-up in (x)'s parent node
- If at some point later in the search, all other nodes have higher estimates than the dropped sub-tree, it is re-generated

## Summary

|   Algorithm   |          Complete           | Optimal |                             Time                              |      Space       |
|:-------------:|:---------------------------:|:-------:|:-------------------------------------------------------------:|:----------------:|
| Greedy Search |         Not always          |   No    | O(b<sup>m</sup>),highly dependent on the heuristic permonance | O(b<sup>m</sup>) |
|   A* Search   |  Yes, unless x is infinite  |   Yes   |       O(x), x is the number of nodes (n) with f(n)<=f*        |       O(x)       |
|     SMA*      | Yes, if reachable in memory |   Yes   |       O(x), x is the number of nodes (n) with f(n)<=f*        |    All of it     |
