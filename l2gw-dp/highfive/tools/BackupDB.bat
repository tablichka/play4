@echo off
TITLE -==[ Automatic updater for L2J Fortress - Backup Module ]==-
REM #### Made by Krab (RMT Team) ####
REM ########################################

set OnlyGeneraltables=False
set PathToBackupDir=D:\L2J_Server\backup

REM ### MYSQL 5.0 ###
set mysqlBinPath=C:\Program Files\MySQL\MySQL Server 5.0\bin
set mysqldumpPath="%mysqlBinPath%\mysqldump"

set user=root
set pass=
set DBname=l2jdb
set DBHost=localhost
set Generaltables=accounts gameservers banned_ips loginserv_log character_friends character_hennas character_macroses character_quests character_recipebook character_shortcuts character_skills character_skills_save character_subclasses characters character_variables clanhall_bids clanhall_data clan_data clanhall_decorations_bids ally_data clan_wars items pets server_variables seven_signs seven_signs_festival siege_clans killcount dropcount craftcount game_log petitions seven_signs_status global_tasks

if not exist %PathToBackupDir% mkdir %PathToBackupDir%

set ctime=%TIME:~0,2%
if "%ctime:~0,1%" == " " (
set ctime=0%ctime:~1,1%
)
set ctime=%ctime%'%TIME:~3,2%'%TIME:~6,2%

echo.
echo Making backup into %DATE%-%ctime%_backup.sql
echo.

if /I %OnlyGeneraltables%==True goto General_backup

:Full_backup
%mysqldumpPath%  --compact --add-drop-table -h %DBHost% -u %user% --password=%pass% %DBname% > %PathToBackupDir%/%DATE%-%ctime%_backup.sql
goto archivating

:General_backup
%mysqldumpPath%  --compact --add-drop-table -h %DBHost% -u %user% --password=%pass% %DBname% %Generaltables% > %PathToBackupDir%/%DATE%-%ctime%_backup.sql

:archivating
start /WAIT winrar.exe a -m2 -df -ibck %PathToBackupDir%\%DATE%-%ctime%_backup.rar %PathToBackupDir%\%DATE%-%ctime%_backup.sql
exit