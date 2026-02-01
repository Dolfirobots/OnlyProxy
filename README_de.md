# 🌐 OnlyProxy

> [!NOTE]
> Sprachen:  
> _**[🇩🇪 Deutsch](./README_de.md)**_  
> **[🇬🇧 English](./README.md)**
  
[![GitHub Dowloads](https://shields.io/github/downloads/Dolfirobots/OnlyProxy/total?label=Downloads&logoColor=Green&color=Blue&style=flat)](https://github.com/Dolfirobots/OnlyProxy/releases)
[![GitHub Release](https://img.shields.io/github/v/release/Dolfirobots/OnlyProxy?color=Green)](https://github.com/Dolfirobots/OnlyProxy/releases "OnlyProxy Releases")
[![Discord](https://img.shields.io/discord/1436724374622441556.svg?logo=discord&logoColor=Green&color=Blue&labelColor=Green&label=Discord)](https://discord.gg/m4hhckJe4v "Discord")

**OnlyProxy** ist ein einfaches Minecraft-Plugin für **Spigot/Paper (1.20 – 1.21.10)**.  
Es schützt deinen Server, indem es nur Spielern erlaubt zu joinen, die über ___deinen___ Proxy joinen.  

---

## ✨ Features
- ✅ Unterstützt **Minecraft 1.20 – 1.21.10**
- ✅ Blockiert direkte Verbindungen ohne Proxy und von andern Proxies die nicht von dir kommen
- ✅ **Log-System** → Alle Joins werden protokolliert
- ✅ Leicht zu konfigurieren
- ✅ Ports forwarding

---

## 📥 Installation
1. Lade dir die neueste Version von [GitHub](https://github.com/Dolfirobots/OnlyProxy/releases "OnlyProxy") oder [Spigot](https://www.spigotmc.org/resources/onlyproxy.128485/ "OnlyProxy") herunter.
2. Lege die `.jar`-Datei in den Ordner:
```

/plugins

````
3. Starte deinen Server neu.

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
  - "12.123.12.12:25468"

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

> [!WARNING]
> Ein Only Proxy Plugin bedeutet,  
> das es die IP abgleicht die von dem Proxy  
> gesendet wurde.  
> Aber __ALLES__ was von dem Proxy oder dem Server  
> gesendet wird, kann manipuliert werden!  
> Deswegen werde ich in Zukunft keine  
> weitere große Updates mehr Hochladen!  
> Kleine Sachen wie Kompatibilität oder bug fixes werden ich noch weiter machen aber nur auf reports.

---

## 📜 Lizenz

Dieses Projekt ist unter der [MIT License](./LICENSE) lizenziert.

---

## 🤝 Mitwirken

* Fehler gefunden? → [Issue erstellen](https://github.com/Dolfirobots/OnlyProxy/issues)
* Ideen oder Verbesserungsvorschläge? → Join mein [Discord](https://discord.gg/m4hhckJe4v "Discord")

---

## 📋 Stats

[![](https://bstats.org/signatures/bukkit/OnlyProxy.svg)](https://bstats.org/plugin/bukkit/OnlyProxy)
