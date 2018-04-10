

import java.awt.Color
import java.awt.Dimension
import java.awt.Font
import java.awt.Graphics
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import java.awt.event.KeyEvent.*
import java.awt.event.WindowEvent
import java.util.*
import javax.swing.*
import javax.swing.Timer
import kotlin.coroutines.experimental.Continuation
import kotlin.coroutines.experimental.CoroutineContext
import kotlin.coroutines.experimental.EmptyCoroutineContext
import kotlin.coroutines.experimental.createCoroutine
import kotlin.coroutines.experimental.intrinsics.COROUTINE_SUSPENDED
import kotlin.coroutines.experimental.intrinsics.suspendCoroutineOrReturn


fun main1() {
    val gameUI = GameSwingUI()
    gameUI.init(object: GameUI.Observer {
        lateinit var game: Game

        override fun onGameStart() {
            game = Game.create(50, 50)
            gameUI.paint(game)
        }

        override fun onTimer() {
            if (game.state != Game.State.Playing) return
            game = game.updateOnTimer()
            gameUI.paint(game)
        }

        override fun onUserInput(direction: Direction) {
            if (game.state != Game.State.Playing) return
            game = game.updateOnUserInput(direction)
            gameUI.paint(game)
        }
    })
}

fun main2() {
    CoGameUI.build(GameSwingUI()) {
        startGame()
        var game = Game.create(50, 50)

        while (game.state == Game.State.Playing) {
            paint(game)

            val event = readEvent()
            game = if (event == null) {
                game.updateOnTimer()
            } else {
                game.updateOnUserInput(event)
            }
        }
        paint(game)
    }
}


interface GameUI {
    fun init(observer: Observer)
    fun paint(game: Game)

    interface Observer {
        fun onGameStart()
        fun onTimer()
        fun onUserInput(direction: Direction)
    }
}


class GameSwingUI: GameUI {
    private lateinit var gamePanel: GamePanel
    private lateinit var jFrame: JFrame
    private lateinit var timer: GameTimer

    override fun init(observer: GameUI.Observer) {
        gamePanel = GamePanel()
        timer = GameTimer {
            SwingUtilities.invokeLater {
                observer.onTimer()
            }
        }.init()

        jFrame = JFrame("Snake")
        jFrame.apply {
            defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
            addKeyListener(object: KeyAdapter() {
                override fun keyPressed(event: KeyEvent) {
                    val direction = when (event.keyCode) {
                        VK_UP, VK_I -> Direction.Up
                        VK_RIGHT, VK_L -> Direction.Right
                        VK_DOWN, VK_K -> Direction.Down
                        VK_LEFT, VK_J -> Direction.Left
                        else -> null
                    }
                    if (direction != null) {
                        observer.onUserInput(direction)
                    }
                    if (event.keyCode == VK_Q) {
                        dispatchEvent(WindowEvent(jFrame, WindowEvent.WINDOW_CLOSING))
                    }
                    if (event.keyCode == VK_S) {
                        observer.onGameStart()
                    }
                }
            })
            add(gamePanel)
            pack()
            setLocationRelativeTo(null)
            isVisible = true
        }

        SwingUtilities.invokeLater {
            observer.onGameStart()
        }
    }

    override fun paint(game: Game) {
        gamePanel.repaintState(game)
    }
}


private class CoGameUI(private val gameUI: GameUI) {
    private var eventContinuation: Continuation<Direction?>? = null

    suspend fun startGame() {
        return suspendCoroutineOrReturn { continuation: Continuation<Unit> ->
            gameUI.init(object: GameUI.Observer {
                override fun onGameStart() {
                    continuation.resume(Unit)
                }

                override fun onTimer() {
                    eventContinuation?.resume(null)
                }

                override fun onUserInput(direction: Direction) {
                    eventContinuation?.resume(direction)
                }
            })
            COROUTINE_SUSPENDED
        }
    }

    fun paint(game: Game) {
        gameUI.paint(game)
    }

    suspend fun readEvent(): Direction? {
        return suspendCoroutineOrReturn { continuation: Continuation<Direction?> ->
            eventContinuation = continuation
            COROUTINE_SUSPENDED
        }
    }

    companion object {
        fun build(gameUI: GameUI, callback: suspend CoGameUI.() -> Unit) {
            val result = CoGameUI(gameUI)
            callback.createCoroutine(result, completion = EmptyContinuation).resume(Unit)
        }
    }
}

object EmptyContinuation: Continuation<Unit> {
    override val context: CoroutineContext = EmptyCoroutineContext
    override fun resume(value: Unit) {}
    override fun resumeWithException(exception: Throwable) = throw exception
}


private class GameTimer(delay: Int = 500, callback: () -> Unit) {
    private val timer = Timer(delay) { callback() }

    fun init() = this.apply {
        timer.start()
    }
}

private class GamePanel: JPanel() {
    private var game: Game? = null
    private val messageFont = Font("DejaVu Sans", Font.BOLD, 35)

    fun repaintState(game: Game) {
        this.game = game
        repaint()
    }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        game?.let { game ->
            val cellWidth = width / game.width
            val cellHeight = height / game.height
            val xPad = cellWidth / 10
            val yPad = cellHeight / 10

            g.color = Color.blue
            for ((x, y) in game.snake.body) {
                g.fillRect(
                    x * cellWidth,
                    y * cellHeight,
                    cellWidth - xPad,
                    cellHeight - yPad
                )
            }
            g.color = Color.red
            for ((x, y) in game.apples) {
                g.fillRect(
                    x * cellWidth,
                    y * cellHeight,
                    cellWidth - xPad,
                    cellHeight - yPad
                )
            }

            if (game.state != Game.State.Playing) {
                val message = "Game Over!"
                g.font = messageFont
                val textWidth = g.fontMetrics.stringWidth(message)
                val textHeight = g.fontMetrics.height
                g.drawString(message, width / 2 - textWidth / 2, height / 2 - textHeight / 2)
            }
        }
    }

    override fun getPreferredSize() = Dimension(800, 800)
}


interface AppleFactory {
    fun produceApples(game: Game): List<Point>

    companion object {
        val noop = object : AppleFactory {
            override fun produceApples(game: Game) = game.apples
        }

        operator fun invoke(random: Random = Random()) = object : AppleFactory {
            override fun produceApples(game: Game): List<Point> = game.run {
                if (apples.size + snake.body.size == width * height) return apples

                val p = Point(random.nextInt(width), random.nextInt(height))
                return if (p !in apples && p !in snake.body) apples + p else produceApples(this)
            }
        }
    }
}

data class Game(
    val width: Int,
    val height: Int,
    val state: State = State.Playing,
    val snake: Snake,
    val apples: List<Point> = emptyList(),
    val appleFactory: AppleFactory = AppleFactory.Companion.noop
) {
    fun updateOnTimer() = update(snake.direction, appleFactory)

    fun updateOnUserInput(direction: Direction) = update(direction, AppleFactory.Companion.noop)

    private fun update(direction: Direction, appleFactory: AppleFactory): Game {
        var newApples = appleFactory.produceApples(this)

        var newSnake = snake.move(direction)
        if (newSnake.body.first() in apples) {
            newSnake = newSnake.copy(body = newSnake.body + snake.body.last())
            newApples = newApples.filter { it != newSnake.body.first() }
        }

        return when {
            newSnake.hitWall() -> copy(state = State.SnakeHitWall)
            newSnake.bitItself -> copy(state = State.SnakeBitItself)
            else -> copy(state = State.Playing, snake = newSnake, apples = newApples)
        }
    }

    private fun Snake.hitWall() = body.any { it.x < 0 || it.x >= width || it.y < 0 || it.y >= height }

    enum class State {
        Playing, SnakeHitWall, SnakeBitItself
    }

    companion object {
        fun create(width: Int, height: Int, snakeLength: Int = 4): Game {
            val x = (width / 2) - snakeLength
            val y = height / 2
            val snake = Snake(0.until(snakeLength).map{ Point(x + it, y) })
            return Game(width, height, State.Playing, snake, emptyList(), AppleFactory(Random()))
        }
    }
}


data class Point(val x: Int, val y: Int) {
    override fun toString() = "($x,$y)"
}

enum class Direction {
    Left, Up, Right, Down
}

data class Snake(val body: List<Point>) {
    constructor(vararg body: Point) : this(body.toList())

    val direction = body.direction

    val bitItself = body.distinct().size != body.size

    fun move(direction: Direction): Snake {
        val reversed = direction.isOppositeTo(body.direction)
        val updatedBody = if (reversed) body.reversed() else body
        val actualDirection = if (reversed) updatedBody.direction else direction
        return Snake(updatedBody.moveIn(actualDirection))
    }

    private fun List<Point>.moveIn(direction: Direction) = listOf(first().moveIn(direction)) + dropLast(1)

    private fun Point.moveIn(direction: Direction) = when (direction) {
        Direction.Left -> Point(x - 1, y)
        Direction.Up -> Point(x, y - 1)
        Direction.Right -> Point(x + 1, y)
        Direction.Down -> Point(x, y + 1)
    }

    private val List<Point>.direction get(): Direction = this.let {
        if (it[0].x == it[1].x - 1) Direction.Left
        else if (it[0].x == it[1].x + 1) Direction.Right
        else if (it[0].y == it[1].y - 1) Direction.Up
        else if (it[0].y == it[1].y + 1) Direction.Down
        else error("")
    }

    private fun Direction.isOppositeTo(direction: Direction): Boolean = when (this) {
        Direction.Left -> direction == Direction.Right
        Direction.Up -> direction == Direction.Down
        Direction.Right -> direction == Direction.Left
        Direction.Down -> direction == Direction.Up
    }
}
