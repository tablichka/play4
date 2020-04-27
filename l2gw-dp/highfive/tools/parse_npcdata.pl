#/usr/bin/perl
#npcdata must be in ANSI (NOT UNICODE!)
open (NPCDATA,"npcdata.txt");
open (OUT,">npclist.txt");

$left = "test";
print OUT "id	name	level	clan	clan_range	undiyng	agrorange\n";
foreach $line (<NPCDATA>)
{
  chomp $line;

  $count=0;
  foreach $arg (split('\t', $line))
  {
     $count++;
     if ($count==3) { print OUT "$arg	"; } # id
     if ($count==4) { print OUT "$arg	"; } # name
     if ($count==5) { print OUT "$arg	"; } # level
     if ($count==9) { print OUT "$arg	"; } # clan
     if ($count==11) { print OUT "$arg	"; } # social range
     if ($count==22) { print OUT "$arg	"; } # undying
     if ($count==26) { print OUT "$arg\n"; } # aggrorange
  }
#  print OUT "Found $count arguments\n";
}

#01 signature	"npc_begin"
#02 npc_type	"warrior"
#03 npc_id	"1"
#04 npc_name	[gremlin]
#05 npc_lvl	level=1
#06 XP_rate	acquire_exp_rate=29.39
#07 SP_rate	acquire_sp=2
#08 unknown	unsowing=0
#09 clan		clan={}
#10 clan2	ignore_clan_list={}
#11 clan3	clan_help_range=300
#12 chest	slot_chest=[]
#13 rhand	slot_rhand=[]
#14 lhand	slot_lhand=[]
#15 shield_rate	shield_defense_rate=0
#16 shield_def	shield_defense=0
#17 skills	skill_list={@s_race_fairy}
#18 npc_ai	npc_ai={[gremlin];{[MoveAroundSocial]=0};{[MoveAroundSocial1]=0};{[MoveAroundSocial2]=0}}
#19 category	category={}
#20 race		race=fairy
#21 sex		sex=male
#22 undying	undying=0
#23 canatt	can_be_attacked=1
#24 corpse_time	corpse_time=7
#25 nosleep	no_sleep_mode=0
#26 agro_range	agro_range=1000
#27 groundhight	ground_high={50;0;0}
#28 groundlow	ground_low={20;0;0}
#29 exp		exp=1
#30 hp		org_hp=39.74519
#31 hp_regen	org_hp_regen=2
#32 mp		org_mp=40
#33 mp_regen	org_mp_regen=0.9
#34 coll_rad	collision_radius={10;10}
#35 coll_hei	collision_height={15;15}
#36 str		str=40
#37 int		int=21
#38 dex		dex=30
#39 wit		wit=20
#40 con		con=43
#41 men		men=10
#42 att_type	base_attack_type=sword
#43 att_range	base_attack_range=40
#44 pdam		base_damage_range={0;0;80;120}
#45 pdamrnd	base_rand_dam=30
#46 phisdam	base_physical_attack=8.47458
#47 critbase	base_critical=4
#48 hitmod	physical_hit_modify=4.75
#49 attspeed	base_attack_speed=253
#50 reuse	base_reuse_delay=0
#51 matack	base_magic_attack=5.78704
#52 defend	base_defend=44.44444
#53 mdef		base_magic_defend=32.52252
#54 pavoid	physical_avoid_modify=0
#55 soulshot	soulshot_count=0
#56 spiritshot	spiritshot_count=0
#57 hittime	hit_time_factor=0.37
#58 itemmake	item_make_list={}
#59 corpsemake	corpse_make_list={}
#60 aitemake	additional_make_list={}
#61 amutilist	additional_make_multi_list={}
#62 hpinc	hp_increase=0
#63 mpinc	mp_increase=0
#64 safeheight	safe_height=100
#65 signature	npc_end
#Found 65 arguments
