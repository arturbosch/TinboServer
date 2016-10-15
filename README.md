# TinboServer

Initial prototype of a storage server for TiNBo.

## Build

- gradle shadow
- java -jar build/libs/tinboserver-{Version}-all.jar

## Endpoints

- '/'       - Displays home site with note/task entries loaded from tinbo
- /backup   - Accepts multipart post requests (Backup-Zips - backup remote command from tinbo)

## Changelog
### 1.0.M1

- first prototype, allows to backup tinbo data
