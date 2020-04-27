<html><head>
<title>Drop Calculator &copy; SYS</title>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=windows-1251">
</head><body>
<?php

/**
 * (All russian text writen at CP1251 encoding.)
 * *����������� ����� by SYS v 1.2, 18/01/2008*
 * ����������:
 * ��� ������ ���������:
 * - ��� ������(apache + php), ������������ �� IE 6.0;
 * - ��������� ����������� ���� � ���������� ���������� � PHP;
 * - ������ � ��(MySQL) �������.
 * �������� (��� ������):
 * - ������������� ���������� ���� �� �������, ���������� ��������� ��������;
 * - ������������ ����� ��������� � ������ � ����� �����;
 * - ������������, ��� �� ����� ������ � ���������� ������ ������ ���� 100%;
 * - ��� ����� � Seal Stones ������������� ���������� 70% ���� ����� � ������;
 * - ��� ���������� ����� ������ � ���� ������ �� �����������.
 * - ����� ��������� �� ���� ���� ��� ���� � ����� ����������� �����������.
 * ��� �� �������� ������� ���(�� ������ TODO):
 * - �������������� ������ ������ ����� ��� ���������, ������ �� ������ � ����
 * � ��������� ��������. ���� ������ ��� ����������.
 * - ����������� ��� ����������� ���������� ���� �����, ���� ����� �������
 * �����, �� ���� ������ ����������� �� 100%. ���� ����������.
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
	echo "������: ���������� ����������� � ��.";
	exit;
}

/********** ��������������� ���������: ����� ID ���� �� �������� **********/
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
			echo "����� � ����� ��������� �� �������.";
	} else
		echo search_mob_id();
} else

/********** ��������������� ���������: ����� ID �������� �� �������� **********/
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
			echo "��������� � ����� ��������� �� �������.";
	} else
		echo search_item_id();
} else

/********** �������� ��������� **********/
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
 * @return html ��� ����� ���� 1
 * $mob_id - id ����
 * $item_id,$item_min,$item_max,$item_sweep,$item_chance,$item_gid - �������
 **/
function form_step1($mob_id='',$item_id=array(),$item_min=array(),$item_max=array(),$item_sweep=array(),$item_chance=array(),$item_gid=array()){
	global $PHP_SELF, $ITEM_QUANTITY, $load;

	if (!isset($item_sweep)) $item_sweep=array_fill(0, $ITEM_QUANTITY, "off");

	$mob_id = $load;

	$result = "<h3>��� 1: ����������� ������ ����� ��� ����, ������ � ���������� �����.</h3>\n";
	$result .= "<form name='step1_form' action='".$PHP_SELF."' method='POST' onSubmit='javascript:_validateStep1();'>\n";
	$result .= "ID ����: <input type='text' name='mob_id' value='".$mob_id."' size='6'> \n";
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
	$result .= "<a href='javascript:void(0);' onClick=\"_openMob()\">[����� �� ��������]</a> \n";
	$result .= "<input type=button onClick='_Load()' value=\"��������� �� ����\">\n";
	$result .= "<hr>\n";
	$result .= "<b>���������� ����� ��� ����:</b><br>\n";
	$result .= "<table cellspacing=5 border=1><tr align='center'><td align='left'>ID ��������</td><td>����������<br>min</td><td>����������<br>max</td><td>�����</td><td>���� �����<br>1/x</tr>\n";

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

				// ��� ����� � Seal Stones ���� ������ 100%, � ���� ������ 70%
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
			echo "���a � ����� ID �� �������.";
	}

	for($i=0;$i<$ITEM_QUANTITY;$i++){

		if ($i==0 || (isset($item_id[$i]) && $item_id[$i]!=''))
			$result .= "<tr id='item-".$i."'>\n";
		else
			$result .= "<tr id='item-".$i."' style='display: none;'>\n";

		$result .= "<td><input type='text' name='item_id[".$i."]' id='item_id[".$i."]' size=4 value='".(isset($item_id[$i]) ? $item_id[$i] : '')."'>\n";
		$result .= "<a href='javascript:void(0)' onClick=\"_openItem(".$i.")\">[������]</a></td>\n";

		// min �� ��������� 1
		$current_min = current($item_min)>0 ? current($item_min) : 1;
		$result .= "<td><input type='text' name='item_min[".$i."]' size=2 value='".$current_min."'></td>\n";

		// max �� ��������� 1 � �� ����� min
		$current_max = current($item_max)>0 ? current($item_max) : 1;
		$current_max = $current_max>$current_min ? $current_max : $current_min;
		$result .= "<td><input type='text' name='item_max[".$i."]' size=2 value='".$current_max."'></td>\n";

		$item_sweep[$i] = !isset($item_sweep[$i]) || $item_sweep[$i]=='' ? 'off' : $item_sweep[$i];
		$current_sweep = $item_sweep[$i]=="on" ? " checked " : "";
		$result .= "<td align='center'><input type='checkbox' name='item_sweep[".$i."]'".$current_sweep."></td>\n";

		// chance �� ��������� 1
		$current_chance = current($item_chance)>0 ? current($item_chance) : 1;
		$result .= "<td>1/<input type='text' name='item_chance[".$i."]' id='item_chance[".$i."]' size=6 value='".$current_chance."' onFocus='_Adena(".$i.");'>\n";

		// group �� ��������� '', ���� �� 2� ���� ������� �����������
		$current_group = '';
		$result .= "<input type='hidden' name='item_gid[".$i."]' size=1 value='".$current_group."'>\n";

		// ������ ������ �������� �� ������� $item_id � �����
		while (end($item_id)=='' && count($item_id)!=0) array_pop($item_id);
		// ������ ���������� ������ � ���������
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
	$result .= "<input type='button' value='�������� ���' onClick='window.location.href(\"".$PHP_SELF."?".mt_rand()."\")'></td><td align=right><input type='submit' value='������������ &gt;&gt;&gt;'></td></tr></table>\n";
	$result .= "</td></tr>\n";
	$result .= "</table>\n";
	$result .= "</form>\n";
	return $result;
}

/**
 * @return html ��� ����� ���� 2 "�����������" 
 * $mob_id - id ����
 * $item_id,$item_min,$item_max,$item_sweep,$item_chance,$item_gid - �������
 **/
function form_step2($mob_id='',$item_id=array(),$item_min=array(),$item_max=array(),$item_sweep=array(),$item_chance=array(),$item_gid=array()){
	global $PHP_SELF, $ITEM_QUANTITY;

	if (!isset($item_sweep)) $item_sweep=array_fill(0, $ITEM_QUANTITY, "off");

	$result = "<h3>��� 2: ����������� �����.</h3>";
	$result .= "<form name='step2_form' action='".$PHP_SELF."' method='POST'>\n";
	$bg_color = $mob_id=='' ? "red" : "#cfcfcf";
	$result .= "ID ����: <input type='text' name='mob_id' value='".$mob_id."' size='6' readonly style='background: ".$bg_color.";'> \n";
	$npc_name = get_npc_name($mob_id);
	$npc_name = !$npc_name ? "<font color=red>����� ��� �� ������</font>" : $npc_name;
	$result .= "[".$npc_name."]<hr>\n";
	$result .= "<b>���� ��� ����� ����:</b><br>\n";
	$result .= "<table cellspacing=5 border=1><tr align='center'><td align='left'>ID ��������</td><td>����������<br>min</td><td>����������<br>max</td><td>�����</td><td>���� �����<br>1/x</td><td>������</td></tr>\n";

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
			$item_name = !$item_name ? '<font color=red>������� �� ������</font>' : $item_name;
		} else {
			$result .= "<tr id='item-".$i."' style='display: none;'>\n";
			$item_name = '';
		}

		$result .= "<td><input type='text' name='item_id[".$i."]' size=4 value='".current($item_id)."' readonly style='background: #cfcfcf;'>\n";
		$result .= "[".$item_name."]</td>\n";

		// min �� ��������� 1
		$current_min = current($item_min)>0 ? current($item_min) : 1;
		$result .= "<td><input type='text' name='item_min[".$i."]' size=2 value='".$current_min."' readonly style='background: #cfcfcf;'></td>\n";

		// max �� ��������� 1 � �� ����� min
		$current_max = current($item_max)>0 ? current($item_max) : 1;
		$current_max = $current_max>$current_min ? $current_max : $current_min;
		$result .= "<td><input type='text' name='item_max[".$i."]' size=2 value='".$current_max."' readonly style='background: #cfcfcf;'></td>\n";

		$item_sweep[$i] = !isset($item_sweep[$i]) || $item_sweep[$i]=='' ? 'off' : $item_sweep[$i];
		$current_sweep = $item_sweep[$i]=="on" ? " checked " : "";
		$result .= "<td align='center'><input type='checkbox' name='item_sweep[".$i."]'".$current_sweep." style='background: #cfcfcf;' onClick='return false;'></td>\n";

		// chance �� ��������� 1
		$current_chance = current($item_chance)>0 ? current($item_chance) : 1;
		// $off = " readonly style='background: #cfcfcf;";
		$result .= "<td>1/<input type='text' name='item_chance[".$i."]' size=6 value='".$current_chance."'".$off."'></td>\n";

		// group �� ��������� 3
		// ����������� ����������� ��������, � 1�� �� 2� ���
		$current_group = $item_gid[$i]=='' ? get_item_group($item_id[$i]) : $item_gid[$i];
		// � ���������� ����� ������ ������ 0
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
	$result .= "<input type='button' value='&lt;&lt;&lt; ������������� ����' onClick='step.value=1;submit();'></td><td align=right><input type='submit' value='��������� &gt;&gt;&gt;'></td></tr></table>\n";
	$result .= "</td></tr>\n";
	$result .= "</table>\n";
	$result .= "</form>\n";

	return $result;
}

/**
 * @return html ��� ����� ���� 3 "���������" 
 * $mob_id - id ����
 * $item_id,$item_min,$item_max,$item_sweep,$item_chance,$item_gid - �������
 **/
function form_step3($mob_id='',$item_id=array(),$item_min=array(),$item_max=array(),$item_sweep=array(),$item_chance=array(),$item_gid=array()){
	global $PHP_SELF, $ITEM_QUANTITY;

	if (!isset($item_sweep)) $item_sweep=array_fill(0, $ITEM_QUANTITY, "off");

	$result = "<h3>��� 3: ���������.</h3>";
	$result .= "<form name='step3_form' action='".$PHP_SELF."' method='POST'>\n";
	$bg_color = $mob_id=='' ? "red" : "#cfcfcf";
	$result .= "ID ����: <input type='text' name='mob_id' value='".$mob_id."' size='6' readonly style='background: ".$bg_color.";'> \n";
	$npc_name = get_npc_name($mob_id);
	$npc_name = !$npc_name ? "<font color=red>����� ��� �� ������</font>" : $npc_name;
	$result .= "[".$npc_name."]<hr>\n";
	$result .= "<b>���� ��� ����� ����:</b><br>\n";
	$result .= "<table cellspacing=5 border=1><tr align='center'><td align='left'>ID ��������</td><td>����������<br>min</td><td>����������<br>max</td><td>�����</td><td>���� �����<br>1/x</td><td>������</td></tr>\n";

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
			$item_name = !$item_name ? '<font color=red>������� �� ������</font>' : $item_name;
		} else {
			$result .= "<tr id='item-".$i."' style='display: none;'>\n";
			$item_name = '';
		}

		$result .= "<td><input type='text' name='item_id[".$i."]' size=4 value='".current($item_id)."' readonly style='background: #cfcfcf;'>\n";
		$result .= "[".$item_name."]</td>\n";

		// min �� ��������� 1
		$current_min = current($item_min)>0 ? current($item_min) : 1;
		$result .= "<td><input type='text' name='item_min[".$i."]' size=2 value='".$current_min."' readonly style='background: #cfcfcf;'></td>\n";

		// max �� ��������� 1 � �� ����� min
		$current_max = current($item_max)>0 ? current($item_max) : 1;
		$current_max = $current_max>$current_min ? $current_max : $current_min;
		$result .= "<td><input type='text' name='item_max[".$i."]' size=2 value='".$current_max."' readonly style='background: #cfcfcf;'></td>\n";

		$item_sweep[$i] = !isset($item_sweep[$i]) || $item_sweep[$i]=='' ? 'off' : $item_sweep[$i];
		$current_sweep = $item_sweep[$i]=="on" ? " checked " : "";
		$result .= "<td align='center'><input type='checkbox' name='item_sweep[".$i."]'".$current_sweep." style='background: #cfcfcf;' onClick='return false;'></td>\n";

		// chance �� ��������� 1
		$current_chance = current($item_chance)>0 ? current($item_chance) : 1;
		$result .= "<td>1/<input type='text' name='item_chance[".$i."]' size=6 value='".$current_chance."' readonly style='background: #cfcfcf;'></td>\n";

		// group �� ��������� 0
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
	$result .= "<input type='submit' value='&lt;&lt;&lt; �����������'></td><td align=right><input type='button' value='������ �������' onClick='window.location.href(\"".$PHP_SELF."?".mt_rand()."\")'></td></tr></table>\n";
	$result .= "</table>\n";
	$result .= "</form>\n";

	// ���������� �������
	// �������� ��������� ������
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

	// ���������� ���������� ���������
	// ������ ������ �������� �� ������� $item_id � �����
	while (end($item["id"])=='' && count($item["id"])!=0) array_pop($item["id"]);
	$total_elements = count($item["id"]);

	// ������� ��� ��������� ������� �� ��������� ���������� ���������
	while (list($key, $val) = each($item)){
		$item[$key] = array_chunk($item[$key],$total_elements);
		$item[$key] = $item[$key][0];
	}

	// ���������� ���������� �����
	$groups = array_count_values($item["gid"]);

	// ������� ������ �����
	while (list($group, $count) = each($groups)){
		// echo "������ $group, ��������� $count:<br>";
		// ������� ������ ��������� ������
		// � ��� ����� ������ ��������� ����� ������ � ���� ������
		$sum_group_chances = 0; // ����� ������ � ������
		foreach (array_keys($item["gid"], $group) as $num){
			// WH% = 1 / ����
			$sum_group_chances += 1/$item["chance"][$num];
			// echo " ".$item["id"][$num]." ".(1/$item["chance"][$num])."<br>";
		}
		// echo "����� ������ ������ $group: $sum_group_chances<br>";

		// ��������� ���� � ������: WH% / ����� ������ * 10^6
		foreach (array_keys($item["gid"], $group) as $num){
			$item["DB_chance"][$num] = 1000000/$item["chance"][$num] / $sum_group_chances;
			$item["DB_chance"][$num] = round($item["DB_chance"][$num],0);
			// echo " ".$item["id"][$num]." ".$item["DB_chance"][$num]."<br>";
		}

		// ��������� ���� ������ �� 1�� ��������:
		// ���� ������ = WH% / ���� � ������ *10000
		$gchance = 0;
		foreach (array_keys($item["gid"], $group) as $num){
			if ($gchance==0){
				$gchance = (1/$item["chance"][$num])/$item["DB_chance"][$num];
				$gchance = round($gchance*1000000000000,0);
			}
			$item["DB_gchance"][$num] = $gchance;
		}
		// echo "���� ������: ".$item["DB_gchance"][$num]."<br>";

		// ����� ������ � ������ ������ ���� 100%
		// ��������� ����������� � ������������ ������� � ������������ ������,
		// �� ��� ��� �� ����� �������.
		$DB_chance = 0;
		foreach (array_keys($item["gid"], $group) as $num){
			$DB_chance += $item["DB_chance"][$num];
		}
		// echo "����� ������, ������������ � �������,  ��� ������ $group: $DB_chance<br>";
		$diff = 1000000 - $DB_chance;
		// echo "����������� ����� ��� ������ $group: $diff<br>";

		// ��������� ������� � ���������� ������ � ���� ������
		$max_chance = 0;
		$max_chance_idx = 0;
		foreach (array_keys($item["gid"], $group) as $num){
			if($item["DB_chance"][$num] > $max_chance){
				$max_chance = $item["DB_chance"][$num];
				$max_chance_idx = $num;
			}
		}
		// ������������
		$item["DB_chance"][$max_chance_idx] = $item["DB_chance"][$max_chance_idx] + $diff;
	}

	// ������ ������ �������� �� ������� $item_id � �����
	while (end($item_id)=='' && count($item_id)!=0) array_pop($item_id);

	$result .= "<b>��������� � ���� SQL �������:</b><br><textarea cols=50 rows=20>\n";
	if ($mob_id=='')
		$result .= "�� ������ ID ����.";
	else {
		$result .= "INSERT INTO `droplist` (`mobId`,`itemId`,`min`,`max`,`sweep`,`chance`,`gid`,`gchance`) VALUES\n";
		for ($c=0;$c<count($item_id);$c++){
			if ($mob_id=='' || $item_id[$c]=='') continue;
			$result .= "(".$mob_id.",".$item_id[$c].",".$item_min[$c].",".$item_max[$c].",";

			if ($item_sweep[$c]=='on'){
				// ������ ���������� �����
				$spoil_chance = round(1 / $item_chance[$c] * 1000000, 0);
				$result .= "1,".$spoil_chance.",0,0)";
			} else if($item_id[$c]==57 || $item_id[$c]==6360 || $item_id[$c]==6361 || $item_id[$c]==6362){
				// ��� ����� � Seal Stones ���� ������ 100%, � ���� ������ 70%
				$chance = ($item_chance[$c]==700000 || $item_chance[$c]==1) ? 1000000 : $item_chance[$c];
				$result .= "0,".$chance.",".$item_gid[$c].",700000)";
			} else
				$result .= "0,".$item["DB_chance"][$c].",".$item_gid[$c].",".$item["DB_gchance"][$c].")";

			// � ���������� ����� � ����� ������ ";"
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
 * @return html ��� ����� "����� ID ���� �� ��������"
 **/
function search_mob_id($name_like=''){
	$result = "<h3>����� ID ���� �� ��������.</h3>";
	$result .= "<form method='POST' name='search'>������� �������� ����<br><input type='text' name='name_like' value=\"".stripslashes($name_like)."\"> <input type='submit' value='������'></form>";
	$result .= "<script>document.search.name_like.focus();</script>";
	return $result;
}

/**
 * @return html ��� ����� "����� ID �������� �� ��������"
 **/
function search_item_id($name_like=''){
	$result = "<h3>����� ID �������� �� ��������.</h3>";
	$result .= "<form method='POST' name='search'>������� �������� ��������<br>\n<input type='text' name='name_like' value=\"".stripslashes($name_like)."\"> <input type='submit' value='������'></form>";
	$result .= "<script>document.search.name_like.focus();</script>";
	return $result;
}

/**
 * @return �������� ��������
 * $id ID ��������
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
 * @return �������� NPC
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
 * ���������� ������ �������� � ���������� ����� ������.
 * ������ 0: �����.
 * ������ 1: �������, �������, ��������� ���������(������, �����, cursed bones).
 * ������ 2: ������, ����� ������, �����(� �.� � ���������), ����� �����.
 * ������ 3: �����, �������, �������, ������ � ��� ��������� ��������.
 * ������ 5,6,7: Seal Stones.
 * @return ��������������� ID ������
 * $id ID ��������
 **/
function get_item_group($id){

	if ($id=='') return '';

	// ���������
	$GROUP_ADENA = 0;
	$GROUP_CONSUMABLES = 1;
	$GROUP_WEAP_ARM = 2;
	$GROUP_ETC = 3; // ������ �� ���������
	$GROUP_GSS = 5; // Seal Stones ��������� �� ������ �������,
	$GROUP_BSS = 6; // ����� ����� ������ ������ 1 ��� ������ �� ���.
	$GROUP_RSS = 7; // � ������ ����� ����������� ������ ��� �����.

	$WHERE_COND = "WHERE `item_id`='".$id."' ";

	// �����
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

	// ����� ������
	$sql = "SELECT count(*) FROM `weapon` ".$WHERE_COND."LIMIT 1;";
	$res = mysql_query($sql);
	if ($res && mysql_num_rows($res)>0)
		if (mysql_result($res, 0)>0)
			return $GROUP_WEAP_ARM;

	// ����� �����
	$sql = "SELECT count(*) FROM `armor` ".$WHERE_COND."LIMIT 1;";
	$res = mysql_query($sql);
	if ($res && mysql_num_rows($res)>0)
		if (mysql_result($res, 0)>0)
			return $GROUP_WEAP_ARM;

	$sql = "SELECT * FROM `etcitem` WHERE `item_id`='".$id."' AND `item_type`!='quest' LIMIT 1;";
	$res = mysql_query($sql);
	if ($res && mysql_num_rows($res)>0)
		$row = mysql_fetch_array($res);
	// �������
	if ($row["item_type"]=="material" && $row["weight"]==2 && $row["consume_type"]=="stackable")
		return $GROUP_CONSUMABLES;
	// �������, ������, �������, Cursed Bone
	if ($row["item_type"]=="potion" || $row["item_type"]=="arrow" || $row["item_type"]=="recipe" || $row["name"]=="Cursed Bone")
		return $GROUP_CONSUMABLES;
	// ����� ������, ����� � ���������
	if ($row["weight"]==60 && $row["consume_type"]=="stackable")
		return $GROUP_WEAP_ARM;

	return $GROUP_ETC;
}

?>
</body></html>