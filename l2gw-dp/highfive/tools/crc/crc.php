<?php
if ($handle = opendir('.')) {
	while (false !== ($file = readdir($handle))) {
		if ($file != "." && $file != "..") {
			echo md5_file($file)." * ".$file."\n";
		}
	}
	closedir($handle);
}
?>