package events.lastHero;

import events.Capture.Capture;
import events.TvT.TvT;
import javolution.util.FastList;
import ru.l2gw.gameserver.Config;
import ru.l2gw.extensions.listeners.L2ZoneEnterLeaveListener;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.Announcements;
import ru.l2gw.gameserver.handler.IOnDieHandler;
import ru.l2gw.gameserver.instancemanager.InstanceManager;
import ru.l2gw.gameserver.model.*;
import ru.l2gw.gameserver.model.entity.instance.Instance;
import ru.l2gw.gameserver.model.entity.olympiad.Olympiad;
import ru.l2gw.gameserver.model.gmaccess.AdminTemplateManager;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.zone.L2Zone;
import ru.l2gw.gameserver.serverpackets.ExShowScreenMessage;
import ru.l2gw.gameserver.tables.ReflectionTable;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.util.Files;
import ru.l2gw.util.Location;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ScheduledFuture;

public class LastHero extends Functions implements ScriptFile, IOnDieHandler {
    public L2Object self;
    public L2NpcInstance npc;

    // Список зарегистрированных игроков
    private static FastList<Integer> registered = new FastList<Integer>();
    // Список участников, прошедших регистрацию и находящихся в бою
    private static FastList<Integer> participants = new FastList<Integer>();
    // Пустой список игроков для инициализации
    private static FastList<L2Player> emptyList = new FastList<L2Player>(0);
    // Экземпляр инстанса для события
    private static Instance lastHeroInstance;

    // Текущий статус события (0 - не запущено, 1 - регистрация, 2 - подготовка, 3 - бой)
    private static Integer _status = 0;

    // Флаг, указывающий, запущено ли событие
    private static Boolean _running = false;

    // Использование альтернативной формулы проведения события
    private static Boolean _alternate_form = Config.EVENT_LastHero_heroMod;
    // Сортировка участников по уровням
    private static Boolean _lvlSort = Config.EVENT_LastHero_sortBylvl;
    // Время до начала боя
    private static Integer _time_to_start = Config.EVENT_LastHeroTime;
    // Продолжительность события
    private static Integer _event_time = Config.EVENT_LastHero_FightTime;
    // ID награды за событие
    private static Integer _bonus_id = Config.EVENT_LastHeroBonusID;
    // Количество наград за событие
    private static Integer _bonus_count = Config.EVENT_LastHeroBonusCount;

    // Сообщения для анонсов и уведомлений
    private static String _ann1 = Config.EVENT_LastHero_ruleMsg1;
    private static String _ann2 = Config.EVENT_LastHero_ruleMsg2;
    private static String _ann3 = Config.EVENT_LastHero_ruleMsg3;
    private static String _ann4 = Config.EVENT_LastHero_ruleMsg4;
    private static String _invMsg = Config.EVENT_LastHero_msgInv;
    private static String _startMsg = Config.EVENT_LastHero_msgStart;
    private static String _stopMsg = Config.EVENT_LastHero_msgStopEv;
    private static String _missMsg = Config.EVENT_LastHero_msgMiss;
    private static String _min_rem = " минут осталось до запуска...";
    private static String _event_end = Config.EVENT_LastHero_msgEndEv;
    private static String _no_winers = Config.EVENT_LastHero_msgNoWIn;
    private static String _die_msg = Config.EVENT_LastHero_msgDie;
    private static String _back_msg = Config.EVENT_LastHero_msgTP;
    private static String _prep_msg = Config.EVENT_LastHero_msgPrep;
    private static String _fight_msg = Config.EVENT_LastHero_msgFight;

    // Флаг для снятия эффектов с игроков
    private static boolean dispel = Config.EVENT_LastHero_dispel;

    // Задача для завершения боя
    @SuppressWarnings("unchecked")
    private static ScheduledFuture _endTask;
    // Задача для циклического запуска события
    private static ScheduledFuture _cycleTask;

    // Слушатель событий входа/выхода из зоны
    private static ZoneListener zoneListener = new ZoneListener();

    // 1. Загружает скрипт при старте сервера. Если событие включено в конфигурации, планирует запуск события по расписанию.
    public void onLoad() {
        if (Config.EVENT_LastHero_enabled) {
            long startTime = Config.EVENT_LastHero_cron.timeNextUsage(System.currentTimeMillis());
            _log.info("Loaded Event: Last Hero [state: activated] event start: " + new Date(startTime));
            // Запускаем задачу для старта события
            _cycleTask = executeTask("events.lastHero.LastHero", "start", new Object[0], startTime - System.currentTimeMillis());
        } else {
            _log.info("Loaded Event: Last Hero [state: deactivated]");
        }
    }

    // 2. Вызывается при перезагрузке скрипта. В данном случае не выполняет никаких действий.
    public void onReload() {
    }

    // 3. Вызывается при выключении сервера. В данном случае не выполняет никаких действий.
    public void onShutdown() {
    }

    // 4. Проверяет, запущено ли событие.
    public static boolean isRunned() {
        return _running;
    }

    // 5. Возвращает HTML-контент для диалога с NPC (ID 31225), используемый для взаимодействия игрока с событием.
    public String DialogAppend_31225(Integer val) {
        if (val == 0) {
            L2Player player = (L2Player) self;
            return Files.read("data/scripts/events/lastHero/31225.html", player);
        }
        return "";
    }

    // 6. Запускает событие Last Hero, если вызвано администратором или по расписанию. Устанавливает статус регистрации, очищает списки участников и запускает задачи для анонсов и регистрации.
    public void start() {
        if (self != null) {
            // Проверка прав администратора для запуска события
            if (!AdminTemplateManager.checkBoolean("eventMaster", (L2Player) self)) {
                return;
            }
        }

        // Проверка текущего статуса события
        if (_status != 0) {
            _log.info("Event: Last Hero not started! status: " + _status);
            if (self != null) {
                ((L2Player) self).sendMessage("Last Hero is running! status: " + _status);
            }
            return;
        }

        _log.info("Event: Last Hero started!");
        _status = 1; // Устанавливаем статус "регистрация"
        _running = true;
        _time_to_start = Config.EVENT_LastHeroTime;
        participants.clear();
        Announcements.getInstance().announceToAll(String.valueOf(_startMsg));
        // Запускаем задачу для предложения участия игрокам
        executeTask("events.lastHero.LastHero", "question", new Object[0], 20000L);
        // Запускаем задачу для анонсов
        executeTask("events.lastHero.LastHero", "announce", new Object[0], 50000L);
    }

    // 7. Отправляет всем подходящим игрокам предложение участвовать в событии через скриптовый запрос.
    public static void question() {
        for (L2Player player : L2ObjectsStorage.getAllPlayers()) {
            if (checkPlayerCondition(player) && !TvT.isRegistered(player) && !Capture.isRegistered(player)) {
                player.scriptRequest(String.valueOf(_invMsg), "events.lastHero.LastHero:addPlayer", new Object[0]);
            }
        }
    }

    // 8. Проверяет, зарегистрирован ли игрок на событие.
    public static boolean isRegistered(L2Player player) {
        return Config.EVENT_LastHero_enabled && registered.contains(player.getObjectId());
    }

    // 9. Регистрирует игрока на событие, проверяя условия участия и текущий статус события.
    public void addPlayer() {
        if (!(self instanceof L2Player)) {
            return;
        }

        L2Player player = (L2Player) self;

        // Проверка статуса события
        if (_status != 1) {
            player.sendMessage("Нельзя зарегистрироваться на эвент в это время.");
            return;
        }

        // Проверка условий для участия
        if (!checkPlayerCondition(player) || TvT.isRegistered(player) || Capture.isRegistered(player)) {
            player.sendMessage("Вы не соответствуете требованиям для участия в эвенте.");
            return;
        }

        // Регистрация игрока
        if (!registered.contains(player.getObjectId())) {
            player.sendMessage("Вы зарегистрированы для участия в Last Hero.");
            registered.add(player.getObjectId());
        } else {
            player.sendMessage("Вы уже зарегистрированы.");
        }
    }

    // 10. Анонсирует оставшееся время до начала события. Если участников недостаточно, отменяет событие. Если время истекло, запускает подготовку.
    public static void announce() {
        Announcements a = Announcements.getInstance();
        // Проверка минимального количества участников
        if (registered.size() < Config.EVENT_LastHeroMinParticipants) {
            a.announceToAll(String.valueOf(_stopMsg));
            registered.clear();
            _log.info("Event: Last Hero no minimum participants.");
            rescheduleEvent();
            return;
        }

        // Уменьшаем время до старта и анонсируем
        if (_time_to_start > 1) {
            _time_to_start--;
            a.announceToAll(_time_to_start + _min_rem);
            if (_time_to_start % 3 == 0) {
                a.announceToAll(String.valueOf(_missMsg));
            }
            executeTask("events.lastHero.LastHero", "announce", new Object[0], 60000L);
        } else {
            a.announceToAll(_prep_msg);
            // Запускаем подготовку к бою
            executeTask("events.lastHero.LastHero", "prepare", new Object[0], 5000L);
        }
    }

    // 11. Подготавливает событие: телепортирует игроков в колизей, отправляет правила и запускает таймер до начала боя.
    public static void prepare() {
        if (!_running) {
            return;
        }

        teleportPlayersToColiseum();
        // Запускаем бой через 2 минуты
        executeTask("events.lastHero.LastHero", "go", new Object[0], 120000L);

        Announcements a = Announcements.getInstance();
        a.announceToAll(String.valueOf(_ann1));
        a.announceToAll(String.valueOf(_ann2));
        a.announceToAll(String.valueOf(_ann3));
        a.announceToAll(String.valueOf(_ann4));
    }

    // 12. Телепортирует зарегистрированных игроков в колизей, создает инстанс события, применяет баффы и снимает эффекты, если требуется.
    public static void teleportPlayersToColiseum() {
        _status = 2; // Устанавливаем статус "подготовка"

        FastList<L2Player> players = FastList.newInstance();
        // Фильтруем зарегистрированных игроков
        for (int objectId : registered) {
            L2Player player = L2ObjectsStorage.getPlayer(objectId);
            if (player == null || player.isInOfflineMode() || player.isInOlympiadMode() || player.inObserverMode() || player.isInDuel() || player.isAlikeDead()
                    || player.isInCombat() || player.isCastingNow() || Olympiad.isRegisteredInComp(player) || player.getReflection() != 0 || player.isInBoat()) {
                continue;
            }
            participants.add(player.getObjectId());
            players.add(player);
        }

        registered.clear();

        // Проверка минимального количества участников
        if (players.size() < Config.EVENT_LastHeroMinParticipants) {
            participants.clear();
            Announcements.getInstance().announceToAll(String.valueOf(_stopMsg));
            _log.info("Event: Last Hero no minimum participants.");
            FastList.recycle(players);
            rescheduleEvent();
            return;
        }

        // Создаем инстанс события
        lastHeroInstance = InstanceManager.getInstance().createNewInstance(-1, emptyList);
        lastHeroInstance.getTemplate().getZone().setActive(true, lastHeroInstance.getReflection());
        lastHeroInstance.getTemplate().getZone().getListenerEngine().addMethodInvokedListener(zoneListener);
        lastHeroInstance.startInstance();

        L2Skill nobleSkill = SkillTable.getInstance().getInfo(1323, 1); // Бафф дворянства
        L2Skill cancelSkill = SkillTable.getInstance().getInfo(4334, 1); // Отмена эффектов

        // Подготовка игроков
        for (L2Player player : players) {
            player.block(); // Блокируем игрока
            if (player.getParty() != null) {
                player.getParty().removePartyMember(player); // Удаляем из группы
            }
            player.setStablePoint(player.getLoc()); // Сохраняем точку возврата
            player.teleToLocation(Location.coordsRandomize(149505, 46719, -3417, 0, 0, 500), lastHeroInstance.getReflection()); // Телепорт в колизей
            player.setTeam(2); // Устанавливаем команду
            if (dispel) {
                List<L2Character> targets = new ArrayList<>(1);
                targets.add(player);
                cancelSkill.useSkill(player, targets); // Снимаем эффекты
            } else {
                player.stopEffects("hero");
                player.stopEffects("barrier");
                player.stopEffects("mystic_immunity");
            }
            nobleSkill.applyEffects(player, player, false); // Накладываем бафф дворянства
            if (player.getPet() != null) {
                player.getPet().block();
                if (dispel) {
                    cancelSkill.applyEffects(player, player.getPet(), false);
                } else {
                    player.getPet().stopEffects("hero");
                    player.getPet().stopEffects("barrier");
                    player.getPet().stopEffects("mystic_immunity");
                }
            }
        }

        FastList.recycle(players);
    }

    // 13. Начинает бой, разблокируя игроков, отправляя сообщение о начале и запуская таймер завершения боя.
    public static void go() {
        _status = 3; // Устанавливаем статус "бой"

        FastList<L2Player> players = getParticipants();
        if (players.size() < Config.EVENT_LastHeroMinParticipants) {
            participants.clear();
            Announcements.getInstance().announceToAll(String.valueOf(_stopMsg));
            _log.info("Event: Last Hero no minimum participants.");
            rescheduleEvent();
            FastList.recycle(players);
            return;
        }

        _log.info("Event: Last Hero start battle, participants: " + players.size());

        // Отправляем сообщение о начале боя
        ExShowScreenMessage msg = new ExShowScreenMessage(">> Start FIGHT <<", 3000, ExShowScreenMessage.ScreenMessageAlign.TOP_CENTER, true);
        for (L2Player player : players) {
            player.unblock(); // Разблокируем игрока
            player.sendPacket(msg);
            if (player.getPet() != null) {
                player.getPet().unblock();
            }
        }

        FastList.recycle(players);
        Announcements.getInstance().announceToAll(_fight_msg);
        // Запускаем задачу завершения боя
        _endTask = executeTask("events.lastHero.LastHero", "endBattle", new Object[0], _event_time * 60000L);
    }

    // 14. Возвращает список игроков, находящихся в инстансе события и участвующих в нем.
    public static FastList<L2Player> getParticipants() {
        if (lastHeroInstance == null) {
            return emptyList;
        }

        Reflection ref = ReflectionTable.getInstance().getById(lastHeroInstance.getReflection());
        if (ref == null) {
            return emptyList;
        }

        FastList<L2Player> players = FastList.newInstance();
        for (L2Object cha : ref.getAllObjects()) {
            if (cha instanceof L2Player && !((L2Player) cha).isDeleting() && ((L2Player) cha).isOnline() && participants.contains(cha.getObjectId())) {
                players.add((L2Player) cha);
            }
        }

        return players;
    }

    // 15. Завершает бой без победителя, анонсирует завершение и возвращает игроков на исходные позиции.
    public static void endBattle() {
        Announcements a = Announcements.getInstance();
        a.announceToAll(_event_end);
        _log.info("Event: Last Hero battle end, no winner.");
        FastList<L2Player> players = getParticipants();

        // Логируем живых игроков
        for (L2Player player : players) {
            if (!player.isDead()) {
                _log.info("Event: Last Hero live " + player + " at " + player.getLoc() + " visible: " + player.isVisible() + " isHide: " + player.isHide() + " isOnline: " + player.isOnline() + " teleport: " + player.isTeleporting());
            }
        }

        a.announceToAll(_no_winers);
        a.announceToAll(_back_msg);
        // Завершаем событие
        executeTask("events.lastHero.LastHero", "end", new Object[0], 5000L);
        rescheduleEvent();

        FastList.recycle(players);

        if (_endTask != null) {
            _endTask.cancel(false);
        }
        _endTask = null;
    }

    // 16. Завершает событие, восстанавливает состояние игроков (HP, MP, CP), снимает эффекты, возвращает их на исходные позиции и завершает инстанс.
    public static void end() {
        FastList<L2Player> players = getParticipants();
        for (L2Player player : players) {
            player.stopEffectsByName("c_fake_death"); // Снимаем эффект ложной смерти
            if (player.isDead()) {
                player.doRevive(); // Воскрешаем игрока
            }
            player.setCurrentCp(player.getMaxCp()); // Восстанавливаем CP
            player.setCurrentHp(player.getMaxHp()); // Восстанавливаем HP
            player.setCurrentMp(player.getMaxMp()); // Восстанавливаем MP
            player.unsetVar("LH_REWARD"); // Удаляем переменную награды
            player.setTeam(0); // Убираем команду
            player.teleToLocation(player.getStablePoint(), 0); // Телепортируем на исходную точку
            player.setStablePoint(null); // Очищаем точку возврата
            player.unblock(); // Разблокируем игрока
            if (player.getPet() != null) {
                player.getPet().unblock();
            }
        }

        // Деактивируем зону и завершаем инстанс
        lastHeroInstance.getTemplate().getZone().getListenerEngine().removeMethodInvokedListener(zoneListener);
        lastHeroInstance.getTemplate().getZone().setActive(false, lastHeroInstance.getReflection());
        lastHeroInstance.stopInstance();
        lastHeroInstance = null;
        FastList.recycle(players);
    }

    // 17. Обрабатывает смерть игрока в бою, выдавая награды убийце (в зависимости от режима) и проверяя, остался ли один живой игрок для определения победителя.
    @Override
    public void onDie(L2Character killed, L2Character killer) {
        if (_status == 3 && killed instanceof L2Player && killed.getReflection() == lastHeroInstance.getReflection()) {
            ((L2Player) killed).sendMessage(_die_msg); // Сообщаем о смерти
            ((L2Player) killed).setTeam(0); // Убираем команду

            if (!_alternate_form) {
                // Награждаем убийцу в стандартном режиме
                if (killer != null && killer.isPlayer()) {
                    if (Config.EVENT_LastHeroRate) {
                        ((L2Player) killer).addItem("last_hero", _bonus_id, ((L2Player) killed).getLevel() * _bonus_count, null, true);
                    } else {
                        ((L2Player) killer).addItem("last_hero", _bonus_id, _bonus_count, null, true);
                    }
                }
            } else if (killer != null && killer.getPlayer() != null) {
                // Награждаем в альтернативном режиме
                L2Player player = killer.getPlayer();
                int pReward = player.getVarInt("LH_REWARD");

                if (Config.EVENT_LastHeroRate) {
                    pReward += ((L2Player) killed).getLevel() * _bonus_count;
                } else if (pReward <= 0) {
                    pReward = _bonus_count;
                }

                player.setVar("LH_REWARD", String.va