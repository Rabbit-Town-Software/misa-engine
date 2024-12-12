---
layout: page
title: Documentation
---

# Documentation

Welcome to the documentation for **Misa Engine**. This guide will help you get started with the engine and explain how to use its core features.

## Getting Started

1. **Download the Engine**: You can download the Misa Engine from the [downloads page](#).
2. **Installation**: 
   - Extract the files to a directory.
   - Open the terminal or command prompt, navigate to the engine folder, and run the engine executable.

## Core Concepts

- **Scenes**: The Misa Engine works by creating scenes, which are made up of game objects.
- **Game Objects**: Entities in the game that you can manipulate, such as characters, items, and enemies.
- **Events**: Use Lua scripts to define events that trigger actions like collisions, time changes, etc.

## Example Code

```lua
-- Basic event example
function onTileEnter()
    print("You've entered a new tile!")
end
