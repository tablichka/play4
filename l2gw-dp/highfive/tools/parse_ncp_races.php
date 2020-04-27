<?
// Author SYS

$strings = file("npcgrp.txt");
if(count($strings)>0){
	for($i=1;$i<count($strings);$i++){
		$data = explode("\t", $strings[$i]);
		if($data[13] == 4416)
			echo "REPLACE INTO `npcskills` VALUES (".$data[0].",".$data[13].",".$data[14].");\n";
	}
}

?>