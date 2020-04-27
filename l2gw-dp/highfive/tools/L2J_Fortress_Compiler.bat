@echo off
TITLE -==[ Automatic updater for L2J Fortress - Compiler Module ]==-
REM #### Made by Krab (RMT Team) ####
REM ########################################

set PathToSVNL2JFortress=D:\L2J_Projects\L2J_SVN\L2J_Fortress

REM ########################################
if exist %PathToSVNL2JFortress%\build (
echo.
echo.Deleting %PathToSVNL2JFortress%\build dir
RD /q /s %PathToSVNL2JFortress%\build
echo.Deleting %PathToSVNL2JFortress%\build dir - Done !!!
echo.
) ELSE (
echo.
echo.Dir %PathToSVNL2JFortress%\build is empty or was deleted !!!
echo.
)

echo.
echo.Updating SVN
svn co http://trac.balancer.ru/svn/l2j/trunk %PathToSVNL2JFortress%
echo.Updating SVN - Done !!!
echo.
cd %PathToSVNL2JFortress%
call ant.bat
exit
