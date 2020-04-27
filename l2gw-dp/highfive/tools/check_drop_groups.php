Check Drop Groups (c) SYS
<?

/**
 * (All russian text writen at CP1251 encoding.)
 * пример запуска: php.exe check_drop_groups.php >bad_groups.txt
 * Описание (что делает):
 * - ищет группы, у которых сумма шансов, не 100% и ругается на них
 */

// error_reporting(E_PARSE);

/********** DB config **********/
$L2JBS_config["mysql_host"]="localhost";
$L2JBS_config["mysql_db"]="la2";
$L2JBS_config["mysql_login"]="la2";
$L2JBS_config["mysql_password"]="";

$link = mysql_connect($L2JBS_config["mysql_host"],$L2JBS_config["mysql_login"],$L2JBS_config["mysql_password"]);
if ($link) mysql_select_db($L2JBS_config["mysql_db"], $link);
else {
	echo "Ошибка: Невозможно соедениться с БД.";
	exit;
}


$sql = "SELECT `mobId` FROM `droplist` GROUP BY `mobId`;";
//SELECT `mobId`,`gid`, sum(`chance`) AS `gsum` FROM `droplist` WHERE `sweep`!=1 AND `mobId`=25470 GROUP BY `gid` ORDER BY `mobId`,`gid`;
$res = mysql_query($sql);
if ($res && mysql_num_rows($res)>0){
	while ($row = mysql_fetch_array($res)){
		$sql_grp = "SELECT `mobId`,`gid`, sum(`chance`) AS `gsum` FROM `droplist` WHERE `sweep`!=1 AND `mobId`=".$row["mobId"]." GROUP BY `gid` ORDER BY `mobId`,`gid`;";
		$res_grp = mysql_query($sql_grp);
		if ($res_grp && mysql_num_rows($res_grp)>0){
			while ($row_grp = mysql_fetch_array($res_grp)){
				if ($row_grp["gsum"] != '1000000')
					echo "Wrong summ group chances. mobId: ".$row["mobId"].", group ".$row_grp["gid"].", summ: ".$row_grp["gsum"]."\n";
			}
		}
	}
} else
	echo "Мобов с таким названием не найдено.";


if ($link) mysql_close();

?>