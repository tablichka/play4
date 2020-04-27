<?php

$br="
";

print "Thinking...\n";

$query = "delete from zone;" . $br;
$query2 = "";

$fp = @fopen("castledata.txt", "r");
$fp1 = @fopen("zone.sql", "w");
$fp2 = @fopen("zone_loc.sql", "w");

$loc1_id = 42000;
$loc2_id = 43000;
$loc3_id = 44000;
$restart_point = 45000;
$id = 2000;

while(!feof($fp))
{
	unset($sk);
	
	$data = @fgets($fp, 1000);
	
	if(substr($data, 0, 10) != "agit_begin")
		continue;
	
	$data = @fgets($fp, 1000);
	$data = str_replace("\r\n", "", $data);	
	$name = $data;
		
	$data = @fgets($fp, 1000);
	$data = str_replace("\r\n", "", $data);	
	$index = $data;
	
	$data = @fgets($fp, 1000);
	$data = str_replace("\r\n", "", $data);	
	$type = $data;
		
	while(1)
	{
		$data = @fgets($fp, 1000);
		
		if(substr($data, 0, 8) == "agit_end")
		{
			$loc1_id_list = explode("};{", $sk[residence_territory]);
			$loc2_id_list = explode("};{", $sk[battlefield_territory]);
			$loc3_id_list = explode("};{", $sk[headquarter_territory]);
			$restart_point_list = explode("};{", $sk[owner_restart_point_list]);
			
			$id++;
			$loc1_id++;
			$loc2_id++;
			$loc3_id++;
			$restart_point++;
			$loc11_id=$loc1_id;
			$loc12_id=$loc2_id;
			$loc13_id=$loc3_id;
			$restart_point1=$restart_point;
			
			$count = count($loc1_id_list);
			for($k=0; $k<$count; $k++)
			{
				$string = explode(";", $loc1_id_list[$k]);

				$loc_x = $string[0];
				$loc_y = $string[1];
				$loc_zmin = $string[2];
				$loc_zmax = $string[3];

				if($loc_x != "")
					$query2 = $query2 . "insert into locations set loc_id=$loc1_id, name='$name', loc_x=$loc_x, loc_y=$loc_y, loc_zmin=$loc_zmin, loc_zmax=$loc_zmax;" . $br;
				else
					$loc11_id = 0;
			}

			$count = count($loc2_id_list);
			for($k=0; $k<$count; $k++)
			{
				$string = explode(";", $loc2_id_list[$k]);

				$loc_x = $string[0];
				$loc_y = $string[1];
				$loc_zmin = $string[2];
				$loc_zmax = $string[3];

				if($loc_x != "")
					$query2 = $query2 . "insert into locations set loc_id=$loc2_id, name='$name', loc_x=$loc_x, loc_y=$loc_y, loc_zmin=$loc_zmin, loc_zmax=$loc_zmax;" . $br;
				else
					$loc12_id = 0;
			}
			
			$count = count($loc3_id_list);
			for($k=0; $k<$count; $k++)
			{
				$string3 = explode(";", $loc3_id_list[$k]);

				$loc_x = $string[0];
				$loc_y = $string[1];
				$loc_zmin = $string[2];
				$loc_zmax = $string[3];

				if($loc_x != "")
					$query2 = $query2 . "insert into locations set loc_id=$loc3_id, name='$name', loc_x=$loc_x, loc_y=$loc_y, loc_zmin=$loc_zmin, loc_zmax=$loc_zmax;" . $br;
				else
					$loc13_id = 0;
			}
			
			$count = count($restart_point_list);
			for($k=0; $k<$count; $k++)
			{
				$string = explode(";", $restart_point_list[$k]);

				$loc_x = $string[0];
				$loc_y = $string[1];
				$loc_zmin = $string[2];
				$loc_zmax = $string[3];

				if($loc_x != "")
					$query2 = $query2 . "insert into locations set loc_id=$restart_point, name='$name', loc_x=$loc_x, loc_y=$loc_y, loc_zmin=$loc_zmin;" . $br;
				else
					$restart_point1 = 0;
			}

			$query = $query . "insert into zone set id=$id, `index`=$index, name='$name', type='$type', loc1_id=$loc11_id, loc2_id=$loc12_id, loc3_id=$loc13_id, restart_point=$restart_point1;" . $br;
			
			break;
		}
		
		$data = str_replace("\r\n", "", $data);
		$data = str_replace(" = ", "=", $data);
		$data = str_replace("	", " ", $data);
		$data = str_replace("{{{", "", $data);
		$data = str_replace("{{", "", $data);
		$data = str_replace("}}}", "", $data);
		$data = str_replace("}}", "", $data);
		$data = str_replace("{}", "", $data);
		$string = explode("=", $data);
		
		if(count($string) == 2)
			$sk[$string[0]] = $string[1];
	}
	

}

fputs($fp1,$query);
fputs($fp2,$query2);

?>