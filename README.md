# **Misa Engine**  
*A lightweight, open-source 2D RPG engine in Java*  

---

## **Overview**  
The **Misa Engine** is a completely **free and open-source** game engine built in **Java**, designed specifically for **turn-based, pixel-art RPGs**.  
Inspired by **classic Final Fantasy titles**, it provides all the core systems needed to create rich RPG experiences **without the overhead of a full general-purpose game engine**.

The goal of the Misa Engine is to make **RPG development faster and easier**, while remaining **lightweight and flexible**, allowing developers to focus more on **game design** instead of building low-level systems from scratch.

Whether you want to build a **prototype** or a **full game**, Misa Engine aims to get you started quickly.

---

## **Current Status**  
- **Version:** `v0.1.1-alpha`  
- **Development Stage:** Physics Update 
- **API Stability:** Early Alpha (breaking changes possible)

The engine is **usable** for very basic testing and demo purposes, but **many advanced systems are still in development**.

---

## **Features Implemented**

- **Global Time System** – In-game clock and event timing
- **Camera System** – Smooth following and boundary clamping
- **Tiled2Misa Map Loader** – Parse and render `.tmx` maps (CSV & Base64)
- **Event System** – Entity spawn/destruction, tile enter/exit, time change
- **Animation System** – Sprite animation with optional looping
- **Game Loop Architecture** – Fixed UPS/FPS handling
- **Configuration System** – Editable config file generation
- **Rendering System** – Pixel-perfect tile and sprite rendering
- **Input Handling** – Simple keyboard input tracking

---

## **How to Use**

### 1. Install Misa Engine (Recommended - Manual .jar Download)

- Download the latest `.jar` file (`misa-engine-0.1.1-alpha.jar`) from the [Releases](https://github.com/case-presley/misa-engine/releases) page.
- In your Java project (IntelliJ, Eclipse, etc.):
  - Add the jar as a **library** (in IntelliJ: `File > Project Structure > Libraries > +`).
  - Or manually include it in your project's **classpath**.

You can now import and use Misa Engine classes like `GameLoop`, `GameWindow`, `Renderer`, etc.

---

### 2. (Optional) Install via Gradle

If you prefer to add it via **Gradle** instead of downloading manually:

In your `build.gradle`:

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

Make sure you have a GitHub personal access token set as an environment variable.

---

### 2. Basic Setup Example

Create a simple main class:

```java
import misa.core.*;

public class MyGame
{
    public static void main(String[] args)
    {
        TimeSystem timeSystem = new TimeSystem(1.0f, new EventManager());
        Camera camera = new Camera(0f, 0f, 20, 15);
        Renderer renderer = new Renderer(camera, null, 32);
        GameLoop gameLoop = new GameLoop(timeSystem, renderer);

        GameWindow.create("My RPG Game", 800, 600, false, false, gameLoop);
    }
}
```

This will launch an empty game window!  
You can then add:
- Your map loading
- Your player object
- Basic event handling
- Camera following
- And more!

---

## **Development Timeline (Subject to Change)**

### Major Update 1: Core Mechanics (Current Release)
- [x] Time System
- [x] Event System
- [x] Animation System
- [x] Camera System
- [x] Game Loop
- [x] Input Handling

### Major Update 2: Physics (Next!)
- [ ] Physics Engine (Movement, Collisions)

### **Major Update 2: Physics**  
- [ ] **Physics System** - In Depth Physics System (Collision, Movement, etc..)

### **Major Update 3: Lighting & NPCs**  
- [ ] Lighting System (Dynamic & static lighting)  
- [ ] NPC System (Basic AI behavior)  

### **Major Update 4: Save & Inventory**  
- [ ] Save/Load System  
- [ ] Inventory System  

### **Major Update 5: UI & Dialogue**  
- [ ] UI System (HUD, menus, overlays)  
- [ ] Dialogue System (NPC interaction & branching text)  

### **Major Update 6: Combat & Effects**  
- [ ] Combat System (Turn-based mechanics)  
- [ ] Particle System (Effects & weather)  
- [ ] Audio System (Music & SFX)  

### **Major Update 7: Scenes & Data**  
- [ ] Database System (Storing game data)  
- [ ] Scene Manager (Transitions between game areas)  
- [ ] Cutscene Manager (Scripted sequences)  

### **Major Update 8: Optimization & Stability**  
- [ ] Culling (Render optimization)  
- [ ] Object Pooling (Efficient memory use)  
- [ ] Performance Monitoring (FPS tracking)  
- [ ] Extended Logging (Debugging)  
- [ ] Crash Reporting (Error handling)  

---

## **Want to Contribute?**

I'm looking for:
- **Developers** – Help expand engine features
- **Testers** – Find bugs, suggest improvements

Feel free to submit **Issues**, **Pull Requests**, or just reach out!

---

## **Contact & Contribution**

- **GitHub Issues** — Bug reports and feature suggestions
- **Pull Requests** — Code contributions welcome
- **Email:** [casepresley.dev@tuta.com](mailto:casepresley.dev@tuta.com)

---

## **Final Notes**

Misa Engine is still in **early alpha**. Everything is subject to change. Expect breaking changes in future updates.
If you’re interested in **following development**, **testing** it out, or even **helping improve it**, feel free to jump in.

**⭐ Star the repo if you like it! ⭐**
