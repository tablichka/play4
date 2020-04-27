@echo off
TITLE -==[ Automatic updater for L2J Fortress - Main Module ]==-
REM #### Made by Krab (RMT Team) ####
REM ######################################## :Option
REM ��������� ���������� ��� ���? False = ���, True = ��

set LoginServer=False
set GameServer=False

set SQLdir=False
set Updatebd=False
set BackupBeforeUpdatebd=False

set MyAddons=True

set doANT=False
set doANTifNOl2j-server.zip=False

set PathToL2JFortress=D:\L2J_Server
set PathToLoginServer=D:\L2J_Server\login
set PathToGameServer=D:\L2J_Server\gameserver
set PathToSQLdir=D:\L2J_Server\sql
set PathTol2j-server.zip=D:\L2J_Projects\L2J_SVN\L2J_Fortress\build
set PathToL2J_Fortress_Compiler.bat=D:\L2J_Server
set PathToMy_Addons.bat=D:\L2J_Server
set PathToBackupDB.bat=D:\L2J_Server

REM ### MYSQL 5.0 ###
set mysqlBinPath=C:\Program Files\MySQL\MySQL Server 5.0\bin
set user=root
set pass=
set DBname=l2jdb
set DBHost=localhost
set mysqlPath="%mysqlBinPath%\mysql"

REM ########################################
echo.-==[ Automatic updater for L2J Fortress - Main Module ]==-
echo.
echo.		You have chosen to update:
echo.Update LoginServer	= %LoginServer%
echo.Update GameServer	= %GameServer%
echo.Update SQL folder	= %SQLdir%
echo.Update SQL Database	= %Updatebd%
echo.Backup Before Update DB	= %BackupBeforeUpdatebd%
echo.Install My Addons	= %MyAddons%
echo.
echo.		Your paths:
echo.Patch to Main dir	= %PathToL2JFortress%
echo.Patch to LoginServer	= %PathToLoginServer%
echo.Patch to GameServer	= %PathToGameServer%
echo.Patch to SQL folder	= %PathToSQLdir%
echo.Patch to l2j-server.zip	= %PathTol2j-server.zip%
echo.Patch to BackupDB.bat	= %PathToBackupDB.bat%
echo.Patch to My_Addons.bat	= %PathToMy_Addons.bat%
echo.
echo.		Apache ANT options:
echo.Use Apache ANT to make l2j-server.zip		= %doANT%
echo.Use Apache ANT if l2j-server.zip not found	= %doANTifNOl2j-server.zip%
echo.Patch to L2J_Fortress_Compiler.bat		= %PathToL2J_Fortress_Compiler.bat%
echo.
pause

REM ######################################## :Apache ANT

if /I %doANT%==True (
echo.Using Apache ANT to make l2j-server.zip
start /WAIT /i call %PathToL2J_Fortress_Compiler.bat%\L2J_Fortress_Compiler.bat
echo.Using Apache ANT to make l2j-server.zip - Done !!!
)

if not exist %PathTol2j-server.zip%\l2j-server.zip (
echo.
echo.Error - wrong path or file not found !!!
echo.%PathTol2j-server.zip%\l2j-server.zip not found !!!
echo.
	if /I %doANTifNOl2j-server.zip%==True (
	echo.Using Apache ANT to make l2j-server.zip
	start /WAIT /i call %PathToL2J_Fortress_Compiler.bat%\L2J_Fortress_Compiler.bat
	echo.Using Apache ANT to make l2j-server.zip - Done !!!
	goto Update_Login_server
	)
goto end_error 
)

REM ######################################## :Update_Login_server
:Update_Login_server

if /I NOT %LoginServer%==True goto Update_Game_server

if not exist %PathToLoginServer%\startLoginServer.bat (
echo.
echo.Error - wrong path or file not found !!!
echo.%PathToLoginServer%\startLoginServer.bat not found !!!
echo.
goto end_error 
)

if exist %PathToLoginServer%\login_is_running.tmp (
cls
echo.
echo.Warning !!! - login server is running !!!
echo.Stop login server and run Automatic Update again !!!
echo.
goto end_error 
)

echo.
echo.Updating login server 
echo.
echo.1.Deleting login server

RD /q /s %PathToLoginServer%\log
del %PathToLoginServer%\*.jar
del %PathToLoginServer%\*.xml

echo.1.Deleting login server - Done !!!
echo.
echo.2.Copying login server files from l2j-server.zip

start /WAIT winrar x -y -xlogin\config\*.* -xlogin\*.sh -xlogin\*.bat -ibck %PathTol2j-server.zip%\l2j-server.zip login\*.* %PathToL2JFortress%

echo.2.Copying login server files from l2j-server.zip - Done !!!
echo.

if exist %PathToLoginServer%\backup\config (
echo.3.Deleting %PathToLoginServer%\backup\config dir
RD /q /s %PathToLoginServer%\backup\config
echo.3.Deleting %PathToLoginServer%\backup\config dir - Done !!!
)

echo.
echo.4.Copy new config files from l2j-server.zip to login\backup dir

start /WAIT winrar e -y -inul -ibck %PathTol2j-server.zip%\l2j-server.zip login\config\*.* gameserver\config\l2j-version.properties %PathToLoginServer%\backup\config\

echo.4.Copy new config files from l2j-server.zip to login\backup dir - Done !!!
echo.
echo.5.Rename login\backup\config files

rename %PathToLoginServer%\backup\config\console.cfg.default console.cfg
rename %PathToLoginServer%\backup\config\log.properties.default log.properties
rename %PathToLoginServer%\backup\config\loginserver.properties.default loginserver.properties
rename %PathToLoginServer%\backup\config\login_telnet.properties.default login_telnet.properties

echo.5.Rename login\backup\config files - Done !!!
echo.
echo.6.Update l2j-version.properties to login server

if exist %PathToLoginServer%\config\l2j-version.properties (
TYPE %PathToLoginServer%\config\l2j-version.properties >> %PathToLoginServer%\config\l2j-version.history
)
copy /y %PathToLoginServer%\backup\config\l2j-version.properties %PathToLoginServer%\config\l2j-version.properties

echo.6.Update l2j-version.properties to login server - Done !!!
echo.
echo.Updating login server - Done !!!
echo.

REM ######################################## :Update_Game_server
:Update_Game_server

if /I NOT %GameServer%==True goto Update_SQLdir

if not exist %PathToGameServer%\startGameServer.bat (
echo.
echo.Error - wrong path or file not found !!!
echo.%PathToGameServer%\startGameServer.bat not found !!!
echo.
goto end_error 
)

if exist %PathToGameServer%\gameserver_is_running.tmp (
cls
echo.
echo.Warning !!! - gameserver is running !!!
echo.Stop gameserver and run Automatic Update again !!!
echo.
goto end_error 
)

echo.
echo.Updating gameserver 
echo.
echo.1.Deleting gameserver

RD /q /s %PathToGameServer%\cachedir
RD /q /s %PathToGameServer%\data
RD /q /s %PathToGameServer%\log
RD /q /s %PathToGameServer%\pathnode
del %PathToGameServer%\*.jar
del %PathToGameServer%\*.f

echo.1.Deleting gameserver - Done !!!
echo.
echo.2.Copying gameserver files from l2j-server.zip

start /WAIT winrar x -y -xgameserver\config\*.* -xgameserver\*.sh -xgameserver\*.bat -ibck %PathTol2j-server.zip%\l2j-server.zip gameserver\*.* %PathToL2JFortress%

echo.2.Copying gameserver files from l2j-server.zip - Done !!!
echo.

if exist %PathToGameServer%\backup\config (
echo.3.Deleting %PathToGameServer%\backup\config dir
RD /q /s %PathToGameServer%\backup\config
echo.3.Deleting %PathToGameServer%\backup\config dir - Done !!!
)

echo.
echo.4.Copy new config files from l2j-server.zip to gameserver\backup\config  dir

start /WAIT winrar e -y -inul -ibck %PathTol2j-server.zip%\l2j-server.zip gameserver\config\*.* %PathToGameServer%\backup\config\

echo.4.Copy new config files from l2j-server.zip to gameserver\backup\config  dir - Done !!!
echo.
echo.5.Rename gameserver\backup\config files

rename %PathToGameServer%\backup\config\access.f.default access.f
rename %PathToGameServer%\backup\config\advipsystem.properties.default advipsystem.properties
rename %PathToGameServer%\backup\config\altsettings.properties.default altsettings.properties
rename %PathToGameServer%\backup\config\announcements.txt.default announcements.txt
rename %PathToGameServer%\backup\config\clanhall.properties.default clanhall.properties
rename %PathToGameServer%\backup\config\console.cfg.default console.cfg
rename %PathToGameServer%\backup\config\GMAccess.xml.default GMAccess.xml
rename %PathToGameServer%\backup\config\log.properties.default log.properties
rename %PathToGameServer%\backup\config\mats.cfg.default mats.cfg
rename %PathToGameServer%\backup\config\olympiad.properties.default olympiad.properties
rename %PathToGameServer%\backup\config\other.properties.default other.properties
rename %PathToGameServer%\backup\config\pvp.properties.default pvp.properties
rename %PathToGameServer%\backup\config\server.f.default server.f
rename %PathToGameServer%\backup\config\server.properties.default server.properties
rename %PathToGameServer%\backup\config\siege.properties.default siege.properties
rename %PathToGameServer%\backup\config\spoil.properties.default spoil.properties
rename %PathToGameServer%\backup\config\telnet.properties.default telnet.properties
rename %PathToGameServer%\backup\config\version-specific.f.default version-specific.f

echo.5.Rename gameserver\backup\config files - Done !!!
echo.
echo.6.Update l2j-version.properties to gameserver

if exist %PathToGameServer%\config\l2j-version.properties (
TYPE %PathToGameServer%\config\l2j-version.properties >> %PathToGameServer%\config\l2j-version.history
)
copy /y %PathToGameServer%\backup\config\l2j-version.properties %PathToGameServer%\config\l2j-version.properties

echo.6.Update l2j-version.properties to gameserver - Done !!!
echo.
echo.Updating gameserver - Done !!!
echo.

REM ######################################## :Update_SQLdir
:Update_SQLdir

if /I NOT %SQLdir%==True goto Backup_before_Update_DataBase

echo.
echo.Updating sql folder 
echo.

if exist %PathToSQLdir% (
echo.1.Deleting sql dir
RD /q /s %PathToSQLdir%
echo.1.Deleting sql dir - Done !!!
)

echo.
echo.2.Copy new sql files from l2j-server.zip
start /WAIT winrar x -x*.sh -x*.default -y -inul -ibck %PathTol2j-server.zip%\l2j-server.zip sql\*.* %PathToSQLdir%
echo.2.Copy new sql files from l2j-server.zip - Done !!!
echo.
echo.Updating sql folder - Done !!!
REM ######################################## :Backup_before_Update_DataBase
:Backup_before_Update_DataBase

if /I NOT %BackupBeforeUpdatebd%==True goto Update_DataBase

if not exist %PathToBackupDB.bat%\BackupDB.bat (
echo.
echo.Error - wrong path or file not found !!!
echo.PathToBackupDB.bat%\BackupDB.bat not found !!!
echo.
goto end_error
)

call %PathToBackupDB.bat%\BackupDB.bat

REM ######################################## :Update_DataBase
:Update_DataBase
if /I NOT %Updatebd%==True goto Install_My_Addons

if not exist %PathToSQLdir%\setup.bat (
echo.
echo.Error - wrong path or file not found !!!
echo.%PathToSQLdir%\setup.bat not found !!!
echo.
goto end_error
)

set ERRORLEVEL=True
cd %PathToSQLdir%
call %PathToSQLdir%\setup.bat

REM ######################################## :Install_My_Addons
:Install_My_Addons
if /I NOT %MyAddons%==True goto if_all_False

if not exist %PathToMy_Addons.bat%\My_Addons.bat (
echo.
echo.Error - wrong path or file not found !!!
echo.%PathToMy_Addons.bat%\My_Addons.bat not found !!!
echo.
goto end_error
)

echo.
echo.Installing My Addons 
start /WAIT /I call %PathToMy_Addons.bat%\My_Addons.bat
echo.Installing My Addons - Done !!!
echo.

REM ########################################
:if_all_False
if /I NOT %LoginServer%==False goto end_ok
if /I NOT %GameServer%==False goto end_ok
if /I NOT %SQLdir%==False goto end_ok
if /I NOT %Updatebd%==False goto end_ok
if /I NOT %doANT%==False goto end_ok
if /I NOT %doANTifNOl2j-server.zip%==False goto end_ok
if /I NOT %MyAddons%==False goto end_ok

echo.
echo.No updates is selectable, Finish !!!
echo.
goto end_error

:end_ok
echo.
echo.All Updates Done, Finish !!!
echo.

:end_error
pause