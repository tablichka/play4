#!/usr/bin/perl

# (C) Mirosya, Saboteur
# Convert l2j_Fortress to C4Retail server
# Required :
# * perl+DBI+DBI-mysql
# * add column password2 into accounts (on java srv) with C4_encrypted password
# * empty password will be set to 'root'
# * runsql.bat - command file that executes sql file in console mode.
# * runsqk.bat example - "@isql -n -E -i %1"
# * modified procedures CreateChar_lj and CreateItem_lj (file l2j2off.proc)

use DBI;

# Tables to convert (true=1,false=0)
$convert_accounts = 1;		# clear all accounts, creating new
$update_passwords = 0;		# updating passwords for exist accounts
$convert_characters = 1;	# clear all chars, create new chars
				#   only base subclass
$convert_clan_data = 1;		# clear clans, create new
$convert_items = 1;		# clear items from chars, create new
				#   converted items will be put to
				#   the char's private warehouse
$convert_clan_items = 1;	# clear clan's warehouses, creating items
$convert_skills = 1;		# clear skills, create new (base subclass)
$convert_hennas = 1;		# clear hennas, create new (base subclass)
$convert_recipebook = 1;	# clear recipebooks, create new
$convert_subclasses = 1;	# clear subclasses, create new
				#   create up to 3 subclasses
				#   fill subclasses hennas
				#   create skills for subclasses
$convert_3proof = 1;		# convert all 3proof to 2proof
				#   remove 3proof skills (with restoring SP)
$convert_email = 1;		# converting emails
				#   create ssn, user_info

# mysql
$host='localhost';
$user='root';
$password='';
$database='l2jsabo';
$port=3306;

$connstring="DBI:mysql:database=$database;host=$host;port=$port";

# pet's hashmap
%pet_npcids = (
'2375' => '1012077' , # wolf
'3500' => '1012311' , # hatchling of wind
'3501' => '1012312' , # hatchling of star
'3502' => '1012313' , # hatchling of twilight
'4422' => '1012526' , # wind strider
'4423' => '1012527' , # Star strider
'4424' => '1012528' , # Twilight strider
'6648' => '1012780' , # Baby Buffalo
'6650' => '1012781' , # Baby Kookaburra
'6649' => '1012782'   # Baby Cougar
);

$error = "open log file";
open(LOG,">>l2j2off.log") or &fatal_error;
$error = "open output file";
open(BAT,">sql/apply.bat") or &fatal_error;

print "\n\t === l2j to c4off database converter ===\n\n";

# current_timer
&local_time;

&checking_db;
print " Linking to database .. ok.\n";

# load data from l2j database
&l2j_parse;
print " Data from l2j parsed .. ok.\n\n";

close(LOG);
close(BAT);
exit;

sub local_time
{
  ($sec,$min,$hour,$mday,$mon,$year,$wday,$yday,$isdst) = localtime(time);
  # fix month
  $mon++;
  #debug
  #$mday = 1;
  if ($mday > 9) {$curr_day = $mday} else {$curr_day = "0".$mday}
  if ($mon  > 9) {$curr_mon = $mon } else {$curr_mon = "0".$mon }
  $curr_year = $year + 1900;
  if ($hour > 9) {$curr_hour = $hour} else {$curr_hour = "0".$hour}
  if ($min > 9) {$curr_min = $min} else {$curr_min = "0".$min}
  if ($sec > 9) {$curr_sec = $sec} else {$curr_sec = "0".$sec}

  $currtime = "$curr_hour:$curr_min $curr_day.$curr_mon.$curr_year";
  $firstmonday =$curr_year.$curr_mon."01";

  if($mon eq 1) {$last_year = $curr_year-1; $last_mon = "12";}
  else 
  {
    $last_year = $curr_year;
    $tmon = $mon-1;
    if ($tmon  > 9) {$last_mon = $tmon } else {$last_mon = "0".$tmon }
  }

  $lastmonday=$last_year.$last_mon."01";
} 

sub fatal_error
{
  print "$currtime $logo Error: $error.\n";
  exit;
}

sub local_error
{
  print LOG "$currtime $logo Error: $error.\n";
  exit;
}

sub checking_db
{
  $dbh = DBI->connect($connstring, $user, $password);
  if($dbh eq '') {print "\tError connection to database.\n\n";exit;}
  $drh = DBI->install_driver("mysql");
  @databases = DBI->data_sources("mysql");

  # checking summary db
  my $sth = $dbh->prepare("show tables");
  if (!$sth->execute) {
    die "Error:" . $sth->errstr . "\n";
  }
  $sth->finish();

  # Disconnect from the database.
  $dbh->disconnect();
}

sub l2j_parse
{
  $dbh = DBI->connect($connstring, $user, $password);
  if($dbh eq '') {print "\tError connection to database.\n\n";exit;}           

  $drh = DBI->install_driver("mysql");
  @databases = DBI->data_sources("mysql");
  print " Connected to database $database at $host\n";

  # --- accounts table ---
  if ($convert_accounts == 1 )
  {
    print " Converting accounts..\n";
    open(MSSQL,">sql/Accounts.sql") or &fatal_error;
    print BAT "\@echo call runsql Accounts.sql >> Accounts.log\n";
    print BAT "call runsql Accounts.sql >> Accounts.log\n";
    $query = "SELECT login, password, access_level, comments, email, password2
	FROM accounts;";

    my $sth = $dbh->prepare($query);
    if (!$sth->execute) { die "Error:" . $sth->errstr . "\n"; }

    $sth->bind_columns(\$login, \$password, \$access_level, \$comments, \$email, \$password2);

    print MSSQL "USE lin2db;\n";
    print MSSQL "delete from user_auth;\n";
    print MSSQL "delete from user_account;\n";
    while($sth->fetch())
    {
      $login=~ s/'/-/g;
      $login=~ s/"/=/g;
      # if no encrypted off-like $password2, set to 'root'
      if ($password2 eq "") { $password2 = "0xb1be70e9a83f19192cb593935ec4e2e2"; }

      if (length($login) > 13)
      { 
	print "->$login<- is very long name.\n";
	next; 
      }
      print MSSQL "EXEC dbo.lin_CreateAccount '$login',$password2,'q1','q2','a1','a2','name','none','none','$email';\n";
    }
    $sth->finish();
    print MSSQL "UPDATE user_account SET pay_stat = '1';\n\n";
    close MSSQL
  }

  # --- update account passwords ---
  if ($update_passwords == 1 )
  {
    open(MSSQL,">sql/Passwords.sql") or &fatal_error;
    print BAT "\@echo call runsql Passwords.sql >> Passwords.log\n";
    print BAT "call runsql Passwords.sql >> Passwords.log\n";
    print " Converting passwords..\n";
    $query = "SELECT login, password2 FROM accounts;";

    my $sth = $dbh->prepare($query);
    if (!$sth->execute) {
      die "Error:" . $sth->errstr . "\n";
    }
    $sth->bind_columns(\$login, \$password2);

    print MSSQL "USE lin2db;\n";
    while($sth->fetch())
    {
	$login=~ s/'/-/g;
	$login=~ s/"/=/g;
	if ($password2 ne "")
	{
	  print MSSQL "update user_auth set password = $password2 where account = '$login';\n";
	}
    }
    $sth->finish();
    close MSSQL;
  }

  # --- characters table ---
  if ($convert_characters == 1)
  {
    print " Converting users..\n";
    $query = "SELECT
	a.account_name, a.obj_Id, a.char_name, a.face, a.hairStyle, a.hairColor, a.sex,
	a.heading, a.x, a.y, a.z, a.karma, a.pvpkills, a.pkkills, a.clanid, a.deletetime, a.title,
	a.allyId, a.rec_have, a.rec_left, a.accesslevel, a.online, a.onlinetime, a.lastAccess,
	a.clan_privs, a.wantspeace, a.deleteclan, a.nochannel, a.noble, a.ketra, a.varka,
	a.equiped_with_zariche, a.zariche_pk, a.zariche_time,
	a.Pledge_class, a.pledge_type, a.pledge_rank, a.apprentice, a.ram,
	b.class_id, b.level, b.exp, b.sp, b.maxHp, b.maxMp, b.maxCp,
	c.RaceId

	FROM characters a, character_subclasses b, char_templates c
	where a.obj_Id = b.char_obj_id and b.isBase = 1 and b.class_id = c.ClassId;";
    my $sth = $dbh->prepare($query);
    if (!$sth->execute) { die "Error:" . $sth->errstr . "\n"; }
    $sth->bind_columns(
        \$account_name, \$obj_Id, \$char_name, \$face, \$hairStyle, \$hairColor, \$sex,
        \$heading, \$x, \$y, \$z, \$karma, \$pvpkills, \$pkkills, \$clanid, \$deletetime, \$title,
        \$allyId, \$rec_have, \$rec_left, \$accesslevel, \$online, \$onlinetime, \$lastAccess,
        \$clan_privs, \$wantspeace, \$deleteclan, \$nochannel, \$noble, \$ketra, \$varka,
        \$equiped_with_zariche, \$zariche_pk, \$zariche_time,
        \$Pledge_class, \$pledge_type, \$pledge_rank, \$apprentice, \$ram,
	\$class_id, \$level, \$exp, \$sp, \$maxHp, \$maxMp, \$maxCp,
	\$race
    );

    $cc=0;
    $c=0;
    open(MSSQL,">sql/Characters$c.sql") or &fatal_error;
    print BAT "\@echo call runsql Characters$c.sql >> Characters$c.log\n";
    print BAT "call runsql Characters$c.sql >> Characters$c.log\n";
    print MSSQL "USE lin2world;\n";
    print MSSQL "declare \@char_ident int;\n";
    print MSSQL "declare \@account_ident int;\n";
    print MSSQL "delete from user_data;\n";
    print MSSQL "delete from quest;\n";
    print MSSQL "delete from user_name_reserved;\n";
    while($sth->fetch())
    {
	$account_name=~ s/'/-/g;
	$account_name=~ s/"/=/g;
	$char_name=~ s/'/-/g;
	$char_name=~ s/"/=/g;

	if ($cc > 5000)
	{
	  close MSSQL;
	  $cc=0;
	  $c++;
	  open(MSSQL,">sql/Characters$c.sql") or &fatal_error;
	  print BAT "\@echo call runsql Characters$c.sql >> Characters$c.log\n";
	  print BAT "call runsql Characters$c.sql >> Characters$c.log\n";
	  print MSSQL "USE lin2world;\n";
	  print MSSQL "declare \@char_ident int;\n";
	  print MSSQL "declare \@account_ident int;\n";
	}
	print MSSQL "set \@account_ident = (select uid from lin2db.dbo.user_account where account = '$account_name');\n";
	print MSSQL "exec dbo.lin_CreateChar '$char_name','$account_name',\@account_ident,0,0,$sex,$race,$class_id,1,$x,$y,$z,$maxHp,$maxMp,$sp,$exp,$level,0,$pkkills,0,$pvpkills,$hairStyle,$hairColor,$face;\n";
	print MSSQL "update user_data set pledge_id = '$clanid' where char_name = '$char_name';\n";
	$cc=$cc+3;
    }
    $sth->finish();
    close MSSQL
  }

  # --- clan_data table ---
  if ($convert_clan_data == 1 )
  {
    open(MSSQL,">sql/Clan_data.sql") or &fatal_error;
    print BAT "\@echo call runsql Clan_data.sql >> Clan_data.log\n";
    print BAT "call runsql Clan_data.sql >> Clan_data.log\n";
    print " Converting clans..\n";
    $query = "SELECT clan_id, clan_name, clan_level, hasHideout, leader_id, c.char_name
	FROM clan_data left join characters c on leader_id = c.obj_id;";

    my $sth = $dbh->prepare($query);
    if (!$sth->execute) { die "Error:" . $sth->errstr . "\n"; }
    $sth->bind_columns(\$clan_id, \$clan_name, \$clan_level, \$hasHideout, \$leader_id, \$leader_name);

    print MSSQL "USE lin2world;\n";
    print MSSQL "delete from Pledge;\n";
    print MSSQL "declare \@leader_id int;\n";
    print MSSQL "declare \@pledge_id int;\n\n";

    while($sth->fetch())
    {
	$clan_name=~ s/'/-/g;
	$clan_name=~ s/"/=/g;
	$leader_name=~ s/'/-/g;
	$leader_name=~ s/"/=/g;
	print MSSQL "set \@leader_id = (select char_id from dbo.user_data where char_name='$leader_name');\n";
	print MSSQL "EXEC dbo.lin_CreatePledge '$clan_name', \@leader_id;\n";
	print MSSQL "set \@pledge_id = (select pledge_id from dbo.Pledge where name = '$clan_name');\n";
	print MSSQL "update Pledge set skill_level = '$clan_level' where pledge_id = \@pledge_id;\n";
	print MSSQL "update user_data set pledge_id = \@pledge_id where pledge_id = '$clan_id';\n";
    }
    $sth->finish();
    close MSSQL;
  }

  # --- items table ---
  if ( $convert_items == 1)
  {
    $pets_summary=0;
    $d="00";
    $cc=0;
    $ic=0;
    $char_name_old="";

    # collect pet's itemid
    my @pet_itemids="";

    $query = "select distinct(item_id) from items where object_id in (select item_obj_id from pets);";
    my $sth = $dbh->prepare($query);
    if (!$sth->execute) { die "Error:" . $sth->errstr . "\n"; }
    $sth->bind_columns( \$pet_itemid );
    while($sth->fetch()) { push @pet_itemids, $pet_itemid; }
    $sth->finish();

    open (MSSQL,">sql/C4_items$d$cc.sql");
    print BAT "\@echo call runsql C4_items$d$cc.sql >> C4_items$d$cc.log\n";
    print BAT "call runsql C4_items$d$cc.sql >> C4_items$d$cc.log\n";

    print MSSQL "USE lin2world;\n";
    print MSSQL "delete from user_item where warehouse <> 2;\n";
    print MSSQL "delete from pet_data;\n\n";
    print MSSQL "declare \@char_id int;\n";
    print MSSQL "declare \@item_id int;\n\n";

    print " Converting items..\n";
    $query = "SELECT
	b.char_name, a.item_id, a.count, a.enchant_level, a.loc, a.loc_data, a.object_id
        FROM items a,characters b WHERE a.owner_id = b.obj_id and a.loc <> 'CLANWH'
	order by b.char_name;";
    my $sth = $dbh->prepare($query);
    if (!$sth->execute) {
      die "Error:" . $sth->errstr . "\n";
    }
    $sth->bind_columns( \$char_name, \$item_id, \$count, \$enchant_level, \$loc, \$loc_data, \$object_id );

    while($sth->fetch())
    {
	$char_name=~ s/'/-/g;
	$char_name=~ s/"/=/g;

	if ($char_name_old ne $char_name)
	{
	  if ( $ic > 5000 )
	  {
	    $ic = 0;
	    $cc++;
	    if ( $cc == 10 ) { $d="0"; }
	    if ( $cc == 100 ) { $d=""; }
	    close MSSQL;
	    open (MSSQL,">sql/C4_items$d$cc.sql");
	    print MSSQL "USE lin2world;\n";
	    print MSSQL "declare \@char_id int;\n";
	    print MSSQL "declare \@item_id int;\n\n";
	    print BAT "\@echo call runsql C4_items$d$cc.sql >> C4_items$d$cc.log\n";
	    print BAT "call runsql C4_items$d$cc.sql >> C4_items$d$cc.log\n";
	  }
	  $ic++;
	  print MSSQL "select \@char_id=char_id from user_data where char_name='$char_name';\n";
	}
	print MSSQL "EXEC dbo.lin_CreateItem_lj \@char_id, $item_id, $count, $enchant_level, 0, 0, 0, 0, 1, \@item_id OUTPUT;\n";
	foreach $pet_itemid (@pet_itemids)
	{
	  if($pet_itemid eq $item_id)
	  {
	    $query = "SELECT exp, name, sp from pets where item_obj_id = $object_id;";
	    my $sth2 = $dbh->prepare($query);
	    if (!$sth2->execute) { die "Error:" . $sth2->errstr . "\n"; }
	    $sth2->bind_columns( \$pet_exp, \$pet_name, \$pet_sp );

	    while($sth2->fetch())
	    {
		$ic+=2;
		print MSSQL "EXEC dbo.lin_CreatePet \@item_id, $pet_npcids{$pet_itemid}, $pet_exp, 100, 100, 100;\n";
		print MSSQL "update pet_data set nick_name = '$pet_name', sp = $pet_sp where pet_id = \@item_id;\n";
		$pets_summary++;
	    }
	    if($pet_npcids{$pet_itemid} eq "") { print " !!!WARNING!!! Unknown pet's itemid -> $pet_itemid.\n"; }
	    last;
	  }
	}
	$ic++;
	$char_name_old = $char_name;
    }
    $sth->finish();
    close MSSQL;
    print "  Created $pets_summary pets\n";
  }

  # --- clan items table ---
  if ( $convert_clan_items == 1)
  {
    $clan_name_old="";
    $c=0;
    $cc=0;
    $pets_summary=0;
    # collect pet's itemid
    my @pet_itemids;

    $query = "select distinct(item_id) from items where object_id in (select item_obj_id from pets);";
    my $sth = $dbh->prepare($query);
    if (!$sth->execute) { die "Error:" . $sth->errstr . "\n"; }
    $sth->bind_columns( \$pet_itemid );
    while($sth->fetch()) { push @pet_itemids, $pet_itemid; }
    $sth->finish();

    print " Converting clan's items..\n";
    $query = "SELECT
        b.clan_name, a.item_id, a.count, a.enchant_level, a.loc, a.loc_data, a.object_id
        FROM items a,clan_data b WHERE a.owner_id = b.clan_id and a.loc = 'CLANWH'
	order by b.clan_name;";
    my $sth = $dbh->prepare($query);
    if (!$sth->execute) { die "Error:" . $sth->errstr . "\n"; }
    $sth->bind_columns( \$clan_name, \$item_id, \$count, \$enchant_level, \$loc, \$loc_data, \$object_id );

    open(MSSQL,">sql/Clan_items$c.sql") or &fatal_error;
    print BAT "\@echo call runsql Clan_items$c.sql >> Clan_items$c.log\n";
    print BAT "call runsql Clan_items$c.sql >> Clan_items$c.log\n";
    print MSSQL "USE lin2world;\n";
    print MSSQL "delete from user_item where warehouse = 2;\n";
    print MSSQL "declare \@item_id int;\n";
    print MSSQL "declare \@clan_id int;\n\n";

    while($sth->fetch())
    {
        $clan_name=~ s/'/-/g;
        $clan_name=~ s/"/=/g;
	if ($clan_name ne $clan_name_old)
	{
	  $cc++;
	  if ($cc > 5000)
	  {
	    $c++;
	    $cc=0;
	    close MSSQL;
	    open(MSSQL,">sql/Clan_items$c.sql") or &fatal_error;
	    print MSSQL "USE lin2world;\n";
	    print MSSQL "declare \@item_id int;\n";
	    print MSSQL "declare \@clan_id int;\n\n";
	    print BAT "\@echo call runsql Clan_items$c.sql >> Clan_items$c.log\n";
	    print BAT "call runsql Clan_items$c.sql >> Clan_items$c.log\n";
	  }
	  print MSSQL "select \@clan_id=pledge_id from pledge where name='$clan_name';\n";
	}
	print MSSQL "EXEC dbo.lin_CreateItem_lj \@clan_id, $item_id, $count, $enchant_level, 0, 0, 0, 0, 2, \@item_id OUTPUT;\n";
	foreach $pet_itemid (@pet_itemids)
	{
		if($pet_itemid eq $item_id)
		{
			$query = "SELECT exp, name, sp from pets where item_obj_id = $object_id;";
			my $sth2 = $dbh->prepare($query);
			if (!$sth2->execute) { die "Error:" . $sth2->errstr . "\n"; }
			$sth2->bind_columns( \$pet_exp, \$pet_name, \$pet_sp );

			while($sth2->fetch())
			{
			  $cc+=2;
			  print MSSQL "EXEC dbo.lin_CreatePet \@item_id, $pet_npcids{$pet_itemid}, $pet_exp, 10, 10, 0;\n";
			  print MSSQL "update pet_data set nick_name = '$pet_name', sp = $pet_sp where pet_id = \@item_id;\n";
			  $pets_summary++;
			}
			if($pet_npcids{$pet_itemid} eq "") { print " !!!WARNING!!! Unknown pet's itemid -> $pet_itemid.\n"; }
			last;
		}
	}
	$cc++;
	$clan_name_old = $clan_name;
    }
    $sth->finish();
    close MSQQL;
    print "  Created $pets_summary pets\n";
  }

  # --- character_skills table---
  if ( $convert_skills == 1)
  {
    $d="0";
    $c=0;
    $char_name_old = "";
    $count=0;
    open(MSSQL,">sql/Skills$d$c.sql") or &fatal_error;
    print BAT "\@echo call runsql Skills$d$c.sql >> Skills$d$c.log\n";
    print BAT "call runsql Skills$d$c.sql >> Skills$d$c.log\n";
    print " Converting skills..\n";
    $query = "Select c.char_name, s.skill_id, s.skill_level
	From characters c left Join character_skills s ON c.obj_Id = s.char_obj_id left Join character_subclasses u ON c.obj_Id = u.char_obj_id
	where u.isBase = '1' and s.class_index=u.class_id
	order by c.char_name;";
    my $sth = $dbh->prepare($query);
    if (!$sth->execute) { die "Error:" . $sth->errstr . "\n"; }
    $sth->bind_columns(	\$char_name, \$skill_id, \$skill_lvl );

    print MSSQL "USE lin2world;\n";
    print MSSQL "delete from user_skill;\n";
    print MSSQL "declare \@char_id int;\n\n";
    while($sth->fetch())
    {
        $char_name=~ s/'/-/g;
        $char_name=~ s/"/=/g;
        if ($char_name ne $char_name_old)
        {
          $count++;
	  if ($count > 5000)
	  {
	    $count=0;
	    close MSSQL;
	    $c++;
	    if ($c >9) {$d="";}
	    open(MSSQL,">sql/Skills$d$c.sql") or &fatal_error;
	    print BAT "\@echo call runsql Skills$d$c.sql >> Skills$d$c.log\n";
	    print BAT "call runsql Skills$d$c.sql >> Skills$d$c.log\n";
	    print MSSQL "USE lin2world;\n";
	    print MSSQL "declare \@char_id int;\n\n";
	  }
          print MSSQL "select \@char_id = char_id from user_data where char_name = '$char_name';\n";
        }
	print MSSQL "insert into user_skill values (\@char_id, $skill_id, $skill_lvl, 0, 0);\n";
        $count++;
        $char_name_old = $char_name;
    }
    $sth->finish();
    close MSSQL;
  }

  # --- character_hennas tablet---
  if ( $convert_hennas == 1)
  {
    open(MSSQL,">sql/Hennas.sql") or &fatal_error;
    print BAT "\@echo call runsql Hennas.sql >> Hennas.log\n";
    print BAT "call runsql Hennas.sql >> Hennas.log\n";
    print " Converting hennas..\n";
    $query = "Select h.symbol_id, h.slot, c.char_name
		From character_hennas h Inner Join characters c ON h.char_obj_id = c.obj_Id Inner Join character_subclasses u ON h.char_obj_id = u.char_obj_id
		Where u.isbase = 1 and u.class_id = h.class_index
		order by h.char_obj_id, h.slot;";

    my $sth = $dbh->prepare($query);
    if (!$sth->execute) { die "Error:" . $sth->errstr . "\n"; }
    $sth->bind_columns( \$henna_id, \$henna_slot, \$char_name );

    $char_name_old = "";
    print MSSQL "USE lin2world;\n";
    print MSSQL "delete from user_henna;\n";
    print MSSQL "declare \@char_id int;\n\n";
    while($sth->fetch())
    {
      $char_name=~ s/'/-/g;
      $char_name=~ s/"/=/g;
      if ($char_name ne $char_name_old)
      {
	print MSSQL "select \@char_id = char_id from user_data where char_name = '$char_name';\n";
      }

      if ( $henna_slot == '1')    { print MSSQL "insert into user_henna values (\@char_id, $henna_id,0,0,0);\n"; }
      elsif ( $henna_slot == '2') { print MSSQL "update user_henna set henna_2 = '$henna_id' where char_id = \@char_id;\n"; }
      elsif ( $henna_slot == '3') { print MSSQL "update user_henna set henna_3 = '$henna_id' where char_id = \@char_id;\n"; }
      $char_name_old = $char_name;
    }
    $sth->finish();
    close MSSQL;
  }

  # --- character_recipebook table ---
  if ( $convert_recipebook == 1)
  {
    open(MSSQL,">sql/RecipeBook.sql") or &fatal_error;
    print BAT "\@echo call runsql RecipeBook.sql >> RecipeBook.log\n";
    print BAT "call runsql RecipeBook.sql >> RecipeBook.log\n";
    print " Converting recipebook..\n";
    $query = "Select r.id, c.char_name
		From character_recipebook r Inner Join characters c ON r.char_id = c.obj_Id
		order by c.char_name;";
    my $sth = $dbh->prepare($query);
    if (!$sth->execute) { die "Error:" . $sth->errstr . "\n"; }
    $sth->bind_columns( \$recipe_id, \$char_name );

    $char_name_old = "";
    print MSSQL "USE lin2world;\n";
    print MSSQL "delete from user_recipe;\n";
    print MSSQL "declare \@char_id int;\n\n";
    while($sth->fetch())
    {
      $char_name=~ s/'/-/g;
      $char_name=~ s/"/=/g;
      if ($char_name ne $char_name_old)
      { print MSSQL "select \@char_id = char_id from user_data where char_name = '$char_name';\n"; }
      print MSSQL "insert into user_recipe values (\@char_id,$recipe_id);\n";

      $char_name_old = $char_name;
    }
    $sth->finish();
    close MSSQL;
  }

  # --- convert subclasses ---
  if ($convert_subclasses == 1 )
  {
    open(MSSQL,">sql/Subclasses.sql") or &fatal_error;
    print MSSQL "USE lin2world;\n";
    print MSSQL "delete from user_subjob;\n";
    print MSSQL "declare \@char_id int;\n";
    print BAT "\@echo call runsql Subclasses.sql >> Subclasses.log\n";
    print BAT "call runsql Subclasses.sql >> Subclasses.log\n";
    print " Converting subclasses..\n";
    $query = "Select u.class_id, u.level, u.exp, u.sp, u.maxHp, u.maxMp, u.maxCp, c.char_name, u.char_obj_id
	From character_subclasses u Inner Join characters c ON u.char_obj_id = c.obj_Id
	where u.isbase = 0
        order by u.char_obj_id;";

    my $sth = $dbh->prepare($query);
    if (!$sth->execute) { die "Error:" . $sth->errstr . "\n"; }
    $sth->bind_columns(\$class_id, \$level, \$exp, \$sp, \$Hp, \$Mp, \$Cp, \$char_name, \$char_id);

    $subjob=1;
    while($sth->fetch())
    {
	$char_name=~ s/'/-/g;
	$char_name=~ s/"/=/g;
	# initialize subjob number or increasing if char have more than one subclass
        if ($char_name eq $char_name_old) { $subjob++;}
        else { $subjob = 1; }
	print MSSQL "select \@char_id = char_id from user_data where char_name = '$char_name';\n";

	# --- getting hennas for current subclass begin
	$query_h = "Select symbol_id, slot from character_hennas 
		Where char_obj_id = $char_id and class_index = $class_id
		order by slot;";
	my $sth_henna = $dbh->prepare($query_h);
	if (!$sth_henna->execute) { die "Error:" . $sth->errstr . "\n"; }
	$sth_henna->bind_columns(\$henna_id, \$slot);
	$counter=1;
	$henna_1 = 0;
	$henna_2 = 0;
	$henna_3 = 0;
	while($sth_henna->fetch())
	{
	  if ( $counter == 1 ) { $henna_1= $henna_id; }	
	  if ( $counter == 2 ) { $henna_1= $henna_id; }	
	  if ( $counter == 3 ) { $henna_1= $henna_id; }	
	  $counter++;
	}
	$sth_henna->finish();
	# --- hennas for current subclass end

	# --- add subclass begin
	print MSSQL "INSERT INTO user_subjob	VALUES	(\@char_id, $Hp, $Mp, $sp, $exp, $level, $henna_1, $henna_2, $henna_3, $subjob, getdate());\n";
	if ( $subjob == 1) { print MSSQL "UPDATE user_data SET subjob1_class = $class_id where char_id = \@char_id;\n"; }
	if ( $subjob == 2) { print MSSQL "UPDATE user_data SET subjob2_class = $class_id where char_id = \@char_id;\n"; }
	if ( $subjob == 3) { print MSSQL "UPDATE user_data SET subjob3_class = $class_id where char_id = \@char_id;\n"; }
	# --- add subclass end

	# --- add skills for current subclass begin
	$query_s = "Select skill_id, skill_level from character_skills
		Where char_obj_id = $char_id and class_index = $class_id;";
	my $sth_skill = $dbh->prepare($query_s);
	if (!$sth_skill->execute) { die "Error:" . $sth->errstr . "\n"; }
	$sth_skill->bind_columns(\$skill_id, \$skill_lvl);
	while($sth_skill->fetch()) { print MSSQL "insert into user_skill values (\@char_id, $skill_id, $skill_lvl, 0, $subjob);\n"; }
	$sth_skill->finish();
	# --- add skills for current subclass end

	$char_name_old = $char_name;
    }
    $sth->finish();
    close MSSQL;
  }

  # --- 3 proof convert ---
  if ( $convert_3proof == 1)
  {
    open(MSSQL,">sql/3Proof.sql") or &fatal_error;
    print BAT "\@echo call runsql 3Proof.sql >> 3Proof.log\n";
    print BAT "call runsql 3Proof.sql >> 3Proof.log\n";
    print " Converting 3Proof..\n";
    $query = "Select c.char_name, s.skill_id, s.class_index
	From character_skills s left join characters c ON s.char_obj_id = c.obj_Id
	where s.skill_id in (328,329,330,334,335,336,337,338,339,340,341,342,343,344,345,346,347,348,349,350,351,352,353,354,355,356,357,358,359,360,361,362,363,364,365,366,367,368,369,1335,1336,1337,1338,1339,1340,1341,1342,1343,1344,1346,1347,1348,1349,1350,1351,1352,1353,1354,1355,1356,1357,1358,1359,1360,1361,1362,1363,1364,1365,1366,1367)
	order by c.char_name;";
    my $sth = $dbh->prepare($query);
    if (!$sth->execute) { die "Error:" . $sth->errstr . "\n"; }
    $sth->bind_columns( \$char_name, \$skill_id, \$class_id );
    $sum_sp=0;
    $char_name_old = "";
    print MSSQL "USE lin2world;\n";
    print MSSQL "declare \@char_id int;\n";
    print MSSQL "declare \@char_sp int;\n\n";
    while($sth->fetch())
    {
        $char_name=~ s/'/-/g;
        $char_name=~ s/"/=/g;
	if ($char_name ne $char_name_old)
	{
	  print MSSQL "select \@char_id = char_id, \@char_sp = sp from user_data where char_name = '$char_name_old';\n";
	  print MSSQL "update user_data set sp = \@char_sp + $sum_sp where char_name='$char_name_old';\n";
          $sum_sp=0;
	}
	$add_sp=0;
	if ($class_id==88 && $skill_id==328) { $addsp=12000000; }
	if ($class_id==88 && $skill_id==329) { $addsp=12000000; }
	if ($class_id==88 && $skill_id==330) { $addsp=15000000; }
	if ($class_id==88 && $skill_id==340) { $addsp=15000000; }
	if ($class_id==88 && $skill_id==345) { $addsp=32000000; }
	if ($class_id==88 && $skill_id==359) { $addsp=15000000; }
	if ($class_id==88 && $skill_id==360) { $addsp=32000000; }
	if ($class_id==89 && $skill_id==328) { $addsp=12000000; }
	if ($class_id==89 && $skill_id==329) { $addsp=12000000; }
	if ($class_id==89 && $skill_id==330) { $addsp=15000000; }
	if ($class_id==89 && $skill_id==339) { $addsp=21000000; }
	if ($class_id==89 && $skill_id==347) { $addsp=21000000; }
	if ($class_id==89 && $skill_id==359) { $addsp=15000000; }
	if ($class_id==89 && $skill_id==360) { $addsp=21000000; }
	if ($class_id==89 && $skill_id==361) { $addsp=15000000; }
	if ($class_id==90 && $skill_id==328) { $addsp=10000000; }
	if ($class_id==90 && $skill_id==329) { $addsp=10000000; }
	if ($class_id==90 && $skill_id==335) { $addsp=10000000; }
	if ($class_id==90 && $skill_id==341) { $addsp=32000000; }
	if ($class_id==90 && $skill_id==350) { $addsp=32000000; }
	if ($class_id==90 && $skill_id==353) { $addsp=20000000; }
	if ($class_id==90 && $skill_id==368) { $addsp=20000000; }
	if ($class_id==91 && $skill_id==328) { $addsp=10000000; }
	if ($class_id==91 && $skill_id==329) { $addsp=10000000; }
	if ($class_id==91 && $skill_id==335) { $addsp=10000000; }
	if ($class_id==91 && $skill_id==342) { $addsp=32000000; }
	if ($class_id==91 && $skill_id==350) { $addsp=32000000; }
	if ($class_id==91 && $skill_id==353) { $addsp=20000000; }
	if ($class_id==91 && $skill_id==368) { $addsp=20000000; }
	if ($class_id==92 && $skill_id==328) { $addsp=15000000; }
	if ($class_id==92 && $skill_id==330) { $addsp=20000000; }
	if ($class_id==92 && $skill_id==334) { $addsp=64000000; }
	if ($class_id==92 && $skill_id==343) { $addsp=15000000; }
	if ($class_id==92 && $skill_id==354) { $addsp=20000000; }
	if ($class_id==93 && $skill_id==328) { $addsp=15000000; }
	if ($class_id==93 && $skill_id==330) { $addsp=20000000; }
	if ($class_id==93 && $skill_id==334) { $addsp=21000000; }
	if ($class_id==93 && $skill_id==344) { $addsp=15000000; }
	if ($class_id==93 && $skill_id==356) { $addsp=21000000; }
	if ($class_id==93 && $skill_id==357) { $addsp=21000000; }
	if ($class_id==93 && $skill_id==358) { $addsp=20000000; }
	if ($class_id==94 && $skill_id==328) { $addsp=30000000; }
	if ($class_id==94 && $skill_id==337) { $addsp=32000000; }
	if ($class_id==94 && $skill_id==1338) { $addsp=32000000; }
	if ($class_id==94 && $skill_id==1339) { $addsp=20000000; }
	if ($class_id==95 && $skill_id==328) { $addsp=10000000; }
	if ($class_id==95 && $skill_id==329) { $addsp=10000000; }
	if ($class_id==95 && $skill_id==337) { $addsp=21000000; }
	if ($class_id==95 && $skill_id==1336) { $addsp=13000000; }
	if ($class_id==95 && $skill_id==1337) { $addsp=21000000; }
	if ($class_id==95 && $skill_id==1343) { $addsp=10000000; }
	if ($class_id==95 && $skill_id==1344) { $addsp=13000000; }
	if ($class_id==96 && $skill_id==328) { $addsp=12000000; }
	if ($class_id==96 && $skill_id==338) { $addsp=32000000; }
	if ($class_id==96 && $skill_id==1346) { $addsp=15000000; }
	if ($class_id==96 && $skill_id==1349) { $addsp=32000000; }
	if ($class_id==96 && $skill_id==1350) { $addsp=12000000; }
	if ($class_id==96 && $skill_id==1351) { $addsp=15000000; }
	if ($class_id==97 && $skill_id==328) { $addsp=10000000; }
	if ($class_id==97 && $skill_id==329) { $addsp=10000000; }
	if ($class_id==97 && $skill_id==336) { $addsp=32000000; }
	if ($class_id==97 && $skill_id==1335) { $addsp=10000000; }
	if ($class_id==97 && $skill_id==1353) { $addsp=13000000; }
	if ($class_id==97 && $skill_id==1360) { $addsp=13000000; }
	if ($class_id==97 && $skill_id==1361) { $addsp=32000000; }
	if ($class_id==98 && $skill_id==328) { $addsp=10000000; }
	if ($class_id==98 && $skill_id==329) { $addsp=10000000; }
	if ($class_id==98 && $skill_id==336) { $addsp=32000000; }
	if ($class_id==98 && $skill_id==1352) { $addsp=10000000; }
	if ($class_id==98 && $skill_id==1356) { $addsp=32000000; }
	if ($class_id==98 && $skill_id==1358) { $addsp=13000000; }
	if ($class_id==98 && $skill_id==1359) { $addsp=13000000; }
	if ($class_id==99 && $skill_id==328) { $addsp=10000000; }
	if ($class_id==99 && $skill_id==329) { $addsp=10000000; }
	if ($class_id==99 && $skill_id==335) { $addsp=10000000; }
	if ($class_id==99 && $skill_id==341) { $addsp=32000000; }
	if ($class_id==99 && $skill_id==351) { $addsp=32000000; }
	if ($class_id==99 && $skill_id==352) { $addsp=20000000; }
	if ($class_id==99 && $skill_id==368) { $addsp=20000000; }
	if ($class_id==100 && $skill_id==328) { $addsp=15000000; }
	if ($class_id==100 && $skill_id==329) { $addsp=15000000; }
	if ($class_id==100 && $skill_id==349) { $addsp=20000000; }
	if ($class_id==100 && $skill_id==363) { $addsp=20000000; }
	if ($class_id==100 && $skill_id==364) { $addsp=64000000; }
	if ($class_id==101 && $skill_id==328) { $addsp=15000000; }
	if ($class_id==101 && $skill_id==330) { $addsp=20000000; }
	if ($class_id==101 && $skill_id==334) { $addsp=21000000; }
	if ($class_id==101 && $skill_id==344) { $addsp=15000000; }
	if ($class_id==101 && $skill_id==355) { $addsp=21000000; }
	if ($class_id==101 && $skill_id==356) { $addsp=21000000; }
	if ($class_id==101 && $skill_id==358) { $addsp=20000000; }
	if ($class_id==102 && $skill_id==328) { $addsp=15000000; }
	if ($class_id==102 && $skill_id==330) { $addsp=20000000; }
	if ($class_id==102 && $skill_id==334) { $addsp=32000000; }
	if ($class_id==102 && $skill_id==343) { $addsp=15000000; }
	if ($class_id==102 && $skill_id==354) { $addsp=20000000; }
	if ($class_id==102 && $skill_id==369) { $addsp=32000000; }
	if ($class_id==103 && $skill_id==328) { $addsp=15000000; }
	if ($class_id==103 && $skill_id==337) { $addsp=32000000; }
	if ($class_id==103 && $skill_id==1338) { $addsp=32000000; }
	if ($class_id==103 && $skill_id==1340) { $addsp=20000000; }
	if ($class_id==103 && $skill_id==1342) { $addsp=15000000; }
	if ($class_id==104 && $skill_id==328) { $addsp=15000000; }
	if ($class_id==104 && $skill_id==338) { $addsp=32000000; }
	if ($class_id==104 && $skill_id==1347) { $addsp=20000000; }
	if ($class_id==104 && $skill_id==1349) { $addsp=32000000; }
	if ($class_id==104 && $skill_id==1350) { $addsp=15000000; }
	if ($class_id==105 && $skill_id==328) { $addsp=10000000; }
	if ($class_id==105 && $skill_id==329) { $addsp=10000000; }
	if ($class_id==105 && $skill_id==336) { $addsp=32000000; }
	if ($class_id==105 && $skill_id==1353) { $addsp=13000000; }
	if ($class_id==105 && $skill_id==1354) { $addsp=10000000; }
	if ($class_id==105 && $skill_id==1355) { $addsp=32000000; }
	if ($class_id==105 && $skill_id==1359) { $addsp=13000000; }
	if ($class_id==106 && $skill_id==328) { $addsp=10000000; }
	if ($class_id==106 && $skill_id==329) { $addsp=10000000; }
	if ($class_id==106 && $skill_id==335) { $addsp=10000000; }
	if ($class_id==106 && $skill_id==342) { $addsp=32000000; }
	if ($class_id==106 && $skill_id==351) { $addsp=32000000; }
	if ($class_id==106 && $skill_id==352) { $addsp=20000000; }
	if ($class_id==106 && $skill_id==368) { $addsp=20000000; }
	if ($class_id==107 && $skill_id==328) { $addsp=15000000; }
	if ($class_id==107 && $skill_id==329) { $addsp=15000000; }
	if ($class_id==107 && $skill_id==365) { $addsp=64000000; }
	if ($class_id==107 && $skill_id==366) { $addsp=20000000; }
	if ($class_id==107 && $skill_id==367) { $addsp=20000000; }
	if ($class_id==108 && $skill_id==328) { $addsp=15000000; }
	if ($class_id==108 && $skill_id==330) { $addsp=20000000; }
	if ($class_id==108 && $skill_id==334) { $addsp=21000000; }
	if ($class_id==108 && $skill_id==344) { $addsp=15000000; }
	if ($class_id==108 && $skill_id==355) { $addsp=21000000; }
	if ($class_id==108 && $skill_id==357) { $addsp=21000000; }
	if ($class_id==108 && $skill_id==358) { $addsp=20000000; }
	if ($class_id==109 && $skill_id==328) { $addsp=15000000; }
	if ($class_id==109 && $skill_id==330) { $addsp=20000000; }
	if ($class_id==109 && $skill_id==334) { $addsp=32000000; }
	if ($class_id==109 && $skill_id==343) { $addsp=15000000; }
	if ($class_id==109 && $skill_id==354) { $addsp=20000000; }
	if ($class_id==109 && $skill_id==369) { $addsp=32000000; }
	if ($class_id==110 && $skill_id==328) { $addsp=10000000; }
	if ($class_id==110 && $skill_id==329) { $addsp=10000000; }
	if ($class_id==110 && $skill_id==330) { $addsp=20000000; }
	if ($class_id==110 && $skill_id==337) { $addsp=32000000; }
	if ($class_id==110 && $skill_id==1338) { $addsp=32000000; }
	if ($class_id==110 && $skill_id==1341) { $addsp=20000000; }
	if ($class_id==110 && $skill_id==1343) { $addsp=10000000; }
	if ($class_id==111 && $skill_id==328) { $addsp=12000000; }
	if ($class_id==111 && $skill_id==329) { $addsp=12000000; }
	if ($class_id==111 && $skill_id==330) { $addsp=15000000; }
	if ($class_id==111 && $skill_id==338) { $addsp=32000000; }
	if ($class_id==111 && $skill_id==1348) { $addsp=15000000; }
	if ($class_id==111 && $skill_id==1349) { $addsp=32000000; }
	if ($class_id==111 && $skill_id==1351) { $addsp=15000000; }
	if ($class_id==112 && $skill_id==328) { $addsp=10000000; }
	if ($class_id==112 && $skill_id==329) { $addsp=10000000; }
	if ($class_id==112 && $skill_id==330) { $addsp=20000000; }
	if ($class_id==112 && $skill_id==336) { $addsp=32000000; }
	if ($class_id==112 && $skill_id==1354) { $addsp=10000000; }
	if ($class_id==112 && $skill_id==1357) { $addsp=32000000; }
	if ($class_id==112 && $skill_id==1358) { $addsp=20000000; }
	if ($class_id==113 && $skill_id==328) { $addsp=10000000; }
	if ($class_id==113 && $skill_id==329) { $addsp=10000000; }
	if ($class_id==113 && $skill_id==330) { $addsp=20000000; }
	if ($class_id==113 && $skill_id==335) { $addsp=10000000; }
	if ($class_id==113 && $skill_id==339) { $addsp=32000000; }
	if ($class_id==113 && $skill_id==347) { $addsp=32000000; }
	if ($class_id==113 && $skill_id==362) { $addsp=20000000; }
	if ($class_id==114 && $skill_id==328) { $addsp=10000000; }
	if ($class_id==114 && $skill_id==329) { $addsp=10000000; }
	if ($class_id==114 && $skill_id==330) { $addsp=20000000; }
	if ($class_id==114 && $skill_id==335) { $addsp=10000000; }
	if ($class_id==114 && $skill_id==340) { $addsp=20000000; }
	if ($class_id==114 && $skill_id==346) { $addsp=64000000; }
	if ($class_id==115 && $skill_id==328) { $addsp=10000000; }
	if ($class_id==115 && $skill_id==329) { $addsp=10000000; }
	if ($class_id==115 && $skill_id==330) { $addsp=13000000; }
	if ($class_id==115 && $skill_id==337) { $addsp=32000000; }
	if ($class_id==115 && $skill_id==1364) { $addsp=13000000; }
	if ($class_id==115 && $skill_id==1365) { $addsp=13000000; }
	if ($class_id==115 && $skill_id==1366) { $addsp=32000000; }
	if ($class_id==115 && $skill_id==1367) { $addsp=10000000; }
	if ($class_id==116 && $skill_id==328) { $addsp=15000000; }
	if ($class_id==116 && $skill_id==329) { $addsp=15000000; }
	if ($class_id==116 && $skill_id==330) { $addsp=20000000; }
	if ($class_id==116 && $skill_id==336) { $addsp=32000000; }
	if ($class_id==116 && $skill_id==1362) { $addsp=20000000; }
	if ($class_id==116 && $skill_id==1363) { $addsp=32000000; }
	if ($class_id==117 && $skill_id==328) { $addsp=10000000; }
	if ($class_id==117 && $skill_id==329) { $addsp=10000000; }
	if ($class_id==117 && $skill_id==330) { $addsp=13000000; }
	if ($class_id==117 && $skill_id==339) { $addsp=32000000; }
	if ($class_id==117 && $skill_id==340) { $addsp=13000000; }
	if ($class_id==117 && $skill_id==347) { $addsp=32000000; }
	if ($class_id==117 && $skill_id==348) { $addsp=10000000; }
	if ($class_id==117 && $skill_id==362) { $addsp=13000000; }
	if ($class_id==118 && $skill_id==328) { $addsp=15000000; }
	if ($class_id==118 && $skill_id==329) { $addsp=15000000; }
	if ($class_id==118 && $skill_id==330) { $addsp=13000000; }
	if ($class_id==118 && $skill_id==339) { $addsp=32000000; }
	if ($class_id==118 && $skill_id==340) { $addsp=13000000; }
	if ($class_id==118 && $skill_id==347) { $addsp=32000000; }
	if ($class_id==118 && $skill_id==362) { $addsp=13000000; }
	$sum_sp = $sum_sp + $addsp;
	$char_name_old = $char_name;
    }
    $sth->finish();

    print MSSQL "delete from user_skill where skill_id in (328,329,330,334,335,336,337,338,339,340,341,342,343,344,345,346,347,348,349,350,351,352,353,354,355,356,357,358,359,360,361,362,363,364,365,366,367,368,369,1335,1336,1337,1338,1339,1340,1341,1342,1343,1344,1346,1347,1348,1349,1350,1351,1352,1353,1354,1355,1356,1357,1358,1359,1360,1361,1362,1363,1364,1365,1366,1367);\n";

    print MSSQL "update user_data set class = 2  where class = 88 ;\n";
    print MSSQL "update user_data set class = 3  where class = 89 ;\n";
    print MSSQL "update user_data set class = 5  where class = 90 ;\n";
    print MSSQL "update user_data set class = 6  where class = 91 ;\n";
    print MSSQL "update user_data set class = 9  where class = 92 ;\n";
    print MSSQL "update user_data set class = 8  where class = 93 ;\n";
    print MSSQL "update user_data set class = 12 where class = 94 ;\n";
    print MSSQL "update user_data set class = 13 where class = 95 ;\n";
    print MSSQL "update user_data set class = 14 where class = 96 ;\n";
    print MSSQL "update user_data set class = 16 where class = 97 ;\n";
    print MSSQL "update user_data set class = 17 where class = 98 ;\n";
    print MSSQL "update user_data set class = 20 where class = 99 ;\n";
    print MSSQL "update user_data set class = 21 where class = 100;\n";
    print MSSQL "update user_data set class = 23 where class = 101;\n";
    print MSSQL "update user_data set class = 24 where class = 102;\n";
    print MSSQL "update user_data set class = 27 where class = 103;\n";
    print MSSQL "update user_data set class = 28 where class = 104;\n";
    print MSSQL "update user_data set class = 30 where class = 105;\n";
    print MSSQL "update user_data set class = 33 where class = 106;\n";
    print MSSQL "update user_data set class = 34 where class = 107;\n";
    print MSSQL "update user_data set class = 36 where class = 108;\n";
    print MSSQL "update user_data set class = 37 where class = 109;\n";
    print MSSQL "update user_data set class = 40 where class = 110;\n";
    print MSSQL "update user_data set class = 41 where class = 111;\n";
    print MSSQL "update user_data set class = 43 where class = 112;\n";
    print MSSQL "update user_data set class = 46 where class = 113;\n";
    print MSSQL "update user_data set class = 48 where class = 114;\n";
    print MSSQL "update user_data set class = 51 where class = 115;\n";
    print MSSQL "update user_data set class = 52 where class = 116;\n";
    print MSSQL "update user_data set class = 55 where class = 117;\n";
    print MSSQL "update user_data set class = 57 where class = 118;\n";
    close MSSQL;
  }

  # --- character_email ---
  if ( $convert_email == 1)
  {
    #set first ssn number!!!
    $ssn = 1010000000000;
    $cc=0;
    $c=0;
    open(MSSQL,">sql/emails.sql") or &fatal_error;
    print BAT "\@echo call runsql emails.sql >> emails.log\n";
    print BAT "call runsql emails.sql >> emails.log\n";
    print " Converting emails..\n";
    $query = "Select login, comments, email, access_level From accounts order by login;";

    my $sth = $dbh->prepare($query);
    if (!$sth->execute) { die "Error:" . $sth->errstr . "\n"; }
    $sth->bind_columns(	\$login, \$comment, \$email, \$access_level );

    $char_name_old = "";
    print MSSQL "USE lin2db;\n\n";

    while($sth->fetch())
    {
      $login=~ s/'/-/g;
      $login=~ s/"/=/g;
      $email=~ s/\@/\\\@/g;
      $email=~ s/\n/ /g;
      $email=~ s/\r/ /g;
      $comment=~ s/\n/ /g;
      $comment=~ s/\r/ /g;
      $comment=~ s/'/ /g;
      $comment=~ s/'/ /g;

#	print MSSQL "delete from ssn where ssn = $ssn;\n";
	print MSSQL "insert into ssn (ssn,name,email,job,phone,zip,addr_main,addr_etc,account_num) values ($ssn,'$login','$email',0,'phone','12345','$comment','','1');\n";
#	print MSSQL "delete from user_info where ssn = $ssn;\n";
	print MSSQL "insert into user_info (account, ssn, kind) values ('$login', $ssn, 99);\n";
	if ($access_level < 0) { print MSSQL "update user_account set pay_stat = 0 where account = '$login';\n"; }
        $cc+=2;
        $ssn++;
	if ($cc > 5000)
	{
	  close MSSQL;
	  $cc=0;
	  $c++;
	  open(MSSQL,">sql/emails$c.sql") or &fatal_error;
	  print BAT "\@echo call runsql emails$c.sql >> emails$c.log\n";
	  print BAT "call runsql emails$c.sql >> emails$c.log\n";
	  print MSSQL "USE lin2db;\n\n";
	}
    }
    $sth->finish();
    close MSSQL;
  }

  # disconnect from database
  $dbh->disconnect();
}
