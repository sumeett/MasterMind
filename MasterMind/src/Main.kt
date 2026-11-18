
val MAX_ATTEMPTS = 10

val debug = true

fun main() {
    var playAgain = true
    while (playAgain) {
        play()
        //TODO: Ask to play again or exit
        playAgain = false
    }
}

fun play() {
    val secret: List<Color> = List(4) { getRandomColor() }
    if(debug) println("DEBUG ON: ${secret}")
    println("I have a secret. It's a combination of 4 pins in which are " +
            "either, Red, Green, Blue, Red, Yellow, Black or White.\n" +
            "You have 10 chances to guess the correct combination.\n" +
            "At the end of each turn I will give a response example:" +
            "2:C 1:M meaning you have 2 colors in the right spot and " +
            "1 color in the wrong spot. \nSimilarly 1:C only 1 color in the right spot" +
            "\nOr 1:M means 1 color in the wrong spot.")
    var attempt = 1
    while (attempt <= MAX_ATTEMPTS) {
        val match = collectAndEvalGuess(secret, attempt)
        if (match) {
            println("Well done you guessed correctly.")
            return
        }
        attempt++
    }
    println("Sorry you were not able to guess, the code was $secret")
}

fun collectAndEvalGuess(secret: List<Color>, attempt: Int): Boolean {
    var guess = collectValidGuess(attempt)
    while (guess == null) {
        println("Guess is invalid, try again.")
       guess = collectValidGuess(attempt)
    }
    //TODO: Add hints here
    println("Your guess $guess")

    if(guess == secret) return true
    return false
}

fun collectValidGuess(attempt: Int): List<Color>? {
    println("Enter Guess number $attempt: ")
    val rawGuess = readlnOrNull().orEmpty()
    val convertedGuess: List<Color> = rawGuess
        .trim()
        .split("\\s+".toRegex())
        .mapNotNull { getMatchingColor(it.uppercase()) }
    if(debug) println("DEBUG ON: converted guess: $convertedGuess")

    if(convertedGuess.size != 4) {
        println("Invalid Guess, try again")
        return null
    }
    return convertedGuess
}

fun getMatchingColor(inputUpperCase: String): Color? {
    val color = runCatching { Color.valueOf(inputUpperCase) }.getOrNull()
    //TODO: if color is null check if the first letter matches and use that
    return color
}


fun getRandomColor(): Color =
    Color.entries.random()

enum class Color {
    RED, YELLOW, GREEN, BLUE, BLACK, WHITE
}