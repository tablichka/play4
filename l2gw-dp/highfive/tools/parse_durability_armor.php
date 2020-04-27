<?
// Author SYS

$strings = file("armorgrp.txt");
if(count($strings)>0)
{
	for($i=1;$i<count($strings);$i++)
	{
		$data = explode("\t", $strings[$i]);
		echo "UPDATE `armor` SET `durability`=".($data[18] == 4294967295 ? -1 : $data[18])." WHERE `item_id`=".$data[1].";\n";
	}
}

?>