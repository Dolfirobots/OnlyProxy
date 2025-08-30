# 🌐 OnlyProxy

> [!NOTE]
> Languages:  
> **[Deutsch](./README_de.md)**  
> _**[English](./README.md)**_

[![Spigot](https://img.shields.io/badge/Spigot-1.20--1.21.8-orange)](https://www.spigotmc.org/)
[![License](https://img.shields.io/github/license/Dolfirobots/OnlyProxy)](./LICENSE)
[![GitHub Release](https://img.shields.io/github/v/release/Dolfirobots/OnlyProxy)](https://github.com/Dolfirobots/OnlyProxy/releases "OnlyProxy Releases")
[![Discord](https://img.shields.io/discord/1079052573845241877.svg?logo=discord&logoColor=fff&color=7389D8&labelColor=6A7EC2)](https://discord.gg/dxZTGpPbkd "Discord")

**OnlyProxy** is a simple Minecraft plugin for **Spigot/Paper (1.20 – 1.21.8)**.  
It protects your server by allowing only players who connect through your proxy.  
Direct connections to unsecured backend servers (e.g., by hackers) are blocked. 🚫

---

## ✨ Features
- ✅ Supports **Minecraft 1.21 – 1.21.8**
- ✅ Blocks direct connections without a proxy and from proxies that are not yours
- ✅ Protection against **hackers** (IMPORTANT: Only those trying to access backend servers directly)
- ✅ **Log system** → All joins are logged
- ✅ Easy to configure
- ✅ Supports port forwarding

---

## 📥 Installation
1. Download the latest version from the [Releases](https://github.com/Dolfirobots/OnlyProxy/releases) page.
2. Place the `.jar` file into the folder:

```
/plugins
```

3. Restart your server. (Or use `/rl confirm`, but note that some plugins don’t support this and it no longer exists in 1.21.x)

---

## ⚙️ Configuration
After the first startup, a file `OnlyProxy/config.yml` will be created in the `/plugins/` folder.  
You can enter your allowed proxy IP(s) there:

```yaml
# Prevent Bypass Proxy
# Plugin by Dolfirobots
# MIT licence

# Permissions:
# onlyproxy.commands : This permission is used to allow the /onlyproxy [reload/version]

# Your allowed proxy IPs
# If you leave this empty, then all connections are allowed
proxyIPs:
  - "127.0.0.1"
  - "0.0.0.0"
  - "play.yourserver.net"
  - "12.123.12.12:25468" # WARNING: Port forwarding is not recommended because it can be manipulated via client!

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

There is one permission for commands like `/onlyproxy reload|version`:

```
onlyproxy.commands
```

---

## 📑 Logs

The plugin automatically creates log entries when someone tries to join without a proxy (or in general).  
You can find the logs here:

```
/plugins/OnlyProxy/logs/
```

### Example log
> [!NOTE]  
> The IPs in this example are randomly generated

```
log_2025-08-25.log:

[12:03:12] [PASSED] Playername: TestPlayer | Player IP: 12.122.12.12 | Proxy IP: 132.13.12.21  
[15:09:32] [BLOCKED] Playername: HackerPlayer | Player IP: 32.223.23.23 | Proxy IP: 300.30.300.30
```
---

## 📜 License

This project is licensed under the [MIT License](./LICENSE).

---

## 🤝 Contributing

* Found a bug? → [Create an issue](https://github.com/Dolfirobots/OnlyProxy/issues)  
* Have ideas or suggestions? → Join my [Discord](https://discord.gg/dxZTGpPbkd "Discord")

---

💡 **Tip:** Use OnlyProxy together with a **firewall and [Velocity](https://papermc.io/downloads/velocity)** to ensure maximum protection for your server.
