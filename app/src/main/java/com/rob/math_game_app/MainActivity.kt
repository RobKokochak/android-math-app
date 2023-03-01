package com.rob.math_game_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rob.math_game_app.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var player1Score = 0
        var player2Score = 0
        var jackpotValue = 5
        var currentPlayer = 0
        var currentPlayerDisplay:Int
        var scoreMultiplier = 1
        var solution = 0
        var scoreVal = 0
        var diceValue = 0

        //set button event handler
        binding.btnRollDie.setOnClickListener {
            binding.tvEquation.text = ""

            diceValue = Random.nextInt(4, 7)

            var diceImageName = "@drawable/dice$diceValue"

            var diceResourceID = resources.getIdentifier(diceImageName, "drawable", packageName)

            binding.ivDie.setImageResource(diceResourceID)

            when(diceValue) {
                1 -> {
                    scoreVal = 1
                    val operand1 = Random.nextInt(0, 100)
                    val operand2 = Random.nextInt(0, 100)
                    solution = operand1 + operand2
                    binding.tvEquation.text = "$operand1 + $operand2 = "
                }
                2 -> {
                    scoreVal = 2
                    val operand1 = Random.nextInt(0, 100)
                    val operand2 = Random.nextInt(0, 100)
                    solution = operand1 - operand2
                    binding.tvEquation.text = "$operand1 - $operand2 = "
                }
                3 -> {
                    scoreVal = 3
                    val operand1 = Random.nextInt(0, 21)
                    val operand2 = Random.nextInt(0, 21)
                    solution = operand1 * operand2
                    binding.tvEquation.text = "$operand1 * $operand2 = "
                }
                4 -> {
                    scoreMultiplier *= 2
                    binding.tvScoreMultiplier.text = "Score Multiplier: $scoreMultiplier"
                    binding.tvEquation.text = "2x points! Roll Again"
                }
                5 -> {
                    currentPlayer = (currentPlayer + 1) % 2
                    currentPlayerDisplay = currentPlayer + 1
                    binding.tvCurrentPlayer.text = "Current Player: $currentPlayerDisplay"
                    binding.tvEquation.text = "Bad Luck! Next Player's Turn"
                }
                6 -> {
                    val operand1 = Random.nextInt(0, 21)
                    val operand2 = Random.nextInt(0, 21)
                    solution = operand1 % operand2
                    binding.tvEquation.text = "$operand1 % $operand2 = "
                }
            }
        }//end setOnClickListener for btnRollDie

        binding.btnGuess.setOnClickListener {
            val guess = Integer.parseInt(binding.etGuess.text.toString())
            if (guess == solution) {
                binding.tvEquation.text = "Correct!"
                when (currentPlayer) {
                    0 -> { // player 1
                        if (diceValue == 6) {
                            player1Score += jackpotValue * scoreMultiplier
                            jackpotValue = 5
                            binding.tvCurrentJackpot.text = "Current Jackpot: $jackpotValue"
                        }
                        player1Score += scoreVal * scoreMultiplier
                        binding.tvP1Score.text = "Player 1 Score: $player1Score"
                    }
                    1 -> { // player 2
                        if (diceValue == 6) {
                            player2Score += jackpotValue * scoreMultiplier
                            jackpotValue = 5
                            binding.tvCurrentJackpot.text = "Current Jackpot: $jackpotValue"
                        }
                        player2Score += scoreVal * scoreMultiplier
                        binding.tvP2Score.text = "Player 2 Score: $player2Score"
                    }
                }
            }
            else { // player got it wrong
                binding.tvEquation.text = "Wrong - Correct Answer: $solution"
                if (diceValue == 6) jackpotValue += jackpotValue * scoreMultiplier
                else jackpotValue += scoreVal * scoreMultiplier
                binding.tvCurrentJackpot.text = "Current Jackpot: $jackpotValue"
            }
            scoreVal = 0
            scoreMultiplier = 1
            currentPlayer = (currentPlayer + 1) % 2
            currentPlayerDisplay = currentPlayer + 1
            binding.tvCurrentPlayer.text = "Current Player: $currentPlayerDisplay"
            binding.tvScoreMultiplier.text = "Score Multiplier: 1"
            binding.etGuess.text.clear()
        }

    }//onCreate end
}