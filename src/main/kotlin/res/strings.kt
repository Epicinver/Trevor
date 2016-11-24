package res

/**
 * Created by sergeyopivalov on 17.11.16.
 */
object ButtonLabel {
    val allNames = "Показать всех пользователей"
    val needHelp = "Помощь друга"
    val salaryToday = "Сегодня зарплата =)"
    val salaryStart = "Начать выдачу зарплаты"
    val salaryList = "Список получающих сегодня"
    val salaryYes = "Да"
    val salaryNo = "Нет"
    val ready = "Иду"
    val notReady = "Не иду"
    val get = "Получил"
    val notGet = "Не получил"
}

object MiscStrings{
    val ok = "Ok"
}

object AdminStrings {
    val commandsList = "Вот возможные действия:"
    val commandNotAllowed = "Вот тебе, именно тебе, такие команды использовать запрещено"
    val helpRequest = "Варя, Тревор на связи. Лене нужна твоя помощь"
    val helpGoing = "Тревор вызвал подмогу, сейчас все будет..."
}

object UserStrings {
    val pass = "0272" //todo вынести пароль в properties
    val alreadyRegistered = "Ну ты ведь зарегестрирован уже..."
    val thereIsNoUsername = "Привет! Ты хочешь получать зарплату, но у тебя не указан username в настройках Telegram. Не надо так. Добавь username и попробуй /reg снова"
    val askPass = "Привет! Ну ладно, ты хочешь получать зарплату. Какой пароль?"
    val registrationComplete = "Регистрация завершена"
    val wrongPass = "Почти правильный пароль"
    val rightPass = "Верный пароль"
    val typeYourName = "Введи свое имя и фамилию. Через пробел. С большой буквы. И без шуточек"
    val typeYourBirthday = "Тревор хочет знать твою дату рождения. Формат : dd.mm.yyyy"
    val incorrectInput = "Ты прислал какую то шляпу..."
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
    val yourTurn = "Твоя очередь. У тебя 2 минуты"
    val isGoing = "идет..."
    val turnSkipped = "Ты пропустил свою очередь. Ожидай следующего вызова"
    val complete = "Зарплата выдана всем"
    val moneyReceived = "Деньги получены. Досвидули"

}

