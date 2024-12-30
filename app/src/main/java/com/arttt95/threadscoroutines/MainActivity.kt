package com.arttt95.threadscoroutines

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.arttt95.threadscoroutines.databinding.ActivityMainBinding
import java.lang.Thread.currentThread
import java.lang.Thread.sleep

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    var pararThread = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // onCreate

        val intent = Intent(this, SegundaActivity::class.java)
        binding.btnNovaTela.setOnClickListener {
            startActivity(intent)
        }

        binding.btnIniciar.setOnClickListener {

//            MinhaThread().start()

            Thread(MinhaRunnable()).start()

            /*Thread{
                repeat(30) { indice ->
                    Log.i(
                        "info_thread",
                        "Executando $indice -> T: ${Thread.currentThread().name}"
                    )
                    runOnUiThread {
                        binding.btnIniciar.text =
                            "Minha Thread $indice -> T: ${Thread.currentThread().name}"
                        binding.btnIniciar.isEnabled = false

                        if(indice == 29 ) {
                            binding.btnIniciar.text = "Reiniciar execução"
                            binding.btnIniciar.isEnabled = true
                        }
                    }

                    sleep(1000)

                }
            }.start()*/

            /*repeat(15) { indice ->
                Log.i("info_thread", "Executando $indice -> T: ${currentThread().name}")
                sleep(1000)
            }*/

        }

        binding.btnParar.setOnClickListener {
            pararThread = true
            binding.btnIniciar.text = "Reiniciar execução"
            binding.btnIniciar.isEnabled = true

        }

    }

    inner class MinhaRunnable : Runnable {

        override fun run() {

            repeat(30) { indice ->

                if(pararThread) {
                    pararThread = false
                    return
                }

                Log.i(
                    "info_thread",
                    "Executando $indice -> T: ${/*Thread.*/currentThread().name}"
                )
                runOnUiThread {
                    binding.btnIniciar.text =
                        "Minha Thread $indice -> T: ${/*Thread.*/currentThread().name}"
                    binding.btnIniciar.isEnabled = false

                    if(indice == 29 ) {
                        binding.btnIniciar.text = "Reiniciar execução"
                        binding.btnIniciar.isEnabled = true
                    }
                }

                sleep(1000)

            }

        }

    }

    inner class MinhaThread : Thread() {

        override fun run() {
            super.run()

            repeat(30) { indice ->
                Log.i("info_thread", "Executando $indice -> T: ${currentThread().name}")
                runOnUiThread {
                    binding.btnIniciar.text = "Minha Thread $indice -> T: ${currentThread().name}"
                    binding.btnIniciar.isEnabled = false

                    if(indice == 29 ) {
                        binding.btnIniciar.text = "Reiniciar execução"
                        binding.btnIniciar.isEnabled = true
                    }
                }
                sleep(1000)
            }

        }

    }

}