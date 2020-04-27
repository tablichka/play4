<?php

$br="
";

print "Thinking...\n";

$query = "delete from zone;" . $br;
$query2 = "";

$fp = @fopen("areadata.txt", "r");
$fp1 = @fopen("territory.sql", "w");
$fp2 = @fopen("territory_loc.sql", "w");

$loc_id = 40000;
$id = 0;

while(!feof($fp))
{
	$data = @fgets($fp, 1000);
	$data = str_replace(" = ", "=", $data);
	$data = str_replace("	", " ", $data);
	$string = explode(" ", $data);

	if($string[0] != "area_begin")
		continue;

	unset($sk);
	
	for($i = 0; $i < count($string); $i++)
	{
		$string2 = explode("=", $string[$i]);
		if(count($string2) == 2)
			$sk[$string2[0]] = $string2[1];
	}

	$name=$sk[name];
	$type = $sk[type];
	$loc_id++;
	$id++;
	$target = $sk[target];
	$skill_name = $sk[skill_name];
	$skill_prob = $sk[skill_prob];
	$unit_tick = $sk[unit_tick];
	$default_status = $sk[default_status];
	$initial_delay = $sk[initial_delay];
	$on_time = $sk[on_time];
	$off_time = $sk[off_time];
	$random_time = $sk[random_time];
	$skill_action_type = $sk[skill_action_type];
	$restart_time = $sk[restart_time];
	$restart_allowed_time = $sk[restart_allowed_time];
	$affect_race = $sk[affect_race];
	$move_bonus = $sk[move_bonus];
	$hp_regen_bonus = $sk[hp_regen_bonus];
	$mp_regen_bonus = $sk[mp_regen_bonus];
	$damage_on_hp = $sk[damage_on_hp];
	$damage_on_mp = $sk[damage_on_mp];
	$message_no = $sk[message_no];
	$entering_message_no = $sk[entering_message_no];
	$leaving_message_no = $sk[leaving_message_no];
	$exp_penalty_per = $sk[exp_penalty_per];
	$item_drop = $sk[item_drop];
	$blocked_actions = $sk[blocked_actions];
	$event_id = $sk[event_id];

	$query = $query . "insert into zone set id=$id, name='$name', type='$type', loc1_id=$loc_id, loc2_id=null, restart_point=null, target='$target', skill_name='$skill_name', skill_prob='$skill_prob', unit_tick='$unit_tick', default_status='$default_status', initial_delay='$initial_delay', on_time='$on_time', off_time='$off_time', random_time='$random_time', skill_action_type='$skill_action_type', restart_time='$restart_time', restart_allowed_time='$restart_allowed_time', affect_race='$affect_race', move_bonus='$move_bonus', hp_regen_bonus='$hp_regen_bonus', mp_regen_bonus='$mp_regen_bonus', damage_on_hp='$damage_on_hp', damage_on_mp='$damage_on_mp', message_no='$message_no', entering_message_no='$entering_message_no', leaving_message_no='$leaving_message_no', exp_penalty_per='$exp_penalty_per', item_drop='$item_drop', blocked_actions='$blocked_actions', event_id='$event_id';" . $br;

	$range = $sk[range];

	$range = str_replace("{{", "", $range);
	$range = str_replace("}}", "", $range);

	$string2 = explode("};{", $range);
	
	$count = count($string2);
	for($k=0; $k<$count; $k++)
	{
		$string3 = explode(";", $string2[$k]);

		$loc_x = $string3[0];
		$loc_y = $string3[1];
		$loc_zmin = $string3[2];
		$loc_zmax = $string3[3];

		$query2 = $query2 . "insert into locations set loc_id=$loc_id, name='$name', loc_x=$loc_x, loc_y=$loc_y, loc_zmin=$loc_zmin, loc_zmax=$loc_zmax;" . $br;
	}
}

fputs($fp1,$query);
fputs($fp2,$query2);

?>