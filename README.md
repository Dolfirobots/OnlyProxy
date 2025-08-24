# 🌐 OnlyProxy

[![Spigot](https://img.shields.io/badge/Spigot-1.20--1.21.8-orange)](https://www.spigotmc.org/)
[![License](https://img.shields.io/github/license/Dolfirobots/OnlyProxy)](./LICENSE)
[![GitHub Release](https://img.shields.io/github/v/release/Dolfirobots/OnlyProxy)](https://github.com/Dolfirobots/OnlyProxy/releases)

**OnlyProxy** ist ein einfaches Minecraft-Plugin für **Spigot/Paper (1.20 – 1.21.8)**.  
Es schützt deinen Server, indem es nur Spielern erlaubt zu joinen, die über deinen Proxy joinen.  
Direkte Verbindungen auf die ungesicherten Unter-Servern (z. B. von Hackern) werden blockiert. 🚫

---

## ✨ Features
- ✅ Unterstützt **Minecraft 1.20 – 1.21.8**
- ✅ Blockiert direkte Verbindungen ohne Proxy
- ✅ Schutz vor **Hackern**
- ✅ **Log-System** → Alle Joins werden protokolliert
- ✅ Leicht zu konfigurieren

---

## 📥 Installation
1. Lade dir die neueste Version von [Releases](https://github.com/Dolfirobots/OnlyProxy/releases) herunter.
2. Lege die `.jar`-Datei in den Ordner:
```

/plugins

````
3. Starte deinen Server neu. (Oder mach `/rl confirm`, aber manche Plugins unterstüzen das nicht)

---

## ⚙️ Konfiguration
Nach dem ersten Start wird eine `OnlyProxy/config.yml` im `/plugin/`-Ordner erstellt.  
Dort kannst du deine Proxy-IP(s) eintragen:

```yaml
# OnlyProxy Konfiguration
allowed-proxies:
- "123.45.67.89"
- "mein.proxy.de"
kick-message: "&cDu kannst nur über den Proxy joinen!"

# Logging aktivieren/deaktivieren
logging: true
````

---

## 📑 Logs

Das Plugin erstellt automatisch Log-Einträge, wenn jemand versucht ohne Proxy oder auch generell zu joinen.
Die Logs findest du hier:

```
/plugins/OnlyProxy/logs/
```

### Beispiel-Log

```
!!HERE COMES SOMTHING UP LATER!!
```

---

## 🛠️ Build (für Entwickler)

Falls du das Plugin selbst kompilieren möchtest:

```bash
git clone https://github.com/Dolfirobots/OnlyProxy.git
cd OnlyProxy
mvn clean package
```

---

## 📜 Lizenz

Dieses Projekt ist unter der [MIT License](./LICENSE) lizenziert.

---

## 🤝 Mitwirken

* Fehler gefunden? → [Issue erstellen](https://github.com/Dolfirobots/OnlyProxy/issues)
* Ideen oder Verbesserungsvorschläge? → Join mein Discord

---

💡 **Tipp:** Nutze OnlyProxy zusammen mit einer **Firewall und [Velocity](https://papermc.io/downloads/velocity)**, um den maximalen Schutz für deinen Server zu erreichen.