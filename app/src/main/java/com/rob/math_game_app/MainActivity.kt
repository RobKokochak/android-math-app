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
        var dieRolledFlag = 0
        var questionValue = 0

        //set button event handler
        binding.btnRollDie.setOnClickListener {
            if (dieRolledFlag == 0) { // die has not been rolled, give a new dice value & equation

                diceValue = Random.nextInt(1, 7)

                val diceImageName = "@drawable/dice$diceValue"

                val diceResourceID = resources.getIdentifier(diceImageName, "drawable", packageName)

                binding.ivDie.setImageResource(diceResourceID)

                dieRolledFlag = 1

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
                        dieRolledFlag = 0
                    }
                    5 -> {
                        currentPlayer = (currentPlayer + 1) % 2
                        currentPlayerDisplay = currentPlayer + 1
                        binding.tvCurrentPlayer.text = "Current Player: $currentPlayerDisplay"
                        binding.tvEquation.text = "Bad Luck! Next Player's Turn"
                        dieRolledFlag = 0
                    }
                    6 -> {
                        scoreVal = jackpotValue
                        val operand1 = Random.nextInt(0, 21)
                        val operand2 = Random.nextInt(0, 21)
                        solution = operand1 % operand2
                        binding.tvEquation.text = "$operand1 % $operand2 = "
                    }
                }
                questionValue = scoreVal * scoreMultiplier
                binding.tvQuestionValue.text = "Question Value: $questionValue"
            }
        }//end setOnClickListener for btnRollDie


        binding.btnGuess.setOnClickListener {
            if (binding.etGuess.text.toString() != "" && dieRolledFlag == 1) {
                val guess = Integer.parseInt(binding.etGuess.text.toString())
                if (guess == solution) {
                    binding.tvEquation.text = "Correct!"
                    when (currentPlayer) {
                        0 -> { // player 1
                            player1Score += questionValue
                            if (diceValue == 6) {
                                jackpotValue = 5
                                binding.tvCurrentJackpot.text = "Current Jackpot: $jackpotValue"
                            }
                            binding.tvP1Score.text = "Player 1 Score: $player1Score"
                        }
                        1 -> { // player 2
                            player2Score += questionValue
                            if (diceValue == 6) {
                                jackpotValue = 5
                                binding.tvCurrentJackpot.text = "Current Jackpot: $jackpotValue"
                            }
                            binding.tvP2Score.text = "Player 2 Score: $player2Score"
                        }
                    }
                } else { // player got it wrong
                    binding.tvEquation.text = "Wrong - Correct Answer: $solution"
                    jackpotValue += questionValue
                    binding.tvCurrentJackpot.text = "Current Jackpot: $jackpotValue"
                }

                scoreVal = 0
                scoreMultiplier = 1
                currentPlayer = (currentPlayer + 1) % 2
                currentPlayerDisplay = currentPlayer + 1
                binding.tvCurrentPlayer.text = "Current Player: $currentPlayerDisplay"
                binding.tvScoreMultiplier.text = "Score Multiplier: 1"
                binding.etGuess.text.clear()
                dieRolledFlag = 0
                questionValue = 0
                binding.tvQuestionValue.text = "Question Value: 0"

                if (player1Score >= 20 || player2Score >= 20) {
                    if (player1Score > player2Score) binding.tvEquation.text = "Player 1 wins! Start again"
                    else binding.tvEquation.text = "Player 2 wins! Start again"
                    player1Score = 0
                    player2Score = 0
                    jackpotValue = 5
                    currentPlayer = 0
                    binding.tvP1Score.text = "Player 1 Score: 0"
                    binding.tvP2Score.text = "Player 2 Score: 0"
                    binding.tvCurrentJackpot.text = "Current Jackpot: 5"
                }
            }
        } //end setOnClickListener for btnGuess

    }//onCreate end
}