#Оффшор зона для Гиран Харбора
insert into locations values
(80004,'offshore_giran_harbor',49572,183368,-5000,-1500),
(80004,'offshore_giran_harbor',50701,185034,-5000,-1500),
(80004,'offshore_giran_harbor',53214,186206,-5000,-1500),
(80004,'offshore_giran_harbor',44192,194099,-5000,-1500),
(80004,'offshore_giran_harbor',41824,188576,-5000,-1500);
insert into zone set id=500014, name='[offshore_giran_harbor]', type='offshore', loc_id=80004, restart_point='80015', PKrestart_point='41455';

#Мирная зона рядом с Гиран Харбором
insert into locations values
(80013,'peace_giran_harbor_alt',50336,184656,-5000,-1500),
(80013,'peace_giran_harbor_alt',50701,185034,-5000,-1500),
(80013,'peace_giran_harbor_alt',53214,186206,-5000,-1500),
(80013,'peace_giran_harbor_alt',44192,194099,-5000,-1500),
(80013,'peace_giran_harbor_alt',41824,188576,-5000,-1500);
insert into zone set id=500023, name='[peace_giran_harbor_altr]', type='peace_zone', loc_id=80013;
#неторговая зона в Гиран Харборе возле ГК
insert into locations values
(80014,'Giran_Harbor_nontrade',47987,186956,-5000,-1500),
(80014,'Giran_Harbor_nontrade',47842,186720,-5000,-1500),
(80014,'Giran_Harbor_nontrade',48126,186617,-5000,-1500),
(80014,'Giran_Harbor_nontrade',48214,186848,-5000,-1500);
insert into zone set id=500024, name='[peace_giran_harbor_altr]', type='dummy', loc_id=80014, blocked_actions='private store;private workshop;';

#Перенос спавна ГК из гиран харбора в мирную зону рядом
update spawnlist set locx=46447, locy=185935, locz=-3583 where npc_templateid=30878;

#Спавн нового Warehouse keeper в Гиран Харборе
insert into spawnlist values ('giran09_npc2123_03',1,30092,48059,186791,-3512,0,0,42000,60,0,0,'Airman','','default_maker','0','0','0',0);

#Спавн нового Trader в Гиран Харборе
insert into spawnlist values ('giran09_npc2123_03',1,30081,48146,186753,-3512,0,0,42000,60,0,0,'Airman','','default_maker','0','0','0',0);

#Спавн нового ГК в Гиран Харборе
insert into spawnlist values ('giran09_npc2123_03',1,36394,47984,186832,-3445,0,0,42000,60,0,0,'Airman','','default_maker','0','0','0',0);

#Правка нового телепортера
update npc set title="Gatekeeper", type = "L2Merchant", ai_type= "npc" where id = 36394;

#Точки рестарта
insert into locations values
(80015,'giran_harbor_restart_point',42554,188785,-3513,-3413),
(80015,'giran_harbor_restart_point',43321,188218,-3513,-3413),
(80015,'giran_harbor_restart_point',44082,188135,-3513,-3413),
(80015,'giran_harbor_restart_point',44942,187715,-3414,-3314),
(80015,'giran_harbor_restart_point',46131,186658,-3512,-3412),
(80015,'giran_harbor_restart_point',47016,186518,-3512,-3412),
(80015,'giran_harbor_restart_point',47813,186051,-3512,-3412),
(80015,'giran_harbor_restart_point',48217,185236,-3512,-3412),
(80015,'giran_harbor_restart_point',49362,184688,-3512,-3412),
(80015,'giran_harbor_restart_point',50328,185143,-3582,-3482),
(80015,'giran_harbor_restart_point',49897,185711,-3582,-3482),
(80015,'giran_harbor_restart_point',50857,186504,-3652,-3552),
(80015,'giran_harbor_restart_point',51174,186261,-3698,-3598),
(80015,'giran_harbor_restart_point',51314,186955,-3652,-3552),
(80015,'giran_harbor_restart_point',51606,187211,-3652,-3552),
(80015,'giran_harbor_restart_point',51966,187360,-3652,-3552),
(80015,'giran_harbor_restart_point',52295,186708,-3698,-3598),
(80015,'giran_harbor_restart_point',52389,187055,-3652,-3552),
(80015,'giran_harbor_restart_point',51557,187931,-3652,-3552),
(80015,'giran_harbor_restart_point',51030,187744,-3698,-3598),
(80015,'giran_harbor_restart_point',51672,187658,-3652,-3552),
(80015,'giran_harbor_restart_point',51485,187227,-3652,-3552),
(80015,'giran_harbor_restart_point',51114,186884,-3652,-3552),
(80015,'giran_harbor_restart_point',50574,186375,-3652,-3552),
(80015,'giran_harbor_restart_point',50198,186511,-3693,-3593),
(80015,'giran_harbor_restart_point',49652,187085,-3723,-3623),
(80015,'giran_harbor_restart_point',49441,186993,-3723,-3623),
(80015,'giran_harbor_restart_point',49555,186684,-3723,-3623),
(80015,'giran_harbor_restart_point',49891,186288,-3723,-3623),
(80015,'giran_harbor_restart_point',49737,185943,-3582,-3482),
(80015,'giran_harbor_restart_point',49022,186583,-3582,-3482),
(80015,'giran_harbor_restart_point',48198,187169,-3582,-3482),
(80015,'giran_harbor_restart_point',47403,187640,-3582,-3482),
(80015,'giran_harbor_restart_point',47762,188361,-3651,-3551),
(80015,'giran_harbor_restart_point',47862,188950,-3652,-3552),
(80015,'giran_harbor_restart_point',48297,189417,-3652,-3552),
(80015,'giran_harbor_restart_point',48673,189306,-3698,-3598),
(80015,'giran_harbor_restart_point',48430,189639,-3652,-3552),
(80015,'giran_harbor_restart_point',48707,190114,-3652,-3552),
(80015,'giran_harbor_restart_point',48889,190134,-3652,-3552),
(80015,'giran_harbor_restart_point',49152,190048,-3652,-3552),
(80015,'giran_harbor_restart_point',49372,189535,-3698,-3598),
(80015,'giran_harbor_restart_point',49592,189920,-3698,-3598),
(80015,'giran_harbor_restart_point',48815,190332,-3652,-3552),
(80015,'giran_harbor_restart_point',48422,190458,-3652,-3552),
(80015,'giran_harbor_restart_point',48103,190733,-3652,-3552),
(80015,'giran_harbor_restart_point',47886,190326,-3652,-3552),
(80015,'giran_harbor_restart_point',48181,190472,-3652,-3552),
(80015,'giran_harbor_restart_point',48569,190197,-3652,-3552),
(80015,'giran_harbor_restart_point',48242,189643,-3652,-3552),
(80015,'giran_harbor_restart_point',47955,188761,-3652,-3552),
(80015,'giran_harbor_restart_point',47514,188224,-3652,-3552),
(80015,'giran_harbor_restart_point',47019,188453,-3652,-3552),
(80015,'giran_harbor_restart_point',46808,188302,-3652,-3552),
(80015,'giran_harbor_restart_point',47079,188129,-3652,-3552),
(80015,'giran_harbor_restart_point',47444,188078,-3647,-3547),
(80015,'giran_harbor_restart_point',47333,187854,-3582,-3482),
(80015,'giran_harbor_restart_point',47002,187915,-3582,-3482),
(80015,'giran_harbor_restart_point',46512,188210,-3582,-3482),
(80015,'giran_harbor_restart_point',46357,188009,-3582,-3482),
(80015,'giran_harbor_restart_point',47061,187602,-3562,-3462),
(80015,'giran_harbor_restart_point',46698,187443,-3511,-3411),
(80015,'giran_harbor_restart_point',46270,187565,-3511,-3411),
(80015,'giran_harbor_restart_point',46231,187187,-3512,-3412),
(80015,'giran_harbor_restart_point',45462,187548,-3435,-3335),
(80015,'giran_harbor_restart_point',44136,188292,-3513,-3413),
(80015,'giran_harbor_restart_point',44497,188485,-3513,-3413),
(80015,'giran_harbor_restart_point',44538,188663,-3513,-3413),
(80015,'giran_harbor_restart_point',43929,189042,-3513,-3413),
(80015,'giran_harbor_restart_point',44172,189237,-3582,-3482),
(80015,'giran_harbor_restart_point',44636,188896,-3581,-3481),
(80015,'giran_harbor_restart_point',44866,189136,-3582,-3482),
(80015,'giran_harbor_restart_point',44650,189408,-3583,-3483),
(80015,'giran_harbor_restart_point',45053,189175,-3652,-3552),
(80015,'giran_harbor_restart_point',45171,189208,-3652,-3552),
(80015,'giran_harbor_restart_point',45318,189525,-3652,-3552),
(80015,'giran_harbor_restart_point',44900,189588,-3652,-3552),
(80015,'giran_harbor_restart_point',44741,189532,-3652,-3552),
(80015,'giran_harbor_restart_point',44888,189329,-3652,-3552),
(80015,'giran_harbor_restart_point',44637,189469,-3583,-3483),
(80015,'giran_harbor_restart_point',44006,189645,-3582,-3482),
(80015,'giran_harbor_restart_point',44110,190050,-3652,-3552),
(80015,'giran_harbor_restart_point',44451,190605,-3652,-3552),
(80015,'giran_harbor_restart_point',44699,191034,-3652,-3552),
(80015,'giran_harbor_restart_point',44784,191275,-3652,-3552),
(80015,'giran_harbor_restart_point',44945,191501,-3652,-3552),
(80015,'giran_harbor_restart_point',45268,191361,-3652,-3552),
(80015,'giran_harbor_restart_point',45597,191184,-3697,-3597),
(80015,'giran_harbor_restart_point',45760,191324,-3697,-3597),
(80015,'giran_harbor_restart_point',45161,191667,-3654,-3554),
(80015,'giran_harbor_restart_point',44567,191998,-3652,-3552),
(80015,'giran_harbor_restart_point',44431,191816,-3652,-3552),
(80015,'giran_harbor_restart_point',44776,191621,-3652,-3552),
(80015,'giran_harbor_restart_point',44615,191176,-3651,-3551),
(80015,'giran_harbor_restart_point',44323,190649,-3652,-3552),
(80015,'giran_harbor_restart_point',44028,190115,-3652,-3552),
(80015,'giran_harbor_restart_point',43696,189712,-3582,-3482),
(80015,'giran_harbor_restart_point',43153,190062,-3582,-3482),
(80015,'giran_harbor_restart_point',43028,190227,-3583,-3483),
(80015,'giran_harbor_restart_point',42820,190222,-3582,-3482),
(80015,'giran_harbor_restart_point',42688,189919,-3582,-3482),
(80015,'giran_harbor_restart_point',43125,189565,-3513,-3413),
(80015,'giran_harbor_restart_point',42595,189708,-3513,-3413),
(80015,'giran_harbor_restart_point',42265,189137,-3513,-3413);


