# Agents + Introduction to AI

## What is an Agent?

- An agent:
  - Perceives its environment through sensors
  - Acts on its environment through effectors
    - e.g. Sensors(software) = Keyboard,mouse,files
    - e.g. Effectors (software) = Screen,printer,files
    - e.g. Percepts (software) = Light,sound,solidity

## Rational Agents

- Definition: Tries to "do the right thing" with respect to a set of goals or utilities
- "the right thing" - can be specified by a performance measure 
  - defining a numerical value for any environment history
- A rational action is whatever aaction maxisise the expected value of the performance measure
  - Given the current state of the environement and the percept sequence to date
- An agents behaviour is specified by an agent function mapping percept sequences to actions
  - Store knowledge or rules that help it to understand and to select actions

### Simple Reflex Agents

- Choose an action using condition-action rules(if ... then ...)
- No history is stored

### Model-based Reflex Agents

- Stores memory of the past
- Understanding of the effects of actions
- Both of these require internal state
  - e.g. Person signal to bus, because the person knows the bus will stop, then will proceed to change lanes

### Goal-based Agents

- Understanding the effects of actions in relation to the goal
- Planning is fundamental

### Utility-based Agents

- Utility defined as a utility to be maximised
  - e.g. optimisation problems
- Agents tries to maximise expected utility