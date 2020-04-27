#0 dchar 1gchar 2qdrop 3qget 4id
# (C) Saboteur
# Perl script for searching items, duped by bug with pets
# run script in the folder with item's log files *.txt
# then look result.log
# remember, that all in the result.log are dupes, but some dupes
# are not special (food for pets and may be other may be occasion)

open (OUT,">>result.log");
foreach $file (`dir /b *.txt`)
{
  chomp $file;

  print "file $file\n";
#  print OUT "file $file\n";
  open (FILE, $file);

  foreach $line (<FILE>)
  {
    chomp $line;
    if (length($line) >10)
    {
      ($dat,$char,$act,$itemid,$q,$obj) = split('\|',$line);
      $dat=substr $line,1,17; $q=$q+0; $itemid=$itemid+0;
      if ($q > 3 && $char ne "null")
      {
          if ($act eq "Get item")
          {
            $id=$obj - 268000000;
            if ($id<0)
            {
             print "$id error!!! $obj $act $char $dat\n";
             $id = 0;
            }
            if ($i[$id][3] == $q && $i[$id][4]==$itemid && $i[$id][0] eq $char && $i[$id][1] eq $char)
            {
              print OUT "$dat	$char	$itemid	$q	$obj ($i[$id][0] drop $i[$id][2] items)\n";
            }
            $i[$id][1]=$char;
            $i[$id][3]=$q;
            $i[$id][4]=$itemid;
          }
          if ($act eq "Drop item")
          {
            $id=$obj - 268000000;
            $i[$id][0]=$char;
            $i[$id][1]="empty";
            $i[$id][2]=$q;
            $i[$id][3]=0;
            $i[$id][4]=$itemid;
          }
       }
    }
  }
  close FILE;
}
close OUT;
