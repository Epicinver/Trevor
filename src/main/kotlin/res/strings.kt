package res

/**
 * Created by sergeyopivalov on 17.11.16.
 */
object ButtonLabel {
    val allUsers = "Показать всех пользователей"
    val needHelp = "Помощь друга"
    val salaryToday = "Сегодня зарплата =)"
    val salaryStart = "Начать выдачу зарплаты"
    val salaryList = "Список получающих сегодня"
    val yes = "Да"
    val no = "Нет"
    val going = "Иду"
    val notGoing = "Не иду"
    val gotPaid = "Получил"
    val notGotPaid = "Не получил"
    val deleteUser = "Удалить пользователя"
    val bigRoom = "Большая"
    val smallRoom = "Маленькая"
    val reservesList = "График бронирования"
}

object MiscStrings {
    val ok = "Ok"
    val greeting = "Привет, это Тревор. Для регистрации отправь /reg"
    val instruction = "1. В день зарплаты получаешь уведомление о выдаче с двумя кнопками\n2. Нажимаешь 'Да' если ты сегодня в офисе и планируешь забрать свои деньги, 'Нет' - если ты на больничном/в отпуске/в командировке и т.д.\n3. Когда настанет твоя очередь - получишь сообщение. На него нужно отреагировать в течении 2 минут, чтобы не задерживать очередь\n4. Идешь и забираешь зарплату\n5. Каеф\n"
}

object AdminStrings {
    val commandsList = "Вот возможные действия:"
    val commandNotAllowed = "Вот тебе, именно тебе, такие команды использовать запрещено"
    val helpRequest = "Варя, Тревор на связи. Лене нужна твоя помощь"
    val helpGoing = "Тревор вызвал подмогу, сейчас все будет..."
    val typeChatIdToDelete = "Введи ID пользователя для удаления"
    val userHasBeenDeleted = "Удалил изменника"
}

object UserStrings {
    val alreadyRegistered = "Ну ты ведь зарегестрирован уже..."
    val thereIsNoUsername = "Привет! Ты хочешь получать зарплату, но у тебя не указан username в настройках Telegram. Не надо так. Добавь username и попробуй /reg снова"
    val askPass = "Привет! Ну ладно, ты хочешь получать зарплату. Какой пароль?"
    val registrationComplete = "Регистрация завершена"
    val wrongPass = "Почти правильный пароль"
    val rightPass = "Верный пароль"
    val typeYourName = "Введи свое имя и фамилию. Через пробел. С большой буквы. И без шуточек"
    val typeYourBirthday = "Тревор хочет знать твою дату рождения. Формат : dd.MM.yyyy"
    val incorrectInput = "Что, прости?"
    val incorrectBirthday = "Ты втер мне дичь. Введи дату рождения в правильном формате"
    val salaryNotification = "Привет! Сегодня зарплату обещают\nБудешь получать ?"
}

object BirthdayStrings {
    val notification = "Cегодня празднует свой день рождения "
    val notificationWeekend = "На выходных был день рождения у "
}

object SalaryDayStrings {
    val dummy = "..."
    val hasBeenAdded = "Добавил тебя в список получающих"
    val inOtherTime = "Хорошо, в другой раз"
    val noOne = "Пока никто не собирается"
    val quantity = "\nВсего - "
    val yourTurn = "Твоя очередь. У тебя 2 минуты на подтверждение"
    val isGoing = "идет..."
    val turnSkipped = "Ты пропустил свою очередь. Ожидай следующего вызова"
    val complete = "Зарплата выдана всем"
    val moneyReceived = "Ciao!"

}

object ReservationStrings {
    val chooseRoom = "Выбери переговорную, которую хочешь зарезервировать"
    val reserved = "Зарезервировано"
    val incorrectDate = "Некорректный формат"
    val incorrectDuration = "Не нужно тебе столько..."
    val typeDuration = "Введи продолжительность резерва в минутах"
    val typeDate = "Когда? Формат dd.MM.yyyy HH:mm"
    val timeNotAvailable = "Время уже занято, либо уже прошло..."
    val noReserves = "Резервов пока нет"
}

