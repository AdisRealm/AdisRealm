# Adi's Realm plugin
This is a Bukkit plugin designed for the Adi's Realm private SMP.
This is not intended for external use and it is not guarateed to work.

## Features
* Nicknames
* AFK management
* Name colors
* Messaging

## Commands
* `/nick <add | remove | list>`
* `/afk [reason]`
* `/settings`
* `/msg <player> <message>` & `/r <message>`

## Building from source
This plugin uses maven to automate the building process.
To build the plugin, execute the following command from 
the base folder of the project:
```
mvn clean install
```
The shaded plugin will output to `target/AdisRealm.jar` 
and will be ready to use.