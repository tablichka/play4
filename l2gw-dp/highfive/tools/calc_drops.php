<html><head>
<title>Drop Calculator &copy; SYS</title>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=windows-1251">
</head><body>
<?php

/**
 * (All russian text writen at CP1251 encoding.)
 * *Калькулятор дропа by SYS v 1.2, 18/01/2008*
 * Примечание:
 * Для работы необходмо:
 * - веб сервер(apache + php), отлаживалось на IE 6.0;
 * - разрешить всплывающие окна и глобальные переменные в PHP;
 * - работа с БД(MySQL) сервера.
 * Описание (что делает):
 * - Автоматически группирует дроп по группам, анализируя указанные предметы;
 * - Подсчитывает шансы предметов в группе и шансы групп;
 * - контролирует, что бы сумма шансов в конкретной группе всегда была 100%;
 * - для адены и Seal Stones автоматически выставляет 70% шанс дропа и группы;
 * - для спойлового дропа группа и шанс группы не учитываются.
 * - Умеет загружать из базы дроп для моба с целью последующей модификации.
 * Что бы хотелось сделать еще(по ихнему TODO):
 * - Автоматический расчет шансов дропа для предметов, исходя из данных о мобе
 * и стоимости предмета. Надо думать над алгоритмом.
 * - Неприемлема для формиования дроплистов рейд босов, если очень высокие
 * шансы, то шанс группы зашкаливает за 100%. Надо переделать.
 */

error_reporting(E_PARSE);

/********** DB config **********/
$L2JBS_config["mysql_host"]="localhost";
$L2JBS_config["mysql_db"]="la2";
$L2JBS_config["mysql_login"]="la2";
$L2JBS_config["mysql_password"]="";

/********** misc config **********/
$ITEM_QUANTITY = 50;
$SQL_LIMIT = 100;

$link = mysql_connect($L2JBS_config["mysql_host"],$L2JBS_config["mysql_login"],$L2JBS_config["mysql_password"]);
if ($link) mysql_select_db($L2JBS_config["mysql_db"], $link);
else {
	echo "Ошибка: Невозможно соедениться с БД.";
	exit;
}

/********** Вспомогательный интерфейс: Поиск ID моба по названию **********/
if(isset($search_mob)){
	if (count($HTTP_POST_VARS)>0){
		echo search_mob_id($name_like);
		echo "<hr>\n";
		echo "<script language='JavaScript'>\n";
		echo "function InsertAndClose(mob_id){\n";
		echo "	var target = window.opener.document.getElementById(\"mob_id\");\n";
		echo "	target.value = mob_id;\n";
		echo "	window.close();\n";
		echo "	return true;\n";
		echo "}\n";
		echo "</script>\n";
		$sql = "SELECT `id`, `name`, `level` FROM `npc` WHERE `name` like '%".$name_like."%' LIMIT ".$SQL_LIMIT.";";
		$res = mysql_query($sql);
		if ($res && mysql_num_rows($res)>0){
			while ($row = mysql_fetch_array($res)){
				echo "<a href='#' onClick=\"InsertAndClose(".$row["id"].")\">".$row["id"]."</a>";
				echo " ".$row["name"]." (".$row["level"].")<br>";
			}
		} else
			echo "Мобов с таким названием не найдено.";
	} else
		echo search_mob_id();
} else

/********** Вспомогательный интерфейс: Поиск ID предмета по названию **********/
if(isset($search_item)){
	if (count($HTTP_POST_VARS)>0){
		echo search_item_id($name_like);
		echo "<hr>\n";
		echo "<script language='JavaScript'>\n";
		echo "function InsertAndClose(mob_id){\n";
		echo "	var target = window.opener.document.getElementById(\"item_id[".$item_num."]\");\n";
		echo "	target.value = mob_id;\n";
		echo "	window.close();\n";
		echo "	return true;\n";
		echo "}\n";
		echo "</script>\n";
		$WHERE_COND = "WHERE `name`  like '%".$name_like."%' ";
		$sql = "SELECT `item_id`, `name`, '' FROM `etcitem` ".$WHERE_COND.
			"UNION ALL ".
			"SELECT `item_id`, `name`, `additional_name` FROM `weapon` ".$WHERE_COND.
			"UNION ALL ".
			"SELECT `item_id`, `name`, `additional_name` FROM `armor` ".$WHERE_COND."ORDER BY `name` ASC LIMIT ".$SQL_LIMIT.";";
		$res = mysql_query($sql);
		if ($res && mysql_num_rows($res)>0){
			while ($row = mysql_fetch_array($res)){
				echo "<a href='#' onClick=\"InsertAndClose(".$row["item_id"].")\">".$row["item_id"]."</a>";
				echo " ".htmlspecialchars($row["name"]);
				if($row[2] != '')
					echo " [".htmlspecialchars($row[2])."]";
				echo "<br>";
			}
		} else
			echo "Предметов с таким названием не найдено.";
	} else
		echo search_item_id();
} else

/********** Основной интерфейс **********/
if (count($HTTP_POST_VARS)>0){
	if (!isset($step) || $step=='' || $step==1){
		echo form_step1($mob_id,$item_id,$item_min,$item_max,@$item_sweep,$item_chance,$item_gid);
	} else if ($step==2){
		echo form_step2($mob_id,$item_id,$item_min,$item_max,@$item_sweep,$item_chance,$item_gid);
	} else if ($step==3){
		echo form_step3($mob_id,$item_id,$item_min,$item_max,@$item_sweep,$item_chance,$item_gid);
	}
} else {
	echo form_step1();
}

if ($link) mysql_close();

/********** Forms **********/

/**
 * @return html код формы шага 1
 * $mob_id - id моба
 * $item_id,$item_min,$item_max,$item_sweep,$item_chance,$item_gid - предмет
 **/
function form_step1($mob_id='',$item_id=array(),$item_min=array(),$item_max=array(),$item_sweep=array(),$item_chance=array(),$item_gid=array()){
	global $PHP_SELF, $ITEM_QUANTITY, $load;

	if (!isset($item_sweep)) $item_sweep=array_fill(0, $ITEM_QUANTITY, "off");

	$mob_id = $load;

	$result = "<h3>Шаг 1: Определение списка дропа для моба, шансов и спойлового дропа.</h3>\n";
	$result .= "<form name='step1_form' action='".$PHP_SELF."' method='POST' onSubmit='javascript:_validateStep1();'>\n";
	$result .= "ID моба: <input type='text' name='mob_id' value='".$mob_id."' size='6'> \n";
	$result .= "<script>\n";
	$result .= "function _openMob(){window.open(\"".$PHP_SELF."?search_mob\");return false;}\n";
	$result .= "function _openItem(num){window.open(\"".$PHP_SELF."?search_item&item_num=\" + num);return false;}\n";
	$result .= "function _showItem(item_strind,item_button){\n";
	$result .= "	obj1 = document.getElementById(item_strind);\n";
	$result .= "	if (obj1.style.display == \"none\")\n";
	$result .= "		obj1.style.display = \"\";\n";
	$result .= "	obj2 = document.getElementById(item_button);\n";
	$result .= "	if (obj2.style.display == \"\")\n";
	$result .= "		obj2.style.display = \"none\";\n";
	$result .= "	return false;\n";
	$result .= "}\n";
	$result .= "function _Adena(item_num){\n";
	$result .= "	item_id = document.getElementById('item_id[' + item_num + ']');\n";
	$result .= "	item_chance = document.getElementById('item_chance[' + item_num + ']');\n";
	$result .= "	if (item_chance.value=='1' && (item_id.value=='57' || item_id.value=='6360' || item_id.value=='6361' || item_id.value=='6362')){item_chance.value=700000}\n";
	$result .= "	return true;\n";
	$result .= "}\n";
	$result .= "function _Load(){\n";
	$result .= "	mob_id = document.getElementById('mob_id');\n";
	$result .= "	if(mob_id.value != '' && mob_id.value == mob_id.value * 1)\n";
	$result .= "		window.location.href(\"".$PHP_SELF."?load=\" + mob_id.value);\n";
	$result .= "	return true;\n";
	$result .= "}\n";
	$result .= "</script>\n";
	$result .= "<a href='javascript:void(0);' onClick=\"_openMob()\">[Найти по названию]</a> \n";
	$result .= "<input type=button onClick='_Load()' value=\"Загрузить из базы\">\n";
	$result .= "<hr>\n";
	$result .= "<b>Добавление дропа для моба:</b><br>\n";
	$result .= "<table cellspacing=5 border=1><tr align='center'><td align='left'>ID предмета</td><td>Количество<br>min</td><td>Количество<br>max</td><td>Спойл</td><td>Шанс дропа<br>1/x</tr>\n";

	reset($item_id);
	reset($item_min);
	reset($item_max);
	reset($item_sweep);
	reset($item_chance);
	reset($item_gid);

	if(isset($load) && is_numeric($load)){
		echo "Loading mob droplist from DB...";
		$SQLq = "SELECT * FROM `droplist` WHERE `mobId` = ".$load." ORDER BY `sweep` ASC, GREATEST(`chance`*`gchance`, `chance`*`sweep`) DESC;";
		$res = mysql_query($SQLq);
		if ($res && mysql_num_rows($res)>0){
			$i = 0;
			while ($row = mysql_fetch_array($res)){
				$item_id[$i] = $row["itemId"];
				$item_min[$i] = $row["min"];
				$item_max[$i] = $row["max"];
				$item_sweep[$i] = $row["sweep"] == "1" ? "on" : "off";

				// Для адены и Seal Stones шанс всегда 100%, а шанс группы 70%
				if($item_id[$i]==57 || $item_id[$i]==6360 || $item_id[$i]==6361 || $item_id[$i]==6362)
					$item_chance[$i]=700000;
				else if ($row["sweep"]!=1)
					$item_chance[$i] = $row["chance"] * $row["gchance"] / 1000000;
				else
					$item_chance[$i] = $row["chance"];

				$item_chance[$i] = round(1000000/$item_chance[$i],0);

				$item_gid[$i] = $row["gid"];
				$i++;
			}
			reset($item_id);
			reset($item_min);
			reset($item_max);
			reset($item_sweep);
			reset($item_chance);
			reset($item_gid);
		} else
			echo "Мобa с таким ID не найдено.";
	}

	for($i=0;$i<$ITEM_QUANTITY;$i++){

		if ($i==0 || (isset($item_id[$i]) && $item_id[$i]!=''))
			$result .= "<tr id='item-".$i."'>\n";
		else
			$result .= "<tr id='item-".$i."' style='display: none;'>\n";

		$result .= "<td><input type='text' name='item_id[".$i."]' id='item_id[".$i."]' size=4 value='".(isset($item_id[$i]) ? $item_id[$i] : '')."'>\n";
		$result .= "<a href='javascript:void(0)' onClick=\"_openItem(".$i.")\">[Искать]</a></td>\n";

		// min по умолчанию 1
		$current_min = current($item_min)>0 ? current($item_min) : 1;
		$result .= "<td><input type='text' name='item_min[".$i."]' size=2 value='".$current_min."'></td>\n";

		// max по умолчанию 1 и не более min
		$current_max = current($item_max)>0 ? current($item_max) : 1;
		$current_max = $current_max>$current_min ? $current_max : $current_min;
		$result .= "<td><input type='text' name='item_max[".$i."]' size=2 value='".$current_max."'></td>\n";

		$item_sweep[$i] = !isset($item_sweep[$i]) || $item_sweep[$i]=='' ? 'off' : $item_sweep[$i];
		$current_sweep = $item_sweep[$i]=="on" ? " checked " : "";
		$result .= "<td align='center'><input type='checkbox' name='item_sweep[".$i."]'".$current_sweep."></td>\n";

		// chance по умолчанию 1
		$current_chance = current($item_chance)>0 ? current($item_chance) : 1;
		$result .= "<td>1/<input type='text' name='item_chance[".$i."]' id='item_chance[".$i."]' size=6 value='".$current_chance."' onFocus='_Adena(".$i.");'>\n";

		// group по умолчанию '', дабы во 2м шаге сделать группировку
		$current_group = '';
		$result .= "<input type='hidden' name='item_gid[".$i."]' size=1 value='".$current_group."'>\n";

		// Удалим пустые элементы из массива $item_id с конца
		while (end($item_id)=='' && count($item_id)!=0) array_pop($item_id);
		// Кнопка добавления строки с предметом
		if ($i < $ITEM_QUANTITY-1 && count($item_id) < $i+2)
			$result .= "<input type='button' id='item_button-".$i."' onClick=\"_showItem('item-".($i+1)."','item_button-".$i."')\" value=' + '>\n";

		$result .= "</td>\n</tr>\n";

		next($item_min);
		next($item_max);
		next($item_sweep);
		next($item_chance);
		next($item_gid);
	}

	$result .= "<input type='hidden' name='step' value='2'>\n";
	$result .= "<tr><td colspan=6>\n";
	$result .= "<table border=0 width=100%><tr><td>\n";
	$result .= "<input type='button' value='Очистить все' onClick='window.location.href(\"".$PHP_SELF."?".mt_rand()."\")'></td><td align=right><input type='submit' value='Группировать &gt;&gt;&gt;'></td></tr></table>\n";
	$result .= "</td></tr>\n";
	$result .= "</table>\n";
	$result .= "</form>\n";
	return $result;
}

/**
 * @return html код формы шага 2 "группировка" 
 * $mob_id - id моба
 * $item_id,$item_min,$item_max,$item_sweep,$item_chance,$item_gid - предмет
 **/
function form_step2($mob_id='',$item_id=array(),$item_min=array(),$item_max=array(),$item_sweep=array(),$item_chance=array(),$item_gid=array()){
	global $PHP_SELF, $ITEM_QUANTITY;

	if (!isset($item_sweep)) $item_sweep=array_fill(0, $ITEM_QUANTITY, "off");

	$result = "<h3>Шаг 2: Группировка дропа.</h3>";
	$result .= "<form name='step2_form' action='".$PHP_SELF."' method='POST'>\n";
	$bg_color = $mob_id=='' ? "red" : "#cfcfcf";
	$result .= "ID моба: <input type='text' name='mob_id' value='".$mob_id."' size='6' readonly style='background: ".$bg_color.";'> \n";
	$npc_name = get_npc_name($mob_id);
	$npc_name = !$npc_name ? "<font color=red>Такой моб не найден</font>" : $npc_name;
	$result .= "[".$npc_name."]<hr>\n";
	$result .= "<b>Дроп для этого моба:</b><br>\n";
	$result .= "<table cellspacing=5 border=1><tr align='center'><td align='left'>ID предмета</td><td>Количество<br>min</td><td>Количество<br>max</td><td>Спойл</td><td>Шанс дропа<br>1/x</td><td>Группа</td></tr>\n";

	reset($item_id);
	reset($item_min);
	reset($item_max);
	reset($item_sweep);
	reset($item_chance);
	reset($item_gid);

	for($i=0;$i<$ITEM_QUANTITY;$i++){

		if ($i==0 || (isset($item_id[$i]) && $item_id[$i]!='')){
			$result .= "<tr id='item-".$i."'>\n";
			$item_name = get_item_name($item_id[$i]);
			$item_name = !$item_name ? '<font color=red>Предмет не найден</font>' : $item_name;
		} else {
			$result .= "<tr id='item-".$i."' style='display: none;'>\n";
			$item_name = '';
		}

		$result .= "<td><input type='text' name='item_id[".$i."]' size=4 value='".current($item_id)."' readonly style='background: #cfcfcf;'>\n";
		$result .= "[".$item_name."]</td>\n";

		// min по умолчанию 1
		$current_min = current($item_min)>0 ? current($item_min) : 1;
		$result .= "<td><input type='text' name='item_min[".$i."]' size=2 value='".$current_min."' readonly style='background: #cfcfcf;'></td>\n";

		// max по умолчанию 1 и не более min
		$current_max = current($item_max)>0 ? current($item_max) : 1;
		$current_max = $current_max>$current_min ? $current_max : $current_min;
		$result .= "<td><input type='text' name='item_max[".$i."]' size=2 value='".$current_max."' readonly style='background: #cfcfcf;'></td>\n";

		$item_sweep[$i] = !isset($item_sweep[$i]) || $item_sweep[$i]=='' ? 'off' : $item_sweep[$i];
		$current_sweep = $item_sweep[$i]=="on" ? " checked " : "";
		$result .= "<td align='center'><input type='checkbox' name='item_sweep[".$i."]'".$current_sweep." style='background: #cfcfcf;' onClick='return false;'></td>\n";

		// chance по умолчанию 1
		$current_chance = current($item_chance)>0 ? current($item_chance) : 1;
		// $off = " readonly style='background: #cfcfcf;";
		$result .= "<td>1/<input type='text' name='item_chance[".$i."]' size=6 value='".$current_chance."'".$off."'></td>\n";

		// group по умолчанию 3
		// Группировка выполняется единожды, с 1го на 2й шаг
		$current_group = $item_gid[$i]=='' ? get_item_group($item_id[$i]) : $item_gid[$i];
		// У спойлового дропа группа всегда 0
		$current_group = $item_sweep[$i]=="on" ? '' : $current_group;
		$ro = @$item_sweep[$i]=="on" ? " readonly style='background: #cfcfcf;'" : "";
		$result .= "<td><input type='text' name='item_gid[".$i."]' size=1 value='".$current_group."'".$ro.">\n";

		$result .= "</td>\n</tr>\n";

		next($item_id);
		next($item_min);
		next($item_max);
		next($item_sweep);
		next($item_chance);
		next($item_gid);
	}

	$result .= "<input type='hidden' name='step' value='3'>\n";
	$result .= "<tr><td colspan=6>\n";
	$result .= "<table border=0 width=100%><tr><td>\n";
	$result .= "<input type='button' value='&lt;&lt;&lt; Редактировать дроп' onClick='step.value=1;submit();'></td><td align=right><input type='submit' value='Результат &gt;&gt;&gt;'></td></tr></table>\n";
	$result .= "</td></tr>\n";
	$result .= "</table>\n";
	$result .= "</form>\n";

	return $result;
}

/**
 * @return html код формы шага 3 "Результат" 
 * $mob_id - id моба
 * $item_id,$item_min,$item_max,$item_sweep,$item_chance,$item_gid - предмет
 **/
function form_step3($mob_id='',$item_id=array(),$item_min=array(),$item_max=array(),$item_sweep=array(),$item_chance=array(),$item_gid=array()){
	global $PHP_SELF, $ITEM_QUANTITY;

	if (!isset($item_sweep)) $item_sweep=array_fill(0, $ITEM_QUANTITY, "off");

	$result = "<h3>Шаг 3: Результат.</h3>";
	$result .= "<form name='step3_form' action='".$PHP_SELF."' method='POST'>\n";
	$bg_color = $mob_id=='' ? "red" : "#cfcfcf";
	$result .= "ID моба: <input type='text' name='mob_id' value='".$mob_id."' size='6' readonly style='background: ".$bg_color.";'> \n";
	$npc_name = get_npc_name($mob_id);
	$npc_name = !$npc_name ? "<font color=red>Такой моб не найден</font>" : $npc_name;
	$result .= "[".$npc_name."]<hr>\n";
	$result .= "<b>Дроп для этого моба:</b><br>\n";
	$result .= "<table cellspacing=5 border=1><tr align='center'><td align='left'>ID предмета</td><td>Количество<br>min</td><td>Количество<br>max</td><td>Спойл</td><td>Шанс дропа<br>1/x</td><td>Группа</td></tr>\n";

	reset($item_id);
	reset($item_min);
	reset($item_max);
	reset($item_sweep);
	reset($item_chance);
	reset($item_gid);

	for($i=0;$i<$ITEM_QUANTITY;$i++){

		if ($i==0 || (isset($item_id[$i]) && $item_id[$i]!='')){
			$result .= "<tr id='item-".$i."'>\n";
			$item_name = get_item_name($item_id[$i]);
			$item_name = !$item_name ? '<font color=red>Предмет не найден</font>' : $item_name;
		} else {
			$result .= "<tr id='item-".$i."' style='display: none;'>\n";
			$item_name = '';
		}

		$result .= "<td><input type='text' name='item_id[".$i."]' size=4 value='".current($item_id)."' readonly style='background: #cfcfcf;'>\n";
		$result .= "[".$item_name."]</td>\n";

		// min по умолчанию 1
		$current_min = current($item_min)>0 ? current($item_min) : 1;
		$result .= "<td><input type='text' name='item_min[".$i."]' size=2 value='".$current_min."' readonly style='background: #cfcfcf;'></td>\n";

		// max по умолчанию 1 и не более min
		$current_max = current($item_max)>0 ? current($item_max) : 1;
		$current_max = $current_max>$current_min ? $current_max : $current_min;
		$result .= "<td><input type='text' name='item_max[".$i."]' size=2 value='".$current_max."' readonly style='background: #cfcfcf;'></td>\n";

		$item_sweep[$i] = !isset($item_sweep[$i]) || $item_sweep[$i]=='' ? 'off' : $item_sweep[$i];
		$current_sweep = $item_sweep[$i]=="on" ? " checked " : "";
		$result .= "<td align='center'><input type='checkbox' name='item_sweep[".$i."]'".$current_sweep." style='background: #cfcfcf;' onClick='return false;'></td>\n";

		// chance по умолчанию 1
		$current_chance = current($item_chance)>0 ? current($item_chance) : 1;
		$result .= "<td>1/<input type='text' name='item_chance[".$i."]' size=6 value='".$current_chance."' readonly style='background: #cfcfcf;'></td>\n";

		// group по умолчанию 0
		$current_group = current($item_gid)!=0 ? current($item_gid) : 0;
		$current_group = $item_sweep[$i]=="on" ? '' : $current_group;
		$result .= "<td><input type='text' name='item_gid[".$i."]' size=1 value='".$current_group."' readonly style='background: #cfcfcf;'>\n";

		$result .= "</td>\n</tr>\n";

		next($item_id);
		next($item_min);
		next($item_max);
		next($item_sweep);
		next($item_chance);
		next($item_gid);
	}

	$result .= "<input type='hidden' name='step' value='2'>\n";
	$result .= "<tr><td colspan=6>\n";
	$result .= "<table border=0 width=100%><tr><td>\n";
	$result .= "<input type='submit' value='&lt;&lt;&lt; Группировка'></td><td align=right><input type='button' value='Начать сначала' onClick='window.location.href(\"".$PHP_SELF."?".mt_rand()."\")'></td></tr></table>\n";
	$result .= "</table>\n";
	$result .= "</form>\n";

	// Произведем расчеты
	// Создадим двумерный массив
	$item = array();
	$item["id"] = $item_id;
	$item["min"] = $item_min;
	$item["max"] = $item_max;
	$item["chance"] = $item_chance;
	$item["DB_chance"] = array_fill(0, $ITEM_QUANTITY, 0);
	$item["DB_gchance"] = array_fill(0, $ITEM_QUANTITY, 0);
	$item["gid"] = $item_gid;
	$item["sweep"] = $item_sweep;

	// print_r($item);

	// Подсчитаем количество элементов
	// Удалим пустые элементы из массива $item_id с конца
	while (end($item["id"])=='' && count($item["id"])!=0) array_pop($item["id"]);
	$total_elements = count($item["id"]);

	// Обрежем все вложенные массивы до реального количества элементов
	while (list($key, $val) = each($item)){
		$item[$key] = array_chunk($item[$key],$total_elements);
		$item[$key] = $item[$key][0];
	}

	// Подсчитаем количество групп
	$groups = array_count_values($item["gid"]);

	// Получим список групп
	while (list($group, $count) = each($groups)){
		// echo "Группа $group, элементов $count:<br>";
		// Получим список элементов группы
		// И для этого списка посчитаем сумму шансов в этой группе
		$sum_group_chances = 0; // Сумма шансов в группе
		foreach (array_keys($item["gid"], $group) as $num){
			// WH% = 1 / Шанс
			$sum_group_chances += 1/$item["chance"][$num];
			// echo " ".$item["id"][$num]." ".(1/$item["chance"][$num])."<br>";
		}
		// echo "Сумма шансов группы $group: $sum_group_chances<br>";

		// Посчитаем шанс в группе: WH% / сумму шансов * 10^6
		foreach (array_keys($item["gid"], $group) as $num){
			$item["DB_chance"][$num] = 1000000/$item["chance"][$num] / $sum_group_chances;
			$item["DB_chance"][$num] = round($item["DB_chance"][$num],0);
			// echo " ".$item["id"][$num]." ".$item["DB_chance"][$num]."<br>";
		}

		// Посчитаем шанс группы по 1му элементу:
		// Шанс группы = WH% / Шанс в группе *10000
		$gchance = 0;
		foreach (array_keys($item["gid"], $group) as $num){
			if ($gchance==0){
				$gchance = (1/$item["chance"][$num])/$item["DB_chance"][$num];
				$gchance = round($gchance*1000000000000,0);
			}
			$item["DB_gchance"][$num] = $gchance;
		}
		// echo "Шанс группы: ".$item["DB_gchance"][$num]."<br>";

		// Сумма шансов в группе должна быть 100%
		// Посчитаем расхождение и модифицируем предмет с максимальным шансом,
		// на нем это не будет заметно.
		$DB_chance = 0;
		foreach (array_keys($item["gid"], $group) as $num){
			$DB_chance += $item["DB_chance"][$num];
		}
		// echo "Сумма шансов, записываемых в таблицу,  для группы $group: $DB_chance<br>";
		$diff = 1000000 - $DB_chance;
		// echo "Расхождение шанса для группы $group: $diff<br>";

		// Определим предмет с наибольшим шансом в этой группе
		$max_chance = 0;
		$max_chance_idx = 0;
		foreach (array_keys($item["gid"], $group) as $num){
			if($item["DB_chance"][$num] > $max_chance){
				$max_chance = $item["DB_chance"][$num];
				$max_chance_idx = $num;
			}
		}
		// модифицируем
		$item["DB_chance"][$max_chance_idx] = $item["DB_chance"][$max_chance_idx] + $diff;
	}

	// Удалим пустые элементы из массива $item_id с конца
	while (end($item_id)=='' && count($item_id)!=0) array_pop($item_id);

	$result .= "<b>Результат в виде SQL запроса:</b><br><textarea cols=50 rows=20>\n";
	if ($mob_id=='')
		$result .= "Не указан ID моба.";
	else {
		$result .= "INSERT INTO `droplist` (`mobId`,`itemId`,`min`,`max`,`sweep`,`chance`,`gid`,`gchance`) VALUES\n";
		for ($c=0;$c<count($item_id);$c++){
			if ($mob_id=='' || $item_id[$c]=='') continue;
			$result .= "(".$mob_id.",".$item_id[$c].",".$item_min[$c].",".$item_max[$c].",";

			if ($item_sweep[$c]=='on'){
				// Расчет спойлового шанса
				$spoil_chance = round(1 / $item_chance[$c] * 1000000, 0);
				$result .= "1,".$spoil_chance.",0,0)";
			} else if($item_id[$c]==57 || $item_id[$c]==6360 || $item_id[$c]==6361 || $item_id[$c]==6362){
				// Для адены и Seal Stones шанс всегда 100%, а шанс группы 70%
				$chance = ($item_chance[$c]==700000 || $item_chance[$c]==1) ? 1000000 : $item_chance[$c];
				$result .= "0,".$chance.",".$item_gid[$c].",700000)";
			} else
				$result .= "0,".$item["DB_chance"][$c].",".$item_gid[$c].",".$item["DB_gchance"][$c].")";

			// У последнего дропа в конце ставим ";"
			if ($c<count($item_id)-1)
				$result .= ",\n";
			else
				$result .= ";\n";
		}
	}
	$result .= "</textarea>\n";

	return $result;
}


/**
 * @return html код формы "Поиск ID моба по названию"
 **/
function search_mob_id($name_like=''){
	$result = "<h3>Поиск ID моба по названию.</h3>";
	$result .= "<form method='POST' name='search'>Введите название моба<br><input type='text' name='name_like' value=\"".stripslashes($name_like)."\"> <input type='submit' value='Искать'></form>";
	$result .= "<script>document.search.name_like.focus();</script>";
	return $result;
}

/**
 * @return html код формы "Поиск ID предмета по названию"
 **/
function search_item_id($name_like=''){
	$result = "<h3>Поиск ID предмета по названию.</h3>";
	$result .= "<form method='POST' name='search'>Введите название предмета<br>\n<input type='text' name='name_like' value=\"".stripslashes($name_like)."\"> <input type='submit' value='Искать'></form>";
	$result .= "<script>document.search.name_like.focus();</script>";
	return $result;
}

/**
 * @return Название предмета
 * $id ID предмета
 **/
function get_item_name($id){
	$WHERE_COND = "WHERE `item_id`='".$id."' ";
	$sql = "SELECT `name`, '' FROM `etcitem` ".$WHERE_COND.
		"UNION ALL ".
		"SELECT `name`, `additional_name` FROM `weapon` ".$WHERE_COND.
		"UNION ALL ".
		"SELECT `name`, `additional_name` FROM `armor` ".$WHERE_COND."LIMIT 1;";
	$res = mysql_query($sql);
	if ($res && mysql_num_rows($res)>0){
		$row = mysql_fetch_array($res);
		$name = htmlspecialchars($row[0]);
		if($row[1] != '')
			$name .= " [".htmlspecialchars($row[1])."]";
		return $name;
	}
	else
		return false;
}

/**
 * @return Название NPC
 * $id ID NPC
 **/
function get_npc_name($id){
	$COLUMN_LIST = "`name`";
	$sql = "SELECT `name` FROM `npc` WHERE `id`='".$id."' LIMIT 1;";
	$res = mysql_query($sql);
	if ($res && mysql_num_rows($res)>0)
		return mysql_result($res, 0);
	else
		return false;
}

/**
 * Производит анализ предмета и возвращает номер группы.
 * Группа 0: адена.
 * Группа 1: ресурсы, рецепты, расходные материалы(стрелы, банки, cursed bones).
 * Группа 2: оружие, куски оружия, броня(в т.ч и бижутерия), куски брони.
 * Группа 3: книги, амулеты, заточки, краски и все остальные предметы.
 * Группа 5,6,7: Seal Stones.
 * @return сгенерированный ID группы
 * $id ID предмета
 **/
function get_item_group($id){

	if ($id=='') return '';

	// константы
	$GROUP_ADENA = 0;
	$GROUP_CONSUMABLES = 1;
	$GROUP_WEAP_ARM = 2;
	$GROUP_ETC = 3; // группа по умолчанию
	$GROUP_GSS = 5; // Seal Stones распихаем по разным группам,
	$GROUP_BSS = 6; // иначе будет падать только 1 вид камней за раз.
	$GROUP_RSS = 7; // А должны иметь возможность упасть все сразу.

	$WHERE_COND = "WHERE `item_id`='".$id."' ";

	// адена
	if ($id =='57')
		return $GROUP_ADENA;

	// Seal Stones
	if ($id =='6360')
		return $GROUP_GSS;

	// Seal Stones
	if ($id =='6361')
		return $GROUP_BSS;

	// Seal Stones
	if ($id =='6362')
		return $GROUP_RSS;

	// целое оружие
	$sql = "SELECT count(*) FROM `weapon` ".$WHERE_COND."LIMIT 1;";
	$res = mysql_query($sql);
	if ($res && mysql_num_rows($res)>0)
		if (mysql_result($res, 0)>0)
			return $GROUP_WEAP_ARM;

	// целая броня
	$sql = "SELECT count(*) FROM `armor` ".$WHERE_COND."LIMIT 1;";
	$res = mysql_query($sql);
	if ($res && mysql_num_rows($res)>0)
		if (mysql_result($res, 0)>0)
			return $GROUP_WEAP_ARM;

	$sql = "SELECT * FROM `etcitem` WHERE `item_id`='".$id."' AND `item_type`!='quest' LIMIT 1;";
	$res = mysql_query($sql);
	if ($res && mysql_num_rows($res)>0)
		$row = mysql_fetch_array($res);
	// Ресурсы
	if ($row["item_type"]=="material" && $row["weight"]==2 && $row["consume_type"]=="stackable")
		return $GROUP_CONSUMABLES;
	// бутылки, стрелы, рецепты, Cursed Bone
	if ($row["item_type"]=="potion" || $row["item_type"]=="arrow" || $row["item_type"]=="recipe" || $row["name"]=="Cursed Bone")
		return $GROUP_CONSUMABLES;
	// куски оружия, брони и бижутерии
	if ($row["weight"]==60 && $row["consume_type"]=="stackable")
		return $GROUP_WEAP_ARM;

	return $GROUP_ETC;
}

?>
</body></html>