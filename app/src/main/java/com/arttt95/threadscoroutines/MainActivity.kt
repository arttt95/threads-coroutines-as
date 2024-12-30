package com.arttt95.threadscoroutines

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.arttt95.threadscoroutines.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import java.lang.Thread.currentThread
import java.lang.Thread.sleep
import kotlin.coroutines.coroutineContext
import kotlin.system.measureTimeMillis

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private var pararThread = false
    private var job: Job? = null

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

/*//            MinhaThread().start()
//            Thread(MinhaRunnable()).start()*/
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

            job = CoroutineScope(Dispatchers.IO).launch {

                /*withTimeout(7000L) {
                    executar()
                }*/

//                recuperarUsuarioLogado()
                /*repeat(15) { indice ->

                    Log.i(
                        "info_coroutine",
                        "Executando $indice -> T: ${Thread.currentThread().name}"
                    )

//                    runOnUiThread {
//                        binding.btnIniciar.text = "Executou"
//                    }

                    withContext(Dispatchers.Main) {
                        binding.btnIniciar.text =
                            "Executando $indice -> T: ${Thread.currentThread().name}"
                    }

                    delay(1000)

                }*/

                val tempo = measureTimeMillis {

                    val resultado1 = async {tarefa1() }
                    val resultado2 = async {tarefa2() }

                    withContext(Dispatchers.Main) {
                        binding.btnIniciar.text =
                            "${resultado1.await()}"
                        binding.btnParar.text =
                            "${resultado2.await()}"
                    }


                    Log.i("info_coroutine", "Resultado-1: ${resultado1.await()}")
                    Log.i("info_coroutine", "Resultado-2: ${resultado2.await()}")
                }

                Log.i("info_coroutine", "Tempo: $tempo")

            }

        }

        binding.btnParar.setOnClickListener {
//            pararThread = true
            job?.cancel()
            binding.btnIniciar.text = "Reiniciar execução"
            binding.btnIniciar.isEnabled = true

        }

    }

    /*override fun onStop() {
        super.onStop()

        job?.cancel()

    }*/

    private suspend fun tarefa1() : String {

        repeat(6) { indice ->

            Log.i(
                "info_coroutine",
                "TAREFA-1 $indice -> T: ${Thread.currentThread().name}"
            )

//                    runOnUiThread {
//                        binding.btnIniciar.text = "Executou"
//                    }

            /*withContext(Dispatchers.Main) {
                binding.btnIniciar.text =
                    "Executando $indice -> T: ${Thread.currentThread().name}"
                binding.btnIniciar.isEnabled = false

                if(indice == 14 ) {
                    binding.btnIniciar.text = "Reiniciar execução"
                    binding.btnIniciar.isEnabled = true
                }

            }*/

            delay(1000L)

        }

        return "Executou Tarefa-1"

    }

    private suspend fun tarefa2() : String {

        repeat(5) { indice ->

            Log.i(
                "info_coroutine",
                "TAREFA-2 $indice -> T: ${Thread.currentThread().name}"
            )

//                    runOnUiThread {
//                        binding.btnIniciar.text = "Executou"
//                    }

            /*withContext(Dispatchers.Main) {
                binding.btnIniciar.text =
                    "Executando $indice -> T: ${Thread.currentThread().name}"
                binding.btnIniciar.isEnabled = false

                if(indice == 14 ) {
                    binding.btnIniciar.text = "Reiniciar execução"
                    binding.btnIniciar.isEnabled = true
                }

            }*/

            delay(2000L)

        }

        return "Executou Tarefa-2"

    }

    private suspend fun executar() {

        repeat(15) { indice ->

            Log.i(
                "info_coroutine",
                "Executando $indice -> T: ${Thread.currentThread().name}"
            )

//                    runOnUiThread {
//                        binding.btnIniciar.text = "Executou"
//                    }

            withContext(Dispatchers.Main) {
                binding.btnIniciar.text =
                    "Executando $indice -> T: ${Thread.currentThread().name}"
                binding.btnIniciar.isEnabled = false

                if(indice == 14 ) {
                    binding.btnIniciar.text = "Reiniciar execução"
                    binding.btnIniciar.isEnabled = true
                }

            }

            delay(1000L)

        }
    }

    private suspend fun dadosUsuario() {
        val usuario = recuperarUsuarioLogado()
        Log.i("info_coroutine", "Usuario: ${usuario.nome} T: ${currentThread().name}")
        val postagens = recuperarPostagensPeloId(usuario.id)
        Log.i("info_coroutine", "NPostagens: ${postagens.size} T: ${currentThread().name}")
    }

    private suspend fun recuperarPostagensPeloId(idUsuario: Int) : List<String> {
        delay(2000)
        val listaPostagens = listOf(
            "Viajei para Ubatuba",
            "Comprei um dog",
            "Troquei de carro"
        )

        return listaPostagens
    }

    private suspend fun recuperarUsuarioLogado(): Usuario{

        delay(2000) // Esperando retorno da API para prosseguir

        return Usuario(1020, "Shisui")

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