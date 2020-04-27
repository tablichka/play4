<?php

$strings = file("npcgrp.txt");
if(count($strings) > 0)
{
	for($i = 1; $i < count($strings); $i++)
	{
		$data = explode("\t", $strings[$i]);
		$skills = explode("\"", $data[8]);

		$npc_id = $data[0];
		$skills = explode(",", $skills[1]);

		if(count($skills) > 0)
		{
			for($j = 0; $j <= count($skills) - 2; $j = $j + 2)
			{
				$skill_id = $skills[$j];
				$skill_level = $skills[$j + 1];

				//if($skill_id <> 4416)
				//	echo "DELETE FROM `npcskills` WHERE `npcid` = ".$npc_id." and `skillid` = ".$skill_id.";<br>";

				echo "REPLACE INTO `npcskills` VALUES (".$npc_id.",".$skill_id.",".$skill_level.");<br>";

				$k++;
			}
		}
	}
}

?>