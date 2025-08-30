# 🌐 OnlyProxy

> [!NOTE]
> Sprachen:  
> __**[Deutsch](./README_de.md)**__  
> **[English](./README.md)**

[![Spigot](https://img.shields.io/badge/Spigot-1.20--1.21.8-orange)](https://www.spigotmc.org/)
[![License](https://img.shields.io/github/license/Dolfirobots/OnlyProxy)](./LICENSE)
[![GitHub Release](https://img.shields.io/github/v/release/Dolfirobots/OnlyProxy)](https://github.com/Dolfirobots/OnlyProxy/releases "OnlyProxy Releases")
[![Discord](https://img.shields.io/discord/1079052573845241877.svg?logo=discord&logoColor=fff&color=7389D8&labelColor=6A7EC2)](https://discord.gg/dxZTGpPbkd "Discord")

**OnlyProxy** ist ein einfaches Minecraft-Plugin für **Spigot/Paper (1.20 – 1.21.8)**.  
Es schützt deinen Server, indem es nur Spielern erlaubt zu joinen, die über deinen Proxy joinen.  
Direkte Verbindungen auf die ungesicherten Unter-Servern (z. B. von Hackern) werden blockiert. 🚫

---

## ✨ Features
- ✅ Unterstützt **Minecraft 1.20 – 1.21.8**
- ✅ Blockiert direkte Verbindungen ohne Proxy und von andern Proxies die nicht von dir kommen
- ✅ Schutz vor **Hackern** (WICHTIG: Nur vor Hackern die versuchen auf den Unter-Server zu joinen)
- ✅ **Log-System** → Alle Joins werden protokolliert
- ✅ Leicht zu konfigurieren
- ✅ Ports forwarding

---

## 📥 Installation
1. Lade dir die neueste Version von [Releases](https://github.com/Dolfirobots/OnlyProxy/releases) herunter.
2. Lege die `.jar`-Datei in den Ordner:
```

/plugins

````
3. Starte deinen Server neu. (Oder mach `/rl confirm`, aber manche Plugins unterstützen das nicht und ab 1.21.x gibt es diese Funktion nicht mehr)

---

## ⚙️ Konfiguration
Nach dem ersten Start wird eine `OnlyProxy/config.yml` im `/plugin/`-Ordner erstellt.  
Dort kannst du deine Proxy-IP(s) eintragen:

```yaml
# Prevent Bypass Proxy
# Plugin by Dolfirobots
# MIT licence

# Permissions:
# onlyproxy.commands : This permission is used to allow the /onlyproxy [reload/version]

# Your allowed proxy IPs
# If you leave this empty, then are all connections allowed
proxyIPs:
  - "127.0.0.1"
  - "0.0.0.0"
  - "play.yourserver.net"
  - "12.123.12.12:25468" # WARNING: Ports forwarding is not recommended because it can be manipulated via client!

# The Prefix of The Plugin
prefix: "&7[&eOnlyProxy&7] "

# The kick message
# You can use the Proxy IPs above like this: %proxy_1% -> 127.0.0.1
# When you only have 2 Proxy IPs set: %proxy_3% -> %proxy_3%
# You can access the prefix like that: %prefix% -> &7[&eOnlyProxy&7]
kickMessage:
  - "§r"
  - "%prefix%"
  - "§r"
  - "§r"
  - "§cYou must join this Server over %proxy_1% or %proxy_2%!"
  - "§7You can change that message in" # Change that here
  - "§7plugins/OnlyProxy/config.yml"
  - "§r"
  - "§r"

log:
  # Should log joins?
  # "ALL" -> All player connections to this server
  # "OTHER" -> Only all attempts to join this Server with a not allowed Proxy
  # "OFF" -> Nothing will be logged (Not recommended)
  # If you don't select any of the options, it will automatically be counted as "OFF"
  logging: "ALL"

  ### Log details ###
  # Should show IP from player?
  logIPs: true
  # Should show username from player? (Recommended)
  logPlayerName: true
  # Should show joined Proxy IP? (Recommended)
  logProxyIPs: true

console:
  # Should log joins in console?
  # "ALL" -> All player connections to this server
  # "OTHER" -> Only all attempts to join this Server with a not allowed Proxy
  # "OFF" -> Nothing will be logged (Not recommended)
  # If you don't select any of the options, it will automatically be counted as "ALL"
  logging: "ALL"

  ### Console log details ###
  # Should show IP from player?
  logIPs: true
  # Should show username from player? (Recommended)
  logPlayerName: true
  # Should show joined Proxy IP? (Recommended)
  logProxyIPs: true
```
---
## 🛡️ Permissions
Es gibt eine Permission für die Commands wie `/onlyproxy reload|version`:
```
onlyproxy.commands
```

---

## 📑 Logs

Das Plugin erstellt automatisch Log-Einträge, wenn jemand versucht ohne Proxy oder auch generell zu joinen.
Die Logs findest du hier:

```
/plugins/OnlyProxy/logs/
```

### Beispiel-Log
> [!NOTE]
> Die IPs sind random generiert im Beispiel
```
log_2025-08-25.log:

[12:03:12] [PASSED] Playername: TestPlayer | Player IP: 12.122.12.12 | Proxy IP: 132.13.12.21
[15:09:32] [BLOCKED] Playername: HackerPlayer | Player IP: 32.223.23.23 | Proxy IP: 300.30.300.30 
```

---

## 📜 Lizenz

Dieses Projekt ist unter der [MIT License](./LICENSE) lizenziert.

---

## 🤝 Mitwirken

* Fehler gefunden? → [Issue erstellen](https://github.com/Dolfirobots/OnlyProxy/issues)
* Ideen oder Verbesserungsvorschläge? → Join mein [Discord](https://discord.gg/dxZTGpPbkd "Discord")

---

💡 **Tipp:** Nutze OnlyProxy zusammen mit einer **Firewall und [Velocity](https://papermc.io/downloads/velocity)**, um den maximalen Schutz für deinen Server zu erreichen.
