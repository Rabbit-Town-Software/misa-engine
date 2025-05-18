# Misa Engine

![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)
![Build Status](https://img.shields.io/badge/build-alpha-lightgrey.svg)
![Version](https://img.shields.io/badge/version-0.1.1--alpha-blue)
![Last Commit](https://img.shields.io/github/last-commit/rabbit-town-software/misa-engine)
![Code Size](https://img.shields.io/github/languages/code-size/rabbit-town-software/misa-engine)
![Top Language](https://img.shields.io/github/languages/top/rabbit-town-software/misa-engine)
![Contributors](https://img.shields.io/github/contributors/rabbit-town-software/misa-engine)

A lightweight, open-source Java game engine for classic turn-based, pixel-art RPGs.

---

## About Misa Engine

**Misa Engine** is a free and open-source game engine built in **Java** for developing classic-style 2D **turn-based RPGs**. Inspired by titles like early Final Fantasy games, it provides a clean and minimal structure for developers who want full control over their game’s systems without the weight of a full general-purpose engine.

Built for **pixel-perfect rendering**, tilemaps, and **modular systems**, Misa Engine helps you focus on game design instead of reinventing the low-level foundation.

---

## Current Status

- **Version:** `0.1.1-alpha`  
- **Stage:** *Physics Update (in progress)*  
- **Stability:** Early Alpha – Expect bugs and breaking changes

The engine is **playable** for basic demos and sandbox development, but key systems like **physics, combat, and UI** are still being built out.

---

## Features

- Pixel-perfect 2D rendering system  
- Modular animation and timing system  
- Tilemap support via Tiled `.tmx` parser  
- Smooth camera with zoom and boundary control  
- Entity-based event system (spawn, tile triggers, time)  
- Fixed timestep game loop  
- Basic keyboard input handling  
- Config file support and customizable boot setup  

---

## Roadmap

### ✅ **Core Systems (Complete)**  
- [x] Global time & update loop  
- [x] Camera & renderer  
- [x] Animation & events  
- [x] Map loading (.tmx)  
- [x] Basic input support  

### 🔧 **Current Update: Physics System**  
- [ ] Collision detection  
- [ ] Movement resolution  
- [ ] Physics-based map objects  

### 🔮 **Upcoming Features**

#### Lighting & NPCs  
- [ ] Static/dynamic lighting  
- [ ] Basic AI/NPC scripting  

#### Save & Inventory  
- [ ] Save/load slots  
- [ ] Item and inventory handling  

#### UI & Dialogue  
- [ ] UI overlay system  
- [ ] Branching dialogue manager  

#### Combat & Visual Effects  
- [ ] Turn-based combat engine  
- [ ] Particle system  
- [ ] Audio (music & SFX)  

#### Scenes & Data  
- [ ] Scene manager & transitions  
- [ ] Cutscene scripting  
- [ ] Database manager for game state  

#### Optimization  
- [ ] Object pooling  
- [ ] Render culling  
- [ ] Debug logging & crash capture  

---

## Getting Started

### Manual `.jar` Installation

- Download the latest release `.jar` from [Releases](https://github.com/rabbit-town-software/misa-engine/releases).
- Add it as a library in your Java project.
- Start building with core classes like `GameLoop`, `Renderer`, and `Camera`.

### Gradle Installation (optional)

```gradle
repositories {
    mavenCentral()
    maven {
        url = uri("https://maven.pkg.github.com/case-presley/misa-engine")
        credentials {
            username = System.getenv("GITHUB_USERNAME")
            password = System.getenv("GITHUB_TOKEN")
        }
    }
}

dependencies {
    implementation 'com.casepresley.misaengine:misaengine:0.1.1-alpha'
}
```

---

## Privacy & License

### Privacy Policy

Misa Engine does **not** collect, track, or transmit any personal data.  
There are no analytics, telemetry, or background network operations.  
We believe software should work without surveillance.

### License

Misa Engine is licensed under the **GNU General Public License v3.0 (GPL-3.0)**.

-✔️ Commercial use allowed  
- ✔️ Modifications & redistribution permitted  
- ❗ Derivative works must also be GPL-3.0 licensed  

---

## Want to Contribute?

We’re looking for:

- Developers (Java)  
- Testers (Engine & example games)  
- Feedback from indie devs building RPGs

Submit an issue, fork the repo, or just reach out.

---

## Contact

- Email: [support@rabbittownsoftware.com](mailto:support@rabbittownsoftware.com)  
- GitHub Issues: For bugs, suggestions, or feature requests

---

![Rabbit Town Software Logo](https://raw.githubusercontent.com/rabbit-town-software/misa-engine/master/assets/rabbittownlogo.jpg)


<p align="center">
  <b>Rabbit Town Software</b> — Open-source. No compromise.
</p>

